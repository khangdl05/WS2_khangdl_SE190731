/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.dao.AccountDAO;
import utils.SessionManager;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AuthController", urlPatterns = {"/login", "/register", "/logout"})
public class AuthController extends HttpServlet {

    private final AccountDAO dao = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("login")) {
            getLogin(request, response);
        } else if (uri.contains("register")) {
            getRegister(request, response);
        } else if (uri.contains("logout")) {
            getLogout(request, response);
        }
    }

    private void getLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void getRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private void getLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Account acc = (Account) session.getAttribute("account");

            if (acc != null) {
                SessionManager.removeSession(acc.getAccount());
            }

            session.invalidate();
        }

        response.sendRedirect(request.getContextPath() + "/index");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("login")) {
            postLogin(request, response);
        } else if (uri.contains("register")) {
            postRegister(request, response);
        }
    }

    private void postLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        Account loginAcc = dao.findByUsername(username);

        if (loginAcc == null || !loginAcc.getPass().equals(password)) {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (!loginAcc.isIsUse()) {
            request.setAttribute("error", "Tài khoản của bạn đã bị khóa. Liên hệ admin để mở khóa!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        HttpSession oldSession = SessionManager.getSession(username);

        if (oldSession != null) {
            try {
                oldSession.invalidate();
            } catch (IllegalStateException e) {
                
            }
            SessionManager.removeSession(username);
        }

        HttpSession session = request.getSession();
        session.setAttribute("account", loginAcc);

        SessionManager.addSession(username, session);

        if ("1".equals(remember)) {
            Cookie c = new Cookie("USERNAME_COOKIE", username);
            c.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(c);
        } else {
            Cookie c = new Cookie("USERNAME_COOKIE", "");
            c.setMaxAge(0);
            response.addCookie(c);
        }

        switch (loginAcc.getRoleInSystem()) {
            case 1:
            case 2:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
            case 3:
                response.sendRedirect(request.getContextPath() + "/user/dashboard");
                break;
        }

    }

    private void postRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String account = request.getParameter("account");
        String pass = request.getParameter("pass");
        String confirmPass = request.getParameter("confirmPass");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String phone = request.getParameter("phone");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));

        request.setAttribute("account", account);
        request.setAttribute("lastName", lastName);
        request.setAttribute("firstName", firstName);
        request.setAttribute("phone", phone);
        request.setAttribute("gender", gender);

        if (!pass.equals(confirmPass)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (dao.findByUsername(account) != null) {
            request.setAttribute("error", "Tài khoản đã tồn tại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("\\d{10}")) {
            request.setAttribute("error", "Số điện thoại phải gồm 10 chữ số!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Account newAcc = new Account(
                account,
                pass,
                lastName,
                firstName,
                null,
                gender,
                phone,
                true,
                3
        );

        int result = dao.insertRec(newAcc);

        if (result == 1) {
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Đăng ký thất bại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
