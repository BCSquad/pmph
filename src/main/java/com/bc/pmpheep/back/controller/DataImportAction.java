package com.bc.pmpheep.back.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bc.pmpheep.migration.GroupMigrationHelper;

/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 下午5:53:30
 *
 **/
@Controller
@RequestMapping(value = "/olddata")
public class DataImportAction {
	
	@Resource
	GroupMigrationHelper helper;
	
	@RequestMapping(value = "/start")
	public void start(){
		helper.group();
        helper.groupMember();
        helper.groupMessage();
	}
}
