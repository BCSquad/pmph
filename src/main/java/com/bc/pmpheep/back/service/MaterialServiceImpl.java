package com.bc.pmpheep.back.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialDao;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * MaterialService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class MaterialServiceImpl extends BaseService implements MaterialService {

    @Autowired
    private MaterialDao materialDao;
    
    @Autowired
    private MaterialExtensionService materialExtensionService;
    
    @Autowired
    private MaterialContactService materialContactService; 
    
    @Autowired
    private MaterialProjectEditorService materialProjectEditorService; 
    
    @Autowired
    private MaterialExtraService materialExtraService;
    
    @Autowired
    private MaterialNoticeAttachmentService materialNoticeAttachmentService;
    
    @Autowired
    private MaterialNoteAttachmentService materialNoteAttachmentService;
    
    @Autowired
	private FileService fileService;
    
    @Autowired
    private PmphUserService pmphUserService;
    

    /**
     * 
     * @param Material 实体对象
     * @return 带主键的 Material
     * @throws CheckedServiceException
     */
    @Override
    public Material addMaterial(Material material) throws CheckedServiceException {
        materialDao.addMaterial(material);
        return material;
    }
    
    @Override
    public Long addOrUpdateMaterial(String sessionId, 
    		String   materialContacts,
    		String   materialExtensions, 
    		String   materialProjectEditors,
    		Material material,
		    MaterialExtra     materialExtra,
		    MultipartFile[]   noticeFiles,
		    String materialNoticeAttachments,
		    MultipartFile[]   noteFiles,
		    String materialNoteAttachments,
		    boolean isUpdate) throws CheckedServiceException {
    	//获取当前用户
    	PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
    	if(null == pmphUser){
    		 throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "请求用户不存在");
    	}
    	//如果是更新教材，判断主键
    	if(isUpdate&&(null ==material.getId())){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材更新时主键为空");
    	}
    	//教材名称的验证
    	if(StringUtil.isEmpty(material.getMaterialName())){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材名称为空");
    	}
    	if(material.getMaterialName().length()>40){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "教材名称过长");
    	}
    	//教材轮次验证
    	if(null == material.getMaterialRound()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材轮次为空");
    	}
    	//显示报名截止时间
    	if(null == material.getDeadline()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "显示报名截止时间为空");
    	}
    	//如果是新建教材，显示报名截止时间必须大于当前时间
    	if(!isUpdate&&material.getDeadline().getTime() <= new Date().getTime()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "显示报名截止时间必须大于当前时间");
    	}
    	//实际报名截止日期
    	if(null == material.getActualDeadline()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "实际报名截止日期为空");
    	}
    	//如果是新建教材，实际报名截止日期必须大于当前时间
    	if(!isUpdate&&material.getActualDeadline().getTime() <= new Date().getTime()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "实际报名截止日期必须大于当前时间");
    	}
    	//实际报名截止日期和显示报名截止时间比较
    	if(material.getActualDeadline().getTime()<material.getDeadline().getTime()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "实际报名截止日期不能小于显示报名截止时间");
    	}
    	//年龄计算截止日期
    	if(null == material.getAgeDeadline()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "年龄计算截止日期为空");
    	}
    	//邮寄地址验证
    	if(StringUtil.isEmpty(material.getMailAddress())){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "邮寄地址为空");
    	}
    	if(material.getMailAddress().length()>100){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "邮寄地址过长");
    	}
    	//教材类型验证
    	if(null == material.getMaterialType()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材类型为空");
    	}
    	if(null == material.getDirector()){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材主任为空");
    	}
    	//教材通知备注内容验证
    	if(StringUtil.isEmpty(materialExtra.getNotice())){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材通知内容为空");
    	}
    	if(materialExtra.getNotice().length()>2000){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "教材通知内容过长");
    	}
    	if(StringUtil.isEmpty(materialExtra.getNote())){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材备注内容为空");
    	}
    	if(materialExtra.getNote().length()>2000){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "教材备注内容过长");
    	}
    	//获取主任
    	PmphUser director=pmphUserService.get(material.getDirector());
    	if(null  == director ){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "找不到对应的主任");
    	}else if(null == director.getDepartmentId() ){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "主任对应的机构为空");
    	}
    	//创建人
    	material.setFounderId(pmphUser.getId());
    	//教材所属部门
    	material.setDepartmentId(director.getDepartmentId());
    	//修改人
    	material.setMenderId(pmphUser.getId());
    	//保存或者更新教材
    	if(isUpdate){
    		materialDao.updateMaterial(material);
    	}else{
    		materialDao.addMaterial(material);
    	}
    	Long materialId = material.getId();
    	Gson gson = new Gson();
    	//扩展项目转换
    	List<MaterialExtension> oldMaterialExtensionlist =materialExtensionService.getMaterialExtensionByMaterialId(materialId);
    	String newMaterialExtensionIds=",";
    	if(!StringUtil.isEmpty(materialExtensions)){
    		List<MaterialExtension> materialExtensionlist = gson.fromJson(materialExtensions,new TypeToken<ArrayList<MaterialExtension>>() { }.getType());
    		for(MaterialExtension materialExtension:materialExtensionlist){
    			if(null == materialExtension){
    				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材联系人对象为空");
    			}
    			if(StringUtil.isEmpty(materialExtension.getExtensionName())){
    				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "扩展项名称为空");
    			}
    			if(materialExtension.getExtensionName().length()>20){
    				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "扩展项名称太长");
    			}
    			materialExtension.setMaterialId(materialId);
    			if(null == materialExtension.getId()){ //保存或者修改扩展项
    				materialExtensionService.addMaterialExtension(materialExtension);
    			}else{
    				materialExtensionService.updateMaterialExtension(materialExtension);
    			}
    			newMaterialExtensionIds += materialExtension.getId()+",";
    		}
    	}
    	for(MaterialExtension oldMaterialExtension: oldMaterialExtensionlist){ //删除删除的MaterialExtension
    		if(!newMaterialExtensionIds.contains(","+oldMaterialExtension.getId()+",")){ //不包含
    			materialExtensionService.deleteMaterialExtensionById(oldMaterialExtension.getId());
    		}
    	}
    	//联系人转换
    	materialContactService.deleteMaterialContactsByMaterialId(materialId); //删除已经有的联系人
    	if(StringUtil.isEmpty(materialContacts)){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "教材联系人参数有误");
    	}
		List<MaterialContact> materialContactlist = gson.fromJson(materialContacts,new TypeToken<ArrayList<MaterialContact>>() { }.getType());
		if(null == materialContactlist || materialContactlist.size()==0){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材联系人为空");
		}
		for(MaterialContact materialContact:materialContactlist){
			if(null == materialContact){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材联系人对象为空");
			}
			if(null == materialContact.getContactUserId()){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "联系人为空");
			}
			if(StringUtil.isEmpty(materialContact.getContactUserName())){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "联系人姓名为空");
			}
			if(materialContact.getContactUserName().length()>20){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "联系人姓名太长");
			}
			if(StringUtil.isEmpty(materialContact.getContactPhone())){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "联系电话为空");
			}
			if(materialContact.getContactPhone().length()>25){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "联系电话太长");
			}
			if(StringUtil.isEmpty(materialContact.getContactEmail())){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "联系邮箱为空");
			}
			if(materialContact.getContactEmail().length()>40){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "联系邮箱太长");
			}
			if(null == materialContact.getSort()){
				materialContact.setSort(999);
			}
			materialContact.setMaterialId(materialId);
			//保存联系人
			materialContactService.addMaterialContact(materialContact);
		}
		//项目编辑转换
		materialProjectEditorService.deleteMaterialProjectEditorByMaterialId(materialId) ; //先删除该教材下的项目编辑
		if(StringUtil.isEmpty(materialProjectEditors)){
    		throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "教材项目编辑参数有误");
    	}
		List<MaterialProjectEditor> materialProjectEditorlist = gson.fromJson(materialProjectEditors,new TypeToken<ArrayList<MaterialProjectEditor>>() { }.getType());
		for(MaterialProjectEditor materialProjectEditor:materialProjectEditorlist){
			if(null == materialProjectEditor || null == materialProjectEditor.getEditorId()){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "项目编辑为空");
			}
			materialProjectEditor.setMaterialId(materialId);
			//保存项目编辑
			materialProjectEditorService.addMaterialProjectEditor(materialProjectEditor);
		}
    	//保存教材通知备注
		materialExtra.setMaterialId(materialId);
		if(null ==  materialExtra.getId()){
			materialExtraService.addMaterialExtra(materialExtra);
		}else{
			materialExtraService.updateMaterialExtra(materialExtra);
		}
		//判断教材备注附件和教材通知附件
		List<MaterialNoticeAttachment> materialNoticeAttachmentlist = new ArrayList<MaterialNoticeAttachment>(5);
		if(!StringUtil.isEmpty(materialNoticeAttachments)){
			materialNoticeAttachmentlist = gson.fromJson( materialNoticeAttachments, new TypeToken<ArrayList<MaterialNoticeAttachment>>() { }.getType() );
		}
		if(null != noticeFiles && noticeFiles.length >0){
			for(MultipartFile notice:noticeFiles){
				if(null == notice || !(notice instanceof MultipartFile)){
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材通知附件为空");
				}
				if(notice.getName().length()>80){
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "教材通知附件文件名过长");
				}
			}
		}
		if((null == noticeFiles && materialNoticeAttachmentlist.size()==0)||(materialNoticeAttachmentlist.size()+noticeFiles.length == 0)){//已有的数量加上新增的数量
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材通知附件为空");
		}
		List<MaterialNoteAttachment> materialNoteAttachmentList = new ArrayList<MaterialNoteAttachment>(5);
		if(!StringUtil.isEmpty(materialNoteAttachments)){
			materialNoteAttachmentList = gson.fromJson( materialNoteAttachments, new TypeToken<ArrayList<MaterialNoteAttachment>>() { }.getType() );
		}
		if(null != noteFiles && noteFiles.length > 0){
			for(MultipartFile note:noteFiles){
				if(null == note || !(note instanceof MultipartFile)){
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材备注附件为空");
				}
				if(note.getName().length()>80){
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.ILLEGAL_PARAM, "教材备注附件文件名过长");
				}
			}
		}
		if((null == noteFiles && materialNoteAttachmentList.size()==0)||(materialNoteAttachmentList.size()+noteFiles.length == 0)){//已有的数量加上新增的数量
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材备注附件为空");
		}
		//保存教材通知附件
		List<MaterialNoticeAttachment> oldMaterialNoticeAttachmentlist =materialNoticeAttachmentService.getMaterialNoticeAttachmentsByMaterialExtraId(materialExtra.getId());
		String newMaterialNoticeAttachmentIds=",";
		for(MaterialNoticeAttachment materialNoticeAttachment:materialNoticeAttachmentlist){
			if(null == materialNoticeAttachment.getId()){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材通知id为空");
			}
			newMaterialNoticeAttachmentIds += materialNoticeAttachment.getId()+",";
		}
		if(null != noticeFiles && noticeFiles.length >0){
			for(MultipartFile notice:noticeFiles){
				MaterialNoticeAttachment materialNoticeAttachment =new MaterialNoticeAttachment();
				materialNoticeAttachment.setAttachment("---------");
				materialNoticeAttachment.setAttachmentName(notice.getName());
				materialNoticeAttachment.setDownload(0L);
				materialNoticeAttachment.setMaterialExtraId(materialExtra.getId());
				//保存通知
				materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
				String noticeId;
				try {
					//保存通知文件
					noticeId = fileService.save(notice,  FileType.MATERIAL_NOTICE_ATTACHMENT,materialNoticeAttachment.getId());
				} catch (Exception e) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.FILE_UPLOAD_FAILED, "上传文件失败");
				}
				materialNoticeAttachment.setAttachment(noticeId);
				//更新通知
				materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
				newMaterialNoticeAttachmentIds += materialNoticeAttachment.getId()+",";
			}
		}
		for(MaterialNoticeAttachment materialNoticeAttachment:oldMaterialNoticeAttachmentlist){
			if(!newMaterialNoticeAttachmentIds.contains(","+materialNoticeAttachment.getId()+",")){//不包含
				fileService.remove(materialNoticeAttachment.getAttachment());					   //删除文件
				materialNoticeAttachmentService.deleteMaterialNoticeAttachmentById(materialNoticeAttachment.getId());
			}
		}
		//保存教材备注附件
		List<MaterialNoteAttachment> oldMaterialNoteAttachmentlist =materialNoteAttachmentService.getMaterialNoteAttachmentByMaterialExtraId(materialExtra.getId());
		String newMaterialNoteAttachmentIds=",";
		for(MaterialNoteAttachment materialNoteAttachment:materialNoteAttachmentList){
			if(null == materialNoteAttachment.getId()){
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "教材备注id为空");
			}
			newMaterialNoteAttachmentIds += materialNoteAttachment.getId()+",";
		}
		if(null != noteFiles && noteFiles.length >0){
			for(MultipartFile note:noteFiles){
				MaterialNoteAttachment materialNoteAttachment =new MaterialNoteAttachment();
				materialNoteAttachment.setAttachment("---------");
				materialNoteAttachment.setAttachmentName(note.getName());
				materialNoteAttachment.setDownload(0L);
				materialNoteAttachment.setMaterialExtraId(materialExtra.getId());
				//保存备注
				materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
				String noticeId;
				try {
					//保存备注文件
					noticeId = fileService.save(note,  FileType.MATERIAL_NOTICE_ATTACHMENT,materialNoteAttachment.getId());
				} catch (Exception e) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.FILE_UPLOAD_FAILED, "上传文件失败");
				}
				materialNoteAttachment.setAttachment(noticeId);
				//更新备注
				materialNoteAttachmentService.updateMaterialNoteAttachment(materialNoteAttachment);
				newMaterialNoteAttachmentIds += materialNoteAttachment.getId()+",";
			}
		}
		for(MaterialNoteAttachment materialNoteAttachment:oldMaterialNoteAttachmentlist){
			if(!newMaterialNoteAttachmentIds.contains(","+materialNoteAttachment.getId()+",")){//不包含
				fileService.remove(materialNoteAttachment.getAttachment());					   //删除文件
				materialNoteAttachmentService.deleteMaterialNoteAttachmentById(materialNoteAttachment.getId());
			}
		}
		return material.getId();
    }

    /**
     * 
     * @param id
     * @return Material
     * @throws CheckedServiceException
     */
    @Override
    public Material getMaterialById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialDao.getMaterialById(id);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteMaterialById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialDao.deleteMaterialById(id);
    }

    /**
     * 通过主键id更新material 不为null 的字段
     * 
     * @param Material
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateMaterial(Material material) throws CheckedServiceException {
        if (null == material.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialDao.updateMaterial(material);
    }

    @Override
    public List<Material> getListMaterial(String materialName) {
        return materialDao.getListMaterial(materialName);
    }

}
