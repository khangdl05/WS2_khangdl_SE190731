<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">


        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    </head>
    <body class="login-page">
        
        <%
            String username_value="";
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for (Cookie cookie : cookies){
                    if(cookie.getName().equals("USERNAME_COOKIE")){
                    username_value = cookie.getValue();
            }
            }
            }
            request.setAttribute("username_value", username_value);
            %>
        <div class="login-container">
            <h2>Đăng nhập</h2>

            <p class="error">${requestScope.error}</p>

            <form method="post" action="login">

                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="username" value="${username_value}" required>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <div class="password-wrapper">
                        <input type="password" name="password" id="pass" required>
                        <i class="fa-solid fa-eye toggle-password" onclick="togglePassword('pass', this)"></i>
                    </div>
                </div>
                <div class="form-group remember-group">
                    <label class="remember-label">
                        <input type="checkbox" name="remember" value="1">
                        Remember me
                    </label>
                </div>

                <button type="submit" class="btn-login">Login</button>

            </form>

            <div class="register-link">
                Chưa có tài khoản? <a href="register">Đăng ký</a>
            </div>
        </div>


        <script>
            function togglePassword(id, icon) {
                var input = document.getElementById(id);

                if (input.type === "password") {
                    input.type = "text";
                    icon.classList.remove("fa-eye");
                    icon.classList.add("fa-eye-slash");
                } else {
                    input.type = "password";
                    icon.classList.remove("fa-eye-slash");
                    icon.classList.add("fa-eye");
                }
            }
        </script>
    </body>
</html>