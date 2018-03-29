package com.baidu.unbiz.fluentvalidator;

import com.baidu.unbiz.fluentvalidator.able.ListAble;
import com.baidu.unbiz.fluentvalidator.able.ToStringable;
import com.baidu.unbiz.fluentvalidator.annotation.NotThreadSafe;
import com.baidu.unbiz.fluentvalidator.annotation.Stateful;
import com.baidu.unbiz.fluentvalidator.exception.RuntimeValidateException;
import com.baidu.unbiz.fluentvalidator.registry.Registry;
import com.baidu.unbiz.fluentvalidator.registry.impl.SimpleRegistry;
import com.baidu.unbiz.fluentvalidator.support.GroupingHolder;
import com.baidu.unbiz.fluentvalidator.util.*;
import com.baidu.unbiz.fluentvalidator.validator.element.IterableValidatorElement;
import com.baidu.unbiz.fluentvalidator.validator.element.MultiValidatorElement;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 链式调用验证器
 * <p/>
 * 按照<a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent Interface</a>风格实现的验证工具，以一种近似于可以语义解释的方式做对象的验证。
 * <p/>
 * 典型的调用方式如下：
 * <pre>
 * Result ret = ValidUtils.checkAll().failFast()
 *     .on(car.getLicensePlate(), new CarLicensePlateValidator())
 *     .on(car.getManufacturer(), new CarManufacturerValidator())
 *     .on(car.getSeatCount(), new CarSeatCountValidator())
 *     .doValidate().result(toSimple());
 * </pre>
 */
@NotThreadSafe
@Stateful
public class ValidUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidUtils.class);

    /**
     * 验证器链，惰性求值期间就是不断的改变这个链表，及时求值期间就是遍历链表依次执行验证
     */
    private ValidatorElementList validatorElementList = new ValidatorElementList();
    /**
     * 双链结构
     */
    private ValidatorElementList tempValidatorElementList = new ValidatorElementList();

    /**
     * 传值的 key
     */
    private String key = "ValidUtils.key";
    private final static String DefaultKey = "ValidUtils.key";

    /**
     * 是否一旦发生验证错误就退出，默认为true
     * @see #failFast()
     * @see #failOver()
     */
    private boolean isFailFast = true;

    /**
     * 验证器上下文
     * <p/>
     * 该<tt>context</tt>可以在所有验证器间共享数据
     */
    private ValidatorContext context = new ValidatorContext();

    /**
     * 验证结果，仅内部使用，外部使用验证结果需要使用{@link #result(ResultCollector)}来做收殓处理
     */
    private ValidationResult result = new ValidationResult();

    /**
     * 默认验证回调
     */
    protected ValidateCallback defaultCb = new DefaultValidateCallback();

    /**
     * 如果启用通过{@link com.baidu.unbiz.fluentvalidator.annotation.FluentValidate}注解方式的验证，需要寻找验证器实例，这里为注册中心
     * <p/>
     * 通过{@link #configure(Registry)}来配置
     */
    private Registry registry = new SimpleRegistry();

    /**
     * 记录上一次添加的验证器数量，用于{@link #when(boolean)}做判断条件是否做当前验证
     */
    private int lastAddCount = 0;

    /**
     * 将键值对放入上下文
     * @param key 键
     * @param value 值
     * @return ValidUtils
     */
    public ValidUtils putAttribute2Context(String key, Object value) {
        if (context == null) {
            context = new ValidatorContext();
        }
        context.setAttribute(key, value);
        return this;
    }

    /**
     * 将闭包注入上下文
     * @param key 键
     * @param value 闭包
     * @return ValidUtils
     */
    public ValidUtils putClosure2Context(String key, Closure value) {
        if (context == null) {
            context = new ValidatorContext();
        }
        context.setClosure(key, value);
        return this;
    }

    /**
     * 私有构造方法，只能通过{@link #checkAll()}去创建对象
     */
    protected ValidUtils() {
    }

    /**
     * 创建<tt>ValidUtils</tt>
     * @return ValidUtils
     */
    public static ValidUtils checkAll() {
        return checkAll(new Class[]{});
    }

    public String getKey() {
        return key;
    }

    /**
     * Groupings分组，有两个用途：
     * <ul>
     * <li>1. 当启用注解声明式验证时，用于区分是否做某次校验</li>
     * <li>1. 当Hibernate Validator时，含义和该框架内部的分组grouping相同</li>
     * </ul>
     */
    private Class<?>[] groups;

    /**
     * 排除的Groupings分组，当启用注解声明式验证时，用于区分是否做某次校验
     */
    private Class<?>[] excludeGroups;

    /**
     * 创建<tt>ValidUtils</tt>
     * @param groups 分组
     * @return ValidUtils
     */
    public static ValidUtils checkAll(Class... groups) {
        return new ValidUtils().setGroups(groups);
    }

    /**
     * 使用已经存在的一个验证上下文，共享context本身以及验证结果
     * @param context 验证上下文
     * @return ValidUtils
     */
    public ValidUtils withContext(ValidatorContext context) {
        this.context = context;
        this.result = context.result;
        return this;
    }

    /**
     * 出错即退出
     * @return ValidUtils
     */
    public ValidUtils failFast() {
        this.isFailFast = true;
        return this;
    }

    /**
     * 出错不退出而继续
     * @return ValidUtils
     */
    public ValidUtils failOver() {
        this.isFailFast = false;
        return this;
    }

    /**
     * 如果启用通过{@link com.baidu.unbiz.fluentvalidator.annotation.FluentValidate}注解方式的验证，需要寻找验证器实例，这里配置注册中心的步骤
     * @param registry 验证器注册查找器
     * @return ValidUtils
     */
    public ValidUtils configure(Registry registry) {
        Preconditions.checkNotNull(registry, "Registry should not be NULL");
        this.registry = registry;
        return this;
    }

    /**
     * 在某个对象上通过{@link com.baidu.unbiz.fluentvalidator.annotation.FluentValidate}注解方式的验证，
     * 需要保证{@link #configure(Registry)}已经先执行配置完毕<code>Registry</code>
     * @param t 待验证对象
     * @return ValidUtils
     */
    public <T> ValidUtils on(T t) {
        MultiValidatorElement multiValidatorElement = doOn(t);
        LOGGER.debug(multiValidatorElement + " will be performed");
        lastAddCount = multiValidatorElement.size();
        return this;
    }

    /**
     * 在某个数组对象上通过{@link com.baidu.unbiz.fluentvalidator.annotation.FluentValidate}注解方式的验证，
     * 需要保证{@link #configure(Registry)}已经先执行配置完毕<code>Registry</code>
     * <p/>
     * 注：当数组为空时，则会跳过
     * @param t 待验证对象
     * @return ValidUtils
     */
    public <T> ValidUtils onEach(T[] t) {
        if (ArrayUtil.isEmpty(t)) {
            lastAddCount = 0;
            return this;
        }

        return onEach(Arrays.asList(t));
    }

    /**
     * 在某个集合对象上通过{@link com.baidu.unbiz.fluentvalidator.annotation.FluentValidate}注解方式的验证，
     * 需要保证{@link #configure(Registry)}已经先执行配置完毕<code>Registry</code>
     * <p/>
     * 注：当集合为空时，则会跳过
     * @param t 待验证对象
     * @return ValidUtils
     */
    public <T> ValidUtils onEach(Collection<T> t) {
        if (CollectionUtil.isEmpty(t)) {
            lastAddCount = 0;
            return this;
        }

        MultiValidatorElement multiValidatorElement = null;
        for (T element : t) {
            multiValidatorElement = doOn(element);
            lastAddCount += multiValidatorElement.size();
        }
        LOGGER.debug(
                String.format("Total %d of %s will be performed", t.size(), multiValidatorElement));
        return this;
    }

    /**
     * 在某个对象上通过{@link com.baidu.unbiz.fluentvalidator.annotation.FluentValidate}注解方式的验证，
     * 需要保证{@link #configure(Registry)}已经先执行配置完毕<code>Registry</code>
     * @param t 待验证对象
     * @return ValidUtils
     */
    //TODO That would be much more easier if leveraging Java8 lambda feature
    protected <T> MultiValidatorElement doOn(final T t) {
        if (registry == null) {
            throw new RuntimeValidateException("When annotation-based validation enabled, one must use configure"
                    + "(Registry) method to let ValidUtils know where to search and get validator instances");
        }
        List<AnnotationValidator> anntValidatorsOfAllFields =
                AnnotationValidatorCache.getAnnotationValidator(registry, t);
        if (CollectionUtil.isEmpty(anntValidatorsOfAllFields)) {
            // no field configured with annotation
            return new MultiValidatorElement(Collections.EMPTY_LIST);
        }

        List<ValidatorElement> elementList = CollectionUtil.createArrayList();
        for (final AnnotationValidator anntValidatorOfOneField : anntValidatorsOfAllFields) {
            Object realTarget = ReflectionUtil.invokeMethod(anntValidatorOfOneField.getMethod(), t);

            if (!CollectionUtil.isEmpty(anntValidatorOfOneField.getValidators())) {
                if (!ArrayUtil.hasIntersection(anntValidatorOfOneField.getGroups(), groups)) {
                    // groups have no intersection
                    LOGGER.debug(String.format("Current groups: %s not match %s", Arrays.toString(groups),
                            anntValidatorOfOneField));
                    continue;
                }

                if (!ArrayUtil.isEmpty(excludeGroups)) {
                    if (ArrayUtil.hasIntersection(anntValidatorOfOneField.getGroups(), excludeGroups)) {
                        LOGGER.debug(String.format("Current groups: %s will be ignored because you specify %s",
                                Arrays.toString(
                                        excludeGroups), anntValidatorOfOneField));
                        continue;
                    }
                }

                for (final Validator v : anntValidatorOfOneField.getValidators()) {
                    elementList.add(new ValidatorElement(realTarget, v, new ToStringable() {
                        @Override
                        public String toString() {
                            return String.format("%s#%s@%s", t.getClass().getSimpleName(),
                                    anntValidatorOfOneField.getField().getName(), v);
                        }
                    }));
                }
            }

            // cascade handle
            if (anntValidatorOfOneField.isCascade()) {
                Field field = anntValidatorOfOneField.getField();
                if (Collection.class.isAssignableFrom(field.getType())) {
                    onEach((Collection) realTarget);
                } else if (field.getType().isArray()) {
                    onEach(ArrayUtil.toWrapperIfPrimitive(realTarget));
                } else {
                    on(realTarget);
                }
            }
        }
        MultiValidatorElement m = new MultiValidatorElement(elementList);
        validatorElementList.add(m);
        return m;
    }

    /**
     * 在待验证对象<tt>t</tt>上，使用<tt>v</tt>验证器进行验证
     * @param t 待验证对象
     * @param v 验证器
     * @return ValidUtils
     */
    public <T> ValidUtils on(T t, Validator<T> v) {
        Preconditions.checkNotNull(v, "Validator should not be NULL");
        composeIfPossible(v, t);
        doAdd(new ValidatorElement(t, v));
        lastAddCount = 1;
        return this;
    }

    /**
     * 在待验证对象<tt>t</tt>上，使用<tt>chain</tt>验证器链进行验证
     * @param t 待验证对象
     * @param chain 验证器链
     * @return ValidUtils
     */
    public <T> ValidUtils on(T t, ValidChain chain) {
        Preconditions.checkNotNull(chain, "ValidChain should not be NULL");
        final ValidUtils self = this;
        if (CollectionUtil.isEmpty(chain.getValidators())) {
            lastAddCount = 0;
        } else {
            for (Validator v : chain.getValidators()) {
                composeIfPossible(v, t);
                doAdd(new ValidatorElement(t, v));
            }
            lastAddCount = chain.getValidators().size();
        }

        return this;
    }

    /*public <T> ValidUtils onKey(String key, ValidChain chain) {
        Preconditions.checkNotNull(chain, "ValidChain should not be NULL");
        Object t = null;
        Object attribute = context.getAttribute(context.getKey());
        if ("own".equalsIgnoreCase(key)) {
            t = attribute;
        } else if (key.startsWith("own") && key.contains(".")) {
            String ownProperties1 = key.substring(key.indexOf("."), key.length());
            try {
                t = BeanUtils.getProperty(attribute, ownProperties1);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            t = attribute;
        }


        final ValidUtils self = this;
        if (CollectionUtil.isEmpty(chain.getValidators())) {
            lastAddCount = 0;
        } else {
            for (Validator v : chain.getValidators()) {
                doAdd(new ValidatorElement(t, v));
            }
            lastAddCount = chain.getValidators().size();
        }

        return this;
    }
*/

    /**
     * 在待验证对象数组<tt>t</tt>上，使用<tt>v</tt>验证器进行验证
     * <p/>
     * 注：当数组为空时，则会跳过
     * @param t 待验证对象数组
     * @param v 验证器
     * @return ValidUtils
     */
    public <T> ValidUtils onEach(T[] t, final Validator<T> v) {
        Preconditions.checkNotNull(v, "Validator should not be NULL");
        if (ArrayUtil.isEmpty(t)) {
            lastAddCount = 0;
            return this;
        }

        return onEach(Arrays.asList(t), v);
    }

    /**
     * 在待验证对象集合<tt>t</tt>上，使用<tt>v</tt>验证器进行验证
     * <p/>
     * 注：当集合为空时，则会跳过
     * @param t 待验证对象集合
     * @param v 验证器
     * @return ValidUtils
     */
    public <T> ValidUtils onEach(Collection<T> t, final Validator<T> v) {
        Preconditions.checkNotNull(v, "Validator should not be NULL");
        if (CollectionUtil.isEmpty(t)) {
            lastAddCount = 0;
        } else {
            List<ValidatorElement> elementList = CollectionUtil.transform(t, new Function<T, ValidatorElement>() {
                @Override
                public ValidatorElement apply(T elem) {
                    composeIfPossible(v, elem);
                    return new ValidatorElement(elem, v);
                }
            });
            lastAddCount = t.size();
            doAdd(new IterableValidatorElement(elementList));
        }

        return this;
    }

    /**
     * 将验证对象及其验证器放入{@link #validatorElementList}中
     * @param listAble 验证对象及其验证器封装类
     */
    protected void doAdd(ListAble<ValidatorElement> listAble) {
        validatorElementList.add(listAble);
        LOGGER.debug(listAble + " will be performed");
    }


    public ValidUtils key(String key) {
        if (validatorElementList == null || validatorElementList.isEmpty()) {
            this.key = key;
        } else {
            for (int i = 0; i < lastAddCount; i++) {
                ListAble<ValidatorElement> validatorElementListAble = validatorElementList.getList().removeLast();
                List<ValidatorElement> asList = validatorElementListAble.getAsList();
                ValidatorElement validatorElement = asList.get(0);
                validatorElement.setKey(key);
                tempValidatorElementList.add(validatorElement);
            }
            for (int i = 0; i < lastAddCount; i++) {
                ListAble<ValidatorElement> validatorElementListAble = tempValidatorElementList.getList().removeLast();
                List<ValidatorElement> asList = validatorElementListAble.getAsList();
                ValidatorElement validatorElement = asList.get(0);
                validatorElementList.add(validatorElement);
            }
        }
        return this;
    }


    /**
     * 当满足<code>expression</code>条件时，才去使用前一个{@link Validator}或者{@link ValidChain}来验证
     * @param expression 满足条件表达式
     * @return ValidUtils
     */
    public ValidUtils when(boolean expression) {
        if (!expression) {
            for (int i = 0; i < lastAddCount; i++) {
                ListAble<ValidatorElement> validatorElementListAble = validatorElementList.getList().removeLast();
                List<ValidatorElement> asList = validatorElementListAble.getAsList();
                ValidatorElement validatorElement = asList.get(0);
                validatorElement.setCheck(false);
                tempValidatorElementList.add(validatorElement);
            }
            for (int i = 0; i < lastAddCount; i++) {
                ListAble<ValidatorElement> validatorElementListAble = tempValidatorElementList.getList().removeLast();
                List<ValidatorElement> asList = validatorElementListAble.getAsList();
                ValidatorElement validatorElement = asList.get(0);
                validatorElementList.add(validatorElement);
            }
        }
        return this;
    }

    /**
     * @param key 别名1
     * @param alia 别名2
     * @return ValidUtils
     */
    public ValidUtils alias(String key, String alia) {
        for (int i = 0; i < lastAddCount; i++) {
            ListAble<ValidatorElement> validatorElementListAble = validatorElementList.getList().removeLast();
            List<ValidatorElement> asList = validatorElementListAble.getAsList();
            ValidatorElement validatorElement = asList.get(0);
            validatorElement.setTargetNameFirst(key);
            validatorElement.setKey(key);
            validatorElement.setTargetNameTwo(alia);
            tempValidatorElementList.add(validatorElement);
        }
        for (int i = 0; i < lastAddCount; i++) {
            ListAble<ValidatorElement> validatorElementListAble = tempValidatorElementList.getList().removeLast();
            List<ValidatorElement> asList = validatorElementListAble.getAsList();
            ValidatorElement validatorElement = asList.get(0);
            validatorElementList.add(validatorElement);
        }
        return this;
    }

    public static class Test {

        private ValidationResult result;

        public Test(ValidationResult result) {
            this.result = result;
        }

        public <T> T result(ResultCollector<T> resultCollector) {
            return resultCollector.toResult(result);
        }
    }

    /**
     * 按照默认验证回调条件，开始使用验证
     * @return ValidUtils
     */
    public Test doValidate() {
        return doValidate(defaultCb);
    }

    /**
     * 按照指定验证回调条件，开始使用验证
     * @param cb 验证回调
     * @return ValidUtils
     * @see ValidateCallback
     */
    public Test doValidate(ValidateCallback cb) {
        Preconditions.checkNotNull(cb, "ValidateCallback should not be NULL");
        if (validatorElementList.isEmpty()) {
            LOGGER.debug("Nothing to validate");
            return new Test(result);
        }
        context.setResult(result);

        LOGGER.debug("Start to validate through " + validatorElementList);
        long start = System.currentTimeMillis();
        try {
            GroupingHolder.setGrouping(groups);
            for (ValidatorElement element : validatorElementList.getAllValidatorElements()) {
                Object target = element.getTarget();
                Validator v = element.getValidator();
                context.setTargetNameFirst(element.getTargetNameFirst());
                context.setTargetNameTwo(element.getTargetNameTwo());
                if (!DefaultKey.equals(getKey())) {
                    context.setKey(getKey());
                }
                try {
                    if (element.isCheck() && v.accept(context, target)) {
                        result.setInvalidValue(target);
                        if (!v.validate(context, element, target)) {
                            result.setIsSuccess(false);
                            if (isFailFast) {
                                result.setKeyValue(context.getAttribute(getKey()));
                                break;
                            }
                        }
                        result.setKeyValue(context.getAttribute(getKey()));
                    }
                } catch (Exception e) {
                    result.setKeyValue(context.getAttribute(getKey()));
                    try {
                        v.onException(e, context, target);
                        cb.onUncaughtException(v, e, target);
                    } catch (Exception e1) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.error(v + " onException or onUncaughtException throws exception due to " + e1
                                    .getMessage(), e1);
                        }
                        throw new RuntimeValidateException(e1);
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.error(v + " failed due to " + e.getMessage(), e);
                    }
                    throw new RuntimeValidateException(e);
                }
            }

            if (result.isSuccess()) {
                cb.onSuccess(validatorElementList);
            } else {
                cb.onFail(validatorElementList, result.getErrors());
            }
        } finally {
            GroupingHolder.clean();
            int timeElapsed = (int) (System.currentTimeMillis() - start);
            LOGGER.debug("End to validate through" + validatorElementList + " costing " + timeElapsed + "ms with "
                    + "isSuccess=" + result.isSuccess());
            result.setTimeElapsed(timeElapsed);
        }
        // return this;
        return new Test(result);
    }

    /**
     * 转换为对外的验证结果，在<code>ValidUtils.on(..).on(..).doValidate()</code>这一连串“<a href="https://en.wikipedia
     * .org/wiki/Lazy_evaluation">惰性求值</a>”计算后的“及时求值”收殓出口。
     * <p/>
     * &lt;T&gt;是验证结果的泛型
     * @param resultCollector 验证结果收集器
     * @return 对外验证结果
     */
    /*public <T> T result(ResultCollector<T> resultCollector) {
        return resultCollector.toResult(result);
    }*/

    /**
     * 设置分组
     * @param groups 分组
     * @return ValidUtils
     */
    public ValidUtils setGroups(Class<?>[] groups) {
        this.groups = groups;
        return this;
    }

    /**
     * 设置是否快速失败
     * @param isFailFast 是否快速失败
     * @return ValidUtils
     */
    public ValidUtils setIsFailFast(boolean isFailFast) {
        this.isFailFast = isFailFast;
        return this;
    }

    /**
     * 设置排除的分组
     * @param excludeGroups 排除分组
     * @return ValidUtils
     */
    public ValidUtils setExcludeGroups(Class<?>[] excludeGroups) {
        this.excludeGroups = excludeGroups;
        return this;
    }

    /**
     * 如果验证器是一个{@link ValidatorHandler}实例，那么可以通过{@link ValidatorHandler#compose(ValidUtils, ValidatorContext, Object)}
     * 方法增加一些验证逻辑
     * @param v 验证器
     * @param t 待验证对象
     */
    private <T> void composeIfPossible(Validator<T> v, T t) {
        final ValidUtils self = this;
        if (v instanceof ValidatorHandler) {
            ((ValidatorHandler) v).compose(self, context, t);
        }
    }


}