package ru.ospos.npf;

import java.util.HashMap;
import java.util.TreeMap;

public class TestMain {

    static String hello = "hello";

    static void change(String s) {
        s = "world";
    }

    class A {

        String s1 = "a";

        void p() {
            System.out.println(s1.length());
        }

        A() {
            p();
        }
    }

    class B extends A {

        String s1 = "bb";

        void p() {
            System.out.println((String)null);
            System.out.println(s1.length());
        }

    }

    private static void m1(byte a) {
        System.out.println(a);
    }
    private static void m1(short a) {
        System.out.println(a);
    }
    private static void m1(int a) {
        System.out.println(a);
    }
    private static void m1(long a) {
        System.out.println(a);
    }

    public static void main(String[] args) {

        System.out.println(hello);
        change(hello);
        System.out.println(hello);

        byte a = 5;
        short b = Short.MAX_VALUE - 1;
        int c = Integer.MAX_VALUE - 1;
        long d = Long.MAX_VALUE;


        m1(a+b);
        m1(b+c);
        m1(c+d);

        //HashMap
        var tm = new TreeMap<Long, String>();

        tm.put(1L, "It's one");
        tm.put(3L, "It's three");
        var tm1 = tm.floorEntry(2L);
        var tm2 = tm.ceilingEntry(2L);



        new TestMain().new B();
    }

    private static boolean c() {
        return false;

    }
}