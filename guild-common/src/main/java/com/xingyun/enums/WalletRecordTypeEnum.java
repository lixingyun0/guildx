package com.xingyun.enums;

public enum WalletRecordTypeEnum {

    RECHARGE(0,"充值"),
    WITHDRAW(1,"提币"),
    INCOME(2,"收益"),
    CONSUME(3,"消费"),
    FEE(4,"手续费"),
    WITHDRAW_CNY(5,"提现"),
    ;


    WalletRecordTypeEnum(int type, String desc) {
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
