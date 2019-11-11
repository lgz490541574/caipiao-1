package com.lottery.domain.model.playtype;

import com.common.util.IGlossary;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.PlayOrderSplitUtil;
import com.lottery.domain.util.*;

import java.util.ArrayList;
import java.util.List;

public enum XY28PlayTypeEnum implements IGlossary, IPlayType {
    /**
     * 幸运28  特码
     */
    TM("特码",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int sum = 0;
                    boolean flag = false;
                    for (String item : resultCodes) {
                        sum += Integer.parseInt(item);
                    }
                    if (code.equals(sum + "")) {
                        flag = true;
                    }
                    return flag;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = TM.rules;
                    String[] codes = ticketCode.split(",");
                    int sum = 0;
                    for (String item : ticketRules[0].getValues()) {
                        for (String code : codes) {
                            if (item.equals(code)) {
                                sum++;
                                break;
                            }
                        }
                    }
                    if (sum == codes.length) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String[] codes) -> {
                String[] itemList = codes[0].split(",");
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, itemList);
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.XY28_CODE_NUMBER.getCodes())
            }),
    /**
     * 幸运28  大小单双
     */
    DXDS("大小单双",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int sum = 0;
                    boolean flag = false;
                    for (String item : resultCodes) {
                        sum += Integer.parseInt(item);
                    }
                    List<String> result = new ArrayList<>();
                    if (sum >= 14) {
                        result.add("大");
                    } else {
                        result.add("小");
                    }
                    if (sum % 2 == 1) {
                        result.add("单");
                    } else {
                        result.add("双");
                    }
                    for (String item : result) {
                        if (item.equals(code)) {
                            flag = true;
                            break;
                        }
                    }
                    return flag;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = DXDS.rules;
                    String[] codes = ticketCode.split(",");
                    int sum = 0;
                    for (String item : ticketRules[0].getValues()) {
                        for (String code : codes) {
                            if (item.equals(code)) {
                                sum++;
                                break;
                            }
                        }
                    }
                    if (sum == codes.length) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String[] codes) -> {
                String[] itemList = codes[0].split(",");
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, itemList);
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.XY28_CODE_DXDS.getCodes())
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

    XY28PlayTypeEnum(String name, IPrizeCheck prizeCheck, IValidateOrder orderCheck, IOrderSplit orderSplit, TicketRule... rules) {
        this.name = name;
        this.prizeCheck = prizeCheck;
        this.orderCheck = orderCheck;
        this.rules = rules;
        this.orderSplit = orderSplit;
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
    public IOrderSplit getOrderSplit() {
        return orderSplit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return ordinal();
    }
}
