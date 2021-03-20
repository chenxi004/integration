package com.chenxi.utils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chenxi.dc.StudentDo;
public class MySqlJdbc {
	// MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
 
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false&serverTimezone=UTC";//项目中用配置文件管理
 
 
    // 数据库的用户名与密码，需要根据自己的设置，项目中用配置文件管理
    static final String USER = "root";
    static final String PASS = "abcd@1234";
 
    public static<T> List<T> executeQuery(String sql,Class<T> clazz,HashMap<String, String> paras) {
    	ResultSet rs =null;
        Connection conn = null;
        Statement stmt = null;
        List<T> list = new ArrayList<T>();
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            //执行sql语句
            rs = stmt.executeQuery(sql);

            // 展开结果集数据库
			while(rs.next()){
				//调用 invokeObject方法，把一条记录封装成一个对象，添加到list中
                list.add(invokeObject(rs, clazz));
			}
			return list;
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
            return null;
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
            return null;
        }finally{
        	//关闭资源
        	close(conn,stmt, rs);
        }
        //System.out.println("Goodbye!");
    }
    
    

    /**
     * 把数据库中的一条记录通过反射包装成相应的Bean
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private static <T> T invokeObject(ResultSet resultSet, Class<T> clazz) throws IllegalAccessException, InstantiationException,
            SQLException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        T object = clazz.getDeclaredConstructor().newInstance();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 0, count = metaData.getColumnCount(); i < count; i++) {
            String columnName = metaData.getColumnName(i + 1);     //数据库返回结果的列名
            String fieldName = StringUtil.camelName(columnName); //去掉列名中的下划线“_”并转为驼峰命名
            Field field = clazz.getDeclaredField(fieldName);            //根据字段名获取field
            String methName = setMethodName(fieldName);         //拼set方法名
            Class type = field.getType();                       //获取字段类型
            Method setMethod = clazz.getDeclaredMethod(methName, field.getType());
            Object value = resultSet.getObject(i + 1);            //获取字段值
            setMethod.invoke(object, type.cast(value));       //强转并且赋值
        }
        return object;
    }
    private static String setMethodName(String str) {
        return "set" + StringUtil.firstUpperCase(str);
    }
    
    private static void close(Connection connection,Statement statement, ResultSet resultSet) {
    	try {
            if(resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
            	statement.close();
            }
            if (connection != null) {
            	connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
