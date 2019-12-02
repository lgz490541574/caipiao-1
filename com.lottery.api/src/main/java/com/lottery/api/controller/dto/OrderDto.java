package com.lottery.api.controller.dto;

import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.model.LotteryCategoryEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto implements Serializable {
    /**
     * 彩种
     */
    private LotteryCategoryEnum type;
    /**
     * 玩法
     */
    private String playType;
    /**
     * 期号
     */
    private String periodCode;

    /**
     * 下单号码
     */
    private List<String[]> codeList;
    /**
     * 部投
     */
    private Integer times;
    /**
     * 下单金额
     */
    private BigDecimal orderMoney;
    /**
     * 追号标记
     */
    private String chaseMark;
    /**
     * 中奖即停
     */
    private YesOrNoEnum prizeStop;
}
