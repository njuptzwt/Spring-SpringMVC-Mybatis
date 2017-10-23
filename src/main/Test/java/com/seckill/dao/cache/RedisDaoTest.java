package com.seckill.dao.cache;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.seckill.dao.SeckillDao;
import com.seckill.entity.Seckill;
import com.seckill.service.SeckilllService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by 18362 on 2017/10/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
/*
告诉Junit4 Spring配置文件的所在目录
 */
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    @Autowired
    private RedisDao redisDao;//依赖注入
    @Autowired
    private SeckillDao seckillDao;
    Long id = 1002L;

    @Test
    public void getSeckill() throws Exception {
        Seckill seckill = redisDao.getSeckill(id);//测试cache是否已经有缓存对象
        if (seckill == null) {
            seckill = seckillDao.queryById(id);//执行数据库操作
            String result = redisDao.putSeckill(seckill);//存储对象
            System.out.println(result);
            seckill = redisDao.getSeckill(id);
            System.out.println(seckill);
        }
    }

    @Test
    public void putSeckill() throws Exception {

    }

}