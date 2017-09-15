package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphGroupSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupSeviceTest.class);
	
	@Resource
	private PmphGroupService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	PmphGroup pmphGroup=new PmphGroup("String groupName", "String groupImage",
    			Long.parseLong(String.valueOf(r.nextInt(200))) , Long.parseLong(String.valueOf(r.nextInt(200)))," String note", null,
        		null)  ;
    	logger.info("---PmphGroupService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addPmphGroup(pmphGroup);
    	logger.info(pmphGroup.toString());
    	//修改
    	pmphGroup.setGroupName(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updatePmphGroup(pmphGroup).toString());
    	//删除
    	logger.info(testService.deletePmphGroupById(1L).toString());
    	//查询
    	logger.info(testService.getPmphGroupById(3L).toString());
    	
    }
    
    
    
}





