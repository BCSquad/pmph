package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialNoteAttachmentDao;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *@author MrYang 
 *@CreateDate 2017年10月25日 下午3:25:58
 *
 **/

@Service
public class MaterialNoteAttachmentServiceImpl extends BaseService implements  MaterialNoteAttachmentService{
	
	@Autowired
	private MaterialNoteAttachmentDao materialNoticeAttachmentDao;
	
	@Override
	public MaterialNoteAttachment addMaterialNoteAttachment(MaterialNoteAttachment materialNoteAttachment) throws CheckedServiceException{
		if (ObjectUtil.isNull(materialNoteAttachment.getMaterialExtraId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "教材通知备注id");
        }
        if (StringUtil.isEmpty(materialNoteAttachment.getAttachment())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "备注内容附件");
        }
        if (StringUtil.isEmpty(materialNoteAttachment.getAttachmentName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "附件名称");
        }
        materialNoticeAttachmentDao.addMaterialNoteAttachment(materialNoteAttachment);
		return materialNoteAttachment;
	}
	
	@Override
	public Integer  updateMaterialNoteAttachment(MaterialNoteAttachment materialNoteAttachment) throws CheckedServiceException{
		if (ObjectUtil.isNull(materialNoteAttachment.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
		if (ObjectUtil.isNull(materialNoteAttachment.getMaterialExtraId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "教材通知备注id");
        }
        if (StringUtil.isEmpty(materialNoteAttachment.getAttachment())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "通知内容附件");
        }
        if (StringUtil.isEmpty(materialNoteAttachment.getAttachmentName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "附件名称");
        }
        return materialNoticeAttachmentDao.updateMaterialNoteAttachment(materialNoteAttachment);
	}
	
	@Override
	public Integer deleteMaterialNoteAttachmentById(Long id)throws CheckedServiceException{
		if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
		return materialNoticeAttachmentDao.deleteMaterialNoteAttachmentById(id);
	}
	
	@Override
	public Integer deleteMaterialNoteAttachmentByMaterialExtraId(Long materialExtraId)throws CheckedServiceException{
		if (null == materialExtraId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
		return materialNoticeAttachmentDao.deleteMaterialNoteAttachmentByMaterialExtraId(materialExtraId);
	}
	
	@Override
	public List<MaterialNoteAttachment> getMaterialNoteAttachmentByMaterialExtraId(Long materialExtraId)throws CheckedServiceException{
		if (null == materialExtraId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
		return materialNoticeAttachmentDao.getMaterialNoteAttachmentByMaterialExtraId(materialExtraId);
	}

}
