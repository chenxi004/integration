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
	// MySQL 8.0 ���°汾 - JDBC �����������ݿ� URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
 
    // MySQL 8.0 ���ϰ汾 - JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false&serverTimezone=UTC";//��Ŀ���������ļ�����
 
 
    // ���ݿ���û��������룬��Ҫ�����Լ������ã���Ŀ���������ļ�����
    static final String USER = "root";
    static final String PASS = "abcd@1234";
 
    public static<T> List<T> executeQuery(String sql,Class<T> clazz,HashMap<String, String> paras) {
    	ResultSet rs =null;
        Connection conn = null;
        Statement stmt = null;
        List<T> list = new ArrayList<T>();
        try{
            // ע�� JDBC ����
            Class.forName(JDBC_DRIVER);
        
            // ������
            System.out.println("�������ݿ�...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            // ִ�в�ѯ
            System.out.println(" ʵ����Statement����...");
            stmt = conn.createStatement();
            //ִ��sql���
            rs = stmt.executeQuery(sql);

            // չ����������ݿ�
			while(rs.next()){
				//���� invokeObject��������һ����¼��װ��һ��������ӵ�list��
                list.add(invokeObject(rs, clazz));
			}
			return list;
        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
            return null;
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
            return null;
        }finally{
        	//�ر���Դ
        	close(conn,stmt, rs);
        }
        //System.out.println("Goodbye!");
    }
    
    

    /**
     * �����ݿ��е�һ����¼ͨ�������װ����Ӧ��Bean
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
            String columnName = metaData.getColumnName(i + 1);     //���ݿⷵ�ؽ��������
            String fieldName = StringUtil.camelName(columnName); //ȥ�������е��»��ߡ�_����תΪ�շ�����
            Field field = clazz.getDeclaredField(fieldName);            //�����ֶ�����ȡfield
            String methName = setMethodName(fieldName);         //ƴset������
            Class type = field.getType();                       //��ȡ�ֶ�����
            Method setMethod = clazz.getDeclaredMethod(methName, field.getType());
            Object value = resultSet.getObject(i + 1);            //��ȡ�ֶ�ֵ
            setMethod.invoke(object, type.cast(value));       //ǿת���Ҹ�ֵ
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
