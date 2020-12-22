package com.xiaojie.mybatisplus.dao;

import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QueryMapper {

    List<Map> selectByMaps(
            @Param("tableName")String tableName,
            @Param("fields")String fields,@Param("condition") Map condition);


    List<Map> selectAll(@Param("tableName")String tableName, @Param("fields")String fields);
}