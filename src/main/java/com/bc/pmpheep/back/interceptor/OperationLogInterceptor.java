package com.bc.pmpheep.back.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.SysOperation;
import com.bc.pmpheep.back.service.SysOperationService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;

/**
 * 
 * <pre>
 * 功能描述：操作日志拦截器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-15
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class OperationLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    SysOperationService  sysOperationService;

    private List<String> excludeUrls;

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    /**
     * 
     * <pre>
     * 功能描述：在调用controller具体方法前拦截
     * 使用示范：
     *
     * @param request HttpServletRequest
     * @param response HttpServletRequest
     * @param object Object
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unused")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)
    throws Exception {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        return true;
    }

    /**
     * 
     * <pre>
     * 功能描述：在调用controller具体方法中，返回前拦截
     * 使用示范：
     *
     * @param request HttpServletRequest
     * @param response HttpServletRequest
     * @param object Object
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
    ModelAndView modelAndView) throws Exception {
        String requestUri = request.getRequestURI();// 完 整请求路径
        String contextPath = request.getContextPath();// 项目路径
        String url = requestUri.substring(contextPath.length());// 模块路径
        if (!url.matches(Const.NO_INTERCEPTOR_PATH)) {
            Class cls = Class.forName(object.getClass().getName());// 类名
            Method[] methods = cls.getMethods();
            String logRemark = "";// 方法用途
            // String sessionId = "";
            // Map<String, Cookie> map = CookiesUtil.ReadCookieMap(request);
            // for (Map.Entry<String, Cookie> entry : map.entrySet()) {
            // if ("sessionId".equals(entry.getKey())) {
            // sessionId = entry.getValue().getValue();
            // System.out.println("session-========" + sessionId);
            // }
            // }
            HttpSession session = request.getSession();
            if (ObjectUtil.notNull(session)) {
                // PmphUser pmphUser1 = SessionUtil.getPmphUser();
                PmphUser pmphUser = (PmphUser) session.getAttribute(Const.SESSION_PMPH_USER);
                // }
                // if (StringUtil.notEmpty(sessionId)) {
                // PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
                if (ObjectUtil.notNull(pmphUser)) {
                    System.out.println(pmphUser.toString());
                    String subUrl = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));// 模块地址
                    for (Method method : methods) {
                        LogDetail logObj = method.getAnnotation(LogDetail.class);
                        if (ObjectUtil.notNull(logObj) && method.getName().equals(subUrl)) {
                            System.out.println(method.getName());
                            logRemark = logObj.logRemark();
                            break;
                        }
                    }
                    System.out.println("logRemark===" + logRemark);
                    // 此处调用 sysOperationService 保存方法
                    sysOperationService.addSysOperation(new SysOperation(pmphUser.getId(),
                                                                         pmphUser.getUsername(),
                                                                         pmphUser.getRealname(),
                                                                         DateUtil.getCurrentTime(),
                                                                         url + "-" + logRemark,
                                                                         request.getRemoteHost()));
                    System.out.println("保存成功！");
                }
            }
        }

    }

    /**
     * 
     * <pre>
     * 功能描述：完成页面的render后调用
     * 使用示范：
     *
     * @param request HttpServletRequest
     * @param response HttpServletRequest
     * @param object Object
     * @param exception Exception
     * @throws Exception
     * </pre>
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
    Object handler, Exception ex) throws Exception {
    }

}
