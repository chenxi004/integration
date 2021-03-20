package com.kkb.core.builder;

import com.kkb.core.config.Configuration;
import com.kkb.core.io.Resources;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    Configuration configuration;
    public XMLMapperBuilder(Configuration config) {
        configuration=config;
    }

    /**
     *
     * @param rootElement <mapper></mapper>
     *
     */
    public void parseMapper(Element rootElement) {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElements = rootElement.elements("select");
        for (Element selectElement : selectElements) {
            XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
            statementBuilder.parseStatement(namespace,selectElement);
        }
    }
}
