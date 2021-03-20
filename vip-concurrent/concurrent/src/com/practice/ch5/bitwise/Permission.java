package com.practice.ch5.bitwise;
/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class Permission {
	
    // 是否允许查询，二进制第1位，0表示否，1表示是  
    private final static int RETRIEVE=1;
      
    // 是否允许新增，二进制第2位，0表示否，1表示是  
    private final static int INSERT=1<<1;
    // 是否允许修改，二进制第3位，0表示否，1表示是  
    private final static int UPDATE=1<<2;
      
    // 是否允许删除，二进制第4位，0表示否，1表示是  
    private final static int DELETE =1<<3;

    // 存储目前的权限状态  
    private int flag=0;
    //设置用户的权限
    public void SetPermission(int per){
        this.flag = per;
    }
    //增加用户的权限（1个或者多个）
    public void AddPermission(int per){
        this.flag |= per;
    }
    //删除用户的权限（1个或者多个）
    public void DeletePermission(int per){
        //this.flag ^= per;
        this.flag =this.flag&~per;
    }
    //判断用户的权限
    public boolean CheckPermission(int per){
        return (this.flag & per)==per;
    }
    //判断用户没有的权限
    public boolean CheckNoPermission(int per){
        return (this.flag & per)==0;
    }
  
    public static void main(String[] args) {
        int flag = (1<<4)-1;
        Permission permission = new Permission();
        permission.SetPermission(flag);
        permission.DeletePermission(DELETE|INSERT);
        System.out.println("select = "+permission.CheckPermission(RETRIEVE));
        System.out.println("update = "+permission.CheckPermission(UPDATE));
        System.out.println("insert = "+permission.CheckPermission(INSERT));
        System.out.println("delete = "+permission.CheckPermission(DELETE));
		
		
	}
}
