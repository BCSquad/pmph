package com.bc.pmpheep.migration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.erp.service.InfoWorking;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
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
	@Autowired
	private CmsContentService cmsContentService;
	@Autowired
	private ContentService contentService;

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
		Map<String,String> snsAndMaterNames =  new HashMap<String,String>(16);
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
					snsAndMaterNames.put(sn.toString().replace(".0", ""),  String.valueOf(row.getCell(8)));
				}
			}
		}
		bookDao.deleted();
		bookDetailDao.deleteBookDetailByBookIds();
		bookDao.deletedBookSupport();
		bookCorrectionDao.deleteBookCoorrectionTrackByBookIds();
		bookEditorDao.deleteBookEditorByBookIds();
		bookUserCommentDao.deleteBookUserCommentBookIds();
		bookUserLikeDao.deleteBookUserLikeByBookIds();
		bookUserMarkDao.deleteBookUserMarkByBookIds();
		bookVideoDao.deleteBookVideoByBookIds();
		//同步更新或者插入书籍
		String sn = sns.toString().substring(1);
		JSONArray bookInfo = new InfoWorking().listBook(sn);
		String[] vns = new String[bookInfo.size()];//版本号
		for (int i = 0; i < bookInfo.size(); i++) {
			JSONObject job = bookInfo.getJSONObject(i);
			vns[i] = job.getString("editionnum");
		}
		bookService.AbuttingJoint(vns, 1);
		//初始化教材社区数据
		Map<String,Long> materNamesAndIds =  new HashMap<String,Long>(16);
		for (int i = 0; i < bookInfo.size(); i++) {
			JSONObject job = bookInfo.getJSONObject(i);
			String bookVn   = job.getString("editionnum");  //本版号
			String bookSn   = job.getString("booknumber");  //书号
			String materName = snsAndMaterNames.get(bookSn);
			if(null == materName || "".equals(materName.trim())){
				continue;
			}
			Long materId = materNamesAndIds .get(materName); 
			if(null ==  materId){
				materId  = Long.MAX_VALUE - i ;
				Content content  = new Content( materName);
				content  = contentService.add(content);
				CmsContent cmsContent  = new CmsContent();
				cmsContent.setParentId(0L);
				cmsContent.setPath("0");
				cmsContent.setMid(content.getId());
				cmsContent.setCategoryId(3L);
				cmsContent.setTitle(materName);
				cmsContent.setAuthorType(new Short("0"));
				cmsContent.setAuthorId(342L);
				cmsContent.setIsPublished(true);
				cmsContent.setAuthStatus(new Short("2"));
				cmsContent.setAuthDate(DateUtil.date2Str(new Date()));
				cmsContent.setAuthUserId(342L);
				cmsContent.setGmtCreate(DateUtil.getCurrentTime() );
				cmsContent.setGmtUpdate(DateUtil.getCurrentTime() );
				cmsContent.setIsMaterialEntry(true);
				cmsContent.setMaterialId(materId);
				cmsContentService.addCmsContent(cmsContent);
				materNamesAndIds.put(materName, materId);
			}
			//更新书籍
			Book book = bookDao.getBookByVn2(bookVn);
			if(null != book ){
				book.setMaterialId(materId);
				book.setSn(bookSn);
				bookDao.updateBook(book);
			}
		}
	}

}
