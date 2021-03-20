package com.kkb.mybatis.framework.sqlnode;

import com.kkb.mybatis.framework.utils.OgnlUtils;

/**
 * 存储带有${}的SQL文本信息
 */
public class IfSqlNode implements SqlNode{
    private String test;

    private SqlNode mixedSqlNode;

    public IfSqlNode(String test, SqlNode mixedSqlNode) {
        this.test = test;
        this.mixedSqlNode = mixedSqlNode;
    }

    @Override
    public void apply(DynamicContext context) {
        Object parameter = context.getBindings().get("_parameter");
        boolean aBoolean = OgnlUtils.evaluateBoolean(test, parameter);
        if (aBoolean){
            mixedSqlNode.apply(context);
        }
    }
}
