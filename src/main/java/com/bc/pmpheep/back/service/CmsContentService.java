package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

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
     * @return CmsContent对象
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
    String scheduledTime, String sessionId, HttpServletRequest request)
    throws CheckedServiceException, IOException;

    /**
     * 
     * <pre>
     * 功能描述：新增帮助
     * 使用示范：
     *
     * @param cmsContent  CmsContent对象
     * @return CmsContent对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsContent addHelp(CmsContent cmsContent, String content, String sessionId,
    HttpServletRequest request) throws CheckedServiceException, IOException;

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
    Integer updateCmsContent(CmsContent cmsContent, String[] files, String[] imgFile,
    String content, String[] attachment, String[] imgAttachment, String scheduledTime,
    String sessionId, HttpServletRequest request) throws CheckedServiceException, IOException;

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
    Integer updateHelp(CmsContent cmsContent, String content, String sessionId,
    HttpServletRequest request) throws CheckedServiceException, IOException;

    /**
     * 更新CmsContent对象
     * 
     * @param cmsContent CmsContent实例
     * @return 影响行数
     * @throws CheckedServiceException 已知服务错误
     */
    Integer updateCmsContent(CmsContent cmsContent) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：定时任务批量更新
     * 使用示范：
     *
     * @param csmContents CmsContent对象集合
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsContentByIds(List<CmsContent> csmContents) throws CheckedServiceException;

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
    Integer publishCmsContentById(Long id, String sessionId) throws CheckedServiceException;

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
     * @param categoryId 栏目类型
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer checkContentById(Long id, Short authStatus, Long categoryId, String sessionId,Boolean isOriginal)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：内容发布列表
     * 使用示范：
     *
     * @param pageParameter 带有分页参数和查询条件参数
     * @param sessionId 
     * @return 分页结果集
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<CmsContentVO> listCmsContent(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：评论审核列表
     * 使用示范：
     *
     * @param pageParameter 带有分页参数和查询条件参数
     * @param sessionId 
     * @return 分页结果集
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<CmsContentVO> listCmsComment(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException;

    /**
     * 通过id获取 CmsContent
     * 
     * @author Mryang
     * @createDate 2017年11月17日 下午5:17:23
     * @param id 主键ID
     * @return CmsContent CmsContent对象
     * @throws CheckedServiceException
     */
    CmsContent getCmsContentById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过materialId获取CmsContent对象
     * 使用示范：
     *
     * @param materialId 教材ID
     * @return CmsContent对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsContent getCmsContentByMaterialId(Long materialId) throws CheckedServiceException;

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
     * 功能描述：通过id获取CmsContent,Contemt,对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsContent 对象
     * @throws CheckedServiceException
     * </pre>
     */
    Map<String, Object> getHelpDetail(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     *@param categoryId CmsCategory 主键ID
     * @return 总条数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer getCmsContentCount(Long categoryId) throws CheckedServiceException;

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
     * 功能描述：根据教材id逻辑删除
     * 使用示范：
     *
     * @ id 主键ID
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsContentByMaterialId(Long MaterialId) throws CheckedServiceException;

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

    /**
     * 
     * <pre>
     * 功能描述：评论审核通过后，评论数加1
     * 使用示范：
     *
     * @param id CmsContent主键
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updatCmsContentCommentsById(Long id, Integer comments) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据parent_id更新文章评论数
     * 使用示范：
     *
     * @param id CmsContent主键
     * @param comments 1/-1
     * @return  影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsContentByParentId(@Param("id") Long id, @Param("comments") Integer comments)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据ParentId查询CMSContent集合
     * 使用示范：
     *
     * @param parentId 
     * @return CmsContent对象集合
     * </pre>
     */
    List<CmsContent> getCmsContentByParentId(Long parentId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：帮助管理列表
     * 使用示范：
     *
     * @param pageParameter 带有分页参数和查询条件参数
     * @param sessionId 
     * @return 分页结果集
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<CmsContentVO> listHelp(PageParameter<CmsContentVO> pageParameter, String sessionId)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：帮助管理-常见问题按标题查询
     * 使用示范：
     *
     * @param title 问题名称
     * @return
     * </pre>
     */
    List<CmsContent> listCmsContentByTitle(String title) throws CheckedServiceException;

    PageResult<Map<String, Object> > recommendlist(Integer recommendPageSize, Integer recommendPageNumber, Long currentCmsId, Boolean relationCms, String cmsTitle,String cmsAuthorName);

    Boolean recommendcheck(Long currentCmsId, Boolean relationCms, Long relationCmsId);
}
