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
import model.dao.OrderDAO;

@WebServlet(name = "AdminOrderController", urlPatterns = {"/admin/orders"})
public class AdminOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null || (acc.getRoleInSystem() != 1 && acc.getRoleInSystem() != 2)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        OrderDAO orderDao = new OrderDAO();

        if (action != null) {
            if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                orderDao.deleteOrder(id);
                response.sendRedirect("orders");
                return;
            } else if (action.equals("update")) {
                int id = Integer.parseInt(request.getParameter("id"));
                int state = Integer.parseInt(request.getParameter("state"));
                orderDao.updateOrderState(id, state);
                response.sendRedirect("orders");
                return;
            }
        }

        List<Order> orders = orderDao.getAllOrders();
        request.setAttribute("ORDERS_LIST", orders);
        request.getRequestDispatcher("/adminOrders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
