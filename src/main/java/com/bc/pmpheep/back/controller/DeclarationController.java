package com.bc.pmpheep.back.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.DecPositionService;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.DecPositionVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * @author MrYang
 * @CreateDate 2017年11月23日 上午11:24:35
 * 
 **/
@Controller
@RequestMapping(value = "/declaration")
@SuppressWarnings("all")
public class DeclarationController {

	private final String BUSSINESS_TYPE = "申报表审核";

	@Autowired
	private DeclarationService declarationService;
	@Autowired
	private DecPositionService decPositionService;

	/**
	 * 符合条件的申报表审核分页数据
	 * 
	 * @author Mryang
	 * @createDate 2017年11月23日 上午10:37:36
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页面大小
	 * @param materialId
	 *            教材id
	 * @param textBookids条件查询的书籍ids
	 *            [1,2,3,...]
	 * @param realname
	 *            条件查询的账号或者姓名
	 * @param position
	 *            条件查询 职务
	 * @param title
	 *            条件查询 职称
	 * @param orgName
	 *            条件查询 工作单位
	 * @param unitName
	 *            条件查询 申报单位
	 * @param positionType
	 *            条件查询 申报职位 ;null全部 1主编 2副主编 3编委
	 * @param onlineProgress
	 *            1待审核 3已经审核
	 * @param offlineProgress
	 *            0 未 2 收到
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "加载申报列表")
	@RequestMapping(value = "/list/declaration", method = RequestMethod.GET)
	public ResponseBean declaration(@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "materialId", required = true) Long materialId,
			@RequestParam(value = "textBookids", required = false) String textBookids,
			@RequestParam(value = "realname", required = false) String realname,
			@RequestParam(value = "position", required = false) String position,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "orgName", required = false) String orgName,
			@RequestParam(value = "unitName", required = false) String unitName,
			@RequestParam(value = "positionType", required = false) Integer positionType,
			@RequestParam(value = "onlineProgress", required = false) Integer onlineProgress,
			@RequestParam(value = "offlineProgress", required = false) Integer offlineProgress) {
		return new ResponseBean(declarationService.pageDeclaration(pageNumber, pageSize, materialId, textBookids,
				realname, position, title, orgName, unitName, positionType, onlineProgress, offlineProgress));
	}

	/**
	 * 确认收到纸质表
	 * 
	 * @author tyc
	 * @createDate 2017年11月24日 下午15:27:36
	 * @param id
	 * @param offlineProgress
	 * @param materialId
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "确认收到纸质表")
	@RequestMapping(value = "/list/declaration/confirmPaperList", method = RequestMethod.GET)
	public ResponseBean confirmPaperList(@RequestParam("id") Long id,
			@RequestParam("offlineProgress") Integer offlineProgress, @RequestParam("materialId") Long materialId)
			throws CheckedServiceException, IOException {
		return new ResponseBean(declarationService.confirmPaperList(id, offlineProgress, materialId));
	}

	/**
	 * 审核进度
	 * 
	 * @author tyc
	 * @createDate 2017年11月24日 下午16:37:36
	 * @param id
	 * @param onlineProgress
	 * @param materialId
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "审核进度")
	@RequestMapping(value = "/list/declaration/onlineProgress", method = RequestMethod.GET)
	public ResponseBean onlineProgress(@RequestParam("id") Long id,
			@RequestParam("onlineProgress") Integer onlineProgress, @RequestParam("materialId") Long materialId)
			throws CheckedServiceException, IOException {
		return new ResponseBean(declarationService.onlineProgress(id, onlineProgress, materialId));
	}

	/**
	 * 保存图书
	 * 
	 * @author tyc
	 * @createDate 2017年11月25日 晚上21:15:30
	 * @param decPositionVO
	 * @throws IOException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存图书")
	@RequestMapping(value = "/list/declaration/saveBooks", method = RequestMethod.POST)
	public ResponseBean saveBooks(DecPositionVO decPositionVO) throws IOException {
		return new ResponseBean(decPositionService.saveBooks(decPositionVO));
	}

	/**
	 * 显示专家信息
	 * 
	 * @author tyc
	 * @createDate 2017年11月25日 上午9:13:09
	 * @param declarationId
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "显示专家信息")
	@RequestMapping(value = "/list/declaration/exportExcel", method = RequestMethod.GET)
	public ResponseBean exportExcel(@RequestParam("declarationId") Long declarationId) throws IOException {
		return new ResponseBean(declarationService.exportExcel(declarationId));
	}

	/**
	 * 
	 * <pre>
	* 功能描述：教材申报-遴选主编/遴选编委
	* 使用示范：
	* 
	* &#64;param textbookId 书籍ID
	* &#64;param realName 申报人姓名
	* &#64;param presetPosition 申报职位
	* &#64;return
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询遴选主编/遴选编委")
	@RequestMapping(value = "/list/editor/selection", method = RequestMethod.GET)
	public ResponseBean selection(@RequestParam("textbookId") Long textbookId,
			@RequestParam("realName") String realName, @RequestParam("presetPosition") Integer presetPosition) {
		return new ResponseBean(decPositionService.listEditorSelection(textbookId, realName, presetPosition));
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：更新遴选主编/遴选编委
	 * 使用示范：
	 *
	 * &#64;param decPositions DecPosition对象集合
	 * &#64;return
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新遴选主编/遴选编委")
	@RequestMapping(value = "/editor/selection/update", method = RequestMethod.PUT)
	public ResponseBean update(@RequestParam("jsonDecPosition") String jsonDecPosition, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(decPositionService.updateDecPositionEditorSelection(jsonDecPosition, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：批量导出excel
	 *
	 * @param materialId
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "导出excel表格")
	@RequestMapping(value = "/down/excel", method = RequestMethod.GET)
	public void downExcel(Long materialId, String textBookids, String realname, String position, String title,
			String orgName, String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress,
			HttpServletResponse response) {
		try {
			declarationService.declarationExcel(materialId, textBookids, realname, position, title, orgName, unitName,
					positionType, onlineProgress, offlineProgress,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
