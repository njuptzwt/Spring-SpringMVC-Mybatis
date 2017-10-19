package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeateKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * 接口的设计应该站在客户的角度去思考问题‘
 * 三个方面要做好：1、设计接口的粒度2、参数尽可能的少3、返回值尽量要有好(return 或者是exception)
 * Created by 18362 on 2017/10/17.
 */
public interface SeckilllService {
    /*
    查询所有的秒杀产品记录
     */
     List<Seckill> getSeckillList();//接口
    /**
    通过id来查询对应的秒杀商品
     */
     Seckill getSeckillById(long id);
     /**
    (某个商品）秒杀地址的接口暴露，输出秒杀接接口的地址
     如果秒杀已经开启的话，那么返回秒杀的地址接口
     如果秒杀没有开启，那么需要返回秒杀活动的开启时间和结束时间
     （接口的返回对象不是业务实体类）
      */
    Exposer exportSeckillUrl(long seckillid);

    /**
     * 执行秒杀的接口，返回的是DTO对象
     * @param seckillid,产品的Id
     * @param userphone,用户的姓名
     * @param md5，验证秒杀的地址是否有效
     */
    SeckillExecution executeSeckill(long seckillid, long userphone, String md5) throws SeckillException,RepeateKillException,SeckillCloseException;
}
