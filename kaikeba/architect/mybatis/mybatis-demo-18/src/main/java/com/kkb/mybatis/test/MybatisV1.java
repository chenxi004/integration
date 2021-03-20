package com.kkb.mybatis.test;

import com.kkb.mybatis.po.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * 解决硬编码问题（properties文件）
 * properties文件中的内容，最终会被【加载】到Properties集合中
 *
 * @author 乔晨曦
 */
public class MybatisV1 {
    private Properties properties = new Properties();

    // 根据用户ID，查询用户信息
    @Test
    public void test(){
        // 加载配置文件中的内容
        loadProperties("jdbc.properties");

        // 根据用户ID查询用户信息
        // select * from user where id = ?
        List<User> users = selectList("queryUserById",1);
        System.out.println(users);

        // 根据用户名称查询用户信息
        // select * from user where username = ?
        List<User> users1 = selectList("queryUserByName", "淘宝");
        System.out.println(users1);

        // 根据用户名称和性别查询用户信息
        // select * from user where username = ? AND sex = ?

        Map map = new HashMap();
        map.put("name","淘宝");
        List<User> users2 = selectList("queryUserByParams", map);
        System.out.println(users2);
    }

    private <T> List<T> selectList(String statementId,Object param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        List<T> results = new ArrayList<>();
        try {
            // 1、加载数据库驱动
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("db.driver"));
            dataSource.setUrl(properties.getProperty("db.url"));
            dataSource.setUsername(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));

            connection = dataSource.getConnection();

            // 3、定义sql语句 ?表示占位符
            String sql = properties.getProperty("db.sql."+statementId);

            // 4、获取预处理 statement
            preparedStatement = connection.prepareStatement(sql);

            // 5、设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
            //preparedStatement.setObject(1,param);
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

            // 6、向数据库发出 sql 执行查询，查询出结果集
            rs = preparedStatement.executeQuery();

            // 7、遍历查询结果集
            Object result = null;
            String resultType = properties.getProperty("db.sql." + statementId + ".resulttype");
            Class clazz = Class.forName(resultType);
            while (rs.next()) {
                Constructor constructor = clazz.getConstructor();
                result = constructor.newInstance();
                ResultSetMetaData metaData = rs.getMetaData();
                // 结果集中列的数量
                int columnCount = metaData.getColumnCount();
                for (int i = 0;i<columnCount ;i++){
                    String columnName = metaData.getColumnName(i + 1);
                    // 给对象赋属性值
                    // 查询结果中的列名和要映射的对象的属性名必须一致
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
            // 8、释放资源
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
            InputStream inputStream = MybatisV1.class.getClassLoader().getResourceAsStream(location);
            properties.load(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}
