package com.lottery.domain.util;


public class AbstractPrizeCheck implements IPrizeCheck {
    /**
     * 是否中奖
     * @param issueResult
     * @param code
     * @return
     */
    @Override
    public boolean isPrize(String issueResult, String code){
        throw new RuntimeException("not instance");
    }
    /**
     * 是否中奖
     * @param issueResult
     * @param code
     * @return
     */
    @Override
    public boolean isPrize(String issueResult, String code, String weiShu){
        throw new RuntimeException("not instance");
    }

    /**
     * 中奖类型
     * @param issueResult
     * @param code
     * @return
     */
    @Override
    public String prizeType(String issueResult, String code){
        return null;
    }
}
