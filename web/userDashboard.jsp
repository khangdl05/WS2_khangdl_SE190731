<%-- 
    Document   : userDashboard
    Created on : Mar 2, 2026, 1:19:26 AM
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
        <title>Trang chủ</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userDashboard.css"> 
    </head>
    <body class="user-dashboard-page">
        <%@ include file="/WEB-INF/layout/header.jspf" %>   
  
        <a href="${pageContext.request.contextPath}/listproduct">
        <section class="hero-banner">
            <div class="hero-models">
            </div>
            <div class="hero-text-bottom">
                <h1></h1>
            </div>
        </section>
        </a>
 
        <div class="featured-products-wrapper">
            <h2>NEW ARRIVALS</h2>

            <div class="product-grid">
                <c:forEach var="pro" items="${GET_NEW_ARRIVALS_PRODUCTS}">
                    <a href="${pageContext.request.contextPath}/productdetail?id=${pro.productId}" class="col-product">
                        <div class="product-img-box">
                            <img src="${pageContext.request.contextPath}${pro.productImage}" alt="${pro.productName}">
                            <c:if test="${pro.discount > 0}">
                                <span class="hot-badge">-${pro.discount}%</span>
                            </c:if>
                            <span class="view-btn">QUICK VIEW</span>
                        </div>
                        <div class="product-info">
                            <h4 class="product-name">${pro.productName}</h4>
                            <div class="product-price">
                                <c:if test="${pro.discount > 0}">
                                    <span class="price-strikethrough">
                                        <fmt:formatNumber value="${pro.price}" type="number" groupingUsed="true"/>₫
                                    </span>
                                </c:if>
                                <span class="price-current">
                                    <fmt:formatNumber value="${pro.price - (pro.price * pro.discount / 100)}" type="number" groupingUsed="true"/>₫
                                </span>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>

        <!-- Recently Viewed Products -->
        <c:if test="${not empty RECENTLY_VIEWED_PRODUCTS}">
            <div class="featured-products-wrapper" style="margin-top: 10px;">
                <h2>SẢN PHẨM BẠN ĐÃ XEM</h2>
                
                <div class="horizontal-scroll-row">
                    <c:forEach var="pro" items="${RECENTLY_VIEWED_PRODUCTS}">
                        <a href="${pageContext.request.contextPath}/productdetail?id=${pro.productId}" class="col-product">
                            <div class="product-img-box">
                                <img src="${pageContext.request.contextPath}${pro.productImage}" alt="${pro.productName}">
                                <c:if test="${pro.discount > 0}">
                                    <span class="hot-badge">-${pro.discount}%</span>
                                </c:if>
                                <span class="view-btn">QUICK VIEW</span>
                            </div>
                            <div class="product-info">
                                <h4 class="product-name">${pro.productName}</h4>
                                <div class="product-price">
                                    <c:if test="${pro.discount > 0}">
                                        <span class="price-strikethrough">
                                            <fmt:formatNumber value="${pro.price}" type="number" groupingUsed="true"/>₫
                                        </span>
                                    </c:if>
                                    <span class="price-current">
                                        <fmt:formatNumber value="${pro.price - (pro.price * pro.discount / 100)}" type="number" groupingUsed="true"/>₫
                                    </span>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <%@ include file="/WEB-INF/layout/footer.jspf" %>
    </body>
</html>
