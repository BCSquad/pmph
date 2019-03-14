package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.BookSyncDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookSyncBak;
import com.bc.pmpheep.back.po.BookSyncConfirm;
import com.bc.pmpheep.back.po.BookSyncConfirmVO;
import com.bc.pmpheep.back.po.BookSyncLog;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
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


    @Override
    public void addBookSyncLog(BookSyncLog bookSyncLog) {
        bookSyncDao.addSyncLog(bookSyncLog);
    }

    @Override
    public void addBookSyncConfirm(BookSyncConfirm bookSyncConfirm) {
        bookSyncDao.addBookSynConfirm(bookSyncConfirm);

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
    public BookSyncBak getBookSyncBak(Long id) {
        return bookSyncDao.getBookSyncBak(id);
    }

    @Override
    public Integer updateBookSyncConfirmStatus(Map<String, Object> params) {
        return bookSyncDao.updateBookSyncConfirmStatus(params);
    }

}
