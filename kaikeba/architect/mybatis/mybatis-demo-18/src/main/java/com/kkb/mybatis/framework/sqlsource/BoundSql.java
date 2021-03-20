package com.kkb.mybatis.framework.sqlsource;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装解析之后的JDBC可以执行的SQL语句和#{}解析出来的参数列表
 */
public class BoundSql {
    private String sql;

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void addParameterMapping(ParameterMapping parameterMapping) {
        this.parameterMappings.add(parameterMapping);
    }
}
