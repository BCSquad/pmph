package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.shiro.kit.ShiroKit;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * @author 曾庆峰 <791038935@qq.com>
 * 
 */
public class WriterUserServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(WriterUserServiceTest.class);

	@Resource
	WriterUserService writerUserService;

	WriterUser writerUser = new WriterUser();

	// @Test
	public void addWriterUserService() {
		writerUser.setUsername("zasd");
		writerUser.setPassword("10214");
		writerUser.setRealname("aswwq");
		writerUser.setAvatar("asdadasdasd");
		writerUser = writerUserService.add(writerUser);
		Assert.assertNotNull("不否保存成功", writerUser.getId());
		logger.info("添加了{}", writerUser.toString());
	}

	// @Test
	public void deleteWriterUserServiceById() {
		int num = -1;
		writerUser.setUsername("zasd");
		writerUser.setPassword("10214");
		writerUser.setRealname("aswwq");
		writerUser.setAvatar("asdadasdasd");
		try {
			writerUser = writerUserService.add(writerUser);
			Assert.assertNotNull("不否保存成功", writerUser.getId());
			Long id = writerUser.getId();
			writerUserService.delete(id);
			num = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.fail();
		if (-1 != num) {
			logger.info("删除成功");
		} else {
			logger.info("失败了");
		}

	}

	// @Test
	public void updateWriterUserById() {
		writerUser.setUsername("zasd");
		writerUser.setPassword("10214");
		writerUser.setRealname("aswwq");
		writerUser.setAvatar("asdadasdasd");
		WriterUser writerUser1 = new WriterUser();
		writerUser = writerUserService.add(writerUser);
		Assert.assertNotNull("否保存成功", writerUser.getId());
		// Long id = writerUser.getId();
		writerUser1 = writerUserService.update(writerUser);
		Assert.assertNotNull("是否更新成功", writerUser1);
		logger.info("修改成功{}", writerUser1.toString());
	}

	// @Test
	public void getWriterUserByUsername() {
		writerUser.setUsername("zasd");
		writerUser.setPassword("10214");
		writerUser.setRealname("aswwq");
		writerUser.setAvatar("asdadasdasd");
		WriterUser writerUser1 = new WriterUser();
		writerUser = writerUserService.add(writerUser);
		Assert.assertNotNull("否保存成功", writerUser.getId());
		Long id = writerUser.getId();
		writerUser1 = writerUserService.get(id);
		Assert.assertNotNull("是否查询成功", writerUser1);
		logger.info("查找成功{}", writerUser1.toString());
	}

	@Test
	public void getListWriterUserVO() {
		PageParameter pageParameter = new PageParameter<>();
		PageResult pageResult = new PageResult<>();
		WriterUserManagerVO managerVO = new WriterUserManagerVO();
		managerVO.setUsername(null);
		managerVO.setRealname(null);
		managerVO.setOrgName(null);
		managerVO.setRank(null);
		pageParameter.setParameter(managerVO);
		pageParameter.setPageSize(15);
		pageResult = writerUserService.getListWriterUser(pageParameter);
		Assert.assertNotNull("是否查询成功", pageResult);
		logger.info("查找成功{}", pageResult);
	}

	/**
	 * PmphUser 添加Test
	 */
	// @Test
	// @Rollback(Const.ISROLLBACK)
	public void addPmphUserTest() {
		List<Long> roleIdList = new ArrayList<Long>();
		roleIdList.add(1L);
		roleIdList.add(2L);
		roleIdList.add(3L);
		WriterUser user = new WriterUser();
		user.setUsername("admin");
		user.setPassword("1");
		user.setRealname("admin");
		user.setIsDisabled(0);
		WriterUser u = writerUserService.add(user);// 添加用户
		Assert.assertNotNull("否保存成功", u.getId());
		WriterUser ps = writerUserService.add(new WriterUser("test1", "123"), roleIdList);// 给单用户添加多个角色
		Assert.assertNotNull(ps);
	}

	/**
	 * PmphUser 添加删除
	 */
	// @Test
	// @Rollback(Const.ISROLLBACK)
	public void deletePmphUserTest() {
		Integer aInteger = 0;
		try {
			List<Long> userIdList = new ArrayList<Long>();
			userIdList.add(19L);
			// userService.delete(18L);// 按ID删除
			writerUserService.deleteUserAndRole(userIdList);// 删除用户对应的角色
			aInteger = 1;
			Assert.assertTrue("是否删除成功", aInteger > 0 ? true : false);
		} catch (CheckedServiceException e) {
			e.printStackTrace();
		}
		Assert.fail();
	}

	/**
	 * 查询
	 */
	// @Test
	public void getListsTest() {
		WriterUser wtUser;
		List<WriterUser> pmUsers;
		List<WriterPermission> listPermissions;
		pmUsers = writerUserService.getList();// 查询所有
		Assert.assertNotNull(pmUsers);
		logger.debug(pmUsers.toString());
		wtUser = writerUserService.getByUsernameAndPassword("test1", ShiroKit.md5("123", "test1"));// 按UserName
		Assert.assertNotNull(wtUser);
		logger.debug(wtUser.toString());
		wtUser = writerUserService.get(1L);// 按ID查询对象
		Assert.assertNotNull(wtUser);
		logger.debug(wtUser.toString());
		wtUser = writerUserService.login("test1", "123");
		Assert.assertNotNull(wtUser);
		logger.debug(wtUser.toString());
		pmUsers = writerUserService.getListByRole(1L);
		Assert.assertNotNull(pmUsers);
		logger.debug(pmUsers.toString());
		listPermissions = writerUserService.getListAllResource(1L);
		Assert.assertNotNull(listPermissions);
		logger.debug(listPermissions.size() == 0 ? "null" : listPermissions.get(0).toString());
		List<String> listRoleNameList = writerUserService.getListRoleSnByUser(1L);
		Assert.assertNotNull(listRoleNameList);
		logger.debug(listRoleNameList.size() == 0 ? "null" : listRoleNameList.get(0));
		List<WriterRole> pr = writerUserService.getListUserRole(1L);
		Assert.assertNotNull(pr);
		logger.debug(pr.size() == 0 ? "null" : pr.get(0).toString());
	}

	/**
	 * PmphUser 更新方法
	 */
	// @Test
	// @Rollback(Const.ISROLLBACK)
	public void updatePmphUserTest() {
		WriterUser pmphUser = new WriterUser();
		pmphUser.setId(18L);
		pmphUser.setUsername("admin1");
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(1L);
		userIdList.add(2L);
		WriterUser pu = writerUserService.update(pmphUser);
		Assert.assertNotNull("是否更新成功", pu);
		logger.debug(pu.toString());
		WriterUser pu1 = writerUserService.update(pmphUser, userIdList);
		Assert.assertNotNull("是否更新成功", pu1);
		logger.debug(pu1.toString());
	}

	@Test
	public void getWriterUserListByOrgIds() {
		WriterUser writerUser = new WriterUser();
		List<Long> orgIds = new ArrayList<Long>();
		orgIds.add(1L);
		orgIds.add(2L);
		orgIds.add(3L);
		writerUser.setUsername("ABC");
		writerUser.setPassword("123");
		writerUser.setRealname("ABC");
		writerUser.setNickname("ABC");
		writerUser.setAvatar("---");
		writerUser.setOrgId(2L);
		writerUserService.add(writerUser);
		List<WriterUser> list = writerUserService.getWriterUserListByOrgIds(orgIds);
		Assert.assertTrue("获取数据失败", list.size() > 0);
		writerUser.setOrgId(5L);
		writerUserService.update(writerUser);
		list = writerUserService.getWriterUserListByOrgIds(orgIds);
		Assert.assertTrue("不应该能获取数据", list.size() == 0);
	}

	@Test
	public void addWriterUserOfBack() {
		WriterUser writerUser = new WriterUser();
		writerUser.setUsername("OPQ");
		writerUser.setRealname("OPQ");
		writerUser.setNickname("OPQ");
		writerUser.setAvatar("---");
		String result = writerUserService.addWriterUserOfBack(writerUser);
		Assert.assertTrue("添加失败", result.equals("SUCCESS"));
	}

	@Test
	public void updateWriterUserOfBack() {
		WriterUser writerUser = new WriterUser();
		writerUser.setUsername("XYZ");
		writerUser.setPassword("789");
		writerUser.setRealname("ZZZ");
		writerUser.setNickname("QQQ");
		writerUser.setAvatar("---");
		writerUserService.addWriterUserOfBack(writerUser);
		writerUser.setRealname("UUU");
		String result = writerUserService.updateWriterUserOfBack(writerUser);
		Assert.assertTrue("更新失败", result.equals("SUCCESS"));
	}
}
