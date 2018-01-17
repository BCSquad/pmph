/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.util;

/**
 * 二进制工具类
 *
 * @author L.X <gugia@qq.com>
 */
public final class BinaryUtil {

    /**
     * 判断整数num的第i位是否为1，若是返回true，反之返回false
     *
     * @param num 任意整数
     * @param i 整数的二进制位数
     * @return 布尔表达式
     */
    public static boolean getBit(int num, int i) {
        return ((num & (1 << i)) != 0);
    }
}
