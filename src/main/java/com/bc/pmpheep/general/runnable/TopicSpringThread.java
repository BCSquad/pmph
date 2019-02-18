package com.bc.pmpheep.general.runnable;

import com.bc.pmpheep.back.dao.ExpertationDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.ExpertationService;
import com.bc.pmpheep.back.service.ProductService;
import com.bc.pmpheep.back.service.TopicService;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.back.vo.TopicTextVO;
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.utils.TopicWordHelper;
import com.bc.pmpheep.utils.TopicWordHelper;
import com.bc.pmpheep.utils.ZipHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TopicSpringThread implements Runnable {

	TopicService topicService;
	Long topicId;
	Logger logger = LoggerFactory.getLogger(TopicSpringThread.class);
	ZipHelper zipHelper;
	TopicWordHelper topicWordHelper;
	String sessionId;

//	ExpertationDao expertationDao;
//	ExpertationVO expertationVO;
//	ProductService productService;
//	ExpertationService expertationService;
	String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public TopicSpringThread(ZipHelper zipHelper, TopicWordHelper topicWordHelper, Long topicId,String sessionId,TopicService topicService, String id) {
		super();
		this.zipHelper = zipHelper;
		this.topicWordHelper = topicWordHelper;
		this.topicService = topicService;
		this.topicId = topicId;
		this.id = id;
		this.sessionId = sessionId;
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

		//业务逻辑获取数据

		TopicTextVO topicTextVO = topicService.topicTextVO(null, sessionId, topicId);

		String fname = FileUtil.replaceIllegalCharForFileName(topicTextVO.getBookname()+"-"+topicTextVO.getRealname());
		String dest = src + this.id;
		zipDownload.setId(this.id);
		zipDownload.setMaterialName(fname); // 由于历史原因 这里导出的bean 的名称 用materialName
		zipDownload.setState(0);
		zipDownload.setDetail("loading...");
		zipDownload.setCreateTime(DateUtil.getCurrentTime());
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);

		try{

			//获取
			if (ObjectUtil.notNull(topicTextVO)) {
				StringBuilder sb = new StringBuilder();
				sb.append(src);
				sb.append(this.id);
				sb.append(File.separator);
				/*sb.append(fname);
				sb.append(File.separator);*/
				//
				this.topicWordHelper.export(topicTextVO.getBookname(), sb.toString(), topicTextVO);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		new Thread(zipDownload).start();
		/*try {
			this.zipHelper.zip(dest + File.separator + fname, dest + File.separator, true, null);
		} catch (Exception e) {
			e.getMessage();
		}*/
		zipDownload.setState(1);
		zipDownload.setDetail("/word/download?id=" + this.id);
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);





		}





}
