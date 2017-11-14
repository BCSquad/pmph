package com.bc.pmpheep.back.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * <pre>
 * 功能描述：解决axios（POST，PUT，DELETE）请求方式跨域问题
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-12
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class ProcessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        //
        // httpServletResponse.setHeader("Access-Control-Allow-Headers",
        // "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                                      "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");

        httpServletResponse.setHeader("X-Powered-By", "Jetty");

        String method = httpServletRequest.getMethod();

        if (method.equals("OPTIONS")) {
            httpServletResponse.setStatus(200);
            return false;
        }
        // System.out.println(method);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
    // @Override
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
    // Object handler) throws Exception {
    // response.setHeader("Access-Control-Allow-Origin", "*");
    // response.setHeader("Access-Control-Allow-Credentials", "true");
    // response.setHeader("Access-Control-Allow-Headers",
    // "X-Requested-With, accept, content-type, xxxx");
    // response.setHeader("Access-Control-Allow-Methods",
    // "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
    // // 拦截器中增加，获取session代码
    // HttpSession session = request.getSession();
    // System.out.println("拦截器中的session的id是====" + session.getId());
    // Object obj = session.getAttribute(Const.SESSION_PMPH_USER);
    // System.out.println(obj);
    // return true;
    // }
}
