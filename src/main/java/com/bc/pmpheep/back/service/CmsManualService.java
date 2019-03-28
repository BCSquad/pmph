package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.CmsManual;
import com.bc.pmpheep.back.vo.CmsManualVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

public interface CmsManualService {
    /**
     * 新增一个CmsManual
     * 
     * @param cmsManual 实体对象
     * @return 影响行数
     */
    CmsManual addCmsManual(CmsManual cmsManual) throws CheckedServiceException;

    CmsManual addCmsManual(CmsManual cmsManual, String sessionId) throws CheckedServiceException;

    Integer updateCmsManual(CmsManual cmsManual, String sessionId) throws CheckedServiceException;

    /**
     * 删除CmsManual 通过主键id
     * 
     * @param id
     * @return 影响行数
     */
    Integer deleteCmsManualById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：操作手册列表
     * 使用示范：
     *
     * @param pageParameter 带有分页参数和查询条件参数
     * @param sessionId 
     * @return 分页结果集
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<CmsManualVO> listCmsManual(PageParameter<CmsManualVO> pageParameter, String sessionId)
    throws CheckedServiceException;

    CmsManual cmsManualById(Long id,String sessionId)
            throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：帮助管理-操作手册按标题查询
     * 使用示范：
     *
     * @param manualName  操作手册标题
     * @return
     * </pre>
     */
    List<CmsManual> listCmsManualByManualName(String manualName) throws CheckedServiceException;
}
