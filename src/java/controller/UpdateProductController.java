/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.Account;
import model.Category;
import model.Product;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateProductController", urlPatterns = {"/admin/updateproduct"})
@MultipartConfig
public class UpdateProductController extends HttpServlet {

    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

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
            out.println("<title>Servlet UpdateProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProductController at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession(false);
        Account loginAcc = (Account) session.getAttribute("account");

        if (loginAcc == null
                || (loginAcc.getRoleInSystem() != 1
                && loginAcc.getRoleInSystem() != 2)) {

            response.sendRedirect(request.getContextPath() + "/accessdenied");
            return;
        }

        String id = request.getParameter("id");
        Product product = productDAO.getObjectById(id);

        List<Category> listCate = categoryDAO.listAll();

        request.setAttribute("product", product);
        request.setAttribute("LIST_CATEGORY", listCate);

        request.getRequestDispatcher("/updateProduct.jsp")
                .forward(request, response);

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

    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession(false);
    Account loginAcc = (Account) session.getAttribute("account");

    if (loginAcc == null
            || (loginAcc.getRoleInSystem() != 1
            && loginAcc.getRoleInSystem() != 2)) {

        response.sendRedirect(request.getContextPath() + "/accessdenied");
        return;
    }

    String id = request.getParameter("productId");
    String name = request.getParameter("productName");
    String brief = request.getParameter("brief");

    String dateStr = request.getParameter("postedDate");
    Date postedDate = null;

    if (dateStr != null && !dateStr.isEmpty()) {
        postedDate = Date.valueOf(dateStr);
    }

    int typeId = Integer.parseInt(request.getParameter("typeId"));
    String unit = request.getParameter("unit");

    int price = Integer.parseInt(request.getParameter("price"));
    int discount = Integer.parseInt(request.getParameter("discount"));

    int quantity = Integer.parseInt(request.getParameter("quantity"));
    
    Product ProductSold = productDAO.getObjectById(id);
    int sold = ProductSold.getSold();
    int status = Integer.parseInt(request.getParameter("status"));

    Part filePart = request.getPart("productImage");
    String fileName = filePart.getSubmittedFileName();

    String imagePath;

    if (fileName != null && !fileName.isEmpty()) {

        String realPath = getServletContext().getRealPath("/");
        String projectPath = realPath.replace("build\\web\\", "web\\");
        String uploadPath = projectPath + "images\\sanPham";

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        filePart.write(uploadPath + File.separator + fileName);

        imagePath = "/images/sanPham/" + fileName;

    } else {

        Product oldProduct = productDAO.getObjectById(id);
        imagePath = oldProduct.getProductImage();
    }

    Category cate = new Category(typeId);
    Account acc = loginAcc;

    Product product = new Product(
            id,
            name,
            imagePath,
            brief,
            postedDate,
            cate,
            acc,
            unit,
            price,
            discount,
            quantity,
            sold,
            status
    );

    productDAO.updateRec(product);

    response.sendRedirect("products");
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
