<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hxy
  Date: 2020/6/19
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>列表展示</title>
    <link href="/js/bootstrap.min.css" rel="stylesheet">

    <script src="/js/bootstrap-theme.min.css"></script>

    <script src="/js/jquery.min.js"></script>

    <script src="/js/bootstrap.min.js"></script>

    <script>
        $(function () {
            $("addBtn").bind("click",function () {
                $.ajax({
                    url:'/list/add',
                    type:'POST',
                    data:'{"id":"1"}',
                    contentType:'application/json;charset=utf-8',
                    dataType: 'json',
                    success: function (data) {
                        alert('添加成功');
                    }
                })
            })
        })

    </script>
</head>
<body>
<div class="container">
<%--    <input type="button" id="addBtn" value="新增">--%>
    <h1>部门列表</h1>
    <table class="table table-striped table-hover">
        <tr class="danger">
            <td>地址</td>
            <td>名称</td>
            <td>电话</td>

        </tr>
<%--        <tr>--%>
<%--            <td>${resume.address}</td>--%>
<%--            <td>${resume.name}</td>--%>
<%--            <td>${resume.phone}</td>--%>
<%--        </tr>--%>
        <c:forEach items="${lists}" var="resume">
            <tr>
                <td>${resume.address }</td>
                <td>${resume.name }</td>
                <td>${resume.phone }</td>
<%--                <td>--%>
<%--                    <input type="button" value="删除" class="btn btn-danger" id="deleteBtn" >--%>
<%--                    <input type="button" value="编辑" class="btn btn-info" id="editBtn">--%>
<%--                </td>--%>
            </tr>
        </c:forEach>


    </table>
</div>

</body>
</html>
