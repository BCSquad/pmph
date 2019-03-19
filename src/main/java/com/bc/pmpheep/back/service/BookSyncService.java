package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookSyncBak;
import com.bc.pmpheep.back.po.BookSyncConfirm;
import com.bc.pmpheep.back.po.BookSyncConfirmVO;
import com.bc.pmpheep.back.po.BookSyncLog;
import com.bc.pmpheep.back.vo.ActivitySourceVO;

import java.util.List;
import java.util.Map;

public interface BookSyncService {

    void addBookSyncLog(BookSyncLog bookSyncLog);
    void addBookSyncConfirm(BookSyncConfirm bookSyncConfirm);
    PageResult<BookSyncConfirmVO> queryBookSyncConfirmList(Integer pageSize,
                                                           Integer pageNumber,
                                                           String bookName,
                                                           String synchronizationType,
                                                           String syncTimeStart,
                                                           String syncTimeEnd,
    Boolean confirm);
    BookSyncConfirmVO getBookSyncConfirmByid(Long id);

    int addBookSynBak(BookSyncBak bookSyncBak);
    int updateBookSynConfirm(BookSyncConfirm bookSyncConfirm);
    int delectBooksyncConfirm(Long id);
    BookSyncBak getBookSyncBak(Long id);
    Integer updateBookSyncConfirmStatus(Map<String,Object> params);
    BookSyncLog getFullBookSyncLogBySyncTime();
    List<BookSyncConfirm> getBookConfirmsByLogId(Long id);
    int updateSyncBookLogConfirmStatusById(Map<String,Object> map);
}
