/**
 * peace convince nominee
 * congress useless tobacco
 * dilemma vague task
 * apple error confluence
 * toss nation nose
 */
package com.xingyun.common;


/**
 * 业务异常类
 *
 * @author 最爱吃小鱼
 */
public class BusinessException extends RuntimeException {

    private String errCode;
    private String errMessage;


    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String message, Object... args) {
        super(String.format(message, args));
    }

    public BusinessException(String message, Throwable throwable){
        super(message,throwable);
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
