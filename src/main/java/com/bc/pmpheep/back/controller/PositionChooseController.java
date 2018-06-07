package com.bc.pmpheep.back.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bc.pmpheep.back.vo.TextbookDecVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.ExcelHelper;

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
	Logger logger = LoggerFactory.getLogger(PositionChooseController.class);
	private final String BUSSINESS_TYPE = "职位遴选";

	@Autowired
	private TextbookService textbookService;
	@Autowired
	private MaterialService materialService;
	@Resource
	ExcelHelper excelHelper;

	/**
	 * 功能描述： 初始化书籍职位列表
	 * 
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
	public ResponseBean list(@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "materialId", required = true) Long materialId,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "textBookIds", required = false) String textBookIds, // [1,2,3,4,5]
			@RequestParam(value = "bookName", required = false) String bookName, // [1,2,3,4,5]
			HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(textbookService.listBookPosition(pageNumber, pageSize, state, textBookIds, bookName,
				materialId, sessionId));
	}

	/**
	 * 功能描述： 初始化书籍职位列表时，同步查出所有页书籍主键
	 *
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
	@RequestMapping(value = "/listAllIdList", method = RequestMethod.GET)
	public ResponseBean listBookPosition_up_AllIdList(@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
							 @RequestParam(value = "pageSize", required = true) Integer pageSize,
							 @RequestParam(value = "materialId", required = true) Long materialId,
							 @RequestParam(value = "state", required = false) Integer state,
							 @RequestParam(value = "textBookIds", required = false) String textBookIds, // [1,2,3,4,5]
							 @RequestParam(value = "bookName", required = false) String bookName, // [1,2,3,4,5]
							 HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(textbookService.listBookPositionIds(pageNumber, pageSize, state, textBookIds, bookName,
				materialId, sessionId));
	}


	/**
	 * 功能描述：通过教材id 修改是否强制结束
	 * 
	 * @param materialId
	 * @param isForceEnd
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "强制结束该教材")
	@RequestMapping(value = "/updateMaterial", method = RequestMethod.PUT)
	public ResponseBean updateMaterial(Material material, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(materialService.updateMaterial(material, sessionId));
	}

	/**
	 * 功能描述：批量通过（名单确认）
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量通过（名单确认）")
	@RequestMapping(value = "/updateTextbook", method = RequestMethod.PUT)
	public ResponseBean updateTextbook(@RequestParam("ids") Long[] ids, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(textbookService.updateTextbooks(ids, sessionId));
	}

	/**
	 * 功能描述：批量结果公布（最终结果公布）
	 * 
	 * @param ids
	 * @return
	 * @throws Exception 
	 * @throws CheckedServiceException 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量结果公布（最终结果公布）")
	@RequestMapping(value = "/updateResult", method = RequestMethod.PUT)
	public ResponseBean updateResult(@RequestParam("ids") Long[] ids,@RequestParam("materialId") Long materialId,
			HttpServletRequest request) throws  Exception {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(textbookService.updateTextbookAndMaterial(ids, sessionId,materialId));
	}

	/**
	 * 分页查询该书籍下的已选主编和编委
	 * 
	 * @param textbookId
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询该书籍下的已选主编和编委")
	@RequestMapping(value = "/editorList", method = RequestMethod.GET)
	public ResponseBean editorList(@RequestParam("textbookId") Long textbookId,
			@RequestParam("pageSize") Integer pageSize,
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber) {
		PageParameter<TextbookDecVO> pageParameter = new PageParameter<>();
		TextbookDecVO textbookDecVO = new TextbookDecVO();
		textbookDecVO.setTextBookId(textbookId);
		pageParameter.setPageNumber(pageNumber);
		pageParameter.setPageSize(pageSize);
		pageParameter.setParameter(textbookDecVO);
		return new ResponseBean(textbookService.listEditorSelection(pageParameter));
	}

	/**
	 * 功能描述：获取教材下的所有书籍
	 * 
	 * @param materialId
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取教材下的所有书籍")
	@RequestMapping(value = "/getTextbookName", method = RequestMethod.GET)
	public ResponseBean getTextbookName(@RequestParam("materialId") Long materialId) {
		return new ResponseBean(textbookService.getTextbooknameList(materialId));
	}

}
