package com.seckill.dto;

/**
 * 封装Json类型的结果，和浏览器交互
 * Created by 18362 on 2017/10/18.
 */
public class SeckillResult<T> {
    private Boolean success;//请求是否成功

    private T data;

    private String error;
    /**
     * 返回成功是的消息，json数据
     */
    public SeckillResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }
    /**
     * 返回出现错误时候的数据
     */
    public SeckillResult(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
