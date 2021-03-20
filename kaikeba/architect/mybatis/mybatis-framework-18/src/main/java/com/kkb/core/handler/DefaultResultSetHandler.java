package com.kkb.core.handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetHandler implements ResultSetHandler {
    @Override
    public <T> List<T> handleResult(String resultType , ResultSet rs) throws Exception {
        List<Object> results=new ArrayList<>();
        Class clazz = Class.forName(resultType);
        while (rs.next()) {
            Constructor constructor = clazz.getConstructor();

            Object result = (T) constructor.newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            // ��������е�����
            int columnCount = metaData.getColumnCount();
            for (int i = 0;i<columnCount ;i++){
                String columnName = metaData.getColumnName(i + 1);
                // ����������ֵ
                // ��ѯ����е�������Ҫӳ��Ķ��������������һ��
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(result,rs.getObject(i+1));
            }

            results.add(result);
        }
        return (List<T>)results;
    }
}
