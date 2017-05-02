<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="messages" scope="session" />

<html>
<head>
    <title>
        <fmt:message key="cart.title"/>
    </title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css">
</head>
<body>
    <div class="header">
        <div>
            <a id="logo"><img src="images/logo.jpg" alt="logo"/></a>
            <div class="navigation">
                <ul class="first">
                    <c:if test="${empty user}">
                        <li class="first"><a href="/controller?command=login-start"><fmt:message key="login.title"/></a></li>
                        <li><a href="/controller?command=register-start"><fmt:message key="register.title"/></a></li>
                    </c:if>
                    <c:if test="${not empty user}">
                        <c:if test="${user.rights == 'ROLE_ADMIN'}">
                            <li class="first">
                                <a href="/controller?command=user-start">
                                    <fmt:message key="admin.user.title"/>
                                </a>
                            </li>
                            <li>
                                <a href="/controller?command=category-start">
                                    <fmt:message key="admin.category.title"/>
                                </a>
                            </li>
                            <li>
                                <a href="/controller?command=good-start">
                                    <fmt:message key="admin.good.title"/>
                                </a>
                            </li>
                            <li>
                                <a href="/controller?command=order-start">
                                    <fmt:message key="admin.order.title"/>
                                </a>
                            </li>
                        </c:if>
                        <li class="first"><a href="/controller?command=cart-list"><fmt:message key="cart.title"/></a></li>
                    </c:if>
                </ul>
                <ul>
                    <li><a href="/controller?command=language&loc=en"><fmt:message key="index.en"/></a></li>
                    <li><a href="/controller?command=language&loc=ru"><fmt:message key="index.ru"/></a></li>
                    <c:if test="${not empty user}">
                        <li><a href="/controller?command=logout"><fmt:message key="login.logout"/></a></li>
                    </c:if>
                </ul>
            </div>
            <form action="controller" class="search" method="post" style="width: 364">
                <input type="hidden" name="command" value="cart-find">
                <input type="text" name="value" onblur="this.value=!this.value?'search':this.value;"
                       onfocus="this.select()" onclick="this.value='';" required maxlength="50" style="width: 364"/>
                <input type="submit" id="submit" value=""/>
            </form>
        </div>
        <div id="navigation">
            <ul>
                <li class="selected"><a href="controller"><fmt:message key="index.title"/></a></li>
                <c:forEach items="${categoryList}" var="category" varStatus="stat">
                    <c:if test="${stat.index < 6}">
                        <li><a href="/controller?command=goods-list-command&categoryId=${category.categoryId}">
                                ${category.name}
                        </a></li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="body">

        <div class="content" style="width: 1000px;">

            <div class="products">
                <div class="table">
                    <table>
                        <tr>
                            <td><fmt:message key="user.login"/></td>
                            <td><fmt:message key="order.date"/></td>
                            <td><fmt:message key="good.name"/></td>
                            <td><fmt:message key="good.image"/></td>
                            <td><fmt:message key="good.price"/></td>
                            <td><fmt:message key="order.number"/></td>
                            <td><fmt:message key="index.delete"/></td>
                            <td><fmt:message key="cart.submit"/></td>
                        </tr>
                        <c:forEach items="${orderList}" var="order">
                            <tr>
                                <td>${order.user.login}</td>
                                <td>${order.date}</td>
                                <td>${order.good.name}</td>
                                <td><img src="${order.good.path}" width="170" height="220"></td>
                                <td>${order.good.price} BYR</td>
                                <td>
                                    <form action="controller" method="post" id="editForm${order.orderId}" style="margin-bottom: 0">
                                        <input type="hidden" name="command" value="cart-edit">
                                        <input type="hidden" name="orderId" value="${order.orderId}" >

                                        <input type="number" name="number" required min="0" value="${order.number}" style="width: 50px;">
                                    </form>
                                </td>
                                <td>
                                    <button class="btn" style="margin: 0">
                                        <a href="/controller?command=cart-delete&orderId=${order.orderId}">
                                            <fmt:message key="index.delete"/>
                                        </a>
                                    </button>
                                </td>
                                <td>
                                    <button class="btn" style="margin: 0">
                                        <a href="/controller?command=cart-submit&orderId=${order.orderId}">
                                            <fmt:message key="cart.submit"/>
                                        </a>
                                    </button>

                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>

        <div class="article">
            <div class="first">
                <h3>
                    <fmt:message key="index.about"/>
                </h3>
                <p><fmt:message key="index.about.message.first"/></p>
                <p><fmt:message key="index.about.message.second"/></p>
            </div>
            <div>
                <h3><fmt:message key="index.about.contact"/> </h3>
                <p><fmt:message key="index.about.contact.address"/></p>
                <p><fmt:message key="index.about.contact.phone"/></p>
                <p><fmt:message key="index.about.contact.email"/></p>
            </div>
        </div>
    </div>

    <div class="footer">
        <p>&#169; 2016 Azimova Lolita. All Rights Reserved.</p> </div>



</body>
</html>
