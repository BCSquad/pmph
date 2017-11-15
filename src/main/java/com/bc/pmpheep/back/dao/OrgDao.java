package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgDao实体类数据访问层接口
 * 
 * @author mryang
 */
public interface OrgDao {

    /**
     * 
     * @param org 实体对象
     * @return 影像行数
     * @throws CheckedServiceException
     */
    Integer addOrg(Org org);

    /**
     * 
     * @param id
     * @return Org
     * @throws CheckedServiceException
     */
    Org getOrgById(Long id);

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteOrgById(Long id);

    /**
     * @param org
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateOrg(Org org);

    /**
     * 
     * <pre>
	 * 功能描述：查询表单的数据总条数
	 * 使用示范：
	 *
	 * @return 表单的总条数
	 * </pre>
     */
    Long getOrgCount();

    /**
     * 
     * 获取OrgVO列表（同时查询分页数据和总条数）
     * 
     * @author Mryang
     * @createDate 2017年9月26日 上午10:36:10
     * @param pageParameter
     * @return List<OrgVO>
     */
    List<OrgVO> listOrg(PageParameter<OrgVO> pageParameter);

    /**
     * 
     * <pre>
	 * 功能描述：获取学校管理员审核列表(同时查询分页数据和总条数）
	 * 使用示范：
	 *
	 * @param pageParameter
	 * @return
	 * </pre>
     */
    List<OrgVO> getSchoolAdminCheckList(PageParameter<OrgVO> pageParameter);

    /**
     * 
     * 
     * 功能描述：在机构用户与作家用户页面新增或者修改用户查询组织机构
     * 
     * @param orgName 机构名称
     * @return 模糊查询到的所有机构
     * 
     */
    List<OrgVO> listOrgByOrgName(String orgName);

    /**
     * 
     * <pre>
	 * 功能描述：系统消息——发送新消息——发送对象（学校管理员、所有人）
	 * 使用示范：
	 *
	 * &#64;param orgName 机构名称
	 * &#64;return
	 * </pre>
     */
    List<OrgVO> listSendToSchoolAdminOrAllUser(@Param("orgName") String orgName);

    /**
     * 
     * @param id
     * @return Org
     * @throws CheckedServiceException
     */
    List<Org> getOrgByOrgName(String orgName);

    /**
     * 
     * 
     * 功能描述：根据机构类型查询机构
     * 
     * @param typeId
     * @return
     * 
     */
    List<Org> listOrgByOrgType(Long typeId);

}
