package com.baidu.unbiz.fluentvalidator.exception;

/**
 * 验证器抛出运行时异常
 * <p/>
 * 所有验证过程中发生的异常会被这个运行时异常包装，验证调用点可以显示捕获，并且拿到内部的实际异常。一种典型的使用方法如下：
 * <pre>
 *     try {
 *         Result ret = ValidUtils.checkAll().failFast()
 *             .on(car.getLicensePlate(), new CarLicensePlateValidator())
 *             .doValidate().result(toSimple());
 *     } catch (RuntimeValidateException e) {
 *         assertThat(e.getCause().getMessage(), is("Call Rpc failed"));
 *     }
 * </pre>
 * @author zhangxu
 */
public class RuntimeValidateException extends RuntimeException {

    public RuntimeValidateException() {
    }

    public RuntimeValidateException(String message) {
        super(message);
    }

    public RuntimeValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeValidateException(Throwable cause) {
        super(cause);
    }

}
