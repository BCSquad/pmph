/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.back.vo.TopicTextVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Microsoft Offic Word 工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class TopicWordHelper {

	private final Logger logger = LoggerFactory.getLogger(TopicWordHelper.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	/**
	 * 批量导出产品申报的word 到指定的目录
	 * @param word_name 产品的名字
	 * @param filePath 系统唯一临时文件目录/产品名/书序和书名的组合，例如"/home/temp35723882/产品名称/"
	 */

	public void export(String word_name, String filePath, TopicTextVO topicTextVO) {
		HashMap<String, XWPFDocument> map = fromDeclarationEtcBOList(word_name, topicTextVO);
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
	private HashMap<String,XWPFDocument> fromDeclarationEtcBOList(String word_name, TopicTextVO bo) {
		InputStream is;
		XWPFDocument document;
		String path = this.getClass().getClassLoader().getResource("TopicTemplate.docx").getPath();
		HashMap<String, XWPFDocument> map = new HashMap<>(1);
		int decSequese =0;

		try {
			is = new FileInputStream(path);
			document = new XWPFDocument(is);
		} catch (IOException ex) {
			logger.error("读取Word模板文件'TopicTemplate.docx'时出现错误：{}", ex.getMessage());
			throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION,
					CheckedExceptionResult.FILE_CREATION_FAILED, "未找到模板文件");
		}
		if (StringUtil.notEmpty(word_name)) {
			List<XWPFRun> runs = document.getParagraphs().get(0).getRuns();
			runs.get(0).setText(word_name.concat(" 选题申报"), 0);
		}
		//标题
		List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
		XWPFParagraph xwpfParagraph = xwpfParagraphs.get(0);

		//表格
		List<XWPFTable> tables = document.getTables();
		String filename = generateFileName(bo,""/*(++decSequese)+"."*/);
		XWPFTable table = tables.get(0);  //
		fillTableData(table,bo);

		/* 选题会审核意见： */
		String authFeedback = bo.getAuthFeedback();
		if (StringUtil.notEmpty(authFeedback)) {
			xwpfParagraphs.get(xwpfParagraphs.size()-1).createRun().setText(authFeedback);
		}

		map.put(filename, document);

		return map;
	}

	/**
	 * topic 表格数据填入
	 * @param table
	 * @param bo
	 * @return
	 */
	private XWPFTable fillTableData(XWPFTable table, TopicTextVO bo) {
		List<XWPFTableRow> rows = table.getRows();
		int rowNum = 0;
		List<XWPFTableCell> cells = rows.get(rowNum).getTableCells();

		String value = "";

		/* 第1行 --------------- */
		//选题名称
		value = bo.getBookname();
		if (StringUtil.notEmpty(value)) {
			cellSetTextWithFont(cells.get(2), value);
			//cells.get(2).setText(value);
		}
		//读者对象
		value = bo.getReadType();
		if (StringUtil.notEmpty(value)) {
			cellSetTextWithFont(cells.get(4), value);
		}
		/*第2行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//选题来源
		String[] sourceType = bo.getSourceType();
		value = getStringFromStrArray(sourceType);
		cellSetTextWithFont(cells.get(2), value);

		/*第3行---------------*/
		cells = rows.get(++rowNum).getTableCells();
		//估计字数
		value = ObjectUtil.notNull(bo.getWordNumber())?bo.getWordNumber().toString()+"(千字)":"";
		cellSetTextWithFont(cells.get(2), value);
		//估计图数
		value = ObjectUtil.notNull(bo.getPictureNumber())?bo.getPictureNumber().toString()+"(副)":"";
		cellSetTextWithFont(cells.get(4), value);
		//预计交稿时间
		value = DateUtil.date2Str(bo.getDeadline(),"yyyy-MM-dd");
		cellSetTextWithFont(cells.get(6), value);
		//学科及专业
		value = bo.getSubject();
		cellSetTextWithFont(cells.get(8), value);

		/*第4行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//图书类别
		String[] typeName = bo.getTypeName();
		value = getStringFromStrArray(typeName);
		cellSetTextWithFont(cells.get(2), value);
		//级别
		String[] rankType = bo.getRankType();
		value = getStringFromStrArray(rankType);
		cellSetTextWithFont(cells.get(4), value);

		/*第5行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//原书名
		value = bo.getRevisionBookname();
		cellSetTextWithFont(cells.get(2), value);

		//原编著者
		value = bo.getRevisionAuthor();
		cellSetTextWithFont(cells.get(4), value);

		/*第6行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//上版出版时间
		value= DateUtil.date2Str(bo.getRevisionPublishDate(),"yyyy-MM-dd");
		cellSetTextWithFont(cells.get(2), value);

		//累计印数
		value = ObjectUtil.notNull(bo.getRevisionPrint())?bo.getRevisionPrint().toString():"";
		cellSetTextWithFont(cells.get(4), value);

		//库存数
		value = ObjectUtil.notNull(bo.getRevisionStock())?bo.getRevisionStock().toString():"";
		cellSetTextWithFont(cells.get(6), value);

		/*第7行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//译稿原文名
		value = bo.getOriginalBookname();
		cellSetTextWithFont(cells.get(2), value);

		/*第8行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//原著者
		value = bo.getOriginalAuthor();
		cellSetTextWithFont(cells.get(2), value);

		//国籍
		value = bo.getNation();
		cellSetTextWithFont(cells.get(4), value);

		//原出版者
		value = bo.getOriginalPublisher();
		cellSetTextWithFont(cells.get(6), value);

		//出版年代及版次
		value = bo.getEdition();
		cellSetTextWithFont(cells.get(8), value);

		/*第9行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//主编姓名
		value = bo.getRealname();
		cellSetTextWithFont(cells.get(2), value);

		//性别
		value = (ObjectUtil.notNull(bo.getSex())&&bo.getSex()>0?"女":"男");
		cellSetTextWithFont(cells.get(4), value);

		//年龄
		value = ObjectUtil.notNull(bo.getPrice())?bo.getPrice().toString():"";
		cellSetTextWithFont(cells.get(6), value);

		//行政职务
		value = bo.getPosition();
		cellSetTextWithFont(cells.get(8), value);

		/*第10行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//专业职务
		value = getStringFromStrArray(bo.getPositionProfessionName());
		cellSetTextWithFont(cells.get(2), value);

		//学历
		value = getStringFromStrArray(bo.getDegreeName());
		cellSetTextWithFont(cells.get(4), value);

		/*第11行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//工作单位
		value = bo.getWorkplace();
		cellSetTextWithFont(cells.get(2), value);

		//电话
		value = bo.getPhone();
		cellSetTextWithFont(cells.get(4), value);

		/*第12行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//通讯地址
		value = bo.getAddress();
		cellSetTextWithFont(cells.get(2), value);

		//邮编
		value = bo.getPostcode();
		cellSetTextWithFont(cells.get(4), value);

		//e-mail
		value = bo.getEmail();
		cellSetTextWithFont(cells.get(6), value);

		/*第13行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//主要专业成就及学术地位
		value = ObjectUtil.notNull(bo.getTopicExtra())?bo.getTopicExtra().getAchievement():"";
		cellSetTextWithFont(cells.get(2), value);

		/*第14行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//写作、外语水平
		value = ObjectUtil.notNull(bo.getTopicExtra())?bo.getTopicExtra().getAbility():"";
		cellSetTextWithFont(cells.get(2), value);

		/*第15行---------------*/
		++rowNum;
		//主要参编者 列表表头行 此时处理加动态数量的空白行 及 合并单元格
		List<TopicWriter> topicWriters = bo.getTopicWriters();
		if(CollectionUtil.isNotEmpty(topicWriters)){
			for(int i = 0;i<topicWriters.size()-1;i++){
				XWPFTableRow newRow = table.insertNewTableRow(rowNum + 1);
				for(int c = 0;c<9;c++){
					newRow.addNewTableCell();
				}
				mergeCellsHorizontal(table,rowNum+1,4,5);
			}
			mergeCellsVertically(table,0,rowNum,rowNum+topicWriters.size()-1);
		}

		/*第16行---------------*/
		//主要参编者 列表数据行 模板第16行（空），可能已经加行，此时填充数据
		if(CollectionUtil.isNotEmpty(topicWriters)){
			for (TopicWriter topicWriter:topicWriters) {
				cells = rows.get(++rowNum).getTableCells();
				//姓名
				value = topicWriter.getRealname();
				cellSetTextWithFont(cells.get(1), value);
				//性别
				value = (ObjectUtil.notNull(topicWriter.getSex())&&topicWriter.getSex()>0?"女":"男");
				cellSetTextWithFont(cells.get(2), value);
				//年龄
				value = ObjectUtil.notNull(topicWriter.getPrice())?topicWriter.getPrice().toString():"";
				cellSetTextWithFont(cells.get(3), value);
				//工作单位
				value= topicWriter.getWorkplace();
				cellSetTextWithFont(cells.get(4), value);

				/*此处 模板中的“工作单位”word文档中已合并 java读取时并不知道，只认为是一个cell
				后面加的行用java代码合并的单元格,故cells集合中存在被合并的单元格只是不显示。由此造成的cells集合大小不一致
				后面行用总大小倒序表示*/

				//电话
				value = topicWriter.getPhone();
				cellSetTextWithFont(cells.get(cells.size()-3), value);
				//职务职称
				value = topicWriter.getPosition();
				cellSetTextWithFont(cells.get(cells.size()-2), value);
				//学历
				value = topicWriter.getDegreeName();
				cellSetTextWithFont(cells.get(cells.size()-1), value);
			}
		}else{
			//空表则跳过模板自带的默认一行空白行
			++rowNum;
		}


		/*第17行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//预计读者及购买力
		value = bo.getReaderQuantity();
		cellSetTextWithFont(cells.get(2), value);

		//作者购书
		value = StringUtil.isEmpty(bo.getPurchase())?"":bo.getPurchase()+"(本)";
		cellSetTextWithFont(cells.get(4), value);

		/*第18行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//可能的宣传方式
		value = bo.getCampaign();
		cellSetTextWithFont(cells.get(2), value);

		//可能的销售渠道
		value = bo.getSalesChannel();
		cellSetTextWithFont(cells.get(4), value);

		/*第19行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//图书生命周期
		value = bo.getLifecycle();
		cellSetTextWithFont(cells.get(2), value);

		//作者赞助
		value = bo.getSponsorship();
		cellSetTextWithFont(cells.get(4), value);

		/*第20行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//印刷、用纸建议
		value = bo.getPrintAdvise();
		cellSetTextWithFont(cells.get(2), value);

		//定价建议
		value = bo.getPriceAdvise();
		cellSetTextWithFont(cells.get(4), value);

		/*第21行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//预计印数
		value = bo.getPrintNumber();
		cellSetTextWithFont(cells.get(2), value);

		//成本估算
		value = bo.getCost();
		cellSetTextWithFont(cells.get(4), value);

		/*第22行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//保底印数
		value = bo.getMinPrintNumber();
		cellSetTextWithFont(cells.get(2), value);

		//效益估算
		value = bo.getBenefit();
		cellSetTextWithFont(cells.get(4), value);

		/*第23行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//选题理由出版价值
		value = ObjectUtil.notNull(bo.getTopicExtra())?bo.getTopicExtra().getReason():"";
		cellSetTextWithFont(cells.get(1), value);

		/*第24行---------------*/
		cells = rows.get(++rowNum).getTableCells();

		//主要内容与特色
		value = ObjectUtil.notNull(bo.getTopicExtra())?bo.getTopicExtra().getScore():"";
		cellSetTextWithFont(cells.get(1), value);


		/*第25行---------------*/
		++rowNum;
		//外设同类书情况 列表表头行 此时处理加动态数量的空白行 及 合并单元格
		List<TopicSimilarBook> topicSimilarBooks = bo.getTopicSimilarBooks();
		if(CollectionUtil.isNotEmpty(topicSimilarBooks)){
			for(int i = 0;i<topicSimilarBooks.size()-1;i++){
				XWPFTableRow newRow = table.insertNewTableRow(rowNum + 1);
				for(int c = 0;c<9;c++){
					newRow.addNewTableCell();
				}
			}
			mergeCellsVertically(table,0,rowNum,rowNum+topicSimilarBooks.size()-1);
		}

		/*第26行---------------*/
		//外设同类书情况 列表数据行 模板第26行（空），可能已经加行，此时填充数据
		if(CollectionUtil.isNotEmpty(topicSimilarBooks)){
			for (TopicSimilarBook topicSimilarBook:topicSimilarBooks) {
				cells = rows.get(++rowNum).getTableCells();
				//书名
				value = topicSimilarBook.getBookname();
				cellSetTextWithFont(cells.get(1), value);
				//版次
				value = topicSimilarBook.getEdition();
				cellSetTextWithFont(cells.get(2), value);
				//作者
				value = topicSimilarBook.getAuthor();
				cellSetTextWithFont(cells.get(3), value);
				//出版单位
				value = topicSimilarBook.getPublisher();
				cellSetTextWithFont(cells.get(4), value);
				//开本
				value = topicSimilarBook.getBooksize();
				cellSetTextWithFont(cells.get(5), value);
				//印数
				value = topicSimilarBook.getPrintNumber();
				cellSetTextWithFont(cells.get(6), value);
				//定价
				value = topicSimilarBook.getPrice();
				cellSetTextWithFont(cells.get(7), value);
				//出版时间
				value = DateUtil.date2Str(topicSimilarBook.getPublishDate(),"yyyy-MM-dd");
				cellSetTextWithFont(cells.get(8), value);
			}
		}else{
			//空表则跳过模板自带的默认一行空白行
			++rowNum;
		}

		return table;
	}

	/**
	 * 向单元格中填入数据 并用同一格式 （简单的setText无法控制同一格式）
	 * @param cell
	 * @param value
	 */
	private void cellSetTextWithFont(XWPFTableCell cell, String value) {
		XWPFParagraph pIO = CollectionUtil.isNotEmpty(cell.getParagraphs())?cell.getParagraphs().get(0):cell.addParagraph();
		XWPFRun rIO = CollectionUtil.isNotEmpty(pIO.getRuns())?pIO.getRuns().get(0):pIO.createRun();
		rIO.setFontFamily("宋体");
		rIO.setFontSize(8);
		rIO.setBold(false);
		rIO.setText(value);
	}

	/**
	 * 将字符串数组拼接成字符串 用,隔开
	 * @param strArr
	 * @return
	 */
	private String getStringFromStrArray(String[] strArr) {
		String value = "";
		if(ObjectUtil.notNull(strArr)){
			for (String s:strArr) {
				value += s;
				value += ",";
			}
			value = value.replaceAll(",$","");
		}
		return value;
	}


	private String generateFileName(TopicTextVO bo ,String decSequese) throws CheckedServiceException {
		String realname = bo.getBookname()+"-"+bo.getRealname();
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
	 * @Description: 跨列合并
	 */
	public  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
		for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
			XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
			if ( cellIndex == fromCell ) {
				// The first merged cell is set with RESTART merge value
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

	/**
	 * @Description: 跨行合并
	 * see http://stackoverflow.com/questions/24907541/row-span-with-xwpftable
	 */
	public  void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
		for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
			XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
			if ( rowIndex == fromRow ) {
				// The first merged cell is set with RESTART merge value
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

}
