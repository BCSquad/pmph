package com.bc.pmpheep.back.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.back.vo.BookListVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *@author MrYang 
 *@CreateDate 2017年11月23日 下午3:39:31
 *
 **/
@Controller
@RequestMapping(value = "/textBook")
@SuppressWarnings("all")
public class TextBookController {
	
	private final String BUSSINESS_TYPE ="教材书籍";
	
	@Autowired
	private TextbookService textbookService;

    /**
     * 
     * @introduction 
     * @author Mryang
     * @createDate 2017年11月23日 下午3:43:46
     * @param materialId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "加载某本教材下面的书籍")
    public ResponseBean list(@RequestParam(name = "materialId") Long materialId) {
    	return new ResponseBean(textbookService.getTextbookByMaterialId(materialId));
    }
    
    /**
     * 
     * Description:保存教材书籍
     * @author:lyc
     * @date:2017年11月26日下午9:34:35
     * @param 
     * @return ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/add/textbook", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存或修改新增教材书籍")
    public ResponseBean textbook( BookListVO bookListVO, HttpServletRequest request){
    	String sessionId = CookiesUtil.getSessionId(request);
    	return new ResponseBean(textbookService.addOrUpdateTextBookList(bookListVO, sessionId));
    }
    
    /**
     * 
     * Description:获取教材书籍列表
     * @author:lyc
     * @date:2017年11月26日下午9:39:30
     * @param 
     * @return ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/list/textbooks", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "加载教材书籍列表")
    public ResponseBean textbooks(@RequestParam(name = "materialId") Long materialId){
    	return new ResponseBean(textbookService.getBookListVO(materialId));
    }
    
    /**
     * 
     * Description:通过Excel文档批量导入教材书籍
     * @author:lyc
     * @date:2018年1月22日下午5:45:14
     * @param 
     * @return ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/import/excel", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "通过Excel文档批量导入教材书籍")
    public ResponseBean excel(@RequestParam(name = "file") MultipartFile file){
    	try {
			return new ResponseBean(textbookService.importExcel(file));
		} catch (CheckedServiceException e) {
			return new ResponseBean(e);
		} catch (IOException ex) {
			return new ResponseBean(ex);
		}
    }
    
    /**
     * 
     * Description:设置选题号页面获取教材书籍列表
     * @author:lyc
     * @date:2017年11月27日上午10:19:15
     * @param 
     * @return ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/list/topics", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取设置选题号页面教材书籍列表")
    public ResponseBean topics(@RequestParam(name = "materialId") Long materialId){
    	return new ResponseBean(textbookService.listTopicNumber(materialId));
    }
    
    /**
     * 
     * Description:设置教材书籍选题号
     * @author:lyc
     * @date:2017年11月27日上午10:29:20
     * @param 
     * @return ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/add/topic", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "设置教材书籍选题号")
    public ResponseBean topic(@RequestParam(name = "topicTextbooks") String topicTextbooks){
    	return new ResponseBean(textbookService.addTopicNumber(topicTextbooks));
    }
    
    /**
     * 
     * Description:设置选题号页面导入Excel文档
     * @author:lyc
     * @date:2018年1月22日下午5:47:04
     * @param 
     * @return ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/import/topicExcel", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "设置选题号页面导入Excel文档")
    public ResponseBean topicExcel(@RequestParam(name = "file") MultipartFile file){
    	try {
			return new ResponseBean(textbookService.importTopicExcel(file));
		} catch (CheckedServiceException e) {
			return new ResponseBean(e);
		} catch (IOException ex) {
			return new ResponseBean(ex);
		}
    }
    
    /**
     * 
     * 分配策划编辑
     * @author:mr
     * @date:2017年11月27日下午17:20:20
     * @param 
     * @return ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/updateEditor", method = RequestMethod.PUT)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "分配策划编辑")
    public ResponseBean updateEditor(Textbook textbook){
    	return new ResponseBean(textbookService.updateTextbook(textbook));
    }
}
