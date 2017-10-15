package com.seckill.dao;

import com.seckill.entity.Successkilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 18362 on 2017/10/14.
 */
public interface SuccesskilledDao {
    //插入购买明细,状态和创建时间可以在接口内实现,int类型的返回值数据代表更新或者插入记录的成功数量
    public int insertSuccessKilled(@Param("seckillid") long seckillid, @Param("userphone") long userphone,@Param("state") int state);
    //查询购买的明细表，通过id和seckill对象(通过seckillid可以查询到对象信息，多对一，一对多的关系
    public Successkilled queryByIdWithSeckill(@Param("seckillid") long seckillid,@Param("userphone") long userphone);//唯一确定一个主键的操作
}
