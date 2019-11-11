package com.lottery.domain.model.playtype;

import com.common.util.IGlossary;
import com.lottery.domain.TicketInfo;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 快三玩法
 */
public enum KsPlayTypeEnum implements IGlossary, IPlayType {
    HZ("和值", new AbstractPrizeCheck() {
        @Override
        public boolean isPrize(String issueResult, String code) {
            boolean flag = false;
            int sum = 0;
            String[] split = issueResult.split(",");
            for (String item : split) {
                sum = sum + Integer.parseInt(item);
            }
            List<String> result = new ArrayList<>();
            result.add(sum + "");
            if (sum > 10) {
                result.add("大");
            } else {
                result.add("小");
            }
            if (sum % 2 == 1) {
                result.add("单");
            } else {
                result.add("双");
            }
            for (String res : result) {
                if (code.equals(res)) {
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
                    TicketRule[] ticketRules = HZ.rules;
                    for (int i = 0; i < ticketRules.length; i++) {
                        for (String item : ticketRules[i].getValues()) {
                            if (ticketCode.equals(item)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            ((LotteryCategoryEnum lotteryType, String playName, String ...code) -> {
                String[] itemList = code[0].split(",");
                return OrderSplitTools.buildTicketInfos(lotteryType, playName, itemList);
            }),
            new TicketRule[]{new TicketRule("",
                    new String[]{"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "大", "小", "单", "双"})}

    ),
    /**
     * 江苏快三三同号通选玩法
     */
    STHTX("三同号通选",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (resultCodes[0].equals(resultCodes[1]) && resultCodes[1].equals(resultCodes[2])) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = STHTX.rules;
                    for (int i = 0; i < ticketRules.length; i++) {
                        for (String item : ticketRules[i].getValues()) {
                            if (ticketCode.equals(item)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            ((LotteryCategoryEnum lotteryType, String playName, String ...code) -> {
                String[] itemList = code[0].split(",");
                return OrderSplitTools.buildTicketInfos(lotteryType, playName, itemList);
            }),
            new TicketRule[]{new TicketRule("", new String[]{"三同号通选"})}
    ),
    /**
     * 江苏快三三同号单选玩法
     */
    STHDX("三同号单选",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    if (issueResult.equals(code.charAt(0) + "," + code.charAt(1) + "," + code.charAt(2))) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = STHDX.rules;
                    for (int i = 0; i < ticketRules.length; i++) {
                        for (String item : ticketRules[i].getValues()) {
                            if (ticketCode.equals(item)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            new IOrderSplit() {
                @Override
                public List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playType, String ...codes) {
                    String[] itemList = codes[0].split(",");
                    return OrderSplitTools.buildTicketInfos(lotteryType, playType, itemList);
                }
            },
            new TicketRule[]{new TicketRule("", new String[]{"111", "222", "333", "444", "555", "666"})}
    ),
    /**
     * 江苏快三三不同号玩法
     */
    SBTH("三不同号",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    if (issueResult.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SBTH.rules;
                    String[] codes = ticketCode.split(",");
                    Set<String> set = new HashSet<>();
                    for (String item : codes) {
                        set.add(item);
                    }
                    if (set.size() == 3) {
                        int count = 0;
                        for (String item : ticketRules[0].getValues()) {
                            for (String str : set) {
                                if (str.equals(item)) {
                                    count++;
                                    break;
                                }
                            }
                        }
                        if (count == set.size()) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            new IOrderSplit() {
                @Override
                public List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playType, String ...codes) {
                    String[] items = codes[0].split(",");
                    List<String> result = OrderSplitTools.sortList(items, 3);
                    return OrderSplitTools.buildTicketInfos(lotteryType, playType, result.toArray(new String[result.size()]));
                }
            },
            new TicketRule[]{
                    new TicketRule("", new String[]{"1", "2", "3", "4", "5", "6"})
            }
    ),
    /**
     * 江苏快三三连号通选玩法
     */
    SLHTX("三连号通选",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Integer a = Integer.parseInt(resultCodes[2]) - Integer.parseInt(resultCodes[1]);
                    Integer b = Integer.parseInt(resultCodes[1]) - Integer.parseInt(resultCodes[0]);
                    if (a == 1 && b == 1) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SLHTX.rules;
                    for (int i = 0; i < ticketRules.length; i++) {
                        for (String item : ticketRules[i].getValues()) {
                            if (ticketCode.equals(item)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            new IOrderSplit() {
                @Override
                public List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playType, String ...codes) {
                    String[] itemList = codes[0].split(",");
                    return OrderSplitTools.buildTicketInfos(lotteryType, playType, itemList);
                }
            },
            new TicketRule[]{new TicketRule("", new String[]{"三连号通选"})}
    ),

    /**
     * 江苏快三二同号复选玩法
     */
    ETHFX("二同号复选",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (resultCodes[0].equals(resultCodes[1]) && resultCodes[1].equals(resultCodes[2])) {
                        return false;
                    } else {
                        if (issueResult.contains(code.charAt(0) + "," + code.charAt(1))) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ETHFX.rules;
                    for (int i = 0; i < ticketRules.length; i++) {
                        for (String item : ticketRules[i].getValues()) {
                            if (ticketCode.equals(item)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            new IOrderSplit() {
                @Override
                public List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playType, String ...codes) {
                    String[] itemList = codes[0].split(",");
                    return OrderSplitTools.buildTicketInfos(lotteryType, playType, itemList);
                }
            },
            new TicketRule[]{new TicketRule("", new String[]{"11", "22", "33", "44", "55", "66"})}
    ),
    /**
     * 江苏快三二同号单选玩法
     */
    ETHDX("二同号单选",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    if (issueResult.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ETHDX.rules;
                    String[] codes = ticketCode.split(",");
                    int count = 0;
                    for (String item : ticketRules[1].getValues()) {
                        for (String str : codes) {
                            if (str.equals(item)) {
                                count++;
                            }
                        }
                    }
                    if (count == codes.length) {
                        return true;
                    }
                    return false;
                }
            },
            new IOrderSplit() {
                @Override
                public List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playType, String ...code) {
                    return OrderSplitTools.getK3EthdxTicketInfos(lotteryType, playType,code);
                }
            },
            new TicketRule[]{new TicketRule("", new String[]{"11", "22", "33", "44", "55", "66"}),
                    new TicketRule("", new String[]{"1", "2", "3", "4", "5", "6"})}
    ),
    /**
     * 江苏快三二不同号玩法
     */
    EBTH("二不同号",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] codes = code.split(",");
                    if (issueResult.contains(codes[0]) && issueResult.contains(codes[1])) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = EBTH.rules;
                    String[] codes = ticketCode.split(",");
                    int count = 0;
                    for (String item : ticketRules[0].getValues()) {
                        for (String str : codes) {
                            if (str.equals(item)) {
                                count++;
                            }
                        }
                    }
                    if (count == codes.length) {
                        return true;
                    }
                    return false;
                }
            },
            new IOrderSplit() {
                @Override
                public List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playType, String ...code) {
                    String[] items = code[0].split(",");
                    List<String> result = OrderSplitTools.sortList(items, 2);
                    return OrderSplitTools.buildTicketInfos(lotteryType, playType, result.toArray(new String[result.size()]));
                }
            },
            new TicketRule[]{
                    new TicketRule("", new String[]{"1", "2", "3", "4", "5", "6"})}
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

    private IOrderSplit split;

    /**
     * 玩法名称
     */
    private String name;

    KsPlayTypeEnum(String name, IPrizeCheck prizeCheck, IValidateOrder orderCheck, IOrderSplit orderSplit, TicketRule... rules) {
        this.name = name;
        this.prizeCheck = prizeCheck;
        this.orderCheck = orderCheck;
        this.rules = rules;
        this.split=orderSplit;
    }

    @Override
    public TicketRule[] getRules() {
        return rules;
    }



    @Override
    public IPrizeCheck getPrizeCheck() {
        return prizeCheck;
    }

    @Override
    public IValidateOrder getOrderCheck() {
        return orderCheck;
    }

    @Override
    public IOrderSplit getOrderSplit() {
        return split;
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
