package com.lottery.domain.model.playtype;

import com.common.util.IGlossary;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.PlayOrderSplitUtil;
import com.lottery.domain.util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * pk10
 * 幸运飞艇
 */
public enum PK10TypeEnum implements IGlossary, IPlayType {
    /**
     * 北京 pk10 定位胆
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
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.getPk10DwdTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GUAN_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.YA_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.JI_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_SI.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_WU.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_LIU.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_QI.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_BA.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_JIU.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_SHI.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes())
            }
    ),
    /**
     * 北京 pk10 猜冠军
     */
    CGJ("猜冠军",
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
                    TicketRule[] ticketRules = CGJ.rules;
                    for (String item : ticketRules[Integer.parseInt(weiShu) - WeiShuEnum.GUAN_JUN.getValue()].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.getPk10DwdTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GUAN_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes())
            }
    ),
    /**
     * 北京 pk10 冠亚和
     */
    GYH("冠亚和",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    List<Integer> itemList = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        itemList.add(Integer.parseInt(resultCodes[i]));
                    }
                    int sum = getSum(itemList);
                    List<String> result = new ArrayList<>();
                    if (sum < 10) {
                        result.add("0" + sum);
                    } else {
                        result.add("" + sum);
                    }
                    if (sum > 11) {
                        result.add("和大");
                    } else {
                        result.add("和小");
                    }
                    if (sum % 2 == 1) {
                        result.add("和单");
                    } else {
                        result.add("和双");
                    }
                    for (String item : result) {
                        if (item.equals(code)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = GYH.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes[0].split(","));
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GUAN_JUN.getName(), CodeEnum.PK10_CODE_GYHE.getCodes())
            }
    ),
    /**
     * 北京 pk10 双面盘
     */
    SMP("双面盘",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code, String weiShu) {
                    String[] resultCodes = issueResult.split(",");
                    int wei = Integer.parseInt(weiShu);
                    int item = Integer.parseInt(resultCodes[wei - WeiShuEnum.GUAN_JUN.getValue()]);
                    List<String> result = new ArrayList<>();
                    if (item > 5) {
                        result.add("大");
                    } else {
                        result.add("小");
                    }
                    if (item % 2 == 1) {
                        result.add("单");
                    } else {
                        result.add("双");
                    }
                    if (wei >= WeiShuEnum.GUAN_JUN.getValue() && wei < WeiShuEnum.DI_LIU.getValue()) {
                        int sum = WeiShuEnum.GUAN_JUN.getValue() + WeiShuEnum.DI_SHI.getValue();
                        int two = Integer.parseInt(resultCodes[sum - wei - WeiShuEnum.GUAN_JUN.getValue()]);
                        if (item - two > 0) {
                            result.add("龙");
                        } else {
                            result.add("虎");
                        }
                    }
                    for (String str : result) {
                        if (str.equals(code)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode, String weiShu) {
                    TicketRule[] ticketRules = SMP.rules;
                    for (String item : ticketRules[Integer.parseInt(weiShu) - WeiShuEnum.GUAN_JUN.getValue()].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.getPk10DwdTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GUAN_JUN.getName(), CodeEnum.PK10_CODE_SMPLH.getCodes()),
                    new TicketRule(WeiShuEnum.YA_JUN.getName(), CodeEnum.PK10_CODE_SMPLH.getCodes()),
                    new TicketRule(WeiShuEnum.JI_JUN.getName(), CodeEnum.PK10_CODE_SMPLH.getCodes()),
                    new TicketRule(WeiShuEnum.DI_SI.getName(), CodeEnum.PK10_CODE_SMPLH.getCodes()),
                    new TicketRule(WeiShuEnum.DI_WU.getName(), CodeEnum.PK10_CODE_SMPLH.getCodes()),
                    new TicketRule(WeiShuEnum.DI_LIU.getName(), CodeEnum.PK10_CODE_SMPDXDS.getCodes()),
                    new TicketRule(WeiShuEnum.DI_QI.getName(), CodeEnum.PK10_CODE_SMPDXDS.getCodes()),
                    new TicketRule(WeiShuEnum.DI_BA.getName(), CodeEnum.PK10_CODE_SMPDXDS.getCodes()),
                    new TicketRule(WeiShuEnum.DI_JIU.getName(), CodeEnum.PK10_CODE_SMPDXDS.getCodes()),
                    new TicketRule(WeiShuEnum.DI_SHI.getName(), CodeEnum.PK10_CODE_SMPDXDS.getCodes())
            }
    ),
    /**
     * 北京 PK10 猜前五
     */
    CQ5("猜前五",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    StringBuffer qianWu = new StringBuffer();
                    int size = code.split(",").length;
                    for (int i = 0; i < size; i++) {
                        qianWu.append(resultCodes[i] + ",");
                    }
                    String result = qianWu.substring(0, qianWu.length() - 1);
                    if (result.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = CQ5.rules;
                    String[] codes = ticketCode.split(",");
                    Set<String> set = new HashSet<>();
                    for (String code : codes) {
                        set.add(code);
                    }
                    if (set.size() != codes.length) {
                        return false;
                    } else {
                        int sum = 0;
                        for (String item : ticketRules[0].getValues()) {
                            for (String code : codes) {
                                if (item.equals(code)) {
                                    sum++;
                                }
                            }
                        }
                        if (sum == codes.length) {
                            return true;
                        }
                    }

                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {

                return PlayOrderSplitUtil.buildDuplicateTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GUAN_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.YA_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.JI_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_SI.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_WU.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes())
            }),
    /**
     * 北京 PK10 猜前四
     */
    CQ4("猜前四",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    StringBuffer qianWu = new StringBuffer();
                    int size = code.split(",").length;
                    for (int i = 0; i < size; i++) {
                        qianWu.append(resultCodes[i] + ",");
                    }
                    String result = qianWu.substring(0, qianWu.length() - 1);
                    if (result.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = CQ4.rules;
                    String[] codes = ticketCode.split(",");
                    Set<String> set = new HashSet<>();
                    for (String code : codes) {
                        set.add(code);
                    }
                    if (set.size() != codes.length) {
                        return false;
                    } else {
                        int sum = 0;
                        for (String item : ticketRules[0].getValues()) {
                            for (String code : codes) {
                                if (item.equals(code)) {
                                    sum++;
                                }
                            }
                        }
                        if (sum == codes.length) {
                            return true;
                        }
                    }

                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {

                return PlayOrderSplitUtil.buildDuplicateTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GUAN_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.YA_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.JI_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.DI_SI.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes())
            }),
    /**
     * 北京 PK10 猜前三
     */
    CQ3("猜前三",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    StringBuffer qianWu = new StringBuffer();
                    int size = code.split(",").length;
                    for (int i = 0; i < size; i++) {
                        qianWu.append(resultCodes[i] + ",");
                    }
                    String result = qianWu.substring(0, qianWu.length() - 1);
                    if (result.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = CQ3.rules;
                    String[] codes = ticketCode.split(",");
                    Set<String> set = new HashSet<>();
                    for (String code : codes) {
                        set.add(code);
                    }
                    if (set.size() != codes.length) {
                        return false;
                    } else {
                        int sum = 0;
                        for (String item : ticketRules[0].getValues()) {
                            for (String code : codes) {
                                if (item.equals(code)) {
                                    sum++;
                                }
                            }
                        }
                        if (sum == codes.length) {
                            return true;
                        }
                    }

                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {

                return PlayOrderSplitUtil.buildDuplicateTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GUAN_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.YA_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.JI_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes())
            }),
    /**
     * 北京 PK10 猜前二
     */
    CQ2("猜前二",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    StringBuffer qianWu = new StringBuffer();
                    int size = code.split(",").length;
                    for (int i = 0; i < size; i++) {
                        qianWu.append(resultCodes[i] + ",");
                    }
                    String result = qianWu.substring(0, qianWu.length() - 1);
                    if (result.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = CQ2.rules;
                    String[] codes = ticketCode.split(",");
                    Set<String> set = new HashSet<>();
                    for (String code : codes) {
                        set.add(code);
                    }
                    if (set.size() != codes.length) {
                        return false;
                    } else {
                        int sum = 0;
                        for (String item : ticketRules[0].getValues()) {
                            for (String code : codes) {
                                if (item.equals(code)) {
                                    sum++;
                                }
                            }
                        }
                        if (sum == codes.length) {
                            return true;
                        }
                    }

                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {

                return PlayOrderSplitUtil.buildDuplicateTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GUAN_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.YA_JUN.getName(), CodeEnum.PK10_CODE_NUMBER.getCodes()),
            }),

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

    PK10TypeEnum(String name, IPrizeCheck prizeCheck, IValidateOrder orderCheck, IOrderSplit orderSplit, TicketRule... rules) {
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
