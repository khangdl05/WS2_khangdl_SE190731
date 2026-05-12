<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt hàng thành công</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Inter', sans-serif; text-align: center; padding: 100px 20px; color: #333; }
        .success-icon { font-size: 80px; color: #2ecc71; margin-bottom: 20px; }
        h1 { margin-bottom: 10px; }
        p { color: #666; margin-bottom: 30px; }
        .home-btn { 
            display: inline-block; 
            padding: 12px 30px; 
            background: #338dbc; 
            color: #fff; 
            text-decoration: none; 
            border-radius: 4px;
            transition: 0.3s;
        }
        .home-btn:hover { background: #2b78a0; }
    </style>
</head>
<body>
    <div class="success-icon"><i class="fas fa-check-circle"></i></div>
    <h1>Cảm ơn bạn đã đặt hàng!</h1>
    <p>${SUCCESS_MSG != null ? SUCCESS_MSG : 'Đơn hàng của bạn đã được tiếp nhận và đang được xử lý.'}</p>
    <a href="${pageContext.request.contextPath}/home" class="home-btn">Tiếp tục mua sắm</a>
</body>
</html>
