/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.dao.AccountDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateController", urlPatterns = {"/admin/updateAccount", "/updateAccount"})
public class UpdateAccountController extends HttpServlet {

    AccountDAO dao = new AccountDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateController at " + request.getContextPath() + "</h1>");
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
        String accountParam = request.getParameter("account");
        Account targetAcc = dao.getObjectById(accountParam);

        if (targetAcc == null || !canEdit(loginAcc, targetAcc)) {
            response.sendRedirect("accessdenied");
            return;
        }
        request.setAttribute("acc", targetAcc);
        request.getRequestDispatcher("/updateAccount.jsp").forward(request, response);
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
        Account loginAcc = (Account) request.getSession().getAttribute("account");
        String accountParam = request.getParameter("account");
        Account targetAcc = dao.getObjectById(accountParam);

        if (targetAcc == null || !canEdit(loginAcc, targetAcc)) {
            response.sendRedirect("accessdenied");
            return;
        }
        String account = request.getParameter("account");
        String pass = request.getParameter("pass");
        String lname = request.getParameter("lname");
        String fname = request.getParameter("fname");
        String phone = request.getParameter("phone");
        String bdayStr = request.getParameter("bday");
        Date bday = Date.valueOf(bdayStr);
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        int role = targetAcc.getRoleInSystem();
        if (loginAcc.getRoleInSystem() == 1) {
            role = Integer.parseInt(request.getParameter("role"));
        }
        boolean isUse = targetAcc.isIsUse();
        if (loginAcc.getRoleInSystem() == 1) {
            isUse = request.getParameter("isUse") != null;
        }
        Account newacc = new Account(account, pass, lname, fname, bday, gender, phone, isUse, role);
        dao.updateRec(newacc);
        Account updatedAcc = dao.getObjectById(accountParam);
        if (loginAcc.getAccount().equals(accountParam)) {
            request.getSession().setAttribute("account", updatedAcc);
        }
        if (loginAcc.getRoleInSystem() == 1 || loginAcc.getRoleInSystem() == 2) {
            response.sendRedirect(request.getContextPath() + "/admin/listacc");
        } else {
            response.sendRedirect(request.getContextPath() + "/user/dashboard");
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

    private boolean canEdit(Account login, Account target) {
        int loginRole = login.getRoleInSystem();
        int targetRole = target.getRoleInSystem();

        if (loginRole == 1) {
            return true;
        }
        if (loginRole == 2 && targetRole == 3) {
            return true;
        }
        if (loginRole == 2 && targetRole == 2 && login.getAccount().equals(target.getAccount())) {
            return true;
        }
        if (loginRole == 3 && login.getAccount().equals(target.getAccount())) {
            return true;
        }
        return false;
    }
}
