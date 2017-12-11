/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.CmsExtraService;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialNoteAttachmentService;
import com.bc.pmpheep.back.service.MaterialNoticeAttachmentService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.RandomUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.ExcelHelper;
import com.bc.pmpheep.utils.WordHelper;
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
	WordHelper wordHelper;
	@Resource
	MaterialService materialService;
	@Resource
	TextbookService textbookService;

	/**
	 * 普通文件下载
	 * 
	 * @param id
	 *            文件在MongoDB中的id
	 * @param response
	 *            服务响应
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/file/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		GridFSDBFile file = fileService.get(id);
		if (null == file) {
			logger.warn("未找到id为'{}'的文件", id);
			return;
		}
		String fileName = file.getFilename();
		// 针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
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
	@RequestMapping(value = "/file/{type}/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable("type") String type, @PathVariable("id") String id, HttpServletResponse response)
			throws UnsupportedEncodingException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/force-download");
		GridFSDBFile file = fileService.get(id);
		if (null == file) {
			logger.warn("未找到id为'{}'的文件", id);
			return;
		}
		response.setHeader("Content-Disposition",
				"attachment;fileName=" + new String(file.getFilename().getBytes("utf-8"), "ISO8859-1"));
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
	@RequestMapping(value = "/groupfile/download/{id}", method = RequestMethod.GET)
	public ResponseBean download(@PathVariable("id") String id, @RequestParam("groupId") long groupId,
			HttpServletResponse response) {
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
		String file_name;
		try {
			file_name = new String(file.getFilename().getBytes(), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment;fileName=" + file_name);
		} catch (UnsupportedEncodingException e) {
			logger.warn("修改编码格式的时候失败");
		}
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
	@RequestMapping(value = "/excel/declaration", method = RequestMethod.GET)
	public void declarationExcel(Long materialId, String textBookids, String realname, String position, String title,
			String orgName, String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress,
			HttpServletResponse response) {
		Workbook workbook = null;
		try {
			workbook = excelHelper
					.fromDeclarationEtcBOList(
							declarationService.declarationEtcBO(materialId, textBookids, realname, position, title,
									orgName, unitName, positionType, onlineProgress, offlineProgress, response),
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
	@RequestMapping(value = "/word/declaration", method = RequestMethod.GET)
	public void declarationWord(Long materialId, String textBookids, String realname, String position, String title,
			String orgName, String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress,
			HttpServletResponse response) {
		long startTime = System.currentTimeMillis();// 获取当前时间
		String tempDir = String.valueOf(System.currentTimeMillis()).concat(String.valueOf(RandomUtil.getRandomNum()));
		String materialName = materialService.getMaterialNameById(materialId);
		List<Textbook> textbooks = textbookService.getTextbookByMaterialId(materialId);
		List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>();
		try {
			declarationEtcBOs = declarationService.declarationEtcBO(materialId, textBookids, realname, position, title,
					orgName, unitName, positionType, onlineProgress, offlineProgress, response);
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		for (int i = 0; i < textbooks.size(); i++) {
			List<DeclarationEtcBO> list = new ArrayList<>();
			for (DeclarationEtcBO declarationEtcBO : declarationEtcBOs) {
				if (textbooks.get(i).getTextbookName().equals(declarationEtcBO.getTextbookName())) {
					list.add(declarationEtcBO);
				}
			}
			StringBuilder sb = new StringBuilder();
			String src = "D:/";
			src = src.substring(1);
			sb.append(src);
			if (!src.endsWith(File.separator)) {
				sb.append(File.separator);
			}
			sb.append(tempDir);
			sb.append(File.separator);
			sb.append(materialName);
			sb.append(File.separator);
			sb.append(i + "." + textbooks.get(i).getTextbookName());
			sb.append(File.separator);
			wordHelper.export(materialName, sb.toString(), list);
		}
		long endTime = System.currentTimeMillis();
		System.err.println("程序运行时间：" + (endTime - startTime) + "ms");
	}

}
