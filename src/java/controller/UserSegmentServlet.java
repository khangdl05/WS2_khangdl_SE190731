/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserView;
import model.dao.UserViewDAO;

/**
 * Admin servlet: xem phân khúc thu nhập của tất cả người dùng
 * dựa trên lịch sử sản phẩm đã xem.
 *
 * URL: /admin/user-segments
 *
 * @author ADMIN
 */
@WebServlet(name = "UserSegmentServlet", urlPatterns = {"/admin/user-segments"})
public class UserSegmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserViewDAO dao = new UserViewDAO();

        // Danh sách [account,avgPrice,segment] của tất cả user
        List<Object[]> segments = dao.getAllUserSegments();
        request.setAttribute("USER_SEGMENTS", segments);

        
        List<UserView> allViews = dao.listAll();
        request.setAttribute("TOTAL_VIEWS", allViews.size());

        request.getRequestDispatcher("/userSegments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Admin - Phân khúc người dùng theo lịch sử xem sản phẩm";
    }
}
