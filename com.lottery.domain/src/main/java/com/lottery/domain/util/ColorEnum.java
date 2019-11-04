package com.lottery.domain.util;

public enum ColorEnum {
    MOREN("默认",null),
    RED("红色","1"),
    BLUE("蓝色","2"),
    GREEN("绿色","3"),

    SX_ONE("一组","01 13 25 37 49"),
    SX_TWO("二组","02 14 26 38"),
    SX_THREE("三组","03 15 27 39"),
    SX_FOUR("四组","04 16 28 40"),
    SX_FIVE("五组","05 17 29 41"),
    SX_SIX("六组","06 18 30 42"),
    SX_SEVEN("七组","07 19 31 43"),
    SX_EIGHT("八组","08 20 32 44"),
    SX_NINE("九组","09 21 33 45"),
    SX_TEN("十组","10 22 34 46"),
    SX_ELEVEN("十一组","11 23 35 47"),
    SX_TWELVE("十二组","12 24 36 48"),
    ;

    ColorEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
