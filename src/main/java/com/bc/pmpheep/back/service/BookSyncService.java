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

    /**
     * 添加图书同步体制
     * @param bookSyncLog
     */
    void addBookSyncLog(BookSyncLog bookSyncLog);

    /**
     * 添加图书同步待确认
     * @param bookSyncConfirm
     */
    Long addBookSyncConfirm(BookSyncConfirm bookSyncConfirm);

    /**
     * 获取所有增量同步待确认列表
     * @param pageSize
     * @param pageNumber
     * @param bookName
     * @param synchronizationType
     * @param syncTimeStart
     * @param syncTimeEnd
     * @param confirm
     * @return
     */
    PageResult<BookSyncConfirmVO> queryBookSyncConfirmList(Integer pageSize,
                                                           Integer pageNumber,
                                                           String bookName,
                                                           String synchronizationType,
                                                           String syncTimeStart,
                                                           String syncTimeEnd,
    Boolean confirm);

    /**
     * 根据待确认id获取待确认对象
     * @param id
     * @return
     */
    BookSyncConfirmVO getBookSyncConfirmByid(Long id);

    /**
     * 根据待确认isbn获取待确认对象
     * @param id
     * @return
     */
    BookSyncConfirm getBookSyncConfirmByISBN(String isbn);

    /**
     * 添加图书备份
     * @param bookSyncBak
     * @return
     */
    int addBookSynBak(BookSyncBak bookSyncBak);

    /**
     * 更新带待确认信息
     * @param bookSyncConfirm
     * @return
     */
    int updateBookSynConfirm(BookSyncConfirm bookSyncConfirm);

    /**
     * 删除待确认信息
     * @param id
     * @return
     */
    int delectBooksyncConfirm(Long id);

    /**
     * 删除待确认信息
     * @param id
     * @return
     */
    int delectBooksyncConfirmByIsbn(String Isbn);
    /**
     * 获取图书备份对象
     * @param id
     * @return
     */
    BookSyncBak getBookSyncBak(Long id);

    /**
     * 更新同步带确认状态
     * @param params
     * @return
     */
    Integer updateBookSyncConfirmStatus(Map<String,Object> params);

    /**
     * 获取最新的一次全量同步信息
     * @return
     */
    List<BookSyncLog> getFullBookSyncLogBySyncTime();

    /**
     * 根同步日志id获取图书待确认信息
     * @param id
     * @return
     */
    List<BookSyncConfirm> getBookConfirmsByLogId(Long id);

    /**
     * 更新log 确认同步信息
     * @param map
     * @return
     */
    int updateSyncBookLogConfirmStatusById(Map<String,Object> map);

    /**
     * 根据vn号更新图书销量信息
     * @param maps
     * @return
     */
    int updateBookSaleByVns(List<Map<String,Object>> maps);
}
