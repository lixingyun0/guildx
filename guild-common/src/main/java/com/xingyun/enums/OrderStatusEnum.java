package com.xingyun.enums;

public enum OrderStatusEnum {

    PAYING(0,"待支付"),
    PAY(1,"已支付"),
    CONFIRM(2,"已交付"),
    ;
    //0 待支付 1已支付 2已交付

    OrderStatusEnum(int status, String desc) {
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
