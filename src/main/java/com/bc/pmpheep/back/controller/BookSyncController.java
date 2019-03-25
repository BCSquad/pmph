package com.bc.pmpheep.back.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.BookSyncService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

@Controller
@RequestMapping(value = "/aiptest")
@SuppressWarnings({"rawtypes", "unchecked"})
public class BookSyncController {

    @Autowired
    BookSyncService bookSyncService;
    @Autowired
    BookService bookService;
    @Autowired
    PmphUserService pmphUserService;
    @Autowired
    MessageService messageService;

    @Autowired
    private UserMessageService userMessageService;

    private static final String BUSSINESS_TYPE = "图书同步";
    private static final String KEY = "bookSync";



    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "图书同步接口")
    @RequestMapping(value = "/syncBook", method = RequestMethod.POST)
    public ResponseBean syncBook(ServletRequest request, @RequestBody String json) {
        /*解析图书信息*/
        String appkey = request.getParameter("app_key");
        ResponseBean<Object> responseBean = new ResponseBean<>();

        String decrypt = decrypt(appkey);
        String appKeyString = "bookSync";

        JSONObject jsonObject = JSON.parseObject(json);



        /*是否增量更新*/
        Boolean increment = jsonObject.getBoolean("increment");
        String synchronizationType = jsonObject.getString("synchronizationType");
        if (appkey == null && appkey == "") {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
                    "app_key参数为空");
        }
        if (!KEY.equals(decrypt)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.ILLEGAL_PARAM,
                    "app_key错误");
        }
        if (increment == null) {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
                    "是否增量同步参数为空参数为空");

        }

        if (synchronizationType == null && synchronizationType == "") {
            throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
                    "同步类型参数为空参数为空");

        }



        Object bookinfo = jsonObject.get("bookInfo");
        JSONArray objects =JSONArray.parseArray(bookinfo.toString());

        //解析实体类
        List<BookSyncConfirm> bookSyncConfirms=new ArrayList<>();
        parsejson(objects,bookSyncConfirms);

        //写入接口日志表
        BookSyncLog bookSyncLog = new BookSyncLog();


        bookSyncLog.setIncrement(increment);
        bookSyncLog.setSynchronizationType(synchronizationType);


        bookSyncService.addBookSyncLog(bookSyncLog);

        Long logId = bookSyncLog.getId();

        int count=1;
        Boolean flag=false;

        StringBuilder sb=new StringBuilder();

       switch (synchronizationType){

           case "add":

               for (BookSyncConfirm book : bookSyncConfirms) {
                   if (StringUtil.isEmpty(book.getIsbn())) {
                       flag = true;
                       sb.append("图书参数" + count + ":的ISBN号不能为空---");
                   }
                   if (StringUtil.isEmpty(book.getBookname())) {
                       flag = true;
                       sb.append("图书参数" + count + ":的图书名称不能为空---");

                   }
                   book.setLogId(logId);
                   bookSyncService.addBookSyncConfirm(book);
               }
               break;
           case "update":
               for (BookSyncConfirm book : bookSyncConfirms) {
                   if(StringUtil.isEmpty(book.getIsbn())){
                       flag=true;
                       sb.append("图书参数"+count+":的ISBN号不能为空---");
                   }
                   if(StringUtil.isEmpty(book.getBookname())){
                       flag=true;
                       sb.append("图书参数"+count+":的图书名称不能为空---");

                   }
                   book.setLogId(logId);
                   bookSyncService.addBookSyncConfirm(book);
               }
               break;
           case "shelf":

               for (BookSyncConfirm book : bookSyncConfirms) {
                   if(StringUtil.isEmpty(book.getIsbn())){
                       flag=true;
                       sb.append("图书参数"+count+":的ISBN号不能为空---");
                   }
                   if(StringUtil.isEmpty(book.getBookname())){
                       flag=true;
                       sb.append("图书参数"+count+":的图书名称不能为空---");
                   }
                   BookSyncConfirm newBookS = new BookSyncConfirm();
                   Book bookByIsbn = bookService.getBookByIsbn(book.getIsbn());
                   if(ObjectUtil.isNull(bookByIsbn)){
                      BookSyncConfirm bookSyncConfirmByISBN = bookSyncService.getBookSyncConfirmByISBN(bookByIsbn.getIsbn());
                      if(ObjectUtil.isNull(bookSyncConfirmByISBN)){
                          flag=true;
                          sb.append("未找到该isbn的图书---");
                      }else{
                          newBookS.setIsOnSale(bookByIsbn.getIsOnSale());
                          newBookS.setLogId(logId);
                          BeanUtils.copyProperties(bookSyncConfirmByISBN, newBookS);
                          newBookS.setId(null);
                          bookSyncService.addBookSyncConfirm(newBookS);
                      }

                   }else{
                       newBookS.setIsOnSale(bookByIsbn.getIsOnSale());
                       newBookS.setLogId(logId);
                       BeanUtils.copyProperties(bookByIsbn, newBookS);
                       newBookS.setId(null);
                       bookSyncService.addBookSyncConfirm(newBookS);
                   }
               }

               break;
           case"obtained":
               for (BookSyncConfirm book : bookSyncConfirms) {
                   BookSyncConfirm newBookS = new BookSyncConfirm();
                   Book bookByIsbn = bookService.getBookByIsbn(book.getIsbn());
                   if(ObjectUtil.isNull(bookByIsbn)){
                       BookSyncConfirm bookSyncConfirmByISBN = bookSyncService.getBookSyncConfirmByISBN(bookByIsbn.getIsbn());
                       if(ObjectUtil.isNull(bookSyncConfirmByISBN)){
                           sb.append("未找到该isbn的图书---");
                       }else{
                           newBookS.setIsOnSale(bookByIsbn.getIsOnSale());
                           newBookS.setLogId(logId);
                           BeanUtils.copyProperties(bookSyncConfirmByISBN, newBookS);
                           newBookS.setId(null);
                           bookSyncService.addBookSyncConfirm(newBookS);
                       }

                   }else{
                       newBookS.setIsOnSale(bookByIsbn.getIsOnSale());
                       newBookS.setLogId(logId);
                       BeanUtils.copyProperties(bookByIsbn, newBookS);
                       newBookS.setId(null);
                       bookSyncService.addBookSyncConfirm(newBookS);
                   }
               }

               break;
       }


       /* List<BookSyncConfirm> books = JSONArray.parseArray(bookinfo.toString(), BookSyncConfirm.class);*/

        if(flag){
            responseBean.setMsg(sb.toString());
        }else{
            List<PmphUser> pmphUserByRole = pmphUserService.getPmphUserByRole();
            for (PmphUser p : pmphUserByRole) {

                String msgContent = "商城同步了图书,请在图书同步管理中确认";// 退回
                // 存入消息主体
                Message message = new Message(msgContent);
                message = messageService.add(message);
                String msg_id = message.getId();
                UserMessage userMessage = new UserMessage(msg_id, "系统消息", new Short("0"), 0L, new Short("0"),
                        p.getId(), new Short("1"), null);
                userMessageService.addUserMessage(userMessage);
            }
            Map<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("appkey", appkey);
            objectObjectHashMap.put("jsonInfo", jsonObject);
        }


        return new ResponseBean(responseBean);
    }



    public void parsejson(JSONArray objects,List<BookSyncConfirm> bookSyncConfirms){
        for(int i=0;i<objects.size();i++){
            JSONObject jsonObject1 = JSON.parseObject(objects.get(i).toString());
            BookSyncConfirm bookSyncConfirm = new BookSyncConfirm();
            bookSyncConfirm.setIsbn(jsonObject1.getString("isbn")==null?null:jsonObject1.getString("isbn"));
            bookSyncConfirm.setBookname(jsonObject1.getString("bookname")==null?null:jsonObject1.getString("bookname"));
            bookSyncConfirm.setAuthor(jsonObject1.getString("author")==null?null:jsonObject1.getString("author"));
            bookSyncConfirm.setPublisher(jsonObject1.getString("publisher")==null?null:jsonObject1.getString("publisher"));
            bookSyncConfirm.setLang(jsonObject1.getString("lang")==null?null:jsonObject1.getString("lang"));
            bookSyncConfirm.setRevision(jsonObject1.getInteger("version")==null?null:jsonObject1.getInteger("version"));
            bookSyncConfirm.setPublishDate(jsonObject1.getDate("publishDate")==null?null:jsonObject1.getDate("publishDate"));
            bookSyncConfirm.setReader(jsonObject1.getString("reader")==null?null:jsonObject1.getString("reader"));
            bookSyncConfirm.setPrice(jsonObject1.getDouble("price")==null?null:jsonObject1.getDouble("price"));
            bookSyncConfirm.setBuyUrl(jsonObject1.getString("buyUrl")==null?null:jsonObject1.getString("buyUrl"));
            bookSyncConfirm.setImageUrl(jsonObject1.getString("imageUrl")==null?null:jsonObject1.getString("imageUrl"));
            bookSyncConfirm.setPdfUrl(jsonObject1.getString("pdfUrl")==null?null:jsonObject1.getString("pdfUrl"));
            bookSyncConfirm.setNew(jsonObject1.getBoolean("isNew")==null?null:jsonObject1.getBoolean("isNew"));
            bookSyncConfirm.setIsOnSale(jsonObject1.getBoolean("isOnSale")==null?null:jsonObject1.getBoolean("isOnSale"));
            bookSyncConfirm.setGmtCreate(jsonObject1.getTimestamp("gmtCreate")==null?null:jsonObject1.getTimestamp("gmtCreate"));
            bookSyncConfirm.setGmtUpdate(jsonObject1.getTimestamp("gmtUpdate")==null?null:jsonObject1.getTimestamp("gmtUpdate"));
            bookSyncConfirm.setVn(jsonObject1.getString("vn")==null?null:jsonObject1.getString("vn"));
            bookSyncConfirm.setContent(jsonObject1.getString("content")==null?null:jsonObject1.getString("content"));
            bookSyncConfirms.add(bookSyncConfirm);
        }
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

    /**
     * 获取列表
     *
     * @param pageSize
     * @param pageNumber
     * @param bookName
     * @param synchronizationType
     * @param syncTimeStart
     * @param syncTimeEnd
     * @param confirm
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取图书同步管理列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseBean<PageResult<BookSyncConfirmVO>> syncBook(Integer pageSize, Integer pageNumber, String bookName,
                                                                String synchronizationType, String syncTimeStart, String syncTimeEnd, Boolean confirm) throws IOException {
        PageParameter<BookSyncConfirmVO> parameter = new PageParameter<>();

        parameter.setPageSize(pageSize);
        parameter.setPageNumber(pageNumber);

        return new ResponseBean<>(bookSyncService.queryBookSyncConfirmList(pageSize, pageNumber, bookName,
                synchronizationType, syncTimeStart, syncTimeEnd, confirm));
    }

    @ResponseBody
    @RequestMapping(value = "/delectBookSyncConfirm", method = RequestMethod.GET)
    public ResponseBean delBookSyncConfirm(Long id) throws IOException {
        return new ResponseBean<>(bookSyncService.delectBooksyncConfirm(id));
    }

    @ResponseBody
    @RequestMapping(value = "/batchdel", method = RequestMethod.GET)
    public ResponseBean batchDel(Long[] ids) throws IOException {
        ResponseBean<Object> objectResponseBean = new ResponseBean<>();
        int code = 0;
        for (Long id : ids) {
            code = bookSyncService.delectBooksyncConfirm(id);
        }
        if (code == 0) {
            objectResponseBean.setCode(0);
        }

        return objectResponseBean;
    }

    @ResponseBody
    @RequestMapping(value = "/batchConfirm", method = RequestMethod.GET)
    public ResponseBean batchConfirm(Long[] ids, HttpServletRequest request) throws IOException {
        ResponseBean<Object> objectResponseBean = new ResponseBean<>();
        for (Long id : ids) {
            BookSyncConfirmVO bookSyncConfirmByid = bookSyncService.getBookSyncConfirmByid(id);

            String synchronizationType = bookSyncConfirmByid.getSynchronizationType();

            // 获取当前用户
            PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(request.getSession().getId());
            if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
                        "用户为空");
            }
            // 获取待确认的图书
            BookSyncConfirmVO bookSyncConfirm = bookSyncService.getBookSyncConfirmByid(id);

            // 根据isbn获取是否已存在该图书
            Book bookByIsbn = bookService.getBookByIsbn(bookSyncConfirm.getIsbn());

            // 获取图书详情
            BookDetail bookDetail = new BookDetail();
            if (bookByIsbn != null) {
                bookDetail = bookService.getBookDetailByBookId(bookByIsbn.getId());
            }
            // 判断同步类型
            switch (synchronizationType) {
                // 如果类型为增加
                case "add":

                    // 如果本地不存在该图书
                    if (ObjectUtil.isNull(bookByIsbn)) {
                        // 创建图书对象
                        Book newBook = new Book();
                        // 从待确认复制到图书对象
                        BeanUtils.copyProperties(bookSyncConfirm, newBook);
                        // 清除id
                        newBook.setId(null);
                        // 设置图书类型
                        /* newBook.setType(nodeId); */
                        // 图书默认评分为10
                        newBook.setScore(10.0);

                        // 同步书籍到本地
                        Book add = bookService.add(newBook);
                        // 同步图书详情到本地
                        BookDetail newBookDetail = new BookDetail(add.getId(), bookSyncConfirm.getContent());
                        bookService.addBookDetail(newBookDetail);

                        // 备份新增的图书信息
                        BookSyncBak bookSyncBak = new BookSyncBak();
                        BeanUtils.copyProperties(add, bookSyncBak);
                        bookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                        bookSyncBak.setConfirmUser(pmphUser.getId());
                        bookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                        bookSyncBak.setBookId(add.getId());
                        bookSyncBak.setSynchronizationType("add");
                        bookSyncService.addBookSynBak(bookSyncBak);

                        // 更新待确认信息
                        bookSyncConfirm.setConfirmUser(pmphUser.getId());
                        bookSyncConfirm.setConfirmStatus(true);

                        BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                        BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                        bookSyncService.updateBookSynConfirm(bookSyncConfirm1);
                    } else {

                        // 如果不为空 且图书是删除状态 恢复该图书
                        if (bookByIsbn.getIsDelected()) {
                            bookByIsbn.setIsDelected(false);
                            bookService.updateBook(bookByIsbn);
                        } else {
                            objectResponseBean.setCode(2);
                            objectResponseBean.setMsg("需要添加图书已存在");
                        }

                    }
                    break;
                // 类型是更新
                case "update":
                    if (ObjectUtil.notNull(bookByIsbn)) {
                        Book book = new Book();
                        // 更新图书信息
                        BeanUtils.copyProperties(bookByIsbn, book);
                        /* book.setType(nodeId); */
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
                        // 更新图书详情
                        bookDetail.setBookId(book.getId());
                        bookDetail.setDetail(bookSyncConfirm.getContent());
                        bookService.updateBookDetail(bookDetail);

                        BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(id);

                        if (ObjectUtil.isNull(bookSyncBak)) {
                            BookSyncBak newBookSyncBak = new BookSyncBak();
                            BeanUtils.copyProperties(bookByIsbn, newBookSyncBak);
                            newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                            newBookSyncBak.setConfirmUser(pmphUser.getId());
                            newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                            newBookSyncBak.setSynchronizationType("update");
                            newBookSyncBak.setBookId(book.getId());
                            bookSyncService.addBookSynBak(newBookSyncBak);
                        }

                        // 更新待确认信息
                        bookSyncConfirm.setConfirmUser(pmphUser.getId());
                        bookSyncConfirm.setConfirmStatus(true);

                        BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                        BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                        bookSyncService.updateBookSynConfirm(bookSyncConfirm1);
                    } else {
                        objectResponseBean.setCode(2);
                        objectResponseBean.setMsg("需要更新的图书不存在");
                    }
                    break;
                // 类型为上架
                case "shelf":
                    if (ObjectUtil.notNull(bookByIsbn)) {
                        Book book = new Book();
                        // 更新图书信息
                        BeanUtils.copyProperties(bookByIsbn, book);
                        /* book.setType(nodeId); */
                        book.setIsOnSale(bookSyncConfirm.getOnSale());
                        book.setGmtUpdate(bookSyncConfirm.getGmtUpdate());
                        bookService.updateBook(book);
                        BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(id);

                        if (ObjectUtil.isNull(bookSyncBak)) {
                            // 备份图书信息
                            BookSyncBak newBookSyncBak = new BookSyncBak();
                            BeanUtils.copyProperties(bookByIsbn, newBookSyncBak);
                            newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                            newBookSyncBak.setConfirmUser(pmphUser.getId());
                            newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                            newBookSyncBak.setSynchronizationType("update");
                            newBookSyncBak.setBookId(book.getId());
                            bookSyncService.addBookSynBak(newBookSyncBak);
                        }
                        // 更新待确认信息
                        bookSyncConfirm.setConfirmUser(pmphUser.getId());
                        bookSyncConfirm.setConfirmStatus(true);

                        BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                        BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                        bookSyncService.updateBookSynConfirm(bookSyncConfirm1);

                    } else {
                        objectResponseBean.setCode(2);
                        objectResponseBean.setMsg("需要上架的图书不存在");
                    }
                    break;
                case "obtained":
                    if (ObjectUtil.notNull(bookByIsbn)) {
                        Book book = new Book();
                        BeanUtils.copyProperties(bookByIsbn, book);
                        /* book.setType(nodeId); */
                        book.setIsOnSale(bookSyncConfirm.getOnSale());
                        book.setGmtUpdate(bookSyncConfirm.getGmtUpdate());
                        bookService.updateBook(book);
                        BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(id);

                        if (ObjectUtil.isNull(bookSyncBak)) {
                            BookSyncBak newBookSyncBak = new BookSyncBak();
                            BeanUtils.copyProperties(bookByIsbn, newBookSyncBak);
                            newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                            newBookSyncBak.setConfirmUser(pmphUser.getId());
                            newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                            newBookSyncBak.setSynchronizationType("update");
                            newBookSyncBak.setBookId(book.getId());
                            bookSyncService.addBookSynBak(newBookSyncBak);
                        }
                        // 更新待确认信息
                        bookSyncConfirm.setConfirmUser(pmphUser.getId());
                        bookSyncConfirm.setConfirmStatus(true);

                        BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                        BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                        bookSyncService.updateBookSynConfirm(bookSyncConfirm1);

                    } else {
                        objectResponseBean.setCode(2);
                        objectResponseBean.setMsg("需要下架的图书不存在");
                    }
                    break;
            }

        }
        return objectResponseBean;
    }

    @ResponseBody
    @RequestMapping(value = "/showDetail", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查看图书同步详情")
    public ResponseBean showDateil(Long id, String type) throws IOException {
        BookSyncConfirmVO bookSyncConfirm = bookSyncService.getBookSyncConfirmByid(id);
        Book bookByIsbn = bookService.getBookByIsbn(bookSyncConfirm.getIsbn());
        if (bookByIsbn == null) {
            bookByIsbn = new Book();
        }
        List<Map<String, Object>> list = new ArrayList();
        list.add(addRES("图书名称", bookByIsbn.getBookname(), bookSyncConfirm.getBookname()));
        list.add(addRES("ISBN号", bookByIsbn.getIsbn(), bookSyncConfirm.getIsbn()));
        list.add(addRES("作者", bookByIsbn.getAuthor(), bookSyncConfirm.getAuthor()));
        list.add(addRES("出版社", bookByIsbn.getPublisher(), bookSyncConfirm.getPublisher()));
        list.add(addRES("语言", bookByIsbn.getLang(), bookSyncConfirm.getLang()));
        list.add(addRES("版次", bookByIsbn.getRevision(), bookSyncConfirm.getRevision()));
        list.add(addRES("出版日期", bookByIsbn.getPublishDate(), bookSyncConfirm.getPublishDate()));
        list.add(addRES("读者对象", bookByIsbn.getReader(), bookSyncConfirm.getReader()));
        list.add(addRES("价格", bookByIsbn.getPrice(), bookSyncConfirm.getPrice()));
        list.add(addRES("购买地址", bookByIsbn.getBuyUrl(), bookSyncConfirm.getBuyUrl()));
        list.add(addRES("是否新书", bookByIsbn.getIsNew() == null ? null : bookByIsbn.getIsNew() ? "是" : "否",
                bookSyncConfirm.getNew() == null ? null : bookSyncConfirm.getNew() ? "是" : "否"));
        list.add(addRES("是否上架", bookByIsbn.getIsOnSale() == null ? null : bookByIsbn.getIsOnSale() ? "是" : "否",
                bookSyncConfirm.getOnSale() == null ? null : bookSyncConfirm.getOnSale() ? "是" : "否"));
        list.add(addRES("本版号", bookByIsbn.getVn(), bookSyncConfirm.getVn()));
        return new ResponseBean<>(list);
    }

    public Object ifnull(Object obj) {
        return obj == null ? "------" : obj;

    }

    public Map<String, Object> addRES(String name, Object oldBook, Object newBook) {
        Map<String, Object> res = new HashMap<>();
        res.put("name", name);
        res.put("oldBook", ifnull(oldBook));
        res.put("newBook", ifnull(newBook));
        Boolean equals = true;

        if (oldBook != null) {
            if (!oldBook.toString().equals(newBook.toString())) {
                equals = false;

            }
        }
        res.put("equals", equals);
        return res;
    }

    /**
     * 撤回确认操作
     *
     * @param id
     * @param synchronizationType
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "撤回已确认图书同步")
    @RequestMapping(value = "/revokeBookSyncComfirm", method = RequestMethod.GET)
    public ResponseBean revokeBookSyncComfirm(Long id, String synchronizationType) throws IOException {
        BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(id);
        Book book = new Book();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("confirm", false);
        switch (synchronizationType) {
            case "add":
                BeanUtils.copyProperties(bookSyncBak, book);
                book.setIsDelected(true);
                book.setId(bookSyncBak.getBookId());
                bookService.updateBook(book);
                break;
            case "update":
                BeanUtils.copyProperties(bookSyncBak, book);
                book.setId(bookSyncBak.getBookId());
                bookService.updateBook(book);

                break;
            case "shelf":
                BeanUtils.copyProperties(bookSyncBak, book);
                book.setId(bookSyncBak.getBookId());
                bookService.updateBook(book);
                break;
            case "obtained":
                BeanUtils.copyProperties(bookSyncBak, book);
                book.setId(bookSyncBak.getBookId());
                bookService.updateBook(book);
                break;
        }

        return new ResponseBean<>(bookSyncService.updateBookSyncConfirmStatus(params));
    }


    Integer flag = 0;
    Integer speed = 0;
    Integer count = 0;

    /**
     * 全量更新图书
     *
     * @param id
     * @param synchronizationType
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "全量更新图书")
    @RequestMapping(value = "/syncFullBooks", method = RequestMethod.GET)
    public ResponseBean syncFullBooks(HttpServletRequest request) throws IOException {

        ResponseBean<Object> responseBean = new ResponseBean<>();
        BookSyncLog bookSyncLog = bookSyncService.getFullBookSyncLogBySyncTime();
        // 获取当前用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(request.getSession().getId());
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
        }

        if (ObjectUtil.isNull(bookSyncLog)) {
            responseBean.setCode(2);
            responseBean.setMsg("未发现可同步的数据");
            return responseBean;

        } else {
            if (!bookSyncLog.getConfirmStatus()) {
                responseBean.setCode(2);
                responseBean.setMsg("未发现可同步的数据");
                return responseBean;

            }
        }


        List<BookSyncConfirm> bookSyncConfirms = bookSyncService.getBookConfirmsByLogId(bookSyncLog.getId());

        if (ObjectUtil.isNull(bookSyncConfirms)) {
            responseBean.setCode(2);
            responseBean.setMsg("未发现可同步的数据");
            return responseBean;

        }


        flag = bookSyncConfirms.size() / 100;

        for (BookSyncConfirm bookSyncConfirm : bookSyncConfirms) {
            count++;

            // 获取图书详情
            BookDetail bookDetail = new BookDetail();
            Book bookByIsbn = bookService.getBookByIsbn(bookSyncConfirm.getIsbn());
            if (bookByIsbn != null) {
                bookDetail = bookService.getBookDetailByBookId(bookByIsbn.getId());
            }
            if (ObjectUtil.notNull(bookByIsbn)) {

                Book book = new Book();
                // 更新图书信息
                BeanUtils.copyProperties(bookByIsbn, book);

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
                // 更新图书详情
                bookDetail.setBookId(book.getId());
                bookDetail.setDetail(bookSyncConfirm.getContent());
                bookService.updateBookDetail(bookDetail);

                BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(bookSyncConfirm.getId());

                if (ObjectUtil.isNull(bookSyncBak)) {
                    BookSyncBak newBookSyncBak = new BookSyncBak();
                    BeanUtils.copyProperties(bookByIsbn, newBookSyncBak);
                    newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                    newBookSyncBak.setConfirmUser(pmphUser.getId());
                    newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                    newBookSyncBak.setSynchronizationType("update");
                    newBookSyncBak.setBookId(book.getId());
                    bookSyncService.addBookSynBak(newBookSyncBak);
                }
                // 更新待确认信息
                bookSyncConfirm.setConfirmUser(pmphUser.getId());
                bookSyncConfirm.setConfirmStatus(true);

                BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                bookSyncService.updateBookSynConfirm(bookSyncConfirm1);

            } else {
                // 创建图书对象
                Book newBook = new Book();
                // 从待确认复制到图书对象
                BeanUtils.copyProperties(bookSyncConfirm, newBook);
                // 清除id
                newBook.setId(null);
                // 图书默认评分为10
                newBook.setScore(10.0);

                // 同步书籍到本地
                Book add = bookService.add(newBook);
                // 同步图书详情到本地
                BookDetail newBookDetail = new BookDetail(add.getId(), bookSyncConfirm.getContent());
                bookService.addBookDetail(newBookDetail);

                // 备份新增的图书信息
                BookSyncBak bookSyncBak = new BookSyncBak();
                BeanUtils.copyProperties(add, bookSyncBak);
                bookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                bookSyncBak.setConfirmUser(pmphUser.getId());
                bookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                bookSyncBak.setBookId(add.getId());
                bookSyncBak.setSynchronizationType("add");
                bookSyncService.addBookSynBak(bookSyncBak);

                // 更新待确认信息
                bookSyncConfirm.setConfirmUser(pmphUser.getId());
                bookSyncConfirm.setConfirmStatus(true);

                BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                bookSyncService.updateBookSynConfirm(bookSyncConfirm1);

            }
            if (count % flag == 0 && speed < 100) {
                speed++;
            }

        }
        Map<String, Object> params = new HashMap<>();
        params.put("confirm", true);
        params.put("id", bookSyncLog.getId());
        bookSyncService.updateSyncBookLogConfirmStatusById(params);
        speed = 100;
        return responseBean;
    }


    /**
     * 功能描述：获取图书同步进度
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取图书同步进度")
    @RequestMapping(value = "/getSpeed", method = RequestMethod.GET)
    public ResponseBean getSpeed() {
        return new ResponseBean(speed);
    }


    /**
     * 确认导入图书
     *
     * @param synchronizationType
     * @param bookId
     * @param nodeId
     * @param sessionId
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "确认图书同步")
    @RequestMapping(value = "/confirmBook", method = RequestMethod.GET)
    public ResponseBean confirmBookSync(String synchronizationType, Long bookId, Long nodeId,
                                        HttpServletRequest request) throws IOException {
        System.out.println(bookId);
        ResponseBean<Object> objectResponseBean = new ResponseBean<>();
        // 获取当前用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(request.getSession().getId());
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        // 获取待确认的图书
        BookSyncConfirmVO bookSyncConfirm = bookSyncService.getBookSyncConfirmByid(bookId);

        // 根据isbn获取是否已存在该图书
        Book bookByIsbn = bookService.getBookByIsbn(bookSyncConfirm.getIsbn());

        // 获取图书详情
        BookDetail bookDetail = new BookDetail();
        if (bookByIsbn != null) {
            bookDetail = bookService.getBookDetailByBookId(bookByIsbn.getId());
        }
        // 判断同步类型
        switch (synchronizationType) {
            // 如果类型为增加
            case "add":

                // 如果本地不存在该图书
                if (ObjectUtil.isNull(bookByIsbn)) {
                    // 创建图书对象
                    Book newBook = new Book();
                    // 从待确认复制到图书对象
                    BeanUtils.copyProperties(bookSyncConfirm, newBook);
                    // 清除id
                    newBook.setId(null);
                    // 设置图书类型
                    newBook.setType(nodeId);
                    // 图书默认评分为10
                    newBook.setScore(10.0);

                    // 同步书籍到本地
                    Book add = bookService.add(newBook);
                    // 同步图书详情到本地
                    BookDetail newBookDetail = new BookDetail(add.getId(), bookSyncConfirm.getContent());
                    bookService.addBookDetail(newBookDetail);

                    // 备份新增的图书信息
                    BookSyncBak bookSyncBak = new BookSyncBak();
                    BeanUtils.copyProperties(add, bookSyncBak);
                    bookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                    bookSyncBak.setConfirmUser(pmphUser.getId());
                    bookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                    bookSyncBak.setBookId(add.getId());
                    bookSyncBak.setSynchronizationType("add");
                    bookSyncService.addBookSynBak(bookSyncBak);

                    // 更新待确认信息
                    bookSyncConfirm.setConfirmUser(pmphUser.getId());
                    bookSyncConfirm.setConfirmStatus(true);

                    BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                    BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                    bookSyncService.updateBookSynConfirm(bookSyncConfirm1);
                } else {

                    // 如果不为空 且图书是删除状态 恢复该图书
                    if (bookByIsbn.getIsDelected()) {
                        bookByIsbn.setIsDelected(false);
                        bookService.updateBook(bookByIsbn);
                        // 更新待确认信息
                        bookSyncConfirm.setConfirmUser(pmphUser.getId());
                        bookSyncConfirm.setConfirmStatus(true);

                        BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                        BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                        bookSyncService.updateBookSynConfirm(bookSyncConfirm1);


                    } else {
                        objectResponseBean.setCode(2);
                        objectResponseBean.setMsg("需要添加图书已存在");
                    }

                }
                break;
            // 类型是更新
            case "update":
                if (ObjectUtil.notNull(bookByIsbn)) {
                    Book book = new Book();
                    // 更新图书信息
                    BeanUtils.copyProperties(bookByIsbn, book);
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
                    // 更新图书详情
                    bookDetail.setBookId(book.getId());
                    bookDetail.setDetail(bookSyncConfirm.getContent());
                    bookService.updateBookDetail(bookDetail);

                    BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(bookId);

                    if (ObjectUtil.isNull(bookSyncBak)) {
                        BookSyncBak newBookSyncBak = new BookSyncBak();
                        BeanUtils.copyProperties(bookByIsbn, newBookSyncBak);
                        newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                        newBookSyncBak.setConfirmUser(pmphUser.getId());
                        newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                        newBookSyncBak.setSynchronizationType("update");
                        newBookSyncBak.setBookId(book.getId());
                        bookSyncService.addBookSynBak(newBookSyncBak);
                    }
                    // 更新待确认信息
                    bookSyncConfirm.setConfirmUser(pmphUser.getId());
                    bookSyncConfirm.setConfirmStatus(true);

                    BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                    BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                    bookSyncService.updateBookSynConfirm(bookSyncConfirm1);

                } else {
                    objectResponseBean.setCode(2);
                    objectResponseBean.setMsg("需要更新的图书不存在");
                }
                break;
            // 类型为上架
            case "shelf":
                if (ObjectUtil.notNull(bookByIsbn)) {
                    Book book = new Book();
                    // 更新图书信息
                    BeanUtils.copyProperties(bookByIsbn, book);
                    book.setType(nodeId);
                    book.setIsOnSale(bookSyncConfirm.getOnSale());
                    book.setGmtUpdate(bookSyncConfirm.getGmtUpdate());
                    bookService.updateBook(book);
                    BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(bookId);

                    if (ObjectUtil.isNull(bookSyncBak)) {
                        // 备份图书信息
                        BookSyncBak newBookSyncBak = new BookSyncBak();
                        BeanUtils.copyProperties(bookByIsbn, newBookSyncBak);
                        newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                        newBookSyncBak.setConfirmUser(pmphUser.getId());
                        newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                        newBookSyncBak.setSynchronizationType("update");
                        newBookSyncBak.setBookId(book.getId());
                        bookSyncService.addBookSynBak(newBookSyncBak);
                    }
                    // 更新待确认信息
                    bookSyncConfirm.setConfirmUser(pmphUser.getId());
                    bookSyncConfirm.setConfirmStatus(true);

                    BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                    BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                    bookSyncService.updateBookSynConfirm(bookSyncConfirm1);
                } else {
                    objectResponseBean.setCode(2);
                    objectResponseBean.setMsg("需要上架的图书不存在");
                }
                break;
            case "obtained":
                if (ObjectUtil.notNull(bookByIsbn)) {
                    Book book = new Book();
                    BeanUtils.copyProperties(bookByIsbn, book);
                    book.setType(nodeId);
                    book.setIsOnSale(bookSyncConfirm.getOnSale());
                    book.setGmtUpdate(bookSyncConfirm.getGmtUpdate());
                    bookService.updateBook(book);
                    BookSyncBak bookSyncBak = bookSyncService.getBookSyncBak(bookId);

                    if (ObjectUtil.isNull(bookSyncBak)) {
                        BookSyncBak newBookSyncBak = new BookSyncBak();
                        BeanUtils.copyProperties(bookByIsbn, newBookSyncBak);
                        newBookSyncBak.setConfirmGmt(DateUtil.getCurrentTime());
                        newBookSyncBak.setConfirmUser(pmphUser.getId());
                        newBookSyncBak.setBookSyncConfirmId(bookSyncConfirm.getId());
                        newBookSyncBak.setSynchronizationType("update");
                        newBookSyncBak.setBookId(book.getId());
                        bookSyncService.addBookSynBak(newBookSyncBak);
                    }
                    // 更新待确认信息
                    bookSyncConfirm.setConfirmUser(pmphUser.getId());
                    bookSyncConfirm.setConfirmStatus(true);

                    BookSyncConfirm bookSyncConfirm1 = new BookSyncConfirm();
                    BeanUtils.copyProperties(bookSyncConfirm, bookSyncConfirm1);
                    bookSyncService.updateBookSynConfirm(bookSyncConfirm1);

                } else {
                    objectResponseBean.setCode(2);
                    objectResponseBean.setMsg("需要下架的图书不存在");
                }
                break;
        }

        return objectResponseBean;
    }

}
