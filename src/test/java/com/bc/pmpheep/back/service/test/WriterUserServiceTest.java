package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * @author 曾庆峰 <791038935@qq.com>
 *
 */
public class WriterUserServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(WriterUserMessageServiceTest.class);

	@Resource
	WriterUserService writerUserService;

	WriterUser writerUser = new WriterUser();

	// @Test
	public void addWriterUserService() {
		writerUser.setUsername("zasd");
		writerUser.setPassword("10214");
		writerUser.setRealname("aswwq");
		writerUser.setAvatar("asdadasdasd");
		try {
			writerUser = writerUserService.add(writerUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != writerUser) {
			logger.info("添加了{}", writerUser.toString());
		} else {
			logger.info("失败了");
		}
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
			Long id = writerUser.getId();
			writerUserService.delete(id);
			num = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			writerUser = writerUserService.add(writerUser);
			Long id = writerUser.getId();
			writerUser1 = writerUserService.update(writerUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != writerUser1) {
			logger.info("修改成功{}", writerUser1.toString());
		} else {
			logger.info("失败了");
		}

	}

	// @Test
	public void getWriterUserByUsername() {
		writerUser.setUsername("zasd");
		writerUser.setPassword("10214");
		writerUser.setRealname("aswwq");
		writerUser.setAvatar("asdadasdasd");
		WriterUser writerUser1 = new WriterUser();
		try {
			writerUser = writerUserService.add(writerUser);
			Long id = writerUser.getId();
			writerUser1 = writerUserService.get(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != writerUser1) {
			logger.info("查找成功{}", writerUser1.toString());
		} else {
			logger.info("失败了");
		}

	}

	@Test
	public void getListWriterUserVO() {
		Page<WriterUserManagerVO, WriterUserManagerVO> page = new Page<>();
		WriterUserManagerVO managerVO = new WriterUserManagerVO();
		managerVO.setUsername(null);
		managerVO.setRealname(null);
		managerVO.setOrgName(null);
		managerVO.setRank(null);
		page.setParameter(managerVO);
		page.setPageSize(15);
		page = writerUserService.getListWriterUser(page);
		if (page.getRows().isEmpty()) {
			logger.info("失败了");
		} else {
			logger.info("查找成功{}", page);
		}
	}

	/**
	 * PmphUser 添加Test
	 */
	// @Test
	// @Rollback(false)
	public void addPmphUserTest() {
		try {
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
			Long a = u.getId();// 返回自增主键
			System.out.println(a);
			WriterUser ps = writerUserService.add(new WriterUser("test1", "123"), roleIdList);// 给单用户添加多个角色
			Assert.assertNotNull(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * PmphUser 添加删除
	 */
	// @Test
	// @Rollback(false)
	public void deletePmphUserTest() {
		Integer aInteger = 0;
		try {
			List<Long> userIdList = new ArrayList<Long>();
			userIdList.add(19L);
			// userService.delete(18L);// 按ID删除
			writerUserService.deleteUserAndRole(userIdList);// 删除用户对应的角色
			aInteger = 1;
		} catch (CheckedServiceException e) {
			e.printStackTrace();
		}
		System.out.println(aInteger);
	}

	/**
	 * 查询
	 */
	// @Test
	public void getListsTest() {
		WriterUser wtUser;
		List<WriterUser> pmUsers;
		List<WriterPermission> listPermissions;
		try {
			pmUsers = writerUserService.getList();// 查询所有
			logger.debug(pmUsers.toString());
			wtUser = writerUserService.getByUsername("test1");// 按UserName 查询对象
			logger.debug(wtUser.toString());
			wtUser = writerUserService.get(1L);// 按ID查询对象
			logger.debug(wtUser.toString());
			wtUser = writerUserService.login("test1", "123");
			logger.debug(wtUser.toString());
			pmUsers = writerUserService.getListByRole(1L);
			logger.debug(pmUsers.toString());
			listPermissions = writerUserService.getListAllResource(1L);
			logger.debug(listPermissions.size() == 0 ? "null" : listPermissions.get(0).toString());
			List<String> listRoleNameList = writerUserService.getListRoleSnByUser(1L);
			logger.debug(listRoleNameList.size() == 0 ? "null" : listRoleNameList.get(0));
			List<WriterRole> pr = writerUserService.getListUserRole(1L);
			logger.debug(pr.size() == 0 ? "null" : pr.get(0).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * PmphUser 更新方法
	 */
	// @Test
	// @Rollback(false)
	public void updatePmphUserTest() {
		WriterUser pmphUser = new WriterUser();
		pmphUser.setId(18L);
		pmphUser.setUsername("admin1");
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(1L);
		userIdList.add(2L);
		WriterUser pu = writerUserService.update(pmphUser);
		logger.debug(pu.toString());
		WriterUser pu1 = writerUserService.update(pmphUser, userIdList);
		logger.debug(pu1.toString());
	}

	// @Test
	// public void getListWriterUserPO() {
	// Page<WriterUser, Map<String, String>> page = new Page<>();
	// Map<String, String> map = new HashMap<>();
	// map.put("username", null);
	// map.put("realname", null);
	// map.put("orgName", null);
	// page.setParameter(map);
	// page.setPageSize(15);
	// Page<WriterUserManagerVO, Map<String, String>> pageVO = new Page<>();
	// try {
	// pageVO = writerUserService.getListWriter(page);
	// } catch (CheckedServiceException | ReflectiveOperationException e) {
	// logger.error(e.getMessage());
	// }
	// if (pageVO.getRows().isEmpty()) {
	// logger.info("失败了");
	// } else {
	// logger.info("查找成功{}", pageVO);
	// }
	// }
}
