package com.seckill.dto;

import java.util.Date;

/**
 * 记录暴露及接口的返回值信息（一般是跟业务不相关的信息，在web层和服务层之间传输的数据)
 * 如果没有开启秒杀返回的值和未开启秒杀返回的值
 * Created by 18362 on 2017/10/17.
 */
public class Exposer {
   private Boolean exposed;//是否已经开门秒杀
    /**
     * 加密的一种机制md5
     */
    private String md5;
    /**
     * 秒杀商品的id
     */
    private Long seckillid;
    /**
     * 秒杀开启时间
     */
    private Date start;
    /**
     * 秒杀结束时间
     */
    private Date end;
    /**
     * 当前的系统时间，和浏览器时间对比，判断秒杀是否开启
     */
    private Date now;

    /**
     * 如果成功返回的是接口地址
     * @param exposed
     * @param md5
     * @param seckillid
     */
    public Exposer(Boolean exposed, String md5, Long seckillid) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillid = seckillid;
    }
    /**
     * 返回秒杀未开启的时候数据的对象
     */
    public Exposer(Boolean exposed, long seckillid, Date start, Date end, Date now) {
        this.exposed = exposed;
        this.seckillid=seckillid;
        this.start = start;
        this.end = end;
        this.now = now;
    }
    /**
     *
     */
    public Exposer(Boolean exposed, Long seckillid) {
        this.exposed = exposed;
        this.seckillid = seckillid;
    }

    public Boolean getExposed() {
        return exposed;
    }

    public void setExposed(Boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getSeckillid() {
        return seckillid;
    }

    public void setSeckillid(Long seckillid) {
        this.seckillid = seckillid;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillid=" + seckillid +
                ", start=" + start +
                ", end=" + end +
                ", now=" + now +
                '}';
    }
}
