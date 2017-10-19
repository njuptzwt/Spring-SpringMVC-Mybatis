<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!--加入必要的标签组才能使用-->
<html>
<head>
    <!--静态包含公共页面，实现的效果是可以把包含的整个页面的内容加载到该页面的jsp代码中-->
    <!--动态包含是指多个Servlet的最终运行结果饭囊在一起-->
    <title>秒杀产品的列表页</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>详情页</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="sk">
                    <tr>
                        <td>${sk.name}</td>
                        <td>${sk.number}</td>
                        <td>
                            <fmt:formatDate value="${sk.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${sk.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${sk.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td><a class="btn btn-info" href="${pageContext.request.contextPath}/seckill/${sk.seckillid}/detail" target="_blank">详情</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
