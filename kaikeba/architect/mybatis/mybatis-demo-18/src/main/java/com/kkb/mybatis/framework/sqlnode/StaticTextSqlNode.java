package com.kkb.mybatis.framework.sqlnode;

/**
 * 存储不带有${}的SQL文本信息
 */
public class StaticTextSqlNode implements SqlNode{
    private String sqlText;

    public StaticTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        context.appendSql(sqlText);
    }
}
