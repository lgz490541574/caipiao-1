package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

/**
 * 配置信息
 */
@Data
@CompoundIndexes({
        @CompoundIndex(name = "pin_unique", def = "{'proxyId':1,'lotteryType':1,'key':1}", unique = true)
})
public class Config extends AbstractBaseEntity {
    /**
     * 代理商
     */
    private String proxyId;
    /**
     * 玩法
     */
    private String key;
    /**
     * 赔率
     */
    private String content;
    /**
     * 彩种
     */
    private String lotteryType;
    /**
     * 号码限制
     */
    private Integer codeLimit;

    /**
     * 备注
     */
    private String remark;

}
