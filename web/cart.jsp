<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ Hàng</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css"> 
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
    </head>
    <body>

        <%@ include file="/WEB-INF/layout/header.jspf" %>
        <div class="cart-container">

            <h1 class="cart-title">GIỎ HÀNG CỦA BẠN</h1>

            <c:if test="${empty requestScope.cart.items}">
                <p class="cart-empty">Giỏ hàng đang trống.</p>
                <div class="continue-shopping-container">
                    <a href="home" class="continue-shopping">
                        ← Tiếp tục mua hàng
                    </a>
                </div>
            </c:if>

            <c:if test="${not empty requestScope.cart.items}">
                
                <div class="cart-wrapper">

                    <div class="cart-left">
                        <div class="cart-header-text">
                            Bạn đang có ${fn:length(requestScope.cart.items)} sản phẩm trong giỏ hàng
                        </div>

                        <ul class="cart-item-list">
                            <c:forEach var="item" items="${requestScope.cart.items.values()}">
                                <li class="cart-item">
                                    <div class="cart-item-img">
                                        <img src="${pageContext.request.contextPath}${item.getProduct().getProductImage()}" 
                                             alt="${item.getProduct().getProductName()}">
                                    </div>

                                    <div class="cart-item-info">
                                        <h3>${item.getProduct().getProductName()}</h3>
                                        <p class="variant">WHITE / S</p> 
                                    </div>

                                    <div class="cart-item-qty">
                                        <form action="cart" method="post" class="qty-form">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="productId" value="${item.getProduct().getProductId()}">
                                            <input type="hidden" name="quantity" value="${item.getQuantity() - 1}">
                                            <button type="submit" class="qty-btn">-</button>
                                        </form>

                                        <span class="qty-number">${item.getQuantity()}</span>

                                        <form action="cart" method="post" class="qty-form">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="productId" value="${item.getProduct().getProductId()}">
                                            <input type="hidden" name="quantity" value="${item.getQuantity() + 1}">
                                            <button type="submit" class="qty-btn">+</button>
                                        </form>
                                    </div>

                                    <div class="cart-item-price">
                                        <c:set var="finalPrice" value="${item.getProduct().getPrice() - (item.getProduct().getPrice() * item.getProduct().getDiscount() / 100)}" />
                                        <fmt:formatNumber value="${finalPrice}" type="number" />₫
                                    </div>

                                    <div class="cart-item-subtotal">
                                        <span class="subtotal-label">Thành tiền:</span>
                                        <span class="subtotal-value">
                                            <fmt:formatNumber value="${finalPrice * item.getQuantity()}" type="number" />₫
                                        </span>
                                        <form action="cart" method="post" class="remove-form">
                                            <input type="hidden" name="action" value="remove">
                                            <input type="hidden" name="productId" value="${item.getProduct().getProductId()}">
                                            <button type="submit" class="remove-btn"><i class="far fa-trash-alt"></i></button>
                                        </form>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="cart-right">
                        <div class="continue-shopping-right">
                            <a href="index">Tiếp tục mua hàng <span>→</span></a>
                        </div>
                        <div class="order-summary-card">
                            <h3>Thông tin đơn hàng</h3>
                            <div class="total-row">
                                <span class="total-label">Tổng tiền:</span>
                                <span class="total-value">
                                   <fmt:formatNumber value="${requestScope.cart.getTotalPrice()}" type="number" />₫
                                </span>
                            </div>
                            <p class="summary-note">Bạn có thể nhập mã giảm giá ở trang thanh toán</p>
                            <a href="${pageContext.request.contextPath}/checkout" class="checkout-btn" style="text-decoration: none; display: block; text-align: center;">THANH TOÁN</a>
                        </div>
                    </div>

                </div>

            </c:if>

        </div>
 <%@ include file="/WEB-INF/layout/footer.jspf" %>
    </body>
</html>
