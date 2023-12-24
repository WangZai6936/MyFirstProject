package com.hxh.domain;

public class ResultEntity {
    private String code;
    private String message;
    private Object object;

    public ResultEntity(String code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    public static ResultEntity SUCCESS(){
        return new ResultEntity("200","请求成功",null);
    }
    public static ResultEntity SUCCESS(Object object){
        return new ResultEntity("200","请求成功",object);
    }
}
