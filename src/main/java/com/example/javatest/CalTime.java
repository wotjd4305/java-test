package com.example.javatest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class CalTime {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss.SSS", Locale.KOREA);
        Date d1 = f.parse("01:05:10.571");
        Date d2 = f.parse("01:05:07.334");
        long diff = d1.getTime() - ((Date) d2).getTime();
        long sec = diff / 1000;
        long milsec = diff % 1000;
        System.out.println(sec + "." + milsec);
        d1.setTime(diff + 100);
        System.out.println(d1.getTime());

        System.out.println("--------");

        //
        milsec = 3555;
        Date d3 = f.parse("01:05:10.571");
        System.out.println(d3.getTime());
        long curTime = d1.getTime() + milsec;
        System.out.println(d3.getTime());
        d3.setTime(curTime);

        diff = d3.getTime();
        sec = diff/1000;
        milsec = diff%1000;
        System.out.println(sec + "." + milsec);

        System.out.println("-----------");
        //
        LocalDateTime today = LocalDateTime.now();
        System.out.println(today);
        LocalDateTime today2 = today.plusNanos(1500*1000000);
        System.out.println(today2);

        System.out.println("-----");
        Date date = java.sql.Timestamp.valueOf(today);
        System.out.println(date.toString().substring(11,23));
        date = f.parse(date.toString().substring(11,23));
        System.out.println(date.getTime());

    }

}
