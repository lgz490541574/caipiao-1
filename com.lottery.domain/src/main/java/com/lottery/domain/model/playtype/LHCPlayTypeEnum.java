package com.lottery.domain.model.playtype;

import com.common.util.DateUtil;
import com.common.util.IGlossary;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.model.PlayOrderSplitUtil;
import com.lottery.domain.util.*;

import java.util.*;

@SuppressWarnings("ALL")
public enum LHCPlayTypeEnum implements IGlossary, IPlayType {
    /**
     * 香港六合彩  特码直选
     */
    TM_ZX("特码直选",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (code.equals(resultCodes[resultCodes.length - 1])) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = TM_ZX.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String ...codes)->{
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playType, codes[0].split(","));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  特码两面
     */
    TM_LM( "特码两面",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int teMa = Integer.parseInt(resultCodes[resultCodes.length - 1]);
                    List<String> result = getLhcLiangMian(teMa);
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
                    TicketRule[] ticketRules = TM_LM.rules;
                    for (String item : ticketRules[0].getValues()) {
                        if (item.equals(ticketCode)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playType, String ...codes)->{
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playType, codes[0].split(","));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_LM.getCodes())
            }),
    /**
     * 香港六合彩  正码任选
     */
    ZM_RX( "正码任选",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    for (int i = 0; i < resultCodes.length - 1; i++) {
                        if (resultCodes[i].equals(code)) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZM_RX.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  正一特
     */
    ZM_ZYT("正一特",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (resultCodes[0].equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZM_ZYT.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  正一两面
     */
    ZM_ZYLM("正一两面",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int teMa = Integer.parseInt(resultCodes[0]);
                    List<String> result = getLhcLiangMian(teMa);
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
                    TicketRule[] ticketRules = ZM_ZYLM.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_LM.getCodes())
            }),
    /**
     * 香港六合彩  正二特
     */
    ZM_ZERT("正二特",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (resultCodes[1].equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZM_ZERT.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  正二两面
     */
    ZM_ZERLM("正二两面",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int teMa = Integer.parseInt(resultCodes[1]);
                    List<String> result = getLhcLiangMian(teMa);
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
                    TicketRule[] ticketRules = ZM_ZERLM.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_LM.getCodes())
            }),
    /**
     * 香港六合彩  正三特
     */
    ZM_ZSANT("正三特",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (resultCodes[2].equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZM_ZSANT.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  正三两面
     */
    ZM_ZSANLM("正三两面",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int teMa = Integer.parseInt(resultCodes[2]);
                    List<String> result = getLhcLiangMian(teMa);
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
                    TicketRule[] ticketRules = ZM_ZSANLM.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_LM.getCodes())
            }),
    /**
     * 香港六合彩  正四特
     */
    ZM_ZSIT("正四特",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (resultCodes[3].equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZM_ZSIT.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  正四两面
     */
    ZM_ZSILM("正四两面",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int teMa = Integer.parseInt(resultCodes[3]);
                    List<String> result = getLhcLiangMian(teMa);
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
                    TicketRule[] ticketRules = ZM_ZSILM.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_LM.getCodes())
            }),
    /**
     * 香港六合彩  正五特
     */
    ZM_ZWUT("正五特",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (resultCodes[4].equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZM_ZWUT.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  正五两面
     */
    ZM_ZWULM("正五两面",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int teMa = Integer.parseInt(resultCodes[4]);
                    List<String> result = getLhcLiangMian(teMa);
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
                    TicketRule[] ticketRules = ZM_ZWULM.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_LM.getCodes())
            }),
    /**
     * 香港六合彩  正六特
     */
    ZM_ZLIUT("正六特",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    if (resultCodes[5].equals(code)) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = ZM_ZLIUT.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  正六两面
     */
    ZM_ZLIULM("正六两面",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int teMa = Integer.parseInt(resultCodes[5]);
                    List<String> result = getLhcLiangMian(teMa);
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
                    TicketRule[] ticketRules = ZM_ZLIULM.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_LM.getCodes())
            }),
    /**
     * 香港六合彩  特码半波
     */
    TM_BB("特码半波",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String teMa = resultCodes[6];
                    String[] result = codeMap.get(code).split(" ");
                    for (String item : result) {
                        if (item.equals(teMa)) {
                            return true;
                        }
                    }
                    //结算
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = TM_BB.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_BB.getCodes())
            }),
    /**
     * 香港六合彩  生肖 特肖
     */
    SX_TX("特肖",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] sxCodes = buildMapYearShengxiao(new Date()).get(code).split(",");
                    for (String item : sxCodes) {
                        if (item.equals(resultCodes[6])) {
                            return true;
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SX_TX.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_SX.getCodes())
            }),
    /**
     * 香港六合彩  生肖 一肖
     */
    SX_YX("一肖",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] sxCodes = buildMapYearShengxiao(new Date()).get(code).split(",");
                    for (String item : sxCodes) {
                        for (String resultCode : resultCodes) {
                            if (item.equals(resultCode)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SX_YX.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_SX.getCodes())
            }),
    /**
     * 香港六合彩  生肖 二肖连
     */
    SX_EXL("二肖连",
            new AbstractPrizeCheck() {
                @Override
                public String prizeType(String issueResult, String code) {
                    String sx = getCurrentYearSX(new Date());
                    if(code.contains(sx)){
                        return "2";
                    }else{
                        return "3";
                    }
                }
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Map<String, String> sxMap = buildMapYearShengxiao(new Date());
                    Set<String> list = new HashSet<>();
                    for (String key : sxMap.keySet()) {
                        String[] values = sxMap.get(key).split(",");
                        for (String item : values) {
                            for (String resultCode : resultCodes) {
                                if (item.equals(resultCode)) {
                                    list.add(key);
                                }
                            }
                        }
                    }
                    int sum = 0;
                    String[] codes = code.split(",");
                    for (String cc : codes) {
                        for (String item : list) {
                            if (item.equals(cc)) {
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
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SX_EXL.rules;
                    String[] codes = ticketCode.split(",");
                    String sx = getCurrentYearSX(new Date());
                    int sum = 0;
                    boolean flag = false;
                    for (String item : ticketRules[0].getValues()) {
                        for (String code : codes) {
                            if (item.equals(code)) {
                                sum++;
                                if (sx.equals(code)) {
                                    flag = true;
                                }
                                break;
                            }
                        }
                    }
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 2);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_SX.getCodes())
            }),

    /**
     * 香港六合彩  生肖 三肖连
     */
    SX_SXL( "三肖连",
            new AbstractPrizeCheck() {
                @Override
                public String prizeType(String issueResult, String code) {
                    String sx = getCurrentYearSX(new Date());
                    if(code.contains(sx)){
                        return "2";
                    }else{
                        return "3";
                    }
                }
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Map<String, String> sxMap = buildMapYearShengxiao(new Date());
                    Set<String> list = new HashSet<>();
                    for (String key : sxMap.keySet()) {
                        String[] values = sxMap.get(key).split(",");
                        for (String item : values) {
                            for (String resultCode : resultCodes) {
                                if (item.equals(resultCode)) {
                                    list.add(key);
                                }
                            }
                        }
                    }
                    int sum = 0;
                    String[] codes = code.split(",");
                    for (String cc : codes) {
                        for (String item : list) {
                            if (item.equals(cc)) {
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
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SX_FOURXL.rules;
                    String[] codes = ticketCode.split(",");
                    String sx = getCurrentYearSX(new Date());
                    int sum = 0;
                    boolean flag = false;
                    for (String item : ticketRules[0].getValues()) {
                        for (String code : codes) {
                            if (item.equals(code)) {
                                sum++;
                                if (sx.equals(code)) {
                                    flag = true;
                                }
                                break;
                            }
                        }
                    }
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 3);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_SX.getCodes())
            }),
    /**
     * 香港六合彩  生肖 四肖连
     */
    SX_FOURXL("四肖连",
            new AbstractPrizeCheck() {
                @Override
                public String prizeType(String issueResult, String code) {
                    String sx = getCurrentYearSX(new Date());
                    if(code.contains(sx)){
                        return "2";
                    }else{
                        return "3";
                    }
                }
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Map<String, String> sxMap = buildMapYearShengxiao(new Date());
                    Set<String> list = new HashSet<>();
                    for (String key : sxMap.keySet()) {
                        String[] values = sxMap.get(key).split(",");
                        for (String item : values) {
                            for (String resultCode : resultCodes) {
                                if (item.equals(resultCode)) {
                                    list.add(key);
                                }
                            }
                        }
                    }
                    int sum = 0;
                    String[] codes = code.split(",");
                    for (String cc : codes) {
                        for (String item : list) {
                            if (item.equals(cc)) {
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
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = SX_FOURXL.rules;
                    String[] codes = ticketCode.split(",");
                    String sx = getCurrentYearSX(new Date());
                    int sum = 0;
                    boolean flag = false;
                    for (String item : ticketRules[0].getValues()) {
                        for (String code : codes) {
                            if (item.equals(code)) {
                                sum++;
                                if (sx.equals(code)) {
                                    flag = true;
                                }
                                break;
                            }
                        }
                    }
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 4);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_SX.getCodes())
            }),
    /**
     * 香港六合彩  连码 三全中
     */
    LM_SQZ("三全中",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int sum = 0;
                    String[] codes = code.split(",");
                    for (int i = 0; i < resultCodes.length; i++) {
                        for (String item : codes) {
                            if (item.equals(resultCodes[i])) {
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
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = LM_SQZ.rules;
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
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 3);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  连码 三中二
     */
    LM_SZE("三中二",
            new AbstractPrizeCheck () {
                @Override
                public String prizeType(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int sum = 0;
                    String[] codes = code.split(",");
                    for (int i = 0; i < resultCodes.length; i++) {
                        for (String item : codes) {
                            if (item.equals(resultCodes[i])) {
                                sum++;
                                break;
                            }
                        }
                    }
                    /**中二*/
                    if (sum == codes.length - 1) {
                        return "2";
                    }
                    /**中三*/
                    if (sum == codes.length) {
                        return "3";
                    }
                    return null;
                }

                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int sum = 0;
                    String[] codes = code.split(",");
                    for (int i = 0; i < resultCodes.length; i++) {
                        for (String item : codes) {
                            if (item.equals(resultCodes[i])) {
                                sum++;
                                break;
                            }
                        }
                    }
                    /**中二*/
                    if (sum == codes.length - 1) {
                        return true;
                    }
                    /**中三*/
                    if (sum == codes.length) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = LM_SZE.rules;
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
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 3);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  连码 二全中
     */
    LM_EQZ("二全中",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int sum = 0;
                    String[] codes = code.split(",");
                    for (int i = 0; i < resultCodes.length; i++) {
                        for (String item : codes) {
                            if (item.equals(resultCodes[i])) {
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
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = LM_EQZ.rules;
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
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 2);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  连码 二中特
     */
    LM_EZT("二中特",
            new AbstractPrizeCheck() {
                @Override
                public String prizeType(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int sum = 0;
                    String[] codes = code.split(",");
                    String teMa = resultCodes[6];
                    boolean flag = false;
                    for (int i = 0; i < resultCodes.length; i++) {
                        for (String item : codes) {
                            if (item.equals(resultCodes[i])) {
                                sum++;
                                if (i == 6) {
                                    flag = true;
                                }
                                break;
                            }
                        }
                    }
                    //二中特
                    if (sum == codes.length && flag) {
                        return "2";
                    }
                    //二中
                    if (sum == codes.length && !flag) {
                        return "3";
                    }
                    return null;
                }

                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int sum = 0;
                    String[] codes = code.split(",");
                    String teMa = resultCodes[6];
                    boolean flag = false;
                    for (int i = 0; i < resultCodes.length; i++) {
                        for (String item : codes) {
                            if (item.equals(resultCodes[i])) {
                                sum++;
                                if (i == 6) {
                                    flag = true;
                                }
                                break;
                            }
                        }
                    }
                    //二中特
                    if (sum == codes.length && flag) {
                        return true;
                    }
                    //二中
                    if (sum == codes.length && !flag) {
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = LM_EZT.rules;
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
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 2);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  连码 特串
     */
    LM_TC("特串",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int index = 0;
                    List<String> codes = new ArrayList<>(Arrays.asList(code.split(",")));
                    String teMa = resultCodes[6];
                    for (int i = 0; i < codes.size(); i++) {
                        if (codes.get(i).equals(teMa)) {
                            index = i;
                        }
                    }
                    if (index != 0) {
                        codes.remove(index);
                        for (int i = 0; i < resultCodes.length - 1; i++) {
                            if (resultCodes[i].equals(codes.get(0))) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = LM_TC.rules;
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
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 2);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  尾数 特码头尾
     */
    WS_TMTW("特码头尾",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    int teMa = Integer.parseInt(resultCodes[6]);
                    List<String> result = new ArrayList<>();
                    result.add(teMa / 10 + "头");
                    result.add(teMa % 10 + "尾");
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
                    TicketRule[] ticketRules = WS_TMTW.rules;
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
                    new TicketRule("", CodeEnum.LHC_CODE_TW.getCodes())
            }),
    /**
     * 香港六合彩  尾数 二尾连
     */
    WS_EWL("二尾连",
            new AbstractPrizeCheck() {
                @Override
                public String prizeType(String issueResult, String code) {
                    String[] codes = code.split(",");
                    boolean flag = false;
                    label:
                    for(String item : codes){
                        if(item.startsWith("0")){
                            flag = true;
                            break label;
                        }
                    }
                    if(flag){
                        return "2";
                    }else{
                        return "3";
                    }
                }
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Set<String> result = new HashSet<>();
                    String[] codes = code.split(",");
                    for (String item : resultCodes) {
                        result.add(item.charAt(1) + "尾");
                    }
                    int sum = 0;
                    for (String item : result) {
                        for(String ccode : codes){
                            if (item.equals(ccode)) {
                                sum++;
                                break;
                            }
                        }
                    }
                    if(sum == codes.length){
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = WS_EWL.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 2);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_WS.getCodes())
            }),
    /**
     * 香港六合彩  尾数 三尾连
     */
    WS_SANWL("三尾连",
            new AbstractPrizeCheck() {
                @Override
                public String prizeType(String issueResult, String code) {
                    String[] codes = code.split(",");
                    boolean flag = false;
                    label:
                    for(String item : codes){
                        if(item.startsWith("0")){
                            flag = true;
                            break label;
                        }
                    }
                    if(flag){
                        return "2";
                    }else{
                        return "3";
                    }
                }
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Set<String> result = new HashSet<>();
                    String[] codes = code.split(",");
                    for (String item : resultCodes) {
                        result.add(item.charAt(1) + "尾");
                    }
                    int sum = 0;
                    for (String item : result) {
                        for(String ccode : codes){
                            if (item.equals(ccode)) {
                                sum++;
                                break;
                            }
                        }
                    }
                    if(sum == codes.length){
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = WS_SANWL.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 3);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_WS.getCodes())
            }),
    /**
     * 香港六合彩  尾数 四尾连
     */
    WS_SIWL("四尾连",
            new AbstractPrizeCheck() {
                @Override
                public String prizeType(String issueResult, String code) {
                    String[] codes = code.split(",");
                    boolean flag = false;
                    label:
                    for(String item : codes){
                        if(item.startsWith("0")){
                            flag = true;
                            break label;
                        }
                    }
                    if(flag){
                        return "2";
                    }else{
                        return "3";
                    }
                }
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    Set<String> result = new HashSet<>();
                    String[] codes = code.split(",");
                    for (String item : resultCodes) {
                        result.add(item.charAt(1) + "尾");
                    }
                    int sum = 0;
                    for (String item : result) {
                        for(String ccode : codes){
                            if (item.equals(ccode)) {
                                sum++;
                                break;
                            }
                        }
                    }
                    if(sum == codes.length){
                        return true;
                    }
                    return false;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = WS_SIWL.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 4);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_WS.getCodes())
            }),
    /**
     * 香港六合彩  不中 五不中
     */
    BZ_WBZ("五不中",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codes = code.split(",");
                    boolean flag = true;
                    lable:
                    for(String item : codes){
                        for (String resultCode : resultCodes){
                            if(item.equals(resultCode)){
                                flag=false;
                                break lable;
                            }
                        }
                    }
                    return flag;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = BZ_WBZ.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 5);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  不中 六不中
     */
    BZ_LBZ("六不中",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codes = code.split(",");
                    boolean flag = true;
                    lable:
                    for(String item : codes){
                        for (String resultCode : resultCodes){
                            if(item.equals(resultCode)){
                                flag=false;
                                break lable;
                            }
                        }
                    }
                    return flag;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = BZ_LBZ.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 6);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  不中 七不中
     */
    BZ_QBZ("七不中",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codes = code.split(",");
                    boolean flag = true;
                    lable:
                    for(String item : codes){
                        for (String resultCode : resultCodes){
                            if(item.equals(resultCode)){
                                flag=false;
                                break lable;
                            }
                        }
                    }
                    return flag;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = BZ_QBZ.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 7);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  不中 八不中
     */
    BZ_BBZ("八不中",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codes = code.split(",");
                    boolean flag = true;
                    lable:
                    for(String item : codes){
                        for (String resultCode : resultCodes){
                            if(item.equals(resultCode)){
                                flag=false;
                                break lable;
                            }
                        }
                    }
                    return flag;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = BZ_BBZ.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 8);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  不中 九不中
     */
    BZ_JBZ("九不中",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codes = code.split(",");
                    boolean flag = true;
                    lable:
                    for(String item : codes){
                        for (String resultCode : resultCodes){
                            if(item.equals(resultCode)){
                                flag=false;
                                break lable;
                            }
                        }
                    }
                    return flag;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = BZ_JBZ.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 9);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
            }),
    /**
     * 香港六合彩  不中 十不中
     */
    BZ_SBZ("十不中",
            new AbstractPrizeCheck() {
                @Override
                public boolean isPrize(String issueResult, String code) {
                    String[] resultCodes = issueResult.split(",");
                    String[] codes = code.split(",");
                    boolean flag = true;
                    lable:
                    for(String item : codes){
                        for (String resultCode : resultCodes){
                            if(item.equals(resultCode)){
                                flag=false;
                                break lable;
                            }
                        }
                    }
                    return flag;
                }
            },
            new AbstractValidateOrder() {
                @Override
                public boolean checkTicketCode(String ticketCode) {
                    TicketRule[] ticketRules = BZ_SBZ.rules;
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
                    if (sum == codes.length ) {
                        return true;
                    }
                    return false;
                }
            },
            (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
                String[] items = codes[0].split(",");
                List<String> result = PlayOrderSplitUtil.combine(items, 10);
                return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
            },
            new TicketRule[]{
                    new TicketRule("", CodeEnum.LHC_CODE_NUMBER.getCodes())
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

    IOrderSplit split;
    /**
     * 玩法名称
     */
    private String name;

    LHCPlayTypeEnum(String name, IPrizeCheck prizeCheck, IValidateOrder orderCheck,IOrderSplit split, TicketRule... rules) {
        this.name = name;
        this.prizeCheck = prizeCheck;
        this.orderCheck = orderCheck;
        this.rules = rules;
        this.split=split;
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

    private static Map<String, String> codeMap = new HashMap<>();
    //正月初一日期数组
    private static final String[] chuYiArrays = new String[]{"2019-02-05","2020-01-25","2021-02-12","2022-02-01"
            ,"2023-01-24","2024-02-10","2025-01-29","2026-02-17",
            "2027-02-06","2028-01-26","2029-02-13","2030-02-03"};

    static {
        codeMap.put("01", ColorEnum.RED.getValue());
        codeMap.put("02", ColorEnum.RED.getValue());
        codeMap.put("03", ColorEnum.BLUE.getValue());
        codeMap.put("04", ColorEnum.BLUE.getValue());
        codeMap.put("05", ColorEnum.GREEN.getValue());
        codeMap.put("06", ColorEnum.GREEN.getValue());
        codeMap.put("07", ColorEnum.RED.getValue());
        codeMap.put("08", ColorEnum.RED.getValue());
        codeMap.put("09", ColorEnum.BLUE.getValue());
        codeMap.put("10", ColorEnum.BLUE.getValue());
        codeMap.put("11", ColorEnum.GREEN.getValue());
        codeMap.put("12", ColorEnum.RED.getValue());
        codeMap.put("13", ColorEnum.RED.getValue());
        codeMap.put("14", ColorEnum.BLUE.getValue());
        codeMap.put("15", ColorEnum.BLUE.getValue());
        codeMap.put("16", ColorEnum.GREEN.getValue());
        codeMap.put("17", ColorEnum.GREEN.getValue());
        codeMap.put("18", ColorEnum.RED.getValue());
        codeMap.put("19", ColorEnum.RED.getValue());
        codeMap.put("20", ColorEnum.BLUE.getValue());
        codeMap.put("21", ColorEnum.GREEN.getValue());
        codeMap.put("22", ColorEnum.GREEN.getValue());
        codeMap.put("23", ColorEnum.RED.getValue());
        codeMap.put("24", ColorEnum.RED.getValue());
        codeMap.put("25", ColorEnum.BLUE.getValue());
        codeMap.put("26", ColorEnum.BLUE.getValue());
        codeMap.put("27", ColorEnum.GREEN.getValue());
        codeMap.put("28", ColorEnum.GREEN.getValue());
        codeMap.put("29", ColorEnum.RED.getValue());
        codeMap.put("30", ColorEnum.RED.getValue());
        codeMap.put("31", ColorEnum.BLUE.getValue());
        codeMap.put("32", ColorEnum.GREEN.getValue());
        codeMap.put("33", ColorEnum.GREEN.getValue());
        codeMap.put("34", ColorEnum.RED.getValue());
        codeMap.put("35", ColorEnum.RED.getValue());
        codeMap.put("36", ColorEnum.BLUE.getValue());
        codeMap.put("37", ColorEnum.BLUE.getValue());
        codeMap.put("38", ColorEnum.GREEN.getValue());
        codeMap.put("39", ColorEnum.GREEN.getValue());
        codeMap.put("40", ColorEnum.RED.getValue());
        codeMap.put("41", ColorEnum.BLUE.getValue());
        codeMap.put("42", ColorEnum.BLUE.getValue());
        codeMap.put("43", ColorEnum.GREEN.getValue());
        codeMap.put("44", ColorEnum.GREEN.getValue());
        codeMap.put("45", ColorEnum.RED.getValue());
        codeMap.put("46", ColorEnum.RED.getValue());
        codeMap.put("47", ColorEnum.BLUE.getValue());
        codeMap.put("48", ColorEnum.BLUE.getValue());
        codeMap.put("49", ColorEnum.GREEN.getValue());
        codeMap.put("红大", "29 30 34 35 40 45 46");
        codeMap.put("红小", "01 02 07 08 12 13 18 19 23 24");
        codeMap.put("红单", "01 07 13 19 23 29 35 45");
        codeMap.put("红双", "02 08 12 18 24 30 34 40 46");
        codeMap.put("红合单", "01 07 12 18 23 29 30 34 45");
        codeMap.put("红合双", "02 08 13 19 24 35 40 46");
        codeMap.put("绿大", "27 28 32 33 38 39 43 44");
        codeMap.put("绿小", "05 06 11 16 17 21 22");
        codeMap.put("绿单", "05 11 17 21 27 33 39 43");
        codeMap.put("绿双", "06 16 22 28 32 38 44");
        codeMap.put("绿合单", "05 16 21 27 32 38 43");
        codeMap.put("绿合双", "06 11 17 22 28 33 39 44");
        codeMap.put("蓝大", "25 26 31 36 37 41 42 47 48");
        codeMap.put("蓝小", "03 04 09 10 14 15 20");
        codeMap.put("蓝单", "03 09 15 25 31 37 41 47");
        codeMap.put("蓝双", "04 10 14 20 26 36 42 48");
        codeMap.put("蓝合单", "03 09 10 14 25 36 41 47");
        codeMap.put("蓝合双", "04 15 20 26 31 37 42 48");
    }

    //截止年份数据
    private static int stickYear=0;
    /**
     * 六合彩两面结果
     *
     * @param teMa
     * @return
     */
    public static List<String> getLhcLiangMian(int teMa) {
        List<String> result = new ArrayList<>();
        //计算特码两面结果
        if (teMa != 49) {
            if (teMa >= 25) {//大
                result.add("大");
            } else {
                result.add("小");
            }
            if (teMa % 2 == 1) {//奇数
                result.add("单");
            } else {
                result.add("双");
            }
            if (teMa >= 25 && teMa % 2 == 1) {
                result.add("大单");
            } else if (teMa >= 25 && teMa % 2 == 0) {
                result.add("大双");
            } else if (teMa < 25 && teMa % 2 == 1) {
                result.add("小单");
            } else {
                result.add("小双");
            }
            int one = teMa / 10;
            int two = teMa % 10;
            int plus = one + two;
            if (plus <= 6) {
                result.add("合小");
            } else {
                result.add("合大");
            }
            if (plus % 2 == 1) {
                result.add("合单");
            } else {
                result.add("合双");
            }
            if (two <= 4) {
                result.add("尾小");
            } else {
                result.add("尾大");
            }
        } else {
            result.add("和");
        }
        String jiaqin = "牛马羊鸡狗猪"; //家禽
        String yeshou = "鼠虎兔龙蛇猴";//野兽
        Map<String, String> sxMap = buildMapYearShengxiao(new Date());
        String sxName = "";
        lable:
        for (Map.Entry<String, String> entry : sxMap.entrySet()) {
            String[] values = entry.getValue().split(",");
            for (String item : values) {
                if (teMa == Integer.parseInt(item)) {
                    sxName = entry.getKey();
                    break lable;
                }
            }
        }
        if (jiaqin.contains(sxName)) {
            result.add("家禽");
        } else {
            result.add("野兽");
        }
        String color = codeMap.get(teMa);
        if ("1".equals(color)) {
            result.add("红波");
        } else if ("2".equals(color)) {
            result.add("蓝波");
        } else if ("3".equals(color)) {
            result.add("绿波");
        }
        return result;
    }
    //生肖号码数据map
    private static Map<String,String> map = new HashMap<>();
    //生肖数组
    private static final String[] sx = new String[]{"鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};
    //六合彩各个生肖对应的号码
    private static final String[] group = new String[]{
            "01,13,25,37,49","02,14,26,38","03,15,27,39","04,16,28,40",
            "05,17,29,41","06,18,30,42","07,19,31,43","08,20,32,44",
            "09,21,33,45","10,22,34,46","11,23,35,47","12,24,36,48"
    };

    private static final int[] lunarYears = new int[]{2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029};
    /**
     * 获取农历年年份生肖
     * @return
     */
    public static String getCurrentYearSX(Date date){
        int year = getLunarYear(date);
        if(year < 1996){
            return null;
        }
        int start = 1996;

        return sx[(year-start)%sx.length];
    }
    /**
     * 构造六合彩生肖Map数据
     * @param
     * @return
     */
    public static Map<String,String> buildMapYearShengxiao(Date date){
        int year = getLunarYear(date);
        if(year < 1996){
            return null;
        }
        if(year == stickYear){
            return map;
        }else{
            stickYear = year;
            int start = 1996;
//		Map<String,String> map = new HashMap<>();

            int index = (year-start)%sx.length;
            for(int i=0;i<sx.length;i++){
                map.put(sx[i],"");
            }
            int j=-1;
            for(int i=index;i>=0;i--){
                j++;
                map.replace(sx[i],group[j]);
            }
            int m = 0;
            if(j<group.length-1){
                for(int k=j+1;k<group.length;k++){
                    map.replace(sx[k],group[group.length-1-m]);
                    m++;
                }
            }
            return map;
        }
    }
    /**
     * 获取当前时间的农历年
     * @return
     */
    public static int getLunarYear(Date date){
        if(date == null){
            date = new Date();
        }
        int index = 0;
        for(int i=0;i<chuYiArrays.length;i++){
            //当前元素的初一时间
            Date chuYi = DateUtil.StringToDate(chuYiArrays[i]);
            if(date.getTime()<chuYi.getTime()){
                index = i;
                break;
            }
        }
        return lunarYears[index];
    }
}
