package com.bc.pmpheep.back.controller;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.SearchKeyword;
import com.bc.pmpheep.back.service.SearchKeywordService;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/SearchKeyword")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SearchKeywordController {
	@Autowired
	SearchKeywordService SearchKeywordService;

	// 当前业务类型
	private static final String BUSSINESS_TYPE = "搜索关键词";

	/**
	 * 
	 * 
	 * 功能描述：添加一个搜索关键词
	 *
	 * @param SearchKeyword
	 * @return
	 *
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "添加一个搜索关键词")
	@ResponseBody
	public ResponseBean add(SearchKeyword SearchKeyword) {
		return new ResponseBean(SearchKeywordService.add(SearchKeyword));
	}

	/**
	 * 
	 * 
	 * 功能描述：修改搜索关键词
	 *
	 * @param SearchKeyword
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改搜索关键词")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseBean update(SearchKeyword SearchKeyword) {
		return new ResponseBean(SearchKeywordService.update(SearchKeyword));
	}

	/**
	 * 
	 * 
	 * 功能描述：删除搜索关键词
	 *
	 * @param ids
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除搜索关键词")
	@RequestMapping(value = "/isDeleted", method = RequestMethod.DELETE)
	public ResponseBean isDeleted(Long[] ids) {
		return new ResponseBean(SearchKeywordService.deletedIsDeleted(ids));
	}
	
	
	/**
	 * 
	 * 
	 * 功能描述：分页查询搜索关键词
	 *
	 * @param word
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "分页查询搜索关键词")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(String word, Integer pageSize, Integer pageNumber) {
		PageParameter<SearchKeyword> pageParameter = new PageParameter<>(pageNumber, pageSize);
		SearchKeyword SearchKeyword = new SearchKeyword();
		SearchKeyword.setWord(word);
		pageParameter.setParameter(SearchKeyword);
		return new ResponseBean(SearchKeywordService.list(pageParameter));
	}
}
