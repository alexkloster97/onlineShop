<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="messages" scope="session" />
<html>
<head>
    <title>
        <fmt:message key="access.denied"/>
    </title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body style="background: url(../../images/access-denied.jpg) no-repeat; background-size: 100%;">
    <div class="accden"></div>
</body>
</html>
