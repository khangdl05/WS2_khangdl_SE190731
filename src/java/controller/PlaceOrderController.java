package controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderDetail;
import model.dao.CartItemDAO;
import model.dao.OrderDAO;
import model.dao.OrderDetailDAO;
import model.dao.ProductDAO;
import utils.CartUtils;

@WebServlet(name = "PlaceOrderController", urlPatterns = {"/placeorder"})
public class PlaceOrderController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        // 1Lấy giỏ hàng của user
        Cart cart = CartUtils.getCartFromDB(acc);
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        // Lấy thông tin gioa hàng
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        
        String fullAddress = address + ", " + ward + ", " + district + ", " + city;

        // Check kho
        ProductDAO productDao = new ProductDAO();
        StringBuilder stockError = new StringBuilder();
        for (CartItem item : cart.getItems().values()) {
            int stock = productDao.getProductQuantity(item.getProduct().getProductId());
            if (item.getQuantity() > stock) {
                stockError.append("Sản phẩm '")
                          .append(item.getProduct().getProductName())
                          .append("' chỉ còn ")
                          .append(stock)
                          .append(" mặt hàng. ");
            }
        }
        
        if (stockError.length() > 0) {
            request.getSession().setAttribute("ERROR_MSG", stockError.toString());
            response.sendRedirect("cart");
            return;
        }

        // 3. tạo order
        Order order = new Order();
        order.setAccount(acc);
        order.setCustName(fullName);
        order.setCustPhone(phone);
        order.setCustAddr(fullAddress);
        order.setCreatedDate(new Date(System.currentTimeMillis()));
        order.setTotalValue((int) cart.getTotalPrice());
        order.setOrdState(0); 

        OrderDAO orderDao = new OrderDAO();
        int orderId = orderDao.insertOrder(order);

        if (orderId > 0) {
            order.setOrderId(orderId);
            
            OrderDetailDAO detailDao = new OrderDetailDAO();
            CartItemDAO cartItemDao = new CartItemDAO();

            // 5. insert từng product vào orderdetails
            for (CartItem item : cart.getItems().values()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(order);
                detail.setProduct(item.getProduct());
                detail.setQuantity(item.getQuantity());
                detail.setPrice(item.getProduct().getPrice());
                detail.setDiscount(item.getProduct().getDiscount());
                
                detailDao.insertOrderDetail(detail);
                
                // cập nhật lại kho
                productDao.updateProductQuantityAfterOrder(item.getProduct().getProductId(), item.getQuantity());
            }

            // 7.xóa sản phẩm trong giỏ hàng sau khi tạo đơn thành công
            cartItemDao.clearCart(cart.getCartId());
            
            request.setAttribute("SUCCESS_MSG", "Đơn hàng của bạn đã được đặt thành công!");
            request.getRequestDispatcher("orderSuccess.jsp").forward(request, response);
        } else {
            response.sendRedirect("checkout?error=failed");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("cart");
    }
}
