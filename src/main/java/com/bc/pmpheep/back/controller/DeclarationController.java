package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.vo.BookUserCommentVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 *@author MrYang 
 *@CreateDate 2017年11月23日 上午11:24:35
 *
 **/
@Controller
@RequestMapping(value = "/declaration")
@SuppressWarnings("all")
public class DeclarationController {
	
	private final String BUSSINESS_TYPE ="申报表审核";
	
	@Autowired
	private DeclarationService declarationService;
	
	/**
	 * 符合条件的申报表审核分页数据
	 * @author Mryang
	 * @createDate 2017年11月23日 上午10:37:36
	 * @param pageNumber 页码
	 * @param pageSize   页面大小
	 * @param materialId 教材id
	 * @param textBookids条件查询的书籍ids [1,2,3,...]
	 * @param realname   条件查询的账号或者姓名
	 * @param position   条件查询 职务
	 * @param title      条件查询 职称
	 * @param orgName    条件查询 工作单位
	 * @param unitName   条件查询 申报单位
	 * @param positionType 条件查询 申报职位 ;null全部  1主编 2副主编 3编委 
	 * @param onlineProgress 1待审核 3已经审核 
	 * @param offlineProgress 0 未  2 收到
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "加载申报列表")
	@RequestMapping(value = "/list/declaration", method = RequestMethod.GET)
	public ResponseBean declaration(
			@RequestParam(value="pageNumber",required=true)		 Integer pageNumber, 
			@RequestParam(value="pageSize",required=true)		 Integer pageSize,
			@RequestParam(value="materialId",required=true)		 Long materialId, 
			@RequestParam(value="textBookids",required=false)	 String textBookids ,
			@RequestParam(value="realname",required=false)		 String realname,
			@RequestParam(value="position",required=false)		 String position,
			@RequestParam(value="title",required=false)			 String title,
			@RequestParam(value="orgName",required=false)		 String orgName,
			@RequestParam(value="unitName",required=false)		 String unitName,
			@RequestParam(value="positionType",required=false)   Integer positionType,
			@RequestParam(value="onlineProgress",required=false) Integer onlineProgress,
			@RequestParam(value="offlineProgress",required=false)Integer offlineProgress
			) {
		return new ResponseBean(declarationService.pageDeclaration(
				pageNumber,
				pageSize, 
				materialId, 
				textBookids,
				realname, 
				position, 
				title, 
				orgName, 
				unitName, 
				positionType, 
				onlineProgress, 
				offlineProgress));
	}
}
