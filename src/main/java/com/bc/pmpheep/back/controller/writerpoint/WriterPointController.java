package com.bc.pmpheep.back.controller.writerpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.WriterPointService;
import com.bc.pmpheep.back.vo.WriterPointVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 积分管理用户积分控制层
 * @author mr
 * 2017-12-29
 */
@Controller
@RequestMapping(value = "/writerpoint")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriterPointController {
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "积分管理";
	
	@Autowired
	WriterPointService writerPointService;
	
	/**
	 * 用户积分分页查询
	 * @param pageSize
	 * @param pageNumber
	 * @param username
	 * @param realname
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "分页查询用户积分列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(@RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("username") String username,
			@RequestParam("realname") String realname){
		PageParameter<WriterPointVO> pageParameter=new PageParameter<WriterPointVO>(pageNumber, pageSize);
		WriterPointVO writerPoint=new WriterPointVO();
		writerPoint.setUsername(username);
		writerPoint.setRealname(realname);
		pageParameter.setParameter(writerPoint);
		return new ResponseBean(writerPointService.getListWriterPoint(pageParameter));
	}
}
