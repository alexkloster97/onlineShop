package by.bsuir.shop.filter;

import by.bsuir.shop.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security filter
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = "/jsp/admin/*")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        User user = (User) session.getAttribute("user");

        if(user==null || user.getRights()!= "ROLE_ADMIN") {
            ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath()+"/jsp/error/access-denied.jsp");
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
