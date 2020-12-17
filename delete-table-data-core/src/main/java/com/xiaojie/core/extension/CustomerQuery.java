package com.xiaojie.core.extension;

import java.util.List;
import java.util.Map;

/**
 * 自定义查询逻辑接口
 * @classname: CustomerQuery
 * @author: wangye
 * @date: 2020/12/17 10:40
 **/
public interface CustomerQuery {

    List<Map> get(String tableName, Map<String,Object> param);
}
