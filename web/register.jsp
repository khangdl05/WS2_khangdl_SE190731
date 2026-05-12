<%-- 
    Document   : register
    Created on : Feb 27, 2026, 5:34:36 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Register</title>

   

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
    </head>
<body class="register-page">

    <div class="register-container">
        <h2>Đăng ký</h2>

        <p class="error">${requestScope.error}</p>

        <form method="post" action="register">

            <div class="form-group">
                <label>Tài khoản</label>
                <input type="text" name="account" value="${requestScope.account}" required>
            </div>

            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="pass" required>
            </div>

            <div class="form-group">
                <label>Xác nhận mật khẩu</label>
                <input type="password" name="confirmPass" required>
            </div>

            <div class="form-row">
    
            <div class="form-group last-name">
                <label>Họ và tên đệm</label>
                <input type="text" name="lastName" value="${requestScope.lastName}" required>
            </div>

            <div class="form-group first-name">
                <label>Tên</label>
                <input type="text" name="firstName" value="${requestScope.firstName}" required>
            </div>

        </div>
            <div class="form-group">
            <label>Giới tính</label>
            <select name="gender" required>
                <option value="" disabled selected>-- Chọn giới tính --</option>
                <option value="true" ${requestScope.gender == 'true' ? 'selected' : ''}>Nam</option>
                <option value="false" ${requestScope.gender == 'false' ? 'selected' : ''}>Nữ</option>
            </select>
        </div>
            <div class="form-group">
            <label>Số điện thoại</label>
            <input type="text" name="phone" maxlength="10"oninput="this.value = this.value.replace(/[^0-9]/g, '')" value="${requestScope.phone}" required=>
        </div>

            <button type="submit" class="btn-register">Đăng ký</button>

        </form>

        <div class="login-link">
            Đã có tài khoản? <a href="login">Đăng nhập</a>
        </div>
    </div>

</body>
</html>
