package com.lottery.main.controller;

import com.common.annotation.RoleResource;
import com.common.util.ContentDto;
import com.common.util.DateUtil;
import com.common.util.GlosseryEnumUtils;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.PeriodDayLog;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.main.AbstractClientController;
import com.lottery.main.controller.dto.LotteryPeriodDto;
import com.lottery.service.LotteryPeriodService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Api(description = "期号")
@RestController
@RequestMapping(method = {RequestMethod.POST})
public class PeriodController extends AbstractClientController {

    @Resource
    private LotteryPeriodService lotteryPeriodService;


    /**
     * 列表查询
     *
     * @param dto
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/period/list")
    public Map<String, Object> list(@RequestBody LotteryPeriodDto dto) {
        return buildMessage(() -> {
            LotteryCategoryEnum category = GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, dto.getLotteryType());
            YesOrNoEnum status = GlosseryEnumUtils.getItem(YesOrNoEnum.class, dto.getStatus());
            return lotteryPeriodService.queryByPage(category, status, dto.getProxyId(), dto.getPageinfo().getPage());
        });
    }

    @RoleResource(resource = "lottery")
    @RequestMapping("/period/buildLHC")
    public Map<String, Object> buildLHC(ContentDto dto) {
        return buildMessage(() -> {
            lotteryPeriodService.buildLHCPeriod(dto.getContent());
            return null;
        });
    }

//
//    /**
//     * 手动开奖
//     *
//     * @param info
//     * @return
//     */
//    @RoleResource(resource = "lottery")
//    @RequestMapping("/period/save")
//    public Map<String, Object> save(@RequestBody PeriodDto info) {
//        return buildMessage(() -> {
//            GameTypeEnum type = GlosseryEnumUtils.getItem(GameTypeEnum.class,info.getGameType());
//            IPeriodInfoService periodInforService = findPeriodInforService(type);
//            AbstractPeriod entity =castAbstractPeriod(periodInforService);
//            BeanCoper.copyProperties(entity, info);
//            periodInforService.openAndSettle(entity);
//            return null;
//        });
//    }
//
//    /**
//     * 手动开奖
//     *
//     * @param info
//     * @return
//     */
//    @RoleResource(resource = "lottery")
//    @RequestMapping("/period/updateTime")
//    public Map<String, Object> updateTime(@RequestBody PeriodDto info) {
//        return buildMessage(() -> {
//            GameTypeEnum type = GlosseryEnumUtils.getItem(GameTypeEnum.class,info.getGameType());
//            IPeriodInfoService periodInforService = findPeriodInforService(type);
//            AbstractPeriod entity =castAbstractPeriod(periodInforService);
//            entity.setId(info.getId());
//            entity.setGameType(info.getGameType());
//            entity.setEndOrderTime(info.getEndOrderTime());
//            periodInforService.save(entity);
//            return null;
//        });
//    }
//
//
//    /**
//     * 查询
//     *
//     * @param content
//     * @return
//     */
//    @RoleResource(resource = "lottery")
//    @RequestMapping("/period/view")
//    public Map<String, Object> view(@RequestBody String content) {
//        GameTypeEnum type = GlosseryEnumUtils.getItem(GameTypeEnum.class,getGameTypeValue(content));
//        IPeriodInfoService periodInforService = findPeriodInforService(type);
//        return buildMessage(() -> periodInforService.findById(getIdByJson(content),getProxyIdValue(content)));
//    }
//
//
//    /**
//     * 手动开奖
//     *
//     * @param info
//     * @return
//     */
//    @RoleResource(resource = "lottery")
//    @RequestMapping("/period/settle")
//    public Map<String, Object> settle(@RequestBody PeriodDto info) {
//        return buildMessage(() -> {
//            GameTypeEnum type = GlosseryEnumUtils.getItem(GameTypeEnum.class,info.getGameType());
//            IPeriodInfoService periodInforService = findPeriodInforService(type);
//            AbstractPeriod entity =castAbstractPeriod(periodInforService);
//            BeanCoper.copyProperties(entity, info);
//            entity.setStatus(YesOrNoEnum.NO.getValue());
//            entity.setSettleStatus(YesOrNoEnum.NO.getValue());
//            periodInforService.settlePeriodByHand(entity);
//            return null;
//        });
//    }
//
//    /**
//     * 手动结算
//     *
//     * @param info
//     * @return
//     */
//    @RoleResource(resource = "lottery")
//    @RequestMapping("/period/execute")
//    public Map<String, Object> execute(@RequestBody PeriodDto info) {
//        return buildMessage(() -> {
//            GameTypeEnum type = GlosseryEnumUtils.getItem(GameTypeEnum.class,info.getGameType());
//            IPeriodInfoService periodInforService = findPeriodInforService(type);
//            AbstractPeriod entity =castAbstractPeriod(periodInforService);
//            BeanCoper.copyProperties(entity, info);
//            entity.setStatus(YesOrNoEnum.YES.getValue());
//            entity.setSettleStatus(YesOrNoEnum.NO.getValue());
//            periodInforService.executePeriodByHand(entity);
//            return null;
//        });
//    }
//
//    /**
//     * 手动添加彩票期号
//     *
//     * @param
//     * @return
//     */
//    @RoleResource(resource = "lottery")
//    @RequestMapping("/period/create")
//    public Map<String, Object> createPeriod(@RequestBody PeriodDto info) {
//        return buildMessage(() -> {
//            GameTypeEnum type = GlosseryEnumUtils.getItem(GameTypeEnum.class,info.getGameType());
//            IPeriodInfoService periodInforService = findPeriodInforService(type);
//            Date date = null;
//            if(StringUtils.isBlank(info.getDate())){
//                date = new Date();
//            }else {
//                date = DateUtil.StringToDate(info.getDate());
//            }
//            try {
//                Long proxyId = null;
//                if(type.name().startsWith("DSH")){
//                    proxyId = info.getProxyId();
//                }
//                periodInforService.buildDayPeriod(date,proxyId);
//            } catch (Exception e) {
//                LOGGER.error("createPeriod.error", e);
//                throw new ApplicationException("createPeriod.error",e);
//            }
//            return null;
//        });
//    }
//
//    /**
//     * 修改六合彩下注截止时间
//     *
//     * @param
//     * @return
//     */
//    @RoleResource(resource = "lottery")
//    @RequestMapping("/period/updateLhcEndTime")
//    public Map<String, Object> updateLhcEndTime(@RequestBody PeriodDto info) {
//        return buildMessage(() -> {
//            GameTypeEnum type = GlosseryEnumUtils.getItem(GameTypeEnum.class,info.getGameType());
//            IPeriodInfoService periodInforService = findPeriodInforService(type);
//            LhcPeriodInfo period = new LhcPeriodInfo();
//            period.setGameType(type.getValue());
//            period.setStatus(YesOrNoEnum.NO.getValue());
//            List<LhcPeriodInfo> query = periodInforService.query(period);
//            String date = info.getDate();
//            if(date.length()!=8){
//                throw new BizException("时间格式错误，例如：21:30:00");
//            }
//            for(LhcPeriodInfo p : query){
//                String dateTime = DateUtil.formatDateTime(p.getEndOrderTime(), "yyyy-MM-dd");
//                Date endTime = DateUtil.parse(dateTime+" "+date,"yyyy-MM-dd HH:mm:ss");
//                LhcPeriodInfo periodInfo = new LhcPeriodInfo();
//                periodInfo.setId(p.getId());
//                periodInfo.setEndOrderTime(endTime);
//                periodInforService.save(periodInfo);
//            }
//            return null;
//        });
//    }
//
//    /**
//     * 往期期号未开奖校验并开奖
//     *
//     * @param
//     * @return
//     */
//    @RoleResource(resource = "lottery")
//    @RequestMapping("/period/valid")
//    public Map<String, Object> valid(@RequestBody PeriodDto info) {
//        return buildMessage(() -> {
//            GameTypeEnum type = GlosseryEnumUtils.getItem(GameTypeEnum.class,info.getGameType());
//            IPeriodInfoService periodInforService = findPeriodInforService(type);
//            String date = info.getDate();
//            periodInforService.validPeriodByData(date);
//            return null;
//        });
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    /**
//     * json字符串获取游戏类型
//     * @param content
//     * @return
//     */
//    private Integer getGameTypeValue(String content){
//        try {
//            JSONObject json = JSONObject.fromObject(content);
//            return json.getInt("gameType");
//        } catch (Exception var3) {
//            log.error("get json gameType error", var3);
//            throw new ApplicationException("获取JSON gameType 失败");
//        }
//    }
//
//    private Long getProxyIdValue(String content){
//        try {
//            JSONObject json = JSONObject.fromObject(content);
//            if(!json.containsKey("proxyId")){
//                return null;
//            }
//            if(StringUtils.isBlank(json.get("proxyId").toString())){
//                return null;
//            }
//            return json.getLong("proxyId");
//        } catch (Exception var3) {
//            log.error("get json gameType error", var3);
//            throw new ApplicationException("获取JSON gameType 失败");
//        }
//    }
//
//    /**
//     * 获取期号实体类型
//     * @param periodInforService
//     * @return
//     */
//    private AbstractPeriod castAbstractPeriod(IPeriodInfoService periodInforService){
//
//        AbstractPeriod entity =null;
//        try {
//            entity= (AbstractPeriod) periodInforService.getEntityType().getConstructors()[0].newInstance();
//            return entity;
//        }
//        catch(Exception e){
//            log.error("PeriodController->list.error",e);
//            throw new BizException("period.list.error","查找期号实体类型错误");
//        }
//    }
}
