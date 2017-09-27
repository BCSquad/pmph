/**
 * 
 */
package com.bc.pmpheep.back.controller.pmphUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.mysql.fabric.Response;

/**
 * <p>Description:社内用户控制层<p>
 * @author lyc
 * @date 2017年9月26日 下午6:07:55
 */
@Controller
@RequestMapping(value = "/pmphUser")
@SuppressWarnings({"rawtypes","unchecked"})
public class PmphUserController {

	@Autowired
	private PmphUserService pmphUserService;
	
	/**
	 * 
	 * Description:分页查询pmphUser
	 * @author:lyc
	 * @date:2017年9月26日下午6:12:14
	 * @Param: PmphUserManagerVO
	 * @Return:查询结果集
	 */
	@RequestMapping(value = "/getListPmphUser")
	@ResponseBody
	public ResponseBean getListPmphUser(PmphUserManagerVO pmphUserManagerVO,Page page){
		page.setParameter(pmphUserManagerVO);
		return new ResponseBean(pmphUserService.getListPmphUser(page));
	}
	
	/**
	 * 
	 * Description:根据id获取用户对象
	 * @author:lyc
	 * @date:2017年9月27日上午9:45:52
	 * @Param:id
	 * @Return:PmphUser
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public ResponseBean getPmphUserById(Long id){
		return new ResponseBean(pmphUserService.get(id));
	}
	
	
	/**
	 * 
	 * Description:根据用户名获取用户对象（用于登录）
	 * @author:lyc
	 * @date:2017年9月27日上午9:47:45
	 * @Param:username
	 * @Return:PmphUser
	 */
	@RequestMapping(value = "/getByUsername")
	@ResponseBody
	public ResponseBean getPmphUserByUsername(String username){
		return new ResponseBean(pmphUserService.getByUsername(username));
	}
	
	/**
	 * 
	 * Description:根据用户名或真实姓名模糊查询社内用户
	 * @author:lyc
	 * @date:2017年9月27日上午10:12:38
	 * @Param:name 模糊查询参数
	 * @Return:Page
	 */
	@RequestMapping(value = "/pmphUserFuzzyLookup")
	@ResponseBody
	public ResponseBean getListByUsernameAndRealname(String name, int number,int size){
		return new ResponseBean(pmphUserService.getListByUsernameAndRealname(name, number, size));
	}
	
	
}
