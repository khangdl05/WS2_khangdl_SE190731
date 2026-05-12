<%-- 
    Document   : listProduct
    Created on : Feb 25, 2026, 11:31:53 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sản phẩm</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/layout/header.jspf" %>
        <div class="featured-products">

<div class="list-header">

    <h2>${PAGE_TITLE}</h2>

    <div class="filter-bar">

        <!-- PRICE FILTER -->
        <div class="price-dropdown">

    <button class="filter-btn">
        Lọc Theo Giá ▼
    </button>

    <div class="price-menu">

        <form action="${pageContext.request.contextPath}/listproduct" method="get">

            <!-- giữ filter cũ -->
            <input type="hidden" name="cid" value="${param.cid}">
            <input type="hidden" name="keyword" value="${param.keyword}">
            <input type="hidden" name="sort" value="${param.sort}">

            <div class="price-inputs">

                <input type="number"
                       name="minPrice"
                       placeholder="Tối thiểu"
                       value="${param.minPrice != null ? param.minPrice : ''}">

                <span>-</span>

                <input type="number"
                       name="maxPrice"
                       placeholder="Tối đa"
                       value="${param.maxPrice}">

            </div>

            <button type="submit" class="apply-btn">
                Apply
            </button>

            <div class="price-range">

                <a href="listproduct?minPrice=0&maxPrice=700000">
                    0 - 700K
                </a>

                <a href="listproduct?minPrice=700000&maxPrice=2000000">
                    700K - 2M
                </a>

            </div>

        </form>

    </div>

</div>


        <!-- SORT -->
        <form action="${pageContext.request.contextPath}/listproduct" method="get">

            <input type="hidden" name="cid" value="${param.cid}">
            <input type="hidden" name="keyword" value="${param.keyword}">
            <input type="hidden" name="minPrice" value="${param.minPrice}">
            <input type="hidden" name="maxPrice" value="${param.maxPrice}">

            <select name="sort" onchange="this.form.submit()" class="sort-select">

                <option value="featured"
                        ${param.sort == 'featured' ? 'selected' : ''}>
                    BÁN CHẠY
                </option>

                <option value="priceAsc"
                        ${param.sort == 'priceAsc' ? 'selected' : ''}>
                    GIÁ: TĂNG DẦN
                </option>

                <option value="priceDesc"
                        ${param.sort == 'priceDesc' ? 'selected' : ''}>
                    GIÁ: GIẢM DẦN
                </option>

                <option value="oldest"
                        ${param.sort == 'oldest' ? 'selected' : ''}>
                    CŨ NHẤT
                </option>

                <option value="newest"
                        ${empty param.sort || param.sort == 'newest' ? 'selected' : ''}>
                    MỚI NHẤT
                </option>

                <option value="sale"
                        ${param.sort == 'sale' ? 'selected' : ''}>
                    SALE
                </option>

            </select>

        </form>

    </div>

</div>


    <c:if test="${sessionScope.account.roleInSystem == 1 
                  || sessionScope.account.roleInSystem == 2}">
        <a href="${pageContext.request.contextPath}/admin/createproduct"
           class="add-product-btn">
            + Thêm sản phẩm
        </a>
    </c:if>


            <div class="product-list">
                <c:forEach items="${requestScope.LIST_PRODUCTS}" var="pro">

                    <div class="product-card">      
                        <div class="image-wrapper">

                            <a href="${pageContext.request.contextPath}/productdetail?id=${pro.productId}">
                                <img src="${pageContext.request.contextPath}${pro.productImage}">
                            </a>

                            <c:if test="${pro.discount > 0}">
                                <span class="discount-badge">
                                    -${pro.discount}%
                                </span>
                            </c:if>

                            <div class="hover-actions">

                                <a href="${pageContext.request.contextPath}/addtocart?id=${pro.productId}" 
                                   class="cart-btn">
                                    Thêm vào giỏ
                                </a>

                                <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${pro.productId}">
                        <button type="submit" class="btn-buy-now">
                            MUA NGAY
                        </button>
                    </form>

                            </div>
                        </div>

                        <h4>${pro.productName}</h4>

                        <div class="price-box">
                            <c:if test="${pro.discount > 0}">
                                <span class="old-price">
                                    <fmt:formatNumber value="${pro.price}" type="number" groupingUsed="true"/> đ
                                </span>
                            </c:if>

                            <span class="new-price">
                                <fmt:formatNumber 
                                    value="${pro.price - (pro.price * pro.discount / 100)}" 
                                    type="number" 
                                    groupingUsed="true"/> đ
                            </span>
                        </div>


                        <c:if test="${sessionScope.account.roleInSystem == 1 
                                      || sessionScope.account.roleInSystem == 2}">
                              <div class="admin-mini-actions">
                                  <a class="update-btn"
                                     href="${pageContext.request.contextPath}/admin/updateproduct?id=${pro.productId}">
                                      Update
                                  </a>

                                  <a class="remove-btn"
                                     href="${pageContext.request.contextPath}/admin/removeproduct?id=${pro.productId}">
                                      Remove
                                  </a>
                              </div>
                        </c:if>

                    </div>

                </c:forEach>
            </div>
        </div>
                    <br><br><br><br>
                     <%@ include file="/WEB-INF/layout/footer.jspf" %>
    </body>
    
</html>
