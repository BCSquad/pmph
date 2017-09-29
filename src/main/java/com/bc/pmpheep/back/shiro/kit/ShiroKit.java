package com.bc.pmpheep.back.shiro.kit;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 
 * <pre>
 * 功能描述：盐值 MD5加密
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-9-29
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class ShiroKit {

    public static String md5(String originPassword, String salt) {
        return new Md5Hash(originPassword, salt).toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("123456", "admin"));
    }
}
