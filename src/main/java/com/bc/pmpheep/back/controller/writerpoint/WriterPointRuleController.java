package com.bc.pmpheep.back.controller.writerpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.back.service.WriterPointRuleService;
import com.bc.pmpheep.back.service.WriterPointService;
import com.bc.pmpheep.back.vo.WriterPointRuleVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 积分管理页面积分规则控制层
 * @author mr
 * 2017-12-29
 */
@Controller
@RequestMapping(value = "/writerPoint")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriterPointRuleController {
	@Autowired
	WriterPointRuleService writerPointRuleService;
	
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "积分管理";
	
	
	/**
	 * 分页查询积分规则列表
	 * @param pageSize
	 * @param pageNumber
	 * @param ruleCode
	 * @param ruleName
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "分页查询积分规则列表")
	@RequestMapping(value = "/pointrule/list", method = RequestMethod.GET)
	public ResponseBean list(@RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("ruleCode") String ruleCode,
			@RequestParam("ruleName") String ruleName) {
		PageParameter<WriterPointRuleVO> pageParameter=new PageParameter<WriterPointRuleVO>(pageNumber, pageSize);
		WriterPointRuleVO writerPointRuleVO=new WriterPointRuleVO();
		writerPointRuleVO.setRuleName(ruleName);
		writerPointRuleVO.setRuleCode(ruleCode);
		pageParameter.setParameter(writerPointRuleVO);
		return new ResponseBean(writerPointRuleService.getListWriterPointRule(pageParameter));
	}
	
	/**
	 * 分页查询兑换规则列表
	 * @param pageSize
	 * @param pageNumber
	 * @param ruleCode
	 * @param ruleName
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "分页查询兑换规则列表")
	@RequestMapping(value = "/exchangeList", method = RequestMethod.GET)
	public ResponseBean exchangeList(
			@RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("ruleCode") String ruleCode,
			@RequestParam("ruleName") String ruleName){
		PageParameter<WriterPointRuleVO> pageParameter=new PageParameter<WriterPointRuleVO>(pageNumber, pageSize);
		WriterPointRuleVO writerPointRuleVO=new WriterPointRuleVO();
		writerPointRuleVO.setRuleName(ruleName);
		writerPointRuleVO.setRuleCode(ruleCode);
		pageParameter.setParameter(writerPointRuleVO);
		return new ResponseBean(writerPointRuleService.getlistWriterPointRulePoint(pageParameter));
	}
	
	/**
	 * 添加积分规则和兑换规则
	 * @param writerPointRule
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "增加积分规则和兑换规则")
	@RequestMapping(value = "/pointrule/add", method = RequestMethod.POST)
	public ResponseBean add(WriterPointRule writerPointRule){
		return new ResponseBean(writerPointRuleService.addWriterPointRule(writerPointRule));
	}
	
	/**
	 * 	修改积分或兑换规则
	 * @param writerPointRule
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "修改积分或兑换规则")
	@RequestMapping(value = "/pointrule/update", method = RequestMethod.PUT)
	public ResponseBean update(WriterPointRuleVO writerPointRule){
		return new ResponseBean(writerPointRuleService.updateWriterPointRule(writerPointRule));
	}
	
	/**
	 * 根据id删除积分或兑换规则
	 * @param id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "删除积分或兑换规则")
	@RequestMapping(value = "/pointrule/delete", method = RequestMethod.DELETE)
	public ResponseBean delete(Long id){
		return new ResponseBean(writerPointRuleService.deleteWriterPointRule(id));
	}
}
