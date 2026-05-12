/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.dao.AccountDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "RemoveController", urlPatterns = {"/admin/remove"})
public class RemoveAccountController extends HttpServlet {

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
            out.println("<title>Servlet RemoveController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RemoveController at " + request.getContextPath() + "</h1>");
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
       Account loginUser = (Account) request.getSession().getAttribute("account");
         String accStr = request.getParameter("account");
    AccountDAO dao = new AccountDAO();
    Account targetAccount = dao.getObjectById(accStr);
    if (targetAccount == null) {
        response.sendRedirect("listacc");
        return;
    }

    int loginRole = loginUser.getRoleInSystem();
    int targetRole = targetAccount.getRoleInSystem();

    if (loginUser.getAccount().equals(accStr)) {
        response.sendRedirect("accessDenied");
        return;
    }

    if (loginRole == 1) {
        dao.deleteRec(targetAccount);
        response.sendRedirect("listacc");
        return;
    }
    
    if (loginRole == 2 && targetRole == 3) {
        dao.deleteRec(targetAccount);
        response.sendRedirect("listacc");
        return;
    }

    response.sendRedirect("accessDenied");
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
        processRequest(request, response);
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
