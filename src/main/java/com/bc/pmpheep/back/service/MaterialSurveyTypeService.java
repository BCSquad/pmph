package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyType;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.List;

/**
 * 
 * <pre>
 * 功能描述：问卷调查类型业务层接口
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
public interface MaterialSurveyTypeService {
    /**
     * 新增一个SurveyType
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:16:45
     * @param SurveyType 实体对象
     * @return 影响行数
     */
    SurveyType addSurveyType(SurveyType surveyType) throws CheckedServiceException;

    /**
     * 删除SurveyType通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:19:22
     * @param SurveyType
     * @return 影响行数
     */
    Integer deleteSurveyTypeById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 SurveyType通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:25:07
     * @param SurveyType
     * @return 影响行数
     */
    Integer updateSurveyType(SurveyType surveyType) throws CheckedServiceException;

    /**
     * 查找SurveyType通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:32:58
     * @param SurveyType
     * @return 影响行数
     */
    SurveyType getSurveyTypeById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取所有问卷调查类型
     * 使用示范：
     *
     * @return SurveyType对象集合
     * @throws CheckedServiceException
     * </pre>
     */
    List<SurveyType> listSurveyType() throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按调查对象名称查询
     * 使用示范：
     *
     * @param surveyName 调查对象名称
     * @return SurveyType
     * </pre>
     */
    SurveyType getSurveyTypeByName(String surveyName) throws CheckedServiceException;
}
