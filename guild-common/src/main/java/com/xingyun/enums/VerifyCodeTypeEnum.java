package com.xingyun.enums;

public enum VerifyCodeTypeEnum {

    REGISTER(1,"注册"),
    LOGIN(2,"登录"),
    INFO(3,"修改信息"),
    PAY(4, "转账"),
    ADD(5, "添加地址"),
    ;


    VerifyCodeTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private Integer type;
    private String desc;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
