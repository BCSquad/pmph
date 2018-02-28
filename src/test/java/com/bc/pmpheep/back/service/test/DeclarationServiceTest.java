/**
 * 
 */
package com.bc.pmpheep.back.service.test;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.DeclarationListVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DeclarationService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 下午3:24:59
 */
public class DeclarationServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DeclarationServiceTest.class);
	@Resource
	DeclarationService declarationService;
	
	@SuppressWarnings("unused")
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testPageDeclaration (){
		Integer pageNumber =null;
		Integer pageSize=null;
		Long materialId= 117L; 
		String textBookids ="[489,491,493,495,505,540]";
		String realname=null;
		String position=null;
		String title=null;
		String orgName=null;
		Long orgId=null;
		String unitName=null;
		Integer positionType=null;
		Integer onlineProgress=null;
		Integer offlineProgress=null;
		PageResult<DeclarationListVO> page=declarationService.pageDeclaration(
					pageNumber, pageSize, materialId, textBookids, realname, position, title, orgName, orgId,unitName, positionType, onlineProgress, offlineProgress, null
				);
		
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDeclaration(){
		Declaration declaration = new Declaration();
		declaration.setMaterialId(5L);
		declaration.setUserId(6L);
		declaration = declarationService.addDeclaration(declaration);
		Assert.assertTrue("添加数据失败", declaration.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDeclaration(){
		long id = add().getId();
		Integer count = declarationService.deleteDeclarationById(id);
		Assert.assertTrue("删除数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDeclaration(){
		Declaration declaration = add();
		declaration.setMaterialId(10L);
		declaration.setUserId(9L);
		Integer count = declarationService.updateDeclaration(declaration);
		Assert.assertTrue("数据更新失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDeclaration(){
		long id = add().getId();
		Declaration declaration = declarationService.getDeclarationById(id);
		Assert.assertNotNull("获取作家申报表信息失败", declaration);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDeclaration(){
		add();
		List<Declaration> list = new ArrayList<>();
		list = declarationService.getDeclarationByMaterialId(2L);
		Assert.assertTrue("获取作家申报表信息集合失败", list.size() > 1);
	}
        
        @Test
        public void getDeclarationByMaterialIdAndUserId() {
            add();
            Declaration declaration = declarationService.getDeclarationByMaterialIdAndUserId(2L, 1L);
            Assert.assertNotNull("获取作家申报表信息失败", declaration);
        }
	
	private Declaration add(){
		Declaration declaration = new Declaration();
		declaration.setMaterialId(2L);
		declaration.setUserId(1L);
		declarationService.addDeclaration(declaration);
		Declaration declaration2 = new Declaration(2L, 2L);
		declarationService.addDeclaration(declaration2);
		Declaration declaration3 = new Declaration(1L, 3L);
		declarationService.addDeclaration(declaration3);
		return declaration3;
	}
	
	//因数据依赖强，注释该测试类
	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void confirmPaperList() throws CheckedServiceException, IOException{
		Declaration declaration = new Declaration();
		declaration.setMaterialId(2L);
		declaration.setRealname("徐玲");
		declaration.setUserId(1L);
		declaration.setOrgId(6L);
		declaration.setOfflineProgress(0);
		declarationService.addDeclaration(declaration);
		Declaration declarationConf = declarationService.getDeclarationById(declaration.getId());
		declarationConf.setOfflineProgress(2);
		Declaration declarations = declarationService.confirmPaperList(declarationConf.getId(), 
				declarationConf.getOfflineProgress(), declarationConf.getMaterialId());
		Assert.assertNotNull("确认收到纸质表失败", declarations);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void onlineProgress() throws CheckedServiceException, IOException{
		Declaration declaration = new Declaration();
		declaration.setMaterialId(2L);
		declaration.setRealname("徐玲");
		declaration.setUserId(1L);
		declaration.setOrgId(7L);
		declaration.setOnlineProgress(0);
		declarationService.addDeclaration(declaration);
		Declaration declarationConf = declarationService.getDeclarationById(declaration.getId());
		declarationConf.setOnlineProgress(3);
		Declaration declarations = declarationService.onlineProgress(declarationConf.getId(), 
				declarationConf.getOnlineProgress(), declarationConf.getMaterialId());
		Assert.assertNotNull("审核进度失败", declarations);
	}*/
}
