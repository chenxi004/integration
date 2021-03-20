package com.kkb.core.builder;

import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;
import com.kkb.core.sqlsource.SqlSource;
import org.dom4j.Element;

public class XMLStatementBuilder {
    Configuration configuration;
    public XMLStatementBuilder(Configuration config) {
        configuration=config;
    }

    public void parseStatement(String namespace, Element selectElement) {
        String id = selectElement.attributeValue("id");
        String statementId = namespace+"."+id;
        String resultType = selectElement.attributeValue("resultType");
        String statementType = selectElement.attributeValue("statementType");

        // ��װSqlSource����
        SqlSource sqlSource = createSqlSource(selectElement);

        // ��װMappedStatement
        MappedStatement mappedStatement = new MappedStatement(statementId,resultType,statementType,sqlSource);

        configuration.addMappedStatement(statementId,mappedStatement);
    }
    private SqlSource createSqlSource(Element selectElement) {
        XMLScriptBuilder scriptBuilder = new XMLScriptBuilder();
        SqlSource sqlSource = scriptBuilder.parseScriptNode(selectElement);
        return sqlSource;
    }
}
