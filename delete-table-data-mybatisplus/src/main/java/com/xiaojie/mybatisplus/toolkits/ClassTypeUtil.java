package com.xiaojie.mybatisplus.toolkits;

import java.util.Collection;

/**
 * @author wangye
 * @classname ClassTypeUtil
 * @date 2020/12/21 14:27
 **/
public class ClassTypeUtil {

    public static boolean isCollection(Object obj){
        return obj instanceof Collection;
    }
}
