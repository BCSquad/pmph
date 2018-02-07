package com.bc.pmpheep.back.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.BookVedioDao;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookVedio;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookVedioVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;




@Service("bookVedioService")
public class BookVedioServiceImpl  implements BookVedioService {
	

	@Autowired
	private BookVedioDao bookVedioDao;
	
	@Autowired
	private BookService bookService;
	

	@Override
	public Integer deleteBookVedioByIds(List<Long> ids) throws CheckedServiceException {
		if(null == ids || ids.size() == 0 ){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		for(Long id :ids){
			if(null == id ){
				throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "有参数为空");
			}
		}
		return bookVedioDao.deleteBookVedioByIds(ids);
	}

	
	@Override
	public PageResult<BookVedioVO> getList(Integer pageSize, Integer pageNumber, String bookName) {
		Map<String, Object> map = new HashMap<String, Object>(3);
		if(!StringUtil.isEmpty(bookName)){
			bookName = StringUtil.toAllCheck(bookName.trim());
			map.put("bookName", bookName);
		}
		map.put("start",    ((pageNumber-1)*pageSize) );
		map.put("pageSize", pageSize);
		//获取书籍的分页
		PageResult<Book> listBooks =   bookService.listBook(pageSize,pageNumber, bookName);
		List<BookVedioVO> lst = new ArrayList<BookVedioVO> ();
		Integer total = 0;
		if(null != listBooks && null != listBooks.getTotal() && listBooks.getTotal().intValue() > 0 ){
			total = listBooks.getTotal();
			List<Long> bookIds = new ArrayList<Long> (listBooks.getRows().size());
			for(Book book: listBooks.getRows()){
				bookIds.add(book.getId());
				BookVedioVO bookVedioVO = new BookVedioVO();
				bookVedioVO.setAuthor(book.getAuthor())
						   .setBookId(book.getId())
						   .setBookname(book.getBookname())
						   .setImageUrl(book.getImageUrl())
						   .setRevision(book.getRevision())
						   .setSn(book.getSn());
				lst.add(bookVedioVO);
			}
			List<BookVedio> BookVedios= bookVedioDao.getBookVedioByBookIds(bookIds);
			for(BookVedioVO bookVedioVO: lst){
				List<BookVedio> bookVedios = new ArrayList<BookVedio>();
				for(BookVedio BookVedio: BookVedios){
					if(bookVedioVO.getBookId().equals(BookVedio.getBookId())){
						bookVedios.add(BookVedio);
					}
				}
				bookVedioVO.setBookVedios(bookVedios);
			}
		}
		PageResult<BookVedioVO> BookVedioVOlst= new  PageResult<BookVedioVO>();
		BookVedioVOlst.setPageNumber(pageNumber);
		BookVedioVOlst.setPageSize(pageSize);
		BookVedioVOlst.setTotal(total);
		BookVedioVOlst.setRows(lst);
		return BookVedioVOlst;
	}

	@Override
	public Integer updateBookVedio(BookVedio bookVedio)
			throws CheckedServiceException {
		if(null == bookVedio || null == bookVedio.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return bookVedioDao.updateBookVedio(bookVedio);
	}

	
	
	
	
}
