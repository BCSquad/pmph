package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.BookSyncBak;
import com.bc.pmpheep.back.po.BookSyncConfirm;
import com.bc.pmpheep.back.po.BookSyncConfirmVO;
import com.bc.pmpheep.back.po.BookSyncLog;
import com.bc.pmpheep.back.vo.ActivitySourceVO;

import java.util.List;
import java.util.Map;

public interface BookSyncDao {

    void addSyncLog(BookSyncLog bookSyncLog);
    void addBookSynConfirm(BookSyncConfirm bookSyncConfirm);
    List<BookSyncConfirmVO> queryBookSyncConfirmList(Map<String,Object> Parameter);
    int queryBookSyncConfirmListcount(Map<String,Object> Parameter);
    BookSyncConfirmVO getBookSyncConfirmByid(Long id);
    int addBookSynBak(BookSyncBak bookSyncBak);
    int updateBookSyncConfirm(BookSyncConfirm bookSyncConfirm);
    int delectBooksyncConfirm(Long id);
    int delectBoolsyncConfirmByIsbn(String isbn);
    BookSyncBak getBookSyncBak(Long id);
    Integer updateBookSyncConfirmStatus(Map<String,Object> params);
    BookSyncLog getFullBookSyncLogBySyncTime();
    List<BookSyncConfirm> getBookSyncConfirmsByLogId(Long id);
    int updateSyncBookLogConfirmStatusById(Map<String,Object> map);
    BookSyncConfirm  getBookSyncConfirmByISBN(String isbn);
}
