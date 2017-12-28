package com.bc.pmpheep.back.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 
 * <pre>
 * 功能描述：HTTP 请求工具类 
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-28
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("deprecation")
public class HttpUtil {
    static PoolingHttpClientConnectionManager connMgr;
    static RequestConfig                      requestConfig;
    private static final int                  MAX_TIMEOUT = 7000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        // configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 
     * <pre>
     * 功能描述：发送 GET 请求（HTTP），不带输入数据
     * 使用示范：
     *
     * @param url 请求地址
     * @return
     * </pre>
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, Object>(), false);
    }

    /**
     * 
     * <pre>
     * 功能描述：发送 GET 请求（HTTP），Key-Value形式
     * 使用示范：
     *
     * @param url  请求地址
     * @param params 参数map
     * @param isHttps 
     * @return
     * </pre>
     */
    public static String doGet(String url, Map<String, Object> params, boolean isHttps) {
        HttpResponse response = null;
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        CloseableHttpClient httpclient = null;
        if (isHttps) {
            httpclient = createSSLClientDefault();
        } else {
            httpclient = HttpClients.createDefault();
        }
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("执行状态码 : " + statusCode);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // InputStream instream = entity.getContent();
                // result = IOUtils.toString(instream, "UTF-8");
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 
     * <pre>
     * 功能描述：发送 POST 请求（HTTP），不带输入数据
     * 使用示范：
     *
     * @param apiUrl API接口URL
     * @return
     * </pre>
     */
    public static String doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 
     * <pre>
     * 功能描述：发送 POST 请求（HTTP），K-V形式
     * 使用示范：
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     * </pre>
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair =
                new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 
     * <pre>
     * 功能描述：发送 POST 请求（HTTP），JSON形式
     * 使用示范：
     *
     * @param apiUrl API接口URL
     * @param json JSON对象
     * @return
     * </pre>
     */
    public static String doPost(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 
     * <pre>
     * 功能描述：发送 SSL POST 请求（HTTPS），Key-Value形式
     * 使用示范：
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     * </pre>
     */
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient =
        HttpClients.custom()
                   .setSSLSocketFactory(createSSLConnSocketFactory())
                   .setConnectionManager(connMgr)
                   .setDefaultRequestConfig(requestConfig)
                   .build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair =
                new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 
     * <pre>
     * 功能描述：发送 SSL POST 请求（HTTPS），JSON形式
     * 使用示范：
     * 
     * @param apiUrl API接口URL
     * @param json JSON对象
     * @return
     * </pre>
     */
    public static String doPostSSL(String apiUrl, Object json) {
        CloseableHttpClient httpClient =
        HttpClients.custom()
                   .setSSLSocketFactory(createSSLConnSocketFactory())
                   .setConnectionManager(connMgr)
                   .setDefaultRequestConfig(requestConfig)
                   .build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 
     * <pre>
     * 功能描述：创建SSL安全连接
     * 使用示范：
     *
     * @return
     * </pre>
     */
    public static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext =
            new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts)
                throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    /**
     * 
     * <pre>
     * 功能描述：创建HttpClient
     * 使用示范：
     *
     * @return
     * </pre>
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext =
            new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
}
