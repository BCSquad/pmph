package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphUserWechat;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：PmphUserWechatService接口
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-2-28
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public interface PmphUserWechatService {
    /**
     * 添加一个用户
     * 
     * @param PmphUserWechat 添加用户的详细信息
     * @return 影响的行数
     */
    PmphUserWechat add(PmphUserWechat user) throws CheckedServiceException;

    /**
     * 
     * <pre>
      * 功能描述：更新PmphUserWechat
      * 使用示范：
      *
      * @param user PmphUserWechat对象
      * @return 影响的行数
      * </pre>
     */
    Integer update(PmphUserWechat user) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据ID删除PmphUserWechat对象
     * 使用示范：
     *
     * @param id 主键
     * @return 影响的行数
     * </pre>
     */
    Integer delete(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据微信企业号用户ID删除PmphUserWechat对象
     * 使用示范：
     *
     * @param wechatId 微信企业号用户ID
     * @return 影响的行数
     * </pre>
     */
    Integer deleteByWechatId(String wechatId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据微信企业号用户ID查询PmphUserWechat对象
     * 使用示范：
     *
     * @param wechatId 微信企业号用户ID
     * @return  PmphUserWechat对象
     * </pre>
     */
    PmphUserWechat getPmphUserWechatByWechatId(String wechatId) throws CheckedServiceException;
}
