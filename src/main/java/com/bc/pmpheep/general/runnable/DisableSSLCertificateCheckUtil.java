package com.bc.pmpheep.general.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public final class DisableSSLCertificateCheckUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisableSSLCertificateCheckUtil.class);

    /**
     * Prevent instantiation of utility class.
     */

    private DisableSSLCertificateCheckUtil() {

    }

    /**
     * Disable trust checks for SSL connections.
     */

    public static void disableChecks() {
        try {
            new URL("https://0.0.0.0/").getContent();
        } catch (IOException e) {
            // This invocation will always fail, but it will register the
            // default SSL provider to the URL class.
        }
        try {         
            SSLContext sc = SSLContext.getInstance("TLS");  
            sc.init(null, new TrustManager[] { new X509TrustManager() {  
                @Override  
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                }  
          
          
                @Override  
                public void checkServerTrusted(X509Certificate[] chain, String authType)  
          
          
                throws CertificateException {  
                }  
          
          
                @Override  
                public X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
            } }, new SecureRandom());  
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {  
                @Override  
                public boolean verify(String arg0, SSLSession arg1) {  
                    return true;  
                }  
            });  
        } catch (Exception e) {  
            e.printStackTrace();
            throw new IllegalArgumentException("证书校验异常！");
        }  
    }
}
