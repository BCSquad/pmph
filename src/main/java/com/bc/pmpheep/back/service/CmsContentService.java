package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsContentService 接口
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-25
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public interface CmsContentService {
    /**
     * 
     * <pre>
     * 功能描述：CmsContent CMS内容新增
     * 使用示范：
     *
     * @param cmsContent CmsContent对象
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    CmsContent addCmsContent(CmsContent cmsContent) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsContent  CmsContent对象
     * @return CmsContent对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsContent addCmsContent(CmsContent cmsContent, String[] files, String content,
    String scheduledTime, String sessionId) throws CheckedServiceException, IOException;

    /**
     * 
     * <pre>
     * 功能描述：修改CmsContent
     * 使用示范：
     *
     * @param cmsContent 
     * @return  cmsContent CmsContent对象
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsContent(CmsContent cmsContent, String[] files, String content,
    String[] attachment, String scheduledTime, String sessionId) throws CheckedServiceException,
    IOException;

    /**
     * 
     * <pre>
     * 功能描述： 内容发布
     * 使用示范：
     * 
     * @param id 主键ID
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer publishCmsContentById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述： 内容隐藏
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer hideCmsContentById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述： 社外内容审核操作(通过/拒绝)
     * 使用示范：
     *
     * @param id 主键ID
     * @param authStatus 审核状态
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer checkContentById(Long id, Short authStatus, String sessionId)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：内容发布列表
     * 使用示范：
     *
     * @param pageParameter 带有分页参数和查询条件参数
     * @param sessionId 
     * @return 
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<CmsContentVO> listCmsContent(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：分页查询条件查询《社外内容管理》列表
     * 使用示范：
     *
     * @param pageParameter 带有分页参数和查询条件参数
     * @param sessionId 
     * @return 
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<CmsContentVO> listContentManage(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：分页查询条件查询《社外内容审核》列表
     * 使用示范：
     *
     * @param pageParameter 带有分页参数和查询条件参数
     * @param sessionId 
     * @return 
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<CmsContentVO> listContentCheck(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException;

    CmsContent getCmsContentById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：查询CmsContent列表(全部)
     * 使用示范：
     *
     * @param cmsContent 
     * @return CmsContent集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<CmsContent> getCmsContentList(CmsContent cmsContent) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsContent,Contemt,对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsContent 对象
     * @throws CheckedServiceException
     * </pre>
     */
    Map<String, Object> getCmsContentAndContentAndAttachmentById(Long id)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     *
     * @return 总条数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer getCmsContentCount() throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按主键Id删除
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer deleteCmsContentById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：批量删除
     * 使用示范：
     *
     * @param ids 主键id 集合
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer deleteCmsContentByIds(List<Long> ids) throws CheckedServiceException;
}