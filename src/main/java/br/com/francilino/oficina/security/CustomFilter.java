package br.com.francilino.oficina.security;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Cookie[] allCookies = req.getCookies();
        if (allCookies != null) {
            Cookie session
                    = Arrays.stream(allCookies).filter(x -> x.getName().equals("JSESSIONID"))
                            .findFirst().orElse(null);

            if (session != null) {
                res.setHeader("Set-Cookie",
                        session.getName() + "=" + session.getValue()
                        + ";HostOnly; "
                        + "HttpOnly; "
                        + "Secure; "
                        + "SameSite=strict");
            }
        }

        chain.doFilter(req, res);
    }
}
