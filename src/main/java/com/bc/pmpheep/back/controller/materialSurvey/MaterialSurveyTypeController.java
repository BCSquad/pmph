package com.bc.pmpheep.back.controller.materialSurvey;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.SurveyType;
import com.bc.pmpheep.back.service.MaterialSurveyTypeService;
import com.bc.pmpheep.back.service.SurveyTypeService;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * <pre>
 * 功能描述：问卷调查类型（对象）控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-25
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/materialSurvey/type")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MaterialSurveyTypeController {
    @Autowired
    MaterialSurveyTypeService surveyTypeService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "教材调研-调查类型";

    /**
     * 
     * <pre>
     * 功能描述：获取教材调研类型集合
     * 使用示范：
     *
     * @return SurveyType对象集合
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取教材调研类型集合")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBean list() {
        return new ResponseBean(surveyTypeService.listSurveyType());
    }

    /**
     * 
     * <pre>
     * 功能描述：新增SurveyType
     * 使用示范：
     *
     * @param surveyType
     * @return SurveyType对象
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增教材调研类型")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseBean create(SurveyType surveyType) {
        return new ResponseBean(surveyTypeService.addSurveyType(surveyType));
    }

    /**
     * 
     * <pre>
     * 功能描述：更新教材调研类型
     * 使用示范：
     *
     * @param surveyType SurveyType对象
     * @return 影响行数 
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新教材调研类型")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseBean update(SurveyType surveyType) {
        return new ResponseBean(surveyTypeService.updateSurveyType(surveyType));
    }

    /**
     * 
     * <pre>
     * 功能描述：按id删除教材调研类型
     * 使用示范：
     *
     * @param id SurveyType主键 ID 
     * @return 影响行数 
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除教材调研类型")
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public ResponseBean remove(@PathVariable("id") Long id) {
        return new ResponseBean(surveyTypeService.deleteSurveyTypeById(id));
    }
}
