package com.xingyun.enums;

public enum AccountTypeEnum {

    RECHARGE_ACCOUNT(1,"充值帐户"),
    WITHDRAW_ACCOUNT(2,"提现帐户"),
    ;

    AccountTypeEnum(int accountType, String desc) {
        this.accountType = accountType;
        this.desc = desc;
    }

    private Integer accountType;
    private String desc;

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
