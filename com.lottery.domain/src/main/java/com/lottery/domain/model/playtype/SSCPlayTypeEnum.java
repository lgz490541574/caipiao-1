package com.lottery.domain.model.playtype;

import com.common.util.IGlossary;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.model.PlayOrderSplitUtil;
import com.lottery.domain.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public enum SSCPlayTypeEnum implements IGlossary, IPlayType {
    /**
     * 重庆时时彩一星复式
     */
    YX_FS("一星复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code, String weiShu) {
                    String[] resultCodes = issueResult.split(",");
                    if (code.equals(resultCodes[Integer.parseInt(weiShu) - WeiShuEnum.WAN_WEI.getValue()])) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode, String weiShu) {
                    TicketRule[] ticketRules = YX_FS.rules;
                    for (String item : ticketRules[Integer.parseInt(weiShu) - WeiShuEnum.WAN_WEI.getValue()].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },(LotteryCategoryEnum lotteryType, String playName, String ...code) -> {
        return PlayOrderSplitUtil.getSscYxfsTicketInfos(lotteryType, playName, code);
    },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.WAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
            }),
    /**
     * 重庆时时彩两面
     */
    LM_LM( "两面",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code, String weiShu) {
                    String[] resultCodes = issueResult.split(",");
                    /**总和*/
                    if (weiShu.equals(WeiShuEnum.ZONG_HE.getValue() + "")) {
                        int sum = 0;
                        for (String item : resultCodes) {
                            sum += Integer.parseInt(item);
                        }
                        if (sum > 22) {
                            /**和大*/
                            if (code.equals("和大")) {
                                return true;
                            }
                        } else {
                            /**和小*/
                            if (code.equals("和小")) {
                                return true;
                            }
                        }
                        if (sum % 2 == 1) {
                            /**和单*/
                            if (code.equals("和单")) {
                                return true;
                            }
                        } else {
                            /**和双*/
                            if (code.equals("和双")) {
                                return true;
                            }
                        }
                    } else {/**单个位数*/
                        Integer weiShuCode = Integer.parseInt(resultCodes[Integer.parseInt(weiShu) - WeiShuEnum.WAN_WEI.getValue()]);
                        if (weiShuCode > 4) {
                            /**大*/
                            if (code.equals("大")) {
                                return true;
                            }
                        } else {
                            /**小*/
                            if (code.equals("小")) {
                                return true;
                            }
                        }
                        if (weiShuCode % 2 == 1) {
                            /**单*/
                            if (code.equals("单")) {
                                return true;
                            }
                        } else {
                            /**双*/
                            if (code.equals("双")) {
                                return true;
                            }
                        }
                        String zhi = "1,2,3,5,7";
                        if (zhi.contains(resultCodes[Integer.parseInt(weiShu)- WeiShuEnum.WAN_WEI.getValue()])) {
                            /**质*/
                            if (code.equals("质")) {
                                return true;
                            }
                        } else {
                            /**合*/
                            if (code.equals("合")) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode, String weiShu) {
                    TicketRule[] ticketRules = LM_LM.rules;
                    if (weiShu.equals(WeiShuEnum.ZONG_HE.getValue() + "")) {
                        for (String item : ticketRules[0].getValues()) {
                            if (item.equals(ticketCode)) {
                                return true;
                            }
                        }
                    } else {
                        for (String item : ticketRules[Integer.parseInt(weiShu)].getValues()) {
                            if (item.equals(ticketCode)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.getSscLmTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.ZONG_HE.getName(), CodeEnum.SSC_CODE_LMZH.getCodes()),
                    new TicketRule(WeiShuEnum.WAN_WEI.getName(), CodeEnum.SSC_CODE_LMDX.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_LMDX.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_LMDX.getCodes()),
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_LMDX.getCodes()),
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_LMDX.getCodes())
            }),
    /**
     * 重庆时时彩龙虎斗
     */
    LH_LHD("龙虎斗",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code, String weiShu) {
                    String[] resultCodes = issueResult.split(",");
                    List<List<Integer>> resultList = new ArrayList<>();
                    /**构造10组龙虎斗数据*/
                    for (int i = 0; i < 4; i++) {
                        for (int j = i + 1; j < 5; j++) {
                            List<Integer> itemList = new ArrayList<>();
                            itemList.add(Integer.parseInt(resultCodes[i]));
                            itemList.add(Integer.parseInt(resultCodes[j]));
                            resultList.add(itemList);
                        }
                    }
                    List<Integer> item = resultList.get(Integer.parseInt(weiShu) - WeiShuEnum.WAN_QIAN.getValue());
                    Integer one = item.get(0);
                    Integer two = item.get(1);
                    if (one > two) {
                        /**结果为龙*/
                        if (code.equals("龙")) {
                            return true;
                        }
                    } else if (one < two) {
                        /**结果为虎*/
                        if (code.equals("虎")) {
                            return true;
                        }
                    } else {
                        /**结果为合*/
                        if (code.equals("和")) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode, String weiShu) {
                    TicketRule[] ticketRules = LH_LHD.rules;
                    for (String item : ticketRules[Integer.parseInt(weiShu) - WeiShuEnum.WAN_QIAN.getValue()].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.getSscLhdTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.WAN_QIAN.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.WAN_BAI.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.WAN_SHI.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.WAN_GE.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_BAI.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_SHI.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_GE.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_SHI.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_GE.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
                    new TicketRule(WeiShuEnum.SHI_GE.getName(), CodeEnum.SSC_CODE_LH.getCodes()),
            }),

    /**
     * 重庆时时彩豹子
     */
    CBZ_BZ("猜豹子",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code, String weiShu) {
                    StringBuffer buf = new StringBuffer();
                    for (int i = 0; i < 5; i++) {
                        buf.append(code + ",");
                    }
                    if (issueResult.equals(buf.substring(0, buf.length() - 1))) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = CBZ_BZ.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.getSscCbzTicketInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.BAO_ZI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
            }),
    /**
     * 大小单双前二
     */
    DXDS_QE("前二",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Integer one = Integer.parseInt(resultCodes[0]);
                    Integer two = Integer.parseInt(resultCodes[1]);
                    List<List<String>> dimValue = new ArrayList<>();
                    dimValue.add(getSingleTwo(one));
                    dimValue.add(getSingleTwo(two));
                    List<String> result = getMoreRecursive(dimValue);
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
                    TicketRule[] ticketRules = DXDS_QE.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes())
            }),
    /**
     * 时时彩大小单双 后二
     */
    DXDS_HE("后二",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Integer four = Integer.parseInt(resultCodes[3]);
                    Integer five = Integer.parseInt(resultCodes[4]);
                    List<List<String>> dimValue = new ArrayList<>();
                    dimValue.add(getSingleTwo(four));
                    dimValue.add(getSingleTwo(five));
                    List<String> result = getMoreRecursive(dimValue);
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
                    TicketRule[] ticketRules = DXDS_HE.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes()),
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes())
            }),
    /**
     * 重庆时时彩大小单双 前三
     */
    DXDS_QS("前三",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Integer one = Integer.parseInt(resultCodes[0]);
                    Integer two = Integer.parseInt(resultCodes[1]);
                    Integer three = Integer.parseInt(resultCodes[2]);
                    List<List<String>> dimValue = new ArrayList<>();
                    dimValue.add(getSingleTwo(one));
                    dimValue.add(getSingleTwo(two));
                    dimValue.add(getSingleTwo(three));
                    List<String> result = getMoreRecursive(dimValue);
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
                    TicketRule[] ticketRules = DXDS_QS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes()),
            }),
    /**
     * 重庆时时彩大小单双 后三
     */
    DXDS_HS("后三",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Integer three = Integer.parseInt(resultCodes[2]);
                    Integer four = Integer.parseInt(resultCodes[3]);
                    Integer five = Integer.parseInt(resultCodes[4]);
                    List<List<String>> dimValue = new ArrayList<>();
                    dimValue.add(getSingleTwo(three));
                    dimValue.add(getSingleTwo(four));
                    dimValue.add(getSingleTwo(five));
                    List<String> result = getMoreRecursive(dimValue);
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
                    TicketRule[] ticketRules = DXDS_HS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes()),
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes()),
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_DXDS.getCodes())
            }),
    /**
     * 重庆时时彩前二 前二复式
     */
    QE_FS("前二复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String qeFs = resultCodes[0] + "," + resultCodes[1];
                    if (qeFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = QE_FS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.WAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
            }),
    /**
     * 重庆时时彩前二 前二和值
     */
    QE_HZ("前二和值",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String qeFs = resultCodes[0] + "," + resultCodes[1];
                    if (qeFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = QE_HZ.rules;
                    String[] codes = ticketCode.split(",");
                    int sum = 0;
                    for (String code : codes) {
                        sum += Integer.parseInt(code);
                    }
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == sum) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                List<String> result = PlayOrderSplitUtil.getZxHzList(2, codes[0].split(","));
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.HZ_ZHI.getName(), new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                            "10", "11", "12", "13", "14", "15", "16", "17", "18"})
            }),
    /**
     * 重庆时时彩前二 前二跨度
     */
    QE_KD("前二跨度",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String qeFs = resultCodes[0] + "," + resultCodes[1];
                    if (qeFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            }, new AbstractValidateOrder() {
        @Override
        public boolean checkTicketCode(String ticketCode) {
            TicketRule[] ticketRules = QE_HZ.rules;
            List<String> codes = Arrays.asList(ticketCode.split(","));
            int max = Integer.parseInt(Collections.max(codes));
            int min = Integer.parseInt(Collections.min(codes));
            int minus = max - min;
            for (String item : ticketRules[0].getValues()) {
                if (Integer.parseInt(item) == minus) {
                    return true;
                }
            }
            return false;
        }
    },(LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
        List<String> result = PlayOrderSplitUtil.getZxKdList(2, codes[0].split(","));
        return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
    },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.KU_DU.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
            }),
    /**
     * 重庆时时彩后二 后二复式
     */
    HE_FS("后二复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String heFs = resultCodes[3] + "," + resultCodes[4];
                    if (heFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = HE_FS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },(LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
        return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
    },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
            }),
    /**
     * 重庆时时彩后二 后二和值
     */
    HE_HZ("后二和值",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String heFs = resultCodes[3] + "," + resultCodes[4];
                    if (heFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = HE_HZ.rules;
                    String[] codes = ticketCode.split(",");
                    int sum = 0;
                    for (String code : codes) {
                        sum += Integer.parseInt(code);
                    }
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == sum) {
                            return true;
                        }
                    }
                    return false;
                }
            },(LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
        List<String> result = PlayOrderSplitUtil.getZxHzList(2, codes[0].split(","));
        return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
    },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.HZ_ZHI.getName(), new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                            "10", "11", "12", "13", "14", "15", "16", "17", "18"})
            }),
    /**
     * 重庆时时彩后二 后二跨度
     */
    HE_KD("后二跨度",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String heFs = resultCodes[3] + "," + resultCodes[4];
                    if (heFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = HE_KD.rules;
                    List<String> codes = Arrays.asList(ticketCode.split(","));
                    int max = Integer.parseInt(Collections.max(codes));
                    int min = Integer.parseInt(Collections.min(codes));
                    int minus = max - min;
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == minus) {
                            return true;
                        }
                    }
                    return false;
                }
            },(LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
        List<String> result = PlayOrderSplitUtil.getZxKdList(2, codes[0].split(","));
        return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
    },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.KU_DU.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
            }),
    /**
     * 重庆时时彩前三 前三复式
     */
    QS_FS("前三复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String qsFs = resultCodes[0] + "," + resultCodes[1] + "," + resultCodes[2];
                    if (qsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = QS_FS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.WAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
            }),
    /**
     * 重庆时时彩前三 前三和值
     */
    QS_HZ("前三和值",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String qsFs = resultCodes[0] + "," + resultCodes[1] + "," + resultCodes[2];
                    if (qsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = QS_HZ.rules;
                    String[] codes = ticketCode.split(",");
                    int sum = 0;
                    for (String code : codes) {
                        sum += Integer.parseInt(code);
                    }
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == sum) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                List<String> result = PlayOrderSplitUtil.getZxHzList(3, codes[0].split(","));
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.HZ_ZHI.getName(), new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"})
            }),
    /**
     * 重庆时时彩前三 前三跨度
     */
    QS_KD("前三跨度",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String qsFs = resultCodes[0] + "," + resultCodes[1] + "," + resultCodes[2];
                    if (qsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = QS_KD.rules;
                    List<String> codes = Arrays.asList(ticketCode.split(","));
                    int max = Integer.parseInt(Collections.max(codes));
                    int min = Integer.parseInt(Collections.min(codes));
                    int minus = max - min;
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == minus) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                List<String> result = PlayOrderSplitUtil.getZxKdList(3, codes[0].split(","));
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.KU_DU.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
            }),
    /**
     * 重庆时时彩中三 中三复式
     */
    ZS_FS("中三复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String zsFs = resultCodes[1] + "," + resultCodes[2] + "," + resultCodes[3];
                    if (zsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZS_FS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
            }),
    /**
     * 重庆时时彩中三 中三和值
     */
    ZS_HZ("中三和值",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String zsFs = resultCodes[1] + "," + resultCodes[2] + "," + resultCodes[3];
                    if (zsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZS_HZ.rules;
                    String[] codes = ticketCode.split(",");
                    int sum = 0;
                    for (String code : codes) {
                        sum += Integer.parseInt(code);
                    }
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == sum) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                List<String> result = PlayOrderSplitUtil.getZxHzList(3, codes[0].split(","));
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.HZ_ZHI.getName(), new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"})
            }),
    /**
     * 重庆时时彩中三 中三跨度
     */
    ZS_KD("中三跨度",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String zsFs = resultCodes[1] + "," + resultCodes[2] + "," + resultCodes[3];
                    if (zsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZS_KD.rules;
                    List<String> codes = Arrays.asList(ticketCode.split(","));
                    int max = Integer.parseInt(Collections.max(codes));
                    int min = Integer.parseInt(Collections.min(codes));
                    int minus = max - min;
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == minus) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                List<String> result = PlayOrderSplitUtil.getZxKdList(3, codes[0].split(","));
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.KU_DU.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
            }),
    /**
     * 重庆时时彩后三 后三复式
     */
    HS_FS("后三复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String hsFs = resultCodes[2] + "," + resultCodes[3] + "," + resultCodes[4];
                    if (hsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = HS_FS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
            }),
    /**
     * 重庆时时彩后三 后三和值
     */
    HS_HZ("后三和值",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String hsFs = resultCodes[2] + "," + resultCodes[3] + "," + resultCodes[4];
                    if (hsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = HS_HZ.rules;
                    String[] codes = ticketCode.split(",");
                    int sum = 0;
                    for (String code : codes) {
                        sum += Integer.parseInt(code);
                    }
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == sum) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                List<String> result = PlayOrderSplitUtil.getZxHzList(3, codes[0].split(","));
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.HZ_ZHI.getName(), new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"})
            }),
    /**
     * 重庆时时彩后三 后三跨度
     */
    HS_KD("后三跨度",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String hsFs = resultCodes[2] + "," + resultCodes[3] + "," + resultCodes[4];
                    if (hsFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = HS_KD.rules;
                    List<String> codes = Arrays.asList(ticketCode.split(","));
                    int max = Integer.parseInt(Collections.max(codes));
                    int min = Integer.parseInt(Collections.min(codes));
                    int minus = max - min;
                    for (String item : ticketRules[0].getValues()) {
                        if (Integer.parseInt(item) == minus) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                List<String> result = PlayOrderSplitUtil.getZxKdList(3, codes[0].split(","));
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.KU_DU.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
            }),
    /**
     * 重庆时时彩四星 四星复式
     */
    SX_FS("四星复式",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String sxFs = issueResult.substring(0, issueResult.length() - 2);
                    if (sxFs.equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SX_FS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.WAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
            }),
    /**
     * 重庆时时彩五星 五星复式
     */
    WX_FS("五星复式",
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
                    TicketRule[] ticketRules = WX_FS.rules;
                    String[] codes = ticketCode.split(",");
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
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
            },
            new TicketRule[]{
                    new TicketRule(WeiShuEnum.WAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.QIAN_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.BAI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.SHI_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes()),
                    new TicketRule(WeiShuEnum.GE_WEI.getName(), CodeEnum.SSC_CODE_NUMBER.getCodes())
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
    /**
     * 玩法名称
     */
    private String name;
    private IOrderSplit orderSplit;
    SSCPlayTypeEnum(String name, IPrizeCheck prizeCheck, IValidateOrder orderCheck,IOrderSplit orderSplit, TicketRule... rules) {
        this.name = name;
        this.prizeCheck = prizeCheck;
        this.orderCheck = orderCheck;
        this.rules = rules;
        this.orderSplit=orderSplit;
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

    /**
     * 不去重获取笛卡尔积
     *
     * @param dimValue
     * @return
     */
    public static List<String> getMoreRecursive(List<List<String>> dimValue) {
        List<List<String>> recursiveResult = new ArrayList<List<String>>();
        // 递归实现笛卡尔积
        recursive(dimValue, recursiveResult, 0, new ArrayList<String>());

        List<String> result = new ArrayList<>();
        for (List<String> list : recursiveResult) {
            String s = "";
            for (String string : list) {
                s += string + ",";
            }
            if (s.split(",").length == dimValue.size()) {
                s = s.substring(0, s.length() - 1);

                result.add(s);
            }
        }
        return result;
    }

    /**
     * 递归实现dimValue中的笛卡尔积，结果放在result中
     *
     * @param dimValue 原始数据
     * @param result   结果数据
     * @param layer    dimValue的层数
     * @param curList  每次笛卡尔积的结果
     */
    private static void recursive(List<List<String>> dimValue, List<List<String>> result, int layer, List<String> curList) {
        if (layer < dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                recursive(dimValue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimValue.get(layer).get(i));
                    recursive(dimValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimValue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

    /**
     * 获取大小单双结果
     *
     * @param c
     * @return
     */
    public static List<String> getSingleTwo(Integer c) {
        List<String> result = new ArrayList<>();
        String stDesc = "";
        if (c > 4) {
            result.add("大");
        } else {
            result.add("小");
        }
        if (c % 2 == 1) {
            result.add("单");
        } else {
            result.add("双");
        }
        return result;
    }
}
