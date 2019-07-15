package com.bc.pmpheep.back.controller;

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
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
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

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.Buffer;
import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/pmphWeb")
@SuppressWarnings({"rawtypes", "unchecked"})
public class PmphUserSyncController {

    @Autowired
    PmphUserService pmphUserService;

    @Autowired
    private UserMessageService userMessageService;

    private static final String BUSSINESS_TYPE = "同步社内用户";




    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "同步社内用户")
    @RequestMapping(value = "/syncDatas", method = RequestMethod.POST)
    public void syncDatas(HttpServletRequest request, @RequestBody String json) {
        System.out.println("同步社内用户");
        System.out.println(json);
        try{
          /*  JSONObject jsonObject = JSON.parseObject(json);
            System.out.println(jsonObject);*/

        /*    if(jsonObject!=null){
                JSONArray goodsList = jsonObject.getJSONArray("params");
                if(goodsList!=null) {
                    for (Object o : goodsList) {
                        PmphUser user = JSON.parseObject(o.toString(), PmphUser.class);
                        PmphUser old = pmphUserService.getPmphUser(user.getUsername());
                        if (old != null) {
                            PmphUser newU = new PmphUser();
                            newU.setId(user.getId());
                            if (user.getPassword() != null) newU.setPassword(user.getPassword());
                            if (user.getAvatar() != null) newU.setAvatar(user.getAvatar());
                            if (user.getDepartmentId() != null) newU.setDepartmentId(user.getDepartmentId());
                            if (user.getRealname() != null) newU.setRealname(user.getRealname());
                            if (user.getIsAdmin() != null) newU.setIsAdmin(user.getIsAdmin());
                            if (user.getIsDisabled() != null) newU.setIsDisabled(user.getIsDisabled());
                            if (user.getLoginType() != null) newU.setLoginType(user.getLoginType());
                            if (user.getEmail() != null) newU.setEmail(user.getEmail());
                            if (user.getHandphone() != null) newU.setHandphone(user.getHandphone());
                            if (user.getNote() != null) newU.setNote(user.getNote());
                            if (user.getGmtCreate() != null) newU.setGmtCreate(user.getGmtCreate());
                            if (user.getIsDirector() != null) newU.setIsDirector(user.getIsDirector());
                            if (user.getSort() != null) newU.setSort(user.getSort());
                            if (user.getGmtUpdate() != null) newU.setGmtUpdate(user.getGmtUpdate());
                            if (user.getIsDeleted() != null) newU.setIsDeleted(user.getIsDeleted());
                            if (user.getOpenid() != null) newU.setOpenid(user.getOpenid());
                            pmphUserService.updateUser(newU);
                        } else {
                            PmphUser newU = new PmphUser();
                            if (user.getUsername() != null) newU.setUsername(user.getUsername());
                            if (user.getPassword() != null) newU.setPassword(user.getPassword());
                            if (user.getAvatar() != null) newU.setAvatar(user.getAvatar());
                            if (user.getDepartmentId() != null) newU.setDepartmentId(user.getDepartmentId());
                            if (user.getRealname() != null) newU.setRealname(user.getRealname());
                            if (user.getIsAdmin() != null) newU.setIsAdmin(user.getIsAdmin());
                            if (user.getIsDisabled() != null) newU.setIsDisabled(user.getIsDisabled());
                            if (user.getLoginType() != null) newU.setLoginType(user.getLoginType());
                            if (user.getEmail() != null) newU.setEmail(user.getEmail());
                            if (user.getHandphone() != null) newU.setHandphone(user.getHandphone());
                            if (user.getNote() != null) newU.setNote(user.getNote());
                            if (user.getGmtCreate() != null) newU.setGmtCreate(user.getGmtCreate());
                            if (user.getIsDirector() != null) newU.setIsDirector(user.getIsDirector());
                            if (user.getSort() != null) newU.setSort(user.getSort());
                            if (user.getGmtUpdate() != null) newU.setGmtUpdate(user.getGmtUpdate());
                            if (user.getIsDeleted() != null) newU.setIsDeleted(user.getIsDeleted());
                            if (user.getOpenid() != null) newU.setOpenid(user.getOpenid());
                            pmphUserService.add(newU);
                        }

                    }
                }}*/
        }catch (Exception e){
            e.printStackTrace();
        }


    }




}
