package com.kkb.core.executor;

import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;
import com.kkb.core.handler.StatementHandler;
import com.kkb.core.sqlsource.BoundSql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor extends BaseExecutor {

    @Override
    protected <T> List<T> queryFromDataBase(Configuration configuration, MappedStatement ms, BoundSql boundSql, Object param) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        List<T> results = new ArrayList<>();

        try {
            // 获取连接
            connection = getConnection(configuration);
            // 获取StatementType,创建Statement
            StatementHandler statementhandler = configuration.newStatementHandler(ms.getStatementType());
            statement = statementhandler.prepare(connection,boundSql.getSql());
            // 设置参数
            statementhandler.parameterize(statement,param,boundSql);
            // 执行Statement
            results = statementhandler.query(statement,ms);
            // 结果映射
            return results;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 8、释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private Connection getConnection(Configuration configuration) throws Exception{
        DataSource dataSource = configuration.getDataSource();
        return dataSource.getConnection();
    }

}
