package com.bc.pmpheep.back.controller;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.WxMessageService;
import com.bc.pmpheep.back.vo.UserMessageVO;
import com.bc.pmpheep.back.vo.WxMessageVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/wxMessages")
public class WxMessageController {

    @Autowired
    private WxMessageService wxMessageService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseBean getWxMessage(@RequestParam("sessionId")String sessionId,
                                     @RequestParam("title")String title,
                                     @RequestParam("msgdbtype")Integer msgdbtype,
                                     @RequestParam(value = "pageNumber",defaultValue = "1")int pageNumber,
                                     @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                     HttpServletRequest request){

        PageParameter<WxMessageVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        WxMessageVO queryWxMessageVO = new WxMessageVO();
        queryWxMessageVO.setContent(title);
        queryWxMessageVO.setMsgdbtype(msgdbtype);
        pageParameter.setParameter(queryWxMessageVO);



        Map<String,Object> result = wxMessageService.listMessage(pageParameter, sessionId);

        return new ResponseBean(result);

    }


}
