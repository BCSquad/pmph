package com.bc.pmpheep.back.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * Writer 资源Test
 * 
 * @author Administrator
 * 
 */
public class WriterPermissionServiceTest extends BaseTest {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(WriterPermissionServiceTest.class);

	@Autowired
	WriterPermissionService writerPermissionService;

	/**
	 * PmphPermission 添加Test
	 */
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addPmphPermissionTest() {
		WriterPermission pp = new WriterPermission();
		pp.setMenuName("用户管理1");
		pp.setUrl("admin/user/add");
		pp.setPermissionName("用户管理添加1");
		pp.setPath("admin:add");
		pp.setParentId(1L);
		Integer aInteger = writerPermissionService.addWriterPermission(pp);// 添加资源目录
		Assert.assertTrue("是否添加成功", aInteger > 0 ? true : false);
	}

	/**
	 * PmphPermission 更新Test
	 */
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updatePmphPermissionTest() {
		WriterPermission pp = new WriterPermission();
		pp.setMenuName("用户管理2");
		pp.setUrl("admin/user/add");
		pp.setPermissionName("用户管理添加2");
		pp.setPath("admin:add");
		pp.setParentId(2L);
		writerPermissionService.addWriterPermission(pp);
		pp.setId(pp.getId());
		pp.setMenuName("用户管理2");
		Integer aInteger = writerPermissionService.updateWriterPermissionById(pp);// 按ID更新资源目录
		Assert.assertTrue("是否更新成功", aInteger > 0 ? true : false);
	}

	/**
	 * PmphPermission 删除Test
	 */
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deletePmphPermissionTest() {
		WriterPermission pp = new WriterPermission();
		pp.setMenuName("用户管理3");
		pp.setUrl("admin/user/add");
		pp.setPermissionName("用户管理添加3");
		pp.setPath("admin:add");
		pp.setParentId(3L);
		writerPermissionService.addWriterPermission(pp);
		pp.setId(pp.getId());
		//String[] ids = { "2L" };
		Integer aInteger = writerPermissionService.delete(pp.getId());// 按ID删除资源
		Assert.assertTrue("delete是否删除成功", aInteger > 0 ? true : false);
		//Integer bInteger = writerPermissionService.deleteWriterPermissionById(ids);
		//Assert.assertTrue("deleteWriterPermissionById是否删除成功", bInteger > 0 ? true : false);
	}

	/**
	 * PmphPermission 查询Test
	 */
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getPmphPermissionTest() {
		WriterPermission pp = new WriterPermission();
		pp.setMenuName("用户管理4");
		pp.setUrl("admin/user/add");
		pp.setPermissionName("用户管理添加");
		pp.setPath("admin:add");
		pp.setParentId(4L);
		writerPermissionService.addWriterPermission(pp);
		pp.setId(pp.getId());
		Assert.assertNotNull("getWriterPermissionByPermissionName是否为空",
				writerPermissionService.getWriterPermissionByPermissionName("用户管理添加"));// 按ID查询资源
		Assert.assertNotNull("get是否为空", writerPermissionService.get(pp.getId()));
		Assert.assertNotNull("getListResource是否为空", writerPermissionService.getListResource());// 查询所有资源
		//Assert.assertNotNull("WriterPermission是否为空", writerPermissionService.getListAllParentMenu());
		//Assert.assertNotNull("WriterPermissionByParentId是否为空", writerPermissionService.getListChildMenuByParentId(0L));

	}
}
