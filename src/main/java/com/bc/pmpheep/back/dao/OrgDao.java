package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.vo.OrgExclVO;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgDao实体类数据访问层接口
 * 
 * @author mryang
 */
public interface OrgDao {

    /**
     * 根据书籍获取当选了该书籍的人员所属机构
     * 
     * @author Mryang
     * @createDate 2017年11月19日 上午9:20:57
     * @param bookIds
     * @return List<Org>
     */
    List<Org> listBeElectedOrgByBookIds(List<Long> bookIds);

    List<Org> listBeElectedOrgByBookIds_up1(List<Long> bookIds);

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
     * 根据学校名称和学校机构代码获取机构
     * 
     * @param name 学校名称
     * @param username 机构代码
     * @return Org
     * @throws CheckedServiceException
     */
    Org getOrgByNameAndUserName(@Param("name") String name, @Param("username") String username);

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
	 * @param orgName 机构名称
	 * @param materialId 教材ID
	 * @return
	 * </pre>
     */
    List<OrgVO> listSendToSchoolAdminOrAllUser(@Param("orgName") String orgName,
    @Param("materialId") Long materialId);

    /**
     *
     * <pre>
     * 功能描述：系统消息——发送新消息——发送对象（学校管理员、所有人）
     * 使用示范：
     *
     * @param orgName 机构名称
     * @param productId 教材ID
     * @return
     * </pre>
     */
    List<OrgVO> listSendClinicalToSchoolAdminOrAllUser(@Param("orgName") String orgName,
                                               @Param("productId") Long productId);

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

    /**
     * 功能描述：根据orgName去获取id
     * 
     * @param orgName
     * @return id
     */
    Long getOrgid(String orgName);

    /**
     * 
     * <pre>
     * 功能描述：所有学校导出
     * 使用示范：
     *
     * @return
     * </pre>
     */
    List<OrgExclVO> listAllOrgToExcel(@Param("chooseOrg") String[] chooseOrg);

    /**
     * 导出管理员信息
     * @param orgVO
     * @return
     */
    List<OrgVO> exportOrgUser(OrgVO orgVO);
}
