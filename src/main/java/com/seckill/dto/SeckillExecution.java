package com.seckill.dto;

import com.seckill.entity.Successkilled;
import com.seckill.enums.SeckillStatEnum;

/**
 * f封装执行秒杀的结果集合
 * Created by 18362 on 2017/10/17.
 */
public class SeckillExecution {
    private long seckillid;
    //秒杀的状态
    private int state;
    //状态的信息
    private String stateInfo;
    //对应的秒杀成功的对象
    private Successkilled successkilled;
    /**
     *
     * 如果秒杀成功返回的对象
     */
    public SeckillExecution(long seckillid, SeckillStatEnum seckillStatEnum, Successkilled successkilled) {
        this.seckillid = seckillid;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateinfo();
        this.successkilled = successkilled;
    }
    /**
     * 如果秒杀没有成功返回的对象
     */
    public SeckillExecution(long seckillid, SeckillStatEnum seckillStatEnum) {
        this.seckillid = seckillid;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateinfo();
    }

    public long getSeckillid() {
        return seckillid;
    }

    public void setSeckillid(long seckillid) {
        this.seckillid = seckillid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Successkilled getSuccesskilled() {
        return successkilled;
    }

    public void setSuccesskilled(Successkilled successkilled) {
        this.successkilled = successkilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillid=" + seckillid +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successkilled=" + successkilled +
                '}';
    }
}
