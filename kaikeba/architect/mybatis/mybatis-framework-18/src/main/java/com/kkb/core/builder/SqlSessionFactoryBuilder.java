package com.kkb.core.builder;

import com.kkb.core.config.Configuration;
import com.kkb.core.factory.DefaultSqlSessionFactory;
import com.kkb.core.factory.SqlSessionFactory;
import com.kkb.core.sqlsession.SqlSession;
import com.kkb.core.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

public class SqlSessionFactoryBuilder {
    private Configuration configuration;
    public SqlSessionFactory build(InputStream inputStream) {
        // 获取配置文件对应的Document对象
        Document document = DocumentUtils.createDocument(inputStream);
        //通过字符流封装Configuration
        XMLConfigBuilder configBuilder= new XMLConfigBuilder();
        configuration = configBuilder.parseCofiguration(document.getRootElement());
        return build(configuration);
    }
    public SqlSessionFactory build(Reader reader) {
        //通过字符流封装Configuration
        Configuration configuration=null;
        return build(configuration);
    }
    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
