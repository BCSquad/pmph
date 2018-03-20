/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.dto.SsoRequest;
import com.bc.pmpheep.general.dto.SsoResponse;
import com.bc.pmpheep.general.dto.SsoUser;
import com.bc.pmpheep.general.service.SsoService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * SSO工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component("prototype")
public class SsoHelper {

    @Value("#{spring['sso.url']}")
    private String url;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    static Logger logger = LoggerFactory.getLogger(SsoHelper.class);
    static Gson gson = new Gson();
    Retrofit retrofit;
    volatile Boolean result;
    volatile String message = "";
    volatile SsoUser ssoUser;

    @PostConstruct
    public void postConstruct() {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public String createSSOAccount(OrgUser orgUser) throws CheckedServiceException {
        if (StringUtil.isEmpty(orgUser.getUsername())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                    CheckedExceptionResult.NULL_PARAM, "新建SSO用户时用户名不能为空");
        }
        SsoUser user = new SsoUser();
        user.setUserName(orgUser.getUsername());
        String address = orgUser.getAddress();
        String email = orgUser.getEmail();
        String handphone = orgUser.getHandphone();
        String realname = orgUser.getRealname();
        if (StringUtil.notEmpty(address)) {
            user.setAddress(address);
        }
        if (StringUtil.notEmpty(email)) {
            user.setEmail(email);
        }
        if (StringUtil.notEmpty(handphone)) {
            user.setMobile(handphone);
        }
        if (StringUtil.notEmpty(realname)) {
            user.setRealName(realname);
        }
        user.setPassword("123456");
        SsoService ssoService = retrofit.create(SsoService.class);
        SsoRequest<SsoUser> request = new SsoRequest<>();
        request.setMethod("ZAS.AddUser");
        request.setParams(user);
//        logger.info("request={}", gson.toJson(request));
        result = null;
        Call<SsoResponse> call = ssoService.addUser(request);
        call.enqueue(new Callback<SsoResponse>() {
            @Override
            public void onResponse(Call<SsoResponse> call, Response<SsoResponse> response) {
                boolean httpSuccess = response.isSuccessful();
                logger.info("isSuccessful={}", httpSuccess);
                logger.info("code={}", response.code());
                if (!httpSuccess) {
                    result = false;
                    message = "向SSO发送请求失败，错误代码".concat(String.valueOf(response.code()));
                    return;
                }
                if (null != response.body()) {
                    SsoResponse<SsoUser> ssoResponse = response.body();
                    result = ssoResponse.getSuccess();
                    message = ssoResponse.getMessage();
                    logger.info("sso.success={}", result);
                    logger.info("sso.message={}", message);
                    logger.info("sso.userdata={}", gson.toJson(ssoResponse.getUserData()));
                } else {
                    result = false;
                    message = "已向SSO成功发送请求，但没有数据返回";
                    logger.warn(message);
                }
            }

            @Override
            public void onFailure(Call<SsoResponse> call, Throwable t) {
                logger.error("请求出错", t);
                result = false;
                message = t.getMessage();
            }
        });
        while (null == result) {
        }
        if (result) {
            return "success";
        } else {
            return message;
        }
    }

    public boolean resetPassword(String loginID, String newPassword) {
        if (StringUtil.isEmpty(loginID) || StringUtil.isEmpty(newPassword)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                    CheckedExceptionResult.NULL_PARAM, "重置SSO用户密码时用户名和新的密码均不能为空");
        }
        SsoService ssoService = retrofit.create(SsoService.class);
        SsoRequest<Map<String, String>> request = new SsoRequest<>();
        request.setMethod("ZAS.ModifyUser");
        Map<String, String> map = new HashMap<>(2);
        map.put("LoginID", loginID);
        map.put("Password", newPassword);
        request.setParams(map);
        result = null;
        Call<SsoResponse> call = ssoService.modifyUser(request);
        call.enqueue(new Callback<SsoResponse>() {
            @Override
            public void onResponse(Call<SsoResponse> call, Response<SsoResponse> response) {
                boolean httpSuccess = response.isSuccessful();
                logger.info("isSuccessful={}", httpSuccess);
                logger.info("code={}", response.code());
                if (!httpSuccess) {
                    result = false;
                    return;
                }
                if (null != response.body()) {
                    SsoResponse<SsoUser> ssoResponse = response.body();
                    result = ssoResponse.getSuccess();
                    logger.info("sso.success={}", result);
                    logger.info("sso.message={}", message);
                    logger.info("sso.userdata={}", gson.toJson(ssoResponse.getUserData()));
                } else {
                    result = false;
                    message = "已向SSO成功发送请求，但没有数据返回";
                    logger.warn(message);
                }
            }

            @Override
            public void onFailure(Call<SsoResponse> call, Throwable t) {
                logger.error("请求出错", t);
                result = false;
            }
        });
        while (null == result) {
        }
        return result;
    }

    public SsoUser getUserInfo(String loginID, String password) {
        if (StringUtil.isEmpty(loginID) || StringUtil.isEmpty(password)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                    CheckedExceptionResult.NULL_PARAM, "查询SSO用户时用户名和密码均不能为空");
        }
        SsoService ssoService = retrofit.create(SsoService.class);
        SsoRequest<Map<String, String>> request = new SsoRequest<>();
        request.setMethod("ZAS.UserInfo");
        Map<String, String> map = new HashMap<>(2);
        map.put("LoginID", loginID);
        map.put("Password", password);
        request.setParams(map);
        result = null;
        ssoUser = null;
        Call<SsoResponse> call = ssoService.getUserInfo(request);
        call.enqueue(new Callback<SsoResponse>() {
            @Override
            public void onResponse(Call<SsoResponse> call, Response<SsoResponse> response) {
                boolean httpSuccess = response.isSuccessful();
                logger.info("isSuccessful={}", httpSuccess);
                logger.info("code={}", response.code());
                if (!httpSuccess) {
                    result = false;
                    message = "向SSO发送请求失败，错误代码".concat(String.valueOf(response.code()));
                    return;
                }
                if (null != response.body()) {
                    SsoResponse<SsoUser> ssoResponse = response.body();
                    result = ssoResponse.getSuccess();
                    message = ssoResponse.getMessage();
                    ssoUser = ssoResponse.getUserData();
                    logger.info("sso.success={}", result);
                    logger.info("sso.message={}", message);
                    logger.info("sso.userdata={}", gson.toJson(ssoResponse.getUserData()));
                } else {
                    result = false;
                    message = "已向SSO成功发送请求，但没有数据返回";
                    logger.warn(message);
                }
            }

            @Override
            public void onFailure(Call<SsoResponse> call, Throwable t) {
                logger.error("请求出错", t);
            }
        });
        while (null == result) {
        }
        return ssoUser;
    }
}
