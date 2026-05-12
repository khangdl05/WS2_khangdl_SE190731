<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập nhật sản phẩm</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/updateProduct.css">

    </head>

    <body>

        <%@ include file="/WEB-INF/layout/header.jspf" %>

        <div class="product-update-wrapper">

            <div class="product-update-card">

                <h2 class="product-update-title">Cập nhật thông tin sản phẩm</h2>

                <c:if test="${not empty ERROR}">
                    <div class="product-create-error">
                        ${ERROR}
                    </div>
                </c:if>

                <form method="post"
                      enctype="multipart/form-data"
                      action="${pageContext.request.contextPath}/admin/updateproduct"
                      class="product-update-form">

                    <!-- ID -->

                    <div class="product-update-group">
                        <label>ID sản phẩm</label>
                        <input type="text"
                               name="productId"
                               value="${product.productId}"
                               readonly
                               class="product-update-readonly">
                    </div>

                    <!-- NAME -->

                    <div class="product-update-group">
                        <label>Tên sản phẩm</label>
                        <input type="text"
                               name="productName"
                               value="${product.productName}"
                               required>
                    </div>

                    <!-- IMAGE -->

                    <div class="product-update-group">
                        <label>Ảnh hiện tại</label>
                        <div class="product-update-image-box">
                            <img src="${pageContext.request.contextPath}${product.productImage}" width="120">
                        </div>
                    </div>

                    <div class="product-update-group">
                        <label>Ảnh mới</label>
                        <input type="file"
                               name="productImage"
                               accept="image/*">
                    </div>

                    <!-- BRIEF -->

                    <div class="product-update-group">
                        <label>Mô tả</label>
                        <input type="text"
                               name="brief"
                               value="${product.brief}">
                    </div>

                    <!-- DATE -->

                    <div class="product-update-group">
                        <label>Ngày đăng</label>
                        <input type="date"
                               name="postedDate"
                               value="${product.postedDate}">
                    </div>

                    <!-- CATEGORY -->

                    <div class="product-update-group">
                        <label>Mục</label>

                        <select name="typeId">

                            <c:forEach var="cate" items="${LIST_CATEGORY}">

                                <option value="${cate.typeId}"
                                        ${cate.typeId == product.type.typeId ? 'selected' : ''}>

                                    ${cate.categoryName}

                                </option>

                            </c:forEach>

                        </select>

                    </div>

                    <!-- UNIT -->

                    <div class="product-update-group">
                        <label>Đơn vị</label>
                        <input type="text"
                               name="unit"
                               value="${product.unit}">
                    </div>

                    <!-- PRICE -->

                    <div class="product-update-group">
                        <label>Giá</label>
                        <input type="number"
                               name="price"
                               value="${product.price}"
                               min="0"
                               required>
                    </div>

                    <!-- DISCOUNT -->

                    <div class="product-update-group">
                        <label>Giảm giá (%)</label>
                        <input type="number"
                               name="discount"
                               value="${product.discount}"
                               min="0"
                               max="100">
                    </div>

                    <!-- QUANTITY (THÊM) -->

                    <div class="product-update-group">
                        <label>Số lượng</label>
                        <input type="number"
                               name="quantity"
                               value="${product.quantity}"
                               min="0"
                               required>
                    </div>

                    <!-- STATUS -->

                    <div class="product-update-group">
                        <label>Trạng thái</label>

                        <select name="status">

                            <option value="1"
                                    ${product.status == 1 ? 'selected' : ''}>
                                Còn bán
                            </option>

                            <option value="0"
                                    ${product.status == 0 ? 'selected' : ''}>
                                Ngừng bán
                            </option>

                        </select>

                    </div>

                    <!-- SOLD (READONLY) -->

                    <div class="product-update-group">
                        <label>Đã bán</label>
                        <input type="number"
                               value="${product.sold}"
                               readonly>
                    </div>

                    <button type="submit" class="product-update-btn">
                        CẬP NHẬT SẢN PHẨM
                    </button>

                </form>

            </div>

        </div>

    </body>
</html>