package com.kkb.core.handler;

import com.kkb.core.sqlsource.BoundSql;

import java.sql.SQLException;
import java.sql.Statement;

public interface ParameterHandler {
    void setParameter(Statement statement, Object param, BoundSql boundSql) throws SQLException, Exception;
}
