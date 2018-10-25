package com.bc.pmpheep.back.controller.materialSurvey;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.service.MaterialSurveyQuestionService;
import com.bc.pmpheep.back.service.SurveyQuestionService;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * <pre>
 * 功能描述：问卷问题控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-26
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/materialSurvey/question")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MaterialSurveyQuestionController {
    @Autowired
    MaterialSurveyQuestionService surveyQuestionService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "教材调研-调研问题";

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
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询问题")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBean list(@RequestParam("questionId") Long questionId,
    @RequestParam("categoryId") Long categoryId) {
        return new ResponseBean(
                                surveyQuestionService.getQuestionOptionByQuestionIdOrCategoryId(questionId,
                                                                                                categoryId));
    }
}
