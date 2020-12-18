package com.xiaojie.core.context;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.util.List;
import java.util.Map;

/**
 * 删除数据
 * @author wangye
 * @classname DeleteContext
 * @date 2020/12/18 13:53
 **/
@Data
public class TableDataContext {
    /**
     *  保存查询数据
     */
    private static final ThreadLocal<TableDataContext> local = new ThreadLocal();

    private String fileName;

    private Map<String, List<Map>> data;

    public static TableDataContext instance(){
        if(local.get() == null){
            local.set(new TableDataContext());
        }
        return local.get();
    }

    public void remove(){
        local.remove();
    }
}
