package com.lottery.domain.model.playtype;

import com.common.util.IGlossary;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.PlayOrderSplitUtil;
import com.lottery.domain.util.*;

import java.util.*;

public enum SYXWTypeEnum implements IGlossary, IPlayType {

    /**
     * 定位胆
     */
    DWD("定位胆",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code, String weiShu) {
                    String[] resultCodes = issueResult.split(",");
                    if (code.equals(resultCodes[Integer.parseInt(weiShu) - WeiShuEnum.GUAN_JUN.getValue()])) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode, String weiShu) {
                    TicketRule[] ticketRules = DWD.rules;
                    for (String item : ticketRules[Integer.parseInt(weiShu) - WeiShuEnum.GUAN_JUN.getValue()].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String... codes) -> {
                return PlayOrderSplitUtil.getPk10DwdTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule("第一位", CodeEnum.SYXW_NUMBER.getCodes()),
                    new TicketRule("第二位", CodeEnum.SYXW_NUMBER.getCodes()),
                    new TicketRule("第三位", CodeEnum.SYXW_NUMBER.getCodes()),
                    new TicketRule("第四位", CodeEnum.SYXW_NUMBER.getCodes()),
                    new TicketRule("第五位", CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 不定胆-前三位
     */
    BDD("不定胆-前三位",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    for (int i = 0; i < 3; i++) {
                        if (!resultCodes[0].equals(code)) {
                            return false;
                        }
                    }
                    return true;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = BDD.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String... codes) -> {
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 任选-复式-一中一
     */
    RX_YZY("任选-复式-一中一",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    for (int i = 0; i < resultCodes.length; i++) {
                        if (!resultCodes[i].equals(code)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = RX_YZY.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String... codes) -> {
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 任选-复式-二中二
     */
    RX_EZE("任选-复式-二中二",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codeItem = code.split(",");
                    int indexCount = 0;

                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 2) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = RX_EZE.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes, 2);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 任选-复式-三中三
     */
    RX_TZT("任选-复式-三中三",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codeItem = code.split(",");
                    int indexCount = 0;

                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 3) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = RX_TZT.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes, 3);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 任选-复式-四中四
     */
    RX_FZF("任选-复式-四中四",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codeItem = code.split(",");
                    int indexCount = 0;

                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 4) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = RX_FZF.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes, 4);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 任选-复式-五中五
     */
    RX_WZW("任选-复式-五中五",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codeItem = code.split(",");
                    int indexCount = 0;

                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 5) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = RX_WZW.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes, 5);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 任选-六中五
     */
    RX_LZW("任选-复式-六中五",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codeItem = code.split(",");
                    int indexCount = 0;

                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 5) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = RX_LZW.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes, 6);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 任选-复式-七中五
     */
    RX_QZW("任选-复式-七中五",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codeItem = code.split(",");
                    int indexCount = 0;

                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 5) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = RX_QZW.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes, 7);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 任选-复式-八中五
     */
    RX_BZW("任选-复式-八中五",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codeItem = code.split(",");
                    int indexCount = 0;

                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 5) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = RX_BZW.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes, 8);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 二码-前二直选-复式
     */
    EM_ZHI_FS("二码-前二直选-复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = Arrays.copyOfRange(issueResult.split(","), 0, 2);
                    String[] codeItem = code.split(",");
                    if (codeItem[0].equals(resultCodes[0]) && codeItem[1].equals(resultCodes[1])) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = EM_ZHI_FS.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 二码-前二组选-复式
     */
    EM_ZU_FS("二码-前二组选-复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = Arrays.copyOfRange(issueResult.split(","), 0, 2);
                    String[] codeItem = code.split(",");
                    int indexCount = 0;
                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 2) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = EM_ZU_FS.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 三码-前三直选-复式
     */
    SM_ZHI_FS("三码-前三直选-复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = Arrays.copyOfRange(issueResult.split(","), 0, 3);
                    String[] codeItem = code.split(",");
                    if (codeItem[0].equals(resultCodes[0]) && codeItem[1].equals(resultCodes[1]) && codeItem[2].equals(resultCodes[2])) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SM_ZHI_FS.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    /**
     * 三码-前三组选-复式
     */
    SM_ZU_FS("三码-前三组选-复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = Arrays.copyOfRange(issueResult.split(","), 0, 3);
                    String[] codeItem = code.split(",");
                    int indexCount = 0;
                    for (String item : codeItem) {
                        for (String result : resultCodes) {
                            if (item.equals(result)) {
                                indexCount++;
                                break;
                            }
                        }
                    }
                    if (indexCount == 3) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SM_ZU_FS.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String... codes) -> {
                return PlayOrderSplitUtil.analysisGroup(lotteryType, playType, codes);
            },
            new TicketRule[]{
                    new TicketRule(CodeEnum.SYXW_NUMBER.getCodes()),
            }
    ),
    ;

    /**
     * 规则
     */
    private TicketRule[] rules;
    /**
     * 是否中奖检查
     */
    private IPrizeCheck prizeCheck;
    /**
     * 订单检查
     */
    private IValidateOrder orderCheck;

    private IOrderSplit orderSplit;
    /**
     * 玩法名称
     */
    private String name;

    SYXWTypeEnum(String name, IPrizeCheck prizeCheck, IValidateOrder orderCheck, IOrderSplit orderSplit, TicketRule... rules) {
        this.name = name;
        this.prizeCheck = prizeCheck;
        this.orderCheck = orderCheck;
        this.rules = rules;
        this.orderSplit = orderSplit;
    }

    public IOrderSplit getOrderSplit() {
        return orderSplit;
    }

    public TicketRule[] getRules() {
        return rules;
    }

    public IPrizeCheck getPrizeCheck() {
        return prizeCheck;
    }

    public IValidateOrder getOrderCheck() {
        return orderCheck;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return ordinal();
    }

    /**
     * 获取号码和值
     *
     * @param itemList
     * @return
     */
    public static Integer getSum(List<Integer> itemList) {
        int sum = 0;
        for (Integer item : itemList) {
            sum += item;
        }
        return sum;
    }
}
