package com.kkb.mybatis.framework.sqlnode;

import java.util.HashMap;
import java.util.Map;

/**
 * 1、用于拼接SqlNode解析之后的SQL片段
 * 2、用于传递解析SqlNode时需要的参数信息
 */
public class DynamicContext {
    private StringBuffer sb = new StringBuffer();

    private Map<String,Object> bindings = new HashMap<>();

    /**
     * 通过构造方法，注入入参信息
     * @param param
     */
    public DynamicContext(Object param) {
        this.bindings.put("_parameter", param);
    }

    public String getSql(){
        return sb.toString();
    }

    public void appendSql(String sqlText){
        this.sb.append(sqlText);
        this.sb.append(" ");
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }

}
