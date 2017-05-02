<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="messages" scope="session" />

<html>
<head>
    <title>
        <fmt:message key="admin.category.title"/>
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
                    <input type="hidden" name="command" value="category-add">

                    <p class="field_label"><fmt:message key="category.name"/></p>
                    <input type="text" name="name" required maxlength="20" class="field_input">
                    <c:if test="${errorSet.contains('name.error')}">
                        <fmt:message key="category.error.name"/><br/>
                    </c:if>

                    <p class="field_label"><fmt:message key="category.image"/></p>
                    <input type="file" name="file" required class="field_input">

                    <button type="submit" class="btn">
                        <fmt:message key="index.add"/>
                    </button>
                </form>
            </div>
        </div>

        <div class="content" style="width: 1000px;">

            <div class="products">
                <div class="table">
                    <table>
                        <tr>
                            <td><fmt:message key="category.name"/></td>
                            <td><fmt:message key="category.image"/></td>
                            <td><fmt:message key="index.delete"/></td>
                            <td><fmt:message key="index.edit"/></td>
                        </tr>
                        <c:forEach items="${categoryList}" var="category">
                            <tr>
                                <td>
                                    <form action="controller" method="post" id="editForm${category.categoryId}">
                                        <input type="hidden" name="command" value="category-edit">
                                        <input type="hidden" name="categoryId" value="${category.categoryId}">
                                        <input type="hidden" name="path" value="${category.path}">

                                        <input type="text" name="name" required maxlength="20" value="${category.name}"><br/>
                                        <c:if test="${errorSetEdit.contains('name.error')}">
                                            <fmt:message key="category.error.name"/><br/>
                                        </c:if>
                                    </form>
                                </td>
                                <td><img src="${category.path}" width="390" height="140"></td>
                                <td><a href="/controller?command=category-delete&categoryId=${category.categoryId}"><fmt:message key="index.delete"/></a></td>
                                <td>
                                    <button type="submit" form="editForm${category.categoryId}" class="btn" style="margin: 0">
                                        <fmt:message key="index.edit"/>
                                    </button>
                                </td>
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
