package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookDetail;
import com.bc.pmpheep.back.po.BookUserLike;
import com.bc.pmpheep.back.po.BookUserMark;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 图书单元测试
 * 
 * @author
 * @date 2017-11-06
 * @修改人 mr
 */
public class BookServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(BookServiceTest.class);

	@Resource
	private BookService bookService;

	// @Test
	@Rollback(Const.ISROLLBACK)
	public void serviceAllMethodTest() {
		// add 图书添加
		Book book = bookService.add(new Book("生理学", "1234", "1", "作者", "出版社", null, 1, 1L, null, null, 99D, 9D,
				"http:www.baidu.com", "d://ee", "d://aa", 0L, 0L, 0L, 0L, true, 999, null, true, 999, null, true, 999,
				null, 0L, true, null, null));
		Assert.assertNotNull("插入内容后返回的book.id不应为空", book.getId());
		// AbuttingJoint 通过本版编号来获取人卫系统上的图书商品详情
		// String [] vns={"2","3"};
		// String result=bookService.AbuttingJoint(vns, 2);
		// Assert.assertTrue("查询结果失败", "SUCCESS".equals(result)? true : false);
		// add 保存图书详情
		BookDetail bookDetail = bookService.add(new BookDetail(1L, "这是一本医学巨著"));
		Assert.assertNotNull("插入内容后返回bookDetail.id不应为空", bookDetail.getId());
		// add 图书被点赞
		BookUserLike bookUserLike = bookService.add(new BookUserLike(1L, 2L, null));
		Assert.assertNotNull("插入内容后返回bookUserlike.id不应为空", bookUserLike.getId());
		// add 图书被收藏
		BookUserMark bookUserMark = bookService.add(new BookUserMark(2L, 5L, 7L, null));
		Assert.assertNotNull("插入内容后返回bookUserMark.id不应为空", bookUserMark.getId());
		// listBookVO 分页初始化/查询图书详情
		// PageParameter<BookVO> pageParameter =new PageParameter<>(1, 1);
		// BookVO bookVO = new BookVO();
		// bookVO.setName("书名1");
		// bookVO.setIsNew(true);
		// bookVO.setPath(null);
		// bookVO.setIsOnSale(true);
		// bookVO.setIsPromote(true);
		// pageParameter.setParameter(bookVO);
		// PageResult<BookVO> pageResult=bookService.listBookVO(pageParameter);
		// Assert.assertTrue("查询的时候返回pageResult.getRows当前页面不为空",
		// pageResult.getRows().size() > 0 ? true : false);
		// updateBookById 修改
		Long[] ids = { 1L, 2L, 3L };
		String result1 = bookService.updateBookById(ids, 2L, true, true, false);
		Assert.assertTrue("是否修改成功", "SUCCESS".equals(result1) ? true : false);
		// deleteBookById 删除
		String result2 = bookService.deleteBookById((long) 2);
		Assert.assertTrue("是否删除成功", "SUCCESS".equals(result2) ? true : false);
	}

	 @Test
	 public void All() {
	 bookService.AllSynchronization(1);
	 }
}
