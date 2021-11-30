package com.xingyun.enums;

public enum ProductStatusEnum {

    SELL(0,"上架"),
    OFF(1,"下架"),
    ;


    ProductStatusEnum(int status, String desc) {
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
