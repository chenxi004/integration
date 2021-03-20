package com.kkb.core.executor;

import com.kkb.core.cache.PurpetualCache;
import com.kkb.core.config.Configuration;
import com.kkb.core.config.MappedStatement;
import com.kkb.core.sqlsource.BoundSql;
import com.kkb.core.sqlsource.SqlSource;

import java.util.List;
//抽象出一级缓存的处理逻辑
public abstract class BaseExecutor implements Executor {
    private PurpetualCache cache = new PurpetualCache();
    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement ms, Object param) {

        //先从一级缓存中获取数据
        SqlSource sqlSource = ms.getSqlSource();
        BoundSql boundSql = sqlSource.getBoundSql(param);
        String key = CreateCacheKey(ms.getId(),boundSql.getSql(),param.toString());
        List<T> list = (List<T>)cache.get(key);
        //没有再查询数据库
        if(list == null){
            list = queryFromDataBase(configuration,ms,boundSql,param);
            cache.put(key,list);
        }

        return list;

    }

    //模板方法设计模式
    protected abstract <T> List<T> queryFromDataBase(Configuration configuration, MappedStatement ms, BoundSql boundSql, Object param);

    private String CreateCacheKey(String id, String sql,String paramstring) {
        return id+"-"+sql+"-"+paramstring;
    }
}
