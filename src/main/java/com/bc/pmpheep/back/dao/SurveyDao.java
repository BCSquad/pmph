package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.back.vo.SurveyVO;

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
     * 逻辑删除Survey通过主键id
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
    Survey getSurveyById(Long id);

    /**
     * 问卷表分页列表（同时查询分页数据和总条数）
     * 
     * @author:tyc
     * @date:2017年12月25日上午10:28:56
     * @param pageParameter
     * @return
     */
    List<SurveyVO> listSurvey(PageParameter<SurveyVO> pageParameter);

    /**
     * 
     * <pre>
     * 功能描述：查询问卷和问卷对应的类型
     * 使用示范：
     *
     * @param id 问卷id
     * @return SurveyVO 对象
     * </pre>
     */
    SurveyVO getSurveyAndSurveyTypeById(@Param("id") Long id);

    /**
     * 
     * <pre>
     * 功能描述：根据问卷ID查询问卷已发送对象
     * 使用示范：
     *
     * @param surveyId 问卷id
     * @return
     * </pre>
     */
    List<OrgVO> listSendOrgBySurveyId(Long surveyId);
}
