package com.xiaojie.core.exception;

/**
 * 删除表异常
 * @author wangye
 * @classname DeleteTableException
 * @date 2020/12/18 16:22
 **/
public class DeleteTableException extends RuntimeException {

    public DeleteTableException(){
        super();
    }

    public DeleteTableException(String message){
        super(message);
    }

    public DeleteTableException(String message,Throwable e){
        super(message,e);
    }
}
