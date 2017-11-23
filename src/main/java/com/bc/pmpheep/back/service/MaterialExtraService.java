package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.Map;

import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.vo.MaterialExtraVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialExtraService 接口
 * 
 * @author 曾庆峰
 * 
 */
public interface MaterialExtraService {

    /**
     * 新增一个MaterialExtra
     * 
     * @param materialExtra 实体对象
     * @return 带主键的 MaterialExtra thorws CheckedServiceException
     */
    MaterialExtra addMaterialExtra(MaterialExtra materialExtra) throws CheckedServiceException;

    /**
     * 查询一个 MaterialExtra 通过主键id
     * 
     * @param id
     * @return MaterialExtra
     * @throws CheckedServiceException
     */
    MaterialExtra getMaterialExtraById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据教材ID查询MaterialExtra对象
     * 使用示范：
     *
     * @param materialId 教材ID
     * @return MaterialExtra对象
     * @throws CheckedServiceException
     * </pre>
     */
    MaterialExtra getMaterialExtraByMaterialId(Long materialId) throws CheckedServiceException;

    /**
     * 删除MaterialExtra 通过主键id
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteMaterialExtraById(Long id) throws CheckedServiceException;

    /**
     * 根据主键id 更新materialExtra 不为null和不为‘’的字段
     * 
     * @param materialExtra
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateMaterialExtra(MaterialExtra materialExtra) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：编辑通知详情
     * 使用示范：
     *
     * @param materialExtraVO MaterialExtraVO
     * @return Map<String, Object>集合
     * @throws CheckedServiceException
     * </pre>
     */
    Map<String, Object> updateMaterialExtraAndNoticeFile(MaterialExtraVO materialExtraVO)
    throws CheckedServiceException, IOException;

    /**
     * 
     * <pre>
     * 功能描述：根据教材ID查询教材通知详情及附件
     * 使用示范：
     *
     * @param materialId 教材ID
     * @return Map<String, Object> 集合
     * @throws CheckedServiceException
     * </pre>
     */
    Map<String, Object> getMaterialExtraAndNoticeDetail(Long materialId)
    throws CheckedServiceException;

}
