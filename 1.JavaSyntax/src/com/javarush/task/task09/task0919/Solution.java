package com.javarush.task.task09.task0919;

/* 
Деление на ноль
*/

public class Solution {

    public static void main(String[] args) {

        try {
            divideByZero();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void divideByZero(){
        int a = 1/0;
        System.out.println(a);
    }
}
