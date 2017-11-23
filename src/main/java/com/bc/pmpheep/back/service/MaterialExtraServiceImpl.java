package com.bc.pmpheep.back.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialExtraDao;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.back.util.FileUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.MaterialExtraVO;
import com.bc.pmpheep.general.bean.FileType;
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
     * @param materialId 教材ID
     * @return MaterialExtra对象
     * @throws CheckedServiceException
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
     * @param materialExtraVO MaterialExtraVO
     * @return Map<String, Object>集合
     * @throws CheckedServiceException
     * </pre>
     */
    @Override
    public Map<String, Object> updateMaterialExtraAndNoticeFile(MaterialExtraVO materialExtraVO)
    throws CheckedServiceException, IOException {
        // 教材ID
        Long materialId = materialExtraVO.getMaterialId();
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材ID为空");
        }
        // 教材名称
        String materialName = materialExtraVO.getMaterialName();
        if (StringUtil.isEmpty(materialName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材名称为空");
        }
        if (materialName.length() > 40) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "教材名称过长");
        }
        // 更新教材
        materialService.updateMaterial(new Material(materialId, materialName));
        // 教材通知内容验证
        String notice = materialExtraVO.getNotice();
        if (StringUtil.isEmpty(notice)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "教材通知内容为空");
        }
        if (notice.length() > 2000) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "教材通知内容过长");
        }
        // 教材备注内容验证
        String note = materialExtraVO.getNote();
        if (StringUtil.isEmpty(note)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "教材备注内容为空");
        }
        if (note.length() > 2000) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "教材备注内容过长");
        }
        // 更新教材通知备注
        MaterialExtra materialExtra = this.getMaterialExtraByMaterialId(materialId);
        Long materialExtraId = materialExtra.getId();
        if (ObjectUtil.isNull(materialExtraId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "教材信息不存在");
        }
        this.updateMaterialExtra(new MaterialExtra(materialExtra.getId(), notice, note));
        // 教材通知附件
        String[] noticeFiles = materialExtraVO.getNoticeFiles();
        if (ArrayUtil.isNotEmpty(noticeFiles)) {
            this.saveFileToMongoDB(noticeFiles, materialExtraId, NOTICE);
        }
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
        String[] noteFiles = materialExtraVO.getNoteFiles();
        if (ArrayUtil.isNotEmpty(noteFiles)) {
            this.saveFileToMongoDB(noteFiles, materialExtraId, NOTE);
        }
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
        return null;
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
     */
    @Override
    public Map<String, Object> getMaterialExtraAndNoticeDetail(Long materialId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材主键为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Material material = materialService.getMaterialById(materialId);
        resultMap.put("materialName", material.getMaterialName());// 教材名称
        List<MaterialContact> materialContacts =
        materialContactService.listMaterialContactByMaterialId(materialId);
        resultMap.put("联系人", materialContacts);// 联系人
        MaterialExtra materialExtra = this.getMaterialExtraByMaterialId(materialId);
        resultMap.put("教材通知备注", materialExtra);// 教材通知备注
        Long materialExtraId = materialExtra.getId();
        if (ObjectUtil.notNull(materialExtraId)) {
            List<MaterialNoticeAttachment> materialNoticeAttachments =
            materialNoticeAttachmentService.getMaterialNoticeAttachmentsByMaterialExtraId(materialExtraId);
            List<MaterialNoteAttachment> materialNoteAttachments =
            materialNoteAttachmentService.getMaterialNoteAttachmentByMaterialExtraId(materialExtraId);
            resultMap.put("教材通知附件", materialNoticeAttachments);// 教材通知附件
            resultMap.put("教材备注附件", materialNoteAttachments);// 教材备注附件
        }
        return resultMap;
    }

    /**
     * 
     * <pre>
     * 功能描述：保存文件到MongoDB
     * 使用示范：
     *
     * @param files 临时文件路径
     * @param materialExtraId 教材通知备注ID
     * @param materialType 教材通知/备注
     * @throws CheckedServiceException
     * </pre>
     */
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
