package com.bc.pmpheep.back.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialDao;
import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.vo.MaterialListVO;
import com.bc.pmpheep.back.vo.MaterialMainInfoVO;
import com.bc.pmpheep.back.vo.MaterialProjectEditorVO;
import com.bc.pmpheep.back.vo.MaterialVO;
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
	PmphRoleDao roleDao;

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
	private MaterialService materialService;

	@Autowired
	private MaterialNoticeAttachmentService materialNoticeAttachmentService;

	@Autowired
	private MaterialNoteAttachmentService materialNoteAttachmentService;

	@Autowired
	private FileService fileService;

	@Autowired
	private PmphUserService pmphUserService;

	@Autowired
	private DecExtensionService decExtensionService;

	@Autowired
	TextbookService textbookService;

	@Autowired
	PmphGroupService pmphGroupService;

	@Autowired
	CmsContentService cmsContentService;

	@Autowired
	private MaterialTypeService materialTypeService;

	@Autowired
	WXQYUserService wxqyUserService;
	@Autowired
	WxSendMessageService wxSendMessageService;


	/**
	 * 
	 * @param  //Material
	 *            实体对象
	 * @return 带主键的 Material
	 * @throws CheckedServiceException
	 */
	@Override
	public Material addMaterial(Material material) throws CheckedServiceException {
		materialDao.addMaterial(material);
		return material;
	}

	@Override
	public Long addOrUpdateMaterial(HttpServletRequest request, MaterialVO materialVO,
			// MultipartFile[] noticeFiles,
			// MultipartFile[] noteFiles,
			boolean isUpdate) throws CheckedServiceException, IOException {
		//企业微信推送对象的微信id集合
		Set<String> touserOpenidSet = new HashSet<String>();
		List<Long> useridList = new ArrayList<Long>();
		if (null == request.getSession(false)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"会话过期");
		}
		// 获取当前用户
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		Material material = materialVO.getMaterial();
		// 如果是更新教材，判断主键
		if (isUpdate && (null == material.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材更新时主键为空");
		}
		// 教材名称的验证
		if (StringUtil.isEmpty(material.getMaterialName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材名称为空");
		}
		if (material.getMaterialName().length() > 50) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"教材名称过长");
		}
		// 教材轮次验证
		if (null == material.getMaterialRound()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材轮次为空");
		}
		// 显示报名截止时间
		if (null == material.getDeadline()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"显示报名截止时间为空");
		}
		// 如果是新建教材，显示报名截止时间必须大于当前时间
		if (!isUpdate && material.getDeadline().getTime() <= new Date().getTime()) {
			// throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
			// CheckedExceptionResult.ILLEGAL_PARAM,
			// "显示报名截止时间必须大于当前时间");
		}
		// 实际报名截止日期
		if (null == material.getActualDeadline()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"实际报名截止日期为空");
		}
		// 如果是新建教材，实际报名截止日期必须大于当前时间
		if (!isUpdate && material.getActualDeadline().getTime() <= new Date().getTime()) {
			// throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
			// CheckedExceptionResult.ILLEGAL_PARAM,
			// "实际报名截止日期必须大于当前时间");
		}
		// 实际报名截止日期和显示报名截止时间比较
		if (material.getActualDeadline().getTime() < material.getDeadline().getTime()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"实际报名截止日期不能小于显示报名截止时间");
		}
		// 年龄计算截止日期
		if (null == material.getAgeDeadline()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"年龄计算截止日期为空");
		}
		// 邮寄地址验证
		if (StringUtil.isEmpty(material.getMailAddress())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"邮寄地址为空");
		}
		if (material.getMailAddress().length() > 100) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"邮寄地址过长");
		}
		// 教材类型验证
		String materialType = materialVO.getMaterialType();
		if (StringUtil.isEmpty(materialType) || "[]".equals(materialType.replace(" ", ""))
				|| "[NaN]".equals(materialType.replace(" ", ""))) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材类型为空");
		}
		if (null == material.getDirector()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材主任为空");
		}
		// 教材通知备注内容验证
		MaterialExtra materialExtra = materialVO.getMaterialExtra();
		if (StringUtil.isEmpty(materialExtra.getNotice())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材通知内容为空");
		}
		if (materialExtra.getNotice().length() > 2000) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"教材通知内容过长");
		}
		// if (StringUtil.isEmpty(materialExtra.getNote())) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		// CheckedExceptionResult.NULL_PARAM,
		// "教材备注内容为空");
		// }
		if ("null".equals(materialExtra.getNote()) || "[]".equals(materialExtra.getNote())) {
			materialExtra.setNote("");
		}
		if (null != materialExtra.getNote() && materialExtra.getNote().length() > 2000) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"教材备注内容过长");
		}
		Gson gson = new Gson();
		// 获取主任
		PmphUser director = pmphUserService.get(material.getDirector());
		if (null == director) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"找不到对应的主任");
		} else if (null == director.getDepartmentId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"主任对应的机构为空");
		}

		// //给主任添加角色
		// String roleName="主任";//通过roleName查询roleid
		// List<PmphRole> pmphRoles=pmphRoleService.getList(roleName);
		// if (ObjectUtil.isNull(material.getDirector()) ||
		// ObjectUtil.isNull(pmphRoles.get(0).getId())) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
		// CheckedExceptionResult.NULL_PARAM, "角色ID或主任ID为空时禁止新增");
		// }
		// //先查看该主任是否已有主任角色 没有则新加
		// List<PmphUserRole>
		// pmphUserRoles=pmphRoleService.getUserRoleList(material.getDirector(),pmphRoles.get(0).getId());
		// if(ObjectUtil.isNull(pmphUserRoles)&&pmphUserRoles.size() == 0){
		// pmphRoleService.addUserRole(material.getDirector(),
		// pmphRoles.get(0).getId());
		// }
		// 教材所属部门
		material.setDepartmentId(director.getDepartmentId());
		// 修改人
		material.setMenderId(pmphUser.getId());
		// 教材类型
		List<Long> materialTypeList = gson.fromJson(materialType, new TypeToken<ArrayList<Long>>() {
		}.getType());
		material.setMaterialType(materialTypeList.get(materialTypeList.size() - 1));
		// 设置权限
		material.setPlanPermission(Integer.valueOf(materialVO.getCehuaPowers(), 2));
		material.setProjectPermission(Integer.valueOf(materialVO.getProjectEditorPowers(), 2));
		// 保存或者更新教材
		if (isUpdate) {
			materialDao.updateMaterial(material);
		} else {
			// 创建人
			material.setFounderId(pmphUser.getId());
			material.setIsPublic(true);
			materialDao.addMaterial(material);
		}
		Long materialId = material.getId();

		// 扩展项转换
		List<MaterialExtension> oldMaterialExtensionlist = materialExtensionService
				.getMaterialExtensionByMaterialId(materialId);
		String newMaterialExtensionIds = ",";
		String materialExtensions = materialVO.getMaterialExtensions();
		if (!StringUtil.isEmpty(materialExtensions)) {
			List<MaterialExtension> materialExtensionlist = gson.fromJson(materialExtensions,
					new TypeToken<ArrayList<MaterialExtension>>() {
					}.getType());
			for (MaterialExtension materialExtension : materialExtensionlist) {
				if (null == materialExtension) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.NULL_PARAM, "扩展项对象为空");
				}
				if (StringUtil.isEmpty(materialExtension.getExtensionName())) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.NULL_PARAM, "扩展项名称为空");
				}
				if (materialExtension.getExtensionName().length() > 100) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.NULL_PARAM, "扩展项名称太长");
				}
				materialExtension.setMaterialId(materialId);
				if (null == materialExtension.getId()) { // 保存或者修改扩展项
					materialExtensionService.addMaterialExtension(materialExtension);
				} else {
					materialExtensionService.updateMaterialExtension(materialExtension);
				}
				newMaterialExtensionIds += materialExtension.getId() + ",";
			}
		}
		for (MaterialExtension oldMaterialExtension : oldMaterialExtensionlist) { // 删除删除的MaterialExtension
			if (!newMaterialExtensionIds.contains("," + oldMaterialExtension.getId() + ",")) { // 不包含
				// 删除扩展项
				materialExtensionService.deleteMaterialExtensionById(oldMaterialExtension.getId());
				// 删除扩展值
				decExtensionService.deleteDecExtensionByExtensionId(oldMaterialExtension.getId());
			}
		}
		// 联系人转换
		String materialContacts = materialVO.getMaterialContacts();
		materialContactService.deleteMaterialContactsByMaterialId(materialId); // 删除已经有的联系人
		String contactUserNamesStr = ""; //联系人姓名字符串，以,分隔
		if (StringUtil.isEmpty(materialContacts)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"教材联系人参数有误");
		}
		List<MaterialContact> materialContactlist = gson.fromJson(materialContacts,
				new TypeToken<ArrayList<MaterialContact>>() {
				}.getType());
		if (null == materialContactlist || materialContactlist.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材联系人为空");
		}
		for (MaterialContact materialContact : materialContactlist) {
			if (null == materialContact) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"教材联系人对象为空");
			}
			if (null == materialContact.getContactUserId()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"联系人为空");
			}
			if (StringUtil.isEmpty(materialContact.getContactUserName())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"联系人姓名为空");
			}
			if (materialContact.getContactUserName().length() > 20) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"联系人姓名太长");
			}
			if (StringUtil.isEmpty(materialContact.getContactPhone())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"联系电话为空");
			}
			if (materialContact.getContactPhone().length() > 25) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"联系电话太长");
			}
			if (StringUtil.isEmpty(materialContact.getContactEmail())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"联系邮箱为空");
			}
			if (materialContact.getContactEmail().length() > 40) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"联系邮箱太长");
			}
			if (null == materialContact.getSort()) {
				materialContact.setSort(999);
			}
			materialContact.setMaterialId(materialId);
			// 保存联系人
			materialContactService.addMaterialContact(materialContact);
			contactUserNamesStr += materialContact.getContactUserName()+",";
		}
		// 项目编辑转换
		materialProjectEditorService.deleteMaterialProjectEditorByMaterialId(materialId); // 先删除该教材下的项目编辑
		String materialProjectEditors = materialVO.getMaterialProjectEditors();
		String projectEditorNamesStr = "";
		if (StringUtil.isEmpty(materialProjectEditors)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"教材项目编辑参数有误");
		}
		List<MaterialProjectEditorVO> materialProjectEditorVOlist = gson.fromJson(materialProjectEditors,
				new TypeToken<ArrayList<MaterialProjectEditorVO>>() {
				}.getType());
		for (MaterialProjectEditorVO materialProjectEditorVO : materialProjectEditorVOlist) {
			if (null == materialProjectEditorVO || null == materialProjectEditorVO.getEditorId()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"项目编辑为空");
			}
			MaterialProjectEditor materialProjectEditor = new MaterialProjectEditor();
			materialProjectEditor.setEditorId(materialProjectEditorVO.getEditorId());
			materialProjectEditor.setMaterialId(materialId);
			// 保存项目编辑
			materialProjectEditorService.addMaterialProjectEditor(materialProjectEditor);
			PmphUser projectEditorUser = pmphUserService.get(materialProjectEditorVO.getEditorId());
			//项目编辑加入企业微信推送对象集合
			touserOpenidSet.add(projectEditorUser.getOpenid());
			useridList.add(projectEditorUser.getId());
			projectEditorNamesStr += materialProjectEditorVO.getRealname()+",";
			// // 项目编辑绑定角色
			// String rolename="项目编辑";//通过roleName查询roleid
			// List<PmphRole> pmphRoleList=pmphRoleService.getList(rolename);
			// if (ObjectUtil.isNull(materialId) ||
			// ObjectUtil.isNull(pmphRoleList.get(0).getId())) {
			// throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
			// CheckedExceptionResult.NULL_PARAM, "角色ID或项目编辑ID为空时禁止新增");
			// }
			// //判断该用户是否有项目编辑角色 若没有则新加
			// List<PmphUserRole> pmphUserRoles2=pmphRoleService.getUserRoleList(materialId,
			// pmphRoleList.get(0).getId());
			// if(ObjectUtil.isNull(pmphUserRoles2) && pmphUserRoles2.size() == 0){
			// roleDao.addUserRole(materialId, pmphRoleList.get(0).getId());//给项目编辑绑定角色
			// }
		}
		// 保存教材通知备注
		materialExtra.setMaterialId(materialId);
		if (null == materialExtra.getId()) {
			materialExtraService.addMaterialExtra(materialExtra);
		} else {
			materialExtraService.updateMaterialExtra(materialExtra);
		}
		// 保存通知附件
		String materialNoticeAttachments = materialVO.getMaterialNoticeAttachments();
		if (StringUtil.isEmpty(materialNoticeAttachments)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材通知附件为空");
		}
		List<MaterialNoticeAttachment> materialNoticeAttachmentlist = gson.fromJson(materialNoticeAttachments,
				new TypeToken<ArrayList<MaterialNoticeAttachment>>() {
				}.getType());
		// 原来有的通知附件
		List<MaterialNoticeAttachment> oldMaterialNoticeAttachmentlist = materialNoticeAttachmentService
				.getMaterialNoticeAttachmentsByMaterialExtraId(materialExtra.getId());
		String newTempNoticeFileIds = ",";
		for (MaterialNoticeAttachment materialNoticeAttachment : materialNoticeAttachmentlist) {
			if (null == materialNoticeAttachment.getId()) {
				/*String tempFileId = materialNoticeAttachment.getAttachment();
				byte[] fileByte = (byte[]) request.getSession(false).getAttribute(tempFileId);
				String fileName = (String) request.getSession(false).getAttribute("fileName_" + tempFileId);
				materialNoticeAttachment.setAttachment(String.valueOf(new Date().getTime()));

				materialNoticeAttachment.setAttachmentName(fileName);*/

				materialNoticeAttachment.setDownload(0L);
				materialNoticeAttachment.setMaterialExtraId(materialExtra.getId());
				// 保存通知
				materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
				String noticeId;
				// 保存通知文件
				/*InputStream sbs = new ByteArrayInputStream(fileByte);
				noticeId = fileService.save(sbs, fileName, FileType.MATERIAL_NOTICE_ATTACHMENT,
						materialNoticeAttachment.getId());
				materialNoticeAttachment.setAttachment(noticeId);*/
				// 更新通知
				materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
				// 移除session的文件
				// request.getSession(false).removeAttribute(tempFileId);
				// request.getSession(false).removeAttribute("fileName_" + tempFileId);
			} else {
				newTempNoticeFileIds += materialNoticeAttachment.getId() + ",";
			}
		}
		for (MaterialNoticeAttachment materialNoticeAttachment : oldMaterialNoticeAttachmentlist) {
			if (!newTempNoticeFileIds.contains("," + materialNoticeAttachment.getId() + ",")) {// 不包含
				fileService.remove(materialNoticeAttachment.getAttachment()); // 删除文件
				materialNoticeAttachmentService.deleteMaterialNoticeAttachmentById(materialNoticeAttachment.getId());
			}
		}
		// 备注附件保存
		String materialNoteAttachments = materialVO.getMaterialNoteAttachments();
		if (StringUtil.isEmpty(materialNoteAttachments)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材备注附件为空");
		}
		List<MaterialNoteAttachment> materialNoteAttachmentList = gson.fromJson(materialNoteAttachments,
				new TypeToken<ArrayList<MaterialNoteAttachment>>() {
				}.getType());
		// 原来的备注文件
		List<MaterialNoteAttachment> oldMaterialNoteAttachmentlist = materialNoteAttachmentService
				.getMaterialNoteAttachmentByMaterialExtraId(materialExtra.getId());
		String newTempNoteFileIds = ",";
		for (MaterialNoteAttachment materialNoteAttachment : materialNoteAttachmentList) {
			if (null == materialNoteAttachment.getId()) {
				String tempFileId = materialNoteAttachment.getAttachment();
				byte[] file = (byte[]) request.getSession(false).getAttribute(tempFileId);
				String fileName = (String) request.getSession(false).getAttribute("fileName_" + tempFileId);
				materialNoteAttachment.setAttachment(String.valueOf(new Date().getTime()));
				materialNoteAttachment.setAttachmentName(fileName);
				materialNoteAttachment.setDownload(0L);
				materialNoteAttachment.setMaterialExtraId(materialExtra.getId());
				// 保存备注
				materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
				String noticeId;
				InputStream sbs = new ByteArrayInputStream(file);
				// 保存备注文件
				noticeId = fileService.save(sbs, fileName, FileType.MATERIAL_NOTICE_ATTACHMENT,
						materialNoteAttachment.getId());
				materialNoteAttachment.setAttachment(noticeId);
				// 更新备注
				materialNoteAttachmentService.updateMaterialNoteAttachment(materialNoteAttachment);
				// 移除session的文件
				// request.getSession().removeAttribute(tempFileId);
				// request.getSession().removeAttribute("fileName_" + tempFileId);
			} else {
				newTempNoteFileIds += materialNoteAttachment.getId() + ",";
			}
		}
		for (MaterialNoteAttachment materialNoteAttachment : oldMaterialNoteAttachmentlist) {
			if (!newTempNoteFileIds.contains("," + materialNoteAttachment.getId() + ",")) {// 不包含
				fileService.remove(materialNoteAttachment.getAttachment()); // 删除文件
				materialNoteAttachmentService.deleteMaterialNoteAttachmentById(materialNoteAttachment.getId());
			}
		}
		CmsContent cmsContent = cmsContentService.getCmsContentByMaterialId(materialId);
		String title = material.getMaterialName();
		if (null != cmsContent && null != cmsContent.getTitle() && !title.equals(cmsContent.getTitle())) {
			cmsContent.setTitle(title);
			cmsContent.setAuthDate(DateUtil.date2Str(DateUtil.str4Date(cmsContent.getAuthDate()),"yyyy-MM-dd HH:mm:ss"));
			cmsContentService.updateCmsContent(cmsContent);
		}

		/*以下向主任和项目编辑发送微信推送*/
        if (isUpdate) {
			String touser = "";
			Set<String> touserIdSet = new HashSet<String>();
			//主任加入企业微信推送对象集合
			touserOpenidSet.add(director.getOpenid());
			useridList.add(director.getId());
			projectEditorNamesStr = projectEditorNamesStr.substring(0, projectEditorNamesStr.lastIndexOf(",") > 0 ? projectEditorNamesStr.lastIndexOf(",") : projectEditorNamesStr.length());
			contactUserNamesStr = contactUserNamesStr.substring(0, contactUserNamesStr.lastIndexOf(",") > 0 ? contactUserNamesStr.lastIndexOf(",") : contactUserNamesStr.length());
			String msg1 = director.getRealname() + "已被选为“" + material.getMaterialName() + "”的主任。";
			String msg2 = projectEditorNamesStr + "已被选为“" + material.getMaterialName() + "”的项目编辑。";
			String msg3 = contactUserNamesStr + "已被选为“" + material.getMaterialName() + "”的联系人。";
			touserOpenidSet.remove(null);
			touser = touserOpenidSet.toString();

			if (touserOpenidSet.size() > 0) {
				wxqyUserService.sendTextMessage("0", "0", touser, "", "", "text", msg1, (short) 0,"");
				wxqyUserService.sendTextMessage("0", "0", touser, "", "", "text", msg2, (short) 0,"");
				wxqyUserService.sendTextMessage("0", "0", touser, "", "", "text", msg3, (short) 0,"");
			}
			wxSendMessageService.batchInsertWxMessage(msg1,0,useridList,"0","0","");
			wxSendMessageService.batchInsertWxMessage(msg2,0,useridList,"0","0","");
			wxSendMessageService.batchInsertWxMessage(msg3,0,useridList,"0","0","");
		}



		/**
		 * // 判断教材备注附件和教材通知附件 List<MaterialNoticeAttachment>
		 * materialNoticeAttachmentlist = new ArrayList<MaterialNoticeAttachment>(5);
		 * String materialNoticeAttachments=materialVO.getMaterialNoticeAttachments();
		 * if (!StringUtil.isEmpty(materialNoticeAttachments)) {
		 * materialNoticeAttachmentlist = gson.fromJson(materialNoticeAttachments, new
		 * TypeToken<ArrayList<MaterialNoticeAttachment>>() { }.getType()); } if (null
		 * != noticeFiles && noticeFiles.length > 0) { for (MultipartFile notice :
		 * noticeFiles) { if (null == notice || !(notice instanceof MultipartFile)) {
		 * throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		 * CheckedExceptionResult.NULL_PARAM, "教材通知附件为空"); } if
		 * (notice.getName().length() > 80) { throw new
		 * CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		 * CheckedExceptionResult.ILLEGAL_PARAM, "教材通知附件文件名过长"); } } } if ((null ==
		 * noticeFiles && materialNoticeAttachmentlist.size() == 0) ||
		 * (materialNoticeAttachmentlist.size() + noticeFiles.length == 0)) {//
		 * 已有的数量加上新增的数量 throw new
		 * CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		 * CheckedExceptionResult.NULL_PARAM, "教材通知附件为空"); }
		 * List<MaterialNoteAttachment> materialNoteAttachmentList = new
		 * ArrayList<MaterialNoteAttachment>(5); String materialNoteAttachments =
		 * materialVO.getMaterialNoteAttachments(); if
		 * (!StringUtil.isEmpty(materialNoteAttachments)) { materialNoteAttachmentList =
		 * gson.fromJson(materialNoteAttachments, new
		 * TypeToken<ArrayList<MaterialNoteAttachment>>() { }.getType()); } if (null !=
		 * noteFiles && noteFiles.length > 0) { for (MultipartFile note : noteFiles) {
		 * if (null == note || !(note instanceof MultipartFile)) { throw new
		 * CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		 * CheckedExceptionResult.NULL_PARAM, "教材备注附件为空"); } if (note.getName().length()
		 * > 80) { throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		 * CheckedExceptionResult.ILLEGAL_PARAM, "教材备注附件文件名过长"); } } } if ((null ==
		 * noteFiles && materialNoteAttachmentList.size() == 0) ||
		 * (materialNoteAttachmentList.size() + noteFiles.length == 0)) {// 已有的数量加上新增的数量
		 * throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		 * CheckedExceptionResult.NULL_PARAM, "教材备注附件为空"); } // 保存教材通知附件
		 * List<MaterialNoticeAttachment> oldMaterialNoticeAttachmentlist =
		 * materialNoticeAttachmentService
		 * .getMaterialNoticeAttachmentsByMaterialExtraId(materialExtra.getId()); String
		 * newMaterialNoticeAttachmentIds = ","; for (MaterialNoticeAttachment
		 * materialNoticeAttachment : materialNoticeAttachmentlist) { if (null ==
		 * materialNoticeAttachment.getId()) { throw new
		 * CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		 * CheckedExceptionResult.NULL_PARAM, "教材通知id为空"); }
		 * newMaterialNoticeAttachmentIds += materialNoticeAttachment.getId() + ","; }
		 * if (null != noticeFiles && noticeFiles.length > 0) { for (MultipartFile
		 * notice : noticeFiles) { MaterialNoticeAttachment materialNoticeAttachment =
		 * new MaterialNoticeAttachment();
		 * materialNoticeAttachment.setAttachment("---------");
		 * materialNoticeAttachment.setAttachmentName(notice.getOriginalFilename());
		 * materialNoticeAttachment.setDownload(0L);
		 * materialNoticeAttachment.setMaterialExtraId(materialExtra.getId()); // 保存通知
		 * materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		 * String noticeId; // 保存通知文件 noticeId = fileService.save(notice,
		 * FileType.MATERIAL_NOTICE_ATTACHMENT, materialNoticeAttachment.getId());
		 * 
		 * materialNoticeAttachment.setAttachment(noticeId); // 更新通知
		 * materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
		 * newMaterialNoticeAttachmentIds += materialNoticeAttachment.getId() + ","; } }
		 * for (MaterialNoticeAttachment materialNoticeAttachment :
		 * oldMaterialNoticeAttachmentlist) { if
		 * (!newMaterialNoticeAttachmentIds.contains("," +
		 * materialNoticeAttachment.getId() + ",")) {// 不包含
		 * fileService.remove(materialNoticeAttachment.getAttachment()); // 删除文件
		 * materialNoticeAttachmentService
		 * .deleteMaterialNoticeAttachmentById(materialNoticeAttachment.getId()); } } //
		 * 保存教材备注附件 List<MaterialNoteAttachment> oldMaterialNoteAttachmentlist =
		 * materialNoteAttachmentService
		 * .getMaterialNoteAttachmentByMaterialExtraId(materialExtra.getId()); String
		 * newMaterialNoteAttachmentIds = ","; for (MaterialNoteAttachment
		 * materialNoteAttachment : materialNoteAttachmentList) { if (null ==
		 * materialNoteAttachment.getId()) { throw new
		 * CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		 * CheckedExceptionResult.NULL_PARAM, "教材备注id为空"); }
		 * newMaterialNoteAttachmentIds += materialNoteAttachment.getId() + ","; } if
		 * (null != noteFiles && noteFiles.length > 0) { for (MultipartFile note :
		 * noteFiles) { MaterialNoteAttachment materialNoteAttachment = new
		 * MaterialNoteAttachment(); materialNoteAttachment.setAttachment("---------");
		 * materialNoteAttachment.setAttachmentName(note.getOriginalFilename());
		 * materialNoteAttachment.setDownload(0L);
		 * materialNoteAttachment.setMaterialExtraId(materialExtra.getId()); // 保存备注
		 * materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
		 * String noticeId; // 保存备注文件 noticeId = fileService.save(note,
		 * FileType.MATERIAL_NOTICE_ATTACHMENT, materialNoteAttachment.getId());
		 * materialNoteAttachment.setAttachment(noticeId); // 更新备注
		 * materialNoteAttachmentService.updateMaterialNoteAttachment(materialNoteAttachment);
		 * newMaterialNoteAttachmentIds += materialNoteAttachment.getId() + ","; } } for
		 * (MaterialNoteAttachment materialNoteAttachment :
		 * oldMaterialNoteAttachmentlist) { if
		 * (!newMaterialNoteAttachmentIds.contains("," + materialNoteAttachment.getId()
		 * + ",")) {// 不包含 fileService.remove(materialNoteAttachment.getAttachment());
		 * // 删除文件 materialNoteAttachmentService
		 * .deleteMaterialNoteAttachmentById(materialNoteAttachment.getId()); } }
		 */
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
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return materialDao.getMaterialById(id);
	}

	@Override
	public String getMaterialNameById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return materialDao.getMaterialNameById(id);
	}

	@Override
	public MaterialMainInfoVO getMaterialMainInfoById(Long materialId, String sessionId)
			throws CheckedServiceException {
		// 验证用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		// 教材权限的检查
		List<PmphRole> pmphRoles = pmphUserService.getListUserRole(pmphUser.getId());
		// 下面进行授权
		Integer projectEditorPowers = 0;
		Integer role = 0;
		// 系统管理员权限检查
		for (PmphRole pmphRole : pmphRoles) {
			if (null != pmphRole && null != pmphRole.getRoleName() && "系统管理员".equals(pmphRole.getRoleName())) {
				// 我是系统管理原
				projectEditorPowers = 255; // "11111111";
				role = 1;
			}
		}
		// 教材主任检查
		Material material = this.getMaterialById(materialId);

		if (null != material && null != material.getDirector()) {
			//2948 N198-组织机构核对完成后，在教材申报发通知的时候，选择的主任（可能是主任，某个部门的普通员工），要求选择的主任的所有归属单位的主任都有和选择主任的权限一样。
			List<PmphUser> parentDeptsDirectors =pmphUserService.getSomebodyParentDeptsPmphUserOfSomeRole(material.getDirector(),null,"主任");
			for (PmphUser PDDirector: parentDeptsDirectors) {
				if(pmphUser.getId().equals(PDDirector.getId())){
					projectEditorPowers = 255; // 我是教材的主任 的部门或上级部门的主任
					role = (0 == role.intValue() ? 2 : role);
					break;
				}
			}
			if(pmphUser.getId().equals(material.getDirector())){
				projectEditorPowers = 255; // 我是教材的主任 本人
				role = (0 == role.intValue() ? 2 : role);
			}
		}
		// 教材项目编辑检查
		List<MaterialProjectEditorVO> materialProjectEditors = materialProjectEditorService
				.listMaterialProjectEditors(materialId);
		if (null != materialProjectEditors && materialProjectEditors.size() > 0) {
			for (MaterialProjectEditorVO materialProjectEditor : materialProjectEditors) {
				if (null != materialProjectEditor && null != materialProjectEditor.getEditorId()
						&& materialProjectEditor.getEditorId().equals(pmphUser.getId())) {
					// 我是教材的项目编辑
					projectEditorPowers = (projectEditorPowers | material.getProjectPermission());
					role = (0 == role.intValue() ? 3 : role);
				}
			}
		}
		// 教材策划编辑检查
		Integer num = this.getPlanningEditorSum(materialId, pmphUser.getId());
		if (null != num && num.intValue() > 0) {
			// 我是教材的策划编辑编辑
			projectEditorPowers = (projectEditorPowers | material.getPlanPermission());
			role = (0 == role.intValue() ? 4 : role);
		}

		MaterialMainInfoVO materialMainInfoVO = new MaterialMainInfoVO(materialId, material.getMaterialName(),
				material.getIsPublished(), material.getIsAllTextbookPublished(), material.getIsForceEnd(),
				material.getIsDeleted(), StringUtil.tentToBinary(projectEditorPowers), role);
		return materialMainInfoVO;
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
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return materialDao.deleteMaterialById(id);
	}

	/**
	 * 通过主键id更新material 不为null 的字段
	 * 
	 * @param // Material
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateMaterial(Material material, String sessionId) throws CheckedServiceException {
		if (null == material.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		// if (!pmphUser.getIsAdmin()) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
		// CheckedExceptionResult.ILLEGAL_PARAM,
		// "该用户没有操作权限");
		// }
		// 教材权限的检查
		List<PmphRole> pmphRoles = pmphUserService.getListUserRole(pmphUser.getId());
		Integer power = null;
		// 系统管理员权限检查
		for (PmphRole pmphRole : pmphRoles) {
			if (null != pmphRole && null != pmphRole.getRoleName() && "系统管理员".equals(pmphRole.getRoleName())) {
				power = 1; // 我是系统管理原
			}
		}
		// 教材主任检查
		Material materialDirector = materialService.getMaterialById(material.getId());
		if (null == power) {
			if (null != materialDirector && null != materialDirector.getDirector()
					&& pmphUser.getId().equals(materialDirector.getDirector())) {
				power = 2; // 我是教材的主任
			}
		}
		return materialDao.updateMaterial(material);
	}

	@Override
	public PageResult<MaterialListVO> listMaterials(PageParameter<MaterialListVO> pageParameter, String sessionId)
			throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (ObjectUtil.isNull(pageParameter)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"参数为空");
		}
		PageResult<MaterialListVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		List<MaterialListVO> list = new ArrayList<>();
		if (pmphUser.getIsAdmin()) {
			pageParameter.getParameter().setIsMy(null);
		}
		if (null != pageParameter.getParameter().getIsMy() && !pageParameter.getParameter().getIsMy()) {
			pageParameter.getParameter().setIsMy(null);
		}
		if (!ObjectUtil.isNull(pageParameter.getParameter().getIsMy()) && pageParameter.getParameter().getIsMy()) {
			pageParameter.getParameter().setUserId(pmphUser.getId());
		}
		Integer total = 0;
		String state = pageParameter.getParameter().getState();
		if ("遴选结束".equals(state)) {
			pageParameter.getParameter().setIsAllTextbookPublished(true);
			pageParameter.getParameter().setIsForceEnd(true);
			pageParameter.getParameter().setIsPublished(true);
			total = materialDao.listMaterialEndTotal(pageParameter);
			if (total > 0) {
				list = materialDao.listMaterialEnd(pageParameter);
			}
		} else if ("报名结束".equals(state)) {
			pageParameter.getParameter().setIsAllTextbookPublished(false);
			pageParameter.getParameter().setIsForceEnd(false);
			pageParameter.getParameter().setIsPublished(true);
			total = materialDao.listMaterialSignUpEndTotal(pageParameter);
			if (total > 0) {
				list = materialDao.listMaterialSignUpEnd(pageParameter);
			}
		}else if ("已结束".equals(state)) {
			total = materialDao.listMaterialOrEndSignUpEndTotal(pageParameter);
			if (total > 0) {
				list = materialDao.listMaterialOrEndSignUpEnd(pageParameter);
			}
		}else {
			if (!StringUtil.isEmpty(state)) {
				switch (state) {
				case "未发布":
					pageParameter.getParameter().setIsAllTextbookPublished(false);
					pageParameter.getParameter().setIsForceEnd(false);
					pageParameter.getParameter().setIsPublished(false);
					break;
				case "已发布":
					pageParameter.getParameter().setIsAllTextbookPublished(false);
					pageParameter.getParameter().setIsForceEnd(false);
					pageParameter.getParameter().setIsPublished(true);
					break;
				case "未结束":
					pageParameter.getParameter().setIsAllTextbookPublished(false);//是否所有书籍已公布
					pageParameter.getParameter().setIsForceEnd(false);//是否被强制结束
					pageParameter.getParameter().setIsPublished(null);//是否已发布到前台
					break;

				default:
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.ILLEGAL_PARAM, "教材状态错误");
				}
			}
			total = materialDao.listMaterialTotal(pageParameter);
			if (total > 0) {
				list = materialDao.listMaterial(pageParameter);
			}
		}
		list = addMaterialContact(list, sessionId);

		//2948 N198-组织机构核对完成后，在教材申报发通知的时候，选择的主任（可能是主任，某个部门的普通员工），要求选择的主任的所有归属单位的主任都有和选择主任的权限一样。
		for (MaterialListVO m: list) {
			if (null != m && null != m.getDirector()) {
				List<PmphUser> parentDeptsDirectors = pmphUserService.getSomebodyParentDeptsPmphUserOfSomeRole(m.getDirector(), null, "主任");
				for (PmphUser PDDirector : parentDeptsDirectors) {
					if (pmphUser.getId().equals(PDDirector.getId())) {
						m.setIsMy(true);// 我是教材的主任 的部门或上级部门的主任
						break;
					}
				}
			}
		}

		pageResult.setRows(list);
		pageResult.setTotal(total);
		return pageResult;
	}

	/**
	 * 
	 * 
	 * 功能描述：想每个教材中加入联系人信息
	 * 
	 * @param list
	 *            教材集合
	 * @return
	 * 
	 */
	public List<MaterialListVO> addMaterialContact(List<MaterialListVO> list, String sessionId) {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		for (MaterialListVO materialListVO : list) {
			materialListVO.setFounderName(pmphUserService.get(materialListVO.getFounderId()).getRealname());
			materialListVO.setIsMy(false);
			materialListVO.setContacts(materialContactService.listMaterialContactByMaterialId(materialListVO.getId()));// 获取联系人
			if (!materialListVO.getIsPublished()) {
				if (textbookService.getTextbookByMaterialId(materialListVO.getId()).size() == 0) {// 判断新建教材时是否到了书目录页面
					materialListVO.setMaterialStep("设置书目录");
				}
				if (ObjectUtil.isNull(cmsContentService.getCmsContentByMaterialId(materialListVO.getId()))) {// 判断新建教材时是否到了编辑通知详情页面
					materialListVO.setMaterialStep("编辑通知详情");
				}
			}
			if (pmphUser.getIsAdmin()) {// 如果是超级管理员则为所欲为
				materialListVO.setIsMy(true);
			}
			if (pmphUser.getId().equals(materialListVO.getFounderId())) {// 判断是否是创建者
				materialListVO.setIsMy(true);
				materialListVO.setIsFounder(true);
			} else {
				materialListVO.setIsFounder(false);
			}
			if (pmphUser.getId().equals(materialListVO.getDirector())) {// 判断是否是主任
				materialListVO.setIsMy(true);
				materialListVO.setIsDirector(true);
			} else {
				materialListVO.setIsDirector(false);
			}
			if (!ObjectUtil.isNull(materialProjectEditorService
					.getMaterialProjectEditorByMaterialIdAndUserId(materialListVO.getId(), pmphUser.getId()))) {// 判断是否为项目编辑
				materialListVO.setIsMy(true);
			}
			if (!CollectionUtil.isEmpty(
					textbookService.getTextbookByMaterialIdAndUserId(materialListVO.getId(), pmphUser.getId()))) {// 判断是否为策划编辑
				materialListVO.setIsMy(true);
			}
			SimpleDateFormat sdfDay  = new SimpleDateFormat("yyyy-MM-dd");
			Long time1 = DateUtil.fomatDate(DateUtil.getDay()).getTime();
			Long time2 = DateUtil.fomatDate(sdfDay.format( materialListVO.getActualDeadline())).getTime();
			if (materialListVO.getIsPublished()) {
				if (materialListVO.getIsForceEnd() || materialListVO.getIsAllTextbookPublished()) {
					materialListVO.setState("遴选结束");
				} else if( time1> time2){
					materialListVO.setState("报名结束");
				}else{
					materialListVO.setState("已发布");
				}
			} else{

					materialListVO.setState("未发布");

			}
			String myPower = getMaterialMainInfoById(materialListVO.getId(), sessionId).getMyPower();
			if (materialListVO.getIsFounder()) {
				myPower = "1" + myPower.substring(1);
			}
			materialListVO.setMyPower(myPower);
		}
		return list;
	}

	@Override
	public List<Material> getListMaterial(String materialName) {
		return materialDao.getListMaterial(materialName);
	}

	@Override
	public Integer getPlanningEditorSum(Long materialId, Long pmphUserId) {
		if (null == materialId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材为空");
		}
		if (null == pmphUserId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		return materialDao.getPlanningEditorSum(materialId, pmphUserId);
	}

	@Override
	public String updateMaterial(Long id, String sessionId) throws CheckedServiceException {
		Material material = materialDao.getMaterialById(id);
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (pmphUser.getIsAdmin() || pmphUser.getId().equals(material.getDirector())
				|| pmphUser.getId().equals(material.getFounderId())) {
			List<Textbook> list = textbookService.getTextbookByMaterialId(id);
			for (Textbook textbook : list) {
				PmphGroup group = pmphGroupService.getPmphGroupByTextbookId(textbook.getId());
				if (ObjectUtil.notNull(group)) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.ILLEGAL_PARAM,
							"您在" + textbook.getTextbookName() + "书籍下还有未解散的小组，请先解散小组");
				}
			}
			material = new Material();
			material.setId(id);
			material.setIsDeleted(true);
			materialDao.updateMaterial(material);
			cmsContentService.updateCmsContentByMaterialId(id);
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"您没有删除教材" + material.getMaterialName() + "的权限");
		}

		return "SUCCESS";
	}

	@Override
	public MaterialVO getMaterialVO(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材主键为空");
		}
		// 教材主要信息
		Material material = materialDao.getMaterialById(id);
		// 教材主任
		PmphUser director = pmphUserService.get(material.getDirector());
		// 教材类型字符串
		MaterialType materialType = materialTypeService.getMaterialTypeById(material.getMaterialType());
		String mtype = "[]";
		if (null != materialType) {
			mtype = "[" + materialType.getPath().replace("-", ",") + "," + material.getMaterialType() + "]";
			mtype = mtype.replace("[0,", "[").replace("[0", "["); // 去掉 0
		}
		// 教材通知备注表
		MaterialExtra materialExtra = materialExtraService.getMaterialExtraByMaterialId(id);
		Gson gson = new Gson();
		// 联系人
		List<MaterialContact> materialContactList = materialContactService.listMaterialContactByMaterialId(id);
		String materialContacts = gson.toJson(materialContactList);
		// 扩展项
		List<MaterialExtension> materialExtensionList = materialExtensionService.getMaterialExtensionByMaterialId(id);
		String materialExtensions = gson.toJson(materialExtensionList);
		// 项目编辑
		List<MaterialProjectEditorVO> materialProjectEditorVOList = materialProjectEditorService
				.listMaterialProjectEditors(id);
		String materialProjectEditorVOs = gson.toJson(materialProjectEditorVOList);
		// 通知附件信息
		List<MaterialNoticeAttachment> materialNoticeAttachmentList = new ArrayList<MaterialNoticeAttachment>(5);
		if (null != materialExtra) {
			materialNoticeAttachmentList = materialNoticeAttachmentService
					.getMaterialNoticeAttachmentsByMaterialExtraId(materialExtra.getId());
		}
		String materialNoticeAttachments = gson.toJson(materialNoticeAttachmentList);
		// 通知备注附件信息
		List<MaterialNoteAttachment> materialNoteAttachmentList = new ArrayList<MaterialNoteAttachment>(5);
		if (null != materialExtra) {
			materialNoteAttachmentList = materialNoteAttachmentService
					.getMaterialNoteAttachmentByMaterialExtraId(materialExtra.getId());
		}
		String materialNoteAttachments = gson.toJson(materialNoteAttachmentList);

		MaterialVO result = new MaterialVO(material, director == null ? null : director.getRealname(), mtype, materialExtra,
				materialContacts, materialExtensions, materialProjectEditorVOs, materialNoticeAttachments,
				materialNoteAttachments, StringUtil.tentToBinary(material.getPlanPermission()),
				StringUtil.tentToBinary(material.getProjectPermission()));
		result.setDirector(material==null?0:material.getDirector());
		return result;
	}

	@Override
	public List<String> upTempFile(HttpServletRequest request, MultipartFile[] files)
			throws CheckedServiceException, IOException {
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用户没有登录!");
		}
		if (null == files || files.length == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"没有文件");
		}
		for (MultipartFile file : files) {
			if (file.getOriginalFilename().length() > 80) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
						CheckedExceptionResult.ILLEGAL_PARAM, "附件名称超出80个字符长度，请修改后上传！");
			}
		}
		List<String> filsRelativePaths = new ArrayList<String>(files.length);
		for (MultipartFile file : files) {
			UUID uuid = UUID.randomUUID();
			String tempFileId = uuid.toString();
			request.getSession(false).setAttribute("fileName_" + tempFileId, file.getOriginalFilename());
			byte[] fileByte = file.getBytes();
			request.getSession(false).setAttribute(tempFileId, fileByte);
			filsRelativePaths.add(tempFileId);
		}
		return filsRelativePaths;
	}

	@Override
	public Material getMaterialByName(Long[] textbookIds) {
		if (null == textbookIds) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"书籍id为空");
		}
		return materialDao.getMaterialByName(textbookIds);
	}

	@Override
	public List<Material> listBook(String materialName) throws CheckedServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Material> listPublishedMaterial() throws CheckedServiceException {
		return materialDao.listPublishedMaterial();
	}
}
