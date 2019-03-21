package com.bc.pmpheep.back.controller.orgType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
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
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "机构类型";

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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取机构类型")
	@ResponseBody
	public ResponseBean orgtype(String typeName) {
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增机构类型")
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除机构类型")
	@RequestMapping(value = "/delete/orgtype", method = RequestMethod.DELETE)
	public ResponseBean deleteOrgType(Long id) {
		return new ResponseBean(orgTypeService.deleteOrgTypeById(id));
	}
}
