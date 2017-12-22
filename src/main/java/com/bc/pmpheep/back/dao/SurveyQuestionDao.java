package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.vo.SurveyQuestionOptionCategoryVO;

/**
 * SurveyQuestion问题实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyQuestionDao {

    /**
     * 新增一个SurveyQuestion
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:47:44
     * @param SurveyQuestion 实体对象
     * @return 影响行数
     */
    Integer addSurveyQuestion(SurveyQuestion surveyQuestion);

    /**
     * 逻辑删除SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:47:44
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer deleteSurveyQuestionById(Long id);

    /**
     * 更新一个 SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:47:44
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer updateSurveyQuestion(SurveyQuestion surveyQuestion);

    /**
     * 查找SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:47:44
     * @param SurveyQuestion
     * @return 影响行数
     */
    SurveyQuestion getSurveyQuestionById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：根据问题id查询问题选项
     * 使用示范：
     *
     * @param id Question主键ID
     * @return
     * </pre>
     */
    /**
     * 
     * <pre>
     * 功能描述：根据问题id/问题类型Id查询问题选项
     * 使用示范：
     *
     * @param questionId Question主键ID
     * @param categoryId Question_Category主键ID
     * @return
     * </pre>
     */
    List<SurveyQuestionOptionCategoryVO> getQuestionOptionByQuestionIdOrCategoryId(
    @Param("questionId") Long questionId, @Param("categoryId") Long categoryId);
}
