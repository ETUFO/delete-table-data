package com.xiaojie.core.exception;

/**
 * 根据删除标签删除异常
 * @author wangye
 * @classname DeleteParamException
 * @date 2020/12/18 16:30
 **/
public class DeleteParamException extends DeleteTableException {

    public DeleteParamException(){
        super();
    }

    public DeleteParamException(String message){
        super(message);
    }

    public DeleteParamException(String message,Throwable e){
        super(message,e);
    }
}
