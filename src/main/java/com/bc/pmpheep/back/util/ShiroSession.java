package com.bc.pmpheep.back.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

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
public class ShiroSession {

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
    public Session getShiroSessionUser() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return session;
    }
}
