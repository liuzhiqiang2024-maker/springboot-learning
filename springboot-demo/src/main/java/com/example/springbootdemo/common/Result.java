package com.example.springbootdemo.common;

public class Result <T>{
    private T data;
    private String message;
    private int code;
    public Result(int code, String message,T data) {
        this.data = data;
        this.message = message;
        this.code = code;
    }
    //成功，带数据
    public static<T>Result<T> success(T data){
        return new Result<>(200,"成功",data);
    }
    //成功，不带数据
    public static<T>Result<T> success(){
        return new Result<>(200,"成功",null);
    }
    //失败
    public static<T>Result<T> error(int code,String message){
        return new Result<>(code,message,null);
    }
    public int getCode() {return code;}
    public void setCode(int code) {this.code = code;}
    public T getData() {return data;}
    public void setData(T data) {this.data = data;}
}
