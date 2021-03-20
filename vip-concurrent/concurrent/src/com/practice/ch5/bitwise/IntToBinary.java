package com.practice.ch5;

public class IntToBinary {
    public static void main(String[] args){
        //按位与
        System.out.println("4："+ Integer.toBinaryString(4));
        System.out.println("6："+ Integer.toBinaryString(6));
        System.out.println("4&6："+ Integer.toBinaryString(4&6));
        //按位或
        System.out.println("4|6："+ Integer.toBinaryString(4|6));
        //按位非
        System.out.println("~4："+ Integer.toBinaryString(~4));
        //按位抑或
        System.out.println("4^6："+ Integer.toBinaryString(4^6));
        // <<有符号左移 >>有符号的右移  >>>无符号右移

        //取模的操作 a % (2^n) 等价于 a&(2^n-1)
        System.out.println("365%16："+ Integer.toBinaryString(365%16) +" or "+Integer.toBinaryString(365&(16-1)));
    }
}
