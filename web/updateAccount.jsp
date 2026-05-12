<%-- 
    Document   : update
    Created on : Feb 24, 2026, 4:57:45 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập nhật tài khoản</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/updateAccount.css"> 
    </head>
    <body>
<%@ include file="/WEB-INF/layout/header.jspf" %>
<div class="account-edit-wrapper">

    <div class="account-edit-card">

        <h2 class="account-edit-title">Cập nhật tài khoản</h2>

        <form method="post" action="updateAccount" class="account-edit-form">

           
            <div class="account-edit-group">
                <label>Tài khoản</label>
                <input type="text"
                       value="${requestScope.acc.account}"
                       disabled>
                <input type="hidden"
                       name="account"
                       value="${requestScope.acc.account}">
            </div>

            <div class="account-edit-group">
                <label>Mật khẩu</label>
                <input type="password"
                       name="pass"
                       value="${requestScope.acc.pass}">
            </div>

            <div class="account-edit-group">
                <label>Họ và tên đệm</label>
                <input type="text"
                       name="lname"
                       value="${requestScope.acc.lastName}">
            </div>

            <div class="account-edit-group">
                <label>Tên</label>
                <input type="text"
                       name="fname"
                       value="${requestScope.acc.firstName}">
            </div>

            <div class="account-edit-group">
                <label>Số điện thoại</label>
                <input type="tel"
                    name="phone"
                    value="${requestScope.acc.phone}"
                    pattern="[0-9]{9,11}"
                    inputmode="numeric"
                    oninput="this.value=this.value.replace(/[^0-9]/g,'');"
                    required>
            </div>

            <div class="account-edit-group">
                <label>Ngày sinh</label>
                <input type="date"
                       name="bday"
                       value="${requestScope.acc.birthday}">
            </div>

            <div class="account-edit-group">
                <label>Giới tính</label>
                <div class="account-edit-radio">
                    <label>
                        <input type="radio" name="gender" value="true"
                        ${requestScope.acc.gender ? "checked" : ""}>
                        Male
                    </label>

                    <label>
                        <input type="radio" name="gender" value="false"
                        ${!requestScope.acc.gender ? "checked" : ""}>
                        Female
                    </label>
                </div>
            </div>


            <div class="account-edit-group">
                <label>Vai trò</label>

                <c:choose>


                    <c:when test="${sessionScope.account.roleInSystem == 1}">
                        <select name="role" class="account-edit-select">
                            <option value="1"
                                ${requestScope.acc.roleInSystem == 1 ? "selected" : ""}>
                                Administrator
                            </option>
                            <option value="2"
                                ${requestScope.acc.roleInSystem == 2 ? "selected" : ""}>
                                Manager
                            </option>
                            <option value="3"
                                ${requestScope.acc.roleInSystem == 3 ? "selected" : ""}>
                                User
                            </option>
                        </select>
                    </c:when>

                    <c:otherwise>
                        <input type="text"
                               class="account-edit-readonly"
                               value="${requestScope.acc.roleInSystem == 1 ? 'Administrator' :
                                 requestScope.acc.roleInSystem == 2 ? 'Manager' : 'User'}"
                               disabled>

                        <input type="hidden"
                               name="role"
                               value="${requestScope.acc.roleInSystem}">
                    </c:otherwise>

                </c:choose>
            </div>


            <c:if test="${sessionScope.account.roleInSystem == 1}">
                <div class="account-edit-group account-edit-checkbox">
                    <label>
                        <input type="checkbox" name="isUse" value="true"
                        ${requestScope.acc.isUse ? "checked" : ""}>
                        Is active
                    </label>
                </div>
            </c:if>

            <button type="submit" class="account-edit-btn">
                UPDATE ACCOUNT
            </button>

        </form>

    </div>

</div>

</body>
</html>
