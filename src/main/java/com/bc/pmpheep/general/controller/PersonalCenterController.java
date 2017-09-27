/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
