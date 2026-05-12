<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Chi tiết đơn hàng - #${ORDER.orderId}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orders.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .detail-card {
            background: #fff;
            border: 1px solid #eee;
            padding: 30px;
            margin-bottom: 30px;
        }
        .detail-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 1px solid #f0f0f0;
        }
        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 40px;
            margin-bottom: 40px;
        }
        .info-section h3 {
            font-size: 14px;
            text-transform: uppercase;
            margin-bottom: 15px;
            color: #888;
            letter-spacing: 1px;
        }
        .info-content p {
            margin: 8px 0;
            font-size: 15px;
        }
        .product-item {
            display: flex;
            align-items: center;
            padding: 15px 0;
            border-bottom: 1px solid #f9f9f9;
        }
        .product-img {
            width: 80px;
            height: 100px;
            object-fit: cover;
            margin-right: 20px;
            border: 1px solid #eee;
        }
        .product-info { flex: 1; }
        .product-name { font-weight: 600; font-size: 15px; margin-bottom: 5px; }
        .product-meta { color: #777; font-size: 13px; }
        .product-price-qty { text-align: right; }
        .order-summary-box {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 2px solid #000;
            width: 300px;
            margin-left: auto;
        }
        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-size: 15px;
        }
        .summary-row.total {
            font-weight: 700;
            font-size: 18px;
            margin-top: 15px;
        }
    </style>
</head>
<body class="orders-page">
    <%@ include file="/WEB-INF/layout/header.jspf" %>

    <div class="orders-container">
        <div class="detail-header">
            <h1>Chi tiết đơn hàng #${ORDER.orderId}</h1>
            <a href="${pageContext.request.contextPath}/orders" class="view-detail-btn" style="border:none; text-decoration: underline;">Quay lại danh sách</a>
        </div>

        <div class="detail-card">
            <div class="info-grid">
                <div class="info-section">
                    <h3>Thông tin vận chuyển</h3>
                    <div class="info-content">
                        <p><strong>Người nhận:</strong> ${ORDER.custName}</p>
                        <p><strong>Số điện thoại:</strong> ${ORDER.custPhone}</p>
                        <p><strong>Địa chỉ:</strong> ${ORDER.custAddr}</p>
                    </div>
                </div>
                <div class="info-section">
                    <h3>Thông tin đơn hàng</h3>
                    <div class="info-content">
                        <p><strong>Ngày đặt:</strong> <fmt:formatDate value="${ORDER.createdDate}" pattern="dd/MM/yyyy HH:mm"/></p>
                        <p><strong>Trình trạng:</strong> 
                            <c:choose>
                                <c:when test="${ORDER.ordState == 0}"><span class="status-badge status-0">Chờ xác nhận</span></c:when>
                                <c:when test="${ORDER.ordState == 1}"><span class="status-badge status-1">Đang xử lý</span></c:when>
                                <c:when test="${ORDER.ordState == 2}"><span class="status-badge status-2">Đang giao</span></c:when>
                                <c:when test="${ORDER.ordState == 3}"><span class="status-badge status-3">Hoàn thành</span></c:when>
                                <c:when test="${ORDER.ordState == 4}"><span class="status-badge status-4">Đã hủy</span></c:when>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </div>

            <div class="product-list-header" style="border-bottom: 1px solid #000; padding-bottom: 10px; margin-bottom: 10px; font-weight: 600; text-transform: uppercase; font-size: 13px;">
                Sản phẩm đã mua
            </div>

            <c:forEach var="d" items="${ORDER_DETAILS}">
                <div class="product-item">
                    <img src="${pageContext.request.contextPath}${d.product.productImage}" alt="${d.product.productName}" class="product-img">
                    <div class="product-info">
                        <div class="product-name">${d.product.productName}</div>
                        <div class="product-meta">Đơn giá: <fmt:formatNumber value="${d.price}" type="number"/>₫</div>
                        <c:if test="${d.discount > 0}">
                            <div class="product-meta" style="color: #e74c3c;">Giảm giá: ${d.discount}%</div>
                        </c:if>
                    </div>
                    <div class="product-price-qty">
                        <div style="font-size: 14px; margin-bottom: 5px;">x ${d.quantity}</div>
                        <div style="font-weight: 600;">
                            <fmt:formatNumber value="${(d.price - (d.price * d.discount / 100)) * d.quantity}" type="number"/>₫
                        </div>
                    </div>
                </div>
            </c:forEach>

            <div class="order-summary-box">
                <div class="summary-row">
                    <span>Thành tiền:</span>
                    <span><fmt:formatNumber value="${ORDER.totalValue}" type="number"/>₫</span>
                </div>
                <div class="summary-row">
                    <span>Phí vận chuyển:</span>
                    <span>0đ</span>
                </div>
                <div class="summary-row total">
                    <span>TỔNG CỘNG:</span>
                    <span><fmt:formatNumber value="${ORDER.totalValue}" type="number"/>₫</span>
                </div>
            </div>
        </div>
    </div>
</body>
 <%@ include file="/WEB-INF/layout/footer.jspf" %>
</html>
