package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeateKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 18362 on 2017/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})

public class SeckilllServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckilllService seckillService;//集成测试

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("List={}", list);//单元测试类
    }

    @Test
    public void getSeckillById() throws Exception {
        long seckillid = 1000;
        Seckill seckill = seckillService.getSeckillById(seckillid);
        logger.info("Seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillid = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(seckillid);
        logger.info("exposer={}", exposer);
    }

//    @Test
//    public void executeSeckill() throws Exception {
//        String md5="a94a663266e024773423f2a221e21dd5";//验证用户秒杀的接口地址是否和暴露的接口地址相同
//        long id=1000;
//        long userphone=18362971303L;
//        try {//在本省函数中try-catch，不用向上抛出给Junit4
//            SeckillExecution seckillExecution = seckillService.executeSeckill(id, userphone, md5);//返回执行秒杀的结果，或者返回正确值或者抛出相应的异常
//            logger.info("seckillExecution={}", seckillExecution);
//        }catch(SeckillCloseException e)
//        {
//            logger.error(e.getMessage());
//        }
//        catch(RepeateKillException e1)
//        {
//            logger.error(e1.getMessage());
//        }
//
//    }

    /**
     * 集成测试
     *
     * @throws Exception
     */
    @Test
    public void SeckillLogic() throws Exception {
        long seckillid = 1001;
        long userphone = 18362971301L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillid);
        if (exposer.getExposed() == true)//秒杀已开启
        {
            try {/**
             *在函数中try-catch，不用向上抛出给Junit4,通过try-catch手动捕捉异常，进行更友好的处理
             * 向上抛出给JUNIT处理类似向上抛出给JVM处理，出来的界面不友好~
             */
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillid, userphone, exposer.getMd5());//返回执行秒杀的结果，或者返回正确值或者抛出相应的异常
                logger.info("seckillExecution={}", seckillExecution);
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            } catch (RepeateKillException e1) {
                logger.error(e1.getMessage());
            }catch (SeckillException e2)
            {
                logger.error(e2.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }
    }

}