package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by 18362 on 2017/10/14.
 */
public interface SeckillDao {
    //根据id返回对象
    public Seckill queryById(@Param("seckillid") long seckillid);
    //减少库存,库存减少的同时，要传入秒杀的时间，记录，同时需要传入id说明哪个库存少了
    /*
    java没有保存形参名字的记录，当有多个参数时候解析为(arg0,arg1.....)，
    所以为了对应到sql语句中能被数据库识别，需要
    用@Param来标注形参的名字
     */
    public int reduceNumber(@Param("seckillid") long seckillid, @Param("killtime") Date killtime);
    //根据下标值去选择所有需要查询的对象
    public List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

}
