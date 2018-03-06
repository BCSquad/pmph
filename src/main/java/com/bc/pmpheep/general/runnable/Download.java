package com.bc.pmpheep.general.runnable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;

@Service
public class Download {
    Logger                      logger     = LoggerFactory.getLogger(Download.class);
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
     */
    public List<String> download(List<String> listImgSrc) {
        List<String> listHtmlImgs = new ArrayList<String>();
        for (String url : listImgSrc) {
            try {
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                Integer wenhaoInteger = imageName.indexOf("?");
                if (wenhaoInteger >= 0) {
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
                InputStream inputStream = new ByteArrayInputStream(data);
                double randomNumber = Math.random() * 100; // 随机数
                Random rand = new Random();
                String mongoId = null;
                mongoId =
                fileService.save(inputStream,
                                 String.valueOf(randomNumber),
                                 FileType.CMS_IMG,
                                 (long) rand.nextInt(900) + 100);
                listHtmlImgs.add(mongoId);
                in.close();
                outStream.close();
            } catch (Exception e) {
                logger.error("输入或输出异常:{}", e.getMessage());
            }
        }
        return listHtmlImgs;
    }
}
