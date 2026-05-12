package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Cart;
import model.dao.CartDAO;
import model.dao.CartItemDAO;
import model.dao.ProductDAO;

@WebServlet(name = "AddToCartController", urlPatterns = { "/addtocart" })
public class AddToCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("id");
        String quantityRaw = request.getParameter("quantity");

        int quantity = 1;
        try {
            if (quantityRaw != null && !quantityRaw.trim().isEmpty()) {
                quantity = Integer.parseInt(quantityRaw);
            }
        } catch (Exception e) {
        }

        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        if (productId != null && !productId.trim().isEmpty() && quantity > 0) {
            ProductDAO pDao = new ProductDAO();
            int stock = pDao.getProductQuantity(productId);

            CartDAO cartDao = new CartDAO();
            Cart cart = cartDao.getOrCreateCart(acc.getAccount());
            CartItemDAO itemDao = new CartItemDAO();
            int inCart = itemDao.getQuantityInCart(cart.getCartId(), productId);

            if (inCart + quantity > stock) {
                request.getSession().setAttribute("ERROR_MSG", "Sản phẩm này chỉ còn " + stock + " mặt hàng.");
            } else {
                if (cart != null) {
                    itemDao.addOrUpdate(cart.getCartId(), productId, quantity);
                    request.getSession().setAttribute("SUCCESS_MSG", "Đã thêm vào giỏ hàng!");
                }
            }
        }
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("listproduct");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
