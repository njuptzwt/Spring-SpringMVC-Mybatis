<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--静态包含公共页面，实现的效果是可以把包含的整个页面的内容加载到该页面的jsp代码中-->
    <!--动态包含是指多个Servlet的最终运行结果饭囊在一起-->
    <title>秒杀产品的详情页</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="pannel-heading">
            <h1>${seckill.name}</h1>
        </div>

        <div class="panel-body">
            <h2 class="text-danger">
                <%--显示time图标--%>
                <span class="glyphicon glyphicon-time"></span>
                <%--展示倒计时--%>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>
<%--登录弹出层 输入电话--%>
<div id="killPhoneModal" class="modal fade">

    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"> </span>秒杀电话:
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="填写手机号^o^" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <%--验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"> </span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>

        </div>
    </div>

</div>
<<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%--jQuery Cookie操作插件--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>


<script type="text/javascript">
    //javaScript的触发函数，加载的时候自动加载函数,EL表达式
    $(function () {
        //使用EL表达式传入参数
        seckill.detail.init({
            seckillid:${seckill.seckillid},
            starttime:${seckill.starttime.time},//毫秒
            endtime:${seckill.endtime.time}
        });
    })
</script>

<script type="text/javascript">

    //存放主要交互逻辑的js代码
    // javascript 模块化(package.类.方法)
    var seckill = {
        //封装秒杀相关ajax的url
        URL: {
            //
            now: function () {
                return  "/seckill/time/now";
            },
            exposer: function (seckillId) {
                return '/seckill/' + seckillId + '/exposer';
            },
            execution: function (seckillId, md5) {
                return  '/seckill/' + seckillId + '/' + md5 + '/execution';
            }
        },

        /*执行秒杀*/
        handlerSeckill: function (seckillId, node) {
            node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
            $.get(seckill.URL.exposer(seckillId), {}, function (result) {
                console.log("---exposer:"+seckill.URL.exposer(seckillId));
                //在回调函数中，执行交互流程
                if (result && result['success']) {
                    var exposer = result['data'];
                    if (exposer['exposed']) {
                        //开启秒杀
                        //获取秒杀地址
                        var md5 = exposer['md5'];
                        console.log("---md5:" + md5);
                        var killUrl = seckill.URL.execution(seckillId, md5);
                        console.log("---killUrl:" + killUrl);
                        //绑定一次点击事件
                        $('#killBtn').one('click', function () {
                            //执行秒杀请求
                            //1.先禁用按钮
                            $(this).addClass('disabled');
                            //2.发送请求(没有发送出去...执行出错)
                            $.post(killUrl, {}, function (result) {
                                console.log('result:' + result['data']);//浏览器调试
                                if (result && result['success']) {
                                    var killResult = result['data'];
                                    var state = killResult['state'];
                                    var stateInfo = killResult['stateInfo'];
                                    //3.显示秒杀结果
                                    node.html('<span class="label label-success">' + stateInfo + '</span>');
                                }
                            });
                        });
                        node.show();
                    } else {
                        //未开启秒杀
                        var now = exposer['now'];
                        var start = exposer['start'];
                        var end = exposer['end'];
                        //重新计算计时逻辑
                        seckill.countdown(seckillId, now, start, end);
                    }
                } else {
                    console.log('result:' + result);//浏览器调试
                }
            })
        },

        //验证手机号
        validatePhone: function (phone) {
            if (phone && phone.length == 11 && !isNaN(phone)) {
                return true;//直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
            } else {
                return false;
            }
        },

        //计时显示
        countdown: function (seckillId, nowTime, startTime, endTime) {
            var seckillBox = $('#seckill-box');
            //时间判断
            if (nowTime > endTime) {
                //秒杀结束
                seckillBox.html('秒杀结束！');
            } else if (nowTime < startTime) {
                //秒杀未开始，计时时间绑定
                var killTime = new Date(startTime + 1000);
                seckillBox.countdown(killTime, function (event) {
                    //时间格式
                    var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                    seckillBox.html(format);
                }).on('finish.countdown', function () {
                    //获取秒杀地址，控制现实逻辑，执行秒杀
                    seckill.handlerSeckill(seckillId, seckillBox);
                })
            } else {
                //秒杀开始
                seckill.handlerSeckill(seckillId, seckillBox);
            }
        },

        //详情页秒杀逻辑
        detail: {
            //详情页初始化
            init: function (params) {
                //手机验证和登录，计时交互
                //规划交互流程
                //在cookie中查找手机号

                var killPhone = $.cookie('killPhone');
                console.log("---killPhone:" + killPhone + "---startTime:" + params['starttime']);
                //验证手机号
                if (!seckill.validatePhone(killPhone)) {
                    //绑定phone
                    //控制输出
                    var killPhoneModal = $('#killPhoneModal');
                    //显示弹出层
                    killPhoneModal.modal({
                        show: true,//显示弹出层
                        backdrop: false,//禁止位置关闭
                        keyboard: false//关闭键盘事件
                    });
                    $('#killPhoneBtn').click(function () {
                        var inputPhone = $('#killPhoneKey').val();
                        console.log('---inputphone=' + inputPhone);
                        if (seckill.validatePhone(inputPhone)) {
                            //电话写入cookie
                            $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'})
                            //刷新页面
                            window.location.reload();
                        } else {
                            $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                        }
                    });
                }
                //已经登录
                //计时交互
                var startTime = params['starttime'];
                var endTime = params['endtime'];
                var seckillId = params['seckillid'];
                //回掉函数，result，返回执行的结果，result。DTO数据，默认传回JSON格式的数据进行解析~JavaScript解析数据格式
                $.get(seckill.URL.now(), {}, function (result) {
                    if (result && result['success']) {
                        var nowTime = result['data'];
                        //时间判断,计时交互
                        seckill.countdown(seckillId, nowTime, startTime, endTime);
                    } else {
                        console.log('result:' + result);
                    }
                })
            }
        }
    }
</script>
</body>
</html>
