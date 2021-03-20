package com.kkb.mybatis.phase01.dao;

import com.kkb.mybatis.phase01.po.User;

public interface UserDao {

	User findUserById(int id);
}
