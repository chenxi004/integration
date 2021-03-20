package com.kkb.core.handler;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetHandler {
    <T> List<T> handleResult(String resultType, ResultSet rs) throws ClassNotFoundException, Exception;
}
