package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.BookSyncConfirm;
import com.bc.pmpheep.back.po.BookSyncLog;

public interface BookSyncDao {

    void addSyncLog(BookSyncLog bookSyncLog);
    void addBookSynConfirm(BookSyncConfirm bookSyncConfirm);
}
