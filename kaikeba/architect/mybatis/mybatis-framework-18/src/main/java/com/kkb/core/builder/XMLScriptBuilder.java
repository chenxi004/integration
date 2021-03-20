package com.kkb.core.builder;

import com.kkb.core.sqlnode.*;
import com.kkb.core.sqlsource.DynamicSqlSource;
import com.kkb.core.sqlsource.RawSqlSource;
import com.kkb.core.sqlsource.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.List;

public class XMLScriptBuilder {

    private boolean isDynamic = false;
    public SqlSource parseScriptNode(Element selectElement) {
        // 1、解析动态标签，获取SqlNode信息
        SqlNode mixedSqlNode = parseDynamicTags(selectElement);
        // 2、封装SqlSource
        SqlSource sqlSource = null;
        if(isDynamic){
            sqlSource = new DynamicSqlSource(mixedSqlNode);
        }else{
            sqlSource = new RawSqlSource(mixedSqlNode);
        }
        return sqlSource;
    }
    private SqlNode parseDynamicTags(Element selectElement) {
        List<SqlNode> sqlNodes = new ArrayList<>();

        int nodeCount = selectElement.nodeCount();
        for(int i = 0 ; i< nodeCount ; i++){
            Node node = selectElement.node(i);
            if (node instanceof Text){
                String text = node.getText().trim();
                if (text.equals("")){
                    continue;
                }
                TextSqlNode textSqlNode = new TextSqlNode(text);
                if (textSqlNode.isDynamic()){
                    isDynamic = true;
                    sqlNodes.add(textSqlNode);
                }else{
                    sqlNodes.add(new StaticTextSqlNode(text));
                }

            }else if (node instanceof Element){
                // 此处就是动态标签的处理逻辑
                isDynamic = true;

                Element element = (Element) node;
                String name = element.getName();
                // TODO 使用策略模式进行优化
                if ("if".equals(name)){
                    String test = element.attributeValue("test");
                    SqlNode mixedSqlNode = parseDynamicTags(element);

                    SqlNode ifSqlNode = new IfSqlNode(test,mixedSqlNode);

                    sqlNodes.add(ifSqlNode);
                }else if("where".equals(name)){

                }
            }else{
                //...
            }
        }

        return new MixedSqlNode(sqlNodes);
    }
}
