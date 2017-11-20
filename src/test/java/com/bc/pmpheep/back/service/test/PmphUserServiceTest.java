package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.shiro.kit.ShiroKit;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;
import com.google.gson.Gson;

/**
 * PmphUser 单元测试
 * 
 * @author Administrator
 * 
 */
public class PmphUserServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(PmphUserServiceTest.class);
	Gson gson = new Gson();

	@Autowired
	PmphUserService userService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * PmphUser 添加Test
	 */
	@Test
	public void testAddPmphUser() {
		List<Long> roleIdList = new ArrayList<Long>();
		roleIdList.add(1L);
		roleIdList.add(2L);
		roleIdList.add(3L);
		PmphUser user = new PmphUser();
		user.setUsername("admin3");
		user.setAvatar("110");
		user.setPassword("1");
		user.setRealname("admin3");
		user.setIsDisabled(false);
		PmphUser u = userService.add(user);// 添加用户
		// 查看两个对象的引用是否相等。类似于使用“==”比较两个对象
		Assert.assertNotSame("是否有返回值", null, u.getId());
	}

	/**
	 * PmphUser 添加Test
	 */
	@Test
	public void testAdd() {
		List<Long> roleIdList = new ArrayList<Long>();
		roleIdList.add(1L);
		roleIdList.add(2L);
		roleIdList.add(3L);
		PmphUser user = new PmphUser();
		user.setUsername("admin3");
		user.setAvatar("110");
		user.setPassword("1");
		user.setRealname("admin3");
		user.setIsDisabled(false);
		PmphUser ps = userService.add(user, roleIdList);// 给单用户添加多个角色
		// 查看对象是否不为空。
		Assert.assertNotNull("是否保存成功", ps);
	}

	/**
	 * PmphUser 添加删除
	 */
	@Test
	public void deletePmphUserTest() {
		Integer aInteger = 0;
		try {
			List<Long> userIdList = new ArrayList<Long>();
			userIdList.add(19L);
			userService.deleteUserAndRole(userIdList);// 删除用户对应的角色
			thrown.expect(CheckedServiceException.class);// 预期异常的属性信息
			aInteger = 1;
		} catch (CheckedServiceException e) {
			e.printStackTrace();
		}
		Assert.fail("CheckedServiceException");
		// 查看两个对象的引用是否相等。类似于使用“==”比较两个对象
		Assert.assertSame("是否等于1", 1, aInteger);
	}

	/**
	 * PmphUser 更新方法
	 */
	@Test
	public void testUpdate() {
		PmphUser pmphUser = new PmphUser();
		pmphUser.setId(18L);
		pmphUser.setUsername("test1");
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(1L);
		userIdList.add(2L);
		PmphUser pu = userService.update(pmphUser);
		// 查看对象是否不为空。
		Assert.assertNotNull("修改成功", pu);
	}

	/**
	 * PmphUser 更新方法
	 */
	@Test
	public void updatePmphUserTest() {
		PmphUser pmphUser = new PmphUser();
		pmphUser.setId(18L);
		pmphUser.setUsername("test1");
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(1L);
		userIdList.add(2L);
		PmphUser pu = userService.update(pmphUser, userIdList);
		Assert.assertNotNull("修改成功", pu);
	}

	/**
	 * 查询
	 */
	@Test
	public void testGetList() {
		List<PmphUser> pmUsers;
		pmUsers = userService.getList();// 查询所有
		Assert.assertNotNull("查询失败", pmUsers);
	}

	@Test
	public void testLogin() {
		PmphUser puPmphUser;
		puPmphUser = userService.login("admin", ShiroKit.md5("123", "admin"));// 按UserName
		Assert.assertNotNull("登录失败", puPmphUser);
	}

	@Test
	public void testGet() {
		PmphUser puPmphUser;
		puPmphUser = userService.get(1L);// 按ID查询对象
		Assert.assertNotNull("查询失败", puPmphUser);
	}

	@Test
	public void test() {
		PmphUser puPmphUser;
		puPmphUser = userService.login("admin", "123");
		Assert.assertNotNull("查询失败", puPmphUser);
	}

	@Test
	public void testGetListByRole() {
		List<PmphUser> pmUsers;
		pmUsers = userService.getListByRole(1L);
		Assert.assertNotNull("查询失败", pmUsers);
	}

	@Test
	public void testGetListAllResource() {
		List<PmphPermission> listPermissions;
		listPermissions = userService.getListAllResource(1L);
		Assert.assertNotNull("查询失败", listPermissions);
	}

	@Test
	public void testGetListRoleSnByUser() {
		List<String> listRoleNameList = userService.getListRoleSnByUser(1L);
		Assert.assertNotNull("查询失败", listRoleNameList);
	}

	@Test
	public void testGetListUserRole() {
		List<PmphRole> pr = userService.getListUserRole(1L);
		Assert.assertNotNull("查询失败",pr);
	}

	@Test
	public void getListByUsernameAndRealname() {
		PageResult pageResult = userService.getListByUsernameAndRealname("1", 1, 10);
		Assert.assertNotNull("查找失败", pageResult);
	}

	@Test
	public void testTetListPmphUserVO() {
		PageResult pageResult = new PageResult<>();
		PageParameter pageParameter = new PageParameter<>();
		PmphUserManagerVO managerVO = new PmphUserManagerVO();
		managerVO.setUsername(null);
		managerVO.setRealname(null);
		managerVO.setPath("0-92-174");
		managerVO.setDepartmentId(176L);
		pageParameter.setParameter(managerVO);
		pageParameter.setPageNumber(1);
		pageParameter.setPageSize(30);
		pageResult = userService.getListPmphUser(pageParameter);
		Assert.assertNotNull("查找失败", pageResult);
	}

	@Test
	public void testDelete() {
		PmphUser pmphUser = new PmphUser();
		pmphUser.setUsername("admin001");
		pmphUser.setPassword("123");
		pmphUser.setRealname("ABC");
		pmphUser.setAvatar("110");
		userService.add(pmphUser);
		PmphUser pmphUser2 = new PmphUser();
		pmphUser2 = userService.getByUsernameAndPassword(pmphUser.getUsername(),
				ShiroKit.md5(pmphUser.getPassword(), pmphUser.getUsername()));
		Assert.assertTrue("删除失败", userService.delete(pmphUser2.getId()) > 0);
	}

	@Test
	public void testDeleteUserAndRole() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		PmphUser pmphUser = new PmphUser();
		pmphUser.setUsername("ABC");
		pmphUser.setPassword("123");
		pmphUser.setAvatar("110");
		pmphUser.setRealname("ABC");
		userService.add(pmphUser, ids);
		Assert.assertTrue("影响行数不为3就为错误", userService.deleteUserAndRole(ids) == 3);
	}

	@Test
	public void testUpdatePmphUserOfBack() {
		PmphUser pmphUser = new PmphUser();
		pmphUser.setUsername("BBB");
		pmphUser.setPassword("666");
		pmphUser.setAvatar("110");
		pmphUser.setRealname("CCC");
		PmphUser pmphUser2 = new PmphUser();
		pmphUser2 = userService.add(pmphUser);
		pmphUser2.setPassword("777");
		PmphUserManagerVO managerVO = new PmphUserManagerVO();
		managerVO.setId(pmphUser2.getId());
		managerVO.setUsername(managerVO.getUsername());
		managerVO.setRealname(pmphUser2.getRealname());
		managerVO.setRealname("角色");
		managerVO.setHandphone("18728090611");
		managerVO.setEmail("1249115@qq.com");
		String result = userService.updatePmphUserOfBack(managerVO);
		Assert.assertTrue("更新失败", result.equals("SUCCESS"));
	}
}
