package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.service.WriterRolePermissionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * WriterRolePermissionService 单元测试
 * 
 * @author 曾庆峰 <791038935@qq.com>
 *
 */
public class WriterRolePermissionServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(WriterRolePermissionServiceTest.class);

	@Resource
	private WriterRolePermissionService writerRolePermissionService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void addWriterRolePermission() throws Exception {
		WriterRolePermission a = new WriterRolePermission(6L, 25L);
		writerRolePermissionService.addWriterRolePermission(a);
		logger.info(
				"----WriterRolePermissionService-------------------------------------------------------------------------");
		logger.info(a.toString());
		a.setRoleId(15L);
		logger.info(writerRolePermissionService.updateWriterRolePermission(a).toString());
		logger.info(writerRolePermissionService.deleteWriterRolePermissionById(2L).toString());
		logger.info(writerRolePermissionService.getWriterRolePermissionById(3L).toString());
	}

}
