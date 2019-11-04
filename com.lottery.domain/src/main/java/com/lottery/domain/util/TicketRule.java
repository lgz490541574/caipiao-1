package com.lottery.domain.util;

import java.io.Serializable;

/**
 * 彩票规则
 */
public class TicketRule implements Serializable {

    public TicketRule(String wei, String[] values) {
        this.wei = wei;
        this.values = values;
    }

    private String wei;
    private String[] values;

    public String getWei() {
        return wei;
    }

    public void setWei(String wei) {
        this.wei = wei;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }
}
