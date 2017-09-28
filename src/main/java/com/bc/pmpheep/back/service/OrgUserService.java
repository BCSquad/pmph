package com.bc.pmpheep.back.service;

import java.util.List;
import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.vo.OrgUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgUserService 接口
 * 
 * @author Mryang
 */
public interface OrgUserService {
	/**
	 * 根据机构id集查询用户
	 */
	List<OrgUser> getOrgUserListByOrgIds(List<Long> orgIds) throws CheckedServiceException;
	
	/**
	 * 
	 * @param orgUser
	 *            实体对象
	 * @return 带主键的 OrgUser
	 * @throws CheckedServiceException
	 */
	OrgUser addOrgUser(OrgUser orgUser) throws CheckedServiceException;

	/**
	 * 
	 * @param id
	 * @return OrgUser
	 * @throws CheckedServiceException
	 */
	OrgUser getOrgUserById(Long id) throws CheckedServiceException;

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteOrgUserById(Long id) throws CheckedServiceException;

	/**
	 * @param orgUser
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateOrgUser(OrgUser orgUser) throws CheckedServiceException;

	/**
	 * 
	 * 功能描述：分页查询作家用户
	 *
	 * @param page
	 *            传入的查询条件
	 * @return
	 */
	Page<OrgUserManagerVO, OrgUserManagerVO> getListOrgUser(Page<OrgUserManagerVO, OrgUserManagerVO> page)
			throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：后台机构用户管理页面添加机构用户
	 *
	 * @param OrgUser
	 *            添加的机构用户用户属性
	 * @return 是否成功
	 * @throws CheckedServiceException
	 *
	 */
	String addOrgUserOfBack(OrgUser orgUser) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：后台机构用户管理页面修改机构用户
	 *
	 * @param writerUser
	 *            修改机构用户用户的属性
	 * @return 是否成功
	 * @throws CheckedServiceException
	 *
	 */
	String updateOrgUserOfBack(OrgUser orgUser) throws CheckedServiceException;
}
