package com.kkb.mybatis.framework.sqlnode;

import java.util.List;

/**
 * 存储同级别下的多个SqlNode节点信息，方便统一处理
 */
public class MixedSqlNode implements SqlNode{
    private List<SqlNode> sqlNodes;

    public MixedSqlNode(List<SqlNode> sqlNodes) {
        this.sqlNodes = sqlNodes;
    }

    @Override
    public void apply(DynamicContext context) {
        for (SqlNode sqlNode : sqlNodes) {
            sqlNode.apply(context);
        }
    }
}
