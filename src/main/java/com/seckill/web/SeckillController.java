package com.seckill.web;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeateKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckilllService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


/**
 * 控制层的作用，浏览器跳转到哪个页面。执行Service层的方法，逻辑控制
 * url的规范  url:模块/资源/{id}/细分   如：seckill/list/  seckill/{seckillid}/execution
 * Created by 18362 on 2017/10/18.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    /**
     * 日志输出
     */
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckilllService seckillService;//集成测试
    /**
     * 获取列表页展示
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String List(Model model)
    {
        List<Seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }
    /**
     * 按照id来返回需要的实体类
     */
    @RequestMapping(value = "/{seckillid}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillid") Long seckillid,Model model)
    {
        /**
         * 对象属性才有空值Long.Integer,Double,Float类属性，使用装盒拆盒思想
         */
        if(seckillid==null)
        {
            return "redirect: /seckill/list";
        }
        Seckill seckill=seckillService.getSeckillById(seckillid);
        if(seckill==null)
        {
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }
    /**
     * ajax的JSON数据类型
     * 需要用@ResponseBody的注解让浏览器将数据类型转换成JSON数据类型和前端共享数据
     * 暴露接口返回一个json对象说明秒杀地址是否有效
     * SeckillResult是封装的新类型，用于和前端页面交换数据使用的JSON数据格式
     *点击列表页的链接的时候，执行的操作，显示秒杀地址（根据时间判断，没有达到时间，倒计时，到达时间，给出秒地址，超出时间，显示超出时间信息
     * DTO对象在业务逻辑中扮演了很重要的角色
     * 除了返回JSP格式,后台还可以像浏览器传递JSON数据格式和XML数据
     * 使用注解的方式
     */
    @RequestMapping(value = "/{seckillid}/exposer", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillid") Long Seckillid)
    {
        SeckillResult<Exposer> seckillResult;
        try
        {
            Exposer exposer=seckillService.exportSeckillUrl(Seckillid);//封装接口暴露地址的信息，是否开启等。。
            seckillResult= new SeckillResult<Exposer>(true,exposer);//执行语句过程中没有出错，返回接口地址信息，是否开启等信息
        }catch(Exception e)
        {
            logger.error(e.getMessage());
            seckillResult=new SeckillResult<Exposer>(false,e.getMessage());//执行出错的时候
        }

        return seckillResult;//最终返回该对象和前端交互//JSON数据的格式
    }
    /**
     * 执行秒杀操作的核心层，返回的是ExecutionResult,JSON数据格式和前端交互
     */
    @RequestMapping(value = "/{seckillid}/{md5}/execution",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<SeckillExecution> execution(@PathVariable("seckillid") long seckillid, @PathVariable("md5") String md5,
                                                     @CookieValue(value = "userphone",required = false)Long userphone)//Cookie值从request获取用户号码
    {
        /**
         * 使用cookie默认为false当传递数据的时候由后台进行检查
         */
        if(userphone==null)
        {
            return new SeckillResult<SeckillExecution>(false,"用户没有注册");
        }
        /**
         * 业务处理的过程，返回结果给前端，异常或者正确结果，使用JSON数据的好处，大大减小了传递的数据量
         */
        try
        {
            SeckillExecution seckillExecution=seckillService.executeSeckill(seckillid,userphone,md5);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);//传递正确值，返回秒杀结果
        }catch(SeckillCloseException e1)
        {
            //返回秒杀已经关闭的异常，相当于是做判断，哪个错了
            logger.error("error message:" + e1.getMessage());
            SeckillExecution seckillExecution=new SeckillExecution(seckillid, SeckillStatEnum.End);//秒杀关闭
            return new SeckillResult<SeckillExecution>(false,seckillExecution);//返回秒杀的执行结果(统一的DTO传输数据,JSON数据格式)
        }catch (RepeateKillException e2)
        {
            logger.error("error message:" + e2.getMessage());
            SeckillExecution seckillExecution=new SeckillExecution(seckillid, SeckillStatEnum.Repeate);//重复秒杀
            return new SeckillResult<SeckillExecution>(false,seckillExecution);//返回秒杀的执行结果(统一的DTO传输数据,JSON数据格式)
        }
        catch(SeckillException e3)
        {
            logger.error("error message:" + e3.getMessage());
            SeckillExecution seckillExecution=new SeckillExecution(seckillid, SeckillStatEnum.InnerError);//内部错误
            return new SeckillResult<SeckillExecution>(false,seckillExecution);//返回秒杀的执行结果(统一的DTO传输数据,JSON数据格式)
        }
    }
    /**
     * 获取系统时间的函数
     */
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    public SeckillResult<Long> time(){
        Date now=new Date();
      return new SeckillResult(true,now.getTime());//值得注意的是web层的接口返回的数据都用同一种类型来处理DTO数据！！！然后使用JSON格式和前端通信！！
    }

}
