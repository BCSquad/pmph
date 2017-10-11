package com.bc.pmpheep.back.controller.orgType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * @author MrYang
 * @CreateDate 2017年9月26日 上午9:26:14
 *
 **/
@Controller
@RequestMapping(value = "/orgType")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrgTypeController {

	@Autowired
	private OrgTypeService orgTypeService;

	/**
	 * 获取所有机构类型 或者指定的机构类型
	 * 
	 * @author Mryang
	 * @createDate 2017年9月25日 下午3:00:28
	 * @param typeName
	 *            可以为null
	 * @return
	 */
	@RequestMapping(value = "/list/orgtype/{typeName}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean listOrgTypeByTypeName(@RequestParam("typeName") String typeName) {
		return new ResponseBean(orgTypeService.listOrgTypeByTypeName(typeName));
	}
}
