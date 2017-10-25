package com.bc.pmpheep.back.service.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.dao.UserMessageDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
@SuppressWarnings("all")
public class UserMessageServiceTest extends BaseTest {
    Logger                     logger = LoggerFactory.getLogger(UserMessageServiceTest.class);

    @Resource
    private UserMessageService userMessageService;

    @Resource
    UserMessageDao             userMessageDao;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void getMessageStateListTest() throws IOException {
        SessionUtil.getShiroSessionUser().setAttribute(Const.SESSION_PMPH_USER, new PmphUser(1L));
        MessageStateVO messageStateVO = new MessageStateVO();
        PageParameter pageParameter = new PageParameter<>();
        List<String> list = new ArrayList<String>();
        list.add("fd");
        pageParameter.setParameter(messageStateVO);
        userMessageService.listMessageState(pageParameter, "");
        userMessageService.addOrUpdateUserMessage(new Message(null, "eee"),
                                                  "测试",
                                                  1,
                                                  "1",
                                                  null,
                                                  "1",
                                                  true,
                                                  new String[] { "1" },
                                                  "");
        userMessageService.addOrUpdateUserMessage(new Message("1", "eee"),
                                                  "测试",
                                                  2,
                                                  "2",
                                                  "1",
                                                  "1",
                                                  false,
                                                  new String[] { "1" },
                                                  "");
        userMessageService.updateUserMessage(new Message("1", "aa"), "", "");
        userMessageService.updateToWithdraw(new UserMessage("1", true));
        userMessageService.deleteMessageByMsgId(list);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void addUserMessageBatch() {
        List<UserMessage> userMessageList = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            userMessageList.add(new UserMessage(String.valueOf(r.nextInt(200)), "asdhga",
                                                (short) 1,
                                                Long.parseLong(String.valueOf(r.nextInt(200))),
                                                (short) 1,
                                                Long.parseLong(String.valueOf(r.nextInt(200))),
                                                (short) 1, true, true, true, null, null));
        }
        userMessageDao.addUserMessageBatch(userMessageList);
    }

}
