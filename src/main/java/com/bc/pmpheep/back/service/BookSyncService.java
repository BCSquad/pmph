package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.BookSyncConfirm;
import com.bc.pmpheep.back.po.BookSyncLog;

public interface BookSyncService {

    void addBookSyncLog(BookSyncLog bookSyncLog);
    void addBookSyncConfirm(BookSyncConfirm bookSyncConfirm);
}
