package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.BookSyncDao;
import com.bc.pmpheep.back.po.BookSyncConfirm;
import com.bc.pmpheep.back.po.BookSyncLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
