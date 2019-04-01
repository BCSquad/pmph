package com.bc.pmpheep.back.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.pmpheep.back.service.BookSyncService;
import com.bc.pmpheep.back.util.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.net.www.http.HttpClient;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

@Component
public class FlightTrainTask {


    @Resource
    private BookSyncService bookSyncService;
    private static final Logger logger = LoggerFactory
            .getLogger(FlightTrainTask.class.getName());

    //生成文件路径
    private static String path = "/usr/local/tomcat/logs/";

    //文件路径+名称
    private static String filenameTemp;
    /**
     * 签名编码
     */
    public static final String UTF8 = "utf-8";
    /**
     * 签名key
     */
    public static final String SIGN_KEY = "sign";


    @Scheduled(cron = "0 0 1 1/1 * ? ")  //每隔一天执行一次定时任务
    public void consoleInfo() {
        try {
            SyncBookSellWell();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void SyncBookSellWell() throws UnsupportedEncodingException {
        Map<String, Object> api = new HashMap<String, Object>();
        api.put("app_key", "nmkt8v9NkWbQ9WPFl3l6lFNsyThsfcep");
        api.put("format", "json");
        api.put("method", "com.ai.ecp.pmph.order.saleRank");
        api.put("session", "MDzjI012CaqX4HG1HbOj35ps1yOYxJ7KfL1ezjKT89OLZZe0f22S6LY6eZ4DleBR");
        api.put("sign_method", "md5");
        api.put("timestamp", DateUtil.formatTimeStamp("yyy-MM-dd HH:mm:ss", DateUtil.getCurrentTime()));
        api.put("v", "1.0");
        String sign = DigestUtil.digest(api, "hbP5YsbmiWnkOP4IPtXE126JiIaFRCWD4gpfrcULPbs5hytCw06T2SooKfcUnc2g");
        String params = SyncUtils.getUrlApi(api);
        params += "&sign=" + sign;
        params += "&biz_content=" + CodecUtil.encodeURL("{\"num\":10}");
        /* params=CodecUtil.encodeURL(params);*/

        String url = "http://aip.pmph.com/route/rest";
        String res = SyncUtils.StringGet(params, url);

        JSONObject jsonObject = JSON.parseObject(res);
        Integer code = jsonObject.getInteger("code");
        List<Map<String,Object>> sales= new ArrayList<Map<String, Object>>();
        if(code==0){
            String msg = jsonObject.getString("msg");
            JSONArray goodsList = jsonObject.getJSONArray("goodsList");
            for(Object o:goodsList){
                Map<String,Object> saleMap=new HashMap<>();
                JSONObject jso = JSON.parseObject(o.toString());
                String vn = jso.getString("bb_code");
                String sale = jso.getString("trade_amount");
                saleMap.put("vn", vn);
                saleMap.put("sale",sale);
                sales.add(saleMap);
            }
            int i = bookSyncService.updateBookSaleByVns(sales);


        }


        createFile("SyncSaleLog",new Date().toLocaleString()+"同步图书原始数据:"+res);
    }


    /**
     * 创建文件
     * @param fileName  文件名称
     * @param filecontent   文件内容
     * @return  是否创建成功，成功则返回true
     */
    public static boolean createFile(String fileName,String filecontent){
        Boolean bool = false;
        filenameTemp = path+fileName+".txt";//文件路径+名称+文件类型
        File file = new File(filenameTemp);
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is "+filenameTemp);
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bool;
    }


    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath,String newstr) throws IOException{
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

}