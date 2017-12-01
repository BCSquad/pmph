/**
 * 
 */
package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.DecPositionService;
import com.bc.pmpheep.back.vo.DeclarationSituationBookResultVO;
import com.bc.pmpheep.back.vo.DeclarationSituationSchoolResultVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * <p>Title:DecPositionController<p>
 * <p>Description:申报结果统计界面<p>
 * @author lyc
 * @date 2017年12月1日 下午2:52:57
 */
@Controller
@RequestMapping(value = "/decPosition")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DecPositionController {
	
	private final String BUSSINESS_TYPE = "申报结果统计";
	
	@Autowired
	private DecPositionService decPositionService;
	
	/**
	 * 
	 * Description:加载学校申报情况统计结果
	 * @author:lyc
	 * @date:2017年12月1日下午3:03:36
	 * @param 
	 * @return ResponseBean
	 */
	@ResponseBody
	@RequestMapping(value="/list/schoolResults",method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark="加载学校申报情况统计结果")
    public ResponseBean schoolResults(Integer pageSize, Integer pageNumber, Long materialId, String schoolName){
		PageParameter<DeclarationSituationSchoolResultVO> pageParameter = 
				new PageParameter<>(pageNumber, pageSize);
		DeclarationSituationSchoolResultVO declarationSituationSchoolResultVO = 
				new DeclarationSituationSchoolResultVO();
		declarationSituationSchoolResultVO.setMaterialId(materialId);
		declarationSituationSchoolResultVO.setSchoolName(schoolName);
		pageParameter.setParameter(declarationSituationSchoolResultVO);
		return new ResponseBean(decPositionService.listDeclarationSituationSchoolResultVOs(pageParameter));
	}
	
	/**
	 * 
	 * Description:获取总体统计情况
	 * @author:lyc
	 * @date:2017年12月1日下午3:07:15
	 * @param 
	 * @return ResponseBean
	 */
	@ResponseBody
	@RequestMapping(value ="/result/count", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "加载总体统计情况")
	public ResponseBean count(Long materialId){
		return new ResponseBean(decPositionService.getDeclarationCountVO(materialId));
	}
	
	/**
	 * 
	 * Description:加载书本申报情况统计结果
	 * @author:lyc
	 * @date:2017年12月1日下午5:44:16
	 * @param 
	 * @return ResponseBean
	 */
	@ResponseBody
	@RequestMapping(value="/list/bookResults",method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE,logRemark = "加载书本申报情况统计结果")
	public ResponseBean bookResults(Integer pageSize, Integer pageNumber, Long materialId, String bookName){
		PageParameter<DeclarationSituationBookResultVO> pageParameter = 
				new PageParameter<>(pageNumber, pageSize);
		DeclarationSituationBookResultVO declarationSituationBookResultVO = 
				new DeclarationSituationBookResultVO();
		declarationSituationBookResultVO.setMaterialId(materialId);
		declarationSituationBookResultVO.setBookName(bookName);
		pageParameter.setParameter(declarationSituationBookResultVO);
		return new ResponseBean(decPositionService.listDeclarationSituationBookResultVOs(pageParameter));
	}
}
