package com.kkb.mybatis.homework;

import com.kkb.mybatis.po.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * ���Ӳ�������⣨properties�ļ���
 * properties�ļ��е����ݣ����ջᱻ�����ء���Properties������
 *
 * @author ���ղ
 */
public class week2_mybatis{
    private Properties properties = new Properties();

    // �����û�ID����ѯ�û���Ϣ
    @Test
    public void test(){
        // ���������ļ��е�����
        loadProperties("jdbc.properties");

        // �����û�ID��ѯ�û���Ϣ
        // select * from user where id = ?
        List<User> users = selectList("queryUserById",1);
        System.out.println(users);

        // �����û����Ʋ�ѯ�û���Ϣ
        // select * from user where username = ?
        List<User> users1 = selectList("queryUserByName", "ǧ������ɪ");
        System.out.println(users1);

        // �����û����ƺ��Ա��ѯ�û���Ϣ
        // select * from user where username = ? AND sex = ?

        Map map = new HashMap();
        map.put("username","ǧ������ɪ");
        map.put("sex","��");
        List<User> users2 = selectList("queryUserByParams", map);
        System.out.println(users2);
    }

    private <T> List<T> selectList(String statementId,Object param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        List<T> results = new ArrayList<>();
        try {
            // 1���������ݿ�����
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("db.driver"));
            dataSource.setUrl(properties.getProperty("db.url"));
            dataSource.setUsername(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));

            connection = dataSource.getConnection();

            // 3������sql��� ?��ʾռλ��
            String sql = properties.getProperty("db.sql."+statementId);

            // 4����ȡԤ���� statement
            preparedStatement = connection.prepareStatement(sql);

            // 5�����ò�������һ������Ϊ sql ����в�������ţ��� 1 ��ʼ�����ڶ�������Ϊ���õ�
//            preparedStatement.setObject(1,param);
            if (param instanceof Integer){
                preparedStatement.setObject(1,param);
            }else if (param instanceof String){
                preparedStatement.setObject(1,param);
            }else if (param instanceof Map){
                Map map = (Map) param;

                String columnnames = properties.getProperty("db.sql." + statementId + ".columnnames");
                String[] nameArray = columnnames.split(",");
                for (int i = 0 ; i<nameArray.length ;i++) {
                    String name = nameArray[i];
                    Object value = map.get(name);

                    preparedStatement.setObject(i+1,value);
                }
            }

            // 6�������ݿⷢ�� sql ִ�в�ѯ����ѯ�������
            rs = preparedStatement.executeQuery();

            // 7��������ѯ�����
            Object result = null;
            String resultType = properties.getProperty("db.sql." + statementId + ".resulttype");
            Class clazz = Class.forName(resultType);
            while (rs.next()) {
                Constructor constructor = clazz.getConstructor();
                result = constructor.newInstance();
                ResultSetMetaData metaData = rs.getMetaData();
                // ��������е�����
                int columnCount = metaData.getColumnCount();
                for (int i = 0;i<columnCount ;i++){
                    String columnName = metaData.getColumnName(i + 1);
                    // ����������ֵ
                    // ��ѯ����е�������Ҫӳ��Ķ��������������һ��
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result,rs.getObject(i+1));
                }

                results.add((T) result);
            }

            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8���ͷ���Դ
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void loadProperties(String location) {
        try {
            InputStream inputStream = week2_mybatis.class.getClassLoader().getResourceAsStream(location);
            properties.load(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //TODO �ͷ�io��
        }
    }
}
