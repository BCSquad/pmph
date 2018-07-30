package com.bc.pmpheep.back.controller.expertation;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.ExpertationService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            , Integer pageSize, Integer pageNumber

    ){
        String sessionId = CookiesUtil.getSessionId(request);

        PageParameter<ExpertationVO> pageParameter = new PageParameter<ExpertationVO>(pageNumber, pageSize);
        pageParameter.setParameter(expertationVO);

        PageResult<ExpertationVO> pageResult = expertationService.list4Audit(pageParameter,sessionId);
        ResponseBean responseBean = new ResponseBean(pageResult);

        return responseBean;
    }

}
