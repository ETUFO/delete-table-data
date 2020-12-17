package com.xiaojie.core.extension;

import java.util.Map;

/**
 * 自定义删除逻辑扩展接口
 * @author wangye
 * @classname CustomerDelete
 * @date 2020/12/17 10:41
 **/
public interface CustomerDelete {

    int delete(String tableName, Map<String,Object> param);
}
