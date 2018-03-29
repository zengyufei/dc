package com.baidu.unbiz.fluentvalidator;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 消息传输类。结果返回集。
 * 可用在 service 服务层或 controller 控制层层
 * 有三种快捷方式: ok,fail 和 error
 * error 与 fail 一样的功能，只是名称不一样。
 * @author zengyufei
 * @since 1.0.0
 */
public class Msg {

    private boolean success = true;    //是否成功
    private Object data;        //数据
    private String message;     //信息
    private long code;       //错误代码
    @JsonIgnore
    private Object invalidData;     //被验证的值
    @JsonIgnore
    private String logMessage;     //错误日志

    public Object getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public long getCode() {
        return this.code;
    }

    public String getLogMessage() {
        return this.logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Msg() {
    }

    public Msg(int status) {
        this.code = status;
    }

    public Msg(String msg, Object data) {
        this.message = msg;
        this.data = data;
    }

    public Msg(boolean success, String msg, Object data) {
        this.success = success;
        this.message = msg;
        this.data = data;
    }

    public Msg(int status, String msg, Object data) {
        this.code = status;
        this.message = msg;
        this.data = data;
    }

    public Msg(boolean success, int status, String msg, Object data) {
        this.success = success;
        this.code = status;
        this.message = msg;
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public static class Test {

        private Object invalidData;

        public Test(Object invalidData) {
            this.invalidData = invalidData;
        }

        @Override
        public String toString() {
            if (this.invalidData == null)
                return "";
            return this.invalidData.toString();
        }

        public String toJson() {
            return JSONObject.toJSONString(this.invalidData);
        }

    }

    public Test getInvalidData() {
        return new Test(this.invalidData);
    }

    public void setInvalidData(Object invalidData) {
        this.invalidData = invalidData;
    }

    public static Msg.BodyBuilder status(boolean success, int code) {
        return new Msg.DefaultBuilder(success, code);
    }

    public static Msg.BodyBuilder status(boolean success) {
        return new Msg.DefaultBuilder(success);
    }

    /* 快捷输出 start */
    public static Msg.BodyBuilder ok() {
        return status(true);
    }

    public static Msg.BodyBuilder ok(int code) {
        return status(true, code);
    }

    public static Msg ok(Object data) {
        Msg.BodyBuilder builder = ok();
        return builder.body(data);
    }

    public static Msg ok(String msg) {
        Msg.BodyBuilder builder = ok();
        return builder.msg(msg).build();
    }

    public static Msg ok(int code, Object data) {
        Msg.BodyBuilder builder = ok(code);
        return builder.body(data);
    }

    public static Msg ok(String msg, Object data) {
        Msg.BodyBuilder builder = ok();
        return builder.msg(msg).body(data);
    }

    public static Msg ok(int code, String msg, Object data) {
        Msg.BodyBuilder builder = ok(code);
        return builder.msg(msg).body(data);
    }

    public static Msg.BodyBuilder fail() {
        return status(false);
    }

    public static Msg.BodyBuilder fail(int code) {
        return status(false, code);
    }

    public static Msg fail(Object data) {
        Msg.BodyBuilder builder = fail();
        return builder.body(data);
    }

    public static Msg fail(String msg) {
        Msg.BodyBuilder builder = fail();
        return builder.msg(msg).build();
    }

    public static Msg fail(String msg, Object data) {
        Msg.BodyBuilder builder = fail();
        return builder.msg(msg).body(data);
    }

    public static Msg fail(int code, String msg, Object data) {
        Msg.BodyBuilder builder = fail(code);
        return builder.msg(msg).body(data);
    }

    public static Msg error(Object data) {
        Msg.BodyBuilder builder = fail();
        return builder.body(data);
    }

    public static Msg error(String msg) {
        Msg.BodyBuilder builder = fail();
        return builder.msg(msg).build();
    }

    public static Msg error(int code, Object data) {
        Msg.BodyBuilder builder = fail(code);
        return builder.body(data);
    }

    public static Msg error(String msg, Object data) {
        Msg.BodyBuilder builder = fail();
        return builder.msg(msg).body(data);
    }

    public static Msg error(int code, String msg, Object data) {
        Msg.BodyBuilder builder = fail(code);
        return builder.msg(msg).body(data);
    }
    /* 快捷输出 end */

    private static class DefaultBuilder implements Msg.BodyBuilder {
        private boolean success;
        private int code;
        private String message;

        public DefaultBuilder(boolean success) {
            this.success = success;
        }

        public DefaultBuilder(boolean success, int code) {
            this.success = success;
            this.code = code;
        }

        public DefaultBuilder(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        @Override
        public Msg body(Object data) {
            Msg msg = new Msg();
            msg.success = this.success;
            msg.message = this.message;
            msg.code = this.code;
            if (data instanceof Number) {
                return new Msg(this.success, this.message, data);
            }
            msg.data = data;
            if (msg.data == null) {
                msg.data = new Object();
            }
            return msg;
        }

        @Override
        public Msg.BodyBuilder msg(String message) {
            this.message = message;
            return this;
        }

        @Override
        public Msg build() {
            return new Msg(this.success, this.code, this.message, "");
        }
    }

    public interface BodyBuilder {

        Msg body(Object var1);

        Msg.BodyBuilder msg(String message);

        Msg build();
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    static class SimpleResultCollectorImpl implements ResultCollector<Msg> {

        @Override
        public Msg toResult(ValidationResult result) {
            Msg msg = new Msg();
            msg.setInvalidData(result.getInvalidValue());
            msg.setSuccess(result.isSuccess());
            msg.setData(result.getKeyValue());
            if (result.isSuccess()) {
                if (result.getErrors() != null && !result.getErrors().isEmpty()) {
                    ValidError validError = result.getErrors().get(0);
                    msg.setCode(validError.getErrorCode());
                    msg.setMessage(validError.getErrorMsg());
                    msg.setLogMessage(validError.getErrorLogMsg());
                } else {
                    msg.setCode(200);
                }
            } else {
                if (result.getErrors() != null && !result.getErrors().isEmpty()) {
                    ValidError validError = result.getErrors().get(0);
                    msg.setCode(validError.getErrorCode());
                    msg.setMessage(validError.getErrorMsg());
                    msg.setLogMessage(validError.getErrorLogMsg());
                } else {
                    msg.setCode(500);
                }
            }
            return msg;
        }
    }

    public static ResultCollector<Msg> toMsg() {
        return new SimpleResultCollectorImpl();
    }
}