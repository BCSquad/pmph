package com.bc.pmpheep.general.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
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
        	InetAddress ia = InetAddress.getLocalHost();
            new URL("http://" + ia.getHostAddress() + "/").getContent();
        } catch (IOException e) {
            // This invocation will always fail, but it will register the
            // default SSL provider to the URL class.
        }
        try {
            SSLContext sslc;
            sslc = SSLContext.getInstance("TLS");
            TrustManager[] trustManagerArray = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            sslc.init(null, trustManagerArray, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (Exception e) {
            LOGGER.error("error msg:{}", e);
            throw new IllegalArgumentException("证书校验异常！");
        }
    }
}