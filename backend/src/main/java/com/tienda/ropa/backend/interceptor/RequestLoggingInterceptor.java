package com.tienda.ropa.backend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.http.HttpResponse;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
            throws Exception {
        req.setAttribute("t0", System.currentTimeMillis());
        System.out.println("preHandle:" + req.getMethod() + " " + req.getRequestURI());
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex)
            throws Exception {
        Long t0 = (long) req.getAttribute("t0");
        long elapsed = (t0 == null) ? -1 : (System.currentTimeMillis() - t0);
        System.out.println("afterCompletion -> status: " + resp.getStatus() + " tiempo: " + elapsed + "ms");
    }

}
