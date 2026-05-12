<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard | MyShop</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminDashboard.css">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    </head>
    <body class="admin-dashboard">
        <%@ include file="/WEB-INF/layout/header.jspf" %>
        <div class="dashboard-container">
            <div class="dashboard-header">
                <h1>BẢNG QUẢN LÝ HỆ THỐNG</h1>
                <p>Chào mừng, ${sessionScope.account.firstName}!</p>
            </div>

            <!-- 4 Statistic Cards -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon blue">
                        <i class="fas fa-shopping-bag"></i>
                    </div>
                    <div class="stat-info">
                        <h3>Tổng Đơn Hàng</h3>
                        <span class="number">${TOTAL_ORDERS != null ? TOTAL_ORDERS : '0'}</span>
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-icon green">
                        <i class="fas fa-money-bill-wave"></i>
                    </div>
                    <div class="stat-info">
                        <h3>Tổng Doanh Thu</h3>
                        <span class="number">
                            <fmt:formatNumber 
                                value="${TOTAL_REVENUE != null ? TOTAL_REVENUE : 0}" 
                                type="number" 
                                groupingUsed="true"/> đ
                        </span>
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-icon orange">
                        <i class="fas fa-box"></i>
                    </div>
                    <div class="stat-info">
                        <h3>Sản Phẩm Đang Bán</h3>
                        <span class="number">${TOTAL_PRODUCTS_AVAILABLE != null ? TOTAL_PRODUCTS_AVAILABLE : '0'}</span>
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-icon purple">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="stat-info">
                        <h3>Người Dùng</h3>
                        <span class="number">${TOTAL_USERS != null ? TOTAL_USERS : '0'}</span>
                    </div>
                </div>
            </div>

            <div class="dashboard-row">
                <!-- Recent Orders Table -->
                <div class="content-card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                        <h2 style="margin: 0;">Đơn Hàng Gần Đây</h2>
                        <a href="${pageContext.request.contextPath}/admin/orders" style="font-size: 13px; color: var(--primary-color); text-decoration: none;">Xem tất cả</a>
                    </div>
                    <div class="table-responsive">
                        <table class="admin-table">
                            <thead>
                                <tr>
                                    <th>Mã ĐH</th>
                                    <th>Khách Hàng</th>
                                    <th>Tổng Tiền</th>
                                    <th>Trạng Thái</th>
                                    <th>Ngày Đặt</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${RECENT_ORDERS}">
                                    <tr>
                                        <td>#${order.orderId}</td>
                                        <td>${order.account.lastName} ${order.account.firstName}</td>
                                        <td>
                                            <fmt:formatNumber value="${order.totalValue}" type="number" groupingUsed="true"/>đ
                                        </td>
                                        <td>
                                            <span class="status-badge ${order.ordState == 3 ? 'status-completed' : 'status-pending'}">
                                                ${order.ordState == 0 ? 'Chờ xác nhận' : 
                                                  order.ordState == 1 ? 'Đang xử lý' : 
                                                  order.ordState == 2 ? 'Đang giao' : 
                                                  order.ordState == 3 ? 'Hoàn thành' : 'Đã hủy'}
                                            </span>
                                        </td>
                                        <td><fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy"/></td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty RECENT_ORDERS}">
                                    <tr><td colspan="5" style="text-align: center; color: #999; padding: 30px;">Chưa có đơn hàng nào.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Quick Actions Section -->
                <div class="content-card">
                    <h2>Thao Tác Nhanh</h2>
                    <div class="quick-actions">
                        <a href="${pageContext.request.contextPath}/admin/products" class="action-btn">
                            <i class="fas fa-box-open"></i><br>Sản Phẩm
                        </a>
                        <a href="${pageContext.request.contextPath}/listcategory" class="action-btn">
                            <i class="fas fa-folder-plus"></i><br>Danh Mục
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/orders" class="action-btn">
                            <i class="fas fa-clipboard-list"></i><br>Đơn Hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/user-segments" class="action-btn">
                            <i class="fas fa-chart-pie"></i><br>Phân khúc
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/listacc" class="action-btn">
                            <i class="fas fa-user-cog"></i><br>Tài Khoản
                        </a>
                    </div>
                </div>
            </div>

            <div class="dashboard-row">
                <!-- Top Selling Products -->
                <div class="content-card">
                    <h2>Top Sản Phẩm Bán Chạy</h2>
                    <div class="table-responsive">
                        <table class="admin-table">
                            <thead>
                                <tr>
                                    <th>Tên Sản Phẩm</th>
                                    <th style="text-align: right;">Đã Bán</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tp" items="${TOP_PRODUCTS}">
                                    <tr>
                                        <td>${tp.productName}</td>
                                        <td style="text-align: right; font-weight: 600; color: var(--primary-color);">${tp.sold}</td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty TOP_PRODUCTS}">
                                    <tr><td colspan="2" style="text-align: center; color: #999; padding: 30px;">Chưa có dữ liệu.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Low Stock Warning -->
                <div class="content-card">
                    <h2 style="color: var(--danger-color);">Cảnh Báo Hết Hàng</h2>
                    <div class="low-stock-list">
                        <c:choose>
                            <c:when test="${not empty LOW_STOCK_PRODUCTS}">
                                <c:forEach var="p" items="${LOW_STOCK_PRODUCTS}">
                                    <div class="low-stock-item">
                                        <span class="p-name">${p.productName}</span>
                                        <span class="p-qty">SL: ${p.quantity}</span>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div style="text-align: center; padding: 40px 0; color: #999;">
                                    <i class="fas fa-check-circle" style="font-size: 40px; color: var(--success-color); margin-bottom: 15px; display: block;"></i>
                                    Kho hàng ổn định.
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>