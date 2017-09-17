package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * @author 曾庆峰 <791038935@qq.com>
 *
 */
public class WriterPermissionServiceTest extends BaseTest {

//	Logger logger = LoggerFactory.getLogger(WriterPermissionServiceTest.class);
//
//	@Resource
//	WriterPermissionService writerPermissionService;
//
//	@Test
//	public void add() {
//		WriterPermission writerPermission = new WriterPermission(12L, "www", "woq", "宋代福寿沟", "/ssaa9");
//		try {
//			writerPermission = writerPermissionService.addWriterPermission(writerPermission);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (null != writerPermission) {
//			logger.info("添加了{}条数据", writerPermission.toString());
//		} else {
//			logger.info("失败了");
//		}
//	}
//
//	@Test
//	public void delete() {
//		int num = -1;
//		WriterPermission writerPermission = new WriterPermission(12L, "www", "woq", "宋代福寿沟", "/ssaa9");
//		WriterPermission writerPermission2 = new WriterPermission();
//		try {
//			writerPermission = writerPermissionService.addWriterPermission(writerPermission);
//			Long id = writerPermission.getId();
//			num = writerPermissionService.delete(Integer.parseInt(String.valueOf(id)));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (-1 != num) {
//			logger.info("删除了{}条数据", num);
//		} else {
//			logger.info("失败了");
//		}
//	}
//
//	@Test
//	public void update() {
//		int num = -1;
//		WriterPermission writerPermission = new WriterPermission(12L, "www", "woq", "宋代福寿沟", "/ssaa9");
//		WriterPermission writerPermission2 = new WriterPermission(1L, "ww", "oq", "宋寿沟", "/ss9");
//		try {
//			writerPermission = writerPermissionService.addWriterPermission(writerPermission);
//			writerPermission2.setId(writerPermission.getId());
//			num = writerPermissionService.updateWriterPermissionById(writerPermission2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (-1 != num) {
//			logger.info("修改了{}条数据", num);
//		} else {
//			logger.info("失败了");
//		}
//	}
//
//	@Test
//	public void get() {
//		WriterPermission writerPermission = new WriterPermission(12L, "www", "woq", "宋代福寿沟", "/ssaa9");
//		WriterPermission writerPermission2 = new WriterPermission();
//		try {
//			writerPermission = writerPermissionService.addWriterPermission(writerPermission);
//			Long id = writerPermission.getId();
//			writerPermission2 = writerPermissionService.getWriterPermissionByPermissionName("woq");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (null != writerPermission2) {
//			logger.info("查询到了{}", writerPermission2.toString());
//		} else {
//			logger.info("失败了");
//		}
//	}

}
