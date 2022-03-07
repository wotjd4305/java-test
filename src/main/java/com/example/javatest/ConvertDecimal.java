package com.example.javatest;

import java.util.Scanner;

public class ConvertDecimal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int integerNo;        // 변환하는 정수
        int cardinalNo;        // 기수
        int digitLength;    // 변환 후의 자릿수
        int retry;    // 재시도
        char[] charArray = new char[32];    // 변환 후 각 자리의 숫자를 넣어두는 문자의 배열

        System.out.println("10진수를 기수 변환합니다.");
        do{
            do{
                System.out.println("변환하는 음이 아닌 정수: ");
                integerNo = scanner.nextInt();
            }while(integerNo <0);

            do{
                System.out.println("어떤 진수로 변환할까요? (2~36) : ");
                cardinalNo = scanner.nextInt();
            }while(cardinalNo < 2 || cardinalNo > 36);
            // 10, 2, []
            digitLength = cardConverse(integerNo, cardinalNo, charArray);
            System.out.println(cardinalNo + "진수로는 ");
            for(int i= digitLength-1; i >=0; i--){
                System.out.print(charArray[i]);
            }
            System.out.println("입니다.");
            System.out.print("한번 더 할까요? (1.예 / 0.아니오) : ");
            retry = scanner.nextInt();
        }while(retry == 1);
    }


    // 정수값 x를 r진수로 변환하여 배열 d에 아랫자리부터 넣어두고 자릿수를 반환한다.
    static int cardConverse(int integerNo, int cardinalNo, char[] charArray){
        int digits = 0;
        String dchar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        do{
            // cardConverse(10, 2, [])
            // 10 % 2 = 0 , charArray[0] = 0, 10/2 = 5
            // 5 % 2 = 1      , charArray[1] = 1, 5/2 = 2
            // 2 % 2 = 0      , charArray[2] = 0, 2/2  = 1
            // 1 % 2 = 1      , charArray[3] = 1, 1/2  = 0
            charArray[digits++] = dchar.charAt(integerNo % cardinalNo);
            integerNo /= cardinalNo;
        }while(integerNo != 0);
        // digits = 4
        return digits;
    }

}
