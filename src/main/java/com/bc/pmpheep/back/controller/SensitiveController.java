package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Sensitive;
import com.bc.pmpheep.back.service.SensitiveService;
import com.bc.pmpheep.controller.bean.ResponseBean;

@Controller
@RequestMapping(value = "/sensitive")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SensitiveController {
	@Autowired
	SensitiveService sensitiveService;

	// 当前业务类型
	private static final String BUSSINESS_TYPE = "敏感词";

	/**
	 * 
	 * 
	 * 功能描述：添加一个敏感词
	 *
	 * @param sensitive
	 * @return
	 *
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "添加一个敏感词")
	@ResponseBody
	public ResponseBean add(Sensitive sensitive) {
		return new ResponseBean(sensitiveService.add(sensitive));
	}

	/**
	 * 
	 * 
	 * 功能描述：修改敏感词
	 *
	 * @param sensitive
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改敏感词")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseBean update(Sensitive sensitive) {
		return new ResponseBean(sensitiveService.update(sensitive));
	}

	/**
	 * 
	 * 
	 * 功能描述：逻辑删除敏感词
	 *
	 * @param ids
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "逻辑删除敏感词")
	@RequestMapping(value = "/isDeleted", method = RequestMethod.PUT)
	public ResponseBean isDeleted(Long[] ids) {
		return new ResponseBean(sensitiveService.updateIsDeleted(ids));
	}

	/**
	 * 
	 * 
	 * 功能描述：分页查询敏感词
	 *
	 * @param word
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "分页查询敏感词")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(String word, Integer pageSize, Integer pageNumber) {
		PageParameter<Sensitive> pageParameter = new PageParameter<>(pageNumber, pageSize);
		Sensitive sensitive = new Sensitive();
		sensitive.setWord(word);
		pageParameter.setParameter(sensitive);
		return new ResponseBean(sensitiveService.list(pageParameter));
	}
}
