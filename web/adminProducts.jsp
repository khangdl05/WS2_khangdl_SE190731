<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý sản phẩm | Admin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminProducts.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body class="admin-page">
    <%@ include file="/WEB-INF/layout/header.jspf" %>
    <div class="admin-container">
        <div class="admin-header">
            <div>
                <h1>QUẢN LÝ SẢN PHẨM</h1>
                <p>Danh sách toàn bộ sản phẩm trong hệ thống</p>
            </div>
            <a href="${pageContext.request.contextPath}/admin/createproduct" class="btn-create">
                <i class="fas fa-plus"></i> Thêm sản phẩm mới
            </a>
        </div>

        <div class="table-card">
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>Mã SP</th>
                        <th>Hình ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Giá gốc</th>
                        <th>Giảm giá</th>
                        <th>Kho</th>
                        <th>Đã bán</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${LIST_PRODUCTS}">
                        <tr>
                            <td>${p.productId}</td>
                            <td>
                                <img src="${pageContext.request.contextPath}${p.productImage}" alt="${p.productName}" class="product-thumb">
                            </td>
                            <td class="product-name">${p.productName}</td>
                            <td><fmt:formatNumber value="${p.price}" type="number"/>đ</td>
                            <td>${p.discount}%</td>
                            <td>
                                <span class="${p.quantity < 5 ? 'low-stock' : ''}">
                                    ${p.quantity}
                                </span>
                            </td>
                            <td>${p.sold}</td>
                            <td>
                                <span class="status-badge ${p.status == 1 ? 'status-active' : 'status-inactive'}">
                                    ${p.status == 1 ? 'Đang bán' : 'Ngừng bán'}
                                </span>
                            </td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/admin/updateproduct?id=${p.productId}" class="action-btn edit" title="Chỉnh sửa">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/admin/removeproduct?id=${p.productId}" 
                                   class="action-btn delete" 
                                   title="Xóa"
                                   onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">
                                    <i class="fas fa-trash-alt"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
                 <%@ include file="/WEB-INF/layout/footer.jspf" %>
</body>
</html>
