package com.bc.pmpheep.back.service.common.test;

import java.io.IOException;
import java.util.Arrays;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;
/**
 *@author MrYang 
 *@CreateDate 2017年11月20日 上午11:28:44
 *
 **/
public class SystemMessageServiceTest extends BaseTest {
	Logger              logger = LoggerFactory.getLogger(SystemMessageServiceTest.class);

    @Resource
    private SystemMessageService systemMessageService;
    
    Long materialId =6L;
    
    Long[] orgIds = new Long[]{530L};
    
    Long bookId = 158L;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testMaterialSend() throws CheckedServiceException, IOException {
    	systemMessageService.materialSend(materialId,Arrays.asList(orgIds));
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenConfirmFirstEditor() throws CheckedServiceException, IOException {
    	systemMessageService.sendWhenConfirmFirstEditor(bookId) ;
  	}

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenInviteJoinGroup() throws CheckedServiceException, IOException {
    	String inviterName ="张三";
    	Long groupId = 5L;
    	Long[] invitedPersonIds = new Long[]{530L,270L,79L};
    	systemMessageService.sendWhenInviteJoinGroup(inviterName,groupId,Arrays.asList(invitedPersonIds),new Short("1")) ;
	}

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenQuitGroup() throws CheckedServiceException, IOException {
    	String inviterName ="张三";
    	Long groupId = 38L;
    	systemMessageService.sendWhenQuitGroup (inviterName,groupId) ;
	  	
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenTeacherCertificationAudit() throws CheckedServiceException, IOException {
    	String auditorOrgName ="首都也可大学";
	  	systemMessageService.sendWhenTeacherCertificationAudit(auditorOrgName ,Arrays.asList(orgIds),true);
	}

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenManagerCertificationAudit() throws CheckedServiceException, IOException {
    	systemMessageService.sendWhenManagerCertificationAudit(Arrays.asList(orgIds),true) ;
	  	
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenSubmitDeclarationForm() throws CheckedServiceException, IOException {
    	systemMessageService.sendWhenSubmitDeclarationForm(5L) ;
    	systemMessageService.sendWhenSubmitDeclarationForm(6L) ;
	  	
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenDeclarationFormAudit() throws CheckedServiceException, IOException {
    	systemMessageService.sendWhenDeclarationFormAudit(5L,true) ;
	  	
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenReceiptAudit() throws CheckedServiceException, IOException {
    	systemMessageService.sendWhenReceiptAudit (5L,true )  ;
	  	
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenInformalEssayAudit() throws CheckedServiceException, IOException {
    	systemMessageService.sendWhenInformalEssayAudit(5L,true) ;
	  	
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testSendWhenPubfinalResult() throws CheckedServiceException, IOException {
    	systemMessageService.sendWhenPubfinalResult(bookId) ;
    }
    
}
