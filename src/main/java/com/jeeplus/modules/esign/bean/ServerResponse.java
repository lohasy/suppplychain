package com.jeeplus.modules.esign.bean;


import java.io.Serializable;

/**
 * restful通用返回对象
 * @param <T>
 */
public class ServerResponse<T> implements Serializable {


    private static final long serialVersionUID = 1011570200526843614L;
    /**
     * 返回code
     */
    private int code;
    /**
     * 描述信息
     */
    private String msg;
    /**
     * 返回内容
     */
    private T data;

    public static <T> ServerResponse<T> success(T data) {
        return serverResponse(0, "成功", data);
    }

    public static <T> ServerResponse<T> success(T data, String msg) {
        return serverResponse(0, msg, data);
    }

    public static <T> ServerResponse<T> success( String msg) {
        return serverResponse(0, msg, null);
    }

    public static <T>  ServerResponse<T> fail(int code, String msg) {
        return serverResponse(code, msg, null);
    }

    public static <T> ServerResponse<T> fail(String msg) {
        return serverResponse(-1, msg, null);
    }

    public static <T> ServerResponse<T> fail(int code, String msg, T data) {
        return serverResponse(code, msg, data);
    }

    private static <T> ServerResponse<T> serverResponse(int code, String msg, T data) {
        ServerResponse<T> resultData = new ServerResponse<>();
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
