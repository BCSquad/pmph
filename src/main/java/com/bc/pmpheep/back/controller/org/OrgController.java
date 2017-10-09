package com.bc.pmpheep.back.controller.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * @author MrYang
 * @CreateDate 2017年9月26日 上午9:44:18
 *
 **/
@Controller
@RequestMapping(value = "/org")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrgController {

	@Autowired
	private OrgService orgService;

	/**
	 * 分页查询org
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 上午9:46:19
	 * @param orgVO
	 * @return 分页数据集
	 */
	@RequestMapping(value = "/list/org", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean listOrg(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize") Integer pageSize, OrgVO orgVO) {
		PageParameter<OrgVO> pageParameter = new PageParameter<OrgVO>(pageNumber, pageSize, orgVO);
		return new ResponseBean(orgService.listOrg(pageParameter));
	}

	/**
	 * 新增一个org
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午1:48:59
	 * @param org
	 * @return 新增后的org
	 */
	@RequestMapping(value = "/add/org", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBean addOrg(Org org) {
		return new ResponseBean(orgService.addOrg(org));
	}

	/**
	 * 更新 org
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午1:57:33
	 * @param org
	 * @return 更新影响的行数
	 */
	@RequestMapping(value = "/update/org", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseBean updateOrg(Org org) {
		return new ResponseBean(orgService.updateOrg(org));
	}

	/**
	 * 通过id删除 一个org
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午1:58:42
	 * @param id
	 * @return 影响的行数
	 */
	@RequestMapping(value = "/delete/org/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseBean deleteOrgById(Long id) {
		return new ResponseBean(orgService.deleteOrgById(id));
	}

	/**
	 * 
	 * 
	 * 功能描述：在新增或者修改用户页面查询机构
	 *
	 * @param orgName
	 *            机构名称
	 * @return 模糊查询出来的机构
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/list/org/{orgName}", method = RequestMethod.GET)
	public ResponseBean listOrgByOrgName(@RequestParam("orgName") String orgName) {
		return new ResponseBean(orgService.listOrgByOrgName(orgName));
	}

}
