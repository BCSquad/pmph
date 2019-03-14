package com.bc.pmpheep.back.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.vo.BookVideoTrackVO;
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
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import java.io.IOException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@Service("bookVideoService")
public class BookVideoServiceImpl implements BookVideoService {

    static Logger logger = LoggerFactory.getLogger(BookVideoServiceImpl.class);

    @Autowired
    private BookVideoDao bookVideoDao;

    @Autowired
    private BookService bookService;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private WriterUserService writerUserService;

    @Override
    public PageResult<BookVideoVO> getVideoList(Integer pageSize, Integer pageNumber, String bookName, Integer state,
            String upLoadTimeStart, String upLoadTimeEnd) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("start", ((pageNumber - 1) * pageSize));
        map.put("pageSize", pageSize);
        bookName = StringUtil.toAllCheck(bookName);
        if (null != bookName) {
            map.put("bookName", bookName);
        }
        if (null != state && 0 != state.intValue()) {//all
            map.put("state", state);
        }
        //yyyy-MM-dd
        Timestamp startTime = null;
        if (StringUtil.notEmpty(upLoadTimeStart)) {
            startTime = DateUtil.str2Timestam(upLoadTimeStart);
            map.put("startTime", startTime);
        }
        if (StringUtil.notEmpty(upLoadTimeEnd)) {
            Timestamp endTime = DateUtil.str2Timestam(upLoadTimeEnd);
            if (ObjectUtil.notNull(startTime)) {
                if (startTime.compareTo(endTime) == 0) {
                    endTime.setTime(startTime.getTime() + 24 * 60 * 60 * 1000);
                }
                map.put("endTime", endTime);
            }
        }
        PageResult<BookVideoVO> BookVideoVO2lst = new PageResult<BookVideoVO>();
        BookVideoVO2lst.setPageNumber(pageNumber);
        BookVideoVO2lst.setPageSize(pageSize);
        Integer total = bookVideoDao.getVideoListTotal(map);
        if (null != total && total > 0) {
            BookVideoVO2lst.setRows(bookVideoDao.getVideoList(map));
        }
        BookVideoVO2lst.setTotal(total);
        return BookVideoVO2lst;
    }

    @Override
    public List<BookVideoTrackVO> getVideoTrackList(Integer pageSize, Integer pageNumber, String bookName, Integer state,
                                                        String upLoadTimeStart, String upLoadTimeEnd) {
        Map<String, Object> map = new HashMap<String, Object>(3);


        if (null != bookName&&""==bookName) {
            map.put("bookName", bookName);
        }
        if (null != state && 0 != state.intValue()) {//all
            map.put("state", state);
        }
        //yyyy-MM-dd
        Timestamp startTime = null;
        if (StringUtil.notEmpty(upLoadTimeStart)) {
            startTime = DateUtil.str2Timestam(upLoadTimeStart);
            map.put("startTime", startTime);
        }
        if (StringUtil.notEmpty(upLoadTimeEnd)) {
            Timestamp endTime = DateUtil.str2Timestam(upLoadTimeEnd);
            if (ObjectUtil.notNull(startTime)) {
                if (startTime.compareTo(endTime) == 0) {
                    endTime.setTime(startTime.getTime() + 24 * 60 * 60 * 1000);
                }
                map.put("endTime", endTime);
            }
        }
        List<BookVideoTrackVO> BookVideoVO2lst = new ArrayList<BookVideoTrackVO>();

        Integer total = bookVideoDao.getVideoListTotal(map);
        if (null != total && total > 0) {
            BookVideoVO2lst=bookVideoDao.getVideoTrackList(map);
        }

        return BookVideoVO2lst;
    }

    @Override
    public Integer deleteBookVideoByIds(List<Long> ids) throws CheckedServiceException {
        if (null == ids || ids.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        for (Long id : ids) {
            if (null == id) {
                throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "有参数为空");
            }
        }
        return bookVideoDao.deleteBookVideoByIds(ids);
    }

    @Override
    public PageResult<PastBookVideoVO> getList(Integer pageSize, Integer pageNumber, String bookName) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        if (!StringUtil.isEmpty(bookName)) {
            bookName = StringUtil.toAllCheck(bookName.trim());
            map.put("bookName", bookName);
        }
        map.put("start", ((pageNumber - 1) * pageSize));
        map.put("pageSize", pageSize);
        //获取书籍的分页
        PageResult<Book> listBooks = bookService.listBook(pageSize, pageNumber, bookName);
        List<PastBookVideoVO> lst = new ArrayList<PastBookVideoVO>();
        Integer total = 0;
        if (null != listBooks && null != listBooks.getTotal() && listBooks.getTotal().intValue() > 0) {
            total = listBooks.getTotal();
            List<Long> bookIds = new ArrayList<Long>(listBooks.getRows().size());
            for (Book book : listBooks.getRows()) {
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
            List<BookVideo> BookVideos = bookVideoDao.getBookVideoByBookIds(bookIds);
            for (PastBookVideoVO pastBookVideoVO : lst) {
                List<BookVideo> bookVideos = new ArrayList<BookVideo>();
                for (BookVideo BookVideo : BookVideos) {
                    if (pastBookVideoVO.getBookId().equals(BookVideo.getBookId())) {
                        bookVideos.add(BookVideo);
                    }
                }
                pastBookVideoVO.setBookVideos(bookVideos);
            }
        }
        PageResult<PastBookVideoVO> BookVideoVOlst = new PageResult<PastBookVideoVO>();
        BookVideoVOlst.setPageNumber(pageNumber);
        BookVideoVOlst.setPageSize(pageSize);
        BookVideoVOlst.setTotal(total);
        BookVideoVOlst.setRows(lst);
        return BookVideoVOlst;
    }

    @Override
    public Integer updateBookVideo(BookVideo bookVideo)
            throws CheckedServiceException {
        if (null == bookVideo || null == bookVideo.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return bookVideoDao.updateBookVideo(bookVideo);
    }

    @Override
    public Integer addBookVideo(String sessionId, BookVideo bookVideo, MultipartFile cover)
            throws CheckedServiceException, IOException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(bookVideo.getBookId()) || StringUtil.isEmpty(bookVideo.getTitle())
                || StringUtil.isEmpty(bookVideo.getFileName()) || StringUtil.isEmpty(bookVideo.getPath())
                || StringUtil.isEmpty(bookVideo.getOrigFileName()) || StringUtil.isEmpty(bookVideo.getOrigPath())
                || ObjectUtil.isNull(bookVideo.getFileSize()) || ObjectUtil.isNull(bookVideo.getOrigFileSize())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        bookVideo.setUserId(0L);
        bookVideo.setIsAuth(true);
        bookVideo.setAuthUserId(pmphUser.getId());
        bookVideo.setAuthDate(new Date());
        bookVideo.setCover("DEFAULT");//暂设为默认值
        bookVideoDao.addBookVideo(bookVideo);
        /* 保存封面 */
        String coverId = fileService.save(cover, FileType.BOOKVEDIO_CONER, bookVideo.getId());
        bookVideo.setCover(coverId);
        return bookVideoDao.updateBookVideo(bookVideo);
    }

    @Override
    public Integer addBookVideoFront(Long userId, BookVideo bookVideo, MultipartFile cover)
            throws CheckedServiceException, IOException {
        WriterUser user = writerUserService.get(userId);
        if (ObjectUtil.isNull(user)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(bookVideo.getBookId()) || StringUtil.isEmpty(bookVideo.getTitle())
                || StringUtil.isEmpty(bookVideo.getFileName()) || StringUtil.isEmpty(bookVideo.getPath())
                || StringUtil.isEmpty(bookVideo.getOrigFileName()) || StringUtil.isEmpty(bookVideo.getOrigPath())
                || ObjectUtil.isNull(bookVideo.getFileSize()) || ObjectUtil.isNull(bookVideo.getOrigFileSize())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        bookVideo.setUserId(userId);
        bookVideo.setIsAuth(false);
        bookVideo.setCover("DEFAULT");//暂设为默认值
        bookVideoDao.addBookVideo(bookVideo);
        /* 保存封面 */
        String coverId = fileService.save(cover, FileType.BOOKVEDIO_CONER, bookVideo.getId());
        bookVideo.setCover(coverId);
        return bookVideoDao.updateBookVideo(bookVideo);
    }
}
