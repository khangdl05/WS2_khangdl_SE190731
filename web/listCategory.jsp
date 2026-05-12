<%-- 
    Document   : listCategory
    Created on : Feb 25, 2026, 1:21:43 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý danh mục</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listCategory.css"> 
    </head>
    <body>
<%@ include file="/WEB-INF/layout/header.jspf" %>
<div class="category-page-wrapper">

    <div class="category-page-card">

        <div class="category-page-header">
            <h2>Quản lý danh mục</h2>

            <c:if test="${sessionScope.account.roleInSystem == 1 
                          || sessionScope.account.roleInSystem == 2}">
                <a href="${pageContext.request.contextPath}/admin/createcategory"
                   class="category-page-btn">
                   + Tạo mới danh mục
                </a>
            </c:if>
        </div>

        <table class="category-page-table">
            <thead>
                <tr>
                    <c:if test="${sessionScope.account.roleInSystem == 1 
                                  || sessionScope.account.roleInSystem == 2}">
                        <th>Type Id</th>
                    </c:if>
                    <th>Tên danh mục</th>
                    <th>Memo</th>
                    <c:if test="${sessionScope.account.roleInSystem == 1 
                                  || sessionScope.account.roleInSystem == 2}">
                        <th>Thao tác</th>
                    </c:if>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${requestScope.LIST_CATEGORY}" var="cate">
                    <tr>
                        <c:if test="${sessionScope.account.roleInSystem == 1 
                                      || sessionScope.account.roleInSystem == 2}">
                            <td>${cate.typeId}</td>
                        </c:if>

                        <td>${cate.categoryName}</td>
                        <td>${cate.memo}</td>

                        <c:if test="${sessionScope.account.roleInSystem == 1 
                                      || sessionScope.account.roleInSystem == 2}">
                            <td class="category-page-action">
                                <a href="${pageContext.request.contextPath}/admin/updatecate?id=${cate.typeId}"
                                   class="category-page-link update-link">
                                   Cập nhật
                                </a>

                                <a href="${pageContext.request.contextPath}/admin/removecate?id=${cate.typeId}"
                                   class="category-page-link remove-link"
                                   onclick="return confirm('Bạn có chắc muốn xóa?')">
                                   Xóa
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>

</div>

</body>
</html>
