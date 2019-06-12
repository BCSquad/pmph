package com.bc.pmpheep.back.service;

import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.po.MaterialOrg;
import com.bc.pmpheep.back.vo.OrgExclVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * @author MrYang
 * @CreateDate 2017年11月1日 下午3:12:08
 * 
 **/
public interface MaterialOrgService {

    /**
     * 新增 materialOrg
     * 
     * @author Mryang
     * @createDate 2017年11月1日 下午3:25:33
     * @param materialOrg
     * @return 带主键的materialOrg
     * @throws CheckedServiceException
     */
    MaterialOrg addMaterialOrg(MaterialOrg materialOrg) throws CheckedServiceException;

    /**
     * 批量新增 materialOrg
     * 
     * @author Mryang
     * @createDate 2017年11月1日 下午3:25:33
     * @param materialOrgs
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer addMaterialOrgs(List<MaterialOrg> materialOrgs) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按materialId查询MaterialOrg对象集合
     * 使用示范：
     *
     * @param materialId 教材 ID
     * @return orgId集合
     * @throws CheckedServiceException
     * </pre>
     */
    List<Long> getListMaterialOrgByMaterialId(Long materialId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据教材 ID导出已发布的学校
     * 使用示范：
     *
     * @param materialId 教材 ID
     * @return OrgExclVO
     * @throws CheckedServiceException
     * </pre>
     */
    List<OrgExclVO> getOutPutExclOrgByMaterialId(Long materialId) throws CheckedServiceException;

    /**
     * 根据materialId删除materialOrg
     * 
     * @author Mryang
     * @createDate 2017年11月1日 下午3:49:52
     * @param materialId
     * @return 影响行数
     */
    Integer deleteMaterialOrgByMaterialId(Long materialId) throws CheckedServiceException;

    /**
     * 根据orgId删除materialOrg
     * 
     * @author Mryang
     * @createDate 2017年11月1日 下午3:50:31
     * @param orgId
     * @return 影响行数
     */
    Integer deleteMaterialOrgByOrgId(Long orgId) throws CheckedServiceException;

    Integer deleteMaterialOrgByMaterialAndOrgId(Map<String,Object> params);
}
