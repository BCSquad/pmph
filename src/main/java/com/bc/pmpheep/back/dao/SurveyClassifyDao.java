package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyClassify;
/**
 * SurveyClassify实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyClassifyDao {

	/**
	 * 新增一个SurveyClassify
	 * @author:tyc
     * @date:2017年12月20日下午16:43:11
	 * @param SurveyClassify
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addSurveyClassify(SurveyClassify surveyClassify);

	/**
	 * 删除SurveyClassify通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:43:11
	 * @param SurveyClassify
	 * @return 影响行数
	 */
	Integer deleteSurveyClassifyById(Long id);

	/**
	 * 更新一个 SurveyClassify通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:43:11
	 * @param SurveyClassify
	 * @return 影响行数
	 */
	Integer updateSurveyClassify(SurveyClassify surveyClassify);
	
	/**
	 * 查找SurveyClassify通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:43:11
	 * @param SurveyClassify
	 * @return 影响行数
	 */
	SurveyClassify selectSurveyClassifyById(Long id);
}
