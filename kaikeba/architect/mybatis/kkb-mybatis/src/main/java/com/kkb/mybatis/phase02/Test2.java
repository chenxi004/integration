package com.kkb.mybatis.phase02;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.kkb.mybatis.phase01.po.User;
import com.kkb.mybatis.phase02.mapper.UserMapper;

/**
 * 测试基础应用案例
 * 
 * @author think
 *
 */
public class Test2 {
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws Exception {
		// 加载全局配置文件（同时把映射文件也加载了）
		String resource = "phase02/SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// sqlsessionFactory需要通过sqlsessionFactoryBuilder读取全局配置文件信息之后
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testFindUserById() {
		//创建UserMapper对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//获取mapper接口的代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		//调用UserMapper对象的API
		User user = mapper.findUserById(1);
		System.out.println(user);
	}

}
