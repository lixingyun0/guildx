package com.xingyun.enums;

public enum BonusTypeEnum {

    Check_In("Check In"),
    Daily_Deposit_reward("Daily Deposit reward"),
    Daily_Trade_reward("Daily Trade reward"),
    Daily_Invite_reward("Daily Invite reward"),
    First_Time_of_Deposi("First Time of Deposi"),
    First_Time_of_Trade ("First Time of Trade "),
    First_Time_of_Invite("First Time of Invite"),

    ;

    private String typeName;

    BonusTypeEnum(String typeName){
        this.typeName= typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
