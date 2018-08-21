package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.vo.OrgExclVO;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgService 接口
 * 
 * @author Mryang
 * 
 */
public interface OrgService {

    /**
     * 根据书籍获取当选了该书籍的人员所属机构
     * 
     * @author Mryang
     * @createDate 2017年11月19日 上午9:17:57
     * @param bookIds
     * @return List<Org>
     * @throws CheckedServiceException
     */
    List<Org> listBeElectedOrgByBookIds(List<Long> bookIds) throws CheckedServiceException;

    List<Org> listBeElectedOrgByBookIds_up1(List<Long> bookIds)throws CheckedServiceException;

    /**
     * 
     * @param org 实体对象
     * @return Org 带主键
     * @throws CheckedServiceException
     */
    Org addOrg(Org org) throws CheckedServiceException;

    /**
     * 
     * @param id
     * @return Org
     * @throws CheckedServiceException
     */
    Org getOrgById(Long id) throws CheckedServiceException;

    /**
     * 根据学校名称和学校机构代码获取机构
     * 
     * @introduction
     * @author Mryang
     * @createDate 2018年1月2日 下午2:33:48
     * @param name 学校名称
     * @param username 机构代码
     * @return
     * @throws CheckedServiceException
     */
    Org getOrgByNameAndUserName(String name, String username) throws CheckedServiceException;

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteOrgById(Long id) throws CheckedServiceException;

    /**
     * @param org
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateOrg(Org org) throws CheckedServiceException;

    /**
     * @param PageParameter 带有分页参数和查询条件参数
     * @return PageResult 包含 List<OrgVO>以及分页数据
     * @throws CheckedServiceException
     */
    PageResult<OrgVO> listOrg(PageParameter<OrgVO> pageParameter) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取学校管理员审核列表(同时查询分页数据和总条数）
     * 使用示范：
     *
     * @param pageParameter 带有分页参数和查询条件参数
     * @return PageResult 包含 List<OrgVO>以及分页数据
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<OrgVO> getSchoolAdminCheckList(PageParameter<OrgVO> pageParameter)
    throws CheckedServiceException;

    /**
     * 
     * 
     * 功能描述：在新增用户与修改用户时查询机构
     * 
     * @param orgName 机构名称
     * @return 模糊查询出的机构集合
     * @throws CheckedServiceException
     * 
     */
    List<OrgVO> listOrgByOrgName(String orgName) throws CheckedServiceException;

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
    @Param("materialId") Long materialId) throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：系统消息——发送新消息——发送对象（学校管理员、所有人）
     * 使用示范：
     *
     * @param orgName 机构名称
     * @param productId productID
     * @return
     * </pre>
     */
    List<OrgVO> listSendClinicalToSchoolAdminOrAllUser(@Param("orgName") String orgName,
                                               @Param("productId") Long productId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：所有学校导出
     * 使用示范：
     *
     * @return
     * </pre>
     */
    List<OrgExclVO> listAllOrgToExcel(String chooseOrg) throws CheckedServiceException;


    /**
     * 导出学校信息
     * @param orgVO
     * @return
     */
    List<OrgVO> exportOrgUser(OrgVO orgVO);
}
