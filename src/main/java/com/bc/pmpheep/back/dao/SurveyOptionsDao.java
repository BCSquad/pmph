package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyOptions;
/**
 * SurveyOptions实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyOptionsDao {

	/**
	 * 新增一个SurveyOptions
	 * @author:tyc
     * @date:2017年12月20日下午16:45:57
	 * @param SurveyOptions
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addSurveyOptions(SurveyOptions surveyOptions);

	/**
	 * 删除SurveyOptions通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:45:57
	 * @param SurveyOptions
	 * @return 影响行数
	 */
	Integer deleteSurveyOptionsById(Long id);

	/**
	 * 更新一个 SurveyOptions通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:45:57
	 * @param SurveyOptions
	 * @return 影响行数
	 */
	Integer updateSurveyOptions(SurveyOptions surveyOptions);
	
	/**
	 * 查找SurveyOptions通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:45:57
	 * @param SurveyOptions
	 * @return 影响行数
	 */
	SurveyOptions selectSurveyOptionsById(Long id);
}
