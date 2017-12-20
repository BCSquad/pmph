package com.bc.pmpheep.back.service.test;


import java.util.Random;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.BookEditor;
import com.bc.pmpheep.back.service.BookEditorService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * BookEditorService 单元测试
 * 
 * @author mryang
 */
public class BookEditorServiceTest extends BaseTest {
    Logger              logger = LoggerFactory.getLogger(BookEditorServiceTest.class);

    @Resource
    private BookEditorService bookEditorService;
    
    Random r =new Random();
    
    BookEditor bookEditor=new BookEditor((long)r.nextInt(200),(long)r.nextInt(200),(long)r.nextInt(200));
	

    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testAddBookEditor() {
    	//新增
    	bookEditorService.addBookEditor(bookEditor);
    	Assert.assertTrue("添加失败",bookEditor.getId() > 0 );
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testDeleteBookEditorById() {
    	//删除
    	Assert.assertTrue("删除失败",bookEditorService.deleteBookEditorById((long)r.nextInt(200)) >= 0 );
    	//新增
    	bookEditorService.addBookEditor(bookEditor);
    	//删除
    	Assert.assertTrue("删除失败",bookEditorService.deleteBookEditorById(bookEditor.getId()) > 0 );
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testUpdateBookEditor() {
    	//新增
    	bookEditorService.addBookEditor(bookEditor);
    	Long id = bookEditor.getId();
    	BookEditor bookEditor=new BookEditor((long)r.nextInt(200),(long)r.nextInt(200),(long)r.nextInt(200));
    	bookEditor.setId(id);
    	//修改
    	Assert.assertTrue("更新失败",bookEditorService.updateBookEditor(bookEditor) > 0 );
    	//修改
    	bookEditor=new BookEditor((long)r.nextInt(200),(long)r.nextInt(200),(long)r.nextInt(200));
    	bookEditor.setId((long)r.nextInt(200));
    	Assert.assertTrue("更新失败",bookEditorService.updateBookEditor(bookEditor) >= 0 );
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetBookEditorById() {
    	bookEditorService.addBookEditor(this.bookEditor);
    	BookEditor  bookEditor = bookEditorService.getBookEditorById(this.bookEditor.getId());
    	//查询
    	Assert.assertNotNull("获取数据失败",bookEditor);
    	bookEditor = bookEditorService.getBookEditorById((long)r.nextInt(200));
    }
}
