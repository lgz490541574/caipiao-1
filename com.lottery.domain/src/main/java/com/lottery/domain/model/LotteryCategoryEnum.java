package com.lottery.domain.model;

import com.common.util.IGlossary;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.model.playtype.*;
import com.lottery.domain.util.IssureRule;

public enum LotteryCategoryEnum implements IGlossary {
    /**
     * 快三
     */
    KS("快三", KsPlayTypeEnum.class),
    /**
     * 北京快三
     */
    KS_BJ(LotteryCategoryEnum.KS, 301, "北京快三", YesOrNoEnum.NO,new IssureRule(44,9,21,20)),
    /**
     * 江苏快三
     */
    KS_JS(LotteryCategoryEnum.KS, 302, "江苏快三", YesOrNoEnum.NO,new IssureRule(41,8,49,20)),
    /**
     * 上海快三
     */
    KS_SH(LotteryCategoryEnum.KS, 303, "上海快三", YesOrNoEnum.NO,new IssureRule(41,8,58,20)),
    /**
     * 安徽快三
     */
    KS_AH(LotteryCategoryEnum.KS, 304, "安徽快三", YesOrNoEnum.NO,new IssureRule(40,8,59,20)),
    /**
     * 贵州快三
     */
    KS_GZ(LotteryCategoryEnum.KS, 305, "贵州快三", YesOrNoEnum.NO,new IssureRule(39,9,30,20)),
    /**
     * 广西快三
     */
    KS_GX(LotteryCategoryEnum.KS, 306, "广西快三", YesOrNoEnum.NO,new IssureRule(40,9,30,20)),
    /**
     * 河北快三
     */
    KS_HE(LotteryCategoryEnum.KS, 307, "河北快三", YesOrNoEnum.NO,new IssureRule(41,8,50,20)),
    /**
     * 吉林快三
     */
    KS_JL(LotteryCategoryEnum.KS, 308, "吉林快三", YesOrNoEnum.NO,new IssureRule(40,8,39,20)),
    /**
     * 湖北快三
     */
    KS_HB(LotteryCategoryEnum.KS, 309, "湖北快三", YesOrNoEnum.NO,new IssureRule(39,9,20,20)),
    /**
     * 九州快三
     */
    KS_JZ(LotteryCategoryEnum.KS, 310, "九州快三", YesOrNoEnum.YES,new IssureRule(800,9,15,1)),
    /**
     * 五分快三
     */
    KS_WF(LotteryCategoryEnum.KS, 311, "五分快三", YesOrNoEnum.YES,new IssureRule(180,8,30,5)),

    /**
     * 时时彩
     */
    SSC("时时彩", SSCPlayTypeEnum.class),
    /**
     * 重庆时时彩
     */
    SSC_CQ(LotteryCategoryEnum.SSC, 401, "重庆时时彩", YesOrNoEnum.NO,new IssureRule(59,0,30,20)),
    /**
     * 新疆时时彩
     */
    SSC_XJ(LotteryCategoryEnum.SSC, 402, "新疆时时彩", YesOrNoEnum.NO,new IssureRule(48,10,19,20)),
    /**
     * 天津时时彩
     */
    SSC_TJ(LotteryCategoryEnum.SSC, 403, "天津时时彩", YesOrNoEnum.NO,new IssureRule(42,9,20,20)),
    /**
     * 九州时时彩
     */
    SSC_JZ(LotteryCategoryEnum.SSC, 404, "九州时时彩", YesOrNoEnum.NO,new IssureRule(800,9,20,1)),
    /**
     * 五分时时彩
     */
    SSC_WF(LotteryCategoryEnum.SSC, 405, "五分时时彩", YesOrNoEnum.NO,new IssureRule(160,9,20,5)),
    /**
     * PK10
     */
    PK10("PK10", PK10TypeEnum.class),
    /**
     * 北京PK10
     */
    PK10_BJ(LotteryCategoryEnum.PK10, 501, "北京PK10", YesOrNoEnum.NO,new IssureRule(160,9,20,5)),
    /**
     * 九州PK10
     */
    PK10_JZ(LotteryCategoryEnum.PK10, 502, "九州PK10", YesOrNoEnum.YES,new IssureRule(800,18,10,1)),
    /**
     * 五分PK10
     */
    PK10_WF(LotteryCategoryEnum.PK10, 503, "五分PK10", YesOrNoEnum.YES,new IssureRule(160,9,18,5)),
    /**
     * 六合彩
     */
    LHC("六合彩", LHCPlayTypeEnum.class),
    /**
     * 香港六合彩
     */
    LHC_XG(LHC, 601, "香港六合彩", YesOrNoEnum.NO,null),

    /**
     * 九州六合彩
     */
    LHC_JZ(LHC, 602, "九州六合彩", YesOrNoEnum.YES,new IssureRule(800,9,18,1)),
    /**
     * 五分六合彩
     */
    LHC_WF(LHC, 603, "五分六合彩", YesOrNoEnum.YES,new IssureRule(160,9,15,5)),

    /**
     * 幸运飞艇
     */
    XY28("幸运飞艇", XY28PlayTypeEnum.class),
    /**
     * 幸运飞艇
     */
    XY28_GW(XY28, 701, "幸运飞艇", YesOrNoEnum.NO,new IssureRule(660,8,00,2)),
    /**
     * 九州飞艇
     */
    XY28_JZ(XY28, 702, "九州飞艇", YesOrNoEnum.YES,new IssureRule(660,8,20,2)),

    /**
     * 11选5
     */
    SYX5("11选5",SYXWTypeEnum.class),
    /**
     * 上海11选5
     */
    SYX5_SH(SYX5,801,"上海11选五",YesOrNoEnum.NO,new IssureRule(45,9,21,20)),

    /**
     * 广东11选五
     */
    SYX5_GD(SYX5,802,"广东11选五",YesOrNoEnum.NO,new IssureRule(42,9,31,20)),
    /**
     * 山东11选五
     */
    SYX5_SD(SYX5,803,"山东11选五",YesOrNoEnum.NO,new IssureRule(43,9,2,20)),
    /**
     * 江西11选五
     */
    SYX5_JX(SYX5,804,"江西11选五",YesOrNoEnum.NO,new IssureRule(42,9,31,20)),
    /**
     * 九州11选五
     */
    SYX5_JZ(SYX5,805,"九州11选五",YesOrNoEnum.NO,new IssureRule(800,9,31,1)),
    /**
     * 快乐11选五
     */
    SYX5_KL(SYX5,806,"快乐11选五",YesOrNoEnum.YES,new IssureRule(160,9,31,5)),

    ;

    LotteryCategoryEnum parent;
    LotteryCategoryEnum(String name, Class playType) {
        this.name = name;
        this.value = -1;
        this.playType = playType;
    }

    LotteryCategoryEnum(LotteryCategoryEnum parent, Integer value, String name, YesOrNoEnum privatelottery, IssureRule rule) {
        this.parent=parent;
        this.name = name;
        this.value = value;
        this.privateLottery = privatelottery;
        this.rule=rule;
    }
    private IssureRule rule;
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

    public IssureRule getRule() {
        return rule;
    }

    public LotteryCategoryEnum getParent() {
        return parent;
    }

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
