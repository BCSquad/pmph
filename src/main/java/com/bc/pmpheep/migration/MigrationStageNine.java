/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookDetail;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.BookUserCommentService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * 数据迁移方案第九部分 - 出版图书相关数据迁移
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class MigrationStageNine {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageNine.class);
    private final Timestamp deadline = Timestamp.valueOf("2030-01-01 00:00:00");
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    BookService bookService;
    @Resource
    BookUserCommentService bookUserCommentService;
    @Resource
    FileService fileService;
    @Resource
    ExcelHelper excelHelper;

    public void start() {
        Date begin = new Date();
        book();
        logger.info("迁移第九步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
    }

    protected void book() {
        String tableName = "book_goodsinfo"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        //JdbcHelper.addColumn("sys_booktypes"); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        /* booktypesid这张表重复的bookid非常多，因此用group来返回唯一结果 */
        String sql = "SELECT booktypesid FROM book_goodstype WHERE bookid = ? GROUP BY bookid";
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            /* Get book_goodsinfo properties */
            String bookid = (String) map.get("bookid");
            String name = (String) map.get("name");
            String isbn = (String) map.get("ISBN");
            String titleno = (String) map.get("titleno");
            String author = (String) map.get("author");
            Integer revision = (Integer) map.get("revision");
            String press = (String) map.get("press");
            Integer impression = (Integer) map.get("impression");
            BigDecimal price = (BigDecimal) map.get("price");
            String buyurl = (String) map.get("buyurl");
            String picurl = (String) map.get("picurl");
            String pdfurl = (String) map.get("pdfurl");
            //String tags = (String) map.get("tags");
            String reader = (String) map.get("reader");
            //String createdate = map.get("createdate").toString();
            BigDecimal score = (BigDecimal) map.get("score");
            //Integer isdelete = (Integer) map.get("isdelete");
            //String language = (String) map.get("language");
            Integer isnew = (Integer) map.get("isnew");
            Integer ismajor = (Integer) map.get("ismajor");
            Integer isonshelves = (Integer) map.get("isonshelves");
            String editnumber = (String) map.get("editnumber");
            /* Set book properties */
            Book book = new Book();
            book.setAuthor(author);
            book.setBookmarks(0L);//抛弃旧平台收藏
            book.setBookname(name);
            if (StringUtil.isEmpty(buyurl)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "购买链接为空");
                excel.add(map);
                logger.error("购买链接(buyurl)为空，本条数据无效，将记录在Excel中");
                continue;
            }
            book.setBuyUrl(buyurl);
            book.setClicks(0L);//旧平台没有点击数
            book.setComments(0L);//抛弃旧平台评论
            book.setIsStick(false);//旧平台没有置顶
            /* 判断旧平台图书是否新书和推荐 */
            if (0 == isnew) {
                book.setIsNew(false);
            } else {
                book.setIsNew(true);
                book.setDeadlineNew(deadline);
            }
            if (0 == ismajor) {
                book.setIsPromote(false);
            } else {
                book.setIsPromote(true);
                book.setDeadlinePromote(deadline);
            }
            if (StringUtil.isEmpty(picurl)) {
                book.setImageUrl("DEFAULT");
            } else {
                book.setImageUrl(picurl);
            }
            book.setIsOnSale(isonshelves > 0);
            book.setIsbn(isbn);
            book.setLikes(0L);//旧平台没有点赞
            if (StringUtil.notEmpty(pdfurl)) {
                book.setPdfUrl(pdfurl);
            }
            book.setPrice(price.doubleValue());
            if (null == map.get("publicationdate")) {
                book.setPublishDate(new Date());
                map.put(SQLParameters.EXCEL_EX_HEADER, "旧数据库中出版日期为空，已设为当前日期");
                excel.add(map);
                logger.warn("旧数据库中出版日期为空，已设为当前日期，此结果将被记录在Excel中");
            } else {
                try {
                    String publicationdate = map.get("publicationdate").toString();
                    book.setPublishDate(sdf.parse(publicationdate));
                } catch (ParseException ex) {
                    book.setPublishDate(new Date());
                    map.put(SQLParameters.EXCEL_EX_HEADER, "出版日期转换失败，已设为当前日期");
                    excel.add(map);
                    logger.warn("出版日期转换失败，此结果将被记录在Excel中，错误信息：{}", ex.getMessage());
                }
            }
            if (StringUtil.notEmpty(press)) {
                book.setPublisher(press);
            } else {
                book.setPublisher("暂缺");
                map.put(SQLParameters.EXCEL_EX_HEADER, "出版图书没有出版社，已设为'暂缺'");
                excel.add(map);
                logger.warn("出版图书的出版社字段为空，此结果将被记录在Excel中");
            }
            if (StringUtil.notEmpty(reader)) {
                book.setReader(reader);
            }
            book.setRevision(revision);
            book.setSales(0L);//旧平台没有销量
            book.setScore(9.0);//评分全是9
            book.setSn(titleno);
            String booktypesid;
            try {
                booktypesid = JdbcHelper.getJdbcTemplate().queryForObject(sql, String.class, bookid);
            } catch (DataAccessException ex) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "查询booktypesid时未返回唯一结果");
                excel.add(map);
                logger.error("查询booktypesid时未返回唯一结果，错误信息{}", ex.getMessage());
                continue;
            }
            Long pk = JdbcHelper.getPrimaryKey("sys_booktypes", "BookTypesID", booktypesid);
            if (null == pk) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "获取sys_booktypes表new_pk字段失败，BookTypesID=" + booktypesid);
                excel.add(map);
                logger.error("获取sys_booktypes表new_pk字段失败，此结果将被记录在Excel中");
                continue;
            }
            book.setType(pk);
            book = bookService.add(book);
            Long bookId = book.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, bookId, "bookid", bookid);
            /* 以下创建关联的BookDetail对象 */
            String content = (String) map.get("content");
            BookDetail detail = new BookDetail();
            detail.setBookId(bookId);
            if (StringUtil.notEmpty(content)) {
                detail.setDetail(content);
            }
            bookService.add(detail);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, tableName, tableName);
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.msg.add(msg);
    }

    protected void bookUserCommnet() {
        /* 放弃迁移虚假评论 */
    }
}
