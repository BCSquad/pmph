package com.bc.pmpheep.back.service;

import java.util.Map;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.vo.OrgUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgUserService 接口
 * @author Mryang
 */
public interface  OrgUserService {
	/**
	 * 
	 * @param orgUser 实体对象
	 * @return   带主键的 OrgUser
	 * @throws CheckedServiceException 
	 */
	OrgUser addOrgUser(OrgUser orgUser) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  OrgUser
	 * @throws CheckedServiceException
	 */
	OrgUser getOrgUserById(Long id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
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
     * <pre>
     * 功能描述：分页查询作家用户
     * 使用示范：
     *
     * @param page 传入的查询条件
     * @return
     * </pre>
     */
    Page<OrgUserManagerVO,Map<String, String>> getListOrgUser(Page<OrgUserManagerVO,Map<String, String>> page) throws CheckedServiceException;
}
