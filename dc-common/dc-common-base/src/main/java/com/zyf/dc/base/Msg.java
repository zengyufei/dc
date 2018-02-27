/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: Msg
 * Author:   zengyufei
 * Date:     2017-9-28 16:39
 * Description: 消息传输类。结果返回集。
 */
package com.zyf.dc.base;

import java.io.PrintStream;

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

    public Object getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public long getCode() {
        return this.code;
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

    public static BodyBuilder status(boolean success, int code) {
        return new DefaultBuilder(success, code);
    }

    public static BodyBuilder status(boolean success) {
        return new DefaultBuilder(success);
    }

    /* 快捷输出 start */
    public static BodyBuilder ok() {
        return status(true);
    }

    public static BodyBuilder ok(int code) {
        return status(true, code);
    }

    public static Msg ok(Object data) {
        BodyBuilder builder = ok();
        return builder.body(data);
    }

    public static Msg ok(String msg) {
        BodyBuilder builder = ok();
        return builder.msg(msg).build();
    }

    public static Msg ok(String msg, Object data) {
        BodyBuilder builder = ok();
        return builder.msg(msg).body(data);
    }

    public static Msg ok(int code, String msg, Object data) {
        BodyBuilder builder = ok(code);
        return builder.msg(msg).body(data);
    }

    public static BodyBuilder fail() {
        return status(false);
    }

    public static BodyBuilder fail(int code) {
        return status(false, code);
    }

    public static Msg fail(Object data) {
        BodyBuilder builder = fail();
        return builder.body(data);
    }

    public static Msg fail(String msg) {
        BodyBuilder builder = fail();
        return builder.msg(msg).build();
    }

    public static Msg fail(String msg, Object data) {
        BodyBuilder builder = fail();
        return builder.msg(msg).body(data);
    }

    public static Msg fail(int code, String msg, Object data) {
        BodyBuilder builder = fail(code);
        return builder.msg(msg).body(data);
    }

    public static Msg error(Object data) {
        BodyBuilder builder = fail();
        return builder.body(data);
    }

    public static Msg error(String msg) {
        BodyBuilder builder = fail();
        return builder.msg(msg).build();
    }

    public static Msg error(String msg, Object data) {
        BodyBuilder builder = fail();
        return builder.msg(msg).body(data);
    }

    public static Msg error(int code, String msg, Object data) {
        BodyBuilder builder = fail(code);
        return builder.msg(msg).body(data);
    }
    /* 快捷输出 end */

    private static class DefaultBuilder implements BodyBuilder {
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
        public BodyBuilder msg(String message) {
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

        BodyBuilder msg(String message);

        Msg build();
    }

    @Override
    public String toString() {
        return "Msg{" +
                "success=" + this.success +
                ", data=" + this.data +
                ", message='" + this.message + '\'' +
                ", code=" + this.code +
                '}';
    }

    public static void main(String[] args) {
        PrintStream out = System.out;
        /*
         * 返回正确的消息
         */
        out.println(Msg.ok("消息"));
        out.println(Msg.ok("消息", new Object()));
        out.println(Msg.ok(200, "消息", new Object()));
        out.println(Msg.ok(new Object()));
        /*
         * 返回错误的消息 fail 与 error 实际上一样
         */
        // out.println(Msg.error());
        out.println(Msg.error("消息"));
        out.println(Msg.error("消息", new Object()));
        out.println(Msg.error(200, "消息", new Object()));
        out.println(Msg.error(new Object()));

        /*
         * 自定义消息体
         */
        out.println(new Msg(200, "消息", new Object()));
        Msg msg1 = Msg.status(true).msg("消息").body(new Object());
        out.println(msg1);
        Msg msg2 = Msg.status(true, 500).msg("消息").body(new Object());
        out.println(msg2);
        Msg msg3 = Msg.status(false).msg("消息").build();
        out.println(msg3);
        Msg msg4 = Msg.status(false).body(new Object());
        out.println(msg4);

        /*
            Msg{success=true, data=, message='消息', code=0}
            Msg{success=true, data=java.lang.Object@2b193f2d, message='消息', code=0}
            Msg{success=true, data=java.lang.Object@355da254, message='消息', code=200}
            Msg{success=true, data=java.lang.Object@4dc63996, message='null', code=0}
            Msg{success=true, data=, message='消息', code=0}
            Msg{success=false, data=java.lang.Object@d716361, message='消息', code=0}
            Msg{success=false, data=java.lang.Object@6ff3c5b5, message='消息', code=200}
            Msg{success=false, data=java.lang.Object@3764951d, message='null', code=0}
            Msg{success=true, data=java.lang.Object@4b1210ee, message='消息', code=200}
            Msg{success=true, data=java.lang.Object@4d7e1886, message='消息', code=0}
            Msg{success=true, data=java.lang.Object@3cd1a2f1, message='消息', code=500}
            Msg{success=true, data=, message='消息', code=0}
            Msg{success=false, data=java.lang.Object@2f0e140b, message='null', code=0}
         */
    }
}