/**
 * 
 */
package com.bc.pmpheep.back.controller.writerUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * <p>
 * Description:后台作家用户控制层
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 下午4:23:56
 */
@Controller
@RequestMapping(value = "/writerUser")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriterUserController {

	@Autowired
	private WriterUserService writerUserService;

	/**
	 * 
	 * Description:后台分页查询作家用户
	 * 
	 * @author:lyc
	 * @date:2017年9月26日
	 * @Param:WriterUserManagerVO
	 * @Return:分页数据集
	 */
	@RequestMapping(value = "/getListWriterUser")
	@ResponseBody
	public ResponseBean getListWriterUser(
			WriterUserManagerVO writerUserManagerVO, Page page) {
		page.setParameter(writerUserManagerVO);
		return new ResponseBean(writerUserService.getListWriterUser(page));

	}

	/**
	 * 
	 * Description:新增一个作家用户
	 * 
	 * @author:lyc
	 * @date:2017年9月26日下午4:46:16
	 * @Param:WriterUser
	 * @Return:新增后的WriterUser
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public ResponseBean addWriterUser(WriterUser writerUser) {
		return new ResponseBean(writerUserService.add(writerUser));
	}

	/**
	 * Description:更新单个用户信息
	 * 
	 * @author:lyc
	 * @date:2017年9月26日下午4:56:02
	 * @Param: WriterUser
	 * @Return:更新后的WriterUser
	 */
	@RequestMapping(value = "updateOneWriterUser")
	@ResponseBody
	public ResponseBean updateWriterUser(WriterUser writerUser) {
		return new ResponseBean(writerUserService.update(writerUser));
	}
	
	/**
	 * 
	 * Description:根据user_id删除作家用户数据
	 * @author:lyc
	 * @date:2017年9月26日下午5:03:48
	 * @Param:id
	 * @Return:ResponseBean
	 */
	@RequestMapping(value="/deleteWriterUser")
	@ResponseBody
	public ResponseBean deleteWriterUser(Long id){
		return null;
	}
}
