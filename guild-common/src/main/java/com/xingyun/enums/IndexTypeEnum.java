package com.xingyun.enums;

public enum IndexTypeEnum {

    BTC("BTC","btc"),
    ETH("ETH","eth"),
    GOLD("GOLD","gold"),
    SILVER("SILVER","silver"),
    ;

    IndexTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
