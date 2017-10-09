package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.back.service.WriterApplicationService;
import com.bc.pmpheep.back.vo.ApplicationVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * @author MrYang
 * @CreateDate 2017年9月30日 下午4:32:17
 *
 **/
@Controller
@RequestMapping(value = "/writerApplication")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriterApplicationController {

	@Autowired
	private WriterApplicationService writerApplicationService;

	/**
	 * 新增教材申报
	 * 
	 * @author Mryang
	 * @createDate 2017年9月30日 下午5:20:28
	 * @param applicationVO
	 * @return
	 */
	@RequestMapping(value = "/add/application", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBean addApplication(ApplicationVO applicationVO) {
		return new ResponseBean(writerApplicationService.addApplication(applicationVO));
	}

}
