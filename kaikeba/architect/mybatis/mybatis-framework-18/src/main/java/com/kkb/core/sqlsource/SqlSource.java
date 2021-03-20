package com.kkb.core.sqlsource;

public interface SqlSource {

    /**
     *
     * @param param 为了解析${}需要的参数
     * @return
     */
    BoundSql getBoundSql(Object param);
}
