/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.service;

import com.bc.pmpheep.general.dto.SsoRequest;
import com.bc.pmpheep.general.dto.SsoResponse;
import com.bc.pmpheep.general.dto.SsoUser;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * SSO三方服务调用接口
 *
 * @author L.X <gugia@qq.com>
 */
public interface SsoService {

    @POST("api/json")
    Call<SsoResponse> getUserInfo(@Body SsoRequest<Map<String, String>> request);

    @POST("api/json")
    Call<SsoResponse> modifyUser(@Body SsoRequest<Map<String, String>> request);

    @POST("api/json")
    Call<SsoResponse> addUser(@Body SsoRequest<SsoUser> request);
}
