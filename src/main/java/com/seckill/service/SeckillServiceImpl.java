package com.seckill.service;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccesskilledDao;
import com.seckill.dao.cache.RedisDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.Successkilled;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeateKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import sun.awt.windows.ThemeReader;

import java.util.Date;
import java.util.List;

/**
 * @service声明该配置是一个service层的服务
 * Created by 18362 on 2017/10/17.
 */
@Service
public class SeckillServiceImpl implements  SeckilllService{

    private Logger logger= LoggerFactory.getLogger(this.getClass());//使用sl4j的日志功能（使用方法)

    @Autowired//@Reponsity,@inject
    private SeckillDao seckillDao;
    /**
     *依赖注入数据库的对象
     */
    @Autowired
    private SuccesskilledDao successkilledDao;
    @Autowired
    private RedisDao redisDao;//缓存的redis操作的数据库对象

    /**
     * 加入混淆的概念，引入随机的盐值
     * @return
     */
    private String slat="sadhjshjhhjs^&57*o)()908&76&8ddshf";

    public List<Seckill> getSeckillList() {
        List<Seckill>list =seckillDao.queryAll(0,100);
        return list;
    }

    public Seckill getSeckillById(long id) {
        Seckill seckill=seckillDao.queryById(id);
        return seckill;
    }

    /**
     * 构造某个秒杀产品的唯一md5值
     * @param seckillid
     * @return
     */
    private String getmd5(long seckillid)
    {
        String base=seckillid+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 获取每一个商品的暴露接口地址，没有到达时间，到达时间，超过时间，返回的结果
     * 通过缓存机制将每一次查询的数据库对象放到Cache中，这样当用户每一次刷新页面的时候
     * 不必访问数据库，而是访问缓存！提高数据库的性能~。Nosql的机制
     * @param seckillid
     * @return
     */
    public Exposer exportSeckillUrl(long seckillid) {
        /**
         * 优化点，访问缓存优化
         */
        Seckill seckill= redisDao.getSeckill(seckillid);//看缓存中是否有数据
        if(seckill==null) {
            //如果缓存中没有数据，从数据库中获取，并写入缓存中
            seckill = seckillDao.queryById(seckillid);//缓存操作的对象
            if (seckill == null)
                return new Exposer(false, seckillid);
            else
                redisDao.putSeckill(seckill);//如果数据库中有对象，写入对象
        }
        Date starttime=seckill.getStarttime();
        Date endtime=seckill.getEndtime();
        Date nowtime=new Date();//系统当前时间
        /**
         * 如果秒杀未开启返回一个exposer是false的结果
         * 如果秒杀已经结束，返回false结果，并返回相应的数值
         */
        if(nowtime.getTime()<starttime.getTime()||endtime.getTime()<nowtime.getTime())
            return new Exposer(false,seckillid,starttime,endtime,nowtime);
       String md5=getmd5(seckillid);

        return new Exposer(true,md5,seckillid);
    }
    @Transactional
    /**
     * 只有该方法需要使用事务管理机制去控制（高并发性能）
     * 注解事务管理机制的好处
     * 1、工程师之间的约定，看到注解理解这个操作是需要事务控制的
     * 2、保证事务执行方法的时间一定要尽可能短，尽量不要插入网络操作等耗时的操作（针对干净的数据库纯操作）
     * 3、并不是每一个接口都需要事务管理
     */
    public SeckillExecution executeSeckill(long seckillid, long userphone, String md5) throws SeckillException, RepeateKillException, SeckillCloseException {
        /**
         * 应该在执行该方法的时候使用事务的操作方法
         * 当抛出任何的(RunTime运行期异常的时候）--------本方法中抛出4类异常
         * Spring的事务会回滚，保证事务的一致性
         * 首先判断是否md5值相同，如果不存在或者不相同
         * 那么说明用户篡改了眼访问的地址
         */
        if(md5==null||!md5.equals(getmd5(seckillid)))
        {
            throw new SeckillException("url has benn rewrited");//url被篡改
        }
        /**
         * 如果秒杀地址有效的话，那么用户可以开始秒杀
          */
        Date killtime=new Date();
        try {
            /**
             * 将判断逻辑放在try-catch中，预防seckill内部抛出数据库超时连接的错误（非已知的错误)
             * 没有用throw抛出的异常由catch自动捕捉，当程序抛出第一个异常，就执行catch语句，后面函数在运行
             * catch语句的分支，先处理已知异常，然后最后处理同意的异常
             *
             */
        int updateCount=seckillDao.reduceNumber(seckillid,killtime);
        /**
         * 判断逻辑，如果是数据库操作成功数据.0如果不成功<=0
         */
        if(updateCount<=0)
        {
            throw new SeckillCloseException("seckill has been closed");//如果没有更新成功，秒杀已经结束。库存没了，或者时间到了
        }
        else
        {
            int insertCount=successkilledDao.insertSuccessKilled(seckillid,userphone,1);
            //唯一的秒杀记录，主键seckillid+userphone,如果主键冲突，那么会返回0，成功返回1
            if(insertCount<=0)
                throw new RepeateKillException("repeate seckill operation");
            else
            {
                //如果执行秒杀成功，应该返回秒杀成功的信息
                Successkilled successkilled=successkilledDao.queryByIdWithSeckill(seckillid,userphone);
                return new SeckillExecution(seckillid,SeckillStatEnum.Success,successkilled);
            }
        }
        }
        //处理异常的分支1
        catch(SeckillCloseException e1)
        {
            throw e1;//抛出异常给web层，进一步处理（JSon数据格式和Enum枚举类型的使用
        }
        //处理异常的分支2,抛出异常给调用者（web层的调用者)
        catch (RepeateKillException e2)
        {
            throw e2;
        }
        //统一的异常处理，非自定义的异常，由他统一处理
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            //将所有的编译期异常转化为运行期异常，try语句块中发生的异常，都会经过这
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }
}
