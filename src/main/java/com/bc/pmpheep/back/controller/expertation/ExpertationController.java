package com.bc.pmpheep.back.controller.expertation;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.ExpertationService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/expertation")
public class ExpertationController {

    @Autowired
    ExpertationService expertationService;

    /**
     * 查找临床决策申报列表
     * @param request
     * expertationVO中的expert_type是必须的
     * QueryBy  可选条件 根据 username 账户/ realname 姓名
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean getExpertationList(
            HttpServletRequest request
            ,ExpertationVO expertationVO
            ,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
            ,@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber

    ){
        String sessionId = CookiesUtil.getSessionId(request);

        PageParameter<ExpertationVO> pageParameter = new PageParameter<ExpertationVO>(pageNumber, pageSize);
        pageParameter.setParameter(expertationVO);

        PageResult<ExpertationVO> pageResult = expertationService.list4Audit(pageParameter,sessionId);
        ResponseBean responseBean = new ResponseBean(pageResult);

        return responseBean;
    }

    /**
     *
     * @param request
     * @param ttype 分类类型 1.学科分类 2.内容分类
     * @param ptype 临床决策产品类型 1.人卫临床助手 2.人卫用药助手 3.人卫中医助手  ect
     * @return
     */
    @RequestMapping("/count/{ttype}/{ptype}")
    @ResponseBody
    public ResponseBean getCountListGroupByType(HttpServletRequest request,
                                                @PathVariable("ttype")int ttype,
                                                @PathVariable("ptype")int ptype,
                                                @RequestParam(value = "type_name",required = false)String type_name,
                                                @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber)
    {

        String sessionId = CookiesUtil.getSessionId(request);
        PageParameter<Map<String,Object>> pageParameter = new PageParameter<Map<String,Object>>(pageNumber, pageSize);
        Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("ttype",ttype); //分类类型 1.学科分类 2.内容分类
        paraMap.put("ptype",ptype); // 临床决策产品类型 1.人卫临床助手 2.人卫用药助手 3.人卫中医助手  ect
        paraMap.put("type_name",type_name); //分类名称模糊查询
        pageParameter.setParameter(paraMap);

        PageResult pageResult = expertationService.getCountListGroupByType(pageParameter,sessionId);

        ResponseBean responseBean = new ResponseBean(pageResult);

        return responseBean;
    }

    /**
     * 获取某一条申报的详情
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResponseBean<ExpertationVO> getById(HttpServletRequest request,@RequestParam(value = "id",required = true) Long id){

        String sessionId = CookiesUtil.getSessionId(request);

        ExpertationVO expertationVO = expertationService.getExpertationById(id,sessionId);

        /*
        * 副表有以下
        * dec_edu_exp 作家学习经历表
        * dec_work_exp
        * dec_acade
        * dec_textbook_pmph
        * dec_monograph
        * dec_national_plan
        * */

        ResponseBean<ExpertationVO> responseBean = new ResponseBean<ExpertationVO>(expertationVO);

        return responseBean;
    }

}
