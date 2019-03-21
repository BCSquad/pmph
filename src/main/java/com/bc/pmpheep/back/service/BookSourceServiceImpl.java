package com.bc.pmpheep.back.service;
import com.bc.pmpheep.back.dao.BookSourceDao;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.BookSource;
import com.bc.pmpheep.back.vo.BookSourceVO;

import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

@Service
public class BookSourceServiceImpl implements BookSourceService {

    static Logger logger = LoggerFactory.getLogger(BookSourceServiceImpl.class);

    @Autowired
    private BookSourceDao bookSourceDao;

    @Autowired
    private BookService bookService;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private WriterUserService writerUserService;

    @Override
    public PageResult<BookSourceVO> getSourceList(Integer pageSize, Integer pageNumber, String bookName,String sourceName, Integer state,
            String upLoadTimeStart, String upLoadTimeEnd) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("start", ((pageNumber - 1) * pageSize));
        map.put("pageSize", pageSize);
        bookName = StringUtil.toAllCheck(bookName);

        sourceName = StringUtil.toAllCheck(sourceName);
        if (null != bookName) {
            map.put("bookName", bookName);
        }
        if (null != state && 0 != state.intValue()) {//all
            map.put("state", state);
        }
        if(null != sourceName){
            map.put("sourceName", sourceName);
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
        PageResult<BookSourceVO> BookSourceVO2lst = new PageResult<BookSourceVO>();
        BookSourceVO2lst.setPageNumber(pageNumber);
        BookSourceVO2lst.setPageSize(pageSize);
        Integer total = bookSourceDao.getSourceListTotal(map);
        if (null != total && total > 0) {
            BookSourceVO2lst.setRows(bookSourceDao.getSourceList(map));
        }
        BookSourceVO2lst.setTotal(total);
        return BookSourceVO2lst;
    }

    @Override
    public Integer deleteBookSourceByIds(List<Long> ids) throws CheckedServiceException {
        if (null == ids || ids.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        for (Long id : ids) {
            if (null == id) {
                throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "有参数为空");
            }
        }
        return bookSourceDao.deleteBookSourceByIds(ids);
    }




    @Override
    public Integer updateBookSource(BookSource bookSource)
            throws CheckedServiceException {
        if (null == bookSource || null == bookSource.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return bookSourceDao.updateBookSource(bookSource);
    }

    @Override
    public Integer addBookSource(String sessionId, BookSource bookSource, String[] file, HttpServletRequest request)
            throws CheckedServiceException, IOException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(bookSource.getBookId()) || StringUtil.isEmpty(bookSource.getSourceName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        bookSource.setUserId(0L);
        bookSource.setAuth(true);
        bookSource.setAuthId(pmphUser.getId());
        bookSource.setAuthDate(new Date());
        bookSource.setFileId("DEFAULT");//暂设为默认值
        bookSourceDao.addBookSource(bookSource);
        int flag=0;
        /* 保存封面 */
        if (ArrayUtil.isNotEmpty(file)) {
            for (int i = 0; i < file.length; i++) {
                byte[] fileByte = (byte[]) request.getSession(false).getAttribute(file[i]);
                String fileName =
                        (String) request.getSession(false).getAttribute("fileName_" + file[i]);
                InputStream sbs = new ByteArrayInputStream(fileByte);
                String gridFSFileId =
                        fileService.save(sbs, fileName, FileType.CMS_ATTACHMENT, bookSource.getId());
                if (StringUtil.isEmpty(gridFSFileId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                            CheckedExceptionResult.FILE_UPLOAD_FAILED,
                            "文件上传失败!");
                }
                bookSource.setFileId(gridFSFileId);
                flag=bookSourceDao.updateBookSource(bookSource);
            }
        }
        return flag;
        }
    @Override
    public Integer addBookSourceFront(Long userId, BookSource bookSource, MultipartFile cover)
            throws CheckedServiceException, IOException {
        WriterUser user = writerUserService.get(userId);
        if (ObjectUtil.isNull(user)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(bookSource.getBookId()) || StringUtil.isEmpty(bookSource.getSourceName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        bookSource.setUserId(userId);
        bookSource.setAuth(false);
        bookSource.setFileId("DEFAULT");//暂设为默认值
        bookSourceDao.addBookSource(bookSource);
        /* 保存封面 */
        String coverId = fileService.save(cover, FileType.BOOKVEDIO_CONER, bookSource.getId());
        bookSource.setFileId(coverId);
        return bookSourceDao.updateBookSource(bookSource);
    }

}
