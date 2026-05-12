<%-- 
    Document   : updateCategory
    Created on : Feb 25, 2026, 8:48:41 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập nhật danh mục</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/updateCategory.css">
</head>
<body>
<%@ include file="/WEB-INF/layout/header.jspf" %>
<div class="category-update-wrapper">

    <div class="category-update-card">

        <h2 class="category-update-title">Cập nhật danh mục</h2>

        <form method="post"
              action="${pageContext.request.contextPath}/admin/updatecate"
              class="category-update-form">

           
            <div class="category-update-group">
                <label>ID Danh mục</label>
                <input type="text"
                       name="id"
                       value="${requestScope.cate.typeId}"
                       readonly
                       class="category-update-readonly"
                       disabled="">
                <input type="text"
                       name="id"
                       value="${requestScope.cate.typeId}"
                       readonly
                       class="category-update-readonly"
                       hidden="">
            </div>

            
            <div class="category-update-group">
                <label>Tên danh mục</label>
                <input type="text"
                       name="name"
                       value="${requestScope.cate.categoryName}"
                       required>
            </div>

           
            <div class="category-update-group">
                <label>Memo</label>
                <input type="text"
                       name="memo"
                       value="${requestScope.cate.memo}">
            </div>

            <button type="submit" class="category-update-btn">
                CẬP NHẬT DANH MỤC
            </button>

        </form>

    </div>

</div>

</body>
</html>
