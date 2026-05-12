<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Đơn hàng của tôi - MyShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orders.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="orders-page">
    <%@ include file="/WEB-INF/layout/header.jspf" %>

    <div class="orders-container">
        <h1>Đơn hàng của tôi</h1>

        <c:choose>
            <c:when test="${empty ORDERS_LIST}">
                <div class="empty-orders">
                    <i class="fas fa-box-open"></i>
                    <p>Bạn chưa có đơn hàng nào.</p>
                    <a href="${pageContext.request.contextPath}/home" class="continue-shopping">Bắt đầu mua sắm</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="orders-table-wrapper">
                    <table class="orders-table">
                        <thead>
                            <tr>
                                <th>Mã đơn hàng</th>
                                <th>Ngày đặt</th>
                                <th>Người nhận</th>
                                <th>Tổng tiền</th>
                                <th>Trạng thái</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${ORDERS_LIST}">
                                <tr>
                                    <td class="order-id">#${o.orderId}</td>
                                    <td><fmt:formatDate value="${o.createdDate}" pattern="dd/MM/yyyy"/></td>
                                    <td>
                                        <div>${o.custName}</div>
                                        <div style="font-size: 11px; color: #888;">${o.custPhone}</div>
                                    </td>
                                    <td><fmt:formatNumber value="${o.totalValue}" type="number"/>₫</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${o.ordState == 0}"><span class="status-badge status-0">Chờ xác nhận</span></c:when>
                                            <c:when test="${o.ordState == 1}"><span class="status-badge status-1">Đang xử lý</span></c:when>
                                            <c:when test="${o.ordState == 2}"><span class="status-badge status-2">Đang giao</span></c:when>
                                            <c:when test="${o.ordState == 3}"><span class="status-badge status-3">Hoàn thành</span></c:when>
                                            <c:when test="${o.ordState == 4}"><span class="status-badge status-4">Đã hủy</span></c:when>
                                        </c:choose>
                                    </td>
                                    <td style="text-align: right;">
                                        <a href="${pageContext.request.contextPath}/orderdetail?id=${o.orderId}" class="view-detail-btn">Chi tiết</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
     <%@ include file="/WEB-INF/layout/footer.jspf" %>
</body>
</html>
