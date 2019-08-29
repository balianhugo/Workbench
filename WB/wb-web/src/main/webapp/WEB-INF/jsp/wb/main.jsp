
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>管理平台</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/wb.css"/>

    <%@include file="../common.jsp" %>
    <script type="text/javascript">
        /* 初始化全局参数 */
        var wb = {
            user:${user},
            nav:${menus}
        };
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/app/app.js"></script>
</head>
<body>

</body>
</html>
