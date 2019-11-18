package com.lottery.main.controller.dto;

import lombok.Data;

@Data
public class ChangeDataDto {

    private Integer sourceType;

    private Integer targetType;

    private String sourceProxyId;

    private String targetProxyId;

}
