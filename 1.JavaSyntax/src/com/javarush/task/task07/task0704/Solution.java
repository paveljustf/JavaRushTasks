package com.javarush.task.task07.task0704;

/* 
Переверни массив
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) throws Exception {
        //напишите тут ваш код
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int [] a = new int[10];


        for (int i = 0; i < 10; i++) {
            int keyboard = Integer.parseInt(reader.readLine());
            a[i] = keyboard;
        }

        for (int i = 9; i >=0 ; i--) {
            System.out.println(a[i]);

        }
    }
}

