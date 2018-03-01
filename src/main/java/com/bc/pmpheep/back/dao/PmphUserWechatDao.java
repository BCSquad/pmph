package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.PmphUserWechat;

public interface PmphUserWechatDao {
    /**
     * 添加一个用户
     * 
     * @param PmphUserWechat 添加用户的详细信息
     * @return 影响的行数
     */
    Integer add(PmphUserWechat user);

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
    Integer update(PmphUserWechat user);

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
    Integer delete(Long id);

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
    Integer deleteByWechatId(String wechatId);

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
    PmphUserWechat getPmphUserWechatByWechatId(String wechatId);
}
