<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý đơn hàng | Admin Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminDashboard.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminOrders.css">
</head>
<body class="admin-dashboard">
    <%@ include file="/WEB-INF/layout/header.jspf" %>
    <div class="dashboard-container">
        <div class="page-header">
            <h1>Quản Lý Đơn Hàng</h1>
            <a href="${pageContext.request.contextPath}/admin/dashboard" style="text-decoration: none; color: var(--text-muted);">
                <i class="fas fa-arrow-left"></i> Quay lại Dashboard
            </a>
        </div>

        <div class="content-card">
            <div class="table-responsive">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>Mã ĐH</th>
                            <th>Tài Khoản</th>
                            <th>Người Nhận</th>
                            <th>Địa Chỉ</th>
                            <th>Số Điện Thoại</th>
                            <th>Ngày Đặt</th>
                            <th>Tổng Tiền</th>
                            <th>Trạng Thái</th>
                            <th>Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="o" items="${ORDERS_LIST}">
                            <tr>
                                <td>#${o.orderId}</td>
                                <td>${o.account.account}</td>
                                <td>${o.custName}</td>
                                <td>${o.custAddr}</td>
                                <td>${o.custPhone}</td>
                                <td><fmt:formatDate value="${o.createdDate}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatNumber value="${o.totalValue}" type="currency" currencySymbol="₫"/></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/orders" method="get" style="margin: 0;">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="id" value="${o.orderId}">
                                        <select name="state" class="state-select status-${o.ordState}" onchange="this.form.submit()">
                                            <option value="0" ${o.ordState == 0 ? 'selected' : ''}>Chờ xác nhận</option>
                                            <option value="1" ${o.ordState == 1 ? 'selected' : ''}>Đang xử lý</option>
                                            <option value="2" ${o.ordState == 2 ? 'selected' : ''}>Đang giao</option>
                                            <option value="3" ${o.ordState == 3 ? 'selected' : ''}>Hoàn thành</option>
                                            <option value="4" ${o.ordState == 4 ? 'selected' : ''}>Đã hủy</option>
                                        </select>
                                    </form>
                                </td>
                                <td>
                                    <div class="order-actions">
                                        <a href="${pageContext.request.contextPath}/orderdetail?id=${o.orderId}" class="btn-action" style="background: #eee; color: #333;">Chi tiết</a>
                                        <a href="${pageContext.request.contextPath}/admin/orders?action=delete&id=${o.orderId}" 
                                           class="btn-action btn-delete" 
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa đơn hàng này?')">Xóa</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty ORDERS_LIST}">
                            <tr>
                                <td colspan="9" style="text-align: center; padding: 40px; color: #999;">
                                    Chưa có đơn hàng nào trong hệ thống.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
                 <%@ include file="/WEB-INF/layout/footer.jspf" %>
</body>
</html>
