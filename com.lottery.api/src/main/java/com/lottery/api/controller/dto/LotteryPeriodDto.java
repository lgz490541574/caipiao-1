package com.lottery.api.controller.dto;

import com.lottery.domain.model.LotteryCategoryEnum;
import lombok.Data;

/**
 * 彩期dto
 */
@Data
public class LotteryPeriodDto {
    private LotteryCategoryEnum type;
    private String code;
}
