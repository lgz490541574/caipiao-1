package com.lottery.main.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "登录表单")
public class LoginDto implements Serializable {
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "内容")
    private String code;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
