package blog.entity;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import blog.service.RESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenManager extends OncePerRequestFilter {

    @Autowired
    private RESTService restService;

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        if (req.getRequestURL().toString().contains("j_spring_security_logout"))
            revokeToken(req);
        if (req.getRequestURL().toString().contains("static/j_spring_security_check"))
            setRESTServiceToken(req);
        chain.doFilter(req, res);
    }

    private void setRESTServiceToken(HttpServletRequest req) {
        User user = new User();
        user.setPassword(req.getParameter("j_password"));
        user.setUsername(req.getParameter("j_username"));
        String token = restService.getToken(user);
        if (token != null) req.getSession().setAttribute("token", token);
    }

    private void revokeToken(HttpServletRequest req) {
        req.getSession().setAttribute("token", null);
    }
}