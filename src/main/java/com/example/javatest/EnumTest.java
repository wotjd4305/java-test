package com.example.javatest;

import lombok.Getter;

public class EnumTest {

    public static void main(String args[]){

        System.out.println(StoreOrderDisplayType.AUTO.getCode());
    }

    @Getter
    public enum StoreOrderDisplayType  {
        NONE("0", "사용안함"),
        SMOOTH("1", "원활"),
        DELAY("2", "지체"),
        CONGEST("3", "정체"),
        AUTO("4", "자동");

        private String phrase;
        private String code;

        StoreOrderDisplayType(String code, String phrase) {
            this.code = code;
            this.phrase = phrase;
        }
    }
}
