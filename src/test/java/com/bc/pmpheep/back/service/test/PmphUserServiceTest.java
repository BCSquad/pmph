package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * PmphUser 单元测试
 * 
 * @author Administrator
 * 
 */
public class PmphUserServiceTest extends BaseTest {
	private static final Logger log = LoggerFactory.getLogger(PmphUserServiceTest.class);

	@Autowired
	PmphUserService userService;

	/**
	 * PmphUser 添加Test
	 */
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addPmphUserTest() {
		try {
			List<Long> roleIdList = new ArrayList<Long>();
			roleIdList.add(1L);
			roleIdList.add(2L);
			roleIdList.add(3L);
			PmphUser user = new PmphUser();
			user.setUsername("admin");
			user.setPassword("1");
			user.setRealname("admin");
			user.setIsDisabled(0);
			PmphUser u = userService.add(user);// 添加用户
			Long a = u.getId();// 返回自增主键
			System.out.println(a);
			PmphUser ps = userService.add(new PmphUser("test1", "123"), roleIdList);// 给单用户添加多个角色
			Assert.assertNotNull(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * PmphUser 添加删除
	 */
	 @Test
	 @Rollback(Const.ISROLLBACK)
	public void deletePmphUserTest() {
		Integer aInteger = 0;
		try {
			List<Long> userIdList = new ArrayList<Long>();
			userIdList.add(19L);
			// userService.delete(18L);// 按ID删除
			userService.deleteUserAndRole(userIdList);// 删除用户对应的角色
			aInteger = 1;
		} catch (CheckedServiceException e) {
			e.printStackTrace();
		}
		System.out.println(aInteger);
	}

	/**
	 * PmphUser 更新方法
	 */
	 @Test
	 @Rollback(Const.ISROLLBACK)
	public void updatePmphUserTest() {
		PmphUser pmphUser = new PmphUser();
		pmphUser.setId(18L);
		pmphUser.setUsername("test1");
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(1L);
		userIdList.add(2L);
		PmphUser pu = userService.update(pmphUser);
		log.debug(pu.toString());
		PmphUser pu1 = userService.update(pmphUser, userIdList);
		log.debug(pu1.toString());
	}

	/**
	 * 查询
	 */
	 @Test
	 @Rollback(Const.ISROLLBACK)
	public void getListsTest() {
		PmphUser puPmphUser;
		List<PmphUser> pmUsers;
		List<PmphPermission> listPermissions;
		try {
			pmUsers = userService.getList();// 查询所有
			log.debug(pmUsers.toString());
			puPmphUser = userService.getByUsername("test1");// 按UserName 查询对象
			log.debug(puPmphUser.toString());
			puPmphUser = userService.get(31L);// 按ID查询对象
			log.debug(puPmphUser.toString());
			puPmphUser = userService.login("test1", "123");
			log.debug(puPmphUser.toString());
			pmUsers = userService.getListByRole(1L);
			log.debug(pmUsers.toString());
			listPermissions = userService.getListAllResource(31L);
			log.debug(listPermissions.size() == 0 ? "null" : listPermissions.get(0).toString());
			List<String> listRoleNameList = userService.getListRoleSnByUser(31L);
			log.debug(listRoleNameList.size() == 0 ? "null" : listRoleNameList.get(0));
			List<PmphRole> pr = userService.getListUserRole(31L);
			log.debug(pr.size() == 0 ? "null" : pr.get(0).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
