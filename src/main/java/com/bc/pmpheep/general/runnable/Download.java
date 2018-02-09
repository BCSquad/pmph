package com.bc.pmpheep.general.runnable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;    
import org.apache.http.conn.ssl.SSLSocketFactory;  

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
			URL uri = new URL(url);
	        DisableSSLCertificateCheckUtil.disableChecks();
	        SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());   
	        //打开链接  
	        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();  
	        //设置请求方式为"GET"  
	        conn.setRequestMethod("GET");  
	        //超时响应时间为5秒  
	        conn.setConnectTimeout(5 * 1000);  
	        //通过输入流获取图片数据  
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
}
