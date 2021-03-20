package com.practice.ch3;

import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicReference {
    static AtomicReference<UserInfo> ar=new AtomicReference<>();
    public static void main(String[] args){
        UserInfo userInfo = new UserInfo();//Ҫ�޸ĵ�ʵ���ʵ��
        ar.set(userInfo);//�����ö�����AtomicReference������
        System.out.println("original name:"+ar.get().GetName());
        UserInfo updateUserInfo = new UserInfo();//Ҫ�仯����ʵ��
        updateUserInfo.SetName("brian");
        ar.compareAndSet(userInfo,updateUserInfo);
        System.out.println("update name:"+ar.get().GetName());
        System.out.println("user object name:"+userInfo.GetName());
    }

    private static class UserInfo {
        private String _name="qiaochenxi";
        private String _age;
        public String GetName(){
            return this._name;
        }
        public void SetName(String name){
            this._name=name;
        }
    }
}
