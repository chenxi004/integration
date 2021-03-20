package com.kkb.core.sqlsession;

import java.util.List;

public interface SqlSession {
    /**
     * ��ѯ��Ϣ����
     * @param statementId
     * @param param
     * @param <T>
     * @return
     */
    <T> List<T> selectList(String statementId,Object param);

    /**
     * ��ѯ������Ϣ
     * @param statementId
     * @param param
     * @param <T>
     * @return
     */
    <T> T selectOne(String statementId,Object param);
}
