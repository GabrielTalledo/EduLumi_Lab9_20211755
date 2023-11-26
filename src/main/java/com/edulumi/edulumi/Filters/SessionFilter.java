package com.edulumi.edulumi.Filters;

import com.edulumi.edulumi.Beans.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(servletNames = {"CursosServlet", "DocentesServlet", "EvaluacionesServlet"})
public class SessionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            response.setHeader("Cache-Control", "no-cache, no-store , must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires",0);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
