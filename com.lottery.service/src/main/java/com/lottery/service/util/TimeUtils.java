package com.lottery.service.util;

import com.common.util.DateUtil;
import com.lottery.domain.model.LotteryCategoryEnum;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    /**
     * 起始时间
     * @param day 天数 1
     * @return
     */
    public static Date getStartTime(Integer day) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY,0);
        todayStart.set(Calendar.MINUTE,0);
        todayStart.set(Calendar.SECOND,0);
        todayStart.set(Calendar.MILLISECOND,0);
        Long dayMillos = 24*3600*1000l;
        return new Date(todayStart.getTime().getTime()-day*dayMillos);
    }

    /**
     * 结束时间
     * @param day 天数
     * @return
     */
    public static Date getEndTime(Integer day) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY,23);
        todayEnd.set(Calendar.MINUTE,59);
        todayEnd.set(Calendar.SECOND,59);
        todayEnd.set(Calendar.MILLISECOND,999);
        Long dayMillos = 24*3600*1000l;
        return new Date(todayEnd.getTime().getTime()-day*dayMillos);
    }

    /**
     * 传入时间的当天零点
     * @param date
     * @return
     */
    public static Date getMinTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 传入时间的当天23:59:59 999
     * @param date
     * @return
     */
    public static Date getMaxTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return new Date(calendar.getTime().getTime()+24*3600*1000l-1);
    }


    public static String buildOrderCode(Date crateTime, LotteryCategoryEnum type, Long proxyId, Long id){
        String yyyyMMddHHmmss = DateUtil.formatDateTime(crateTime, "yyyyMMddHHmmssSSS");
        return yyyyMMddHHmmss+type.getValue()+proxyId+id;
    }


    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            String s = buildOrderCode(new Date(), LotteryCategoryEnum.SSC_JZ, 1l, 100l);
            System.out.println(s);
        }
    }
}
