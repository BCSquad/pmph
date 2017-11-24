/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.service.DecTextbookService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecTextbookService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 上午9:24:19
 */
public class DecTextbookServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecTextbookServiceTest.class);

	@Resource
	DecTextbookService decTextbookService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDecTextbook() {
		DecTextbook decTextbook = new DecTextbook();
		decTextbook.setDeclarationId(2L);
		decTextbook.setMaterialName("社会心理学");
		decTextbook.setRank(2);
		decTextbook.setPosition(1);
		decTextbook.setPublisher("人民出版社");
		decTextbook.setPublishDate(new Date());
		decTextbook.setIsbn("987");
		decTextbook.setNote("专业选修");
		decTextbook.setSort(12);
		decTextbook = decTextbookService.addDecTextbook(decTextbook);
		Assert.assertTrue("添加数据", decTextbook.getId() > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecTextbook() {
		long id = add().getId();
		Integer count = decTextbookService.deleteDecTextbookById(id);
		Assert.assertTrue("删除数据失败", count > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecTextbook() {
		DecTextbook decTextbook = add();
		decTextbook.setIsbn("666");
		decTextbook.setPublishDate(new Date());
		Integer count = decTextbookService.updateDecTextbook(decTextbook);
		Assert.assertTrue("数据更新失败", count > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecTextbook() {
		long id = add().getId();
		DecTextbook decTextbook = decTextbookService.getDecTextbookById(id);
		Assert.assertNotNull("获取作家教材编写情况信息失败", decTextbook);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecTextbook() {
		add();
		List<DecTextbook> list = new ArrayList<>();
		list = decTextbookService.getListDecTextbookByDeclarationId(3L);
		Assert.assertTrue("获取作家教材编写情况信息集合失败", list.size() > 1);
	}

	private DecTextbook add() {
		DecTextbook decTextbook = new DecTextbook();
		decTextbook.setDeclarationId(3L);
		decTextbook.setMaterialName("神经脑科学");
		decTextbook.setRank(1);
		decTextbook.setPosition(1);
		decTextbook.setPublisher("科学出版社");
		decTextbook.setPublishDate(new Date());
		decTextbook.setIsbn("123");
		decTextbook.setNote("重点科目");
		decTextbook.setSort(7);
		decTextbookService.addDecTextbook(decTextbook);
		DecTextbook decTextbook2 = new DecTextbook(2L, "人体解剖学", 1, true, 2, "人民卫生出版社", new Date(), "321", "一级学科", null);
		decTextbookService.addDecTextbook(decTextbook2);
		DecTextbook decTextbook3 = new DecTextbook(3L, "内科学", 3, true, 3, "机械出版社", null, "798", null, 6);
		decTextbookService.addDecTextbook(decTextbook3);
		return decTextbook3;
	}
}
