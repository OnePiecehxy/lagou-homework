<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SpringMVC 测试页</title>


    <script type="text/javascript" src="/js/jquery.min.js"></script>





    <style>
        div{
            padding:10px 10px 0 10px;
        }
    </style>
</head>
<body>
    <div>
        <h2></h2>
        <fieldset>
            <p>测试用例：SpringMVC 对原生servlet api的支持</p>
            <a href="/demo/handle02?id=1">点击测试</a>
        </fieldset>
        <fieldset>
            <p>测试用例：SpringMVC 接收简单数据类型参数</p>
            <a href="/demo/handle03?id=1">点击测试</a>
        </fieldset>
        <fieldset>
            <p>测试用例：SpringMVC 使用@RequestParam 接收简单数据类型参数(形参名和参数名不一致)</p>
        </fieldset>

        <fieldset>
            <p>测试用例：SpringMVC接收pojo类型参数</p>
            <a href="/demo/handle04?id=1&name=zhangsan">点击测试</a>
        </fieldset>

        <fieldset>
            <p>测试用例：SpringMVC接收pojo包装类型参数</p>
            <a href="/demo/handle05?user.id=1&user.name=zhangsan">点击测试</a>
        </fieldset>

        <fieldset>
            <p>测试用例：SpringMVC接收日期类型参数</p>
            <a href="/demo/handle06?birthday=2019-10-08">点击测试</a>
        </fieldset>
    </div>






</body>
</html>
