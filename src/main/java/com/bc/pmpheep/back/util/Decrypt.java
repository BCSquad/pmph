package com.bc.pmpheep.back.util;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {
    private static final String password = "123456781qaz@wsX3";

    private Decrypt() {
    }

    public static String decryptPasswd(String passwd) {
        if (passwd != null && passwd.length() > 0) {
            try {
                byte[] shaByte = sha("123456781qaz@wsX3".getBytes());
                byte[] enCodeFormat = Arrays.copyOf(shaByte, 16);
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(2, key);
                byte[] result = cipher.doFinal(Base64.decode(passwd));
                return new String(result, "utf-8");
            } catch (NoSuchAlgorithmException var6) {
                var6.printStackTrace();
            } catch (NoSuchPaddingException var7) {
                var7.printStackTrace();
            } catch (InvalidKeyException var8) {
                var8.printStackTrace();
            } catch (IllegalBlockSizeException var9) {
                var9.printStackTrace();
            } catch (BadPaddingException var10) {
                var10.printStackTrace();
            } catch (UnsupportedEncodingException var11) {
                var11.printStackTrace();
            }
        }

        return null;
    }

    private static byte[] sha(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA");
        return digest.digest(bytes);
    }
}

