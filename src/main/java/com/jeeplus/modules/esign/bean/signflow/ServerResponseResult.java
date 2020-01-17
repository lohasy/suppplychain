package com.jeeplus.modules.esign.bean.signflow;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ServerResponseResult<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServerResponseResult(int status){
        this.status = status;
    }
    private ServerResponseResult(int status,T data){
        this.status = status;
        this.data = data;
    }

    private ServerResponseResult(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponseResult(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }
    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }


    public static <T> ServerResponseResult<T> createBySuccess(){
        return new ServerResponseResult<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponseResult<T> createBySuccessMessage(String msg){
        return new ServerResponseResult<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponseResult<T> createBySuccess(T data){
        return new ServerResponseResult<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponseResult<T> createBySuccess(String msg, T data){
        return new ServerResponseResult<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }


    public static <T> ServerResponseResult<T> createByError(){
        return new ServerResponseResult<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }


    public static <T> ServerResponseResult<T> createByErrorMessage(String errorMessage){
        return new ServerResponseResult<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerResponseResult<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponseResult<T>(errorCode,errorMessage);
    }
}
