package com.lottery.domain.model;

import com.common.util.IGlossary;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.model.playtype.*;

public enum LotteryCategoryEnum implements IGlossary {
    /**
     * 快三
     */
    KS("快三",  KsPlayTypeEnum.class),

    CQKS(LotteryCategoryEnum.KS,101,"重庆快三",YesOrNoEnum.NO),
    /**
     * 时时彩
     */
    SSC("时时彩",  SSCPlayTypeEnum.class),
    /**
     * PK10
     */
    PK10("PK10",  PK10TypeEnum.class),
    /**
     * 六合彩
     */
    LHC("六合彩",  LHCPlayTypeEnum.class),
    /**
     * 幸运28
     */
    XY28("幸运28",  XY28PlayTypeEnum.class),


    /**
     * 11选5
     */
//    SYX5("11选5",)
    ;


    LotteryCategoryEnum(String name, Class playType) {
        this.name = name;
        this.value = -1;
        this.playType = playType;
    }
    LotteryCategoryEnum(LotteryCategoryEnum parent, Integer value, String name, YesOrNoEnum privateLottery) {
        this.name = name;
        this.value = value;
        this.playType = playType;
    }
    private YesOrNoEnum privateLottery;

    private Class playType;
    /**
     * 名字
     */
    private String name;
    /**
     * 数据库存值
     */
    private Integer value;

    public YesOrNoEnum getPrivateLottery() {
        return privateLottery;
    }

    public Class getPlayType() {
        return playType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
