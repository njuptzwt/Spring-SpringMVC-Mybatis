package com.seckill.dao;

import com.seckill.entity.Successkilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

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
public class SuccesskilledDaoTest {
    @Autowired
    private SuccesskilledDao successkilledDao;//依赖注入，数据库对象注入Impl实现层次(给自己挖了一个大坑，@Autowired没写）
    @Test
    public void insertSuccessKilled() throws Exception {
        int insertcount=successkilledDao.insertSuccessKilled(1004L,18362971303L,0);
        System.out.println("insertcount"+" "+insertcount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        Successkilled successkilled=successkilledDao.queryByIdWithSeckill(1000L,18362971301L);
        System.out.println(successkilled.getSeckill());

    }

}