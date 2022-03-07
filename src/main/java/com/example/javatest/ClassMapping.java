package com.example.javatest;

import java.util.HashMap;

public class ClassMapping {

    public static void main(String[] args) {
        Vo vo = new Vo();
        LogVo logVo = new LogVo();

        HashMap<Class,Class> test = new HashMap<>();
        test.put(vo.getClass(),logVo.getClass());

        System.out.println("# 하이 #" + (test.get(vo.getClass())).toString());
    }


    public static class Vo {
        int a =1;
        int b =2;

        @Override
        public String toString() {
            return "Vo{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }


    public static class LogVo {
        int La =3;
        int Lb =4;

        @Override
        public String toString() {
            return "LogVo{" +
                    "La=" + La +
                    ", Lb=" + Lb +
                    '}';
        }
    }

}

