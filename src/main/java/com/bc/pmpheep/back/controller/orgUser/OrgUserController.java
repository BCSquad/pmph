/**
 * 
 */
package com.bc.pmpheep.back.controller.orgUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.vo.OrgUserManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * <p>
 * Description:后台机构用户管理控制层
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 下午5:40:52
 */
@Controller
@RequestMapping(value = "/orgUser")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrgUserController {

	@Autowired
	private OrgUserService orgUserService;

	/**
	 * Description:分页查询机构用户
	 * 
	 * @author:lyc
	 * @date:2017年9月26日下午5:43:59
	 * @Param: OrgUserManagerVO
	 * @Return:分页数据集
	 */
	@RequestMapping(value="/getListOrgUser")
	@ResponseBody
	public ResponseBean getListOrgUser(OrgUserManagerVO orgUserManagerVO,
			Page page) {
		page.setParameter(orgUserManagerVO);
		return new ResponseBean(orgUserService.getListOrgUser(page));
	}
	
	/**
	 * 
	 * Description:新增一个机构用户
	 * @author:lyc
	 * @date:2017年9月26日下午5:50:20
	 * @Param: OrgUser
	 * @Return:新增的OrgUser
	 */
	@RequestMapping(value = "/addOrgUser")
	@ResponseBody
	public ResponseBean addOrgUser(OrgUser orgUser){
		return new ResponseBean(orgUserService.addOrgUser(orgUser));
	}
	
	/**
	 * 
	 * Description:更新机构用户信息
	 * @author:lyc
	 * @date:2017年9月26日下午5:53:44
	 * @Param: OrgUser
	 * @Return:更新影响的行数
	 */
	@RequestMapping(value = "/updateOrgUser")
	@ResponseBody
	public ResponseBean updateOrgUser(OrgUser orgUser){
		return new ResponseBean(orgUserService.updateOrgUser(orgUser));
	}
	
	/**
	 * 
	 * Description:通过id删除一个OrgUser
	 * @author:lyc
	 * @date:2017年9月26日下午5:56:38
	 * @Param: id
	 * @Return:影响的行数
	 */
	@RequestMapping(value = "/deleteOrgUserById")
	@ResponseBody
	public ResponseBean deleteOrgUserById(Long id){
		return new ResponseBean(orgUserService.deleteOrgUserById(id));
	}
	
	/**
	 * 
	 * Description:根据id查询一个机构用户信息
	 * @author:lyc
	 * @date:2017年9月26日下午5:58:48
	 * @Param: id
	 * @Return:OrgUser
	 */
	@RequestMapping(value = "/getOrgUserById")
	@ResponseBody
	public ResponseBean getOrgUserById(Long id){
		return new ResponseBean(orgUserService.getOrgUserById(id));
	}
}
