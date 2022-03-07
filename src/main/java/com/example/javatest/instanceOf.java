package com.example.javatest;

public class instanceOf {
    public static void main(String[] args){
        A a = new A();
        B b = new B();

        System.out.println(a instanceof testIF);
        System.out.println(b instanceof testIF);
        System.out.println(a.LogString());
    }

    public static class A implements testIF{


        @Override
        public String LogString() {
            return "a - b";
        }
    }

    public static class B{

    }

    public interface testIF{
        public String LogString();
    }
}
