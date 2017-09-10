/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.controller.bean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常AOP拦截器
 *
 * @author L.X <gugia@qq.com>
 */
public class ResponseBeanAop {

    Logger logger = LoggerFactory.getLogger(ResponseBeanAop.class);

    public Object controllerMethodHandler(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        ResponseBean<?> responseBean;
        try {
            responseBean = (ResponseBean<?>) pjp.proceed();
            logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable ex) {
            responseBean = exceptionHandler(pjp, ex);
        }
        return responseBean;
    }

    private ResponseBean<?> exceptionHandler(ProceedingJoinPoint pjp, Throwable ex) {
        ResponseBean<?> responseBean = new ResponseBean();
        // 已知异常
        if (ex instanceof IndexOutOfBoundsException) {
            responseBean.setMsg(ex.getLocalizedMessage());
            responseBean.setCode(ResponseBean.FAILURE);
        } else {
            logger.error(pjp.getSignature() + " error ", ex);
            responseBean.setMsg(ex.toString());
            responseBean.setCode(ResponseBean.FAILURE);
            // 未知异常是应该重点关注的，这里可以做其他操作，如通知邮件，单独写到某个文件等等。
        }
        return responseBean;
    }
}
