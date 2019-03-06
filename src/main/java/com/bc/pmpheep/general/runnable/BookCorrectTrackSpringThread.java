package com.bc.pmpheep.general.runnable;

import com.bc.pmpheep.back.dao.BookCorrectionDao;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.BookCorrectionService;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.BookCorrectionTrackVO;
import com.bc.pmpheep.back.vo.TopicTextVO;
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.BookCorrectTrackWordHelper;
import com.bc.pmpheep.utils.ZipHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookCorrectTrackSpringThread implements Runnable {

	BookCorrectionService bookCorrectionService;
	BookCorrectionDao bookCorrectionDao;
	String bookname;
	Boolean isEditorReplied;
	Logger logger = LoggerFactory.getLogger(BookCorrectTrackSpringThread.class);
	ZipHelper zipHelper;
	BookCorrectTrackWordHelper bookCorrectTrackWordHelper;
	String sessionId;
	Boolean result;
	HttpServletRequest request;

//	ExpertationDao expertationDao;
//	ExpertationVO expertationVO;
//	ProductService productService;
//	ExpertationService expertationService;
	String id;

	public BookCorrectTrackSpringThread(ZipHelper zipHelper,
										BookCorrectTrackWordHelper bookCorrectTrackWordHelper,
										BookCorrectionService bookCorrectionService,
										String bookname, Boolean isEditorReplied,
										String sessionId, Boolean result,
										HttpServletRequest request,
										String id) {
		this.zipHelper = zipHelper;
		this.bookCorrectTrackWordHelper = bookCorrectTrackWordHelper;
		this.bookCorrectionService=bookCorrectionService;
		this.bookname = bookname;
		this.isEditorReplied= isEditorReplied;
		this.sessionId = sessionId;
		this.result = result;
		this.request = request;
		this.id = id;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Override
	public synchronized void run() {
//		System.out.println("线程开始");
		String src = this.getClass().getResource("/").getPath();
		src = src.substring(1);
		if (!src.endsWith(File.separator)) {
			src += File.separator;
		}
		ZipDownload zipDownload = new ZipDownload();
		String fname = FileUtil.replaceIllegalCharForFileName("图书纠错跟踪");
		String dest = src + this.id;
		zipDownload.setId(this.id);
		zipDownload.setMaterialName(fname); // 由于历史原因 这里导出的bean 的名称 用materialName
		zipDownload.setState(0);
		zipDownload.setDetail("loading...");
		zipDownload.setCreateTime(DateUtil.getCurrentTime());
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);

		List<BookCorrectionTrackVO> list = new ArrayList<>();
		//业务逻辑获取数据
		try {
			PageResult<BookCorrectionTrackVO> n = bookCorrectionService.listBookCorrectionTrack(request, 1, -1, bookname, isEditorReplied, result);
			list = n.getRows();
		}catch (CheckedServiceException | IllegalArgumentException e) {
			logger.warn("获取源数据的时候失败");
		}
		try{
			for (int i = 0; i < list.size(); i++) {
				BookCorrectionTrackVO vo = list.get(i);
				if (!CollectionUtil.isEmpty(list)) {
					StringBuilder sb = new StringBuilder();
					sb.append(src);
					sb.append(this.id);
					sb.append(File.separator);
					sb.append(fname);
					sb.append(File.separator);

					this.bookCorrectTrackWordHelper.export(vo.getBookname(),sb.toString(), vo,(i + 1));
				}


			}
		}catch (Exception e) {
			logger.warn("数据文档化的时候失败");
			e.printStackTrace();
		}

		new Thread(zipDownload).start();
		try {
			this.zipHelper.zip(dest + File.separator + fname, dest + File.separator, true, null);
		} catch (Exception e) {
			e.getMessage();
		}
		zipDownload.setState(1);
		zipDownload.setDetail("/zip/download?id=" + this.id);
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);





		}





}
