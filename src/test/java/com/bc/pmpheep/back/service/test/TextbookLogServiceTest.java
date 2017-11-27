package com.bc.pmpheep.back.service.test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.po.TextbookLog;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.TextbookLogService;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.Const;
/**
 * TextbookLogService 单元测试
 *
 * @author mryang
 */
public class TextbookLogServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(TextbookLogServiceTest.class);
	
	@Resource
	private TextbookLogService textbookLogService;
	
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testAddTextbook(){
    	TextbookLog textbookLog = new TextbookLog();
    	textbookLog.setDetail("xaingqing dkals;kd;");
		textbookLog.setIsPmphUpdater(true);
		textbookLog.setTextbookId(3L);
		textbookLog.setUpdaterId(4L);
    	textbookLogService.addTextbookLog(textbookLog);
    	Assert.assertTrue("数据添加失败", textbookLog.getId() > 0);
    }
    
    
}





