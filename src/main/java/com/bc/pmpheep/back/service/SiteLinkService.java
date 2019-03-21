package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SiteLink;
import com.bc.pmpheep.service.exception.CheckedServiceException;

public interface SiteLinkService {
	/**
	 * 
	 * 
	 * 功能描述：添加一个友情链接对象
	 *
	 * @param SiteLink
	 * @return
	 *
	 */
	SiteLink add(SiteLink SiteLink) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：修改友情链接
	 *
	 * @param SiteLink
	 * @return
	 *
	 */
	String update(SiteLink SiteLink) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：获取友情链接列表并分页
	 *
	 * @return
	 *
	 */
	PageResult<SiteLink> list(PageParameter<SiteLink> pageParameter) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：批量逻辑删除友情链接
	 *
	 * @param id
	 * @return
	 *
	 */
	String deletedIsDeleted(Long[] id) throws CheckedServiceException;

	/**
	 * 行内上移 返回交换sort的相邻链接主键 0表示未交换
	 * @param id
	 * @param up true上false下
	 * @return
	 */
    Long sortMove(Long id, Boolean up);
}
