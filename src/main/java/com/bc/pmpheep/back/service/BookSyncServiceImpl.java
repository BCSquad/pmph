package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.BookDao;
import com.bc.pmpheep.back.dao.BookSyncDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import jdk.jfr.events.ThrowablesEvent;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookSyncServiceImpl implements BookSyncService {
    @Autowired
    BookSyncDao bookSyncDao;

    @Autowired
    BookDao bookDao;


    @Override
    public void addBookSyncLog(BookSyncLog bookSyncLog) {
        bookSyncDao.addSyncLog(bookSyncLog);
    }

    @Override
    public Long addBookSyncConfirm(BookSyncConfirm bookSyncConfirm) {
       return bookSyncDao.addBookSynConfirm(bookSyncConfirm);

    }
    @Override
    public   PageResult<BookSyncConfirmVO>  queryBookSyncConfirmList(Integer pageSize,
                                                            Integer pageNumber,
                                                            String bookName,
                                                            String synchronizationType,
                                                            String syncTimeStart,
                                                            String syncTimeEnd,
    Boolean confirm) {
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("start", ((pageNumber - 1) * pageSize));
        parameter.put("pageSize", pageSize);
        parameter.put("confirm",confirm);
        bookName = StringUtil.toAllCheck(bookName);
        if (null != bookName) {
            parameter.put("bookName", bookName);
        }
        if (StringUtil.notEmpty(synchronizationType)) {//all
            parameter.put("synchronizationType", synchronizationType);
        }
        //yyyy-MM-dd
        Timestamp startTime = null;
        if (StringUtil.notEmpty(syncTimeStart)) {
            startTime = DateUtil.str2Timestam(syncTimeStart);
            parameter.put("startTime", syncTimeStart);
        }
        if (StringUtil.notEmpty(syncTimeEnd)) {
            Timestamp endTime = DateUtil.str2Timestam(syncTimeEnd);
            if (ObjectUtil.notNull(startTime)) {
                if (startTime.compareTo(endTime) == 0) {
                    endTime.setTime(startTime.getTime() + 24 * 60 * 60 * 1000);
                }
                parameter.put("endTime", syncTimeEnd);
            }
        }
        PageResult<BookSyncConfirmVO> bookSyncConfirms = new PageResult<BookSyncConfirmVO>();


        bookSyncConfirms.setPageNumber(pageNumber);
        bookSyncConfirms.setPageSize(pageSize);
        Integer total = bookSyncDao.queryBookSyncConfirmListcount(parameter);
        if (null != total && total > 0) {
            List<BookSyncConfirmVO> bookSyncConfirmVOS = bookSyncDao.queryBookSyncConfirmList(parameter);
            bookSyncConfirms.setRows(bookSyncConfirmVOS);
        }
        bookSyncConfirms.setTotal(total);

        return bookSyncConfirms;
    }

    @Override
    public BookSyncConfirmVO getBookSyncConfirmByid(Long id) {

        return bookSyncDao.getBookSyncConfirmByid(id);
    }

    @Override
    public BookSyncConfirm getBookSyncConfirmByISBN(String isbn) {
        return  bookSyncDao.getBookSyncConfirmByISBN(isbn);
    }

    @Override
    public int addBookSynBak(BookSyncBak bookSyncBak) {
        return bookSyncDao.addBookSynBak(bookSyncBak);
    }

    @Override
    public int updateBookSynConfirm(BookSyncConfirm bookSyncConfirm) {
        return bookSyncDao.updateBookSyncConfirm(bookSyncConfirm);
    }

    @Override
    public int delectBooksyncConfirm(Long id) {
        return bookSyncDao.delectBooksyncConfirm(id);
    }

    @Override
    public int delectBooksyncConfirmByIsbn(String isbn) {
        return bookSyncDao.delectBoolsyncConfirmByIsbn(isbn);
    }

    @Override
    public BookSyncBak getBookSyncBak(Long id) {
        return bookSyncDao.getBookSyncBak(id);
    }

    @Override
    public Integer updateBookSyncConfirmStatus(Map<String, Object> params) {
        return bookSyncDao.updateBookSyncConfirmStatus(params);
    }

    @Override
    public List<BookSyncLog> getFullBookSyncLogBySyncTime() {
        return bookSyncDao.getFullBookSyncLogBySyncTime();
    }

    @Override
    public List<BookSyncConfirm> getBookConfirmsByLogId(Long id) {
        return bookSyncDao.getBookSyncConfirmsByLogId(id);
    }

    @Override
    public int updateSyncBookLogConfirmStatusById(Map<String, Object> map) {
        return bookSyncDao.updateSyncBookLogConfirmStatusById(map);
    }

    @Override
    public int updateBookSaleByVns(List<Map<String, Object>> maps) {
        int res=1;
        for(Map<String,Object> map: maps){
            String vn = MapUtils.getString(map,"vn");
            Book bookByBookVn = bookDao.getBookByBookVn(vn);
            if(ObjectUtil.notNull(bookByBookVn)){
                bookByBookVn.setSales(MapUtils.getLong(map,"sale"));
                try {
                    bookDao.updateBook(bookByBookVn);
                }catch (Exception e){
                    e.printStackTrace();
                    res=0;
                }
            }else{
                throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
                        "销量同步参数vn号未找到");
            }
        }
        return res;
    }

}
