package com.bc.pmpheep.general.runnable;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.dao.ExpertationDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.ClicWordHelper;
import com.bc.pmpheep.utils.WordHelper;
import com.bc.pmpheep.utils.ZipHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClicSpringThread implements Runnable {

	Logger logger = LoggerFactory.getLogger(ClicSpringThread.class);
	ZipHelper zipHelper;
	ClicWordHelper clicWordHelper;
	ExpertationDao expertationDao;
	ExpertationVO expertationVO;
	ProductService productService;
	ExpertationService expertationService;
	String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public ClicSpringThread(ZipHelper zipHelper, ClicWordHelper clicWordHelper, ExpertationDao expertationDao, ExpertationService expertationService, ProductService productService, ExpertationVO expertationVO, String id) {
		super();
		this.zipHelper = zipHelper;
		this.clicWordHelper = clicWordHelper;
		this.expertationDao = expertationDao;
		this.expertationVO = expertationVO;
		this.productService = productService;
		this.expertationService = expertationService;
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

		//业务逻辑获取数据
		PageParameter pageParameter = new PageParameter();
		pageParameter.setStart(null);
		pageParameter.setParameter(expertationVO);
        //根据产品id 获取对应的产品 用于自定义产品的选项
		ProductVO productVO = productService.getProductById(expertationVO.getProduct_id());
		StringBuilder str = new StringBuilder();
		str.append(productVO.getIs_edu_exp_used() ? "1" : "0"); //学习经历
		str.append(productVO.getIs_work_exp_used() ? "1" : "0"); // 工作经历
		str.append(productVO.getIs_acade_used() ? "1" : "0"); //学术兼职
		str.append(productVO.getIs_pmph_textbook_used() ? "1" : "0"); //人卫社教材编写情况
		str.append(productVO.getIs_monograph_used() ? "1" : "0"); //图书出版情况
		str.append(productVO.getIs_edit_book_used() ? "1" : "0"); //主编或参编图书情况
		str.append(productVO.getIs_article_published_used() ? "1" : "0"); //文章发表情况
		str.append(productVO.getIs_profession_award_used() ? "1" : "0");  //本专业获奖情况
		str.append(productVO.getIs_subject_type_used() ? "1" : "0"); //学科分类
		str.append(productVO.getIs_content_type_used() ? "1" : "0"); //内容分类
		str.append(productVO.getIs_profession_type_used() ? "1" : "0"); //申报专业
		str.append(productVO.getIs_profession_type_used() ? "1" : "0"); //申报专业

		String dest = src + this.id;
		zipDownload.setId(this.id);
		zipDownload.setMaterialName(productVO.getProduct_name()); // 由于历史原因 这里导出的bean 的名称 用materialName
		zipDownload.setState(0);
		zipDownload.setDetail("loading...");
		zipDownload.setCreateTime(DateUtil.getCurrentTime());
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);

		List<ExpertationVO> list = new ArrayList<>();//产品申报列表
		List<ExpertationVO> listDetail = new ArrayList<>(); //产品申报详情
		try{
			list = expertationDao.queryExpertation(pageParameter);  // 这里service层写方法 获取到一个产品的所有申报列表 及详情
 			List<Long> queryExpertationIdList = new ArrayList();
			for(ExpertationVO expertationVO:list){
				queryExpertationIdList.add(expertationVO.getId());
			}
			listDetail = expertationService.getExpertationByIdList(queryExpertationIdList);
				//ExpertationVO experationV= expertationService.getExpertationById(expertationVO.getId());

				//listDetail.add(experationV);
			//获取
			if (!CollectionUtil.isEmpty(listDetail)) {
				StringBuilder sb = new StringBuilder();
				sb.append(src);
				sb.append(this.id);
				sb.append(File.separator);
				sb.append(FileUtil.replaceIllegalCharForFileName(productVO.getProduct_name()));
				sb.append(File.separator);
				// str 勾选的编码 sb word的名称 产品的名字和数据list
				this.clicWordHelper.export(productVO.getProduct_name(), sb.toString(), listDetail, str.toString()
				);
				list.removeAll(list);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		new Thread(zipDownload).start();
		try {
			this.zipHelper.zip(dest + File.separator + productVO.getProduct_name(), dest + File.separator, true, null);
		} catch (Exception e) {
			e.getMessage();
		}
		zipDownload.setState(1);
		zipDownload.setDetail("/zip/download?id=" + this.id);
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);
	}



}
