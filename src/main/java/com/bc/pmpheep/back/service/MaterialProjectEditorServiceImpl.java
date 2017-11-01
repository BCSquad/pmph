package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialProjectEditorDao;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *@author MrYang 
 *@CreateDate 2017年11月1日 下午5:33:16
 *
 **/
@Service
public class MaterialProjectEditorServiceImpl extends BaseService implements MaterialProjectEditorService {
	
	@Autowired
	private MaterialProjectEditorDao materialProjectEditorDao;
	
	@Override
	public MaterialProjectEditor addMaterialProjectEditor(MaterialProjectEditor materialProjectEditor) throws CheckedServiceException{
		if (null == materialProjectEditor.getMaterialId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PROJECTER, CheckedExceptionResult.NULL_PARAM, "教材id为空");
        }
		if (null == materialProjectEditor.getEditorId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PROJECTER, CheckedExceptionResult.NULL_PARAM, "项目编辑为空");
        }
		materialProjectEditorDao.addMaterialProjectEditor(materialProjectEditor);
		return materialProjectEditor;
	}
	
	@Override
	public Integer addMaterialProjectEditors(List<MaterialProjectEditor> materialProjectEditors) throws CheckedServiceException{
		if(null == materialProjectEditors || materialProjectEditors.size() ==0){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PROJECTER, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return materialProjectEditorDao.addMaterialProjectEditors(materialProjectEditors);
	}
	
	@Override
	public Integer deleteMaterialProjectEditorByMaterialId(Long materialId) throws CheckedServiceException{
		if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PROJECTER, CheckedExceptionResult.NULL_PARAM, "教材id为空");
        }
		return materialProjectEditorDao.deleteMaterialProjectEditorByMaterialId(materialId);
	}
}
