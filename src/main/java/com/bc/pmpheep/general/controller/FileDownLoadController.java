/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bc.pmpheep.back.dao.CmsContentDao;
import com.bc.pmpheep.back.dao.ExpertationDao;
import com.bc.pmpheep.back.dao.ProductDao;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.*;
import com.bc.pmpheep.back.vo.*;
import com.bc.pmpheep.general.runnable.ClicSpringThread;
import com.bc.pmpheep.utils.ClicWordHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.bo.DecPositionBO;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.sessioncontext.SessionContext;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.RandomUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.general.runnable.Front;
import com.bc.pmpheep.general.runnable.SpringThread;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.ExcelHelper;
import com.bc.pmpheep.utils.WordHelper;
import com.bc.pmpheep.utils.ZipHelper;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * 文件下载控制器
 * 
 * @author L.X <gugia@qq.com>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class FileDownLoadController {

	Logger logger = LoggerFactory.getLogger(FileDownLoadController.class);

	@Resource
	FileService fileService;
	@Resource
	PmphGroupFileService groupFileService;
	@Resource
	CmsExtraService cmsExtraService;
	@Resource
	MaterialNoticeAttachmentService materialNoticeAttachmentService;
	@Resource
	MaterialNoteAttachmentService materialNoteAttachmentService;
	@Resource
	DeclarationService declarationService;
	@Resource
	ExcelHelper excelHelper;
	@Resource
	ClicWordHelper clicWordHelper;
	@Resource
	WordHelper wordHelper;
	@Resource
	MaterialService materialService;
	@Resource
	MaterialExtensionService materialExtensionService;
	@Resource
	TextbookService textbookService;
	@Resource
	ZipHelper zipHelper;
	@Resource
	MaterialOrgService materialOrgService;

	@Autowired
	BookCorrectionService bookCorrectionService;
	@Autowired
	SurveyQuestionAnswerService surveyQuestionAnswerService;
	@Autowired
	DecPositionService decPositionService;
	@Resource(name = "taskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	OrgService orgService;
	@Autowired
	ExpertationDao expertationDao;
	@Autowired
	ExpertationService expertationService;

	// 当前业务类型
	private static final String BUSSINESS_TYPE = "文件下载";

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private WriterUserService writerUserService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductDao productDao;

	@Autowired
	CmsContentDao cmsContentDao;

	/**
	 * 普通文件下载
	 * 
	 * @param id
	 *            文件在MongoDB中的id
	 * @param response
	 *            服务响应
	 * @throws UnsupportedEncodingException
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "普通文件下载")
	@RequestMapping(value = "/file/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		GridFSDBFile file = fileService.get(id);

		if (null == file) {
			logger.warn("未找到id为'{}'的文件", id);
			return;
		}
		String fileName = returnFileName(request, file.getFilename());
		fileName = fileName.replaceAll(" ", "");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			file.writeTo(out);
			out.flush();
			out.close();
		} catch (IOException ex) {
			logger.error("文件下载时出现IO异常：{}", ex.getMessage());
		}
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：普通文件下载(更新下载数)
	 * 使用示范：
	 *
	 * &#64;param type 模块类型
	 * &#64;param id 文件在MongoDB中的id
	 * &#64;param response 服务响应
	 * </pre>
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "普通文件下载(更新下载数)")
	@RequestMapping(value = "/file/{type}/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable("type") String type, @PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		GridFSDBFile file = fileService.get(id);
		if (null == file) {
			logger.warn("未找到id为'{}'的文件", id);
			return;
		}
		String fileName = returnFileName(request, file.getFilename());
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			file.writeTo(out);
			out.flush();
			out.close();
			if (Const.CMS_TYPE.equals(type)) {// CMS附件
				cmsExtraService.updateCmsExtraDownLoadCountsByAttachment(id);
			}
			if (Const.MATERIAL_NOTICE_TYPE.equals(type)) {// 教材通知
				materialNoticeAttachmentService.updateMaterialNoticeAttachmentDownLoadCountsByAttachment(id);
			}
			if (Const.MATERIAL_NOTE_TYPE.equals(type)) {// 教材备注
				materialNoteAttachmentService.updateMaterialNoteAttachmentDownLoadCountsByAttachment(id);
			}
			if(Const.CLINICAL_DECISION.equals(type)){ //临床决断
				productService.updateProductAttachmenDownLoadCountsByAttachment(id);
			}
		} catch (IOException ex) {
			logger.error("文件下载时出现IO异常：{}", ex.getMessage());
		}
	}

	/**
	 * 小组文件下载
	 * 
	 * @param id
	 *            图片在MongoDB中的id
	 * @param groupId
	 *            小组id
	 * @param response
	 *            服务响应
	 * @return ResponseBean对象
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "小组文件下载")
	@RequestMapping(value = "/groupfile/download/{id}", method = RequestMethod.GET)
	public ResponseBean download(@PathVariable("id") String id, @RequestParam("groupId") long groupId,
			HttpServletRequest request, HttpServletResponse response) {
		if (groupId < 1) {
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "小组id错误（负数或零）");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		GridFSDBFile file = fileService.get(id);
		if (null == file) {
			logger.warn("未找到id为'{}'的文件", id);
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "未找到对应文件");
		}
		String fileName = groupFileService.getFileName(id);
				fileName =	returnFileName(request, fileName);
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			file.writeTo(out);
			out.flush();
			out.close();
			return new ResponseBean(groupFileService.updatePmphGroupFileOfDown(groupId, id));
		} catch (IOException ex) {
			logger.warn("文件下载时出现IO异常：{}", ex.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}
	/**
	 *
	 * Description:导出反馈信息
	 *

	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出反馈信息")
	@RequestMapping(value = "/bookCorrection/exportfeedback", method = RequestMethod.GET)
	public void exportfeedback( HttpServletRequest request, HttpServletResponse response,Boolean result) {
		//Boolean result = null;
		List<BookFeedBack> list = bookCorrectionService.exportfeedback(result);
		Workbook workbook = null;
		if (list.size() == 0) {
			list.add(new BookFeedBack());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, "读者反馈");

		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		String fileName = returnFileName(request,"读者反馈.xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}


	/**
	 *
	 * Description:导出个人信息
	 *
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出个人信息")
	@RequestMapping(value = "/users/writer/list/exportWriterUser", method = RequestMethod.GET)
	public void exportfeedback(HttpServletRequest request, HttpServletResponse response, String name,
							    Integer rank,  String orgName,  String handphone, String email) {
		//Boolean result = null;
		List<WriterUserManagerVO> list = writerUserService.exportWriterInfo( name,
				 rank,   orgName,   handphone,  email);
		Workbook workbook = null;
		if (list.size() == 0) {
			list.add(new WriterUserManagerVO());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, "个人用户信息");

		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		String fileName = returnFileName(request,"个人用户信息.xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}
	/**
	 *
	 * Description:导出审核管理员信息
	 *

	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出审核管理员信息")
	@RequestMapping(value = "/auth/exportOrgUser", method = RequestMethod.GET)
	public void exportfeedback(HttpServletRequest request, HttpServletResponse response, String orgName,
							     String realname,  Integer progress) {
		OrgVO orgVO = new OrgVO();
		if (StringUtil.notEmpty(orgName)) {
			orgVO.setOrgName(orgName.replaceAll(" ", ""));
		}
		if (StringUtil.notEmpty(realname)) {
			orgVO.setRealname(realname.replaceAll(" ", ""));
		}
		orgVO.setProgress(progress);
		List<OrgVO> list = orgService.exportOrgUser(orgVO);
		Workbook workbook = null;
		if (list.size() == 0) {
			list.add(new OrgVO());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, "审核管理员信息");

		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		String fileName = returnFileName(request,"审核管理员信息.xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 *
	 * Description:导出图书纠错审核信息
	 *

	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出图书纠错审核")
	@RequestMapping(value = "/bookCorrection/exportBookCheck", method = RequestMethod.GET)
	public void exportBookCheck(HttpServletRequest request, HttpServletResponse response, String bookname) {
		List<BookCorrectionAuditVO> list = bookCorrectionService.exportBookCheck(bookname);
		Workbook workbook = null;
		if (list.size() == 0) {
			list.add(new BookCorrectionAuditVO());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, "图书纠错审核信息");

		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		String fileName = returnFileName(request,"图书纠错审核信息.xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 *
	 * Description:导出图书纠错审核信息
	 *

	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "图书评论")
	@RequestMapping(value = "/bookCorrection/exportComments", method = RequestMethod.GET)
	public void exportComments(HttpServletRequest request, HttpServletResponse response, CmsContentVO cmsContentVO) {
		cmsContentVO.setCategoryId(Const.CMS_CATEGORY_ID_0);
		String title = cmsContentVO.getTitle();
		String userName = cmsContentVO.getUsername();
		if (StringUtil.notEmpty(title)) {
			cmsContentVO.setTitle(StringUtil.toAllCheck(title));
		}
		if (StringUtil.notEmpty(userName)) {
			cmsContentVO.setUsername(StringUtil.toAllCheck(userName));
		}
		PageParameter<CmsContentVO> pageParameter =
				new PageParameter<CmsContentVO>(null, null, cmsContentVO);
		String sessionId = CookiesUtil.getSessionId(request);

		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
					CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		if (Const.CMS_CATEGORY_ID_0.longValue() == pageParameter.getParameter().getCategoryId()) {
			pageParameter.getParameter().setIsAdmin(true);
		} else {
			pageParameter.getParameter().setIsAdmin(pmphUser.getIsAdmin());
		}
		pageParameter.setPageSize(100000000);
		List<CmsContentVO> list = cmsContentDao.listCmsComment(pageParameter);
		String[] authStatusName = new String[]{"待审核","未通过","已通过"};
		for(CmsContentVO cmsContentVO1:list){
			cmsContentVO1.setAuthDateS(DateUtil.formatTimeStamp("yyyy-MM-dd",cmsContentVO1.getAuthDate()));
			cmsContentVO1.setGmtCreateS(DateUtil.formatTimeStamp("yyyy-MM-dd",cmsContentVO1.getGmtCreate()));
			cmsContentVO1.setAuthStatusName(authStatusName[cmsContentVO1.getAuthStatus()]);
		}

		Workbook workbook = null;
		if (list.size() == 0) {
			list.add(new CmsContentVO());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, "图书评论");

		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		String fileName = returnFileName(request,"图书评论.xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}
	/**
	 * 导出临床决策-申报列表
	 * @param request
	 * @param response
	 * @param expertationVO 可接受两个模糊查询条件 username realname
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出临床决策-申报列表")
	@RequestMapping(value = "/expertation/exportExpertation", method = RequestMethod.GET)
	public void exportExpertation(HttpServletRequest request, HttpServletResponse response,ExpertationVO expertationVO) {

		PageParameter pageParameter = new PageParameter();
		pageParameter.setStart(null);
		pageParameter.setParameter(expertationVO);
		List<ExpertationVO> list = expertationDao.queryExpertation(pageParameter);
		//String[] stateList = new String[]{"未提交","待学校审核","被学校退回","学校已审核","待学校审核","被出版社退回"};
		ProductVO productVO = null;
		if(!CollectionUtil.isEmpty(list) ){
			productVO = productDao.queryProductById(list.get(0).getProduct_id());
		}
		for (int i= 0;i<list.size();i++) {
			ExpertationVO e = list.get(i);
			/*List<ProductType> clist = expertationDao.queryProductContentTypeListByExpertationId(e.getId());
			List<ProductType> slist = expertationDao.queryProductSubjectTypeListByExpertationId(e.getId());
			List<ProductType> plist = expertationDao.queryProductProfessionTypeListByExpertationId(e.getId());
			e.setProductSubjectTypeList(slist);
			e.setProductContentTypeList(clist);
			e.setProductProfessionTypeList1(plist);*/

			e = expertationService.getExpertationById(e.getId());

			e.setExcelTypeStr();

			e.setSchoolStauts((e.getOrg_id().intValue() != 0 && e.getOnline_progress().intValue()==1)?"待审核"
					:((e.getOrg_id().intValue() != 0 && e.getOnline_progress().intValue()==3)?"审核通过":
					(e.getOrg_id().intValue() !=0 && e.getPmphAudit().intValue()==0  && (e.getOnline_progress().intValue()  == 4||e.getOnline_progress().intValue() == 5)?
							"出版社退回":(e.getOrg_id().intValue()==0?"":""))));
			e.setPmphStauts((e.getPmphAudit().intValue()==1?"通过":
					(e.getPmphAudit().intValue()==2)?"不通过":
							((e.getOnline_progress().intValue()==2||e.getOnline_progress().intValue()==4||e.getOnline_progress().intValue()==5 )?"出版社退回"
									:((e.getPmphAudit().intValue()==0  && e.getOrg_id().intValue() !=0  && (e.getOnline_progress().intValue() == 1||e.getOnline_progress().intValue() == 3 )) || (e.getOrg_id().intValue() ==0 && e.getPmphAudit().intValue()==0))?"待审核":""
							)));
			e.setFinalResultStr(e.getFinalResult()?"已公布":"未公布");
			//e.setOnlineProgressName((e.getOrg_id()==0&&e.getOnline_progress()==1)?"待出版社审核":(e.getOrg_id()==0&&e.getOnline_progress()==3?"出版社已审核":stateList[e.getOnline_progress()]));
			list.set(i,e);
		}
		Workbook workbook = null;
		try {
			//Thread.currentThread().sleep(3000);//毫秒
			if (list.size() == 0) {
				list.add(new ExpertationVO());
			}
			//workbook = excelHelper.fromBusinessObjectList(list, expertationVO.getProduct_name()!=null?getExportName(expertationVO.getExpert_type()+""):"临床决策申报");
			workbook = excelHelper.fromExpertationList(productVO,list, expertationVO.getProduct_name()!=null?expertationVO.getProduct_name():getExportName(expertationVO.getExpert_type()+""));

		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		String fileName = returnFileName(request,(expertationVO.getProduct_name()!=null?expertationVO.getProduct_name():getExportName(expertationVO.getExpert_type()+""))+".xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	private String  getExportName(String expert_type){
		String returnName = "";
		switch (expert_type) {

			case "1":
				returnName= "人卫临床助手申报";
				break;
			case "2":
				returnName= "人卫用药助手";
				break;
			case "3":
				returnName= "人卫中医助手";
				break;

			default:
				returnName= "临床决策申报";;
		}
		return returnName;

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param ptype  产品分类 必须 （1=临床/2=用药/3=中医）
	 * @param type_name  （学科/内容）分类 名称模糊查询
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出临床决策-申报结果统计列表")
	@RequestMapping(value = "/expertation/exportExpertationCount", method = RequestMethod.GET)
	public void exportExpertationCount(HttpServletRequest request, HttpServletResponse response,
									   //int ttype,
									   @RequestParam(value = "ptype" ,required = true) int ptype,
									   @RequestParam(value = "productId" ,required = true) Long id,
									   @RequestParam(value = "type_name",required = false)String type_name) {
		Map<String,Object> paraMap = new HashMap<>();


		paraMap.put("ptype",ptype);
		paraMap.put("type_name",type_name);
		paraMap.put("id",id);
		PageParameter pageParameter = new PageParameter();
		pageParameter.setStart(null);
		pageParameter.setParameter(paraMap);

//		ProductVO product = productDao.queryProductByProductType(Long.valueOf(String.valueOf(ptype)), null);
//		if(product!=null && product.getId() != null){
//			paraMap.put("product_id",product.getId());
//		}else{
//			paraMap.put("product_id",0);
//		}

		Map<String,Object> sheetMap = new HashMap<>();
		List<ExpertationCountnessVO> list = new ArrayList<>();

		//2.内容分类
		paraMap.put("ttype",2);
		List<ExpertationCountnessVO> list2 = expertationDao.getCountListGroupByContentType(pageParameter);

		//1.学科分类
		paraMap.put("ttype",1);
		List<ExpertationCountnessVO> list1 = expertationDao.getCountListGroupBySubjectType(pageParameter);

		//3.专业分类
		paraMap.put("ttype",1);
		List<ExpertationCountnessVO> list3 = expertationDao.getCountListGroupByProfessionType(pageParameter);

		if (list1.size() == 0) {
			list1.add(new ExpertationCountnessVO());
		}
		if (list2.size() == 0) {
			list2.add(new ExpertationCountnessVO());
		}
		if (list3.size() == 0) {
			list3.add(new ExpertationCountnessVO());
		}

		sheetMap.put("学科分类",list1);
		sheetMap.put("内容分类",list2);
		sheetMap.put("申报专业",list3);

		Workbook workbook = null;
		if (list.size() == 0) {
			list.add(new ExpertationCountnessVO());
		}

		try {
			workbook = excelHelper.fromSheetMap(sheetMap);
			//workbook = excelHelper.fromBusinessObjectList(list, (ttype == 2?"内容分类":"学科分类")+"-临床决策申报");

		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		String fileName = returnFileName(request,"临床决策申报统计.xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}


	/**
	 * 
	 * <pre>
	 * 功能描述：处理不同浏览器下载文件乱码问题
	 * 使用示范：
	 *
	 * &#64;param request
	 * &#64;param fileName 文件名
	 * &#64;return 编码后的文件名
	 * </pre>
	 */
	private String returnFileName(HttpServletRequest request, String fileName) {
		String userAgent = request.getHeader("User-Agent");
		String reFileName = "";
		if (StringUtil.isEmpty(fileName)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "未找到对应文件");
		}
		try {
			// 针对IE或者以IE为内核的浏览器：
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				reFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			} else {
				// 非IE浏览器的处理：
				reFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			logger.warn("修改编码格式的时候失败");
		}
		return reFileName;
	}

	/**
	 * 
	 * 
	 * 功能描述：申报表批量导出excel
	 * 
	 * @param materialId
	 *            教材id
	 * @param textBookids
	 *            书籍id集合
	 * @param realname
	 *            条件查询的账号或者姓名
	 * @param position
	 *            条件查询 职务
	 * @param title
	 *            条件查询 职称
	 * @param orgName
	 *            条件查询 工作单位
	 * @param unitName
	 *            条件查询 申报单位
	 * @param positionType
	 *            条件查询 申报职位 ;null全部 1主编 2副主编 3编委
	 * @param onlineProgress
	 *            1待审核 3已经审核
	 * @param offlineProgress
	 *            0 未 2 收到
	 * @param request
	 * @param response
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报表批量导出excel")
	@RequestMapping(value = "/excel/declaration", method = RequestMethod.GET)
	public void declarationExcel(Long materialId, String textBookids, String realname, String position, String title,
			String orgName, String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress,@RequestParam(value = "isSelect", required = false) Boolean isSelect,
			HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = null;
		try {
			workbook = excelHelper.fromDeclarationEtcBOList(materialService.getMaterialById(materialId),
					materialExtensionService.getMaterialExtensionByMaterialId(materialId),
					declarationService.declarationEtcBO(materialId, textBookids, realname, position, title, orgName,
							unitName, positionType, onlineProgress, offlineProgress,isSelect),
					"专家信息表");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		// try {
		StringBuilder sb = new StringBuilder("attachment;fileName=");
		String materialName;
		String userAgent = request.getHeader("User-Agent");
		materialName = returnFileName(request, materialService.getMaterialNameById(materialId));
		// if (userAgent.toLowerCase().contains("mozilla")) {
		// materialName =
		// URLEncoder.encode(materialService.getMaterialNameById(materialId), "UTF-8");
		// } else {
		// materialName =
		// new String(materialService.getMaterialNameById(materialId).getBytes("utf-8"),
		// "ISO8859-1");
		// }
		sb.append(materialName);
		sb.append(".");
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd.HHmm");
		sb.append(sdf.format(new Date()));
		sb.append(".xls");
		response.setHeader("Content-Disposition", sb.toString().replace("+", "%20"));
		// } catch (UnsupportedEncodingException e) {
		// logger.warn("修改编码格式的时候失败");
		// }
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 
	 * 
	 * 功能描述：申报表批量导出word
	 * 
	 * @param materialId
	 *            教材id
	 * @param textBookids
	 *            书籍id集合
	 * @param realname
	 *            条件查询的账号或者姓名
	 * @param position
	 *            条件查询 职务
	 * @param title
	 *            条件查询 职称
	 * @param orgName
	 *            条件查询 工作单位
	 * @param unitName
	 *            条件查询 申报单位
	 * @param positionType
	 *            条件查询 申报职位 ;null全部 1主编 2副主编 3编委
	 * @param onlineProgress
	 *            1待审核 3已经审核
	 * @param offlineProgress
	 *            0 未 2 收到

	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报表批量导出word")
	@RequestMapping(value = "/word/declaration", method = RequestMethod.GET)
	public String declarationWord(Long materialId, String textBookids, String realname, String position, String title,
			String orgName, String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress,@RequestParam(value = "isSelect", required = false) Boolean isSelect) {
		String id = String.valueOf(System.currentTimeMillis()).concat(String.valueOf(RandomUtil.getRandomNum()));
		taskExecutor.execute(new SpringThread(zipHelper, wordHelper, materialService, textbookService,
				declarationService,isSelect, materialId, textBookids, realname, position, title, orgName, unitName, positionType,
				onlineProgress, offlineProgress, id, materialExtensionService));
		return '"' + id + '"';
	}

	/**
	 *
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "临床申报表批量导出word")
	@RequestMapping(value = "/word/clic/declaration", method = RequestMethod.GET)
	public String clicDeclarationWord(ExpertationVO expertationVO) {
		String id = String.valueOf(System.currentTimeMillis()).concat(String.valueOf(RandomUtil.getRandomNum()));

		//List<ExpertationVO> list = expertationDao.queryExpertation(pageParameter);
		taskExecutor.execute(new ClicSpringThread(zipHelper, clicWordHelper,
				expertationDao,expertationService, productService,expertationVO,id));
		return '"' + id + '"';
	}

	/**
	 * 
	 * 
	 * 功能描述：申报表批量导出word
	 * 
	 * 

	 *            申报表ids

	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报表批量导出word")
	@RequestMapping(value = "/front/word/declaration", method = RequestMethod.GET)
	public String declarationWord(String selectedIds) {
		List<Long> decIds = new ArrayList<>();
		String[] decId = selectedIds.split(",");
		for (int i = 0; i < decId.length; i++) {
			decIds.add(Long.valueOf(decId[i]));
		}
		String id = String.valueOf(System.currentTimeMillis()).concat(String.valueOf(RandomUtil.getRandomNum()));
		taskExecutor.execute(new Front(logger, zipHelper, wordHelper, materialService, textbookService,
				declarationService, materialExtensionService, decIds, id));
		return '"' + id + '"';
	}

	/**
	 * 
	 * 
	 * 功能描述：查询进度
	 * 
	 * @param id
	 * @return
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询word打包进度")
	@RequestMapping(value = "/word/progress", method = RequestMethod.GET)
	public ZipDownload progress(String id) {
		ZipDownload zipDownload = new ZipDownload();
		if (Const.WORD_EXPORT_MAP.containsKey(id)) {
			zipDownload.setId(Const.WORD_EXPORT_MAP.get(id).getId());
			zipDownload.setState(Const.WORD_EXPORT_MAP.get(id).getState());
			zipDownload.setDetail(Const.WORD_EXPORT_MAP.get(id).getDetail());
			zipDownload.setCreateTime(Const.WORD_EXPORT_MAP.get(id).getCreateTime());
		}
		return zipDownload;
	}

	/**
	 * word打包文件
	 * 
	 * @param id
	 *            生成的唯一标识符
	 * @param response
	 *            服务响应
	 * @throws UnsupportedEncodingException
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "word打包文件")
	@RequestMapping(value = "/zip/download", method = RequestMethod.GET)
	public void downloadZip(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
		String src = this.getClass().getResource("/").getPath();
		src = src.substring(1);
		if (!src.endsWith(File.separator)) {
			src += File.separator;
		}
		String materialName = Const.WORD_EXPORT_MAP.get(id).getMaterialName();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String filePath = src + id + File.separator + materialName + ".zip";
		String fileName = returnFileName(request, materialName + ".zip");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			int byteRead = 0;
			byte[] buffer = new byte[1024];
			while ((byteRead = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, byteRead);
			}
			bos.flush();
			fis.close();
			bis.close();
			fos.close();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		} finally {
			Const.WORD_EXPORT_MAP.remove(id);
			ZipDownload.DeleteFolder(src + id);
		}
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：导出已发布教材下的学校
	 * 使用示范：
	 *
	 * &#64;param materialId 教材ID
	 * &#64;param request
	 * &#64;param response
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出已发布教材下的学校")
	@RequestMapping(value = "/excel/published/org", method = RequestMethod.GET)
	public void org(@RequestParam("materialId") Long materialId, HttpServletRequest request,
			HttpServletResponse response) {
		Workbook workbook = null;
		List<OrgExclVO> orgList = null;
		try {
			orgList = materialOrgService.getOutPutExclOrgByMaterialId(materialId);
			if (orgList.isEmpty()) {
				orgList.add(new OrgExclVO());
			}
			workbook = excelHelper.fromBusinessObjectList(orgList, "学校信息");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String materialName = null;
		if (CollectionUtil.isNotEmpty(orgList)) {
			materialName = orgList.get(0).getMaterialName();// 教材名称
		}
		if (StringUtil.isEmpty(materialName)) {
			materialName = "已发布学校";
		}
		String fileName = returnFileName(request, materialName + ".xls");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}


	/**
	 *
	 * <pre>
	 * 功能描述：导出已发布教材下的学校
	 * 使用示范：
	 *
	 * &#64;param materialId 教材ID
	 * &#64;param request
	 * &#64;param response
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出已发布临床下的学校")
	@RequestMapping(value = "/clinical/excel/published/org", method = RequestMethod.GET)
	public void clinicalOrg(@RequestParam("productId") Long productId, HttpServletRequest request,
					HttpServletResponse response) {
		Workbook workbook = null;
		List<OrgExclVO> orgList = null;
		try {
			orgList = productService.getOutPutExclOrgByProduct(productId);
			if (orgList.isEmpty()) {
				orgList.add(new OrgExclVO());
			}
			workbook = excelHelper.fromBusinessObjectList(orgList, "学校信息");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String materialName = null;
		if (CollectionUtil.isNotEmpty(orgList)) {
			materialName = (StringUtil.isEmpty(orgList.get(0).getMaterialName())?"":orgList.get(0).getMaterialName())+"已发布学校";// 教材名称
		}
		if (StringUtil.isEmpty(materialName)) {
			materialName = "已发布学校";
		}
		String fileName = returnFileName(request, materialName + ".xls");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 机构用户export
	 */
	@RequestMapping(value = "/orgUserExportEcel", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出excel")
	@ResponseBody
	public void orgUserExportEcel(HttpServletRequest request, HttpServletResponse response, String name, String orgName,
			String orgTypeName, Integer isHospital) {
		PageParameter pageParameter = new PageParameter<>();
		OrgAndOrgUserVO orgAndOrgUserVO = new OrgAndOrgUserVO();
		if (StringUtil.notEmpty(orgName)) {
			orgAndOrgUserVO.setOrgName(orgName.replaceAll(" ", ""));
		}
		if (StringUtil.notEmpty(name)) {
			orgAndOrgUserVO.setName(name.replaceAll(" ", ""));// 去除空格
		}
		if (StringUtil.notEmpty(orgTypeName)) {
			orgAndOrgUserVO.setOrgTypeName(orgTypeName.replaceAll(" ", ""));// 去除空格
		}
		orgAndOrgUserVO.setIsHospital(isHospital);
		pageParameter.setParameter(orgAndOrgUserVO);
		pageParameter.setStart(null);
		// export
		Workbook workbook = null;
		List<OrgAndOrgUserVO> orgList = null;
		try {
			orgList = orgUserService.getListOrgUser(pageParameter).getRows();
			workbook = excelHelper.fromBusinessObjectList(orgList, "机构账户信息");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String fileName = returnFileName(request, "机构账户信息.xls");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}

	}

	/**
	 * 导出书籍遴选名单/批量导出
	 * 
	 * @param request
	 * @param response
	 * @param textbookIds
	 * @throws CheckedServiceException
	 * @throws Exception
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出书籍遴选名单/批量导出")
	@RequestMapping(value = "/chosenPosition/exportExcel", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("textbookIds") Long[] textbookIds) throws CheckedServiceException, Exception {
		Workbook workbook = null;
		List<ExcelDecAndTextbookVO> list = null;
		try {
			list = textbookService.getExcelDecAndTextbooks(textbookIds);
			if (list.size() == 0) {
				// 设置表头 ，放置初始化表出错
				list.add(new ExcelDecAndTextbookVO());
			}
			workbook = excelHelper.fromBusinessObjectList(list, "遴选名单");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String fileName = null;
		if (textbookIds.length > 1) {// 当批量导出的时候 文件名为教材名称
			String materialName = list.get(0).getMaterialName();// 书籍名称
			if (null == materialName) {
				materialName = "遴选名单导出";
			}
			fileName = returnFileName(request, materialName + ".xls");
		} else {// 当单个导出的时候 文件名为书籍名称
			String TextbookName = list.get(0).getTextbookName();// 书籍名称
			if (null == TextbookName) {
				TextbookName = "遴选名单导出";
			}
			fileName = returnFileName(request, TextbookName + ".xls");
		}
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 导出纠错信息
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月20日 下午5:01:53
	 * @param request
	 * @param response
	 * @param bookname
	 * @param isEditorReplied
	 * @throws CheckedServiceException
	 * @throws Exception
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出纠错信息")
	@RequestMapping(value = "/bookCorrectionTrack/exportExcel", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "bookname", required = false) String bookname,
			@RequestParam(value = "isEditorReplied", required = false) Boolean isEditorReplied)
			throws CheckedServiceException, Exception {
		Workbook workbook = null;
		List<BookCorrectionTrackVO> list = null;
		try {
			list = bookCorrectionService.listBookCorrectionTrack(request, null, null, bookname, isEditorReplied, null)
					.getRows();
			if (list.size() == 0) {
				// 设置表头 ，放置初始化表出错
				list.add(new BookCorrectionTrackVO());
			}
			workbook = excelHelper.fromBusinessObjectList(list, "sheet1");
		} catch (CheckedServiceException | IllegalArgumentException e) {
			logger.warn("数据表格化的时候失败");
		}
		String fileName = returnFileName(request, "纠错跟踪" + DateUtil.getTime() + ".xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 角色遴选 批量导出主编、副主编
	 * 
	 * @param textbookIds
	 * @param request
	 * @param response
	 * @throws IllegalAccessException
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "角色遴选 批量导出主编、副主编")
	@RequestMapping(value = "/position/exportEditors", method = RequestMethod.GET)
	public void exportEditors(Long[] textbookIds, HttpServletRequest request, HttpServletResponse response)
			throws IllegalAccessException, Exception {
		List<DecPositionBO> list;
		Workbook workbook = null;
		try {
			list = textbookService.getExcelDecByMaterialId(textbookIds);
			workbook = excelHelper.fromDecPositionBOList(list, "主编-副主编");
		} catch (CheckedServiceException | IllegalArgumentException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_CREATION_FAILED, "数据表格化的时候失败");
		}
		// 通过书籍id获取教材信息
		Material material = materialService.getMaterialByName(textbookIds);
		String fileName = returnFileName(request, material.getMaterialName() + ".xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：导出填空题调查结果Excel 使用示范：
	 *
	 * @user tyc
	 * @param request
	 * @param response
	 *            2018.01.08 18:31
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出填空题调查结果")
	@RequestMapping(value = "/excel/surveyQuestionExcel", method = RequestMethod.GET)
	public void surveyQuestionExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("surveyId") Long surveyId, @RequestParam("questionId") Long questionId) {
		Workbook workbook = null;
		List<SurveyQuestionFillVO> surveyQuestionFillVO = null;
		try {
			PageParameter<SurveyQuestionFillVO> pageParameter = new PageParameter<>(1, 50000);
			pageParameter.setParameter(new SurveyQuestionFillVO(surveyId, questionId));
			surveyQuestionFillVO = surveyQuestionAnswerService.listFillQuestion(pageParameter).getRows();
			if (CollectionUtil.isEmpty(surveyQuestionFillVO)) {
				surveyQuestionFillVO.add(new SurveyQuestionFillVO());
			}
			workbook = excelHelper.fromBusinessObjectList(surveyQuestionFillVO, "填空题调查结果");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String name = "填空题调查结果";
		String fileName = returnFileName(request, name + ".xls");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 
	 * Description:申报情况统计页面按申报单位统计导出统计结果
	 * 
	 * @author:lyc
	 * @date:2018年1月9日上午10:29:21
	 * @param
	 * @return void
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报情况统计页面按申报单位统计导出统计结果")
	@RequestMapping(value = "/result/exportSituationSchool", method = RequestMethod.GET)
	public void exportSituationSchool(Long materialId, String schoolName, Integer state, HttpServletRequest request,
			HttpServletResponse response) {
		PageParameter<DeclarationSituationSchoolResultVO> pageParameter = new PageParameter<>(1, 50000);
		DeclarationSituationSchoolResultVO declarationSituationSchoolResultVO = new DeclarationSituationSchoolResultVO();
		declarationSituationSchoolResultVO.setMaterialId(materialId);
		declarationSituationSchoolResultVO.setSchoolName(schoolName);
		pageParameter.setParameter(declarationSituationSchoolResultVO);
		Workbook workbook = null;
		List<DeclarationSituationSchoolResultVO> list = null;
		String sheetName = "";
		if (state.intValue() == 1) {
			list = decPositionService.listChosenDeclarationSituationSchoolResultVOs(pageParameter).getRows();
			sheetName = "申报情况按单位统计（按当选数排序）";
		} else if (state.intValue() == 2) {
			list = decPositionService.listPresetDeclarationSituationSchoolResultVOs(pageParameter).getRows();
			sheetName = "申报情况按单位统计（按申报数排序）";
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"未知的排序方式");
		}
		if (list.size() == 0) {
			list.add(new DeclarationSituationSchoolResultVO());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, sheetName);
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		Material material = materialService.getMaterialById(materialId);
		String fileName = returnFileName(request, material.getMaterialName() + ".xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 
	 * Description:申报结果统计页面按申报单位统计导出统计结果
	 * 
	 * @author:lyc
	 * @date:2018年1月9日上午11:16:01
	 * @param
	 * @return void
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报结果统计页面按申报单位统计导出统计结果")
	@RequestMapping(value = "/result/exportResultSchool", method = RequestMethod.GET)
	public void exportResultSchool(Long materialId, String schoolName, Integer state, HttpServletRequest request,
			HttpServletResponse response) {
		PageParameter<DeclarationResultSchoolVO> pageParameter = new PageParameter<>(1, 50000);
		DeclarationResultSchoolVO declarationResultSchoolVO = new DeclarationResultSchoolVO();
		declarationResultSchoolVO.setMaterialId(materialId);
		declarationResultSchoolVO.setSchoolName(schoolName);
		pageParameter.setParameter(declarationResultSchoolVO);
		Workbook workbook = null;
		List<DeclarationResultSchoolVO> list = null;
		String sheetName = "";
		if (state.intValue() == 1) {
			list = decPositionService.listChosenDeclarationResultSchoolVOs(pageParameter).getRows();
			sheetName = "申报结果按单位统计（按当选数排序）";
		} else if (state.intValue() == 2) {
			list = decPositionService.listPresetDeclarationResultSchoolVOs(pageParameter).getRows();
			sheetName = "申报结果按单位统计（按申报数排序）";
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"未知的排序方式");
		}
		if (list.size() == 0) {
			list.add(new DeclarationResultSchoolVO());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, sheetName);
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		Material material = materialService.getMaterialById(materialId);
		String fileName = returnFileName(request, material.getMaterialName() + ".xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 
	 * Description:申报情况统计页面按书名统计导出统计结果
	 * 
	 * @author:lyc
	 * @date:2018年1月9日上午11:41:10
	 * @param
	 * @return void
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报情况统计页面按书名统计导出统计结果")
	@RequestMapping(value = "/result/exportSituationBook", method = RequestMethod.GET)
	public void exportSituationBook(Long materialId, String bookName, HttpServletRequest request,
			HttpServletResponse response) {
		PageParameter<DeclarationSituationBookResultVO> pageParameter = new PageParameter<>(1, 50000);
		DeclarationSituationBookResultVO declarationSituationBookResultVO = new DeclarationSituationBookResultVO();
		declarationSituationBookResultVO.setMaterialId(materialId);
		declarationSituationBookResultVO.setBookName(bookName);
		pageParameter.setParameter(declarationSituationBookResultVO);
		Workbook workbook = null;
		List<DeclarationSituationBookResultVO> list = null;
		list = decPositionService.listDeclarationSituationBookResultVOs(pageParameter).getRows();
		if (list.size() == 0) {
			list.add(new DeclarationSituationBookResultVO());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, "申报情况按书名统计");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		Material material = materialService.getMaterialById(materialId);
		String fileName = returnFileName(request, material.getMaterialName() + ".xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 
	 * Description:申报结果统计页面按书名统计导出统计结果
	 * 
	 * @author:lyc
	 * @date:2018年1月9日下午2:42:26
	 * @param
	 * @return void
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报结果统计页面按书名统计导出统计结果")
	@RequestMapping(value = "/result/exportResultBook", method = RequestMethod.GET)
	public void exportResultBook(Long materialId, String bookName, HttpServletRequest request,
			HttpServletResponse response) {
		PageParameter<DeclarationResultBookVO> pageParameter = new PageParameter<>(1, 50000);
		DeclarationResultBookVO declarationResultBookVO = new DeclarationResultBookVO();
		declarationResultBookVO.setMaterialId(materialId);
		declarationResultBookVO.setBookName(bookName);
		pageParameter.setParameter(declarationResultBookVO);
		Workbook workbook = null;
		List<DeclarationResultBookVO> list = null;
		list = decPositionService.listDeclarationResultBookVOs(pageParameter).getRows();
		if (list.size() == 0) {
			list.add(new DeclarationResultBookVO());
		}
		try {
			workbook = excelHelper.fromBusinessObjectList(list, "申报结果按书名统计");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		Material material = materialService.getMaterialById(materialId);
		String fileName = returnFileName(request, material.getMaterialName() + ".xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 
	 * Description:设置选题号页面导出选题号
	 * 
	 * @author:lyc
	 * @date:2018年1月23日下午6:18:41
	 * @param
	 * @return void
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "设置选题号页面导出选题号信息")
	@RequestMapping(value = "/textbook/exportTopic", method = RequestMethod.GET)
	public void exportTopic(Long materialId, HttpServletRequest request, HttpServletResponse response) {
		List<Textbook> list = textbookService.listTopicNumber(materialId);
		Workbook workbook = null;
		if (list.size() == 0) {
			list.add(new Textbook());
		}
		try {
			workbook = excelHelper.fromTextbookTopic(list, "选题号导出");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		Material material = materialService.getMaterialById(materialId);
		String fileName = returnFileName(request, material.getMaterialName() + ".xls");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	/**
	 * 
	 * <pre>
	* 功能描述：导出所有学校
	* 使用示范：
	*
	* &#64;param request
	* &#64;param response
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出所有学校")
	@RequestMapping(value = "/excel/allOrg", method = RequestMethod.GET)
	public void allOrg(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = null;
		List<OrgExclVO> orgList = null;
		String chooseOrg = request.getParameter("chooseOrg");
		try {


			orgList = orgService.listAllOrgToExcel(StringUtil.isEmpty(chooseOrg)?"":chooseOrg);
			workbook = excelHelper.fromBusinessObjectList(orgList, "所有学校信息");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String fileName = returnFileName(request, StringUtil.isEmpty(chooseOrg)?("所有学校"+ ".xls"):("所选学校" + ".xls"));
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.warn("文件下载时出现IO异常：{}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出机构用户对比后的信息")
	@RequestMapping(value = "/org/exportOrgInfo", method = RequestMethod.GET)
	public void exportOrgInfo(HttpServletRequest request, HttpServletResponse response, String uuid) {
		if (StringUtil.isEmpty(uuid)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.NULL_PARAM,
					"参数不能为空");
		}
		String sessionId = CookiesUtil.getSessionId(request);
		if (StringUtil.isEmpty(sessionId)){
			throw new CheckedServiceException(CheckedExceptionBusiness.SESSION, 
					CheckedExceptionResult.NULL_PARAM, "用户登陆超时，请重新登陆再试");
		}
		HttpSession session = SessionContext.getSession(sessionId);
		List<OrgVO> list = (List<OrgVO>) session.getAttribute(uuid);
		if (null == list || list.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM,
					"导出的机构信息不能为空");
		}
		Workbook workbook = null;
		try {
			workbook = excelHelper.fromOrgVO(list, "机构用户信息");
		} catch (CheckedServiceException | IllegalAccessException | IllegalArgumentException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String fileName = returnFileName(request, "机构用户信息" + ".xls");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (OutputStream out = response.getOutputStream()) {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.warn("文件下载时出现IO异常： {}", e.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
					CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
		}
	}
}
