package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.CmsExtra;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsExtraService 接口
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
public interface CmsExtraService {
    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsExtra  CmsExtra对象
     * @return  CmsExtra对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsExtra addCmsExtra(CmsExtra cmsExtra) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：修改CmsExtra
     * 使用示范：
     *
     * @param cmsExtra 
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsExtra(CmsExtra cmsExtra) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按attachment修改下载次数 
     * 使用示范：
     *
     * @param attachment  MongoDB附件表的主键
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsExtraDownLoadCountsByAttachment(String attachment)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：查询CmsExtra列表(全部)
     * 使用示范：
     *
     * @param cmsExtra 
     * @return CmsExtra集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<CmsExtra> getCmsExtraList(CmsExtra cmsExtra) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsExtra对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsCategory 对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsExtra getCmsExtraById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过content_id获取CmsExtra集合对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsCategory 集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<CmsExtra> getCmsExtraByContentId(Long contentId) throws CheckedServiceException;

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
    Integer getCmsExtraCount() throws CheckedServiceException;

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
    Integer deleteCmsExtraById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按attachment删除
     * 使用示范：
     *
     * @param attachment MongoDB附件表的主键
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer deleteCmsExtraByAttachment(String[] attachment) throws CheckedServiceException;

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
    Integer deleteCmsExtraByIds(List<Long> ids) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据attachment查询一个CmsExtra
     * 使用示范：
     *
     * @param attachment MongoDBId
     * @return CmsExtra对象
     * </pre>
     */
    CmsExtra getCmsExtraByAttachment(String attachment) throws CheckedServiceException;
}
