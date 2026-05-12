/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Category;
import model.Product;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CreateProductController", urlPatterns = {"/admin/createproduct"})
@MultipartConfig
public class CreateProductController extends HttpServlet {

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
            out.println("<title>Servlet CreateProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateProductController at " + request.getContextPath() + "</h1>");
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
        CategoryDAO dao = new CategoryDAO();
        List<Category> listCate = dao.listAll();

        request.setAttribute("LIST_CATEGORY", listCate);
        request.getRequestDispatcher("/createProduct.jsp").forward(request, response);
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
         request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");

    String error = null;

    try {

        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String brief = request.getParameter("brief");
        String dateStr = request.getParameter("postedDate");
        String typeIdStr = request.getParameter("typeId");
        String unit = request.getParameter("unit");
        String priceStr = request.getParameter("price");
        String discountStr = request.getParameter("discount");
        String quantityStr = request.getParameter("quantity");
        int sold = 0;
        int status = Integer.parseInt(request.getParameter("status"));
        // ===== VALIDATE =====
        
        if (productId == null || productId.trim().isEmpty()) {
            error = "Product ID không được để trống!";
        }

        Date postedDate = null;
        try {
            postedDate = Date.valueOf(dateStr);
        } catch (Exception e) {
            error = "Ngày không hợp lệ!";
        }

        int typeId = 0;
        try {
            typeId = Integer.parseInt(typeIdStr);
        } catch (Exception e) {
            error = "Category không hợp lệ!";
        }

        int price = 0;
        try {
            price = Integer.parseInt(priceStr);
            if (price < 0) {
                error = "Price phải >= 0!";
            }
        } catch (Exception e) {
            error = "Price phải là số!";
        }

        int discount = 0;
        try {
            discount = Integer.parseInt(discountStr);
            if (discount < 0 || discount > 100) {
                error = "Discount phải từ 0 đến 100!";
            }
        } catch (Exception e) {
            error = "Discount phải là số!";
        }

        ProductDAO dao = new ProductDAO();
        if (dao.isExist(productId)) {
            error = "Product ID đã tồn tại!";
        }
        
        int quantity = 0;

        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity < 0) {
                error = "Quantity phải >= 0!";
            }
        } catch (Exception e) {
            error = "Quantity phải là số!";
        }

        if (error != null) {
            request.setAttribute("ERROR", error);

            CategoryDAO cateDAO = new CategoryDAO();
            request.setAttribute("LIST_CATEGORY", cateDAO.listAll());

            request.getRequestDispatcher("/createProduct.jsp")
                   .forward(request, response);
            return;
        }


        Part filePart = request.getPart("productImage");
        String imagePath = "";

        if (filePart != null && filePart.getSize() > 0) {

            String fileName = filePart.getSubmittedFileName();

            if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
                request.setAttribute("ERROR", "Chỉ được upload file ảnh!");
                request.getRequestDispatcher("/createProduct.jsp")
                       .forward(request, response);
                return;
            }

            String uploadPath = getServletContext()
                    .getRealPath("/images/sanPham");

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            filePart.write(uploadPath + File.separator + fileName);
            imagePath = "/images/sanPham/" + fileName;
        }

        Category cate = new Category(typeId);

        Account acc = (Account) request.getSession()
                                       .getAttribute("account");

        Product pro = new Product(
        productId,
        productName,
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

        dao.insertRec(pro);
        response.sendRedirect("listproduct");

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("ERROR", error);
        request.getRequestDispatcher("/createProduct.jsp")
               .forward(request, response);
    }

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
