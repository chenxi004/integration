package com.kkb.core.handler;

import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;
import com.kkb.core.sqlsource.BoundSql;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrepareStatementHandler implements StatementHandler {
    ParameterHandler defaultParameterHandler;
    ResultSetHandler resultSetHandler;

    public PrepareStatementHandler(Configuration configuration) {
        this.defaultParameterHandler = configuration.newParameterHandler();
        this.resultSetHandler=configuration.newResultSetHandler();;
    }


    @Override
    public Statement prepare(Connection connection, String sql) throws Exception {
        return connection.prepareStatement(sql);
    }

    @Override
    public void parameterize(Statement statement, Object param, BoundSql boundSql) throws Exception {
       //真正进行参数处理的是ParameterHandler
        defaultParameterHandler.setParameter(statement, param, boundSql);
    }

    @Override
    public <T> List<T> query(Statement statement, MappedStatement ms) throws Exception {
        List<T> results = new ArrayList<>();

        if (statement instanceof PreparedStatement){
            PreparedStatement preparedStatement = (PreparedStatement) statement;
            ResultSet rs = preparedStatement.executeQuery();

            results = resultSetHandler.handleResult(ms.getResultType(),rs);
        }
        return results;
    }

    ;
}
