package com.kkb.core.handler;

import com.kkb.core.cache.Cache;
import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;
import com.kkb.core.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class CallableStatementHandler implements StatementHandler {
    public CallableStatementHandler(Configuration configuration) {
    }

    @Override
    public Statement prepare(Connection connection, String sql) {
        return null;
    }

    @Override
    public void parameterize(Statement statement, Object param, BoundSql boundSql) {

    }

    @Override
    public <T> List<T> query(Statement statement, MappedStatement ms) {
        return null;
    }
}
