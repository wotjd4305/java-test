package com.example.javatest.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    public static String getCurrentDateMin30() {
        return LocalDate.now().minusDays(30).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String getCurrentTimeRange(int date) { return LocalDateTime.now().minusHours(date).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); }

    public static void main(String args[]){
        System.out.println(getCurrentDateMin30());
        System.out.println(getCurrentTimeRange(7200000/1000/3600));
    }
}
