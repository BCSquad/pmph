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

import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.service.DecAcadeService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecAcadeService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 上午11:26:59
 */
public class DecAcadeServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecAcadeServiceTest.class);
	@Resource
	DecAcadeService decAcadeService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDecAcade(){
		DecAcade decAcade = new DecAcade(3L, "神经科学研究会", 1, "会长", "名誉会长", 21);
		decAcade = decAcadeService.addDecAcade(decAcade);
		Assert.assertTrue("添加数据失败", decAcade.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecAcade(){
		long id = add().getId();
		Integer count = decAcadeService.deleteDecAcadeById(id);
		Assert.assertTrue("数据删除失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecAcade(){
		DecAcade decAcade = add();
		decAcade.setDeclarationId(2L);
		decAcade.setRank(1);
		Integer count =decAcadeService.updateDecAcade(decAcade);
		Assert.assertTrue("数据更新失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecAcade(){
		long id = add().getId();
		DecAcade decAcade = decAcadeService.getDecAcadeById(id);
		Assert.assertNotNull("作家兼职学术组织信息获取失败", decAcade);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecAcade(){
		add();
		List<DecAcade> list = new ArrayList<>();
		list = decAcadeService.getListDecAcadeByDeclarationId(5L);
		Assert.assertTrue("作家兼职学术组织信息集合获取失败", list.size() > 1);
	}
	
	private DecAcade add(){
		DecAcade decAcade = new DecAcade();
		decAcade.setDeclarationId(3L);
		decAcade.setOrgName("脑科学研究会");
		decAcade.setPosition("副会长");
		decAcade.setRank(3);
		decAcade.setNote("主管科研方向");
		decAcade.setSort(10);
		decAcadeService.addDecAcade(decAcade);
		DecAcade decAcade2 = new DecAcade(5L,"心理学研究会", 2, "副会长", "名誉主席", 12);
		decAcadeService.addDecAcade(decAcade2);
		DecAcade decAcade3 = new DecAcade(5L,"脑科学研究会", 3, "会员", "特邀成员",14);
		decAcadeService.addDecAcade(decAcade3);
		return decAcade3;
	}
}
