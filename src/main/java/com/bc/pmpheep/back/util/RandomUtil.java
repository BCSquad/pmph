package com.bc.pmpheep.back.util;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 
 * <pre>
 * 功能描述：随机数工具类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-19
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public final class RandomUtil {

    private RandomUtil() {
    }

    /**
     * 生成随机数
     */
    public static String getRandom(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * 随机生成六位数验证码
     * 
     * @return
     */
    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;// (Math.random()*(999999-100000)+100000)
    }

    public static void main(String[] args) {
        System.out.println(getRandomNum());
    }
}
