/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: BindValidExceptionHandler
 * Author:   zengyufei
 * Date:     2017/11/23
 * Description: jsr 303 错误捕捉类
 */


package com.zyf.dc.exceptionHandler;

import com.zyf.dc.base.Msg;
import com.zyf.dc.exceptions.ValidError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * jsr 303 错误捕捉类
 * @author zengyufei
 * @since 1.0.0
 */
@ControllerAdvice
@Component
public class BindValidExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(BindValidExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody
    Msg handleConstraintViolationException(ConstraintViolationException e) {
        String messageTemplate = e.getConstraintViolations().iterator().next().getMessageTemplate();
        log.error("方法级别参数验证不通过。错误提示：{}", messageTemplate);
        return Msg.error(messageTemplate);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public @ResponseBody
    Msg handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String className = bindingResult.getTarget().getClass().getName();
        FieldError next = bindingResult.getFieldErrors().iterator().next();
        String fieldName = next.getField();
        String defaultMessage = next.getDefaultMessage();
        if (Pattern.compile("IllegalArgumentException: No enum").matcher(defaultMessage).find()) {
            Matcher matcher = Pattern.compile("for value '(.*?)'").matcher(defaultMessage);
            if (matcher.find()) {
                defaultMessage = "找不到枚举类型【" + matcher.group(1) + "】";
            }
        }
        log.debug("控制层的对象 {} 属性 {} 验证不通过。错误提示：{}", className, fieldName, defaultMessage);
        return Msg.error(defaultMessage);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(ValidError.class)
    public @ResponseBody
    Msg handleValidError(ValidError e) {
        String[] split = e.getMessage().split("-");
        String className = split[0];
        String fieldName = split[1];
        String message = split[2];
        log.debug("工具类对象 {} 属性 {} 验证不通过。错误提示：{}", className, fieldName, message);
        return Msg.error(message);
    }

}