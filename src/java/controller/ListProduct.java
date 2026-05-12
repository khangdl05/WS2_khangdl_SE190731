/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "listProduct", urlPatterns = {"/admin/listproduct", "/listproduct"})
public class ListProduct extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet listProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet listProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    ProductDAO dao = new ProductDAO();
    CategoryDAO cdao = new CategoryDAO();

    String cid = request.getParameter("cid");
    String keyword = request.getParameter("keyword");
    String sort = request.getParameter("sort");
    String minPrice = request.getParameter("minPrice");
    String maxPrice = request.getParameter("maxPrice");
    if (sort == null || sort.isEmpty()) {
        sort = "newest";
    }

    List<Product> listRes = dao.filterProducts(cid, keyword, sort, minPrice, maxPrice);

    String pageTitle = "Tất cả sản phẩm";

    if (keyword != null && !keyword.trim().isEmpty()) {
        pageTitle = "Kết quả tìm kiếm: \"" + keyword + "\"";
    }

    if (cid != null) {
        Category cate = cdao.getObjectById(cid);
        if (cate != null) {
            pageTitle = cate.getCategoryName();
        }
    }

    request.setAttribute("LIST_PRODUCTS", listRes);
    request.setAttribute("LIST_CATEGORY", cdao.listAll());
    request.setAttribute("PAGE_TITLE", pageTitle);

    request.getRequestDispatcher("/listProduct.jsp").forward(request, response);
}

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
