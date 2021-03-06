package com.seckill.entity;

import java.util.Date;

/**
 * Created by 18362 on 2017/10/14.
 */
public class Successkilled {
    private long seckillid;
    private long userphone;
    private short state;
    private Date createtime;//订单创建的时间
    //设计多对一的属性
    private Seckill seckill;

    public long getSeckillid() {
        return seckillid;
    }

    public void setSeckillid(long seckillid) {
        this.seckillid = seckillid;
    }

    public long getUserphone() {
        return userphone;
    }

    public void setUserphone(long userphone) {
        this.userphone = userphone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "Successkilled{" +
                "seckillid=" + seckillid +
                ", userphone=" + userphone +
                ", state=" + state +
                ", createtime=" + createtime +
                '}';
    }
}
