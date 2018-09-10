package com.bc.pmpheep.general.bean;

public enum ProductTypeEnum {
    CLINICAL_ASSISTANT("临床助手",1L),
    MEDICINE_ASSISTANT("用药助手",2L),
    CHINESE_MEDICINE("中医助手",3L)
    ;
    private String name;
    private Long type;

    /**
     * 通过类型编号获取类型名称
     * @param type
     * @return
     */
    public static String getName(Long type){
        for (ProductTypeEnum productTypeEnum:ProductTypeEnum.values()) {
            if(productTypeEnum.getType().equals(type)){
                return productTypeEnum.getName();
            }
        }
        return null;
    }

    ProductTypeEnum(String name, Long type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
