package com.bc.pmpheep.back.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.MaterialTypeService;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.migration.*;

@Controller
@RequestMapping(value = "/data")
@SuppressWarnings("all")
public class DataTestImportController {

	@Autowired
	private MigrationStageOne migrationStageOne;
	@Autowired
	private MigrationStageTwo migrationStageTwo;
	@Autowired
	private MigrationStageThree migrationStageThree;
	@Autowired
	private MigrationStageFour migrationStageFour;
	@Autowired
	private MigrationStageFive migrationStageFive;
	@Autowired
	private MigrationStageSix migrationStageSix;
	@Autowired
	private MigrationStageSeven migrationStageSeven;
	@Autowired
	private MigrationStageEight migrationStageEight;
	@Autowired
	private MigrationStageNine migrationStageNine;
	@Autowired
	private MigrationStageTen migrationStageTen;
	
	@ResponseBody
	@RequestMapping(value = "/import")
	public void listBookVO() {
		System.out.println(new Date());
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;
		boolean flag6 = false;
		boolean flag7 = false;
		boolean flag8 = false;
		boolean flag9 = false;
		boolean flag10 = false;
		Exception e1=null;
		Exception e2=null;
		Exception e3=null;
		Exception e4=null;
		Exception e5=null;
		Exception e6=null;
		Exception e7=null;
		Exception e8=null;
		Exception e9=null;
		Exception e10=null;
		
		try {
			migrationStageOne.start();
			flag1 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e1=e;
		}
		
		try {
			migrationStageTwo.start();
			flag2 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e2=e;
		}
		
		try {
			migrationStageThree.start();
			flag3 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e3=e;
		}
		try {
			migrationStageFour.start();
			flag4 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e4=e;
		}
		try {
			migrationStageFive.start();
			flag5 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e5=e;
		}
		try {
			migrationStageSix.start();
			flag6 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e6=e;
		}
		try {
			migrationStageSeven.start();
			flag7 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e7=e;
		}
		try {
			migrationStageEight.start();
			flag8 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e8=e;
		}
		try {
			migrationStageNine.start();
			flag9 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e9=e;
		}
		try {
			migrationStageTen.start();
			flag10 = true;
		} catch (Exception e) {
			e.printStackTrace();
			e10=e;
		}
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println(flag1);
		System.out.println(flag1?e1:e1.getMessage());
		System.out.println(flag2);
		System.out.println(flag2?e2:e2.getMessage());
		System.out.println(flag3);
		System.out.println(flag3?e3:e3.getMessage());
		System.out.println(flag4);
		System.out.println(flag4?e4:e4.getMessage());
		System.out.println(flag5);
		System.out.println(flag5?e5:e5.getMessage());
		System.out.println(flag6);
		System.out.println(flag6?e6:e6.getMessage());
		System.out.println(flag7);
		System.out.println(flag7?e7:e7.getMessage());
		System.out.println(flag8);
		System.out.println(flag8?e8:e8.getMessage());
		System.out.println(flag9);
		System.out.println(flag9?e9:e9.getMessage());
		System.out.println(flag10);
		System.out.println(flag10?e10:e10.getMessage());
		System.out.println(new Date());
	}

	
}
