package com.bc.pmpheep.back.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.dao.BookDao;
import com.bc.pmpheep.back.dao.BookDetailDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.BookSyncService;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.BookSourceVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import sun.text.resources.FormatData;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/apitest")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BookSyncController {


    @Autowired
    BookSyncService bookSyncService;
    @Autowired
    BookService bookService;
    @Autowired




    private static final String BUSSINESS_TYPE = "图书同步";
    private static final String KEY = "bookSync";



    @ResponseBody
    @RequestMapping(value = "/syncBook", method = RequestMethod.POST)
    public ResponseBean syncBook(ServletRequest request, @RequestBody String json) throws IOException {
        /*解析图书信息*/
        String appkey = request.getParameter("app_key");

        /*是否增量更新*/
        String decrypt = decrypt(appkey);
        String appKeyString="bookSync";

        JSONObject jsonObject = JSON.parseObject(json);
        Object bookinfo = jsonObject.get("bookinfo");


        Boolean increment = jsonObject.getBoolean("increment");
        String synchronizationType = jsonObject.getString("synchronizationType");
        if(appkey==null &&appkey ==""){
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
                    "app_key参数为空");
        }
        if(!KEY.equals(decrypt)){
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.ILLEGAL_PARAM,
                    "app_key错误");
        }
        if(increment==null){
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
                    "是否增量同步参数为空参数为空");

        }

        if(synchronizationType==null &&synchronizationType ==""){
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
                    "同步类型参数为空参数为空");

        }
        BookSyncLog bookSyncLog = new BookSyncLog();

        bookSyncLog.setIncrement(increment);
        bookSyncLog.setSynchronizationType(synchronizationType);
        bookSyncService.addBookSyncLog(bookSyncLog);

        Long logId = bookSyncLog.getId();



        /*解析图书为实体类*/
        List<BookSyncConfirm> books = JSONArray.parseArray(bookinfo.toString(), BookSyncConfirm.class);
        for(BookSyncConfirm book:books){
            book.setLogId(logId);
            bookSyncService.addBookSyncConfirm(book);
        }

        /*解析图书为实体类*/
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("appkey",appkey);
        objectObjectHashMap.put("jsonInfo",jsonObject);

        return new ResponseBean(objectObjectHashMap);
    }
    /**
     * 根据密钥对指定的密文cipherText进行解密.
     *
     * @param cipherText 密文
     * @return 解密后的明文.
     */
    public static final String decrypt(String cipherText) {
        Key secretKey = getKey("medu");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] c = decoder.decodeBuffer(cipherText);
            byte[] result = cipher.doFinal(c);
            String plainText = new String(result, "UTF-8");
            return plainText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Key getKey(String keySeed) {
        if (keySeed == null) {
            keySeed = System.getenv("AES_SYS_KEY");
        }
        if (keySeed == null) {
            keySeed = System.getProperty("AES_SYS_KEY");
        }
        if (keySeed == null || keySeed.trim().length() == 0) {
            keySeed = "abcd1234!@#$";// 默认种子
        }
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(keySeed.getBytes());
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(secureRandom);
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取图书同步管理列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseBean<PageResult<BookSyncConfirmVO>> syncBook(Integer pageSize,
                                                           Integer pageNumber,
                                                           String bookName,
                                                           String synchronizationType,
                                                           String syncTimeStart,
                                                           String syncTimeEnd,
    Boolean confirm) throws IOException {
        PageParameter<BookSyncConfirmVO> parameter = new PageParameter<>();

        parameter.setPageSize(pageSize);
        parameter.setPageNumber(pageNumber);

        return new ResponseBean<>(bookSyncService.queryBookSyncConfirmList(pageSize, pageNumber, bookName, synchronizationType,
                syncTimeStart, syncTimeEnd,confirm));
    }


    @ResponseBody
    @RequestMapping(value = "/delectBookSyncConfirm", method = RequestMethod.GET)
    public ResponseBean delBookSyncConfirm(Long id) throws IOException {
        return new ResponseBean<>( bookSyncService.delectBooksyncConfirm(id));
    }


    @ResponseBody
    @RequestMapping(value = "/revokeBookSyncComfirm", method = RequestMethod.GET)
    public ResponseBean revokeBookSyncComfirm(Long id,String synchronizationType
    ) throws IOException {
            BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(id);
        Book book = new Book();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id",id);
        params.put("confirm",false);
        switch (synchronizationType){
            case "add":
                BeanUtils.copyProperties(bookSyncBak,book);
                book.setIsDelected(true);
                book.setId(bookSyncBak.getBookId());
                bookService.updateBook(book);
                break;
            case "update":
                BeanUtils.copyProperties(bookSyncBak,book);
                book.setId(bookSyncBak.getBookId());
                bookService.updateBook(book);

                break;
            case "shelf":
                BeanUtils.copyProperties(bookSyncBak,book);
                book.setId(bookSyncBak.getBookId());
                bookService.updateBook(book);
                break;
            case "obtained":
                BeanUtils.copyProperties(bookSyncBak,book);
                book.setId(bookSyncBak.getBookId());
                bookService.updateBook(book);
                break;
        }

        return new ResponseBean<>(bookSyncService.updateBookSyncConfirmStatus(params));
    }


    @ResponseBody
    @RequestMapping(value = "/confirmBook", method = RequestMethod.GET)
    public ResponseBean syncBook(String synchronizationType, Long bookId, Long nodeId, String sessionId, HttpServletRequest request) throws IOException {
         System.out.println(bookId);
        ResponseBean<Object> objectResponseBean = new ResponseBean<>();
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(request.getSession().getId());
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        BookSyncConfirm bookSyncConfirm= bookSyncService.getBookSyncConfirmByid(bookId);

        Book bookByIsbn = bookService.getBookByIsbn(bookSyncConfirm.getIsbn());
        BookDetail bookDetail=new BookDetail() ;
        if(bookByIsbn!=null){
            bookDetail= bookService.getBookDetailByBookId(bookByIsbn.getId());
        }

        switch (synchronizationType){
            case "add":
                if(ObjectUtil.isNull(bookByIsbn)){
                    Book newBook = new Book();
                    BeanUtils.copyProperties(bookSyncConfirm,newBook);
                    newBook.setId(null);
                    newBook.setType(nodeId);
                    newBook.setScore(10.0);
                    Book add = bookService.add(newBook);
                    BookDetail newBookDetail = new BookDetail(add.getId(),bookSyncConfirm.getContent());
                    bookService.addBookDetail(newBookDetail);
                    BookSyncBak bookSyncBak = new BookSyncBak();
                    BeanUtils.copyProperties(add,bookSyncBak);
                    bookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                    bookSyncBak.setConfirmUser(pmphUser.getId());
                    bookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                    bookSyncBak.setBookId(add.getId());
                    bookSyncBak.setSynchronizationType("add");
                    bookSyncService.addBookSynBak(bookSyncBak);
                    bookSyncConfirm.setEditorId(pmphUser.getId());
                    bookSyncConfirm.setConfirmStatus(true);
                    bookSyncService.updateBookSynConfirm(bookSyncConfirm);
                }else{
                    if(bookByIsbn.getIsDelected()){
                        bookByIsbn.setIsDelected(false);
                        bookService.updateBook(bookByIsbn);
                    }

                    objectResponseBean.setCode(2);
                    objectResponseBean.setMsg("需要添加图书已存在");
                }
                break;
            case "update":
                if(ObjectUtil.notNull(bookByIsbn)){
                    Book book = new Book();
                    BeanUtils.copyProperties(bookByIsbn,book);
                    book.setType(nodeId);
                    book.setAuthor(bookSyncConfirm.getAuthor());
                    book.setPublisher(bookSyncConfirm.getPublisher());
                    book.setLang(bookSyncConfirm.getLang());
                    book.setPublishDate(bookSyncConfirm.getPublishDate());
                    book.setReader(bookSyncConfirm.getReader());
                    book.setPrice(bookSyncConfirm.getPrice());
                    book.setScore(bookSyncConfirm.getScore());
                    book.setBuyUrl(bookSyncConfirm.getBuyUrl());
                    book.setMaterialId(bookSyncConfirm.getMaterialId());
                    book.setImageUrl(bookSyncConfirm.getImageUrl());
                    book.setPdfUrl(bookSyncConfirm.getPdfUrl());
                    book.setIsNew(bookSyncConfirm.getNew());
                    book.setSales(bookSyncConfirm.getSales());
                    book.setIsOnSale(bookSyncConfirm.getOnSale());
                    book.setGmtCreate(bookSyncConfirm.getGmtCreate());
                    book.setGmtUpdate(bookSyncConfirm.getGmtUpdate());
                    book.setVn(bookSyncConfirm.getVn());
                    book.setContent(bookSyncConfirm.getContent());
                    bookService.updateBook(book);
                    bookDetail.setBookId(book.getId());
                    bookDetail.setDetail(bookSyncConfirm.getContent());
                    bookService.updateBookDetail(bookDetail);
                    BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(bookId);

                    if(ObjectUtil.isNull(bookSyncBak)){
                        BookSyncBak newBookSyncBak = new BookSyncBak();
                        BeanUtils.copyProperties(bookByIsbn,newBookSyncBak);
                        newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                        newBookSyncBak.setConfirmUser(pmphUser.getId());
                        newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                        newBookSyncBak.setSynchronizationType("update");
                        newBookSyncBak.setBookId(book.getId());
                        bookSyncService.addBookSynBak(newBookSyncBak);
                    }

                }else{
                    objectResponseBean.setCode(2);
                    objectResponseBean.setMsg("更新的图书不存在");
                }
                break;
            case "shelf":
                if(ObjectUtil.notNull(bookByIsbn)){
                    Book book = new Book();
                    BeanUtils.copyProperties(bookByIsbn,book);
                    book.setType(nodeId);
                    book.setIsOnSale(bookSyncConfirm.getOnSale());
                    book.setGmtUpdate(bookSyncConfirm.getGmtUpdate());
                    BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(bookId);

                    if(ObjectUtil.isNull(bookSyncBak)){
                        BookSyncBak newBookSyncBak = new BookSyncBak();
                        BeanUtils.copyProperties(bookByIsbn,newBookSyncBak);
                        newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                        newBookSyncBak.setConfirmUser(pmphUser.getId());
                        newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                        newBookSyncBak.setSynchronizationType("update");
                        newBookSyncBak.setBookId(book.getId());
                        bookSyncService.addBookSynBak(newBookSyncBak);
                    }

                }else{
                    objectResponseBean.setCode(2);
                    objectResponseBean.setMsg("需要上架的图书不存在");
                }
                break;
            case "obtained":
                if(ObjectUtil.notNull(bookByIsbn)){
                    Book book = new Book();
                    BeanUtils.copyProperties(bookByIsbn,book);
                    book.setType(nodeId);
                    book.setIsOnSale(bookSyncConfirm.getOnSale());
                    book.setGmtUpdate(bookSyncConfirm.getGmtUpdate());
                    BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(bookId);

                    if(ObjectUtil.isNull(bookSyncBak)){
                        BookSyncBak newBookSyncBak = new BookSyncBak();
                        BeanUtils.copyProperties(bookByIsbn,newBookSyncBak);
                        newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                        newBookSyncBak.setConfirmUser(pmphUser.getId());
                        newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                        newBookSyncBak.setSynchronizationType("update");
                        newBookSyncBak.setBookId(book.getId());
                        bookSyncService.addBookSynBak(newBookSyncBak);
                    }

                }else{
                    objectResponseBean.setCode(2);
                    objectResponseBean.setMsg("需要下ll架的图书不存在");
                }
               break;
        }



       return objectResponseBean;
    }


}
