/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 跨域过滤器
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class CORSFilter implements Filter {

    private final Logger LOG = LoggerFactory.getLogger(CORSFilter.class);

    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        LOG.debug("This is CORSFilter, url :{}", req.getRequestURI());
        HttpServletResponse resp = (HttpServletResponse) response;
//        resp.setHeader("Access-Control-Allow-Origin", "http://120.76.221.250:20802");
//        resp.setHeader("Access-Control-Allow-Credentials", "true");
//        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        resp.setHeader("Access-Control-Max-Age", "3600");
////        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");  
        fc.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}
