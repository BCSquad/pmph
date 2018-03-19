package com.bc.pmpheep.back.controller.writerpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.WriterPointLogService;
import com.bc.pmpheep.back.vo.WriterPointLogVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * @author mr
 * 2018-01-02
 */
@Controller
@RequestMapping(value = "/writerpointlog")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriterPointLogController {
	
	@Autowired
	WriterPointLogService writerPointLogService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "积分管理";
	
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "分页查询用户积分获取记录列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(@RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("userId") Long userId){
		PageParameter<WriterPointLogVO> pageParameter=new PageParameter<>(pageNumber, pageSize);
		WriterPointLogVO writerPointLogVO=new WriterPointLogVO();
		writerPointLogVO.setUserId(userId);
		pageParameter.setParameter(writerPointLogVO);
		return new ResponseBean(writerPointLogService.getListWriterPointLog(pageParameter));
	}
	
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "分页查询用户积分兑换记录列表")
	@RequestMapping(value = "/listExchange", method = RequestMethod.GET)
	public ResponseBean listExchange(@RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("userId") Long userId){
		PageParameter<WriterPointLogVO> pageParameter=new PageParameter<>(pageNumber, pageSize);
		WriterPointLogVO writerPointLogVO=new WriterPointLogVO();
		writerPointLogVO.setUserId(userId);
		pageParameter.setParameter(writerPointLogVO);
		return new ResponseBean(writerPointLogService.getListWriterPointLogExchange(pageParameter));
	}
	
	
}
