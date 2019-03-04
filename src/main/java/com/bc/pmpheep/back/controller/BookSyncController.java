package com.bc.pmpheep.back.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookSyncConfirm;
import com.bc.pmpheep.back.po.BookSyncLog;
import com.bc.pmpheep.back.service.BookSyncService;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
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
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/apitest")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BookSyncController {


    @Autowired
    BookSyncService bookSyncService;


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


}
