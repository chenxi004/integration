package com.practice.ch5;

public class IntToBinary {
    public static void main(String[] args){
        //��λ��
        System.out.println("4��"+ Integer.toBinaryString(4));
        System.out.println("6��"+ Integer.toBinaryString(6));
        System.out.println("4&6��"+ Integer.toBinaryString(4&6));
        //��λ��
        System.out.println("4|6��"+ Integer.toBinaryString(4|6));
        //��λ��
        System.out.println("~4��"+ Integer.toBinaryString(~4));
        //��λ�ֻ�
        System.out.println("4^6��"+ Integer.toBinaryString(4^6));
        // <<�з������� >>�з��ŵ�����  >>>�޷�������

        //ȡģ�Ĳ��� a % (2^n) �ȼ��� a&(2^n-1)
        System.out.println("365%16��"+ Integer.toBinaryString(365%16) +" or "+Integer.toBinaryString(365&(16-1)));
    }
}
