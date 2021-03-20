package com.kkb.core.sqlnode;

import com.kkb.core.utils.GenericTokenParser;
import com.kkb.core.utils.OgnlUtils;
import com.kkb.core.utils.SimpleTypeRegistry;
import com.kkb.core.utils.TokenHandler;

/**
 * 存储带有${}的SQL文本信息
 */
public class TextSqlNode implements SqlNode{
    private String sqlText;

    public TextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    public boolean isDynamic(){
        return sqlText.indexOf("${")> -1 ;
    }


    @Override
    public void apply(DynamicContext context) {

        // 通用分词解析器
        // openToken:
        // closeToken:
        // TokenHandler:被分出来的词要作何处理
        GenericTokenParser tokenParser = new GenericTokenParser("${","}",new BindingTokenHandler(context));

        // 使用通用分词解析器针对指定文本进行解析，解析之后得到JDBC可以执行的SQL语句
        String sql = tokenParser.parse(sqlText);

        context.appendSql(sql);
    }

    class BindingTokenHandler implements TokenHandler {

        private DynamicContext context;
        public BindingTokenHandler(DynamicContext context) {
            this.context = context;
        }

        /**
         * 解析${}
         * @param content
         * @return 参数的值（sql字符串拼接需要）
         */
        @Override
        public String handleToken(String content) {
            Object parameter = context.getBindings().get("_parameter");
            if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
                return parameter.toString();
            }
            // 非简单类型的使用OGNL表达式去获取指定参数的值
            Object value = OgnlUtils.getValue(content, parameter);

            return value == null ? "" : value.toString();
        }
    }
}
