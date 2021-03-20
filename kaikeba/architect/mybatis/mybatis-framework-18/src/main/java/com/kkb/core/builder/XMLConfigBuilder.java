package com.kkb.core.builder;

import com.kkb.core.config.Configuration;
import com.kkb.core.io.Resources;
import com.kkb.core.utils.DocumentUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;
    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parseCofiguration(Element rootElement) {
        Element environments = rootElement.element("environments");
        parseEnvironments(environments);

        Element mappers = rootElement.element("mappers");
        parseMappers(mappers);

        return configuration;
    }

    private void parseEnvironments(Element environments) {
        String aDefault = environments.attributeValue("default");

        List<Element> environmentList = environments.elements("environment");
        for (Element environment : environmentList) {
            String id = environment.attributeValue("id");
            if (aDefault.equals(id)){
                parseDataSource(environment);
            }
        }
    }

    private void parseDataSource(Element environment) {
        Element dataSource = environment.element("dataSource");
        String type = dataSource.attributeValue("type");
        if ("DBCP".equals(type)){
            BasicDataSource ds = new BasicDataSource();

            Properties properties = parseProperties(dataSource);

            ds.setDriverClassName(properties.getProperty("db.driver"));
            ds.setUrl(properties.getProperty("db.url"));
            ds.setUsername(properties.getProperty("db.username"));
            ds.setPassword(properties.getProperty("db.password"));

            configuration.setDataSource(ds);
        }
    }

    private Properties parseProperties(Element dataSource) {
        List<Element> propertyList = dataSource.elements("property");
        Properties properties = new Properties();
        for (Element property : propertyList) {
            String name = property.attributeValue("name");
            String value = property.attributeValue("value");
            properties.put(name,value);
        }
        return properties;
    }

    private void parseMappers(Element mappers) {
        List<Element> mapperList = mappers.elements("mapper");
        for (Element mapper : mapperList) {
            String resource = mapper.attributeValue("resource");

            // 获取配置文件对应的流对象
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 获取配置文件对应的Document对象
            Document document = DocumentUtils.createDocument(inputStream);
            // 按照指定的语义去解析Document文档对象
            XMLMapperBuilder MapperBuilder= new XMLMapperBuilder(configuration);
            MapperBuilder.parseMapper(document.getRootElement());
        }
    }
}
