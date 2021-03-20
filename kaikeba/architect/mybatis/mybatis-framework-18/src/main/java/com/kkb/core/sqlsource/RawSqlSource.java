package com.kkb.core.sqlsource;

import com.kkb.core.parser.SqlSourceParser;
import com.kkb.core.sqlnode.DynamicContext;
import com.kkb.core.sqlnode.SqlNode;

/**
 * 用于封装和解析不带有 ${}或者动态标签的SQL信息
 * SELECT * FROM user WHERE id = #{id}
 *
 * 被封装的SQL信息，只需要解析一次，就可以得到以下的SQL语句
 * SELECT * FROM user WHERE id = ?
 *
 * * 解析时机：
 *  * 1、构造的时候进行SQL解析（只会解析一次）
 *  * 2、每次调用getBoundSql的时候才会解析
 */
public class RawSqlSource implements SqlSource{
    private SqlSource staticSqlSource;
    public RawSqlSource(SqlNode rootSqlNode) {
        // 1.解析SqlNode中的所有节点信息，最终会组成一条SQL语句
        DynamicContext context = new DynamicContext(null);
        rootSqlNode.apply(context);
        // 2.解析#{}
        String sqlText = context.getSql();

        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        staticSqlSource = sqlSourceParser.parseSqlSource(sqlText);
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return staticSqlSource.getBoundSql(param);
    }
}
