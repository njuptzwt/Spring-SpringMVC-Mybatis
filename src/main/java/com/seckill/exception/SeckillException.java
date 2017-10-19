package com.seckill.exception;

/**
 * 秒杀相关业务的所有异常类型
 * Created by 18362 on 2017/10/17.
 */
public class SeckillException extends RuntimeException {
    public SeckillException() {
    }

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
