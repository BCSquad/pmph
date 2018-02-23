package com.bc.pmpheep.general.runnable;

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

import com.bc.pmpheep.back.util.FileUtil;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;

@Service
public class Download {

    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";

    @Autowired
    private FileService fileService;
    
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
			//URL uri = new URL(null,url,new sun.net.www.protocol.https.Handler());
			URL uri = new URL(url);
	        DisableSSLCertificateCheckUtil.disableChecks();
	        // 打开链接  
	        //javax.net.ssl.HttpsURLConnection conn = (javax.net.ssl.HttpsURLConnection) uri.openConnection();
	        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
	        conn.setRequestProperty("Referer", ""); // 这是破解防盗链添加的参数
	        conn.setRequestProperty("accept", "*/*");
	        conn.setRequestProperty("connection", "Keep-Alive");
	        conn.setRequestProperty("user-agent", 
	        		"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
	        		+ "Chrome/53.0.2785.116 Safari/537.36");
	        conn.setDoOutput(true);
	        conn.setDoInput(true); // 是否打开输入流true|false
	        conn.setRequestMethod("GET"); // 设置请求方式为"GET"
	        conn.setConnectTimeout(5 * 1000); // 超时响应时间为5秒 
	        conn.connect(); // 打开连接端口
	        // 通过输入流获取图片数据  
	        InputStream in = conn.getInputStream();
	        double randomNumber = Math.random()*100; // 随机数
	        Random rand = new Random();
			String mongoId = null;
			if (in.available() != 0) {
				mongoId = fileService.save(in, String.valueOf(randomNumber), FileType.CMS_IMG, 
						(long) rand.nextInt(900)+ 100);
			}
			listHtmlImgs.add(mongoId);
			in.close();
		}
		return listHtmlImgs; 
	}
	
	/**
	 * 下载图片
	 * @param listImgSrc
	 * 			图片url集合
	 * @return	
	 * 			返回芒果id集合
	 * @throws Exception
	 */
	public List<String> download(List<String> listImgSrc) throws Exception {
        InputStream is = null;
        OutputStream os = null;
        //存放路径
        String savePath = null;
        //文件名称
        String filename = null;
        List<String> listHtmlImgs = new ArrayList<String>();
        for (String urlString : listImgSrc) {
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
                 File sf = new File(savePath);
                 if (!sf.exists()) {
                     sf.mkdirs();
                 }
                 os = new FileOutputStream(sf.getPath() + "\\" + filename);
                 // 开始读取
                 while ((len = is.read(bs)) != -1) {
                     os.write(bs, 0, len);
                 }
                 // 新建文件用于存放图片
                 File file = new File("D://2.png");
                 Random rand = new Random();
                 // 上传到芒果 返回芒果id 
                 String mongoId =fileService.saveLocalFile(file, FileType.CMS_IMG, (long) rand.nextInt(900) + 100);
                 //把芒果id加到list集合中
                 listHtmlImgs.add(mongoId);
                 // 删除文件夹以及图片
                 FileUtil.deleteDirectory("D:\\\\");
                 System.out.println(mongoId);
             } catch (Exception e) {
             	e.printStackTrace();
             } finally {
                 // 完毕，关闭所有链接
                 os.close();
                 is.close();
             }
		}
		return listHtmlImgs;
    }
   
}
