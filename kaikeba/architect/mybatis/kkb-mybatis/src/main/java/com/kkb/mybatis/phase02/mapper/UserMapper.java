package com.kkb.mybatis.phase02.mapper;

import com.kkb.mybatis.phase01.po.User;

public interface UserMapper {
	User findUserById(int id);
}
