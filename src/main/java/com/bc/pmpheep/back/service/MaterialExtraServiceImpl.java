package com.bc.pmpheep.back.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialExtraDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.common.SystemMessageService;
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
import com.bc.pmpheep.back.vo.MateriaHistorylVO;
import com.bc.pmpheep.back.vo.MaterialExtraVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialExtraService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class MaterialExtraServiceImpl extends BaseService implements MaterialExtraService {

    @Autowired
    private MaterialExtraDao                materialExtraDao;
    @Autowired
    private MaterialService                 materialService;
    @Autowired
    private MaterialContactService          materialContactService;
    @Autowired
    private MaterialNoticeAttachmentService materialNoticeAttachmentService;
    @Autowired
    private MaterialNoteAttachmentService   materialNoteAttachmentService;
    @Autowired
    private FileService                     fileService;
    @Autowired
    private MaterialOrgService              materialOrgService;
    @Autowired
    private SystemMessageService            systemMessageService;
    @Autowired
    private CmsContentService               cmsContentService;
    @Autowired
    private ContentService                  contentService;

    private static final String             NOTICE = "notice";
    private static final String             NOTE   = "note";

    /**
     * 
     * @param materialExtra 实体对象
     * @return 带主键的 MaterialExtra
     * @throws CheckedServiceException
     */
    @Override
    public MaterialExtra addMaterialExtra(MaterialExtra materialExtra)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(materialExtra.getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材为空");
        }
        if (StringUtil.isEmpty(materialExtra.getNotice())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "通知内容为空");
        }
        if (StringUtil.isEmpty(materialExtra.getNote())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "备注内容为空");
        }
        materialExtraDao.addMaterialExtra(materialExtra);
        return materialExtra;
    }

    /**
     * 
     * @param id
     * @return MaterialExtra
     * @throws CheckedServiceException
     */
    @Override
    public MaterialExtra getMaterialExtraById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtraDao.getMaterialExtraById(id);
    }

    /**
     * 
     * <pre>
	 * 功能描述：根据教材ID查询MaterialExtra对象
	 * 使用示范：
	 *
	 * &#64;param materialId 教材ID
	 * &#64;return MaterialExtra对象
	 * &#64;throws CheckedServiceException
	 * </pre>
     */
    @Override
    public MaterialExtra getMaterialExtraByMaterialId(Long materialId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材ID不能为空");
        }
        return materialExtraDao.getMaterialExtraByMaterialId(materialId);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteMaterialExtraById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtraDao.deleteMaterialExtraById(id);
    }

    /**
     * 根据主键id 更新materialExtra 不为null和不为‘’的字段
     * 
     * @param materialExtra
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateMaterialExtra(MaterialExtra materialExtra) throws CheckedServiceException {
        if (null == materialExtra.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtraDao.updateMaterialExtra(materialExtra);
    }

    /**
     * 
     * <pre>
	 * 功能描述：编辑通知详情
	 * 使用示范：
	 *
	 * &#64;param materialExtraVO MaterialExtraVO
	 * &#64;return Map<String, Object>集合
	 * &#64;throws CheckedServiceException
	 * </pre>
     */
    @Override
    public Integer updateMaterialExtraAndNoticeFile(MaterialExtraVO materialExtraVO)
    throws CheckedServiceException, IOException {
        // 教材ID
        Long materialId = materialExtraVO.getMaterialId();
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材ID为空");
        }
        String materialName = materialExtraVO.getMaterialName();
        if (StringUtil.isEmpty(materialName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材名称为空");
        }
        String content = materialExtraVO.getContent();
        if (StringUtil.isEmpty(content)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材通知为空");
        }
        // MongoDB 内容插入
        Content contentObj = contentService.add(new Content(content));
        if (ObjectUtil.isNull(contentObj)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "教材通知保存失败");
        }
        Material material = materialService.getMaterialById(materialId);
        if (StringUtil.isEmpty(materialExtraVO.getContent())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材通知内容为空");
        }
        CmsContent cmsContent = cmsContentService.getCmsContentByMaterialId(materialId);
        if (ObjectUtil.notNull(cmsContent)) {
            if (StringUtil.notEmpty(cmsContent.getMid())) {
                contentService.delete(cmsContent.getMid());// 删除之前教材通知内容
            }
            // 存在就更新
            cmsContentService.updateCmsContent(new CmsContent(
                                                              cmsContent.getId(),
                                                              contentObj.getId(),
                                                              DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                                                       DateUtil.getCurrentTime())));
        } else {
            // 保存CMSContent内容
            cmsContentService.addCmsContent(new CmsContent(
                                                           0L,
                                                           "0",
                                                           contentObj.getId(),
                                                           materialName,
                                                           Const.CMS_AUTHOR_TYPE_0,
                                                           false,
                                                           true,
                                                           material.getFounderId(),
                                                           DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                                                    DateUtil.getCurrentTime()),
                                                           materialId, Const.CMS_CATEGORY_ID_1));
        }
        // 教材通知附件
        // String[] noticeFiles = materialExtraVO.getNoticeFiles();
        // if (ArrayUtil.isNotEmpty(noticeFiles)) {
        // this.saveFileToMongoDB(noticeFiles, materialExtraId, NOTICE);
        // }
        // 教材通知附件MongoDB对应ID
        String[] noticeAttachments = materialExtraVO.getNoticeAttachments();
        if (ArrayUtil.isNotEmpty(noticeAttachments)) {
            // 删除MaterialNoticeAttachment 表
            materialNoticeAttachmentService.deleteMaterialNoticeAttachmentByAttachments(noticeAttachments);
            // 删除MongoDB对应的文件
            for (int i = 0; i < noticeAttachments.length; i++) {
                fileService.remove(noticeAttachments[i]);
            }
        }
        // 教材备注附件
        // String[] noteFiles = materialExtraVO.getNoteFiles();
        // if (ArrayUtil.isNotEmpty(noteFiles)) {
        // this.saveFileToMongoDB(noteFiles, materialExtraId, NOTE);
        // }
        // 教材备注附件MongoDB对应ID
        String[] noteAttachments = materialExtraVO.getNoteAttachments();
        if (ArrayUtil.isNotEmpty(noteAttachments)) {
            // 删除MaterialNoteAttachment 表
            materialNoteAttachmentService.deleteMaterialNoteAttachmentByAttachments(noteAttachments);
            // 删除MongoDB对应的文件
            for (int i = 0; i < noteAttachments.length; i++) {
                fileService.remove(noteAttachments[i]);
            }
        }
        return 1;
    }

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
     * 
     * @throws UnsupportedEncodingException
     */
    @Override
    public Map<String, Object> getMaterialExtraAndNoticeDetail(Long materialId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材主键为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 教材通知附件
        List<MaterialNoticeAttachment> materialNoticeAttachments = null;
        // 教材备注附件
        List<MaterialNoteAttachment> materialNoteAttachments = null;
        // 教材
        Material material = materialService.getMaterialById(materialId);
        resultMap.put("materialName", material);// 教材
        List<MaterialContact> materialContacts =
        materialContactService.listMaterialContactByMaterialId(materialId);
        resultMap.put("materialContacts", materialContacts);// 联系人
        MaterialExtra materialExtra = this.getMaterialExtraByMaterialId(materialId);
        resultMap.put("materialExtra", materialExtra);// 教材通知备注
        Long materialExtraId = materialExtra.getId();
        if (ObjectUtil.notNull(materialExtraId)) {
            // 教材通知附件
            materialNoticeAttachments =
            materialNoticeAttachmentService.getMaterialNoticeAttachmentsByMaterialExtraId(materialExtraId);
            // 教材备注附件
            materialNoteAttachments =
            materialNoteAttachmentService.getMaterialNoteAttachmentByMaterialExtraId(materialExtraId);
        }
        // 判断内容是否已经发布或审核通过
        String fileNoticeDownLoadType = null;
        String fileNoteDownLoadType = null;
        if (Const.TRUE.booleanValue() == material.getIsPublished().booleanValue()) {
            fileNoticeDownLoadType = Const.MATERIAL_NOTICE_FILE_DOWNLOAD;
            fileNoteDownLoadType = Const.MATERIAL_NOTE_FILE_DOWNLOAD;
        } else {
            fileNoticeDownLoadType = Const.FILE_DOWNLOAD;
            fileNoteDownLoadType = Const.FILE_DOWNLOAD;
        }
        CmsContent cmsContent = cmsContentService.getCmsContentByMaterialId(materialId);
        if (ObjectUtil.notNull(cmsContent)) {
            Content content = contentService.get(cmsContent.getMid());
            if (ObjectUtil.notNull(content)) {
                resultMap.put("content", content.getContent());// MongoDB中教材通知
            }
        }
        if (CollectionUtil.isNotEmpty(materialNoticeAttachments)) {
            for (MaterialNoticeAttachment materialNoticeAttachment : materialNoticeAttachments) {
                String attachment = materialNoticeAttachment.getAttachment();
                materialNoticeAttachment.setAttachment(fileNoticeDownLoadType + attachment);// 拼接附件下载路径
            }
        }
        if (CollectionUtil.isNotEmpty(materialNoteAttachments)) {
            for (MaterialNoteAttachment materialNoteAttachment : materialNoteAttachments) {
                String attachment = materialNoteAttachment.getAttachment();
                materialNoteAttachment.setAttachment(fileNoteDownLoadType + attachment);// 拼接附件下载路径
            }
        }
        resultMap.put("materialNoticeAttachments", materialNoticeAttachments);// 教材通知附件
        resultMap.put("materialNoteAttachments", materialNoteAttachments);// 教材备注附件
        return resultMap;
    }

    @Override
    public PageResult<MateriaHistorylVO> listMaterialHistory(
    PageParameter<MateriaHistorylVO> pageParameter, String sessionId)
    throws CheckedServiceException {
        PageResult<MateriaHistorylVO> pageResult = new PageResult<MateriaHistorylVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        List<MateriaHistorylVO> materiaHistorylVOs =
        materialExtraDao.listMaterialHistory(pageParameter);
        if (CollectionUtil.isNotEmpty(materiaHistorylVOs)) {
            Integer count = materiaHistorylVOs.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(materiaHistorylVOs);
        }
        return pageResult;
    }

    @Override
    public Integer noticePublished(Long materialId, List<Long> orgIds, String sessionId)
    throws CheckedServiceException, IOException {
        if (CollectionUtil.isEmpty(orgIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "机构为空");
        }
        // 获取当前用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "请求用户不存在");
        }
        Integer count = 0;
        // 根据教材ID查询教材-机构关联表
        List<Long> OrgIds = materialOrgService.getListMaterialOrgByMaterialId(materialId);
        if (CollectionUtil.isEmpty(OrgIds)) {// 为空，初次发布
            systemMessageService.materialSend(materialId, orgIds);
        } else {// 不为空
            List<Long> newOrgIds = new ArrayList<Long>();// 新选中的机构
            for (Long orgId : orgIds) {
                if (!OrgIds.contains(orgId)) {
                    newOrgIds.add(orgId);
                }
            }
            systemMessageService.materialSend(materialId, newOrgIds);
        }
        CmsContent cmsContent = cmsContentService.getCmsContentByMaterialId(materialId);
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "没有找到对应的教材通知信息");
        }
        cmsContentService.updateCmsContent(new CmsContent(cmsContent.getId(),
                                                          true,
                                                          false,
                                                          Const.CMS_AUTHOR_STATUS_2,
                                                          0L, // authUserId
                                                              // 为0代表系统审核
                                                          DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                                                   DateUtil.getCurrentTime())));
        count = materialService.updateMaterial(new Material(materialId, true));
        return count;
    }

    /**
     * 
     * <pre>
	 * 功能描述：保存文件到MongoDB
	 * 使用示范：
	 *
	 * &#64;param files 临时文件路径
	 * &#64;param materialExtraId 教材通知备注ID
	 * &#64;param materialType 教材通知/备注
	 * &#64;throws CheckedServiceException
	 * </pre>
     */
    @SuppressWarnings("unused")
    private void saveFileToMongoDB(String[] files, Long materialExtraId, String materialType)
    throws CheckedServiceException, IOException {
        // 保存附件到MongoDB
        if (ArrayUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.length; i++) {
                File file = FileUpload.getFileByFilePath(files[i]);
                // 循环获取file数组中得文件
                if (StringUtil.notEmpty(file.getName())) {
                    String gridFSFileId =
                    fileService.saveLocalFile(file, FileType.CMS_ATTACHMENT, materialExtraId);
                    if (StringUtil.isEmpty(gridFSFileId)) {
                        throw new CheckedServiceException(
                                                          CheckedExceptionBusiness.MATERIAL_EXTRA,
                                                          CheckedExceptionResult.FILE_UPLOAD_FAILED,
                                                          "文件上传失败!");
                    }
                    // 教材备注附件保存
                    if (NOTE.equals(materialType)) {
                        MaterialNoteAttachment materialNoteAttachment =
                        materialNoteAttachmentService.addMaterialNoteAttachment(new MaterialNoteAttachment(
                                                                                                           materialExtraId,
                                                                                                           gridFSFileId,
                                                                                                           file.getName(),
                                                                                                           null));
                        if (ObjectUtil.isNull(materialNoteAttachment.getId())) {
                            throw new CheckedServiceException(
                                                              CheckedExceptionBusiness.MATERIAL_EXTRA,
                                                              CheckedExceptionResult.PO_ADD_FAILED,
                                                              "教材备注附件新增失败");
                        }
                    }
                    // 教材通知附件保存
                    if (NOTICE.equals(materialType)) {
                        MaterialNoticeAttachment materialNoticeAttachment =
                        materialNoticeAttachmentService.addMaterialNoticeAttachment(new MaterialNoticeAttachment(
                                                                                                                 materialExtraId,
                                                                                                                 gridFSFileId,
                                                                                                                 file.getName(),
                                                                                                                 null));
                        if (ObjectUtil.isNull(materialNoticeAttachment.getId())) {
                            throw new CheckedServiceException(
                                                              CheckedExceptionBusiness.MATERIAL_EXTRA,
                                                              CheckedExceptionResult.PO_ADD_FAILED,
                                                              "教材通知附件新增失败");
                        }
                    }

                }
                FileUtil.delFile(files[i]);// 删除本地临时文件
            }
        }
    }

}
