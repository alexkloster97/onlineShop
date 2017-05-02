<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="messages" scope="session" />

<html>
<head>
    <title>
        <fmt:message key="admin.good.title"/>
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

        <div class="article">
            <div class="first">
                <form action="controller" method="post" enctype="multipart/form-data" class="form">
                    <input type="hidden" name="command" value="goods-add">

                    <p class="field_label"><fmt:message key="good.name"/></p>
                    <input type="text" name="name" required maxlength="50" class="field_input"><br/>

                    <p class="field_label"><fmt:message key="good.price"/></p>
                    <input type="number" name="price" required min="0" class="field_input"><br/>

                    <p class="field_label"><fmt:message key="good.about"/></p>
                    <textarea name="about" class="field_input" style="height: 70"></textarea><br/>

                    <p class="field_label"><fmt:message key="category.name"/></p>
                    <select name="categoryId" class="field_input">
                        <c:forEach items="${categoryList}" var="category">
                            <option value="${category.categoryId}">${category.name}</option>
                        </c:forEach>
                    </select><br/>

                    <p class="field_label"><fmt:message key="good.image"/></p>
                    <input type="file" name="file" required class="field_input">

                    <button type="submit" class="btn">
                        <fmt:message key="index.add"/>
                    </button>
                </form>
            </div>
            <div>
                <form action="controller" method="post" class="form">
                    <input type="hidden" name="command" value="goods-edit">
                    <input type="hidden" name="goodId" value="${editGoods.goodId}">

                    <p class="field_label"><fmt:message key="good.name"/></p>
                    <input type="text" name="name" required maxlength="50" value="${editGoods.name}" class="field_input">

                    <p class="field_label"><fmt:message key="good.price"/></p>
                    <input type="number" name="price" required min="0" value="${editGoods.price}" class="field_input">

                    <p class="field_label"><fmt:message key="good.about"/></p>
                    <textarea name="about" class="field_input" style="height: 70">${editGoods.about}</textarea>

                    <p class="field_label"><fmt:message key="category.name"/></p>
                    <select name="categoryId" class="field_input">
                        <c:forEach items="${categoryList}" var="category">
                            <c:if test="${editGoods.category.categoryId == category.categoryId}">
                                <option selected value="${category.categoryId}">${category.name}</option>
                            </c:if>
                            <c:if test="${editGoods.category.categoryId != category.categoryId}">
                                <option value="${category.categoryId}">${category.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>

                    <button type="submit" class="btn">
                        <fmt:message key="index.edit"/>
                    </button>
                </form>
            </div>
        </div>

        <div class="content" style="width: 1000px;">

            <div class="products">
                <div class="table">
                    <table>
                        <tr>
                            <td><fmt:message key="good.name"/></td>
                            <td><fmt:message key="good.price"/></td>
                            <td><fmt:message key="category.name"/></td>
                            <td><fmt:message key="index.delete"/></td>
                            <td><fmt:message key="index.edit"/></td>
                        </tr>
                        <c:forEach items="${goodList}" var="good">
                            <tr>
                                <td>${good.name}</td>
                                <td>${good.price}</td>
                                <td>${good.category.name}</td>
                                <td><a href="/controller?command=goods-delete&goodId=${good.goodId}"><fmt:message key="index.delete"/></a></td>
                                <td><a href="/controller?command=goods-edit-start&goodId=${good.goodId}"><fmt:message key="index.edit"/></a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        <p>&#169; 2016 Azimova Lolita. All Rights Reserved.</p>
    </div>

</body>
</html>
