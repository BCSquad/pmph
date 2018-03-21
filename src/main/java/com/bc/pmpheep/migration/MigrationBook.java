package com.bc.pmpheep.migration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.dao.BookCorrectionDao;
import com.bc.pmpheep.back.dao.BookDao;
import com.bc.pmpheep.back.dao.BookDetailDao;
import com.bc.pmpheep.back.dao.BookEditorDao;
import com.bc.pmpheep.back.dao.BookUserCommentDao;
import com.bc.pmpheep.back.dao.BookUserLikeDao;
import com.bc.pmpheep.back.dao.BookUserMarkDao;
import com.bc.pmpheep.back.dao.BookVideoDao;
import com.bc.pmpheep.back.dao.MaterialTypeDao;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.erp.service.InfoWorking;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

@Component
public class MigrationBook {
	@Autowired
	BookService bookService;
	@Autowired
	MaterialTypeDao materialTypeDao;
	@Autowired
	BookDetailDao bookDetailDao;
	@Autowired
	BookUserCommentDao bookUserCommentDao;
	@Autowired
	BookUserMarkDao bookUserMarkDao;
	@Autowired
	BookUserLikeDao bookUserLikeDao;
	@Autowired
	BookVideoDao bookVideoDao;
	@Autowired
	BookCorrectionDao bookCorrectionDao;
	@Autowired
	BookEditorDao bookEditorDao;
	@Autowired
	BookDao bookDao;

	public void start() {
		clearBook();
	}

	protected void clearBook() {
		StringBuilder sns = new StringBuilder();
		InputStream is;
		Workbook workbook;
		String path = this.getClass().getClassLoader().getResource("book.xlsx").getPath();
		try {
			is = new FileInputStream(path);
		} catch (IOException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.FILE_CREATION_FAILED, "未找到模板文件");
		}
		try {
			workbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.ILLEGAL_PARAM,
					"读取文件失败");
		}
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (ObjectUtil.isNull(sheet)) {
				continue;
			}
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				Cell sn = row.getCell(1);
				if (ObjectUtil.notNull(sn) && !"".equals(sn.toString())) {
					sns.append(",'" + sn.toString().replace(".0", "") + "'");
				}
			}
		}
		String sn = sns.toString().substring(1);
		String[] vns = new InfoWorking().listBook(sn);
		List<Long> ids = bookDao.listBookId();
		bookDao.deleted(ids);
		bookDetailDao.deleteBookDetailByBookIds(ids);
		bookDao.deletedBookSupport(ids);
		bookCorrectionDao.deleteBookCoorrectionTrackByBookIds(ids);
		bookEditorDao.deleteBookEditorByBookIds(ids);
		bookUserCommentDao.deleteBookUserCommentBookIds(ids);
		bookUserLikeDao.deleteBookUserLikeByBookIds(ids);
		bookUserMarkDao.deleteBookUserMarkByBookIds(ids);
		bookVideoDao.deleteBookVideoByBookIds(ids);
		bookService.AbuttingJoint(vns, 1);
	}

}
