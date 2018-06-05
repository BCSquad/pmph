package com.bc.pmpheep.back.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.SysOperation;
import com.bc.pmpheep.back.service.SysOperationService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.DeviceUtils;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;

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
    Logger               logger = LoggerFactory.getLogger(OperationLogInterceptor.class);

    @Autowired
    SysOperationService  sysOperationService;
    /**
     * 不需要拦截的url请求方法集合
     */
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
        if (object instanceof HandlerMethod) {
            String requestUri = request.getRequestURI();// 完整请求路径
            String contextPath = request.getContextPath();// 项目路径
            String url = requestUri.substring(contextPath.length());// 模块路径
            Boolean isAccess = false;
            // 不拦截excludeUrls中配置的url请求
            for (String requestUrl : excludeUrls) {
                if (requestUrl.endsWith("/**")) {
                    if (url.startsWith(requestUrl.substring(0, requestUrl.length() - 3))) {
                        isAccess = true;
                        break;
                    }
                } else if (url.startsWith(requestUrl)) {
                    isAccess = true;
                    break;
                }
            }
            if (!isAccess) {
                HandlerMethod handlerMethod = (HandlerMethod) object;
                Class cls = handlerMethod.getBeanType();// 类名
                Method[] methods = cls.getMethods();// 类中的所有方法
                String logRemark = "";// 方法用途
                String businessType = "";// 业务类型
                HttpSession session = null;
                try {
                    if(null == request.getSession(false)){
                        session = request.getSession();
                    }else{
                        session = request.getSession(false);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.warn("session为空时出现异常：{}", e.getMessage());
                }
                if (ObjectUtil.notNull(session)) {
                    PmphUser pmphUser = (PmphUser) session.getAttribute(Const.SESSION_PMPH_USER);
                    if (ObjectUtil.notNull(pmphUser)) {
                        String subUrl = url.substring(url.lastIndexOf("/") + 1, url.length());// 调用接口方法
                        for (Method method : methods) {
                            LogDetail logObj = method.getAnnotation(LogDetail.class);
                            if (ObjectUtil.notNull(logObj) && method.getName().equals(subUrl)) {
                                logRemark = logObj.logRemark();
                                businessType = logObj.businessType();
                                break;
                            }
                        }
                        // 获取用户访问设备类型
                        String deviceType = DeviceUtils.isMobileDevice(request);
                        // 此处调用 sysOperationService 保存方法
                        sysOperationService.addSysOperation(new SysOperation(
                                                                             pmphUser.getId(),
                                                                             pmphUser.getUsername(),
                                                                             pmphUser.getRealname(),
                                                                             DateUtil.getCurrentTime(),
                                                                             url + "-" + logRemark,
                                                                             StringUtil.getClientIP(request),
                                                                             businessType,
                                                                             deviceType));
                    }
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
