package com.bc.pmpheep.back.service.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.dao.UserMessageDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.back.vo.MyMessageVO;
import com.bc.pmpheep.back.vo.UserMessageVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * UserMessageService 单元测试
 * 
 * @author mryang
 */
@SuppressWarnings("all")
public class UserMessageServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(UserMessageServiceTest.class);

	@Resource
	private UserMessageService userMessageService;

	@Resource
	UserMessageDao userMessageDao;

	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void getMessageStateListTest() throws IOException {
		// SessionUtil.getShiroSessionUser().setAttribute(Const.SESSION_PMPH_USER, new
		// PmphUser(1L));
		MessageStateVO messageStateVO = new MessageStateVO();
		UserMessageVO userMessageVO = new UserMessageVO();
		MyMessageVO myMessageVO = new MyMessageVO();
		myMessageVO.setUserId(1L);
		myMessageVO.setUserType(1);
		PageParameter pageParameter = new PageParameter<>();
		PageResult pageResult = new PageResult<>();
		List<String> list = new ArrayList<String>();
		list.add("fd");
		String[] attachment = new String[] { "12" };
		// pageParameter.setParameter(messageStateVO);
		// pageResult = userMessageService.listMessageState(pageParameter, "");
		// Assert.assertNotNull("查询成功了", pageResult);
		// pageParameter.setParameter(userMessageVO);
		// pageResult = userMessageService.listMessage(pageParameter, "");
		// Assert.assertNotNull("查询成功了", pageResult);
		pageParameter.setParameter(myMessageVO);
		pageResult = userMessageService.listMyMessageOfIcon(pageParameter);
		Assert.assertNotNull("查询成功了", pageResult);
		pageResult = userMessageService.listMyMessage(pageParameter);
		Assert.assertNotNull("查询成功了", pageResult);
		myMessageVO = userMessageService.updateMyMessageDetail(746L);
		Assert.assertNotNull("查询成功了", myMessageVO);
		Long[] msgIds = { 746L };
		int num = -1;
		num = userMessageService.updateMyMessage(msgIds);
		Assert.assertTrue("修改成功", num >= 0);
		// num = userMessageService.addOrUpdateUserMessage(new Message(null, "eee"),
		// "测试", 1, "1", null, "1", true,
		// new String[] { "1" }, "");
		// Assert.assertTrue("修改成功", num >= 0);
		// num = userMessageService.addOrUpdateUserMessage(new Message("1", "eee"),
		// "测试", 2, "2", "1", "1", false,
		// new String[] { "1" }, "");
		// Assert.assertTrue("修改成功", num >= 0);
		num = userMessageService.updateUserMessage(new Message("1", "aa"), "", "", attachment, attachment);
		Assert.assertTrue("修改成功", num >= 0);
		num = userMessageService.updateToWithdraw(new UserMessage("1", true));
		Assert.assertTrue("修改成功", num >= 0);
		num = userMessageService.deleteMessageByMsgId(list);
		Assert.assertTrue("修改成功", num >= 0);
		List<String> ids = new ArrayList<String>(1);
		ids.add("1");
		num = userMessageService.deleteMessageByMsgId(ids);
		Assert.assertTrue("删除成功", num >= 0);
	}*/

	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void addUserMessageBatch() {
		List<UserMessage> userMessageList = new ArrayList<>();
		Random r = new Random();
		UserMessage userMessage = userMessageService.addUserMessage(new UserMessage(String.valueOf(r.nextInt(200)),
				"asdhga", (short) 1, Long.parseLong(String.valueOf(r.nextInt(200))), (short) 1,
				Long.parseLong(String.valueOf(r.nextInt(200))), (short) 1, true, true, true, null, null));
		Assert.assertTrue("添加失败", userMessage.getId() > 0);
		int num = -1;
		num = userMessageService.updateUserMessage(userMessage);
		Assert.assertTrue("修改成功", num >= 0);
		for (int i = 0; i < 10; i++) {
			userMessageList.add(new UserMessage(String.valueOf(r.nextInt(200)), "asdhga", (short) 1,
					Long.parseLong(String.valueOf(r.nextInt(200))), (short) 1,
					Long.parseLong(String.valueOf(r.nextInt(200))), (short) 1, true, true, true, null, null));
		}

		userMessageService.addUserMessageBatch(userMessageList);
	}*/
	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateMyMessageDetail(){
		MyMessageVO myMessageVO = new MyMessageVO();
		myMessageVO.setUserId(1L);
		myMessageVO.setUserType(1);
		PageParameter pageParameter = new PageParameter<>();
		pageParameter.setParameter(myMessageVO);
		myMessageVO = userMessageService.updateMyMessageDetail(746L);
		Assert.assertNotNull("查询成功了", myMessageVO);
	}*/
	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetUserMessageById(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = userMessageService.getUserMessageById(1L);
		Assert.assertNotNull("修改消息成功", resultMap);
	}*/
	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void testListMessage(){
		
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListSendOject(){
		
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddOrUpdateUserMessage(){
		
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testMsgUploadFiles(){
		
	}
	*/
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateUserMessageIsDeletedByMsgId(){
		List<String> msgIds = new ArrayList<>();
		msgIds.add("3");
		msgIds.add("4");
		int num = -1;
		num = userMessageService.updateUserMessageIsDeletedByMsgId(msgIds);
		Assert.assertTrue("逻辑删除成功", num >= 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateCancelToWithdraw(){
		String msgId = "59ff414c91f3177a1a03eb69";
		int num = -1;
		num = userMessageService.updateCancelToWithdraw(msgId);
		Assert.assertTrue("取消撤回消息", num >= 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddUserMessageBatch(){
		List<UserMessage> userMessageList = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			userMessageList.add((new UserMessage(String.valueOf(random.nextInt(200)), "asdhga", (short) 1,
					Long.parseLong(String.valueOf(random.nextInt(200))), (short) 1,
					Long.parseLong(String.valueOf(random.nextInt(200))), (short) 1, true, true, true, null, null)));
		}
		userMessageService.addUserMessageBatch(userMessageList);
		Assert.assertTrue("批量添加失败", userMessageList.size() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddUserMessage(){
		Random random = new Random();
		UserMessage userMessage = userMessageService.addUserMessage(new UserMessage(String.valueOf(random.nextInt(200)),
				"asdhga", (short) 1, Long.parseLong(String.valueOf(random.nextInt(200))), (short) 1,
				Long.parseLong(String.valueOf(random.nextInt(200))), (short) 1, true, true, true, null, null));
		Assert.assertTrue("添加失败", userMessage.getId() > 0);
	}
	
	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void testListMessageState(){
		MessageStateVO messageStateVO = new MessageStateVO();
		PageParameter pageParameter = new PageParameter<>();
		PageResult pageResult = new PageResult<>();
		pageParameter.setParameter(messageStateVO);
		pageResult = userMessageService.listMessageState(pageParameter, "");
		Assert.assertNotNull("查询成功了", pageResult);
	}*/
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateUserMessage(){
		Random random = new Random();
		UserMessage userMessage = userMessageService.addUserMessage(new UserMessage(String.valueOf(random.nextInt(200)),
				"asdhga", (short) 1, Long.parseLong(String.valueOf(random.nextInt(200))), (short) 1,
				Long.parseLong(String.valueOf(random.nextInt(200))), (short) 1, true, true, true, null, null));
		int num = -1;
		num = userMessageService.updateUserMessage(userMessage);
		Assert.assertTrue("修改成功", num >= 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateToWithdraw(){
		int num = -1;
		num = userMessageService.updateToWithdraw(new UserMessage("1", true));
		Assert.assertTrue("修改成功", num >= 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteMessageByMsgId(){
		List<String> list = new ArrayList<String>();
		list.add("fd");
		int num = -1;
		num = userMessageService.deleteMessageByMsgId(list);
		Assert.assertTrue("修改成功", num >= 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListMyMessage(){
		MyMessageVO myMessageVO = new MyMessageVO();
		myMessageVO.setUserId(1L);
		myMessageVO.setUserType(1);
		PageParameter pageParameter = new PageParameter<>();
		PageResult pageResult = new PageResult<>();
		pageParameter.setParameter(myMessageVO);
		pageResult = userMessageService.listMyMessage(pageParameter);
		Assert.assertNotNull("查询成功了", pageResult);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListMyMessageOfIcon(){
		MyMessageVO myMessageVO = new MyMessageVO();
		myMessageVO.setUserId(1L);
		myMessageVO.setUserType(1);
		PageParameter pageParameter = new PageParameter<>();
		PageResult pageResult = new PageResult<>();
		pageParameter.setParameter(myMessageVO);
		pageResult = userMessageService.listMyMessageOfIcon(pageParameter);
		Assert.assertNotNull("查询成功了", pageResult);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateMyMessage(){
		Long[] msgIds = { 746L };
		int num = -1;
		num = userMessageService.updateMyMessage(msgIds);
		Assert.assertTrue("修改成功", num >= 0);
	}
	
	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateUserMessageOne() throws IOException{
		String[] attachment = new String[] { "1" };
		int num = -1;
		num = userMessageService.updateUserMessage(new Message("1", "aa"), "", "", attachment, attachment);
		Assert.assertTrue("修改成功", num >= 0);
	}*/

}
