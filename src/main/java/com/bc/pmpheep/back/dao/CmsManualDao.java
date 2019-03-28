package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.CmsManual;
import com.bc.pmpheep.back.vo.CmsManualVO;

public interface CmsManualDao {
    /**
     * 新增一个CmsManual
     * 
     * @param cmsManual 实体对象
     * @return 影响行数
     */
    Integer addCmsManual(CmsManual cmsManual);

    /**
     * 新增一个CmsManual
     *
     * @param cmsManual 实体对象
     * @return 影响行数
     */
    Integer updateCmsManual(CmsManual cmsManual);


    /**
     * 删除CmsManual 通过主键id
     * 
     * @param id
     * @return 影响行数
     */
    Integer deleteCmsManualById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：获取CmsManual列表（同时查询分页数据和总条数）
     * 使用示范：
     *
     * @param pageParameter
     * @return List<CmsContentVO>
     * </pre>
     */
    List<CmsManualVO> listCmsManual(PageParameter<CmsManualVO> pageParameter);



    /**
     * </pre>
     */
    CmsManual cmsManualById(Long id);

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
    List<CmsManual> listCmsManualByManualName(String manualName);

}
