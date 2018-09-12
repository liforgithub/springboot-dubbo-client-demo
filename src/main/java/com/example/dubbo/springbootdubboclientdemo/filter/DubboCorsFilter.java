package com.example.dubbo.springbootdubboclientdemo.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DubboCorsFilter extends OncePerRequestFilter {

    @Override
    protected void initFilterBean() throws ServletException {

        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                this.getServletContext());
        super.initFilterBean();
    }

    @Override
    public void doFilterInternal(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final FilterChain chain) throws IOException, ServletException {
        // Set Cors headers.
        String host = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", host);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,jwtJson,Captcha-Id");

        chain.doFilter(request, response);

    }

}