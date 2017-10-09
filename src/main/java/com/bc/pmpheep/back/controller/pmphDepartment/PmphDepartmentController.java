package com.bc.pmpheep.back.controller.pmphDepartment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * @author MrYang
 * @CreateDate 2017年9月26日 下午3:19:42
 *
 **/
@Controller
@RequestMapping(value = "/pmphDepartment")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PmphDepartmentController {

	@Autowired
	private PmphDepartmentService pmphDepartmentService;

	/**
	 * 根据父级id获取社内机构下级所有部门 parentId为null 获取整个社内部门树（根节点为0）
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:21:34
	 * @param parentId
	 */
	@RequestMapping(value = "/pmphdepartmenttree/{parentId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean getPmphDepartmentTree(Long parentId) {
		return new ResponseBean(pmphDepartmentService.listPmphDepartment(parentId));
	}

	/**
	 * 新增 pmphDepartment
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:28:24
	 * @param pmphDepartment
	 * @return 带主键的pmphDepartment
	 */
	@RequestMapping(value = "/add/pmphdepartment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBean addPmphDepartment(PmphDepartment pmphDepartment) {
		return new ResponseBean(pmphDepartmentService.addPmphDepartment(pmphDepartment));
	}

	/**
	 * 更新 pmphDepartment
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:36:41
	 * @param pmphDepartment
	 * @return 影响行数
	 */
	@RequestMapping(value = "/update/pmphdepartment", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseBean updatePmphDepartment(PmphDepartment pmphDepartment) {
		return new ResponseBean(pmphDepartmentService.updatePmphDepartment(pmphDepartment));
	}

	/**
	 * 删除该部门以及下属部门
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:38:14
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/pmphdepartmentbatch/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseBean deletePmphDepartmentBatch(Long id) {
		return new ResponseBean(pmphDepartmentService.deletePmphDepartmentBatch(id));
	}

}
