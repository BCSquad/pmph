package com.bc.pmpheep.back.service.test;


import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.BookCorrection;
import com.bc.pmpheep.back.service.BookCorrectionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * BookCorrectionService 单元测试
 * 
 * @author mryang
 */
public class BookCorrectionServiceTest extends BaseTest {
    Logger              logger = LoggerFactory.getLogger(BookCorrectionServiceTest.class);

    @Resource
    private BookCorrectionService bookCorrectionService;
    
    Random r =new Random();
    
    BookCorrection bookCorrection=new BookCorrection(
    		(long)r.nextInt(200), (long)r.nextInt(200), r.nextInt(200), r.nextInt(200),
    		String.valueOf(r.nextInt(200)), String.valueOf(r.nextInt(200)), String.valueOf(r.nextInt(200)),
			true, true,
			String.valueOf(r.nextInt(200)), true, String.valueOf(r.nextInt(200)),
			true, new Date() );
	

    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testAddBookCorrection() {
    	//新增
    	bookCorrectionService.addBookCorrection(bookCorrection);
    	Assert.assertTrue("添加失败",bookCorrection.getId() > 0 );
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testDeleteBookCorrectionById() {
    	//删除
    	Assert.assertTrue("删除失败",bookCorrectionService.deleteBookCorrectionById((long)r.nextInt(200)) >= 0 );
    	//新增
    	bookCorrectionService.addBookCorrection(bookCorrection);
    	//删除
    	Assert.assertTrue("删除失败",bookCorrectionService.deleteBookCorrectionById(bookCorrection.getId()) > 0 );
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testUpdateBookCorrection() {
    	//新增
    	bookCorrectionService.addBookCorrection(bookCorrection);
    	Long id = bookCorrection.getId();
    	BookCorrection bookCorrection=new BookCorrection(
    	    		(long)r.nextInt(200), (long)r.nextInt(200), r.nextInt(200), r.nextInt(200),
    	    		String.valueOf(r.nextInt(200)), String.valueOf(r.nextInt(200)), String.valueOf(r.nextInt(200)),
    				true, true,
    				String.valueOf(r.nextInt(200)), true, String.valueOf(r.nextInt(200)),
    				true, new Date() );
    	bookCorrection.setId(id);
    	//修改
    	Assert.assertTrue("更新失败",bookCorrectionService.updateBookCorrection(bookCorrection) > 0 );
    	//修改
    	bookCorrection=new BookCorrection(
	    		(long)r.nextInt(200), (long)r.nextInt(200), r.nextInt(200), r.nextInt(200),
	    		String.valueOf(r.nextInt(200)), String.valueOf(r.nextInt(200)), String.valueOf(r.nextInt(200)),
				true, true,
				String.valueOf(r.nextInt(200)), true, String.valueOf(r.nextInt(200)),
				true, new Date() );
    	bookCorrection.setId((long)r.nextInt(200));
    	Assert.assertTrue("更新失败",bookCorrectionService.updateBookCorrection(bookCorrection) >= 0 );
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetBookCorrectionById() {
    	//新增
    	bookCorrectionService.addBookCorrection(bookCorrection);
    	BookCorrection  bookCorrection = bookCorrectionService.getBookCorrectionById(this.bookCorrection.getId());
    	//查询
    	Assert.assertNotNull("获取数据失败",bookCorrection);
    	bookCorrection = bookCorrectionService.getBookCorrectionById((long)r.nextInt(200));
    }
}













