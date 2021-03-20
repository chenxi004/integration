package com.kkb.mybatis.phase01.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kkb.mybatis.phase01.po.User;

public class UserDaoImpl implements UserDao {

	private SqlSessionFactory sqlSessionFactory;

	// 注入sqlSessionFactory
	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public User findUserById(int id) {
		// sqlsessionFactory工厂类去创建sqlsession会话
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// sqlsession接口，开发人员使用它对数据库进行增删改查操作
		User user = sqlSession.selectOne("test.findUserById", id);
		return user;
	}

}
