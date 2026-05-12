<%-- 
    Document   : createProduct
    Created on : Feb 26, 2026, 10:07:23 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo sản phẩm</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/createProduct.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/layout/header.jspf" %>
        <div class="product-create-wrapper">

            <div class="product-create-card">

                <h2 class="product-create-title">Tạo sản phẩm mới</h2>

                <c:if test="${not empty ERROR}">
                    <div class="product-create-error">
                        ${ERROR}
                    </div>
                </c:if>

                <form action="createproduct"
                      method="post"
                      enctype="multipart/form-data"
                      class="product-create-form">

                    <div class="product-create-group">
                        <label>Id Sản phẩm</label>
                        <input type="text" name="productId" required>
                    </div>

                    <div class="product-create-group">
                        <label>Tên sản phẩm</label>
                        <input type="text" name="productName" required>
                    </div>

                    <div class="product-create-group">
                        <label>Ảnh sản phẩm</label>
                        <input type="file"
                               name="productImage"
                               accept=".jpg,.jpeg,.png,.webp">
                        <div class="product-create-group">
                            <label>Mô tả</label>
                            <input type="text" name="brief">
                        </div>

                        <div class="product-create-group">
                            <label>Ngày đăng</label>
                            <input type="date" name="postedDate">
                        </div>

                        <div class="product-create-group">
                            <label>Mục</label>
                            <select name="typeId">
                                <c:forEach var="cate" items="${LIST_CATEGORY}">
                                    <option value="${cate.typeId}">
                                        ${cate.categoryName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="product-create-group">
                            <label>Tài khoản đăng</label>


                            <input type="text"
                                   value="${sessionScope.account.account}"
                                   readonly
                                   class="product-create-readonly">


                            <input type="hidden"
                                   name="account"
                                   value="${sessionScope.account.account}">
                        </div>

                        <div class="product-create-group">
                            <label>Đơn vị</label>
                            <input type="text" name="unit">
                        </div>


                        <div class="product-create-group">
                            <label>Giá</label>
                            <input type="number"
                                   name="price"
                                   min="0"
                                   step="0.01"
                                   required>
                        </div>


                        <div class="product-create-group">
                            <label>Giảm giá (%)</label>
                            <input type="number"
                                   name="discount"
                                   min="0"
                                   max="100"
                                   step="1"
                                   required>
                        </div>
                        <div class="product-create-group">
                            <label>Số lượng</label>
                            <input type="number"
                                   name="quantity"
                                   min="0"
                                   required>
                        </div>
                        <div class="product-create-group">
                            <label>Trạng thái</label>
                            <select name="status">

                                <option value="1">
                                    Đang bán
                                </option>

                                <option value="0">
                                    Ngừng bán
                                </option>

                            </select>
                        </div>
                        <button type="submit" class="product-create-btn">
                            Thêm sản phẩm
                        </button>

                </form>

            </div>

        </div>

    </body>
</html>
