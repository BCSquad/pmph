package com.bc.pmpheep.back.controller.writerpoint;

import com.bc.pmpheep.back.po.CmsManual;
import com.bc.pmpheep.back.po.WriterPointActivity;
import com.bc.pmpheep.back.util.CookiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.WriterPointService;
import com.bc.pmpheep.back.vo.WriterPointVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

import javax.servlet.http.HttpServletRequest;

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


	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "分页查询用户积分列表")
	@RequestMapping(value = "/listActivity", method = RequestMethod.GET)
	public ResponseBean listActivity(@RequestParam("pageSize") Integer pageSize,
							 @RequestParam("pageNumber") Integer pageNumber){
		PageParameter<WriterPointActivity> pageParameter=new PageParameter<WriterPointActivity>(pageNumber, pageSize);
		return new ResponseBean(writerPointService.getListWriterPointActivity(pageParameter));
	}

	/**
	 * 用户积分分页查询
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "分页查询用户积分列表")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ResponseBean listAll(){
		return new ResponseBean(writerPointService.getAllWriterPoint());
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增操作手册")
	@RequestMapping(value = "/ruleActivity/add", method = RequestMethod.POST)
	public ResponseBean addruleActivity(WriterPointActivity writerPointActivity, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		if(writerPointActivity.getId()==null){
			writerPointActivity.setStatus(true);
			return new ResponseBean(writerPointService.addWriterPointActivity(writerPointActivity, sessionId));
		}else{
			return  new ResponseBean(writerPointService.updateWriterPointActivity(writerPointActivity, sessionId));
		}

	}


}
