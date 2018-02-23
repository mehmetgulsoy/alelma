package com.alelma.dao;

import java.util.List;

public abstract class DataStore {
    public abstract <T> T castGeneratedValue(Class<T> type, Object obj);

    public abstract int insert(String sql, String[] genColNames,
                               Object[] genValues, Object... params) throws Exception;

    public abstract int execDML(String sql, Object... params) throws Exception;

    public abstract <T> T query(Class<T> type, String sql, Object... params) throws Exception;

    public abstract <T> List<T> queryList(Class<T> type, String sql, Object... params) throws Exception;

    public interface RowData {

        public abstract <T> T getValue(Class<T> type, String columnLabel) throws Exception;
    }

    public interface RowHandler<T> {

        abstract T handleRow(RowData rd) throws Exception;
    }

    public abstract <T> T queryDto(String sql, RowHandler<T> rowHandler,
                                   Object... params) throws Exception;

    public abstract <T> List<T> queryDtoList(String sql, RowHandler<T> rowHandler,
                                      Object... params) throws Exception;


}
