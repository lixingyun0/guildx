package com.xingyun.enums;

public enum AccountRecordTypeEnum {

    /**
     * 交易类型 1充值 2充值奖励 3任务奖励 4下单 5获胜 6下级分佣 7提现
     */
    RECHARGE(1,"充值"),
    RECHARGE_REWARD(2,"充值奖励"),
    TASK_REWARD(3,"任务奖励"),
    PAY_ORDER(4,"下单"),
    ORDER_SUCCESS(5,"获胜"),
    SHARE_REWARD(6,"下级分佣"),
    WITHDRAW(7,"提现"),


    ;

    AccountRecordTypeEnum(int type, String desc) {
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
