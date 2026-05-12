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

@WebServlet(name = "MyOrdersController", urlPatterns = {"/orders"})
public class MyOrdersController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        OrderDAO orderDao = new OrderDAO();
        List<Order> list = orderDao.getOrdersByAccount(acc.getAccount());
        
        request.setAttribute("ORDERS_LIST", list);
        request.getRequestDispatcher("myOrders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
