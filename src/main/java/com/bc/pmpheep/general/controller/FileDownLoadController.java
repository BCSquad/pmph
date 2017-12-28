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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.bo.DecPositionBO;
import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.BookCorrectionService;
import com.bc.pmpheep.back.service.CmsExtraService;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialNoteAttachmentService;
import com.bc.pmpheep.back.service.MaterialNoticeAttachmentService;
import com.bc.pmpheep.back.service.MaterialOrgService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.RandomUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookCorrectionTrackVO;
import com.bc.pmpheep.back.vo.ExcelDecAndTextbookVO;
import com.bc.pmpheep.back.vo.OrgExclVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.ExcelHelper;
import com.bc.pmpheep.utils.WordHelper;
import com.bc.pmpheep.utils.ZipHelper;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * 文件下载控制器
 * 
 * @author L.X <gugia@qq.com>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class FileDownLoadController {
	public static Map<String, ZipDownload> map = new HashMap<>();
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
	WordHelper wordHelper;
	@Resource
	MaterialService materialService;
	@Resource
	TextbookService textbookService;
	@Resource
	ZipHelper zipHelper;
	@Resource
	MaterialOrgService materialOrgService;
	@Autowired
	BookCorrectionService bookCorrectionService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "文件下载";

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
		String fileName = returnFileName(request, file.getFilename());
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
	 * @param response
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报表批量导出excel")
	@RequestMapping(value = "/excel/declaration", method = RequestMethod.GET)
	public void declarationExcel(Long materialId, String textBookids, String realname, String position, String title,
			String orgName, String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress,
			HttpServletResponse response) {
		Workbook workbook = null;
		try {
			workbook = excelHelper.fromDeclarationEtcBOList(declarationService.declarationEtcBO(materialId, textBookids,
					realname, position, title, orgName, unitName, positionType, onlineProgress, offlineProgress),
					"专家信息表");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		try {
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + new String("DeclarationEtcBOList.xls".getBytes("utf-8"), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			logger.warn("修改编码格式的时候失败");
		}
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
	 * @param response
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "申报表批量导出word")
	@RequestMapping(value = "/word/declaration", method = RequestMethod.GET)
	public void declarationWord(Long materialId, String textBookids, String realname, String position, String title,
			String orgName, String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress,
			String id) {
		String src = this.getClass().getResource("/").getPath();
		src = src.substring(1);
		if (!src.endsWith(File.separator)) {
			src += File.separator;
		}
		ZipDownload zipDownload = new ZipDownload();
		String materialName = materialService.getMaterialNameById(materialId);
		List<Textbook> textbooks = textbookService.getTextbookByMaterialId(materialId);
		List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>();
		String dest = src + id;
		zipDownload.setId(id);
		zipDownload.setMaterialName(materialName);
		zipDownload.setState(0);
		zipDownload.setDetail("loading...");
		zipDownload.setCreateTime(DateUtil.getCurrentTime());
		map.put(id, zipDownload);
		try {
			declarationEtcBOs = declarationService.declarationEtcBO(materialId, textBookids, realname, position, title,
					orgName, unitName, positionType, onlineProgress, offlineProgress);
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		try {
			for (int i = 0; i < textbooks.size(); i++) {
				List<DeclarationEtcBO> list = new ArrayList<>();
				for (DeclarationEtcBO declarationEtcBO : declarationEtcBOs) {
					if (textbooks.get(i).getTextbookName().equals(declarationEtcBO.getTextbookName())) {
						list.add(declarationEtcBO);
					}
				}
				if (!CollectionUtil.isEmpty(list)) {
					StringBuilder sb = new StringBuilder();
					sb.append(src);
					sb.append(id);
					sb.append(File.separator);
					sb.append(materialName);
					sb.append(File.separator);
					sb.append((i + 1) + "." + textbooks.get(i).getTextbookName());
					sb.append(File.separator);
					wordHelper.export(materialName, sb.toString(), list);
					list.clear();
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		map.put(id, zipDownload);
		new Thread(zipDownload).start();
		try {
			zipHelper.zip(dest + File.separator + materialName, dest + File.separator, true, null);
		} catch (Exception e) {
			e.getMessage();
		}
		zipDownload.setState(1);
		zipDownload.setDetail("/zip/download?id=" + id);
		map.put(id, zipDownload);
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
		zipDownload.setState(0);
		zipDownload.setDetail("loading...");
		if (map.containsKey(id)) {
			zipDownload.setState(map.get(id).getState());
			zipDownload.setDetail(map.get(id).getDetail());
		}
		return zipDownload;
	}

	/**
	 * 
	 * 
	 * 功能描述：返回唯一标识
	 * 
	 * @param id
	 * @return
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "返回word导出唯一标识")
	@RequestMapping(value = "/word/identification", method = RequestMethod.GET)
	public String identification() {
		String id = String.valueOf(System.currentTimeMillis()).concat(String.valueOf(RandomUtil.getRandomNum()));
		return id;
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
		String materialName = map.get(id).getMaterialName();
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
			map.remove(id);
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
			workbook = excelHelper.fromBusinessObjectList(orgList, "学校信息");
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String materialName = orgList.get(0).getMaterialName();// 教材名称
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
	 * 导出书籍遴选名单/批量导出
	 * 
	 * @param request
	 * @param response
	 * @param textbookIds
	 * @throws CheckedServiceException
	 * @throws Exception
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出书籍遴选名单/批量导出")
	@RequestMapping(value = "/position/exportExcel", method = RequestMethod.GET)
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
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String fileName = null;
		if (textbookIds.length > 1) {// 当批量导出的时候 文件名为教材名称
			String materialName = list.get(0).getMaterialName();// 书籍名称
			fileName = returnFileName(request, materialName + ".xls");
		} else {// 当单个导出的时候 文件名为书籍名称
			String TextbookName = list.get(0).getTextbookName();// 书籍名称
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
			list = bookCorrectionService.listBookCorrectionTrack(request, null, null, bookname, isEditorReplied)
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
	 * 角色遴选 批量导出主编、副主编
	 * 
	 * @param textbookIds
	 * @param request
	 * @param response
	 * @throws IllegalAccessException
	 */
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "角色遴选 批量导出主编、副主编")
	@RequestMapping(value = "/position/ExportEditor", method = RequestMethod.GET)
	public void ExportEditor(@RequestParam("textbookIds") Long[] textbookIds, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException {
		Workbook workbook = null;
		List<DecPositionBO> list = null;
		try {
			list = textbookService.getExcelDecByMaterialId(textbookIds);
			workbook = excelHelper.fromDecPositionBOList(list, "主编/副主编");
		} catch (CheckedServiceException | IllegalArgumentException e) {
			logger.warn("数据表格化的时候失败");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		String textbookName = list.get(0).getTextbookName();// 教材名称
		String fileName = returnFileName(request, textbookName + ".xls");
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

}
