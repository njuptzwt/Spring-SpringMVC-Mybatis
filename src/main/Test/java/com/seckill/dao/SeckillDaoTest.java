package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 18362 on 2017/10/15.
 */
/*
配置springjunit4的整合，使得junit4运行的时候可以加载spring的容器进行进一步的测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
/*
告诉Junit4 Spring配置文件的所在目录
 */
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //注入数据库的依赖注入对象Dao(测试用)
    @Resource
    private SeckillDao seckillDao;//依赖注入数据库的对象，DaoImpl由Spring去完成。
    @Test
    public void queryById() throws Exception {
        long id=1001;
   Seckill seckill= seckillDao.queryById(id);
   System.out.println(seckill);
    }

    @Test
    public void reduceNumber() throws Exception {
        /*
        更新库存
         */
        Date killtime=new Date();
        int updatecount=seckillDao.reduceNumber(1001L,killtime);
        System.out.print("updatecount"+" "+updatecount);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> list=seckillDao.queryAll(0,100);
        for(Seckill seckill:list)
            System.out.println(seckill);
    }

}