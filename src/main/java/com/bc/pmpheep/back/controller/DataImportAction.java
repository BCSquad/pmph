package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 下午5:53:30
 *
 **/
@Controller
@RequestMapping(value = "/olddata")
public class DataImportAction {
	
	@Autowired
	private com.bc.pmpheep.back.datamigration.Import it;
	
	@RequestMapping(value = "/start")
	public void start(){
		it.start( );
	}
}
