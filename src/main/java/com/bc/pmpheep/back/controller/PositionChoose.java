package com.bc.pmpheep.back.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * 职位遴选页面数据接口
 * 
 * @author MrYang 
 * 
 * @CreateDate 2017年11月21日 上午9:55:09
 *
 **/
@Controller
@RequestMapping(value = "/position")
@SuppressWarnings("all")
public class PositionChoose {
	private final String BUSSINESS_TYPE ="";
	
	@Autowired
	private TextbookService textbookService;
	
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "加载书籍职位列表")
	@RequestMapping(value = "/list/", method = RequestMethod.GET)
	public ResponseBean bookPosition(@RequestParam("pageNumber") Integer pageNumber,
		    						 @RequestParam("pageSize") Integer pageSize,
		    						 @RequestParam("state") Integer state,
		    						 @RequestParam("textBookIds") String  textBookIds, //[1,2,3,4,5]
		    						 @RequestParam("materialId") Long  materialId,
		    						 HttpServletRequest request){
		 String sessionId = CookiesUtil.getSessionId(request);
		 return new ResponseBean(textbookService.listBookPosition(pageNumber, pageSize, state, textBookIds,materialId, sessionId));
	}
			
}
