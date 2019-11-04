package com.lottery.domain.util;

public interface IPrizeCheck {
    /**
     * 是否中奖
     * @param issueResult
     * @param code
     * @return
     */
    public boolean isPrize(String issueResult,String code);
    /**
     * 是否中奖
     * @param issueResult
     * @param code
     * @return
     */
    public boolean isPrize(String issueResult,String code,String weiShu);

    /**
     * 中奖类型
     * @param issueResult
     * @param code
     * @return
     */
    public String prizeType(String issueResult, String code);
}
