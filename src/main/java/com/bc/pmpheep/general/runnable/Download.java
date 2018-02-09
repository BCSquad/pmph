package com.bc.pmpheep.general.runnable;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;

@Service
public class Download {

    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";

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
        List<String> listImgSrc = new ArrayList<String>();
        for (String image : listImageUrl) {
            String srcStart = "src=\"";
            String srcEnd = "?";
            int srcS = image.indexOf(srcStart) + srcStart.length();
            int srcE = image.indexOf(srcEnd);
            if (srcE < srcS) {
                continue;
            }
            String content = image.substring(srcS, srcE);
            listImgSrc.add(content);
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
            URL uri = new URL(url);
            // DisableSSLCertificateCheckUtil.disableChecks();
            // SSLSocketFactory.getSocketFactory().setHostnameVerifier(new
            // AllowAllHostnameVerifier());
            DataInputStream dis = new DataInputStream(uri.openStream());
            String newImageName = "D:/2.jpg";
            // 建立一个新的文件
            FileOutputStream fos = new FileOutputStream(new File(newImageName));
            byte[] buffer = new byte[1024];
            int length;
            // 开始填充数据
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            // 打开链接
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            // 设置请求方式为"GET"
            conn.setRequestMethod("GET");
            // 超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
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

    public static void download(String urlString, String filename, String savePath)
    throws IOException {
        OutputStream os = null;
        InputStream is = null;
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf = new File("D:\\imgFile");
            if (!sf.exists()) {
                sf.mkdirs();
            }
            os = new FileOutputStream(sf.getPath() + "\\" + filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {

        } finally {
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }
    }

    public static void main(String[] args) throws Exception {
        // Download download = new Download();
        // List<String> list = new ArrayList<String>();
        // list.add("https://bpic.588ku.com/element_banner/20/18/02/7fdfd5de728d6f4eb5555dfeed1b5c5c.png");
        // String aaString =
        // "https://bpic.588ku.com/element_banner/20/18/02/7fdfd5de728d6f4eb5555dfeed1b5c5c.png";
        // download.listDownload(list);
        // download(aaString, "a.png", "d:/");
        double randomNumber = Math.random(); // 随机数
        System.out.println(String.valueOf(randomNumber));
    }
}
