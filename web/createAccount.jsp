<%-- 
    Document   : create
    Created on : Feb 24, 2026, 2:45:21 PM
    Author     : Dang Le Khang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo tài khoản</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/createAccount.css">
    </head>
    <body>
 <%@ include file="/WEB-INF/layout/header.jspf" %>
<div class="admin-create-wrapper">

    <div class="admin-create-card">

        <h2 class="admin-create-title">Thêm tài khoản mới</h2>

        <form method="post"
              action="${pageContext.request.contextPath}/admin/createaccount"
              class="admin-create-form">

            <div class="admin-form-group">
                <label>Tài khoản</label>
                <input type="text" name="account" required>
            </div>

            <div class="admin-form-group">
                <label>Mật khẩu</label>
                <input type="password" name="pass" required>
            </div>

            <div class="admin-form-group">
                <label>Họ và tên đêm</label>
                <input type="text" name="lname" required>
            </div>

            <div class="admin-form-group">
                <label>Tên</label>
                <input type="text" name="fname" required>
            </div>

            <div class="admin-form-group">
                <label>Số điện thoại</label>
                <input type="tel"
       name="phone"
       pattern="[0-9]{9,11}"
       inputmode="numeric"
       oninput="this.value=this.value.replace(/[^0-9]/g,'');"
       required>
            </div>

            <div class="admin-form-group">
                <label>Ngày sinh</label>
                <input type="date" name="bday">
            </div>

            <div class="admin-form-group">
                <label>Giới tính</label>
                <div class="admin-radio-group">
                    <label>
                        <input type="radio" name="gender" value="true" checked> Male
                    </label>
                    <label>
                        <input type="radio" name="gender" value="false"> Female
                    </label>
                </div>
            </div>

            <div class="admin-form-group">
                <label>Vai trò</label>
                <select name="role" class="admin-select">

                    <c:if test="${sessionScope.account.roleInSystem == 1}">
                        <option value="1">Administrator</option>
                        <option value="2">Manager</option>
                        <option value="3">User</option>
                    </c:if>

                    <c:if test="${sessionScope.account.roleInSystem == 2}">
                        <option value="2">Manager</option>
                        <option value="3">User</option>
                    </c:if>

                </select>
            </div>

            <c:if test="${sessionScope.account.roleInSystem == 1}">
                <div class="admin-form-group admin-checkbox-group">
                    <label>
                        <input type="checkbox" name="isUse" value="true">
                        Is active
                    </label>
                </div>
            </c:if>

            <button type="submit" class="admin-create-btn-submit">
                TẠO TÀI KHOẢN
            </button>

        </form>

    </div>

</div>
</body>
</html>
