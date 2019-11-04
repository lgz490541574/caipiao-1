package com.lottery.domain.util;

import com.common.exception.BizException;
import com.common.util.GlosseryEnumUtils;
import com.common.util.IGlossary;
import com.lottery.domain.TicketInfo;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.model.PlayOrderSplitUtil;
import com.lottery.domain.model.playtype.*;

import java.util.*;

/**
 * 拆单工具类
 */
public class OrderSplitTools {

    private static Map<String, IOrderSplit> orderSplitMap = new HashMap<>();

    static {
        //快三
//        /**快三和值*/
//        orderSplitMap.put(LotteryCategoryEnum.KS.name() + KsPlayTypeEnum.HZ.name(), new IOrderSplit() {
//            @Override
//            public List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playName, String[] code) {
//                String[] itemList = code[0].split(",");
//                return buildTicketInfos(lotteryType, playName, itemList);
//            }
//        });
//        /**快三 三同号通选*/
//        orderSplitMap.put(LotteryCategoryEnum.KS.name() + KsPlayTypeEnum.STHTX.name(), (LotteryCategoryEnum lotteryType, String playName, String[] code) -> {
//            String[] itemList = code[0].split(",");
//            return buildTicketInfos(lotteryType, playName, itemList);
//        });
//        /**快三 三同号单选*/
//        orderSplitMap.put(LotteryCategoryEnum.KS.name() + KsPlayTypeEnum.STHDX.name(), (LotteryCategoryEnum lotteryType, String playName, String[] code) -> {
//            String[] itemList = code[0].split(",");
//            return buildTicketInfos(lotteryType, playName, itemList);
//        });
//        /**快三 三不同号*/
//        orderSplitMap.put(LotteryCategoryEnum.KS.name() + KsPlayTypeEnum.SBTH.name(), (LotteryCategoryEnum lotteryType, String playName, String[] code) -> {
//            String[] items = code[0].split(",");
//            List<String> result = sortList(items, 3);
//            return buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**快三 三连号通选*/
//        orderSplitMap.put(LotteryCategoryEnum.KS.name() + KsPlayTypeEnum.SLHTX.name(), (LotteryCategoryEnum lotteryType, String playName, String[] code) -> {
//            String[] itemList = code[0].split(",");
//            return buildTicketInfos(lotteryType, playName, itemList);
//        });
//        /**快三 二同号复选*/
//        orderSplitMap.put(LotteryCategoryEnum.KS.name() + KsPlayTypeEnum.ETHFX.name(), (LotteryCategoryEnum lotteryType, String playName, String[] code) -> {
//            String[] itemList = code[0].split(",");
//            return buildTicketInfos(lotteryType, playName, itemList);
//        });
//        /**快三 二同号单选*/
//        orderSplitMap.put(LotteryCategoryEnum.KS.name() + KsPlayTypeEnum.ETHDX.name(), (LotteryCategoryEnum lotteryType, String playName, String[] code) -> {
//            return getK3EthdxTicketInfos(lotteryType, playName, code);
//        });
//        /**快三 二不同号*/
//        orderSplitMap.put(LotteryCategoryEnum.KS.name() + KsPlayTypeEnum.EBTH.name(), (LotteryCategoryEnum lotteryType, String playName, String[] code) -> {
//            String[] items = code[0].split(",");
//            List<String> result = sortList(items, 2);
//            return buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });

//
        //时时彩
        /**一星复式*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.YX_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...code) -> {
            return PlayOrderSplitUtil.getSscYxfsTicketInfos(lotteryType, playName, code);
        });
        /**两面*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.LM_LM.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.getSscLmTicketInfos(lotteryType, playName, codes);
        });
        /**龙虎斗*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.LH_LHD.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.getSscLhdTicketInfos(lotteryType, playName, codes);
        });
        /**猜豹子*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.YX_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.getSscCbzTicketInfos(lotteryType, playName, codes);
        });
        /**大小单双 前二*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.DXDS_QE.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /**大小单双 后二*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.DXDS_HE.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /**大小单双 前三*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.DXDS_QS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /**大小单双 后三*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.DXDS_HS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /** 前二 复式*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.QE_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /** 前二 和值*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.QE_HZ.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxHzList(2, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 前二 跨度*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.QE_KD.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxKdList(2, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 后二 复式*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.HE_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /** 后二 和值*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.HE_HZ.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxHzList(2, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 后二 跨度*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.HE_KD.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxKdList(2, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 前三 复式*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.QS_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /** 前三 和值*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.QS_HZ.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxHzList(3, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 前三 跨度*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.QS_KD.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxKdList(3, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 中三 复式*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.ZS_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /** 中三 和值*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.ZS_HZ.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxHzList(3, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 中三 跨度*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.ZS_KD.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxKdList(3, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 后三 复式*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.HS_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /** 后三 和值*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.HS_HZ.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxHzList(3, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 后三 跨度*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.HS_KD.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            List<String> result = getZxKdList(3, codes[0].split(","));
            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
        });
        /** 四星 复式*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.SX_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
        /** 五星 复式*/
        orderSplitMap.put(LotteryCategoryEnum.SSC + SSCPlayTypeEnum.WX_FS.name(), (LotteryCategoryEnum lotteryType, String playName, String ...codes) -> {
            return PlayOrderSplitUtil.buildDiKaErTickrtInfos(lotteryType, playName, codes);
        });
//
//
//        /**北京PK10 定位胆*/
//        orderSplitMap.put(LotteryCategoryEnum.PK10 + PK10TypeEnum.DWD.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            return PlayOrderSplitUtil.getPk10DwdTicketInfos(lotteryType, playName, new String[]{codes});
//        });
//        /**北京PK10 猜冠军*/
//        orderSplitMap.put(LotteryCategoryEnum.PK10 + PK10TypeEnum.CGJ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            return PlayOrderSplitUtil.getPk10DwdTicketInfos(lotteryType, playName, new String[]{codes});
//        });
//        /**北京PK10 冠亚和*/
//        orderSplitMap.put(LotteryCategoryEnum.PK10 + PK10TypeEnum.GYH.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**北京PK10 双面盘*/
//        orderSplitMap.put(LotteryCategoryEnum.PK10 + PK10TypeEnum.SMP.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            return PlayOrderSplitUtil.getPk10DwdTicketInfos(lotteryType, playName, new String[]{codes});
//        });
//        /**北京PK10 猜前五*/
//        orderSplitMap.put(LotteryCategoryEnum.PK10 + PK10TypeEnum.CQ5.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildDuplicateTickrtInfos(lotteryType, playName, new String[]{codes});
//        });
//        /**北京PK10 猜前四*/
//        orderSplitMap.put(LotteryCategoryEnum.PK10 + PK10TypeEnum.CQ4.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildDuplicateTickrtInfos(lotteryType, playName, new String[]{codes});
//        });
//        /**北京PK10 猜前三*/
//        orderSplitMap.put(LotteryCategoryEnum.PK10 + PK10TypeEnum.CQ3.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildDuplicateTickrtInfos(lotteryType, playName, new String[]{codes});
//        });
//        /**北京PK10 猜前二*/
//        orderSplitMap.put(LotteryCategoryEnum.PK10 + PK10TypeEnum.CQ2.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildDuplicateTickrtInfos(lotteryType, playName, new String[]{codes});
//        });
//
//
//        /**香港六合彩 特码 直选*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.TM_ZX.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 特码 两面*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.TM_LM.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 任选*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_RX.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正一特*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZYT.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正一两面*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZYLM.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正二特*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZERT.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正二两面*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZERLM.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正三特*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZSANT.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正三两面*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZSANLM.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正四特*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZSIT.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正四两面*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZSILM.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正五特*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZWUT.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正五两面*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZWULM.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正六特*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZLIUT.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 正码 正六两面*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.ZM_ZLIULM.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 特码半波*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.TM_BB.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 生肖 特肖*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.SX_TX.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 生肖 一肖*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.SX_YX.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//
//            return buildTicketInfos(lotteryType, playName, codes.split(","));
//        });
//        /**香港六合彩 生肖 二肖连*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.SX_EXL.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 2);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩 生肖 三肖连*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.SX_SXL.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 3);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩 生肖 四肖连*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.SX_FOURXL.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 4);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩 连码 三全中*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.LM_SQZ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 3);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩 连码 三中二*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.LM_SZE.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 3);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩 连码 二全中*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.LM_EQZ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 2);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩 连码 二中特*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.LM_EZT.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 2);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩 连码 特串*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.LM_TC.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 2);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  尾数 特码头尾*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.WS_TMTW.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, codes[0].split(","));
//        });
//        /**香港六合彩  尾数 二尾连*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.WS_EWL.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 2);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  尾数 三尾连*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.WS_SANWL.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 3);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  尾数 四尾连*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.WS_SIWL.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 4);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  不中 五不中*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.BZ_WBZ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 5);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  不中 六不中*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.BZ_LBZ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 6);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  不中 七不中*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.BZ_QBZ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 7);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  不中 八不中*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.BZ_BBZ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 8);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  不中 九不中*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.BZ_JBZ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 9);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//        /**香港六合彩  不中 十不中*/
//        orderSplitMap.put(LotteryCategoryEnum.LHC + LHCPlayTypeEnum.BZ_SBZ.name(), (LotteryCategoryEnum lotteryType, String playName, String codes) -> {
//            String[] items = codes.split(",");
//            List<String> result = combine(items, 10);
//            return PlayOrderSplitUtil.buildTicketInfos(lotteryType, playName, result.toArray(new String[result.size()]));
//        });
//
//
//        /**幸运28特码*/
//        orderSplitMap.put(LotteryCategoryEnum.XY28 + XY28PlayTypeEnum.TM.name(), (LotteryCategoryEnum lotteryType, String playName, String[] codes) -> {
//            String[] itemList = codes[0].split(",");
//            return buildTicketInfos(lotteryType, playName, itemList);
//        });
//        /**幸运28大小单双*/
//        orderSplitMap.put(LotteryCategoryEnum.XY28 + XY28PlayTypeEnum.DXDS.name(), (LotteryCategoryEnum lotteryType, String playName, String[] codes) -> {
//            String[] itemList = codes[0].split(",");
//            return buildTicketInfos(lotteryType, playName, itemList);
//        });

//        /**经典梯子两面*/
//        orderSplitMap.put(PlayTypeEnum.TZ_LM, (LotteryCategoryEnum lotteryType, String playName, String[] codes) -> {
//            return getTzTicketInfos(playTypeEnum, codes);
//        });
        //==========================================================================================
        //===========================================================================================================

    }

    public static List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playType, String code) {
        return orderSplitMap.get(playType).doSplit(lotteryType,playType, code);
    }

    /**
     * 猜豹子
     *
     * @param lotteryType
     * @param playType
     * @param codes
     * @return
     */
    private static List<TicketInfo> getSscCbzTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            if ("".equals(codes[i])) {
                continue;
            } else {
                String[] weShuCodes = codes[i].split(",");
                for (int j = 0; j < weShuCodes.length; j++) {
                    TicketInfo TicketInfo = new TicketInfo();
                    TicketInfo.setTicketCode(weShuCodes[j]);
                    TicketInfo.setPrize(false);
                    TicketInfo.setWeiShu(i + WeiShuEnum.BAO_ZI.getValue() + "");
                    /**订单验证*/
                    if (!getPlay(lotteryType, playType).getOrderCheck().checkTicketCode(weShuCodes[j])) {
                        throw new BizException("buildOrder.error", "订单非法");
                    }
                    ticketInfos.add(TicketInfo);
                }
            }
        }
        return ticketInfos;
    }

    /**
     * 龙虎斗
     *
     * @param lotteryType
     * @param playType
     * @param codes
     * @return
     */
    private static List<TicketInfo> getSscLhdTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            if ("".equals(codes[i])) {
                continue;
            } else {
                String[] weShuCodes = codes[i].split(",");
                for (int j = 0; j < weShuCodes.length; j++) {
                    TicketInfo TicketInfo = new TicketInfo();
                    String weiShu = i + WeiShuEnum.WAN_QIAN.getValue() + "";
                    TicketInfo.setTicketCode(weShuCodes[j]);
                    TicketInfo.setPrize(false);
                    TicketInfo.setWeiShu(weiShu);
                    /**订单验证*/
                    if (!getPlay(lotteryType, playType).getOrderCheck().checkTicketCode(weShuCodes[j], weiShu)) {
                        throw new BizException("buildOrder.error", "订单非法");
                    }
                    ticketInfos.add(TicketInfo);
                }
            }
        }
        return ticketInfos;
    }

    /**
     * 时时彩两面
     *
     * @param lotteryType
     * @param playType
     * @param codes
     * @return
     */
    private static List<TicketInfo> getSscLmTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            if ("".equals(codes[i])) {
                continue;
            } else {
                String[] weShuCodes = codes[i].split(",");
                for (int j = 0; j < weShuCodes.length; j++) {
                    TicketInfo TicketInfo = new TicketInfo();
                    String weiShu = "";
                    TicketInfo.setTicketCode(weShuCodes[j]);
                    TicketInfo.setPrize(false);
                    if (i == 0) {
                        weiShu = i + WeiShuEnum.ZONG_HE.getValue() + "";
                        TicketInfo.setWeiShu(i + WeiShuEnum.ZONG_HE.getValue() + "");
                    } else {
                        weiShu = i + "";
                        TicketInfo.setWeiShu(i + "");
                    }
                    /**订单验证*/
                    if (!getPlay(lotteryType, playType).getOrderCheck().checkTicketCode(weShuCodes[j], weiShu)) {
                        throw new BizException("buildOrder.error", "订单非法");
                    }
                    ticketInfos.add(TicketInfo);
                }
            }
        }
        return ticketInfos;
    }

    private static List<TicketInfo> getSscYxfsTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] code) {
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (int i = 0; i < code.length; i++) {
            if ("".equals(code[i])) {
                continue;
            } else {
                String[] weShuCodes = code[i].split(",");
                for (int j = 0; j < weShuCodes.length; j++) {
                    TicketInfo TicketInfo = new TicketInfo();
                    String weiShu = i + WeiShuEnum.WAN_WEI.getValue() + "";
                    TicketInfo.setTicketCode(weShuCodes[j]);
                    TicketInfo.setPrize(false);
                    TicketInfo.setWeiShu(weiShu);
                    /**订单验证*/
                    if (!getPlay(lotteryType, playType).getOrderCheck().checkTicketCode(weShuCodes[j], weiShu)) {
                        throw new BizException("buildOrder.error", "订单非法");
                    }
                    ticketInfos.add(TicketInfo);
                }
            }
        }
        return ticketInfos;
    }

    private static List<TicketInfo> getK3EthdxTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] code) {
        List<List<String>> dimValue = new ArrayList<>();
        for (int i = 0; i < code.length; i++) {
            List<String> chList = new ArrayList<>();
            String[] items = code[i].split(",");
            for (String item : items) {
                if (i == 0) {
                    chList.add(item.charAt(0) + "," + item.charAt(1));
                } else {
                    chList.add(item);
                }
            }
            dimValue.add(chList);
        }
        List<String> result = getSortRecursive(dimValue);
        return buildTicketInfos(lotteryType,playType, result.toArray(new String[result.size()]));
    }


    /**
     * 不去重并排序
     * @param dimValue
     * @return
     */
    private static List<String> getSortRecursive(List<List<String>> dimValue){
        List<List<String>> recursiveResult = new ArrayList<List<String>>();
        // 递归实现笛卡尔积
        recursive(dimValue, recursiveResult, 0, new ArrayList<String>());

        List<String> result = new ArrayList<>();
        for (List<String> list : recursiveResult) {
            String s = "";
            for (String string : list) {
                if(s.contains(string)){
                    continue;
                }else{
                    s += string + ",";
                }
            }
            if(s.split(",").length == dimValue.size()+1){
                s = s.substring(0,s.length()-1);

                result.add(s);
            }
        }
        List<String> list = new ArrayList<>();
        for(String s : result){
            List<String> eList = Arrays.asList(s.split(","));
            Collections.sort(eList);
            String e = "";
            for(String str : eList){
                e += str+",";
            }
            list.add(e.substring(0,e.length()-1));
        }
        Collections.sort(list);
        return list;
    }

    /**
     * pk10 位数拆单方法
     * @param lotteryType
     * @param playType
     * @param codes
     * @return
     */
    private static List<TicketInfo> getPk10DwdTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            if ("".equals(codes[i])) {
                continue;
            } else {
                String[] weShuCodes = codes[i].split(",");
                for (int j = 0; j < weShuCodes.length; j++) {
                    TicketInfo TicketInfo = new TicketInfo();
                    String weiShu = i + WeiShuEnum.GUAN_JUN.getValue() + "";
                    TicketInfo.setTicketCode(weShuCodes[j]);
                    TicketInfo.setPrize(false);
                    TicketInfo.setWeiShu(weiShu);
                    /**订单验证*/
                    if (!getPlay(lotteryType, playType).getOrderCheck().checkTicketCode(weShuCodes[j], weiShu)) {
                        throw new BizException("buildOrder.error", "订单非法");
                    }
                    ticketInfos.add(TicketInfo);
                }
            }
        }
        return ticketInfos;
    }

    /**
     * 梯子拆单方法
     *
     * @param lotteryType
     * @param codes
     * @return
     */
    private static List<TicketInfo> getTzTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            if ("".equals(codes[i])) {
                continue;
            } else {
                String[] weShuCodes = codes[i].split(",");
                for (int j = 0; j < weShuCodes.length; j++) {
                    TicketInfo TicketInfo = new TicketInfo();
                    String weiShu = i + WeiShuEnum.QI_DIAN.getValue() + "";
                    TicketInfo.setTicketCode(weShuCodes[j]);
                    TicketInfo.setPrize(false);
                    TicketInfo.setWeiShu(weiShu);
                    /**订单验证*/
                    if (!getPlay(lotteryType, playType).getOrderCheck().checkTicketCode(weShuCodes[j], weiShu)) {
                        throw new BizException("buildOrder.error", "订单非法");
                    }
                    ticketInfos.add(TicketInfo);
                }
            }
        }
        return ticketInfos;
    }


    /**
     * 订单拆分并进行ticket验证
     *
     * @param playType
     * @param code
     * @return
     */
    private static List<TicketInfo> buildTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] code) {
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (String item : code) {
            TicketInfo ticketInfo = new TicketInfo();
            ticketInfo.setPrize(false);
            ticketInfo.setTicketCode(item);
            IPlayType play = getPlay(lotteryType, playType);
            /**订单验证*/
            if (!play.getOrderCheck().checkTicketCode(item)) {
                throw new BizException("buildOrder.error", "订单非法");
            }
            ticketInfos.add(ticketInfo);
        }
        return ticketInfos;
    }

    private static IPlayType getPlay(LotteryCategoryEnum lotteryType, String playType) {
        IGlossary glossary = GlosseryEnumUtils.getItem(lotteryType.getPlayType(), playType);
        return (IPlayType) glossary;
    }


    /**
     * 根据下注号码生成笛卡尔积组合号码
     * @param lotteryType
     * @param playType
     * @param codes
     * @return
     */
    private static List<TicketInfo> buildDiKaErTickrtInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
        List<List<String>> dimValue = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            dimValue.add(Arrays.asList(codes[i].split(",")));
        }
        List<String> result = getMoreRecursive(dimValue);
        return buildTicketInfos(lotteryType,playType, result.toArray(new String[result.size()]));
    }


    /**
     * 根据下注号码生成笛卡尔积并对单注号码去重
     * @param lotteryType
     * @param playType
     * @param codes
     * @return
     */
    private static List<TicketInfo> buildDuplicateTickrtInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
        List<List<String>> dimValue = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            dimValue.add(Arrays.asList(codes[i].split(",")));
        }
        List<String> result = getRecursive(dimValue);
        return buildTicketInfos(lotteryType,playType, result.toArray(new String[result.size()]));
    }




    /**
     * 该方法是去重了的
     *
     * @param dimValue
     * @return
     */
    private static List<String> getRecursive(List<List<String>> dimValue) {

        List<List<String>> recursiveResult = new ArrayList<List<String>>();
        // 递归实现笛卡尔积
        recursive(dimValue, recursiveResult, 0, new ArrayList<String>());

        List<String> result = new ArrayList<>();
        for (List<String> list : recursiveResult) {
            String s = "";
            for (String string : list) {
                if (s.contains(string)) {
                    continue;
                } else {
                    s += string + ",";
                }
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
    private static List<String> sortList(String[] a, int num) {
        List<String> list = new ArrayList<String>();
        List<String> result = combine(a, num);
        for (String s : result) {
            List<String> eList = Arrays.asList(s.split(","));
            Collections.sort(eList);
            String e = "";
            for (String str : eList) {
                e += str + ",";
            }
            list.add(e.substring(0, e.length() - 1));
        }
        Collections.sort(list);
        return list;
    }
    /**
     * 实现的算法
     *
     * @param a   数据数组
     * @param num M选N中 N的个数
     * @return
     */
    private static List<String> combine(String[] a, int num) {
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String[] b = new String[a.length];
        for (int i = 0; i < b.length; i++) {
            if (i < num) {
                b[i] = "1";
            } else
                b[i] = "0";
        }

        int point = 0;
        int nextPoint = 0;
        int count = 0;
        int sum = 0;
        String temp = "1";
        while (true) {
            // 判断是否全部移位完毕
            for (int i = b.length - 1; i >= b.length - num; i--) {
                if (b[i].equals("1"))
                    sum += 1;
            }
            // 根据移位生成数据
            for (int i = 0; i < b.length; i++) {
                if (b[i].equals("1")) {
                    point = i;
                    sb.append(a[point]);
                    sb.append(",");
                    count++;
                    if (count == num)
                        break;
                }
            }
            // 往返回值列表添加数据
            list.add(sb.toString().substring(0, sb.toString().length() - 1));

            // 当数组的最后num位全部为1 退出
            if (sum == num) {
                break;
            }
            sum = 0;

            // 修改从左往右第一个10变成01
            for (int i = 0; i < b.length - 1; i++) {
                if (b[i].equals("1") && b[i + 1].equals("0")) {
                    point = i;
                    nextPoint = i + 1;
                    b[point] = "0";
                    b[nextPoint] = "1";
                    break;
                }
            }
            // 将 i-point个元素的1往前移动 0往后移动
            for (int i = 0; i < point - 1; i++)
                for (int j = i; j < point - 1; j++) {
                    if (b[i].equals("0")) {
                        temp = b[i];
                        b[i] = b[j + 1];
                        b[j + 1] = temp;
                    }
                }
            // 清空 StringBuffer
            sb.setLength(0);
            count = 0;
        }
        return list;

    }

    /**
     * 直选和值
     *
     * @param playNum
     * @param codes
     * @return
     */
    private static List<String> getZxHzList(Integer playNum, String[] codes) {
        String[] arr = CodeEnum.SSC_CODE_NUMBER.getCodes();
        List<String> element = Arrays.asList(arr);
        List<List<String>> dimValue = new ArrayList<>();
        for (int i = 0; i < playNum; i++) {
            dimValue.add(element);
        }
        List<List<String>> recursiveResult = new ArrayList<List<String>>();
        // 递归实现笛卡尔积
        recursive(dimValue, recursiveResult, 0, new ArrayList<String>());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            for (List<String> list : recursiveResult) {
                Integer c = 0;
                String s = "";
                for (String string : list) {
                    c += Integer.parseInt(string);
                    s += string + ",";
                }
                if (c == Integer.parseInt(codes[i]) && s.split(",").length == dimValue.size()) {
                    s = s.substring(0, s.length() - 1);
                    result.add(s);
                }
            }
        }


        return result;
    }

    /**
     * 直选跨度
     *
     * @param playNum
     * @param codes
     * @return
     */
    private static List<String> getZxKdList(Integer playNum, String[] codes) {
        String[] arr = CodeEnum.SSC_CODE_NUMBER.getCodes();
        List<String> element = Arrays.asList(arr);
        List<List<String>> dimValue = new ArrayList<>();
        for (int i = 0; i < playNum; i++) {
            dimValue.add(element);
        }
        List<List<String>> recursiveResult = new ArrayList<List<String>>();
        // 递归实现笛卡尔积
        recursive(dimValue, recursiveResult, 0, new ArrayList<String>());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            for (List<String> list : recursiveResult) {
                //如字符串位数超过两位，需现将List<String> 转为List<Integer>
                Integer max = Integer.parseInt(Collections.max(list));
                Integer min = Integer.parseInt(Collections.min(list));
                String s = "";
                for (String string : list) {
                    s += string + ",";
                }
                if (max - min == Integer.parseInt(codes[i]) && s.split(",").length == dimValue.size()) {
                    s = s.substring(0, s.length() - 1);
                    result.add(s);
                }
            }
        }


        return result;
    }
    /**
     * 不去重获取笛卡尔积
     * @param dimValue
     * @return
     */
    private static List<String> getMoreRecursive(List<List<String>> dimValue){
        List<List<String>> recursiveResult = new ArrayList<List<String>>();
        // 递归实现笛卡尔积
        recursive(dimValue, recursiveResult, 0, new ArrayList<String>());

        List<String> result = new ArrayList<>();
        for (List<String> list : recursiveResult) {
            String s = "";
            for (String string : list) {
                s += string + ",";
            }
            if(s.split(",").length == dimValue.size()){
                s = s.substring(0,s.length()-1);

                result.add(s);
            }
        }
        return result;
    }
}
