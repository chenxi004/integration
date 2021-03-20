package com.kkb.core.factory;

import com.kkb.core.sqlsession.SqlSession;

public interface SqlSessionFactory {
    SqlSession openSqlSession();
}
