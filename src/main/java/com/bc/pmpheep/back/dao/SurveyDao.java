package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.Survey;

/**
 * Survey问卷实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyDao {

    /**
     * 新增一个Survey
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     * @param Survey 实体对象
     * @return 影响行数
     */
    Integer addSurvey(Survey survey);

    /**
     * 删除Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     * @param Survey
     * @return 影响行数
     */
    Integer deleteSurveyById(Long id);

    /**
     * 更新一个 Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     * @param Survey
     * @return 影响行数
     */
    Integer updateSurvey(Survey survey);

    /**
     * 查找Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     * @param Survey
     * @return 影响行数
     */
    Survey selectSurveyById(Long id);
}
