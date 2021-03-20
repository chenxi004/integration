package com.kkb.core.factory;

import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;
import com.kkb.core.executor.Executor;
import com.kkb.core.sqlsession.DefaultSqlSession;
import com.kkb.core.sqlsession.SqlSession;

import java.util.List;

/**
 * ʹ�õ��ǹ����������ģʽ
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    Configuration configuration;
    public DefaultSqlSessionFactory(Configuration config) {
        configuration = config;
    }

    @Override
    public SqlSession openSqlSession(){
        Executor executor = configuration.newExecutor(null);
        return new DefaultSqlSession(configuration,executor);
    };
}
