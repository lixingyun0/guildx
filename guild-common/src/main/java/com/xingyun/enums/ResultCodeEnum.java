package com.xingyun.enums;

public enum ResultCodeEnum {
    /*请求成功*/
    SUCCESS(200,"请求成功"),
    /* 通用的错误码，只返回 MSG 信息时，返回该 CODE */
    COMMON_ERROR(500,"请求错误"),
    /* 请求参数缺失 */
    MISSING_SERVLET_REQUEST_PARAMETER(999,"请求参数缺失"),
    /* 无效的参数 */
    INVALID_PARAMETER(500,"无效的参数"),
    /* 账号或密码错误 */
    ACCOUNT_OR_PASSWORD_WRONG(500,"账号或密码错误"),
    /* 手机号码已被注册 */
    VERIFICATION_CODE_IS_INCORRECT_OR_EXPIRED(500, "验证码错误或已过期"),
    /* 数据不存在 */
    OBJECT_IS_NULL(500, "数据不存在"),
    /* 参数类型不匹配 */
    TYPE_MISMATCH(999, "参数类型不匹配"),
    RESTRICT_LOGIN(500, "连续输错密码5次，限制本账号5分钟内无法尝试"),
    /* 请求方法错误 */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(999, "请求方法错误"),
    /* feign 调用失败 */
    FEIGN_ERROR(999, "远程调用失败"),
    /* 系统错误 */
    SYSTEM_ERROR(999, "系统错误"),
    /* 系统繁忙 */
    SYSTEM_BUSY(999, "系统繁忙，请稍后重试"),
    /*需要登录*/
    NEED_LOGIN(501, "需要登录"),
    /* 无效的权限，拒绝访问 */
    ACCESS_DENIED(502, "访问受限"),
    /* 需要注册 */
    NEED_REGISTER(504, "需要注册"),
    /* 账号被禁用 */
    ACCOUNT_DISABLED(503, "账号被禁用"),
    /* 需要实名认证 */
    NEED_REAL_NAME_VERIFY(505, "需要实名认证"),
    /*登录冲突*/
    LOGIN_CONFLICT(506, "您的帐号在其他地方登录，请重新登录"),
    /* 手机号码已被注册 */
    USERNAME_HAS_BEEN_REGISTERED(507, "账号已经注册，请直接登录"),
    /* 账号被禁用 */
    SHOP_DISABLED(508, "商铺被禁用"),

    TRY_AGAIN_LATER(500, "请稍后重试"),

    NEED_BID_AGREEMENT(600, "需要缴纳保证金"),
    /* 一键登录获取手机号码失败 */
    GET_PHONE_BLOCKED(500, "获取手机号码失败"),

    FREQUENCY_LIMIT(500, "您的操作过于频繁"),
    NOT_ALLOW_REVIEW(509, "不允许回复回复回复的回复"),

    FIRST_CONFIG_TEACHING_PAT(601, "请先保存教程付费配置"),
    PAY_PASSWORD_ERROR(602, "交易密码错误"),
    PASSWORD_ERROR(603, "交易密码错误"),
    STOCK_NOT_ENOUGH(604, "库存不足"),
    BALANCE_NOT_ENOUGH(605, "余额不足"),
    USDT_NOT_ENOUGH(606, "账户USDT小于该笔交易手续费，请先充值"),

    WALLET_SETTING_NULL(607, "代币提现配置未开启"),
    ORDER_FAIL(608, "下单失败"),



    ;


    ResultCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
