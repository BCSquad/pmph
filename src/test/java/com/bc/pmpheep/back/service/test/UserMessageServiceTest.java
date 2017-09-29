package com.bc.pmpheep.back.service.test;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.dao.UserMessageDao;
import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ShiroSession;
import com.bc.pmpheep.back.vo.MessageStateVO;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
@SuppressWarnings("all" )
public class UserMessageServiceTest extends BaseTest {
    Logger              logger = LoggerFactory.getLogger(UserMessageServiceTest.class);

    @Resource
    private UserMessageService userMessageService;
    
    @Resource
    UserMessageDao userMessageDao;
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void getMessageStateListTest() {
    	ShiroSession.getShiroSessionUser().setAttribute(Const.SESSION_PMPH_USER, new PmphUser(2L));
    	MessageStateVO messageStateVO =new MessageStateVO();
        Page page=new Page();
		page.setParameter(messageStateVO);
        userMessageService.getMessageStateList(page);
    }
    
    
    
	@Test
    @Rollback(Const.ISROLLBACK)
    public void addUserMessageBatch() {
		List<UserMessage> userMessageList = new  ArrayList<>();
		Random r =new Random();
		for(int i=0;i<10;i++){
			userMessageList.add(new UserMessage(String.valueOf(r.nextInt(200)),(short)1, Long.parseLong(String.valueOf(r.nextInt(200))), 
					(short)1,Long.parseLong(String.valueOf(r.nextInt(200))), (short)1,
					true, true, true,
					null, null));
		}
		userMessageDao.addUserMessageBatch(userMessageList);
    }

}
