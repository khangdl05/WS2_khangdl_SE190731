package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Order;
import model.OrderDetail;
import model.dao.OrderDAO;
import model.dao.OrderDetailDAO;

@WebServlet(name = "OrderDetailController", urlPatterns = { "/orderdetail" })
public class OrderDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        String idRaw = request.getParameter("id");
        try {
            int orderId = Integer.parseInt(idRaw);
            OrderDAO orderDao = new OrderDAO();
            Order order = orderDao.getOrderById(orderId);

            if (order != null) {
                boolean isOwner = order.getAccount().getAccount().equals(acc.getAccount());
                boolean isAdmin = acc.getRoleInSystem() == 1 || acc.getRoleInSystem() == 2;

                if (isOwner || isAdmin) {
                    OrderDetailDAO detailDao = new OrderDetailDAO();
                    List<OrderDetail> details = detailDao.getDetailsByOrderId(orderId);

                    request.setAttribute("ORDER", order);
                    request.setAttribute("ORDER_DETAILS", details);
                    request.getRequestDispatcher("orderDetail.jsp").forward(request, response);
                } else {
                    response.sendRedirect("orders");
                }
            } else {
                response.sendRedirect("orders");
            }
        } catch (Exception e) {
            response.sendRedirect("orders");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
