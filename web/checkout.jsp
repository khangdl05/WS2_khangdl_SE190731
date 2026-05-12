<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thanh toán</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <body>
        <div class="checkout-container">
            <!-- LEFT SIDE: Billing & Shipping -->
            <div class="checkout-left">
                <div class="brand-name">THANH TOÁN</div>

                <div class="breadcrumb">
                    <a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a> 
                    <i class="fa fa-chevron-right" style="font-size: 10px; margin: 0 5px;"></i> 
                    <span>Thông tin giao hàng</span>
                </div>

                <h2 class="section-title">Thông tin giao hàng</h2>

                <div class="user-status">
                    <div class="user-icon">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="user-details">
                        <p>${sessionScope.account.lastName} ${sessionScope.account.firstName} (${sessionScope.account.account})</p>
                        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                    </div>
                </div>

                <form action="placeorder" method="post" id="checkout-form">
                    <div class="form-group">
                        <input type="text" name="fullName" placeholder="Họ và tên" value="${sessionScope.account.lastName} ${sessionScope.account.firstName}" required>
                    </div>

                    <div class="form-row">
                        <div class="form-group" style="flex: 2;">
                            <input type="text" name="phone" placeholder="Số điện thoại" value="${sessionScope.account.phone}" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <input type="text" name="address" placeholder="Địa chỉ (Số nhà, tên đường...)" required>
                    </div>

                    <div class="form-row">

                        <!-- Province / City -->
                        <div class="form-group">
                            <select name="city" required>
                                <option value="">Chọn tỉnh / thành</option>
                                <option value="Hồ Chí Minh">TP. Hồ Chí Minh</option>
                                <option value="Hà Nội">Hà Nội</option>
                                <option value="Đà Nẵng">Đà Nẵng</option>
                                <option value="Cần Thơ">Cần Thơ</option>
                                <option value="Hải Phòng">Hải Phòng</option>
                                <option value="Bình Dương">Bình Dương</option>
                                <option value="Đồng Nai">Đồng Nai</option>
                                <option value="Khánh Hòa">Khánh Hòa</option>
                                <option value="Quảng Ninh">Quảng Ninh</option>
                                <option value="Huế">Huế</option>
                            </select>
                        </div>

                        <!-- District -->
                        <div class="form-group">
                            <select name="district" required>
                                <option value="">Chọn quận / huyện</option>
                                <option value="Quận 1">Quận 1</option>
                                <option value="Quận 2">Quận 2</option>
                                <option value="Quận 3">Quận 3</option>
                                <option value="Quận 4">Quận 4</option>
                                <option value="Quận 5">Quận 5</option>
                                <option value="Quận 7">Quận 7</option>
                                <option value="Quận 10">Quận 10</option>
                                <option value="Quận Bình Thạnh">Quận Bình Thạnh</option>
                                <option value="Quận Tân Bình">Quận Tân Bình</option>
                                <option value="Quận Gò Vấp">Quận Gò Vấp</option>
                            </select>
                        </div>

                        <!-- Ward -->
                        <div class="form-group">
                            <select name="ward" required>
                                <option value="">Chọn phường / xã</option>
                                <option value="Phường 1">Phường 1</option>
                                <option value="Phường 2">Phường 2</option>
                                <option value="Phường 3">Phường 3</option>
                                <option value="Phường 4">Phường 4</option>
                                <option value="Phường 5">Phường 5</option>
                                <option value="Phường 6">Phường 6</option>
                                <option value="Phường 7">Phường 7</option>
                                <option value="Phường 8">Phường 8</option>
                                <option value="Phường 9">Phường 9</option>
                                <option value="Phường 10">Phường 10</option>
                            </select>
                        </div>

                    </div>

                    <!-- Detail address -->
                    <div class="form-group">
                        <input type="text" name="detailAddress" placeholder="Số nhà, tên đường..." required>
                    </div>

                    <h2 class="section-title" style="margin-top: 30px;">Phương thức vận chuyển</h2>
                    <div style="border: 1px solid #d9d9d9; padding: 20px; border-radius: 4px; text-align: center; color: #737373; font-size: 14px;">
                        <i class="fa fa-box" style="font-size: 40px; margin-bottom: 10px; display: block;"></i>
                        Vui lòng chọn tỉnh / thành để có danh sách phương thức vận chuyển
                    </div>

                    <h2 class="section-title" style="margin-top: 30px;">Phương thức thanh toán</h2>
                    <div class="payment-box">
                        <div class="payment-option">
                            <input type="radio" name="paymentMethod" id="cod" value="COD" checked>
                            <label for="cod">
                                <i class="fa fa-money-bill-alt"></i>
                                Thanh toán khi giao hàng (COD)
                            </label>
                        </div>
                        <div class="payment-option">
                            <input type="radio" name="paymentMethod" id="bank" value="BANK">
                            <label for="bank">
                                <i class="fa fa-university"></i>
                                Chuyển khoản qua ngân hàng
                            </label>
                        </div>
                    </div>

                    <div class="checkout-footer">
                        <a href="${pageContext.request.contextPath}/cart" class="back-to-cart">Giỏ hàng</a>
                        <button type="submit" class="complete-btn">HOÀN TẤT ĐƠN HÀNG</button>
                    </div>
                </form>
            </div>

            <!-- RIGHT SIDE: Order Summary -->
            <div class="checkout-right">
                <div class="order-items">
                    <c:forEach var="item" items="${cart.items.values()}">
                        <div class="checkout-item">
                            <div class="item-img-wrapper">
                                <img src="${pageContext.request.contextPath}${item.product.productImage}" alt="${item.product.productName}">
                                <span class="item-qty-badge">${item.quantity}</span>
                            </div>
                            <div class="item-info">
                                <h4>${item.product.productName}</h4>
                                <p>${item.product.unit}</p>
                            </div>
                            <div class="item-price">
                                <fmt:formatNumber value="${item.subTotal}" type="number"/>₫
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="discount-box">
                    <input type="text" placeholder="Mã giảm giá">
                    <button type="button" class="apply-btn">Sử dụng</button>
                </div>

                <div class="order-summary">
                    <div class="summary-row">
                        <span>Tạm tính</span>
                        <span><fmt:formatNumber value="${cart.totalPrice}" type="number"/>₫</span>
                    </div>
                    <div class="summary-row">
                        <span>Phí vận chuyển</span>
                        <span>—</span>
                    </div>

                    <div class="summary-row total">
                        <span>Tổng cộng</span>
                        <div style="text-align: right;">
                            <span class="currency-code">VND</span> 
                            <span style="font-size: 20px;"><fmt:formatNumber value="${cart.totalPrice}" type="number"/>₫</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
