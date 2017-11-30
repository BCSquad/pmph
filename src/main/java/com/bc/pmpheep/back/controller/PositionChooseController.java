package com.bc.pmpheep.back.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.OrgAndOrgUserVO;
import com.bc.pmpheep.back.vo.TextbookDecVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * 职位遴选页面数据接口
 * 
 * @author MrYang 
 * 
 * @CreateDate 2017年11月21日 上午9:55:09
 *
 **/
@Controller
@RequestMapping(value = "/position")
@SuppressWarnings("all")
public class PositionChooseController {
	
	private final String BUSSINESS_TYPE ="职位遴选";
	
	@Autowired
	private TextbookService textbookService;
	@Autowired
	private MaterialService materialService;
	
	/**
	 * 功能描述： 初始化书籍职位列表
	 * @param pageNumber
	 * @param pageSize
	 * @param state
	 * @param textBookIds
	 * @param materialId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "加载书籍职位列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean bookPosition(@RequestParam(value="pageNumber",required=true) Integer pageNumber,
		    						 @RequestParam(value="pageSize",required=true) Integer pageSize,
		    						 @RequestParam(value="materialId",required=true) Long  materialId,
		    						 @RequestParam(value="state",required=false) Integer state,
		    						 @RequestParam(value="textBookIds",required=false) String  textBookIds, //[1,2,3,4,5]
		    						 HttpServletRequest request){
		 String sessionId = CookiesUtil.getSessionId(request);
		 return new ResponseBean(textbookService.listBookPosition(pageNumber, pageSize, state, textBookIds,materialId, sessionId));
	}
	/**
	 * 功能描述：通过教材id 修改是否强制结束
	 * @param materialId
	 * @param isForceEnd
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "强制结束该教材")
	@RequestMapping(value = "/updateMaterial", method = RequestMethod.PUT)
	public ResponseBean updateMaterial(Material material){
		 return new ResponseBean(materialService.updateMaterial(material));
	}
	/**
	 * 功能描述：批量通过（名单确认）
	 * @param ids 
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量通过（名单确认）")
	@RequestMapping(value = "/updateTextbook", method = RequestMethod.PUT)
	public ResponseBean updateTextbook(@RequestParam("ids") Long[] ids){
		 return new ResponseBean(textbookService.updateTextbooks(ids));
	}
	/**
	 * 功能描述：批量结果公布（最终结果公布）
	 * @param ids 
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量结果公布（最终名单公布）")
	@RequestMapping(value = "/updateResult", method = RequestMethod.PUT)
	public ResponseBean updateResult(@RequestParam("ids") Long[] ids){
		 return new ResponseBean(textbookService.updateTextbookAndMaterial(ids));
	}
	
	/**
	 * 分页查询该书籍下的已选主编和编委
	 * @param textbookId
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询该书籍下的已选主编和编委")
	@RequestMapping(value = "/editorList", method = RequestMethod.GET)
	public ResponseBean editorList(@RequestParam("textbookId")Long  textbookId,
			@RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNumber") Integer pageNumber){
		PageParameter pageParameter = new PageParameter<>();
		TextbookDecVO textbookDecVO = new TextbookDecVO();
		textbookDecVO.setTextbookId(textbookId);
		pageParameter.setPageNumber(pageNumber);
		pageParameter.setPageSize(pageSize);
		pageParameter.setParameter(textbookDecVO);
		return new ResponseBean(textbookService.listEditorSelection(pageParameter));
	}
}
