package com.bc.pmpheep.back.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class MD5 {

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    // public static void main(String[] args) {
    // System.out.println(md5("31119@qq.com" + "123456"));
    // System.out.println(md5("mj1"));
    // }
    public static void main(String[] args) {
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(2L);
        List<Long> list1 = new ArrayList<Long>();
        list1.add(2L);
        list1.add(3L);
        for (Long id : list) {
            if (list1.contains(id)) {
                System.out.println(id);
            }
        }
    }
}
