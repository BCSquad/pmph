package com.bc.pmpheep.back.controller.orgType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.po.OrgType;
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
	@RequestMapping(value = "/list/orgtype", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean listOrgTypeByTypeName(String typeName) {
		return new ResponseBean(orgTypeService.listOrgTypeByTypeName(typeName));
	}

	/**
	 * 
	 * 
	 * 功能描述：新增机构类型
	 *
	 * @param orgType
	 *            新增的机构类型名称 以及排序
	 * @return
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/add/orgtype", method = RequestMethod.POST)
	public ResponseBean addOrgType(OrgType orgType) {
		return new ResponseBean(orgTypeService.addOrgType(orgType));
	}

	/**
	 * 
	 * 
	 * 功能描述：删除机构类型
	 * 
	 * @param id
	 *            机构类型id
	 * @return
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/orgtype", method = RequestMethod.DELETE)
	public ResponseBean deleteOrgType(Long id) {
		return new ResponseBean(orgTypeService.deleteOrgTypeById(id));
	}
}
