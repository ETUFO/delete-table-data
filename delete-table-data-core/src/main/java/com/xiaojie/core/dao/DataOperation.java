package com.xiaojie.core.dao;

import java.util.List;
import java.util.Map;

/**
 * 数据库操作
 *
 * @classname: DataBaseOperation
 * @author: wangye
 * @date: 2020/12/17 9:28
 **/
public interface DataOperation {

    List<Map> selectData(String tableName, String fields, String paramName, Object paramValue);

    List<Map> selectData(String tableName, String fields, List<Param> params);

    List<Map> selectAll(String tableName, String fields);

    int delete(String tableName, String paramName, Object paramValue);

    int delete(String tableName, List<Param> params);

}

