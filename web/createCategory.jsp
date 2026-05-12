<%-- 
    Document   : createCategory
    Created on : Feb 25, 2026, 2:21:15 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo danh mục</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/createCategory.css">
</head>
<body>
<%@ include file="/WEB-INF/layout/header.jspf" %>
<div class="category-create-wrapper">

    <div class="category-create-card">

        <h2 class="category-create-title">Tạo danh mục mới</h2>

        <form method="post" action="createcategory" class="category-create-form">

            <div class="category-create-group">
                <label>Tên danh mục</label>
                <input type="text" name="name" required>
            </div>

            <div class="category-create-group">
                <label>Memo</label>
                <input type="text" name="memo">
            </div>

            <button type="submit" class="category-create-btn">
                LƯU DANH MỤC
            </button>

        </form>

    </div>

</div>

</body>
</html>
