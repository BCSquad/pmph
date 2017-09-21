package com.bc.pmpheep.back.service.test;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.PmphUserDepartmentVO;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphDepartmentSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphDepartmentSeviceTest.class);
	
	@Resource
	private PmphDepartmentService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK)  
    public void test() throws Exception{
//    	Random r =new Random();
//    	PmphDepartment a=new PmphDepartment(5L, "String path", "String dpName", r.nextInt(1000000), "String note") ;
//    	testService.addPmphDepartment(a);
//    	logger.info("---PmphDepartmentService---------------------------------------------------------------------------");
//    	logger.info(a.toString());
//    	a.setDpName(String.valueOf(r.nextLong()));
//    	logger.info(testService.updatePmphDepartment(a).toString());
//    	a.setId(2L);
//    	logger.info(testService.deletePmphDepartmentById(2L).toString());
//        logger.info(testService.getPmphDepartmentById(1L).toString());
        PmphUserDepartmentVO departmentVO = testService.getListPmphDepartment();
    	logger.info(departmentVO.toString());
    }
    
}





