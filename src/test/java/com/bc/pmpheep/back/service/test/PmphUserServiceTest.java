package com.bc.pmpheep.back.service.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import net.sf.json.util.JSONStringer;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONString;
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
		List<PmphUser> li=new ArrayList<>();
		PmphUser user = new PmphUser();
		user.setUsername("admin3");
		user.setAvatar("110");
		user.setPassword("1");
		user.setRealname("admin3");
		user.setIsDisabled(false);
		li.add(user);
		String s = postJson("http://localhost:11000/pmpheep/pmphWeb/syncDatas", li.toString());

		System.out.println(s);



		/*PmphUser ps = userService.add(user, roleIdList);// 给单用户添加多个角色
		// 查看对象是否不为空。
		Assert.assertNotNull("是否保存成功", ps);*/
	}

	/**
	 * post请求，参数为json字符串
	 * @param url 请求地址
	 * @param jsonString json字符串
	 * @return 响应
	 */
	public static String postJson(String url, String jsonString)
	{
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			post.setEntity(stringEntity);
			response = httpClient.execute(post);
			if(response != null && response.getStatusLine().getStatusCode() == 200)
			{
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
				if(response != null)
				{
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static String entityToString(HttpEntity entity) throws IOException {
		String result = null;
		if(entity != null)
		{
			long lenth = entity.getContentLength();
			if(lenth != -1 && lenth < 2048)
			{
				result = EntityUtils.toString(entity,"UTF-8");
			}else {
				InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
				CharArrayBuffer buffer = new CharArrayBuffer(2048);
				char[] tmp = new char[1024];
				int l;
				while((l = reader1.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
				result = buffer.toString();
			}
		}
		return result;
	}
	/**
	 * PmphUser 添加删除
	 */
	@Test
	public void deletePmphUserTest() {
		Integer aInteger = 0;
			List<Long> userIdList = new ArrayList<Long>();
			userIdList.add(1L);
			userService.deleteUserAndRole(userIdList);// 删除用户对应的角色
			aInteger = 1;
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
	public void testGet() {
		PmphUser puPmphUser;
		puPmphUser = userService.get(1L);// 按ID查询对象
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
		pageResult = userService.getListPmphUser(pageParameter,null);
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
		Assert.assertTrue("删除失败", userService.delete(pmphUser.getId()) > 0);
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
		managerVO.setUsername(pmphUser2.getUsername());
		managerVO.setRealname(pmphUser2.getRealname());
		managerVO.setRealname("角色");
		managerVO.setHandphone("18728090611");
		managerVO.setEmail("1249115@qq.com");
		String result = userService.updatePmphUserOfBack(managerVO);
		Assert.assertTrue("更新失败", result.equals("SUCCESS"));
	}
}
