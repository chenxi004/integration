package com.kkb.core.config;

import com.kkb.core.executor.CachingExecutor;
import com.kkb.core.executor.Executor;
import com.kkb.core.executor.SimpleExecutor;
import com.kkb.core.handler.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于存储全局配置文件和映射文件中的数据
 */
public class Configuration {

    private DataSource dataSource;
    private boolean cachEnable=false;

    private Map<String,MappedStatement> mappedStatements = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public MappedStatement getMappedStatementById(String statementId) {
        return mappedStatements.get(statementId);
    }

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        this.mappedStatements.put(statementId,mappedStatement);
    }

    public Executor newExecutor(String executorType) {
        executorType = executorType==null||executorType.equals("")?"simple":executorType;
        Executor executor =null;

        if("simple".equals(executorType)){
            executor = new SimpleExecutor();//创建真正干活的执行器
        }
        //装饰器模式
        if(cachEnable){
            //创建二级缓存处理功能的执行器，但是该执行器还是要继续往下执行，得调用真正干活的执行器
            executor=new CachingExecutor(executor);
        }
        return executor;
    }

    public StatementHandler newStatementHandler(String statementType) {
        RoutingStatementHandler routingStatementHandler = new RoutingStatementHandler(statementType,this);
        return routingStatementHandler;
    }

    public ParameterHandler newParameterHandler() {
        DefaultParameterHandler defaultParameterHandler = new DefaultParameterHandler();
        return defaultParameterHandler;
    }

    public ResultSetHandler newResultSetHandler() {
        DefaultResultSetHandler defaultResultSetHandler = new DefaultResultSetHandler();
        return defaultResultSetHandler;
    }
}
