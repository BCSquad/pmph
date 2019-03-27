/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.util.FileUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookCorrectionTrackVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Microsoft Offic Word 工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class BookChooseTrackWordHelper {

	private final Logger logger = LoggerFactory.getLogger(BookChooseTrackWordHelper.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	/**
	 * 批量导出产品申报的word 到指定的目录
	 * @param word_name 产品的名字
	 * @param filePath 系统唯一临时文件目录/产品名/书序和书名的组合，例如"/home/temp35723882/产品名称/"
	 */

	public void export(String word_name, String filePath, Book vo) {
		HashMap<String, XWPFDocument> map = fromDeclarationEtcBOList(word_name, vo);
		if (createPath(filePath)) {
			if (!filePath.endsWith(File.separator)) {
				filePath = filePath.concat(File.separator);
			}
			File file;
			for (Map.Entry<String, XWPFDocument> entry : map.entrySet()) {
				file = new File(filePath.concat(FileUtil.replaceIllegalCharForFileName(entry.getKey())));
				try {
					FileOutputStream out = new FileOutputStream(file);
					entry.getValue().write(out);
					out.flush();
					out.close();
				} catch (IOException ex) {
					logger.error("Word导出错误：{}", ex.getMessage());
					throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION,
							CheckedExceptionResult.FILE_CREATION_FAILED, "未能创建Word文件");
				}
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION,
					CheckedExceptionResult.DIRECTORY_CREATION_FAILED, "未能创建目录");
		}

	}

	/**
	 * 创建文件路径
	 * @param filePath 文件的路径
	 * @return
	 */
	private boolean createPath(String filePath) {
		File destDir;
		if (filePath.endsWith(File.separator)) {
			destDir = new File(filePath);// 给出的是路径时
		} else {
			destDir = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			return destDir.mkdirs();
		}
		return true;
	}

	/**
	 * 填充 word 数据
	 * @param word_name
	 * @return
	 */
	private HashMap<String,XWPFDocument> fromDeclarationEtcBOList(String word_name, Book bo) {
		InputStream is;
		XWPFDocument document;
		String path = this.getClass().getClassLoader().getResource("BookChooseTrackTemplate.docx").getPath();
		HashMap<String, XWPFDocument> map = new HashMap<>(1);
		int decSequese =0;

		Object object = (Object)bo;

			try {
				is = new FileInputStream(path);
				document = new XWPFDocument(is);
			} catch (IOException ex) {
				logger.error("读取Word模板文件'ClicResumeTemplate.docx'时出现错误：{}", ex.getMessage());
				throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION,
						CheckedExceptionResult.FILE_CREATION_FAILED, "未找到模板文件");
			}
			if (StringUtil.notEmpty(word_name)) {
				List<XWPFRun> runs = document.getParagraphs().get(0).getRuns();
				runs.get(0).setText(word_name.concat("图书纠错"), 0);
			}
			List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
			List<XWPFTable> tables = document.getTables();
			String filename = generateFileName(bo,(++decSequese)+".");
			XWPFTable xwpfTable = tables.get(0);
			xwpfTable.getRow(0).getTableCells().get(1).setText(bo.getIsbn());
			xwpfTable.getRow(0).getCell(3).setText(bo.getBookname()==null?"":bo.getBookname());
			xwpfTable.getRow(0).getCell(5).setText(bo.getIsbn()==null?"":bo.getIsbn().toString());
		/*	map.put(filename, removeEmptyTables(document, filter));*/
			map.put(filename, document);


		return map;
	}



	private String generateFileName(Book bo ,String decSequese) throws CheckedServiceException {
		String realname = bo.getBookname()
				+(StringUtil.notEmpty(bo.getBookname())?"-"+bo.getBookname():"")
				+(StringUtil.notEmpty(bo.getBookname())?"-"+bo.getBookname():"");
		String filename;
		if (StringUtil.isEmpty(realname)) {
			realname = "未署名";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(decSequese);
		sb.append(realname);
		sb.append(".docx");
		filename = sb.toString();
		return filename;
	}

	/**
	 * 获取表头，默认无别名既为header
	 * @param excelHeader
	 * @param alias
	 * @return
	 */
	private String getHeaderName(ExcelHeader excelHeader, String[] alias) {
		String headerName = "";
		if(alias != null && alias.length >0){
			if("0".equals(alias[0])){
				headerName = excelHeader.alias_0();
			}else if("1".equals(alias[0])){
				headerName = excelHeader.alias_1();
			}else if("2".equals(alias[0])){
				headerName = excelHeader.alias_2();
			}else if("3".equals(alias[0])){
				headerName = excelHeader.alias_3();
			}
		}else{
			headerName = excelHeader.header();
		}
		return headerName;
	}


}
