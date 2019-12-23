package com.lottery.domain.util;

import com.common.util.IGlossary;
import com.lottery.domain.model.playtype.PK10TypeEnum;
import com.lottery.domain.model.playtype.SSCPlayTypeEnum;

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
    GUAN_JUN("冠军",0,9),
    YA_JUN("亚军",1,8),
    JI_JUN("季军",2,7),
    DI_SI("第四",3,6),
    DI_WU("第五",4,5),
    DI_LIU("第六",5),
    DI_QI("第七",6),
    DI_BA("第八",7),
    DI_JIU("第九",8),
    DI_SHI("第十",9),

    PK10(
            WeiShuEnum.GUAN_JUN,
            WeiShuEnum.YA_JUN,
            JI_JUN,DI_SI,DI_WU,DI_LIU,DI_QI,DI_BA,DI_JIU,DI_SHI
    ),



    QI_DIAN("起点",31),
    TI_ZI("梯子",32),
    ZHONG_DIAN("终点",33),
    ZHONG_TI("终点X梯子",34),

    ;

    WeiShuEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    WeiShuEnum(String name, Integer value,Integer vsWei) {
        this.name = name;
        this.value = value;
    }


    WeiShuEnum(WeiShuEnum... items) {
        this.items=items;
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

    WeiShuEnum[] items;
    Integer vsWei;

    public Integer getVsWei() {
        return vsWei;
    }

    public WeiShuEnum[] getItems() {
        return items;
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
