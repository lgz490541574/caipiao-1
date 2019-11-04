package com.lottery.domain.model;

import com.common.exception.BizException;
import com.common.util.GlosseryEnumUtils;
import com.common.util.IGlossary;
import com.lottery.domain.TicketInfo;
import com.lottery.domain.util.CodeEnum;
import com.lottery.domain.util.IPlayType;
import com.lottery.domain.util.WeiShuEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayOrderSplitUtil {

    public static IPlayType getPlay(LotteryCategoryEnum lotteryType, String playType) {
        IGlossary glossary = GlosseryEnumUtils.getItem(lotteryType.getPlayType(), playType);
        return (IPlayType) glossary;
    }
    /**
     * 猜豹子
     * @param lotteryType
     * @param playType
     * @param codes
     * @return
     */
    public static List<TicketInfo> getSscCbzTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
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
    public static List<TicketInfo> getSscLhdTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
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
    public static List<TicketInfo> getSscLmTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
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

    public static List<TicketInfo> getSscYxfsTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] code) {
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

    public static List<TicketInfo> getK3EthdxTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] code) {
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
    public static List<String> getSortRecursive(List<List<String>> dimValue){
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
    public static List<TicketInfo> getPk10DwdTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
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
    public static List<TicketInfo> getTzTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
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
    public static List<TicketInfo> buildTicketInfos(LotteryCategoryEnum lotteryType, String playType, String[] code) {
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



    /**
     * 根据下注号码生成笛卡尔积组合号码
     * @param lotteryType
     * @param playType
     * @param codes
     * @return
     */
    public static List<TicketInfo> buildDiKaErTickrtInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
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
    public static List<TicketInfo> buildDuplicateTickrtInfos(LotteryCategoryEnum lotteryType, String playType, String[] codes) {
        List<List<String>> dimValue = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            dimValue.add(Arrays.asList(codes[i].split(",")));
        }
        List<String> result = getRecursive(dimValue);
        return buildTicketInfos(lotteryType,playType, result.toArray(new String[result.size()]));
    }


    public static List<String> sortList(String[] a, int num) {
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
     * 该方法是去重了的
     *
     * @param dimValue
     * @return
     */
    public static List<String> getRecursive(List<List<String>> dimValue) {

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
    public static void recursive(List<List<String>> dimValue, List<List<String>> result, int layer, List<String> curList) {
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
     * 实现的算法
     *
     * @param a   数据数组
     * @param num M选N中 N的个数
     * @return
     */
    public static List<String> combine(String[] a, int num) {
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
    public static List<String> getZxHzList(Integer playNum, String[] codes) {
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
    public static List<String> getZxKdList(Integer playNum, String[] codes) {
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
    public static List<String> getMoreRecursive(List<List<String>> dimValue){
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
