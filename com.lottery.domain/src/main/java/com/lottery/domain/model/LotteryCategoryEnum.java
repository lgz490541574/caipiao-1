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
     * PC蛋蛋幸运28
     */
    XY28_GW(KS, 300, "PC蛋蛋幸运28", YesOrNoEnum.NO, new IssureRule(179, 9, 5, 0, 5)),
    /**
     * 北京快三
     */
    KS_BJ(LotteryCategoryEnum.KS, 301, "北京快三", YesOrNoEnum.NO, new IssureRule(44, 9, 22, 45, 20)),
    /**
     * 江苏快三
     */
    KS_JS(LotteryCategoryEnum.KS, 302, "江苏快三", YesOrNoEnum.NO, new IssureRule(41, 8, 50, 30, 20, "%03d")),
    /**
     * 上海快三
     */
    KS_SH(LotteryCategoryEnum.KS, 303, "上海快三", YesOrNoEnum.NO, new IssureRule(41, 8, 58, 0, 20)),
    /**
     * 安徽快三
     */
    KS_AH(LotteryCategoryEnum.KS, 304, "安徽快三", YesOrNoEnum.NO, new IssureRule(40, 8, 50, 50, 25, "%03d")),
    /**
     * 贵州快三
     */
    KS_GZ(LotteryCategoryEnum.KS, 305, "贵州快三", YesOrNoEnum.NO, new IssureRule(39, 9, 37, 0, 20)),
    /**
     * 广西快三
     */
    KS_GX(LotteryCategoryEnum.KS, 306, "广西快三", YesOrNoEnum.NO, new IssureRule(40, 9, 30, 45, 20)),
    /**
     * 河北快三
     */
    KS_HE(LotteryCategoryEnum.KS, 307, "河北快三", YesOrNoEnum.NO, new IssureRule(41, 8, 50, 0, 20, "%03d")),
    /**
     * 吉林快三
     */
    KS_JL(LotteryCategoryEnum.KS, 308, "吉林快三", YesOrNoEnum.NO, new IssureRule(40, 8, 40, 50, 20, "%03d")),
    /**
     * 湖北快三
     */
    KS_HB(LotteryCategoryEnum.KS, 309, "湖北快三", YesOrNoEnum.NO, new IssureRule(39, 9, 20, 30, 20, "%03d")),
    /**
     * 九州快三
     */
    KS_JZ(LotteryCategoryEnum.KS, 310, "九州快三", YesOrNoEnum.YES, new IssureRule(800, 9, 15, 0, 1)),
    /**
     * 五分快三
     */
    KS_WF(LotteryCategoryEnum.KS, 311, "五分快三", YesOrNoEnum.YES, new IssureRule(180, 8, 30, 0, 5, "%03d")),

    /**
     * 时时彩
     */
    SSC("时时彩", SSCPlayTypeEnum.class),


    /**
     * 重庆欢乐生肖
     */
    SSC_CQ(LotteryCategoryEnum.SSC, 401, "重庆欢乐生肖", YesOrNoEnum.NO, new IssureRule(59, 0, 30, 20, 20, "%03d", new String[]{})),
    /**
     * 新疆时时彩
     */
    SSC_XJ(LotteryCategoryEnum.SSC, 402, "新疆时时彩", YesOrNoEnum.NO, new IssureRule(48, 10, 20, 30, 20)),
    /**
     * 天津时时彩
     */
    SSC_TJ(LotteryCategoryEnum.SSC, 403, "天津时时彩", YesOrNoEnum.NO, new IssureRule(42, 9, 20, 30, 20)),
    /**
     * 九州时时彩
     */
    SSC_JZ(LotteryCategoryEnum.SSC, 404, "九州时时彩", YesOrNoEnum.YES, new IssureRule(800, 9, 20, 0, 1)),
    /**
     * 五分时时彩
     */
    SSC_WF(LotteryCategoryEnum.SSC, 405, "五分时时彩", YesOrNoEnum.YES, new IssureRule(160, 9, 20, 0, 5)),
    /**
     * PK10
     */
    PK10("PK10", PK10TypeEnum.class),
    /**
     * 北京PK10
     */
    PK10_BJ(LotteryCategoryEnum.PK10, 501, "北京PK10", YesOrNoEnum.NO, new IssureRule(44, 9, 30, 40, 20)),

    /**
     * 九州PK10
     */
    PK10_JZ(LotteryCategoryEnum.PK10, 502, "九州PK10", YesOrNoEnum.YES, new IssureRule(800, 9, 10, 0, 1)),
    /**
     * 五分PK10
     */
    PK10_WF(LotteryCategoryEnum.PK10, 503, "五分PK10", YesOrNoEnum.YES, new IssureRule(160, 9, 18, 0, 5)),

    /**
     * 幸运飞艇
     */
    PK10_XYFT(LotteryCategoryEnum.PK10, 504, "幸运飞艇", YesOrNoEnum.NO, new IssureRule(180, 13, 9, 35, 5)),

    /**
     * 六合彩
     */
    LHC("六合彩", LHCPlayTypeEnum.class),

    /**
     * 香港六合彩
     */
    LHC_XG(LHC, 601, "香港六合彩", YesOrNoEnum.NO, null),

    /**
     * 九州六合彩
     */
    LHC_JZ(LHC, 602, "九州六合彩", YesOrNoEnum.YES, new IssureRule(800, 9, 18, 0, 1)),

    /**
     * 五分六合彩
     */
    LHC_WF(LHC, 603, "五分六合彩", YesOrNoEnum.YES, new IssureRule(160, 9, 15, 0, 5)),


    /**
     * 11选5
     */
    SYX5("11选5", SYXWTypeEnum.class),

    /**
     * 上海11选5
     */
    SYX5_SH(SYX5, 801, "上海11选五", YesOrNoEnum.NO, new IssureRule(45, 9, 20, 50, 20)),

    /**
     * 广东11选五
     */
    SYX5_GD(SYX5, 802, "广东11选五", YesOrNoEnum.NO, new IssureRule(42, 9, 30, 30, 20)),

    /**
     * 安徽11选五
     */
    SYX5_AH(SYX5, 803, "安徽11选五", YesOrNoEnum.NO, new IssureRule(40, 9, 0, 30, 20)),

    /**
     * 江西11选五
     */
    SYX5_JX(SYX5, 804, "江西11选五", YesOrNoEnum.NO, new IssureRule(42, 9, 30, 30, 20)),

    /**
     * 九州11选五
     */
    SYX5_JZ(SYX5, 805, "九州11选五", YesOrNoEnum.NO, new IssureRule(800, 9, 0, 0, 1)),

    /**
     * 快乐11选五
     */
    SYX5_KL(SYX5, 806, "快乐11选五", YesOrNoEnum.YES, new IssureRule(160, 9, 0, 0, 5)),
    ;


    LotteryCategoryEnum parent;
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

    LotteryCategoryEnum(String name, Class playType) {
        this.name = name;
        this.value = -1;
        this.playType = playType;
    }

    LotteryCategoryEnum(LotteryCategoryEnum parent, Integer value, String name, YesOrNoEnum privatelottery, IssureRule rule) {
        this.parent = parent;
        this.name = name;
        this.value = value;
        this.privateLottery = privatelottery;
        this.rule = rule;
    }

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
