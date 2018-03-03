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
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookVideo;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PastBookVideoVO;
import com.bc.pmpheep.back.vo.BookVideoVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.back.dao.BookVideoDao;

@Service("bookVideoService")
public class BookVideoServiceImpl  implements BookVideoService {
	

	@Autowired
	private BookVideoDao bookVideoDao;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private FileService fileService;
	
	@Override
	public PageResult<BookVideoVO> getVideoList(Integer pageSize, Integer pageNumber, String bookName, Integer state,
			String upLoadTimeStart, String upLoadTimeEnd) {
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("start",    ((pageNumber-1)*pageSize) );
		map.put("pageSize", pageSize);
		bookName = StringUtil.toAllCheck(bookName);
		if(null != bookName){
			map.put("bookName", bookName);
		}
		if(null != state && 0 == state.intValue() ) {//all
			map.put("state", state);
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
		PageResult<BookVideoVO> BookVideoVO2lst= new  PageResult<BookVideoVO>();
		BookVideoVO2lst.setPageNumber(pageNumber);
		BookVideoVO2lst.setPageSize(pageSize);
		Integer  total = bookVideoDao.getVideoListTotal(map);
		if(null != total && total > 0 ) {
			BookVideoVO2lst.setRows(bookVideoDao.getVideoList(map));
		}
		BookVideoVO2lst.setTotal(total);
		return BookVideoVO2lst;
	}
	

	@Override
	public Integer deleteBookVideoByIds(List<Long> ids) throws CheckedServiceException {
		if(null == ids || ids.size() == 0 ){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		for(Long id :ids){
			if(null == id ){
				throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "有参数为空");
			}
		}
		return bookVideoDao.deleteBookVideoByIds(ids);
	}

	
	@Override
	public PageResult<PastBookVideoVO> getList(Integer pageSize, Integer pageNumber, String bookName) {
		Map<String, Object> map = new HashMap<String, Object>(3);
		if(!StringUtil.isEmpty(bookName)){
			bookName = StringUtil.toAllCheck(bookName.trim());
			map.put("bookName", bookName);
		}
		map.put("start",    ((pageNumber-1)*pageSize) );
		map.put("pageSize", pageSize);
		//获取书籍的分页
		PageResult<Book> listBooks =   bookService.listBook(pageSize,pageNumber, bookName);
		List<PastBookVideoVO> lst = new ArrayList<PastBookVideoVO> ();
		Integer total = 0;
		if(null != listBooks && null != listBooks.getTotal() && listBooks.getTotal().intValue() > 0 ){
			total = listBooks.getTotal();
			List<Long> bookIds = new ArrayList<Long> (listBooks.getRows().size());
			for(Book book: listBooks.getRows()){
				bookIds.add(book.getId());
				PastBookVideoVO pastBookVideoVO = new PastBookVideoVO();
				pastBookVideoVO.setAuthor(book.getAuthor())
						   .setBookId(book.getId())
						   .setBookname(book.getBookname())
						   .setImageUrl(book.getImageUrl())
						   .setRevision(book.getRevision())
						   .setSn(book.getSn());
				lst.add(pastBookVideoVO);
			}
			List<BookVideo> BookVideos= bookVideoDao.getBookVideoByBookIds(bookIds);
			for(PastBookVideoVO pastBookVideoVO: lst){
				List<BookVideo> bookVideos = new ArrayList<BookVideo>();
				for(BookVideo BookVideo: BookVideos){
					if(pastBookVideoVO.getBookId().equals(BookVideo.getBookId())){
						bookVideos.add(BookVideo);
					}
				}
				pastBookVideoVO.setBookVideos(bookVideos);
			}
		}
		PageResult<PastBookVideoVO> BookVideoVOlst= new  PageResult<PastBookVideoVO>();
		BookVideoVOlst.setPageNumber(pageNumber);
		BookVideoVOlst.setPageSize(pageSize);
		BookVideoVOlst.setTotal(total);
		BookVideoVOlst.setRows(lst);
		return BookVideoVOlst;
	}

	@Override
	public Integer updateBookVideo(BookVideo bookVideo)
			throws CheckedServiceException {
		if(null == bookVideo || null == bookVideo.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return bookVideoDao.updateBookVideo(bookVideo);
	}

	
	
	@Override
	public Integer addBookVideo(HttpServletRequest request,  BookVideo bookVideo)  throws CheckedServiceException, Exception{
		String tempFileId = bookVideo.getCover();
		bookVideo.setCover("---");
		// 验证 、、。。。。。内部产生数据不需验证。。。。。。。。
		String oldPath = bookVideo.getOrigPath() ;
		String oldUUid = oldPath.substring(oldPath.lastIndexOf(System.getProperty("file.separator"))+1,oldPath.lastIndexOf("."));
		BookVideo bookVideo2 =  bookVideoDao.getBookVideoByOldPath(oldUUid);
		//如果前台已经存入了，那么这里需要更新
		if(null != bookVideo2){
			bookVideo2.setBookId(bookVideo.getBookId());
			bookVideo2.setTitle(bookVideo.getTitle());
			bookVideo2.setUserId(0L);
			bookVideo2.setCover(bookVideo.getCover());
			bookVideoDao.updateBookVideo(bookVideo2);
			bookVideo.setId(bookVideo2.getId());
		}else {
			bookVideo.setPath("-");
			bookVideo.setFileName("-");
			bookVideo.setFileSize(0L);
			bookVideoDao.addBookVideo(bookVideo);
		}
		//保存fengmian文件
		byte[] fileByte = (byte[]) request.getSession(false).getAttribute(tempFileId);
		String fileName = (String) request.getSession(false).getAttribute("fileName_" + tempFileId);
		InputStream sbs = new ByteArrayInputStream(fileByte);
		String coverId = fileService.save(sbs, fileName, FileType.BOOKVEDIO_CONER,bookVideo.getId());
		bookVideo.setCover(coverId);
		return bookVideoDao.updateBookVideo(bookVideo);
	}


	

	
	
	
	
}
