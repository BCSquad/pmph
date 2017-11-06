package com.bc.pmpheep.back.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsContentDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.CmsExtra;
import com.bc.pmpheep.back.po.CmsSchedule;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.back.util.FileUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsContentService 接口实现
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
@Service
public class CmsContentServiceImpl implements CmsContentService {
    @Autowired
    CmsContentDao       cmsContentDao;
    @Autowired
    ContentService      contentService;
    @Autowired
    CmsScheduleService  cmsScheduleService;
    @Autowired
    private FileService fileService;
    @Autowired
    CmsExtraService     cmsExtraService;

    @Override
    public CmsContent addCmsContent(CmsContent cmsContent) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "CmsContent对象参数为空");
        }
        if (ObjectUtil.isNull(cmsContent.getParentId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "上级id参数为空");
        }
        if (StringUtil.isEmpty(cmsContent.getPath())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "根节点路径参数为空");
        }
        if (StringUtil.isEmpty(cmsContent.getMid())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容id参数为空");
        }
        if (ObjectUtil.isNull(cmsContent.getCategoryId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容类型参数为空");
        }
        if (StringUtil.isEmpty(cmsContent.getTitle())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容标题参数为空");
        }
        if (ObjectUtil.isNull(cmsContent.getAuthorType())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "作者类型参数为空");
        }
        cmsContentDao.addCmsContent(cmsContent);
        return cmsContent;
    }

    @Override
    public CmsContent addCmsContent(CmsContent cmsContent, String[] files, String content,
    String scheduledTime, String sessionId) throws CheckedServiceException, IOException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (StringUtil.isEmpty(content)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容参数为空");
        }
        // MongoDB 内容插入
        Content contentObj = contentService.add(new Content(content));
        if (StringUtil.isEmpty(contentObj.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.PO_ADD_FAILED,
                                              "Content对象内容保存失败");
        }
        // 内容保存
        cmsContent.setParentId(Const.CMS_CONTENT_PARENT_ID);// 上级id
        cmsContent.setPath(Const.CMS_CONTENT_PATH);// 根节点路径
        cmsContent.setMid(contentObj.getId());// 内容id
        cmsContent.setAuthorType(Const.CMS_AUTHOR_TYPE_1);// 作者类型
        cmsContent.setAuthorId(pmphUser.getId());// 作者id
        Long contentId = this.addCmsContent(cmsContent).getId();// 获取新增后的主键ID
        if (ObjectUtil.isNull(contentId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.PO_ADD_FAILED,
                                              "CmsContent添加内容失败");
        }
        // 定时发布
        if (true == cmsContent.getIsScheduled()) {
            if (StringUtil.isEmpty(scheduledTime)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                                  CheckedExceptionResult.NULL_PARAM, "定时发布时间参数为空");
            }
            cmsScheduleService.addCmsSchedule(new CmsSchedule(contentId,
                                                              DateUtil.str2Timestam(scheduledTime)));
        }
        // 保存附件到MongoDB
        if (ArrayUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.length; i++) {
                File file = FileUpload.getFileByFilePath(files[i]);
                // 循环获取file数组中得文件
                if (StringUtil.notEmpty(file.getName())) {
                    String gridFSFileId =
                    fileService.saveLocalFile(file, FileType.CMS_ATTACHMENT, contentId);
                    if (StringUtil.isEmpty(gridFSFileId)) {
                        throw new CheckedServiceException(
                                                          CheckedExceptionBusiness.CMS,
                                                          CheckedExceptionResult.FILE_UPLOAD_FAILED,
                                                          "文件上传失败!");
                    }
                    // 保存对应数据
                    CmsExtra cmsExtra =
                    cmsExtraService.addCmsExtra(new CmsExtra(contentId, gridFSFileId,
                                                             file.getName(), null));
                    if (ObjectUtil.isNull(cmsExtra.getId())) {
                        throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                                          CheckedExceptionResult.PO_ADD_FAILED,
                                                          "CmsExtra对象新增失败");
                    }
                }
                FileUtil.delFile(files[i]);// 删除本地临时文件
            }
        }
        return cmsContent;
    }

    @Override
    public Integer updateCmsContent(CmsContent cmsContent, String[] files, String content,
    String[] attachment, String scheduledTime, String sessionId) throws CheckedServiceException,
    IOException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (StringUtil.notEmpty(content)) {
            // 更新MongoDB 内容
            contentService.update(new Content(cmsContent.getMid(), content));
        }
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        // 如果是更新,或者退回后修改，审核用户ID，审核时间都清空
        cmsContent.setAuthUserId(null);
        cmsContent.setAuthDate(null);
        // 再次编辑时间
        cmsContent.setGmtReedit(DateUtil.getCurrentTime());
        cmsContentDao.updateCmsContent(cmsContent);

        // 是否定时发布
        CmsSchedule csmSchedule = cmsScheduleService.getCmsScheduleByContentId(cmsContent.getId());
        // 1.修改时如果不选择定时发布，则查询该数据之前是否有选择定时发布，如果有则删除
        if (false == cmsContent.getIsScheduled()) {
            if (ObjectUtil.notNull(csmSchedule)) {
                cmsScheduleService.deleteCmsScheduleByContentId(cmsContent.getId());
            }
        }
        // 2.修改时如果选择定时发布，则查询该数据之前是否有选择定时发布，如果有则更新，没有则新增
        if (true == cmsContent.getIsScheduled()) {
            if (StringUtil.isEmpty(scheduledTime)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                                  CheckedExceptionResult.NULL_PARAM, "定时发布时间参数为空");
            }
            if (ObjectUtil.notNull(csmSchedule)) {
                cmsScheduleService.updateCmsSchedule(new CmsSchedule(
                                                                     csmSchedule.getId(),
                                                                     cmsContent.getId(),
                                                                     DateUtil.str2Timestam(scheduledTime)));
            } else {
                cmsScheduleService.addCmsSchedule(new CmsSchedule(
                                                                  cmsContent.getId(),
                                                                  DateUtil.str2Timestam(scheduledTime)));
            }
        }
        // 删除附件
        if (ArrayUtil.isNotEmpty(attachment)) {
            // 删除CmsExtra 表
            cmsExtraService.deleteCmsExtraByAttachment(attachment);
            // 删除MongoDB对应的文件
            for (int i = 0; i < attachment.length; i++) {
                fileService.remove(attachment[i]);
            }
        }
        // 保存附件到MongoDB
        if (ArrayUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.length; i++) {
                File file = FileUpload.getFileByFilePath(files[i]);
                // 循环获取file数组中得文件
                if (StringUtil.notEmpty(file.getName())) {
                    String gridFSFileId =
                    fileService.saveLocalFile(file, FileType.CMS_ATTACHMENT, cmsContent.getId());
                    if (StringUtil.isEmpty(gridFSFileId)) {
                        throw new CheckedServiceException(
                                                          CheckedExceptionBusiness.CMS,
                                                          CheckedExceptionResult.FILE_UPLOAD_FAILED,
                                                          "文件上传失败!");
                    }
                    // 保存对应数据
                    CmsExtra cmsExtra =
                    cmsExtraService.addCmsExtra(new CmsExtra(cmsContent.getId(), gridFSFileId,
                                                             file.getName(), null));
                    if (ObjectUtil.isNull(cmsExtra.getId())) {
                        throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                                          CheckedExceptionResult.PO_ADD_FAILED,
                                                          "CmsExtra对象新增失败");
                    }
                }
                FileUtil.delFile(files[i]);// 删除本地临时文件
            }
        }
        return 0;
    }

    @Override
    public Integer publishCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsContentDao.publishCmsContentById(id);
    }

    @Override
    public Integer hideCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsContentDao.hideCmsContentById(id);
    }

    @Override
    public Integer checkContentById(Long id, Short authStatus, String sessionId)
    throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(id) || ObjectUtil.isNull(authStatus)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsContentDao.checkContentById(new CmsContent(id, authStatus, pmphUser.getId(),
                                                             DateUtil.getCurrentTime(),
                                                             Const.MATERIAL_TYPE_ID));
    }

    @Override
    public PageResult<CmsContentVO> listCmsContent(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        pageParameter.getParameter().setIsAdmin(pmphUser.getIsAdmin());
        pageParameter.getParameter().setAuthorId(pmphUser.getId());
        PageResult<CmsContentVO> pageResult = new PageResult<CmsContentVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<CmsContentVO> cmsContentList = cmsContentDao.listCmsContent(pageParameter);
        if (CollectionUtil.isNotEmpty(cmsContentList)) {
            Integer count = cmsContentList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(cmsContentList);
        }
        return pageResult;
    }

    @Override
    public PageResult<CmsContentVO> listContentManage(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        // pageParameter.getParameter().setIsAdmin(pmphUser.getIsAdmin());
        // pageParameter.getParameter().setAuthorId(pmphUser.getId());
        PageResult<CmsContentVO> pageResult = new PageResult<CmsContentVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<CmsContentVO> cmsContentList = cmsContentDao.listCmsContent(pageParameter);
        if (CollectionUtil.isNotEmpty(cmsContentList)) {
            Integer count = cmsContentList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(cmsContentList);
        }
        return pageResult;
    }

    @Override
    public PageResult<CmsContentVO> listContentCheck(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        PageResult<CmsContentVO> pageResult = new PageResult<CmsContentVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<CmsContentVO> cmsContentList = cmsContentDao.listContentManage(pageParameter);
        if (CollectionUtil.isNotEmpty(cmsContentList)) {
            Integer count = cmsContentList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(cmsContentList);
        }
        return pageResult;
    }

    @Override
    public List<CmsContent> getCmsContentList(CmsContent cmsContent) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.getCmsContentList(cmsContent);
    }

    @Override
    public Map<String, Object> getCmsContentAndContentAndAttachmentById(Long id)
    throws CheckedServiceException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        // 按id 获取CmsContent对象
        CmsContent cmsContent = cmsContentDao.getCmsContentById(id);
        resultMap.put("cmsContent", cmsContent);
        // 判断是内容易是否已经发布或审核通过
        String fileDownLoadType = null;
        if (true == cmsContent.getIsPublished()
            || Const.CMS_AUTHOR_STATUS_2 == cmsContent.getAuthStatus()) {
            fileDownLoadType = Const.CMS_FILE_DOWNLOAD;
        } else {
            fileDownLoadType = Const.FILE_DOWNLOAD;
        }
        // 按mid 获取Content对象
        Content content = contentService.get(cmsContent.getMid());
        resultMap.put("content", content);
        // 按contentId 获取CMS内容附件
        List<CmsExtra> cmsExtras = cmsExtraService.getCmsExtraByContentId(id);
        for (CmsExtra cmsExtra : cmsExtras) {
            String attachment = cmsExtra.getAttachment();
            cmsExtra.setAttachment(fileDownLoadType + attachment);// 拼接附件下载路径
        }
        resultMap.put("cmsExtras", cmsExtras);
        return resultMap;
    }

    @Override
    public CmsContent getCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsContentDao.getCmsContentById(id);
    }

    @Override
    public Integer getCmsContentCount() throws CheckedServiceException {
        return cmsContentDao.getCmsContentCount();
    }

    @Override
    public Integer deleteCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.deleteCmsContentById(id);
    }

    @Override
    public Integer deleteCmsContentByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.deleteCmsContentByIds(ids);
    }

}