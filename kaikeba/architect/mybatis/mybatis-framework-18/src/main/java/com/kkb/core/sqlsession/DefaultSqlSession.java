package com.kkb.core.sqlsession;

import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;
import com.kkb.core.executor.Executor;

import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Executor executor;
    public DefaultSqlSession(Configuration config, Executor executor) {
        configuration= config;
        this.executor = executor;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        MappedStatement ms = configuration.getMappedStatementById(statementId);
        return executor.query(configuration,ms,param);
    }

    @Override
    public <T> T selectOne(String statementId, Object param) {
        List<T> list = this.selectList(statementId,param);
        if(list!=null&&list.size()==1){
            return list.get(0);
        }
        else{
            return null;
        }
    }
}
