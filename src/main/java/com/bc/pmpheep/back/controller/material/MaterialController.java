package com.bc.pmpheep.back.controller.material;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.bc.pmpheep.annotation.LogDetail;
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
import com.bc.pmpheep.back.vo.MaterialVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *@author MrYang 
 *@CreateDate 2017年11月14日 下午2:05:32
 *
 **/
@Controller
@RequestMapping(value = "/material")
@SuppressWarnings("all")
public class MaterialController {
	
	@Autowired
	private MaterialService materialService;
	
	private final String Business_Type="教材";
	
	/**
	 * 新建遴选公告
	 * @param sessionId 
	 * @param material                  教材对象
	 * @param materialContacts          多个教材联系人
	 * @param materialExtensions        多个教材扩展项
	 * @param MaterialProjectEditors    多个教材扩项目编辑
	 * @param materialExtra             教材通知备
	 * @param materialNoticeAttachments 多个教材通知
	 * @param noticeFiles               通知文件
	 * @param materialNoteAttachments   多个教材备注
	 * @param noteFiles                 备注文件
	 * @return
	 */
	@LogDetail(businessType = Business_Type, logRemark = "新建遴选公告")
	@RequestMapping(value = "/add/material", method = RequestMethod.POST)
	public ResponseBean material(	MaterialVO materialVO,
									@RequestParam(name = "sessionId")   String sessionId,
								    MultipartFile[]   noticeFiles,
								    MultipartFile[]   noteFiles
								   	){
		return new ResponseBean(materialService.addOrUpdateMaterial(
				sessionId, 
				materialVO.getMaterialContacts() , 
				materialVO.getMaterialExtensions(),
				materialVO.getMaterialProjectEditors() ,
				materialVO.getMaterial(), 
				materialVO.getMaterialExtra(),
				noticeFiles,
				materialVO.getMaterialNoticeAttachments(),
				noteFiles,
				materialVO.getMaterialNoteAttachments(),
				true));
	}
	
	/**
	 * 更新遴选公告
	 * @param sessionId 
	 * @param material                  教材对象
	 * @param materialContacts          多个教材联系人
	 * @param materialExtensions        多个教材扩展项
	 * @param MaterialProjectEditors    多个教材扩项目编辑
	 * @param materialExtra             教材通知备
	 * @param materialNoticeAttachments 多个教材通知
	 * @param noticeFiles               通知文件
	 * @param materialNoteAttachments   多个教材备注
	 * @param noteFiles                 备注文件
	 * @return
	 */
	@LogDetail(businessType = Business_Type, logRemark = "修改遴选公告")
	@RequestMapping(value = "/update/material", method = RequestMethod.PUT)
	public ResponseBean  material(@RequestParam(name = "sessionId")   String sessionId,
										MaterialVO materialVO,
									    MultipartFile[]   noticeFiles,
									    MultipartFile[]   noteFiles
									   	){
		return new ResponseBean(materialService.addOrUpdateMaterial(
				sessionId, 
				materialVO.getMaterialContacts() , 
				materialVO.getMaterialExtensions(),
				materialVO.getMaterialProjectEditors() ,
				materialVO.getMaterial(), 
				materialVO.getMaterialExtra(),
				noticeFiles,
				materialVO.getMaterialNoticeAttachments(),
				noteFiles,
				materialVO.getMaterialNoteAttachments(),
				true));
	}

}
















