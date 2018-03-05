package com.bc.pmpheep.general.runnable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;

@Service
public class Download {

    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "(http[s]{0,1}):\"?(.*?)(\"|>|\\s+)";

    @Autowired
    private FileService         fileService;

    /***
     * 获取ImageUrl地址
     * 
     * @param HTML
     * @return
     */
    public List<String> getImageUrl(String HTML) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
    }

    /***
     * 获取ImageSrc地址
     * 
     * @param listImageUrl
     * @return
     */
    public List<String> getImageSrc(List<String> listImageUrl) {
        // List<String> listImgSrc = new ArrayList<String>();
        // for (String image : listImageUrl) {
        // String srcStart = "src=\"";
        // String srcEnd = "?";
        // int srcS = image.indexOf(srcStart) + srcStart.length();
        // int srcE = image.indexOf(srcEnd);
        // if (srcE < srcS) {
        // continue;
        // }
        // String content = image.substring(srcS, srcE);
        // listImgSrc.add(content);
        // }
        // return listImgSrc;
        List<String> listImgSrc = new ArrayList<String>();
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return listImgSrc;
    }

    /***
     * 下载图片
     * 
     * @param listImgSrc
     * @throws IOException
     */
    public List<String> listDownload(List<String> listImgSrc) throws IOException {
        List<String> listHtmlImgs = new ArrayList<String>();
        for (String url : listImgSrc) {
            // URL uri = new URL(null,url,new sun.net.www.protocol.https.Handler());
            URL uri = new URL(url);
            // DisableSSLCertificateCheckUtil.disableChecks();
            // 打开链接
            // javax.net.ssl.HttpsURLConnection conn = (javax.net.ssl.HttpsURLConnection)
            // uri.openConnection();
            HttpURLConnection conn = null;
            try {
                trustAllHttpsCertificates();
            } catch (Exception e) {
                e.printStackTrace();
            }
            HostnameVerifier hv = new HostnameVerifier() {
                @Override
                public boolean verify(String urlHostName, SSLSession session) {
                    // TODO Auto-generated method stub
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestProperty("Referer", ""); // 这是破解防盗链添加的参数
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                                    "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
                                    + "Chrome/53.0.2785.116 Safari/537.36");
            conn.setDoOutput(true);
            conn.setDoInput(true); // 是否打开输入流true|false
            conn.setRequestMethod("POST"); // 设置请求方式为"GET"
            conn.setConnectTimeout(5 * 1000); // 超时响应时间为5秒
            conn.connect(); // 打开连接端口
            // 通过输入流获取图片数据
            InputStream in = conn.getInputStream();
            double randomNumber = Math.random() * 100; // 随机数
            Random rand = new Random();
            String mongoId = null;
            if (in.available() != 0) {
                mongoId =
                fileService.save(in,
                                 String.valueOf(randomNumber),
                                 FileType.CMS_IMG,
                                 (long) rand.nextInt(900) + 100);
            }
            listHtmlImgs.add(mongoId);
            in.close();
        }
        return listHtmlImgs;
    }

    /***
     * 下载图片
     * 
     * @param listImgSrc
     */
    public List<String> download(List<String> listImgSrc) {
        List<String> listHtmlImgs = new ArrayList<String>();
        for (String url : listImgSrc) {
            try {
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                Integer wenhaoInteger = imageName.indexOf("?");
                if (wenhaoInteger > 0) {
                    imageName = imageName.substring(0, wenhaoInteger);
                }

                URL uri = new URL(url);
                InputStream in = uri.openStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int length = 0;
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    outStream.write(buf, 0, length);
                }
                byte[] data = outStream.toByteArray(); // 取内存中保存的数据
                InputStream sbs = new ByteArrayInputStream(data);
                double randomNumber = Math.random() * 100; // 随机数
                Random rand = new Random();
                String mongoId = null;
                mongoId =
                fileService.save(sbs,
                                 String.valueOf(randomNumber),
                                 FileType.CMS_IMG,
                                 (long) rand.nextInt(900) + 100);
                listHtmlImgs.add(mongoId);
                in.close();
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listHtmlImgs;
    }

    /**
     * 下载图片
     * 
     * @param listImgSrc 图片url集合
     * @return 返回芒果id集合
     * @throws Exception
     */
    /*
     * public List<String> download(List<String> listImgSrc) throws Exception { InputStream is =
     * null; OutputStream os = null; //存放路径 String savePath = null; //文件名称 String filename = null;
     * List<String> listHtmlImgs = new ArrayList<String>(); for (String urlString : listImgSrc) {
     * try { // 构造URL URL url = new URL(urlString); // 打开连接 URLConnection con =
     * url.openConnection(); // 设置请求超时为5s con.setConnectTimeout(5 * 1000); // 输入流 is =
     * con.getInputStream(); // 1K的数据缓冲 byte[] bs = new byte[1024]; // 读取到的数据长度 int len; // 输出的文件流
     * File sf = new File(savePath); if (!sf.exists()) { sf.mkdirs(); } os = new
     * FileOutputStream(sf.getPath() + "\\" + filename); // 开始读取 while ((len = is.read(bs)) != -1) {
     * os.write(bs, 0, len); } // 新建文件用于存放图片 File file = new File("D://2.png"); Random rand = new
     * Random(); // 上传到芒果 返回芒果id String mongoId =fileService.saveLocalFile(file, FileType.CMS_IMG,
     * (long) rand.nextInt(900) + 100); //把芒果id加到list集合中 listHtmlImgs.add(mongoId); // 删除文件夹以及图片
     * FileUtil.deleteDirectory("D:\\\\"); System.out.println(mongoId); } catch (Exception e) {
     * e.printStackTrace(); } finally { // 完毕，关闭所有链接 os.close(); is.close(); } } return
     * listHtmlImgs; }
     */
    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
        throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
        throws java.security.cert.CertificateException {
            return;
        }
    }
}
