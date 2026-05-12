<%-- 
    Document   : productDetail
    Created on : Mar 3, 2026, 1:22:46 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/productDetail.css">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <title>${PRODUCT.productName} | Details</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/layout/header.jspf" %>

        <div class="product-detail-wrapper">
            <!-- Left: Information Column -->
            <div class="info-sidebar">
                <h3>THÔNG TIN</h3>
                <div class="info-content">
                    <p>${PRODUCT.brief}</p>
                    <ul style="margin-top: 20px;">
                        <li>Danh mục: ${PRODUCT.type.categoryName}</li>
                        <li>Đơn vị: ${PRODUCT.unit}</li>
                        <li>Ngày đăng: <fmt:formatDate value="${PRODUCT.postedDate}" pattern="dd/MM/yyyy"/></li>
                    </ul>
                </div>
            </div>

            <!-- Center: Product Image -->
            <div class="image-main">
                <img src="${pageContext.request.contextPath}${PRODUCT.productImage}" alt="${PRODUCT.productName}">
            </div>

            <!-- Right: Action Column -->
            <div class="action-sidebar">
                <h1>${PRODUCT.productName}</h1>
                <div class="product-id">Mã đơn hàng: ${PRODUCT.productId}</div>
                
                <div class="price">
                    <fmt:formatNumber 
                        value="${PRODUCT.price - (PRODUCT.price * PRODUCT.discount / 100)}" 
                        type="number" 
                        groupingUsed="true"/>₫
                </div>

                <div class="short-desc-label">Mô tả</div>
                <div class="short-desc">
                    ${PRODUCT.brief.length() > 150 ? PRODUCT.brief.substring(0, 150).concat("...") : PRODUCT.brief}
                </div>

                <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/addtocart?id=${PRODUCT.productId}" class="btn-add-to-cart">
                        THÊM VÀO GIỎ
                    </a>
                    
                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${PRODUCT.productId}">
                        <button type="submit" class="btn-buy-now">
                            MUA NGAY
                        </button>
                    </form>
                </div>

                <a href="javascript:history.back()" style="margin-top: 30px; font-size: 13px; color: #888; text-decoration: none;">
                    ← QUAY LẠI
                </a>
            </div>
        </div>
                        <%@ include file="/WEB-INF/layout/footer.jspf" %>
    </body>
</html>