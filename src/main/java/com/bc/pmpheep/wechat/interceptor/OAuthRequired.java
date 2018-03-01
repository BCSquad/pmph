package com.bc.pmpheep.wechat.interceptor;

import java.lang.annotation.*;
/**
 * 验证OAuth2注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OAuthRequired {
	
}
