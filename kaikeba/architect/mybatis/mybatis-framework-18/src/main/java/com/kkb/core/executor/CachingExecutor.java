package com.kkb.core.executor;

import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;

import java.util.List;

public class CachingExecutor implements Executor {
    private Executor delegate;
    public CachingExecutor(Executor executor) {
        this.delegate = executor;
    }

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement ms, Object param) {
        //TODO ��������
        //���Դ�ms�л�ȡ���Ķ����������

        //���û�����ö����������������Ĵ�����

        return delegate.query(configuration, ms, param);
    }
}
