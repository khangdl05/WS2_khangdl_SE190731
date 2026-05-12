package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Product;
import model.dao.ProductDAO;

@WebServlet(name = "AdminProductController", urlPatterns = {"/admin/products"})
public class AdminProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null || (acc.getRoleInSystem() != 1 && acc.getRoleInSystem() != 2)) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        ProductDAO productDao = new ProductDAO();
        List<Product> list = productDao.listAll();
        request.setAttribute("LIST_PRODUCTS", list);

        request.getRequestDispatcher("/adminProducts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
