package com.bc.pmpheep.back.service.test;


import java.util.Random;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.PmphUserMessage;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphUserMessageService;
import com.bc.pmpheep.back.util.Const;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphUserMessageSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphUserMessageSeviceTest.class);

	@Resource
	private PmphUserMessageService pmphUserMessageService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void addArea() throws Exception {
		Random r= new Random();
		PmphUserMessage a = new PmphUserMessage (new Long(String.valueOf(r.nextInt(200))),new Long(String.valueOf(r.nextInt(200))), true,false, null,null);
		pmphUserMessageService.addPmphUserMessage (a);
		logger.info("----PmphUserMessageService-------------------------------------------------------------------------");
		logger.info(a.toString());
		a.setUserId(new Long(String.valueOf(r.nextInt(200))));
		logger.info(pmphUserMessageService.updatePmphUserMessage(a).toString());
		logger.info(pmphUserMessageService.deletePmphUserMessageById(2L).toString());
		logger.info(pmphUserMessageService.getPmphUserMessageById(3L).toString());
	}

}
