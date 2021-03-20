package com.kkb.mybatis.test;


import com.kkb.core.builder.SqlSessionFactoryBuilder;
import com.kkb.core.factory.SqlSessionFactory;
import com.kkb.core.io.Resources;
import com.kkb.core.sqlsession.SqlSession;
import com.kkb.mybatis.po.User;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1.以面向对象的思维去改造mybatis手写框架 2.将手写的mybatis的代码封装一个通用的框架（java工程）给程序员使用
 *
 * @author 灭霸詹
 *
 */

public class MybatisV3 {
    private SqlSessionFactory sqlSessionFactory = null;
    @Before
    public void init(){
        //SqlSessionFactory的创建流程（完成Configration的封装流程）
        String location = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(location);
        sqlSessionFactory =new SqlSessionFactoryBuilder().build(is);
    }

    @Test
    public void test (){
        //SqlSession的执行流程（完成JDBC的执行流程）
        SqlSession session= sqlSessionFactory.openSqlSession();
        Map map = new HashMap();
        map.put("name","淘宝");
        map.put("alexa","13");
        List<User> users = session.selectList("test.queryUserByParams", map);
        System.out.println(users);
    }
}

