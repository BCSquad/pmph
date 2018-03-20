package com.bc.pmpheep.back.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecPositionTemp;
import com.bc.pmpheep.back.service.DecPositionTempService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 作家申报职位暂存表单元测试
 * @author Mr
 *
 */
public class DecPositionTempServiceTest extends BaseTest{
	Logger logger = LoggerFactory.getLogger(DecPositionTempServiceTest.class);
	@Autowired
	DecPositionTempService decPositionTempService;
    @Test
    @Rollback(Const.ISROLLBACK)
	public void testAddDecPositionTemp(){
		DecPositionTemp decPositionTemp=this.add();
		Assert.assertNotNull("添加失败", decPositionTemp.getId());
	}
    
    @Test
    @Rollback(Const.ISROLLBACK)
	public void testDeleteDecPositionTemp(){
		DecPositionTemp decPositionTemp=this.add();
		Assert.assertNotNull("删除失败", decPositionTempService.deleteDecPositionTemp(decPositionTemp.getId()));
	}
    
    @Test
    @Rollback(Const.ISROLLBACK)
	public void testDeleteDecPositionTempByTextbookId(){
		DecPositionTemp decPositionTemp=this.add();
		Assert.assertNotNull("删除失败", decPositionTempService.deleteDecPositionTempByTextbookId(decPositionTemp.getTextbookId()));
	}
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateDecPositionTemp(){
    	DecPositionTemp decPositionTemp=this.add();
    	decPositionTemp.setAuthorId(2L);
    	Assert.assertNotNull("修改失败", decPositionTempService.updateDecPositionTemp(decPositionTemp));
    }
    
	private DecPositionTemp add(){
   	 	DecPositionTemp decPositionTemp = new DecPositionTemp();
   	 	decPositionTemp.setChosenPosition(1);
	   	decPositionTemp.setDeclarationId(2L);
	   	decPositionTemp.setTextbookId(3L);
	   	decPositionTemp.setPresetPosition(4);
   	 	decPositionTempService.addDecPositionTemp(decPositionTemp);
   	 	DecPositionTemp decPositionTemp1 = new DecPositionTemp(3L,4L, 5, 6);
   	 	decPositionTempService.addDecPositionTemp(decPositionTemp1);
   	 	DecPositionTemp decPositionTemp2 = new DecPositionTemp(4L,5L, 6, 7);
	 	decPositionTempService.addDecPositionTemp(decPositionTemp2);
   	 	return decPositionTemp;
    }
}
