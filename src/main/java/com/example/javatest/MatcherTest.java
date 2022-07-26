package com.example.javatest;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherTest {

    public static int count =0;
    public static void main(String[] args) {


        //0
        String pattern = "[\\,|\\s|\\w|\\)]*[\\w]+[\\s\\,\\)]";
        String tstr = "b,aaf,,ads ,,ccasd,, ";
        PatternPrint(pattern, tstr);

        //1
        pattern = "[\\,|\\s|\\w|\\)]*[\\w]+$\\s";
        tstr = "b,aaf,,ads ,,ccasd ";
        PatternPrint(pattern, tstr);

        //2
        pattern = "[\\,|\\s|\\w|\\)]*[\\w]+[\\s\\,\\)]";
        tstr = "b,aaf,,ads ,,ccasd,, ";
        PatternPrint(pattern, tstr);

        //3
        pattern = "[\\,|\\s|\\w|\\)]*[\\w]+[\\)]+[\\)]";
        tstr = "ReqPasswdChangeLogVo(password=asdc,,a, ,afsd21, changeFlag=1, currentPasswd=1213 123!))";
        PatternPrint(pattern, tstr);

        //4
        pattern = "[\\,|\\s|\\w|\\)\\!]+[\\s\\,\\)]+[\\s\\,\\)]";
        tstr = "ReqPasswdChangeLogVo(password=asdc,,a ,)) afsd21, changeFlag=1, currentPasswd=121,, sdf!3)) 123!)), currentPasswd2=121,, sdf!3))) 123!))";
        PatternPrint(pattern, tstr);

        //5
        pattern = "password=[^&]*";
        tstr = "password=qweqweqwe&qwe=qwe ";
        PatternPrint(pattern, tstr);

        //6
        pattern = "password=[^&]*";
        tstr = "password= o)) ,,234,23,2,! ))))password=asd d=qweqweqweqwe=qwe, password2= asdkavmlsdf,. asdf ))$$";
        PatternPrint(pattern, tstr);

        //7 숫자냐
        //7 숫자냐
        pattern = "[0-9]+";
        String arg = "a";
        System.out.println("#7 " + arg.matches(pattern));

        //
        final String REGEX = "[0-9]+";
        String test = "20201119173455-"; //년월일시분초

        if(test.matches(REGEX)) {
            System.out.println("숫자만 있습니다.");
        }else {
            System.out.println("숫자외에 값이 존재합니다.");
        }



    }
    public static void PatternPrint(String reg, String str){

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            System.out.println("#" +count + " " + matcher.group());
        }
            count++;
        System.out.println();
    }
}
