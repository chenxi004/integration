package com.kkb.mybatis.framework.sqlsource;

import java.util.List;

/**
 * 只是用来存储DynamicSqlSource和RawSqlSource解析之后的结果
 */
public class StaticSqlSource implements SqlSource{
    private String sql;

    private List<ParameterMapping> parameterMappings;

    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return new BoundSql(sql,parameterMappings);
    }
}
