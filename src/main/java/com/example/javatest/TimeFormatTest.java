package com.example.javatest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TimeFormatTest {
    public static void main(String[] args) throws Exception {

        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
        // 년월일시분초 14자리 포멧
        SimpleDateFormat fourteen_format = new SimpleDateFormat("YYYYMMddHH");
        System.out.println(fourteen_format.format(date_now)); // 14자리 포멧으로 출력한다

        String date = fourteen_format.format(new Date(System.currentTimeMillis()));

        //1년 후 날짜
        String addDay = calDateWithFormat(date, -365, "yyyyMMddHH");

        System.out.println(addDay); //20200802
        System.out.println(System.currentTimeMillis()); //20200802

        String ym = calDateWithFormat2(new Date(System.currentTimeMillis()),-365, "yyyyMMdd");
        System.out.println(ym);

        System.out.println();
        String yyyyy = calDateWithFormat2(new Date(System.currentTimeMillis()),-(Integer.parseInt("347")), "yyyyMMdd");
        System.out.println(yyyyy);
        String yyyyy2 = calDateWithFormat2(new Date(System.currentTimeMillis()),-(Integer.parseInt("320")), "yyyyMM");
        System.out.println(yyyyy2);
        String yyyyy3 = calDateWithFormat2(new Date(System.currentTimeMillis()),-(Integer.parseInt("333")), "yyyyMM");
        System.out.println(yyyyy3);
        String yyyyy4 = calDateWithFormat2(new Date(System.currentTimeMillis()),-(Integer.parseInt("347")+1), "yyyyMM");
        System.out.println(yyyyy4);

        String yyyyy5 = calDateWithFormat2(new Date(System.currentTimeMillis()),-30, "yyyyMM");
        System.out.println(yyyyy5);

        String yyyyy6 = calDateWithFormat2(new Date(System.currentTimeMillis()),-195, "yyyyMMdd");
        System.out.println(yyyyy6);

        String yyyyy7 = calDateWithFormat2(new Date(System.currentTimeMillis()),-195, "yyyyMM");
        System.out.println(yyyyy7);

        String yyyyy8 = calDateWithFormat2(new Date(System.currentTimeMillis()),-190, "yyyy");
        System.out.println(yyyyy8);

        String yyyyy9 = calDateWithFormat2(new Date(System.currentTimeMillis()),-195, "yyyy");
        System.out.println(yyyyy9);

        System.out.println("############################");


        Calendar ca1l = Calendar.getInstance();
        ca1l.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("current: " + df.format(ca1l.getTime()));

        ca1l.add(Calendar.DATE, -195);
        System.out.println("after: " + df.format(ca1l.getTime()));

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        DateFormat df2 = new SimpleDateFormat("yy-MM");
        System.out.println("current: " + df2.format(cal2.getTime()));

        cal2.add(Calendar.DATE, -195);
        System.out.println("after: " + df2.format(cal2.getTime()));

        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(new Date());
        DateFormat df23= new SimpleDateFormat("yyyy");
        System.out.println("current: " + df23.format(cal3.getTime()));

        cal3.add(Calendar.DATE, -195);
        System.out.println("after: " + df23.format(cal3.getTime()));

        System.out.println("############");

        calDateWithFormat3(new Date(),-195, "yyyyMMdd");
        calDateWithFormat3(new Date(),-195, "yyyyMM");
        calDateWithFormat3(new Date(),-195, "yyyy");
        calDateWithFormat3(new Date(),-195, "yy");

        System.out.println("## 라스트 " + calDateWithFormat4(new Date(System.currentTimeMillis()),-195, "yyyyMMdd"));
        System.out.println("## 라스트 " + calDateWithFormat4(new Date(System.currentTimeMillis()),-195, "yyyyMM"));
        System.out.println("## 라스트 " + calDateWithFormat4(new Date(System.currentTimeMillis()),-195, "yyyy"));
        System.out.println("## 라스트 " + calDateWithFormat4(new Date(System.currentTimeMillis()),-195, "yy"));


        String pattern = "yyyyMMddHHmmss:SS";
        //
        Date dateTest = new Date();
        System.out.println("##포맷없는 Date 형" + dateTest);

        //
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern);
        String dateStr = simpleDateFormat2.format(cal1.getTime());
        System.out.println("##String으로 변환한 Date " + dateStr);
        System.out.println("## String -> Date" + simpleDateFormat2.parse(dateStr));


        //
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        System.out.println("##포맷있는 Date 형" + simpleDateFormat.format(dateTest));




    }

    private static String calDateWithFormat(String date, int day, String format) throws Exception {
        SimpleDateFormat dtFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        Date dt = dtFormat.parse(date);
        cal.setTime(dt);
        cal.add(Calendar.DATE, day);
        return dtFormat.format(cal.getTime());
    }

    public static String calDateWithFormat2(Date date, int day, String format) throws ParseException {
        SimpleDateFormat dtFormat = new SimpleDateFormat(format);
        String strDate = dtFormat.format(date);
        Date dt = dtFormat.parse(strDate);
        Calendar cal = Calendar.getInstance();

        cal.setTime(dt);
        cal.add(Calendar.DATE, day);

        return dtFormat.format(cal.getTime());
    }

    public static String calDateWithFormat3(Date date, int day, String format) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat df2 = new SimpleDateFormat(format);
        System.out.println("current: " + df2.format(cal.getTime()));

        cal.add(Calendar.DATE, day);
        System.out.println("after: " + df2.format(cal.getTime()));

        return df2.format(cal.getTime());
    }

    public static String calDateWithFormat4(Date date, int day, String format) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat df2 = new SimpleDateFormat(format);
        cal.add(Calendar.DATE, day);

        return df2.format(cal.getTime());
    }

}
