package com.lottery.service;

import com.common.mongo.MongoService;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.JobErrorInfo;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.dto.OrderCode;
import com.lottery.domain.model.LotteryCategoryEnum;

import java.math.BigDecimal;

public interface JobErrorService extends MongoService<JobErrorInfo> {

}
