package com.xingyun.common;


import com.xingyun.enums.ResultCodeEnum;

/**
 * ajax 结果返回
 *
 * @author XLY
 *
 **/
public class Result<T> {

    /**
     * 返回的 code 码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 返回的结果
     */
    private T data;

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public static<T> Result<T> forSuccess(ResultCodeEnum resultEnum, T data){
        return new Result<>(resultEnum.getCode(), resultEnum.getDesc(), data);
    }

    public static<T> Result<T> forSuccess(T data, String msg){
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(),msg,data);
    }

    public static<T> Result<T> forSuccess(T data){
        return forSuccess(data,ResultCodeEnum.SUCCESS.getDesc());
    }

    public static Result forSuccess(){
        return forSuccess("");
    }

    public static<T> Result<T> forFail(int code,String msg ,T data){
        return new Result<>(code,msg,data);
    }

    public static Result forFail(int code,String msg){
        return forFail(code,msg,"");
    }


    public static Result forFail(String msg){
        return forFail(ResultCodeEnum.COMMON_ERROR.getCode(),msg);
    }

    public static Result forFail(ResultCodeEnum resultEnum){
        return forFail(resultEnum.getCode(),resultEnum.getDesc());
    }

    public static<T> Result<T> forFail(ResultCodeEnum resultEnum, T data){
        return forFail(resultEnum.getCode(),resultEnum.getDesc(), data);
    }

    public static Result forFail(){
        return forFail(ResultCodeEnum.COMMON_ERROR.getDesc());
    }

    public  Boolean isSuccess(){
        return ResultCodeEnum.SUCCESS.getCode().equals(code);
    }

}
