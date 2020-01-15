package com.jeeplus.modules.esign.bean;


import java.io.Serializable;

/**
 */
public class ServerResponse<T> implements Serializable {

    private int code; //返回code

    private String msg; //描述信息

    private Object data; //返回内容

    public static ServerResponse success(Object data) {
        return ServerResponse(0, "成功", data);
    }

    public static ServerResponse success(Object data, String msg) {
        return ServerResponse(0, msg, data);
    }

    public static ServerResponse success( String msg) {
        return ServerResponse(0, msg, null);
    }

    public static ServerResponse fail(int code, String msg) {
        return ServerResponse(code, msg, null);
    }

    public static ServerResponse fail(String msg) {
        return ServerResponse(-1, msg, null);
    }

    public static ServerResponse fail(int code, String msg, Object data) {
        return ServerResponse(code, msg, data);
    }

    private static ServerResponse ServerResponse(int code, String msg, Object data) {
        ServerResponse resultData = new ServerResponse();
        resultData.setCode(code);
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
