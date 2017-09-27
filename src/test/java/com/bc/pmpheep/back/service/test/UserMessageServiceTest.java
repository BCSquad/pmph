package com.bc.pmpheep.back.service.test;


import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ShiroSession;
import com.bc.pmpheep.back.vo.MessageStateVO;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class UserMessageServiceTest extends BaseTest {
    Logger              logger = LoggerFactory.getLogger(UserMessageServiceTest.class);

    @Resource
    private UserMessageService userMessageService;
    

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
    @Rollback(Const.ISROLLBACK)
    public void getMessageStateListTest() {
    	ShiroSession.getShiroSessionUser().setAttribute(Const.SESSION_PMPH_USER, new PmphUser(2L));;
    	MessageStateVO messageStateVO =new MessageStateVO();
        Page page=new Page();
		page.setParameter(messageStateVO);
        userMessageService.getMessageStateList(page);
    }

}
