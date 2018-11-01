package com.bc.pmpheep.back.controller.materialSurvey;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.service.MaterialSurveyService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.MaterialSurveyCountAnswerVO;
import com.bc.pmpheep.back.vo.SurveyVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * <pre>
 * 功能描述：教材调研控制器
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
@RequestMapping(value = "/materialSurvey")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MaterialSurveyController {
    @Autowired
    MaterialSurveyService surveyService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "教材调研";

    /**
     *
     * <pre>
     * 功能描述：查询调研列表
     * 使用示范：
     *
     * @param surveyVO
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBean list(SurveyVO surveyVO,
                             @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        surveyVO.setTitle(StringUtil.toAllCheck(surveyVO.getTitle()));// 名称
        PageParameter<SurveyVO> pageParameter =
                new PageParameter<>(pageNumber, pageSize);
        pageParameter.setParameter(surveyVO);
        return new ResponseBean(surveyService.listSurvey(pageParameter));

    }

    /**
     *
     * <pre>
     * 功能描述：教材调研添加
     * 使用示范：
     *
     * @param request
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增教材调研")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseBean create(@RequestParam("questionAnswerJosn") String questionAnswerJosn,
                               @RequestParam("del_question")String del_question,
                               @RequestParam("del_question_option")String del_question_option,
                               @RequestParam(value = "tempReCreat",defaultValue = "false")Boolean tempReCreat,
                               SurveyVO surveyVO, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyService.addSurvey(questionAnswerJosn,
                del_question,
                del_question_option,
                surveyVO,
                sessionId,tempReCreat));
    }

    /**
     *
     * <pre>
     * 功能描述：查询下的所有问题
     * 使用示范：
     *
     * @param
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询下的所有问题")
    @RequestMapping(value = "/question/look", method = RequestMethod.GET)
    public ResponseBean look(/*@RequestParam("surveyId") Long surveyId,*/
            @RequestParam("id") Long id) {
        return new ResponseBean(
                surveyService.getSurveyAndQuestionById(id));
    }


    @ResponseBody
    @RequestMapping(value = "/switchActive",method = RequestMethod.GET)
    public ResponseBean switchActive(@RequestParam(value = "id" ,required =true) Long id
            ,@RequestParam(value = "status",defaultValue = "1")Short status){
        Survey updateSurvey = new Survey();
        updateSurvey.setId(id);
        updateSurvey.setStatus(status);
        return new ResponseBean(surveyService.updateSurvey(updateSurvey));
    }

    @ResponseBody
    @RequestMapping(value = "/toAnswerList",method = RequestMethod.GET)
    public ResponseBean toAnswerList(MaterialSurveyCountAnswerVO materialSurveyCountAnswerVO,
                                     @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                     @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
    ){

        PageParameter<MaterialSurveyCountAnswerVO> pageParameter =new PageParameter<>(pageNumber, pageSize);
        pageParameter.setParameter(materialSurveyCountAnswerVO);
        PageResult<MaterialSurveyCountAnswerVO> pageResult = surveyService.toAnswerList(pageParameter);
        return new ResponseBean(pageResult);
    }
}
