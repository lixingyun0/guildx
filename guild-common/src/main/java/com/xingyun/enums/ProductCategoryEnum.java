package com.xingyun.enums;

import com.xingyun.common.BusinessException;

public enum ProductCategoryEnum {

    chia_cloud("mcslsb", "chia满存算力设备","storage"),
    chia_disk("ppyw", "chiaP盘业务","storage"),
    fil_cloud("ysl", "fil云算力","storage,packageDay,releaseDay"),
    fil_server("zjsb", "fil整机设备","storage"),
    swarm_node("jd", "swarm节点","bandwidth,bandwidthFee"),
    swarm_server("jdfwq", "swarm节点服务器","bandwidth,bandwidthFee,beeNum"),

    ;

    ProductCategoryEnum(String code, String productName,String checkFields) {
        this.code = code;
        this.productName = productName;
        this.checkFields = checkFields;
    }

    private String code;

    private String productName;

    private String checkFields;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCheckFields() {
        return checkFields;
    }

    public void setCheckFields(String checkFields) {
        this.checkFields = checkFields;
    }

    public static ProductCategoryEnum getEnumByCode(String code){
        for (ProductCategoryEnum value : values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        throw new BusinessException("产品类型不存在");
    }
}
