package com.bc.pmpheep.back.service;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bc.pmpheep.back.dao.BookVedioDao;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookVedio;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookVedioVO;
import com.bc.pmpheep.back.vo.BookVedioVO2;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;




@Service("bookVedioService")
public class BookVedioServiceImpl  implements BookVedioService {
	

	@Autowired
	private BookVedioDao bookVedioDao;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private FileService fileService;
	
	@Override
	public PageResult<BookVedioVO2> getVedioList(Integer pageSize, Integer pageNumber, String bookName, Boolean isAuth,
			String upLoadTimeStart, String upLoadTimeEnd) {
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("start",    ((pageNumber-1)*pageSize) );
		map.put("pageSize", pageSize);
		bookName = StringUtil.toAllCheck(bookName);
		if(null != bookName){
			map.put("bookName", bookName);
		}
		if(null != isAuth) {
			map.put("isAuth", isAuth);
		}
		//yyyy-MM-dd
		if(StringUtil.notEmpty(upLoadTimeStart)) {
			Timestamp startTime = DateUtil.str2Timestam(upLoadTimeStart);
			map.put("startTime", startTime);
		}
		if(StringUtil.notEmpty(upLoadTimeEnd)) {
			Timestamp endTime = DateUtil.str2Timestam(upLoadTimeEnd);
			map.put("endTime", endTime);
		}
		PageResult<BookVedioVO2> BookVedioVO2lst= new  PageResult<BookVedioVO2>();
		BookVedioVO2lst.setPageNumber(pageNumber);
		BookVedioVO2lst.setPageSize(pageSize);
		Integer  total = bookVedioDao.getVedioListTotal(map);
		if(null != total && total > 0 ) {
			BookVedioVO2lst.setRows(bookVedioDao.getVedioList(map));
		}
		BookVedioVO2lst.setTotal(total);
		return BookVedioVO2lst;
	}
	

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

	
	
	@Override
	public Integer addBookVedio(HttpServletRequest request,  BookVedio bookVedio)  throws CheckedServiceException, Exception{
		String tempFileId = bookVedio.getCover();
		bookVedio.setCover("---");
		// 验证 、、。。。。。内部产生数据不需验证。。。。。。。。
		String oldPath = bookVedio.getOrigPath() ;
		String oldUUid = oldPath.substring(oldPath.lastIndexOf(System.getProperty("file.separator"))+1,oldPath.lastIndexOf("."));
		BookVedio bookVedio2 =  bookVedioDao.getBookVedioByOldPath(oldUUid);
		//如果前台已经存入了，那么这里需要更新
		if(null != bookVedio2){
			bookVedio2.setBookId(bookVedio.getBookId());
			bookVedio2.setTitle(bookVedio.getTitle());
			bookVedio2.setUserId(0L);
			bookVedio2.setCover(bookVedio.getCover());
			bookVedioDao.updateBookVedio(bookVedio2);
			bookVedio.setId(bookVedio2.getId());
		}else {
			bookVedio.setPath("-");
			bookVedio.setFileName("-");
			bookVedio.setFileSize(0L);
			bookVedioDao.addBookVedio(bookVedio);
		}
		//保存fengmian文件
		byte[] fileByte = (byte[]) request.getSession(false).getAttribute(tempFileId);
		String fileName = (String) request.getSession(false).getAttribute("fileName_" + tempFileId);
		InputStream sbs = new ByteArrayInputStream(fileByte);
		String coverId = fileService.save(sbs, fileName, FileType.BOOKVEDIO_CONER,bookVedio.getId());
		bookVedio.setCover(coverId);
		return bookVedioDao.updateBookVedio(bookVedio);
	}


	

	
	
	
	
}
