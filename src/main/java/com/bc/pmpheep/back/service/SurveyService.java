package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问卷业务层接口
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-21
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public interface SurveyService {
	/**
     * 新增一个Survey
     * 
     * @author:tyc
     * @date:2017年12月22日下午15:51:35
     * @param Survey 实体对象
     * @return 影响行数
     */
	Survey addSurvey(Survey survey) throws CheckedServiceException;
	
	/**
     * 更新一个 Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:00:11
     * @param Survey
     * @return 影响行数
     */
    Integer updateSurvey(Survey survey) throws CheckedServiceException;

    /**
     * 查找Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:07:54
     * @param Survey
     * @return 影响行数
     */
    Survey getSurveyById(Long id) throws CheckedServiceException;
}
