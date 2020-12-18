package com.xiaojie.core.exception;

import org.springframework.context.annotation.Bean;

/**
 * 获取不到Bean实例
 * @author wangye
 * @classname BeanNotFound
 * @date 2020/12/18 16:21
 **/
public class BeanNotFound extends  DeleteTableException {

    public BeanNotFound(){
        super();
    }

    public BeanNotFound(String message){
        super(message);
    }

    public BeanNotFound(String message,Throwable e){
        super(message,e);
    }
}
