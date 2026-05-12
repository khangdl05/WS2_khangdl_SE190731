<%-- Document : userSegments Created on : 2026 Author : ADMIN --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@page import="java.util.List" %>
            <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                    <fmt:setLocale value="vi_VN" />
                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                        <title>Phân khúc người dùng</title>
                        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userSegments.css">
                    </head>

                    <body>
                        <%@ include file="/WEB-INF/layout/header.jspf" %>

                            <div class="segment-container">

                                <!-- Header -->
                                <div class="segment-header">
                                    <h2>📊 Phân khúc người dùng</h2>
                                    <div class="segment-stat">
                                        Tổng lượt xem: <strong>${TOTAL_VIEWS}</strong>
                                    </div>
                                </div>

                                <!-- Summary cards (đếm từng phân khúc bằng JSTL) -->
                                <c:set var="countLow" value="0" />
                                <c:set var="countMid" value="0" />
                                <c:set var="countHigh" value="0" />
                                <c:forEach var="row" items="${USER_SEGMENTS}">
                                    <c:choose>
                                        <c:when test="${row[2] == 'Thu nhập thấp'}">
                                            <c:set var="countLow" value="${countLow + 1}" />
                                        </c:when>
                                        <c:when test="${row[2] == 'Thu nhập trung bình'}">
                                            <c:set var="countMid" value="${countMid + 1}" />
                                        </c:when>
                                        <c:when test="${row[2] == 'Thu nhập cao'}">
                                            <c:set var="countHigh" value="${countHigh + 1}" />
                                        </c:when>
                                    </c:choose>
                                </c:forEach>

                                <div class="segment-summary">
                                    <div class="summary-card card-low">
                                        <span class="card-label">🔵 Thu nhập thấp</span>
                                        <span class="card-count">${countLow}</span>
                                        <span>người dùng (dưới 1 triệu)</span>
                                    </div>
                                    <div class="summary-card card-mid">
                                        <span class="card-label">🟡 Thu nhập trung bình</span>
                                        <span class="card-count">${countMid}</span>
                                        <span>người dùng (1 – 2 triệu)</span>
                                    </div>
                                    <div class="summary-card card-high">
                                        <span class="card-label">🟢 Thu nhập cao</span>
                                        <span class="card-count">${countHigh}</span>
                                        <span>người dùng (trên 2 triệu)</span>
                                    </div>
                                </div>

                                <!-- Detail table -->
                                <div class="segment-table-wrapper">
                                    <table class="segment-table">
                                        <thead>
                                            <tr>
                                                <th class="segment-index">#</th>
                                                <th>Tài khoản</th>
                                                <th>Giá TB sản phẩm đã xem</th>
                                                <th>Phân khúc</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>
                                                <c:when test="${empty USER_SEGMENTS}">
                                                    <tr>
                                                        <td colspan="4" class="empty-msg">
                                                            Chưa có dữ liệu xem sản phẩm nào.
                                                        </td>
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach var="row" items="${USER_SEGMENTS}" varStatus="st">
                                                        <tr>
                                                            <td class="segment-index">${st.index + 1}</td>
                                                            <td><strong>${row[0]}</strong></td>
                                                            <td class="avg-price">
                                                                <fmt:formatNumber value="${row[1]}" type="number"
                                                                    groupingUsed="true" /> đ
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${row[2] == 'Thu nhập thấp'}">
                                                                        <span class="badge badge-low">🔵 Thu nhập
                                                                            thấp</span>
                                                                    </c:when>
                                                                    <c:when test="${row[2] == 'Thu nhập trung bình'}">
                                                                        <span class="badge badge-mid">🟡 Thu nhập trung
                                                                            bình</span>
                                                                    </c:when>
                                                                    <c:when test="${row[2] == 'Thu nhập cao'}">
                                                                        <span class="badge badge-high">🟢 Thu nhập
                                                                            cao</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-unknown">⚪ Chưa xác
                                                                            định</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </tbody>
                                    </table>
                                </div>

                            </div>

                    </body>

                    </html>