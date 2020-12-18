package com.xiaojie.core.context;

import com.google.common.collect.Maps;
import lombok.Data;
import sun.reflect.generics.reflectiveObjects.LazyReflectiveObjectGenerator;
import sun.rmi.runtime.Log;

import java.util.Map;

/**
 * @author wangye
 * @classname DeleteLogContext
 * @date 2020/12/18 15:00
 **/
@Data
public class DeleteLogContext {

    private final static ThreadLocal<DeleteLogContext> local = new ThreadLocal<>();

    private String content;

    private Map<String,Object> tableMap = Maps.newHashMap();

    public static DeleteLogContext instance() {
        if (local.get() == null) {
            local.set(new DeleteLogContext());
        }
        return local.get();
    }

    public void remove() {
        local.remove();
    }
}
