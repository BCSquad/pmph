package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.controller.bean.ResponseBean;

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
}
