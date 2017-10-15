package com.seckill.entity;

import java.util.Date;

/**
 * Created by 18362 on 2017/10/14.
 */
public class Seckill {
    private long seckillid;
    private String name;
    private int number;
    private Date starttime;
    private Date  endtime;
    private  Date createtime;

    public long getSeckillid() {
        return seckillid;
    }

    public void setSeckillid(long seckillid) {
        this.seckillid = seckillid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date start_ime) {
        this.starttime = start_ime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillid=" + seckillid +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", start_ime=" + starttime +
                ", endtime=" + endtime +
                ", createtime=" + createtime +
                '}';
    }
}
