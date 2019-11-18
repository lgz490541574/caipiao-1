package com.lottery.main.controller;

import com.common.exception.ApplicationException;
import com.common.util.GlosseryEnumUtils;
import com.common.util.IGlossary;
import com.common.util.StringUtils;
import com.common.util.model.SexEnum;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.WeiShuEnum;
import com.lottery.main.AbstractClientController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "公共接口")
@RestController
@RequestMapping(method = {RequestMethod.POST})
public class GlobalController extends AbstractClientController {

    private static Map<String, Class> glosseryItems;

    static {
        glosseryItems = new HashMap<>();
        glosseryItems.put("yesorno", YesOrNoEnum.class);
        glosseryItems.put("sextype", SexEnum.class);
        glosseryItems.put("weishu", WeiShuEnum.class);
    }

    @RequestMapping(value = "/global/type/{type}")
    @ResponseBody
    public Map<String, Object> buildGlossery(@PathVariable("type") String type) {
        return buildMessage(() -> {
            List<Map<String, Object>> keyValues = new ArrayList<Map<String, Object>>();
            if (StringUtils.isBlank(type)) {
                throw new ApplicationException("buildGlossery Error unKnow type");
            }
            if ("category".equalsIgnoreCase(type)) {
                LotteryCategoryEnum[] types = LotteryCategoryEnum.values();
                List<LotteryCategoryEnum> typeList = new ArrayList<>();
                for (LotteryCategoryEnum item : types) {
                    if (item.getParent() != null) {
                        typeList.add(item);
                        HashMap<String, Object> itemValue = new HashMap<>();
                        itemValue.put("value", item.getValue());
                        itemValue.put("name", item.getName());
                        keyValues.add(itemValue);
                    }
                }
                return keyValues;
            }
            Class currentEnum = glosseryItems.get(type);
            for (Object o : currentEnum.getEnumConstants()) {
                IGlossary v = (IGlossary) o;
                HashMap<String, Object> item = new HashMap<>();
                item.put("value", v.getValue());
                item.put("name", v.getName());
                keyValues.add(item);
            }
            return keyValues;
        });
    }


    @RequestMapping(value = "/global/playTypes/{category}")
    @ResponseBody
    public Map<String, Object> playTypes(@PathVariable("category") String category) {
        return buildMessage(() -> {
            List<Map<String, Object>> keyValues = new ArrayList<>();
            LotteryCategoryEnum categoryType = GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, category);
            Object[] enumConstants = categoryType.getPlayType().getEnumConstants();
            for (Object o : enumConstants) {
                IGlossary enumItem = (IGlossary) o;
                HashMap<String, Object> item = new HashMap<>();
                item.put("value", enumItem.getValue());
                item.put("name", enumItem.getName());
                keyValues.add(item);
            }
            return keyValues;
        });
    }
}
