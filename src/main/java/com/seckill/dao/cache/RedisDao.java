package com.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
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
     *
     */
    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);//通过类名的反射机制，获取构建对象的Schema
    //通过字节码重构对象（针对的是的对象而不是int等数据类型）
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
                    String key="seckill:"+seckillid;//存储的键值
                /**
                 * Redis中没有实现序列化的基本操作，需要自己实现
                 * 注意序列化的方法对性能的影响（不用java中的Serializable的接口）
                 * 采用自定的序列化方法，高效。压缩时间小，压缩的数据量小，节省传输的带宽
                 * protostuff是效果最好的一款
                 */
                 byte []bytes=jedis.get(key.getBytes());//通过key的序列化值，获取对应的二进制序列值
                if(bytes!=null)
                {
                    //对应的数据存在
                    Seckill seckill=schema.newMessage();//构建一个新的对象
                    ProtobufIOUtil.mergeFrom(bytes,seckill,schema);//通过protostuff的工具类反序列化对象，参数序列化的参数，空对象，schma
                    return seckill;
                }


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
        //手动序列化的过程
        try {
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckill.getSeckillid();
                //调用Utils的方法序列化对象
                byte []bytes=ProtobufIOUtil.toByteArray(seckill,schema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //缓存数据，设置缓存的有效时间
                String result=jedis.setex(key.getBytes(),60*60,bytes);
                return result;
            }finally {
                jedis.close();
            }
        }
        catch (Exception e)
        {
            logger.error("error:"+e.getMessage());
        }
        return null;
    }
}
