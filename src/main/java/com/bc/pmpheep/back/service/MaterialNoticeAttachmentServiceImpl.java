package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialNoticeAttachmentDao;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
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
public class MaterialNoticeAttachmentServiceImpl extends BaseService implements  MaterialNoticeAttachmentService{
	
	@Autowired
	private MaterialNoticeAttachmentDao materialNoticeAttachmentDao;
	
	@Override
	public MaterialNoticeAttachment addMaterialNoticeAttachment(MaterialNoticeAttachment materialNoticeAttachment) throws CheckedServiceException{
		if (ObjectUtil.isNull(materialNoticeAttachment.getMaterialExtraId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "教材通知备注id");
        }
        if (StringUtil.isEmpty(materialNoticeAttachment.getAttachment())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "通知内容附件");
        }
        if (StringUtil.isEmpty(materialNoticeAttachment.getAttachmentName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "附件名称");
        }
        materialNoticeAttachmentDao.addMaterialNoticeAttachment(materialNoticeAttachment);
		return materialNoticeAttachment;
	}
	
	@Override
	public Integer  updateMaterialNoticeAttachment(MaterialNoticeAttachment materialNoticeAttachment) throws CheckedServiceException{
		if (ObjectUtil.isNull(materialNoticeAttachment.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
		if (ObjectUtil.isNull(materialNoticeAttachment.getMaterialExtraId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "教材通知备注id");
        }
        if (StringUtil.isEmpty(materialNoticeAttachment.getAttachment())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "通知内容附件");
        }
        if (StringUtil.isEmpty(materialNoticeAttachment.getAttachmentName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "附件名称");
        }
        return materialNoticeAttachmentDao.updateMaterialNoticeAttachment(materialNoticeAttachment);
	}
	
	
	@Override
	public List<MaterialNoticeAttachment> getMaterialNoticeAttachmentsByMaterialExtraId(Long materialExtraId)throws CheckedServiceException{
		if(null == materialExtraId){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return materialNoticeAttachmentDao.getMaterialNoticeAttachmentsByMaterialExtraId(materialExtraId);
	}
	
	@Override
	public Integer deleteMaterialNoticeAttachmentsByMaterialExtraId(Long materialExtraId)throws CheckedServiceException{
		if(null == materialExtraId){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return materialNoticeAttachmentDao.deleteMaterialNoticeAttachmentsByMaterialExtraId(materialExtraId);
	}
	
	@Override
	public Integer deleteMaterialNoticeAttachmentById(Long id)throws CheckedServiceException{
		if(null == id){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return  materialNoticeAttachmentDao.deleteMaterialNoticeAttachmentById(id);
	}

}
