package com.seckill.enums;

/**
 *
 * 表示秒杀状态的枚举常量值（尽量使用枚举去使用常量值)
 * Created by 18362 on 2017/10/18.
 */
public enum SeckillStatEnum {
    /**
     * 先定义部分枚举常量，如果需要其他枚举常量自己定义就可以
     */
    Success(1,"秒杀成功"),End(0,"秒杀结束"),Repeate(-1,"重复秒杀"),InnerError(-2,"系统内部错误"),Data_rewrite(-3,"数据篡改");
    private int state;//状态值
    private String stateinfo;//状态代表的量

    SeckillStatEnum(int state, String stateinfo) {
        this.state = state;
        this.stateinfo = stateinfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateinfo() {
        return stateinfo;
    }

    public void setStateinfo(String stateinfo) {
        this.stateinfo = stateinfo;
    }

    /**
     * 根据传入的整数参数，返回枚举变量
     * @param index
     * @return
     */
    private static SeckillStatEnum stateof(int index)
    {
        for(SeckillStatEnum senum:SeckillStatEnum.values())
        {
            if(senum.getState()==index)
                return senum;
        }
        return null;
    }
}
