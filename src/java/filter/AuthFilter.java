/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author ADMIN
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    
   
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
         HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

         String uri = req.getRequestURI();
    String contextPath = req.getContextPath();

    
    String path = uri.substring(contextPath.length());

    HttpSession session = req.getSession(false);
    boolean loggedIn = (session != null && session.getAttribute("account") != null);

    boolean publicPage =
            path.equals("/index")
            || path.equals("/login")
            || path.equals("/register")
            || path.equals("/listproduct")
            || path.startsWith("/css/")
            || path.startsWith("/js/")
            || path.startsWith("/images/")
            || path.contains("productdetail");

    if (!loggedIn && !publicPage) {
        resp.sendRedirect(contextPath + "/index");
        return;
    }

    if (path.endsWith(".jsp")) {
        resp.sendRedirect(contextPath + "/index");
        return;
    }

    chain.doFilter(request, response);
 
    }
    

}
