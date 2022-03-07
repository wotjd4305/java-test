package com.example.javatest;

public class isNumberic {


    public static void main(String args[]){

        String a = "1,000";
        String b = "1,000";

        if(!(isNumeric(a) && isNumeric(b))){
            System.out.println("True");
        }

    }


    public static boolean isNumeric(String arg){
        if(isEmpty(arg)) return false;
        final String pattern = "[0-9]+";
        return arg.matches(pattern);
    }


    public static boolean isEmpty(String value) {
        if(value == null) return true;

        return value.isBlank();
    }
}
