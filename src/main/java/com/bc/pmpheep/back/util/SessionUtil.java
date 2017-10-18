package com.bc.pmpheep.back.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.sessioncontext.SessionContext;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：获取Shiro Session
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-9-20
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class SessionUtil {

    /**
     * 
     * <pre>
     * 功能描述：获取ShiroSession
     * 使用示范：
     *          Subject currentUser = SecurityUtils.getSubject();
     *          Session session = currentUser.getSession();
     *          User user = (User) session.getAttribute(Const.SESSION_USER);
     * @return
     * </pre>
     */
    public static Session getShiroSessionUser() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return session;
    }

    /**
     * 获取社内用户
     * 
     * @introduction
     * @author Mryang
     * @createDate 2017年9月23日 下午8:29:53
     * @return PmphUser
     */
    public static PmphUser getShiroPmphUser() {
        Object object = getShiroSessionUser().getAttribute(Const.SESSION_PMPH_USER);
        if (null != object) {
            return (PmphUser) object;
        } else {
            return null;
        }
    }

    /**
     * 
     * <pre>
     * 功能描述：根据Request获取用户（现阶段不使用）
     * 使用示范：
     *
     * @return
     * </pre>
     */
    public static PmphUser getPmphUser() {
        HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        PmphUser pmphUser = (PmphUser) session.getAttribute(Const.SESSION_PMPH_USER);
        return pmphUser;
    }

    /**
     * 
     * <pre>
     * 功能描述：根据SessionId获取用户对象(现阶段使用)
     * 使用示范：
     *
     * @return
     * </pre>
     */
    public static PmphUser getPmphUserBySessionId(String sessionId) throws CheckedServiceException {
        HttpSession session = SessionContext.getSession(new DesRun(sessionId).depsw);
        if (Tools.isNullOrEmpty(session)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.SESSION,
                                              CheckedExceptionResult.USER_SESSION,
                                              "当前Session会话已过期，请重新登录!");
        }
        PmphUser pmphUser = (PmphUser) session.getAttribute(Const.SESSION_PMPH_USER);
        return pmphUser;
    }

    /**
     * 获取作家用户
     * 
     * @introduction
     * @author Mryang
     * @createDate 2017年9月23日 下午8:30:08
     * @return WriterUser
     */
    public static WriterUser getShiroWriterUser() {
        Object object = getShiroSessionUser().getAttribute(Const.SESSION_WRITER_USER);
        if (null != object) {
            return (WriterUser) object;
        } else {
            return null;
        }
    }

    /**
     * 
     * <pre>
     * 功能描述：根据Request获取用户（现阶段不使用）
     * 使用示范：
     *
     * @return
     * </pre>
     */
    public static WriterUser getWriterUser() {
        HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        WriterUser writerUser = (WriterUser) session.getAttribute(Const.SESSION_WRITER_USER);
        return writerUser;
    }

    /**
     * 
     * <pre>
     * 功能描述：根据SessionId获取用户对象(现阶段使用)
     * 使用示范：
     *
     * @return
     * </pre>
     */
    public static WriterUser getWriterUserBySessionId(String sessionId)
    throws CheckedServiceException {
        HttpSession session = SessionContext.getSession(new DesRun(sessionId).depsw);
        if (Tools.isNullOrEmpty(session)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.SESSION,
                                              CheckedExceptionResult.USER_SESSION,
                                              "当前Session会话已过期，请重新登录!");
        }
        WriterUser writerUser = (WriterUser) session.getAttribute(Const.SESSION_WRITER_USER);
        return writerUser;
    }
}
