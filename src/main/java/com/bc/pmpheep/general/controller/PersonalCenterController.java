/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.controller;

import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.general.runnable.WechatArticle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页（个人中心）控制器
 *
 * @author L.X <gugia@qq.com>
 */
@Controller
public class PersonalCenterController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
    
    @RequestMapping(value = "/cms/wechat/v/{guid}", method = RequestMethod.GET)
    public ModelAndView getView(@PathVariable("guid") String guid) {
        ModelAndView view = new ModelAndView("wechat");
        if (Const.WACT_MAP.containsKey(guid)) {
            WechatArticle wechatArticle = Const.WACT_MAP.get(guid);
            view.addObject(wechatArticle);
        }
        return view;
    }
}
