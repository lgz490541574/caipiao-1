package com.lottery.domain.util;

import com.common.util.IGlossary;

/**
 * 位数信息
 */
public enum WeiShuEnum implements IGlossary {
    /**
     * 时时彩万位
     */
    WAN_WEI("万位",1),
    QIAN_WEI("千位",2),
    BAI_WEI("百位",3),
    SHI_WEI("十位",4),
    GE_WEI("个位",5),

    WAN_QIAN("万千",6),
    WAN_BAI("万百",7),
    WAN_SHI("万十",8),
    WAN_GE("万个",9),
    QIAN_BAI("千百",10),
    QIAN_SHI("千十",11),
    QIAN_GE("千个",12),
    BAI_SHI("百十",13),
    BAI_GE("百个",14),
    SHI_GE("十个",15),
    ZONG_HE("总和",16),
    BAO_ZI("豹子",17),
    HZ_ZHI("和值",18),
    KU_DU("跨度",19),
    /**
     * pk10位数 冠军
     */
    GUAN_JUN("冠军",21),
    YA_JUN("亚军",22),
    JI_JUN("季军",23),
    DI_SI("第四",24),
    DI_WU("第五",25),
    DI_LIU("第六",26),
    DI_QI("第七",27),
    DI_BA("第八",28),
    DI_JIU("第九",29),
    DI_SHI("第十",30),

    QI_DIAN("起点",31),
    TI_ZI("梯子",32),
    ZHONG_DIAN("终点",33),
    ZHONG_TI("终点X梯子",34),

    ;

    WeiShuEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static WeiShuEnum findByValue(Integer value){
        WeiShuEnum[] weiShuEnums = WeiShuEnum.values();
        for(WeiShuEnum weiShuEnum : weiShuEnums){
            if(weiShuEnum.getValue()==value.intValue()){
                return weiShuEnum;
            }
        }
        return null;
    }

    private String name;

    private Integer value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
