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
        // ��ȡ�����ļ���Ӧ��Document����
        Document document = DocumentUtils.createDocument(inputStream);
        //ͨ���ַ�����װConfiguration
        XMLConfigBuilder configBuilder= new XMLConfigBuilder();
        configuration = configBuilder.parseCofiguration(document.getRootElement());
        return build(configuration);
    }
    public SqlSessionFactory build(Reader reader) {
        //ͨ���ַ�����װConfiguration
        Configuration configuration=null;
        return build(configuration);
    }
    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
