package com.bc.pmpheep.wechat.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.wechat.enums.EnumMethod;

/**
 * 
 * <pre>
 * 功能描述：HttpRequestUtil 工具类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-2-27
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class HttpRequestUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * 发起https请求并获取结果
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {

        System.err.println(requestMethod + "\toutputStr=" + outputStr);

        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
            // System.out.println("jsonObject="+jsonObject);
        } catch (ConnectException ce) {
            logger.error("网络链接:{}", ce.getMessage());
        } catch (UnknownHostException uhe) {
            logger.error("微信API访问异常:{}", uhe.getMessage());
            // httpRequest(requestUrl, requestMethod, outputStr);
        } catch (Exception e) {
            logger.error("出现异常：{}", e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 发起https请求并获取字节数组结果
     * 
     * @param requestUrl
     * @param requestMethod
     * @param data
     * @return
     */
    public static byte[] httpRequest_byte(String requestUrl, String requestMethod, byte[] data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            if (requestMethod == com.bc.pmpheep.wechat.enums.EnumMethod.GET.name() && data != null
                && data.length > 0) {
                if (requestUrl.indexOf('?') > 0) {
                    requestUrl += '&';
                } else {
                    requestUrl += '?';
                }
                requestUrl += new String(data);
            }
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            if (httpUrlConn instanceof HttpsURLConnection) {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = { new MyX509TrustManager() };
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                ((HttpsURLConnection) httpUrlConn).setSSLSocketFactory(ssf);
            }
            boolean truePost =
            requestMethod == EnumMethod.POST.name() && data != null && data.length > 0;
            httpUrlConn.setDoOutput(truePost);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if (requestMethod == EnumMethod.GET.name()) {
                httpUrlConn.connect();
            } else if (truePost) {
                // 提交数据
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(data);
                outputStream.close();
            }

            // 读取返回数据
            InputStream inputStream = httpUrlConn.getInputStream();
            byte[] buf = new byte[1024 * 2];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            // 释放资源
            out.close();
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (ConnectException ce) {
            logger.error("网络链接:{}", ce.getMessage());
        } catch (Exception e) {
            logger.error("出现异常：{}", e.getMessage());
        }
        return out.toByteArray();
    }
}
