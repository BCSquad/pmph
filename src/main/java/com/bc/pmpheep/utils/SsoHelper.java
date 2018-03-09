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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Service
public class SsoHelper {

    static Logger logger = LoggerFactory.getLogger(SsoHelper.class);
    static Gson gson = new Gson();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://sso.ipmph.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    volatile Boolean result;
    volatile String message = "";

    public String createSSOAccount(OrgUser orgUser) throws CheckedServiceException {
        if (StringUtil.isEmpty(orgUser.getUsername())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                    CheckedExceptionResult.NULL_PARAM, "新建SSO用户时用户名不能为空");
        }
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserName(orgUser.getUsername());
        String address = orgUser.getAddress();
        String email = orgUser.getEmail();
        String handphone = orgUser.getHandphone();
        String realname = orgUser.getRealname();
        if (StringUtil.notEmpty(address)) {
            ssoUser.setAddress(address);
        }
        if (StringUtil.notEmpty(email)) {
            ssoUser.setEmail(email);
        }
        if (StringUtil.notEmpty(handphone)) {
            ssoUser.setMobile(handphone);
        }
        if (StringUtil.notEmpty(realname)) {
            ssoUser.setRealName(realname);
        }
        ssoUser.setPassword("123456");
        SsoService ssoService = retrofit.create(SsoService.class);
        SsoRequest<SsoUser> request = new SsoRequest<>();
        request.setMethod("ZAS.AddUser");
        request.setParams(ssoUser);
        logger.info("request={}", gson.toJson(request));
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

    private void test() {
        SsoService ssoService = retrofit.create(SsoService.class);
        SsoRequest<Map<String, String>> request = new SsoRequest<>();
        request.setMethod("ZAS.UserInfo");
        Map<String, String> map = new HashMap<>(2);
        map.put("LoginID", "gugia");
        map.put("Password", "123456");
        request.setParams(map);
        logger.info("request={}", gson.toJson(request));
        Call<Map> call = ssoService.getUserInfo2(request);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                logger.info("isSuccessful={}", response.isSuccessful());
                logger.info("code={}", response.code());
                logger.info("headers={}", response.headers());
                logger.info("message={}", response.message());
                logger.info("errorBody={}", response.errorBody());
                logger.info("raw={}", response.raw());
                if (null != response.body()) {
                    Map<String, ?> map = response.body();
                    logger.info("body={}", gson.toJson(map));
                } else {
                    logger.warn("请求失败");
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                logger.error("请求出错", t);
            }
        });
    }
}
