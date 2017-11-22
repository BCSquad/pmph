package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialContactDao;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialContactService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class MaterialContactServiceImpl extends BaseService implements MaterialContactService {

    @Autowired
    private MaterialContactDao materialContactDao;

    /**
     * 
     * @param MaterialContact 实体对象
     * @return 带主键的 MaterialContact
     * @throws CheckedServiceException
     */
    @Override
    public MaterialContact addMaterialContact(MaterialContact materialContact)
    throws CheckedServiceException {
        if (null == materialContact.getMaterialId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "教材为空");
        }
        if (null == materialContact.getContactUserId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "联系人姓名为空");
        }
        if (StringUtil.isEmpty(materialContact.getContactUserName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "联系人姓名为空");
        }
        if (StringUtil.isEmpty(materialContact.getContactPhone())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "联系人电话为空");
        }
        if (StringUtil.isEmpty(materialContact.getContactEmail())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "联系人邮箱为空");
        }
        materialContactDao.addMaterialContact(materialContact);
        return materialContact;
    }

    /**
     * 
     * @param id
     * @return MaterialContact
     * @throws CheckedServiceException
     */
    @Override
    public MaterialContact getMaterialContactById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialContactDao.getMaterialContactById(id);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteMaterialContactById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialContactDao.deleteMaterialContactById(id);
    }
    
    @Override 
    public Integer deleteMaterialContactsByMaterialId(Long materialId) throws CheckedServiceException{
    	if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "教材id为空");
        }
        return materialContactDao.deleteMaterialContactsByMaterialId(materialId) ;
    }
    
    

    /**
     * 根据id 更新materialContact不为null和''的字段
     * 
     * @param MaterialContact
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateMaterialContact(MaterialContact materialContact)
    throws CheckedServiceException {
        if (null == materialContact.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_CONTACT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialContactDao.updateMaterialContact(materialContact);
    }

	@Override
	public List<MaterialContact> listMaterialContactByMaterialId(Long materialId) throws CheckedServiceException {
		return materialContactDao.listMaterialContactByMaterialId(materialId);
	}

}
