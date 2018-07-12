package com.bc.pmpheep.back.service.common.test;

import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MrYang
 * @CreateDate 2017年11月20日 上午11:28:44
 *
 **/
public class SystemMessageServiceTest extends BaseTest  {
	Logger logger = LoggerFactory.getLogger(SystemMessageServiceTest.class);

	@Resource
	private SystemMessageService systemMessageService;

	Long materialId = 6L;

	Long[] orgIds = new Long[] { 530L };

	Long bookId = 158L;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testMaterialSend() throws CheckedServiceException, IOException {
		systemMessageService.materialSend(materialId, Arrays.asList(orgIds));
	}

	// @Test
	// @Rollback(Const.ISROLLBACK)
	// public void testSendWhenConfirmFirstEditor() throws CheckedServiceException,
	// IOException {
	// systemMessageService.sendWhenConfirmFirstEditor(bookId) ;
	// }

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testSendWhenInviteJoinGroup() throws CheckedServiceException, IOException {
		String inviterName = "张三";
		Long groupId = 5L;
		Long[] invitedPersonIds = new Long[] { 530L, 270L, 79L };
		systemMessageService.sendWhenInviteJoinGroup(inviterName, groupId, Arrays.asList(invitedPersonIds),
				new Short("1"));
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testSendWhenQuitGroup() throws CheckedServiceException, IOException {
		String inviterName = "张三";
		Long groupId = 38L;
		systemMessageService.sendWhenQuitGroup(inviterName, groupId);

	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testSendWhenTeacherCertificationAudit() throws CheckedServiceException, IOException {
		String auditorOrgName = "首都也可大学";
		systemMessageService.sendWhenTeacherCertificationAudit(auditorOrgName, Arrays.asList(orgIds), true);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testSendWhenManagerCertificationAudit() throws CheckedServiceException, IOException {
		systemMessageService.sendWhenManagerCertificationAudit(Arrays.asList(orgIds), true,"",340L);

	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testSendWhenSubmitDeclarationForm() throws CheckedServiceException, IOException {
		systemMessageService.sendWhenSubmitDeclarationForm(5L);
		systemMessageService.sendWhenSubmitDeclarationForm(6L);

	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testSendWhenDeclarationFormAudit() throws CheckedServiceException, IOException {
		systemMessageService.sendWhenDeclarationFormAudit(5L, true, "");

	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testSendWhenReceiptAudit() throws CheckedServiceException, IOException {
		systemMessageService.sendWhenReceiptAudit(5L, true);

	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testSendWhenInformalEssayAudit() throws CheckedServiceException, IOException {
		systemMessageService.sendWhenInformalEssayAudit(5L, true);

	}

	@Test
	public void TestReplace(){
		String str = "《<font color='red'>1212</font>》的编写团队遴选已结束，贵校共sum位老师当选，名单如下：</br>[<font color='red'>王欣然</font>] - 1 - 编委、数字编委</br>[<font color='red'>张臻</font>] - 2 - 编委</br>[<font color='red'>钟丹丹1方法</font>] - 1 - 编委、数字编委</br>[<font color='red'>张成明</font>] - 2 - 主编</br>[<font color='red'>张成明</font>] - 3 - 编委、数字编委</br>[<font color='red'>钱聪</font>] - 3 - 第一主编、" +
				"数字编委</br>[<font color='red'>冯浩</font>] - 1 - 第一主编</br>[<font color='red'>冯浩</font>] - " +
				"2 - 第一主编</br>[<font color='red'>冯浩</font>] - 3 - 编委、数字编委";
		Pattern r = Pattern.compile("sum");
		Matcher m = r.matcher(str);
		str = m.replaceAll("1111");

		System.out.println(str);
	}
	//
	// @Test
	// @Rollback(Const.ISROLLBACK)
	// public void testSendWhenPubfinalResult() throws CheckedServiceException,
	// IOException {
	// systemMessageService.sendWhenPubfinalResult(bookId) ;
	// }
	//
}
