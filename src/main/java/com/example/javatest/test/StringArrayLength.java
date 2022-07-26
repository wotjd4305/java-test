package com.example.javatest.test;

public class StringArrayLength {

    public static void main(String args[]){


        String nums ="";
        String week ="";

        System.out.println(nums.length());
        System.out.println(week.length());

        String[] nums1 = new String[nums.length()];
        for ( int i = 0 ; i < nums.length() ; i++)
            nums1[i] = (new Character(nums.charAt(i))).toString();

        String[] week1 = new String[week.length()];
        for ( int i = 0 ; i < week.length() ; i++)
            week1[i] = (new Character(week.charAt(i))).toString();

        System.out.println(nums1 == null ? "a" : "b");
        System.out.println(week1  == null ? "a" : "b");


    }
}


