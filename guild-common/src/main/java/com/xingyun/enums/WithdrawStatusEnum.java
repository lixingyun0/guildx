package com.xingyun.enums;

public enum WithdrawStatusEnum {

    CHECKING(0,"审核中"),
    SUCCESS(1,"通过"),
    DENY(2,"拒绝"),
    ;


    WithdrawStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private Integer status;
    private String desc;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
