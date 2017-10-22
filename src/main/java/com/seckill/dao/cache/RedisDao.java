package com.seckill.dao.cache;

import com.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *使用高并发的Cache缓存，Redis的缓存，支持键值对查询的非关系型数据库，
 * 键值对查询，效率高，轻量级
 * Created by 18362 on 2017/10/22.
 */
public class RedisDao {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());//构造日志记录
    private Jedis jedis;
    private JedisPool jedisPool;
    public RedisDao(String ip,int port)
    {
        jedisPool=new JedisPool(ip,port);//构造数据库的连接池
    }
    /**
     * 从Redis中反序列化得到对象
     */
    public Seckill getSeckill(Long seckillid)
    {
        /**
         * 缓存操作的逻辑，相当于是一个数据库的链接过程
         */
        try {
            Jedis jedis=jedisPool.getResource();//jedis相当于是connection,jedisPool相当于是数据库的连接池
            try {

            }finally {
                jedis.close();//关闭连接
            }
        }catch(Exception e)
        {
            logger.error("error:"+e.getMessage()
            );
        }
        return null;
    }
    /**
     * 序列化一个已知的对象到Redis中，序列化操作,返回的是字节的数组
     * 相当于map中的get()和put()操作
     */
    public String putSeckill(Seckill seckill)
    {
        return null;
    }
}
