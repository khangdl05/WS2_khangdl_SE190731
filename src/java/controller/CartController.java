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
import utils.CartUtils;

@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }
        
        Cart cart = CartUtils.getCartFromDB(acc);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }
        
        String action = request.getParameter("action");
        String productId = request.getParameter("productId");
        
        Cart cart = new CartDAO().getCartByAccount(acc.getAccount());
        if (cart != null) {
            CartItemDAO itemDao = new CartItemDAO();
            ProductDAO pDao = new ProductDAO();
            
            if ("update".equals(action)) {
                String quantityRaw = request.getParameter("quantity");
                try {
                    int quantity = Integer.parseInt(quantityRaw);
                    int stock = pDao.getProductQuantity(productId);
                    if (quantity > stock) {
                        request.getSession().setAttribute("ERROR_MSG", "Sản phẩm này chỉ còn " + stock + " mặt hàng.");
                    } else {
                        itemDao.updateQuantity(cart.getCartId(), productId, quantity);
                    }
                } catch (Exception e) {}
            } else if ("remove".equals(action)) {
                itemDao.deleteItem(cart.getCartId(), productId);
            } else if ("clear".equals(action)) {
                itemDao.clearCart(cart.getCartId());
            } else if ("add".equals(action)) {
                int stock = pDao.getProductQuantity(productId);
                int inCart = itemDao.getQuantityInCart(cart.getCartId(), productId);
                if (inCart + 1 > stock) {
                    request.getSession().setAttribute("ERROR_MSG", "Sản phẩm này chỉ còn " + stock + " mặt hàng.");
                } else {
                    itemDao.addOrUpdate(cart.getCartId(), productId, 1);
                }
            }
        }
        response.sendRedirect("cart");
    }
}
