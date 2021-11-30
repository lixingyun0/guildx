package com.xingyun.enums;

/**
 * @author h
 * 账单分类 0.提现 1.充值 2.闪兑 3 收益 4 理财本金回退 5 理财产品支付
 */
public enum WalletDetailTypeEnum {

    WITHDRAW(0,"提现"),
    RECHARGE(1,"充值"),
    FLASH(2,"闪兑"),
    INCOME(3,"收益"),
    FINANCIAL_CAPITAL_BACK(4,"理财本金回退"),
    FINANCIAL_PAY(5,"理财产品支付"),
    POWER_EXCHANGE(6, "购买算力币"),
    STAKE(7, "质押"),
    CANCEL(8, "取消质押")
    ;


    WalletDetailTypeEnum(int type, String desc) {
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
