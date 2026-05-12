<%-- 
    Document   : index
    Created on : Feb 24, 2026, 1:26:34 PM
    Author     : ADMIN
--%>

<%@page import="model.Account"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý tài khoản</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listAccount.css"> 
    </head>    

    <body>
        <%@ include file="/WEB-INF/layout/header.jspf" %>
        <div class="admin-account-container">

            <div class="admin-account-header">
                <h2>Quản lý tài khoản</h2>

                <a href="createaccount" class="admin-create-btn">
                    + Tạo tài khoản mới
                </a>
            </div>

            <div class="admin-account-table-wrapper">

                <table class="admin-account-table">

                    <thead>
                        <tr>
                            <th>Account</th>
                            <th>Họ</th>
                            <th>Tên</th>
                            <th>Ngày sinh</th>
                            <th>Giới tính</th>
                            <th>Điện thoại</th>
                            <th>Trạng thái</th>
                            <th>Role</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach items="${requestScope.LIST_ACCOUNTS}" var="acc">

                            <tr>

                                <td>${acc.account}</td>
                                <td>${acc.lastName}</td>
                                <td>${acc.firstName}</td>
                                <td>
                                    <fmt:formatDate value="${acc.birthday}" pattern="dd/MM/yyyy"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${acc.gender}">
                                            <span class="gender-male">Nam</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="gender-female">Nữ</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${acc.phone}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${acc.isUse}">
                                            <span class="status-active">Hoạt động</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-inactive">Đã khóa</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${acc.roleInSystem == 1}">
                                            <span class="role-admin">Admin</span>
                                        </c:when>
                                        <c:when test="${acc.roleInSystem == 2}">
                                            <span class="role-manager">Manager</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="role-user">User</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td class="admin-actions">

                                    <a href="updateAccount?account=${acc.account}" 
                                       class="admin-update-btn">
                                        Cập nhật
                                    </a>

                                    <a href="remove?account=${acc.account}" 
                                       class="admin-remove-btn"
                                       onclick="return confirm('Bạn có chắc muốn xóa?')">
                                        Xóa
                                    </a>

                                    
                                    <c:if test="${sessionScope.account.roleInSystem == 1}">

                                        <a href="${pageContext.request.contextPath}/toggleActive?account=${acc.account}"
                                           class="admin-active-btn
                                           ${acc.isUse ? 'btn-green' : 'btn-red'}">

                                            ${acc.isUse ? 'Active' : 'Inactive'}

                                        </a>

                                    </c:if>

                                </td>

                            </tr>

                        </c:forEach>
                    </tbody>

                </table>

            </div>

        </div>

    </body>
</html>
