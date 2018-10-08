package com.bc.pmpheep.test;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @Author：MrXiao
 * @Description:
 * @Date:created in 17:51 2018/9/29
 * @Modified By:
 */
public class A {

    @Test
    public void testCharset() throws UnsupportedEncodingException {
        String s = "你好";

// 编码
        byte[] utf = s.getBytes("utf-8");
        byte[] gbk = s.getBytes("gbk");
        byte[] iso = s.getBytes("ISO-8859-1");

        System.out.println("utf-8编码：" + Arrays.toString(utf)); // [-28, -67, -96, -27, -91, -67]  6个字节
        System.out.println("gbk编码：" + Arrays.toString(gbk)); // [-60, -29, -70, -61]	4个字节
        System.out.println("ISO-8859-1：" + Arrays.toString(iso));

// 解码
        String s1 = new String(utf, "utf-8"); // 你好
        String s2 = new String(utf, "gbk");// gbk解码：浣犲ソ gbk用2个字节解码，所以会多一个字符
        String s3 = new String(gbk, "utf-8");// gbk用utf-8解码：??? 	utf-8解码需要6个字节
        String s4 = new String(iso,"ISO-8859-1");
        String s5 = new String(iso,"utf-8"); // iso 编码 utf-8 解码
        String s6 = new String(gbk,"ISO-8859-1"); // gbk 编码 iso解码
        String s7 = new String(utf,"ISO-8859-1");// utf 编码 iso解码

        System.out.println("--------------------");
        System.out.println("utf-8解码：" + s1);
        System.out.println("gbk解码：" + s2);
        System.out.println("gbk用utf-8解码：" + s3);
        System.out.println("iso  解码: "+s5);
        System.out.println("iso 编码 utf-8 解码: "+s5);
        System.out.println("gbk 编码 iso解码: "+s6);
        System.out.println("utf 编码 iso解码: "+s7);
        System.out.println("---------------------");

        System.out.println("用utf-8编码回去");

        s3 = new String(s3.getBytes("utf-8"), "gbk");  // 锟斤拷锟?   gbk用utf-8解码后无法编回去

        System.out.println(s3);

    }
}
