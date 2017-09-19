package com.bc.pmpheep.back.service.test;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.MaterialExtraService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class MaterialExtraServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialExtraServiceTest.class);
	
	@Resource
	private MaterialExtraService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	MaterialExtra testPar=new MaterialExtra (new Long(r.nextInt(200)),"notice", "noticeAttachment", "note", "noteAttachment");
    	logger.info("---MaterialExtraService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addMaterialExtra(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setNotice(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updateMaterialExtra(testPar).toString());
    	//删除
    	logger.info(testService.deleteMaterialExtraById(16L).toString());
    	//查询
    	logger.info(testService.getMaterialExtraById(14L).toString());
    	
    }
    
    
    
}





