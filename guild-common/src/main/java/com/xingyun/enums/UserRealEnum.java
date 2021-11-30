package com.xingyun.enums;

/**
 * 用户实名认证状态
 */
public enum UserRealEnum {

    INIT(0, "未实名"), REAL(1, "已实名"), BACK(2, "实名驳回"), CHECK(3, "实名申请提交审核中");

    private final int code;

    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    UserRealEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
