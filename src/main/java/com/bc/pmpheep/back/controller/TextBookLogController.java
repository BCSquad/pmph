package com.bc.pmpheep.back.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.TextbookLogService;
import com.bc.pmpheep.back.vo.TextbookLogVO;
import com.bc.pmpheep.controller.bean.ResponseBean;


/**
 *@author MrYang 
 *@CreateDate 2017年11月23日 下午3:39:31
 *
 **/
@Controller
@RequestMapping(value = "/textBookLog")
public class TextBookLogController {
	
	private final String BUSSINESS_TYPE ="教材书籍日志";
	
	@Autowired
	private TextbookLogService textbookLogService;

    /**
     * 
     * @author Mryang
     * @createDate 2017年11月28日 下午2:24:39
     * @param textbookId
     * @param pageSize
     * @param pageNumber
     * @param updaterName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "加载教材书籍的日志")
    public ResponseBean<PageResult<TextbookLogVO>> list(
    		@RequestParam(name = "textbookId",required = true ) Long textbookId,
    		@RequestParam(name = "pageSize" ,required = false)   Integer pageSize,
    		@RequestParam(name = "pageNumber" ,required = false)  Integer pageNumber,
    		@RequestParam(name = "updaterName" ,required = false) String updaterName
    						) {
    	return new ResponseBean<PageResult<TextbookLogVO>>(
    			textbookLogService.listTextbookLogByTextBookId(textbookId, pageSize, pageNumber, updaterName)
    			);
    }
    
}
