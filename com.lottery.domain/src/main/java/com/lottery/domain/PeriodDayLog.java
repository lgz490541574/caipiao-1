package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

/**
 *
 */
@Data
@CompoundIndexes(
        {
                @CompoundIndex(name = "uniqueIndex", def = "{'lotteryCategory':1,day:1}", unique = true)
        })
public class PeriodDayLog extends AbstractBaseEntity {
    /**
     * 彩种
     */
    private Integer lotteryCategory;
    /**
     * 日期
     */
    private String day;

    /**
     * 异常状态
     * 1 是 异常
     * 2 否 正常
     */
    private Integer errorStatus;
}
