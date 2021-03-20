package com.kkb.core.parser;

import com.kkb.core.sqlsource.SqlSource;
import com.kkb.core.sqlsource.StaticSqlSource;
import com.kkb.core.utils.GenericTokenParser;
import com.kkb.core.utils.ParameterMappingTokenHandler;

public class SqlSourceParser {
    public SqlSource parseSqlSource(String sqlText){
        // 分词处理器（如何处理#{}中的内容）
        // 思路：就是将#{}中的内容封装成ParameterMapping对象，并且放入List集合中
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();

        // 通用分词解析器
        // openToken:
        // closeToken:
        // TokenHandler:被分出来的词要作何处理
        GenericTokenParser tokenParser = new GenericTokenParser("#{","}",tokenHandler);

        // 使用通用分词解析器针对指定文本进行解析，解析之后得到JDBC可以执行的SQL语句
        String sql = tokenParser.parse(sqlText);

        System.out.println(sql);
        // 3.将解析出来的SQL语句和参数列表封装到StaticSqlSource中
        return new StaticSqlSource(sql,tokenHandler.getParameterMappings());
    }
}
