package com.bc.pmpheep.back.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;

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
    public static Session getShiroSessionUser() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return session;
    }
    /**
     * 获取社内用户  
     * @introduction 
     * @author Mryang
     * @createDate 2017年9月23日 下午8:29:53
     * @return PmphUser
     */
    public static PmphUser getPmphUser(){
    	Object object = getShiroSessionUser().getAttribute(Const.SESSION_PMPH_USER);
    	if(null != object){
    		return (PmphUser)object;
    	}else{
    		return null;
    	}
    }
    
    /**
     * 获取作家用户
     * @introduction 
     * @author Mryang
     * @createDate 2017年9月23日 下午8:30:08
     * @return WriterUser
     */
    public static WriterUser getWriterUser(){
    	Object object = getShiroSessionUser().getAttribute(Const.SESSION_WRITER_USER);
    	if(null != object){
    		return (WriterUser)object ;
    	}else{
    		return null;
    	}
    }
}
