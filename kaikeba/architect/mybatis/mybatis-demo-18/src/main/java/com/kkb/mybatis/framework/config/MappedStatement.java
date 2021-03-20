package com.kkb.mybatis.framework.config;

import com.kkb.mybatis.framework.sqlsource.SqlSource;

/**
 * 存储映射文件中的crud标签的内容
 */
public class MappedStatement {
    private String id;

    private String resultType;

    private String statementType;

    private SqlSource sqlSource;

    public MappedStatement(String id, String resultType, String statementType, SqlSource sqlSource) {
        this.id = id;
        this.resultType = resultType;
        this.statementType = statementType;
        this.sqlSource = sqlSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }
}
