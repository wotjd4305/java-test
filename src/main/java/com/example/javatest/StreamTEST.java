package com.example.javatest;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamTEST {

    public static void main(String args[]){
        List<Ob> memInfoList = new ArrayList<>();
        memInfoList.add(new Ob("a1","b1","c1"));
        memInfoList.add(new Ob("a2","b2","c2"));
        memInfoList.add(new Ob("a3","b3","c3"));
        memInfoList.add(new Ob("a4","b4","c4"));
        memInfoList.add(new Ob("a5","b5","c5"));
        memInfoList.add(new Ob("a6","b6","c6"));

        System.out.println(memInfoList.stream().map(o-> o.getY().
                replace("-","")).collect(toList()));

        Object a = memInfoList.stream().map(o-> o.getY().
                replace("-","")).collect(toList());

        System.out.println(a.toString());

    }



    public static class Ob{
        String x;
        String y;

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getZ() {
            return z;
        }

        public void setZ(String z) {
            this.z = z;
        }

        String z;

        public Ob(String x, String y, String z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
