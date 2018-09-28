package com.bc.pmpheep.back.service.test;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class TextbookServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(TextbookServiceTest.class);
	
	@Resource
	private TextbookService textbookService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetExcelDecAndTextbooks(){
		Long[] ids = new Long[2];
		ids[0] = 488L;
		ids[1] = 489L;
		System.out.println(JSON.toJSONString(textbookService.getExcelDecAndTextbooks(ids)));
	}
	
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testAddTextbook(){
    	Textbook textbook = new Textbook();
    	textbook.setMaterialId(23L);
    	textbook.setTextbookName("社会心理学");
    	textbook.setTextbookRound(5);
    	textbook.setSort(12);
    	textbook.setFounderId(77L);
    	textbook = textbookService.addTextbook(textbook);
    	Assert.assertTrue("数据添加失败", textbook.getId() > 0);
    }
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDeleteTextbook(){
    	long id = add().getId();
    	Integer count = textbookService.deleteTextbookById(id);
    	Assert.assertTrue("数据删除失败", count > 0);
    }
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateTextbook(){
    	Textbook textbook = add();
    	textbook.setMaterialId(17L);
    	textbook.setSort(6);
    	Integer count = textbookService.updateTextbook(textbook);
    	Assert.assertTrue("数据更新失败", count > 0);
    }
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetTextbook(){
    	long id = add().getId();
    	Textbook textbook = textbookService.getTextbookById(id);
    	Assert.assertNotNull("获取教材书籍信息失败", textbook);
    }
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testListTextbook(){
    	add();
    	List<Textbook> list = new ArrayList<>();
    	list = textbookService.getTextbookByMaterialId(1L);
    	Assert.assertTrue("获取教材书籍信息集合失败", list.size() > 1);
    }
    private Textbook add(){
    	Textbook textbook = new Textbook();
    	textbook.setMaterialId(1L);
    	textbook.setTextbookName("心理诊断学");
    	textbook.setTextbookRound(5);
    	textbook.setSort(2);
    	textbook.setFounderId(2L);
    	textbookService.addTextbook(textbook);
    	Textbook textbook2 = new Textbook(2L, "心理统计学", 3, 1, 9L);
    	textbookService.addTextbook(textbook2);
    	Textbook textbook3 = new Textbook(1L, "变态心理学", 8, 3, 10L);
    	textbookService.addTextbook(textbook3);
    	return textbook3;
    }
    
}





