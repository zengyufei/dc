package com.zyf.dc.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求支持跨域
 * @author zengyufei
 * @since 1.0.0
 */
@WebFilter
@Component
public class AllowOriginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest reqs = (HttpServletRequest) req;
        String acao = "Access-Control-Allow-Origin";
        String acac = "Access-Control-Allow-Credentials";
        String acam = "Access-Control-Allow-Methods";
        String acma = "Access-Control-Max-Age";
        String acah = "Access-Control-Allow-Headers";

        String aTrue = "true";
        String origin = "Origin";
        String methods = "POST, GET, OPTIONS, DELETE";
        String outOfTime = "3600";
        String xrw = "x-requested-with";

        response.setHeader(acac, aTrue);
        response.setHeader(acao, reqs.getHeader(origin));
        response.setHeader(acam, methods);
        response.setHeader(acma, outOfTime);
        response.setHeader(acah, xrw);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}