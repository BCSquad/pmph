/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.controller.bean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 异常AOP拦截器
 * 
 * @author L.X <gugia@qq.com>
 */
public class ResponseBeanAop {

    Logger logger = LoggerFactory.getLogger(ResponseBeanAop.class);

    public Object controllerMethodHandler(ProceedingJoinPoint pjp) throws Throwable {
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

    @SuppressWarnings("rawtypes")
    private ResponseBean<?> exceptionHandler(ProceedingJoinPoint pjp, Throwable ex) {
        ResponseBean<?> responseBean = new ResponseBean();
        StringBuilder sb = new StringBuilder();
        sb.append(pjp.getSignature().toString());
        sb.append(" 发生错误:{}");
        if (ex instanceof CheckedServiceException) {
            responseBean.setCode(((CheckedServiceException) ex).getResult().getValue());
            // 如果是已检查的异常，不打印异常堆栈
            responseBean.setMsg(((CheckedServiceException) ex).getBusiness() + "===>"
                                + ex.getMessage());
            logger.error(sb.toString(), ex.toString());
        } else if (ex instanceof IndexOutOfBoundsException) {
            responseBean.setMsg("下标越界异常");
            logger.error(sb.toString(), ex.toString());
        } else if (ex instanceof ClassCastException) {
            responseBean.setMsg("类型转换异常");
            logger.error(sb.toString(), ex.toString());
        } else {
            responseBean.setMsg(ex.toString());
            responseBean.setCode(ResponseBean.UNKNOWN_ERROR);
            // 未知异常应打印堆栈
            logger.error(pjp.getSignature() + " 发生未知错误", ex);
        }

        return responseBean;
    }
}
