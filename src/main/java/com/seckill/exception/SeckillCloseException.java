package com.seckill.exception;

/**
 * 秒杀结束的异常
 * Created by 18362 on 2017/10/17.
 */
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException() {
    }

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
