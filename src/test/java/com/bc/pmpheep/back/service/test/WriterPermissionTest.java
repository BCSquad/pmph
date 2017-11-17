package com.bc.pmpheep.back.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.test.BaseTest;

/**
 * Writer 资源Test
 * 
 * @author Administrator
 * 
 */
public class WriterPermissionTest extends BaseTest {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(WriterPermissionTest.class);

	@Autowired
	WriterPermissionService writerPermissionService;

	/**
	 * PmphPermission 添加Test
	 */
	@Test
	// @Rollback(Const.ISROLLBACK)
	public void addPmphPermissionTest() {
		WriterPermission pp = new WriterPermission();
		pp.setMenuName("用户管理");
		pp.setUrl("admin/user/add");
		pp.setPermissionName("用户管理添加");
		pp.setPath("admin:add");
		pp.setParentId(1L);
		Integer aInteger = writerPermissionService.addWriterPermission(pp);// 添加资源目录
		Assert.assertTrue("是否添加成功", aInteger > 0 ? true : false);
	}

	/**
	 * PmphPermission 更新Test
	 */
	@Test
	// @Rollback(Const.ISROLLBACK)
	public void updatePmphPermissionTest() {
		WriterPermission pp = new WriterPermission();
		pp.setId(2L);
		pp.setMenuName("用户管理1");
		Integer aInteger = writerPermissionService.updateWriterPermissionById(pp);// 按ID更新资源目录
		Assert.assertTrue("是否更新成功", aInteger > 0 ? true : false);
	}

	/**
	 * PmphPermission 删除Test
	 */
	@Test
	// @Rollback(Const.ISROLLBACK)
	public void deletePmphPermissionTest() {
		WriterPermission pp = new WriterPermission();
		pp.setId(2L);
		String[] ids = { "2L" };
		Integer aInteger = writerPermissionService.delete(2L);// 按ID删除资源
		Assert.assertTrue("delete是否删除成功", aInteger > 0 ? true : false);
		Integer bInteger = writerPermissionService.deleteWriterPermissionById(ids);
		Assert.assertTrue("deleteWriterPermissionById是否删除成功", bInteger > 0 ? true : false);
	}

	/**
	 * PmphPermission 查询Test
	 */
	@Test
	// @Rollback(Const.ISROLLBACK)
	public void getPmphPermissionTest() {
		WriterPermission pp = new WriterPermission();
		pp.setId(2L);
		Assert.assertNotNull("getWriterPermissionByPermissionName是否为空",
				writerPermissionService.getWriterPermissionByPermissionName("用户管理添加"));// 按ID查询资源
		Assert.assertNotNull("get是否为空", writerPermissionService.get(2L));
		Assert.assertNotNull("getListResource是否为空", writerPermissionService.getListResource());// 查询所有资源
		Assert.assertNotNull("WriterPermission是否为空", writerPermissionService.getListAllParentMenu());
		Assert.assertNotNull("WriterPermissionByParentId是否为空", writerPermissionService.getListChildMenuByParentId(0L));

	}
}
