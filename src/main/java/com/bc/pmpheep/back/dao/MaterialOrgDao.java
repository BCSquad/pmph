package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.MaterialOrg;
import com.bc.pmpheep.back.vo.OrgExclVO;

/**
 * @author MrYang
 * @CreateDate 2017年11月1日 下午3:28:50
 * 
 **/
@Repository
public interface MaterialOrgDao {

    /**
     * 新增 materialOrg
     * 
     * @author Mryang
     * @createDate 2017年11月1日 下午3:25:33
     * @param materialOrg
     * @return 影响行数
     */
    Integer addMaterialOrg(MaterialOrg materialOrg);

    /**
     * 批量新增 materialOrg
     * 
     * @author Mryang
     * @createDate 2017年11月1日 下午3:25:33
     * @param materialOrgs
     * @return 影响行数
     */
    Integer addMaterialOrgs(List<MaterialOrg> materialOrgs);

    /**
     * 
     * <pre>
     * 功能描述：按materialId查询MaterialOrg对象集合
     * 使用示范：
     *
     * @param materialId 教材 ID
     * @return orgId集合
     * </pre>
     */
    List<Long> getListMaterialOrgByMaterialId(Long materialId);

    /**
     * 
     * <pre>
     * 功能描述：根据教材 ID导出已发布的学校
     * 使用示范：
     *
     * @param materialId 教材 ID
     * @return OrgExclVO
     * </pre>
     */
    List<OrgExclVO> getOutPutExclOrgByMaterialId(Long materialId);

    /**
     * 根据materialId删除materialOrg
     * 
     * @author Mryang
     * @createDate 2017年11月1日 下午3:49:52
     * @param materialId
     * @return 影响行数
     */
    Integer deleteMaterialOrgByMaterialId(Long materialId);

    /**
     * 根据orgId删除materialOrg
     * 
     * @author Mryang
     * @createDate 2017年11月1日 下午3:50:31
     * @param orgId
     * @return 影响行数
     */
    Integer deleteMaterialOrgByOrgId(Long orgId);

    Integer deleteMaterialOrgByMaterialAndOrgId(Map<String,Object> params);

}
