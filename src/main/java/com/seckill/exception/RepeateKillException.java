package com.seckill.exception;

/**
 * 定义重复秒杀的异常
 * 继承runtimeexception,支持事务的回滚操作(和秒杀相关的业务统一由SeckillException代替继承RuntimeException）
 * 编译期异常，事务不回滚
 * Created by 18362 on 2017/10/17.
 */
public class RepeateKillException extends SeckillException {
    public RepeateKillException() {
    }

    public RepeateKillException(String message) {
        super(message);
    }

    public RepeateKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
