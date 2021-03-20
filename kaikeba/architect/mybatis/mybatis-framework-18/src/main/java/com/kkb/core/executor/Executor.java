package com.kkb.core.executor;

import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;

import java.util.List;

public interface Executor {
    <T>List<T> query(Configuration configuration, MappedStatement ms, Object param);
}
