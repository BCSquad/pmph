package com.bc.pmpheep.back.controller.material;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.vo.MaterialListVO;
import com.bc.pmpheep.back.vo.MaterialVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author MrYang
 * @CreateDate 2017年11月14日 下午2:05:32
 *
 **/
@Controller
@RequestMapping(value = "/material")
@SuppressWarnings("all")
public class MaterialController {

	@Autowired
	private MaterialService materialService;

	private final String Business_Type = "教材";

	/**
	 * 新建遴选公告
	 * 
	 * @param sessionId
	 * @param material
	 *            教材对象
	 * @param materialContacts
	 *            多个教材联系人
	 * @param materialExtensions
	 *            多个教材扩展项
	 * @param MaterialProjectEditors
	 *            多个教材扩项目编辑
	 * @param materialExtra
	 *            教材通知备
	 * @param materialNoticeAttachments
	 *            多个教材通知
	 * @param noticeFiles
	 *            通知文件
	 * @param materialNoteAttachments
	 *            多个教材备注
	 * @param noteFiles
	 *            备注文件
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "新建遴选公告")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseBean add(MaterialVO materialVO, HttpServletRequest request, MultipartFile[] noticeFiles,
			MultipartFile[] noteFiles,String deadline,String actualDeadline,String ageDeadline) {

		String sessionId = CookiesUtil.getSessionId(request);
		try {
			materialVO.getMaterial().setDeadline(DateUtil.str3Date(deadline));
			materialVO.getMaterial().setActualDeadline(DateUtil.str3Date(actualDeadline));
			materialVO.getMaterial().setAgeDeadline(DateUtil.str3Date(ageDeadline));
			return new ResponseBean(materialService.addOrUpdateMaterial(sessionId, materialVO.getMaterialContacts(),
					materialVO.getMaterialExtensions(), materialVO.getMaterialProjectEditors(),
					materialVO.getMaterial(), materialVO.getMaterialExtra(), noticeFiles,
					materialVO.getMaterialNoticeAttachments(), noteFiles, materialVO.getMaterialNoteAttachments(),
					true));
		} catch (CheckedServiceException e) {
			return new ResponseBean(e);
		} catch (IOException e) {
			return new ResponseBean("上传文件失败");
		}
	}

	/**
	 * 更新遴选公告
	 * 
	 * @param sessionId
	 * @param material
	 *            教材对象
	 * @param materialContacts
	 *            多个教材联系人
	 * @param materialExtensions
	 *            多个教材扩展项
	 * @param MaterialProjectEditors
	 *            多个教材扩项目编辑
	 * @param materialExtra
	 *            教材通知备
	 * @param materialNoticeAttachments
	 *            多个教材通知
	 * @param noticeFiles
	 *            通知文件
	 * @param materialNoteAttachments
	 *            多个教材备注
	 * @param noteFiles
	 *            备注文件
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "修改遴选公告")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseBean update(HttpServletRequest request, MaterialVO materialVO, MultipartFile[] noticeFiles,
			MultipartFile[] noteFiles,String deadline,String actualDeadline,String ageDeadline) {
		String sessionId = CookiesUtil.getSessionId(request);
		try {
			materialVO.getMaterial().setDeadline(DateUtil.str3Date(deadline));
			materialVO.getMaterial().setActualDeadline(DateUtil.str3Date(actualDeadline));
			materialVO.getMaterial().setAgeDeadline(DateUtil.str3Date(ageDeadline));
			return new ResponseBean(materialService.addOrUpdateMaterial(sessionId, materialVO.getMaterialContacts(),
					materialVO.getMaterialExtensions(), materialVO.getMaterialProjectEditors(),
					materialVO.getMaterial(), materialVO.getMaterialExtra(), noticeFiles,
					materialVO.getMaterialNoticeAttachments(), noteFiles, materialVO.getMaterialNoteAttachments(),
					true));
		} catch (CheckedServiceException e) {
			return new ResponseBean(e);
		} catch (IOException e) {
			return new ResponseBean("上传文件失败");
		}
	}

	/**
	 * 
	 * 
	 * 功能描述：初始化/条件查询教材列表
	 *
	 * @param request
	 * @param pageSize
	 *            当前页条数
	 * @param pageNumber
	 *            当前页数
	 * @param isMy
	 *            是否我的
	 * @param state
	 *            当前状态
	 * @param materialName
	 *            教材名称
	 * @param contactUserName
	 *            联系人名称
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "查询教材公告列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(HttpServletRequest request, Integer pageSize, Integer pageNumber, Boolean isMy,
			String state, String materialName, String contactUserName) {
		String sessionId = CookiesUtil.getSessionId(request);
		PageParameter<MaterialListVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		MaterialListVO materialListVO = new MaterialListVO();
		materialListVO.setIsMy(isMy);
		materialListVO.setState(state);
		materialListVO.setContactUserName(contactUserName);
		materialListVO.setMaterialName(materialName);
		pageParameter.setParameter(materialListVO);
		return new ResponseBean(materialService.listMaterials(pageParameter, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：逻辑删除教材
	 *
	 * @param request
	 * @param id
	 *            教材id
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "逻辑删除教材")
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	public ResponseBean delete(HttpServletRequest request, Long id) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(materialService.updateMaterial(id, sessionId));
	}

	/**
	 * 根据教材id获取更新教材视图数据
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年11月23日 下午5:54:04
	 * @param id
	 *            教材id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = Business_Type, logRemark = "获取教材视图数据")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseBean get(Long id) {
		return new ResponseBean(materialService.getMaterialVO(id));
	}
}
