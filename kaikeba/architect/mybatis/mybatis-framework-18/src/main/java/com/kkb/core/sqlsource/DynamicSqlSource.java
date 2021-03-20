package com.kkb.core.sqlsource;

import com.kkb.core.parser.SqlSourceParser;
import com.kkb.core.sqlnode.DynamicContext;
import com.kkb.core.sqlnode.SqlNode;

/**
 * 用于封装和解析带有${}或者动态标签的一些SQL信息
 * SELECT * FROM user WHERE name = ${name}
 * 每一次根据参数的不同得到的sql不同
 * SELECT * FROM user WHERE name = zhangsan
 * SELECT * FROM user WHERE name = lisi
 *
 * 解析时机：
 * 1、构造的时候进行SQL解析（只会解析一次）
 * 2、每次调用getBoundSql的时候才会解析
 */
public class DynamicSqlSource implements SqlSource{
    private SqlNode rootSqlNode;

    public DynamicSqlSource(SqlNode rootSqlNode) {
        this.rootSqlNode = rootSqlNode;
    }

    /**
     * 每一次都需要重新解析sql语句
     * @param param 为了解析${}需要的参数
     * @return
     */
    @Override
    public BoundSql getBoundSql(Object param) {
        // 1.解析SqlNode中的所有节点信息，最终会组成一条SQL语句(解析${}或者动态标签)
        DynamicContext context = new DynamicContext(param);
        rootSqlNode.apply(context);
        // 2.解析#{}
        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        SqlSource sqlSource = sqlSourceParser.parseSqlSource(context.getSql());
        return sqlSource.getBoundSql(param);
    }
}
