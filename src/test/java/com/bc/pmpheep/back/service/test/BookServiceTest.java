package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookDetail;
import com.bc.pmpheep.back.po.BookUserLike;
import com.bc.pmpheep.back.po.BookUserMark;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.vo.BookPreferenceAnalysisVO;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.test.BaseTest;
import org.springframework.test.annotation.Rollback;

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

	@Test
	public void testAddBook() {
		Book book = this.addBook();
		logger.info("插入的Book对象=" + book.toString());
		Assert.assertNotNull("插入内容后返回的Book不应为空", book.getId());
	}

	@Test
	public void testAddBookDetail() {
		BookDetail bookDetail = this.addBookDetail();
		logger.info("插入的BookDetail对象=" + bookDetail.toString());
		Assert.assertNotNull("插入内容后返回的BookDetail不应为空", bookDetail.getId());
	}

	@Test
	public void testAddBookUserLike() {
		BookUserLike bookUserLike = this.addBookUserLike();
		logger.info("插入的BookUserLike对象=" + bookUserLike.toString());
		Assert.assertNotNull("插入内容后返回的BookUserLike不应为空", bookUserLike.getId());
	}

	@Test
	public void testAddBookUserMark() {
		BookUserMark bookUserMark = this.addBookUserMark();
		logger.info("插入的BookUserMark对象=" + bookUserMark.toString());
		Assert.assertNotNull("插入内容后返回的BookUserMark不应为空", bookUserMark.getId());
	}

	@Test
	public void testUpdateBookById() {
		String returnSring = "ERROR";
		Book book = this.addBook();
		Assert.assertNotNull("插入内容后返回的Book不应为空", book.getId());
		Long[] ids = { book.getId() };
		returnSring = bookService.updateBookById(ids, 1L, true, true, false, null, false,false);
		Assert.assertEquals("是否更新成功", "SUCCESS", returnSring);

	}

	@Test
	public void testDeleteBookById() {
		String returnSring = "ERROR";
		Book book = this.addBook();
		Assert.assertNotNull("插入内容后返回的Book不应为空", book.getId());
		returnSring = bookService.deleteBookById(book.getId());
		Assert.assertEquals("是否删除成功", "SUCCESS", returnSring);
	}

	@Test
	public void testListBookVO() {
		// listBookVO 分页初始化/查询图书详情
		PageParameter<BookVO> pageParameter = new PageParameter<>(1, 1);
		BookVO bookVO = new BookVO();
		bookVO.setName("书名1");
		bookVO.setIsNew(true);
		bookVO.setPath(null);
		bookVO.setIsOnSale(true);
		bookVO.setIsPromote(true);
		pageParameter.setParameter(bookVO);
		PageResult<BookVO> pageResult = bookService.listBookVO(pageParameter);
		Assert.assertTrue("查询结果为空：", pageResult.getRows().isEmpty());

	}

	@Test
	public void testAllSynchronization() {
		String returnSring = "ERROR";
		returnSring = bookService.AllSynchronization(1);
		Assert.assertEquals("书籍同步成功", "SUCCESS", returnSring);
	}

	@Test
	@Rollback(true)
	public void testAbuttingJoint() {
		String returnSring = "ERROR";
		String[] vns = { "2017005621",
			"2017005208",
			"2017005543",
			"2017005896",
			"2017005209",
			"2017005899",
			"2017005901",
			"2017005959",
			"2017005902",
			"2017006046"
		};
		returnSring = bookService.AbuttingJoint(vns, 1,null);
		Assert.assertEquals("书籍同步成功", "SUCCESS", returnSring);
	}



	@Test
	public void testGetBookPreferenceAnalysis() {
		PageParameter<BookPreferenceAnalysisVO> pageParameter = new PageParameter<>(1, 10);
		BookPreferenceAnalysisVO bookPreferenceAnalysisVO = new BookPreferenceAnalysisVO();
		bookPreferenceAnalysisVO.setBookname(null);
		pageParameter.setParameter(bookPreferenceAnalysisVO);
		PageResult<BookPreferenceAnalysisVO> pageResult = bookService.getBookPreferenceAnalysis(pageParameter);
		Assert.assertNotNull(pageResult.getRows());
	}

	private Book addBook() {
		// add 图书添加
		Book book = bookService.add(new Book(null, "生理学", "1234", "1", "作者", "出版社", null, null, 1, 1L, null, null, 99D,
				9D, "http:www.baidu.com", null, "d://ee", "d://aa", 0L, 0L, 0L, 0L, true, 999, null, true, 999, null,
				true, 999, null, 0L, true, null, null, null, null, null,null,null));
		return book;
	}

	private BookDetail addBookDetail() {
		// add 保存图书详情
		BookDetail bookDetail = bookService.add(new BookDetail(1L, "这是一本医学巨著"));
		return bookDetail;
	}

	private BookUserLike addBookUserLike() {
		// add 图书被点赞
		BookUserLike bookUserLike = bookService.add(new BookUserLike(1L, 2L, null));
		return bookUserLike;
	}

	private BookUserMark addBookUserMark() {
		// add 图书被收藏
		BookUserMark bookUserMark = bookService.add(new BookUserMark(2L, 5L, 7L, null));
		return bookUserMark;
	}

}
