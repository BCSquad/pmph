package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.pmpheep.back.service.BookSyncService;
import com.bc.pmpheep.back.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookDetail;
import com.bc.pmpheep.back.po.BookUserLike;
import com.bc.pmpheep.back.po.BookUserMark;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.vo.BookPreferenceAnalysisVO;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.test.BaseTest;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图书单元测试
 * 
 * @author
 * @date 2017-11-06
 * @修改人 mr
 */
public class BookServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(BookServiceTest.class);

	@Resource
	private BookService bookService;
	@Resource
	private BookSyncService bookSyncService;

	@Test
	public void SyncBookSellWell() throws UnsupportedEncodingException {
		Map<String,Object> api=new HashMap<String,Object>();
		api.put("app_key","nmkt8v9NkWbQ9WPFl3l6lFNsyThsfcep");
		api.put("format","json");
		api.put("method","com.ai.ecp.pmph.order.saleRank");
		api.put("session","MDzjI012CaqX4HG1HbOj35ps1yOYxJ7KfL1ezjKT89OLZZe0f22S6LY6eZ4DleBR");
		api.put("sign_method","md5");
		api.put("timestamp", DateUtil.formatTimeStamp("yyy-MM-dd HH:mm:ss",DateUtil.getCurrentTime()));
		api.put("v","1.0");
		String sign = DigestUtil.digest(api, "hbP5YsbmiWnkOP4IPtXE126JiIaFRCWD4gpfrcULPbs5hytCw06T2SooKfcUnc2g");
		String params = SyncUtils.getUrlApi(api);
		params+="&sign="+sign;
		params+="&biz_content="+ CodecUtil.encodeURL("{\"num\":\"10\"}");
		/* params=CodecUtil.encodeURL(params);*/
		String url="http://aip.pmph.com/route/rest";

		String urlapi=url+"?"+params;
		String s1 = HttpUtil.doGet(url+"?"+params);
		JSONObject jsonObject = JSON.parseObject(s1);
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

		System.out.println(s1);


	}


	@Test
	public void SyncBookS() throws UnsupportedEncodingException {
		String url="http://localhost:11000/pmpheep//aiptest/syncBook?app_key=VGFeV8wXQ3tWJw7MwYyZCA==?";
		/*String url="";*/

		Map<String,Object> jsonMap=new HashMap<String,Object>();
		jsonMap.put("increment",true);
		jsonMap.put("synchronizationType","add");

		Map<String,Object> bookinfo=new HashMap<String,Object>();
		bookinfo.put("bookname","测试书籍333");

		bookinfo.put("isbn","testIsbn333");
		bookinfo.put("author","testauthor");
		bookinfo.put("publisher","test");
		bookinfo.put("lang","中文");
		bookinfo.put("version","20190226");
		bookinfo.put("publishDate","20190226");
		bookinfo.put("reader","学生");
		bookinfo.put("price","19.99");
		bookinfo.put("buyUrl","http://www.pmphmall.com/gdsdetail/613520-288118");
		bookinfo.put("imageUrl","http://www.pmphmall.com/gdsdetail/613520-288118");
		bookinfo.put("pdfUrl","http://www.pmphmall.com/gdsdetail/613520-288118");
		bookinfo.put("isNew",true);
		bookinfo.put("isPromote",true);
		bookinfo.put("isOnSale",true);
		bookinfo.put("gmtCreate","20190226");
		bookinfo.put("gmtUpdate","20190226");
		bookinfo.put("vn","20190226");
		bookinfo.put("content","这是新书的详情");


		jsonMap.put("bookinfo",bookinfo);

		String jsonStr = JSONUtils.toJSONString(jsonMap);
		String Jsons="{\"increment\":true,\"synchronizationType\":\"obtained\",\"bookInfo\":[{\"isbn\":\"20190327003\",\"isOnSale\":false}]}";

		String s1 = SyncUtils.postJson(url, Jsons);
		System.out.println(s1);


	}

	@Test
	public void ShoppingCart(){

		Map<String,String> api=new HashMap<String,String>();
		api.put("app_key","nmkt8v9NkWbQ9WPFl3l6lFNsyThsfcep");
		api.put("format","json");
		api.put("method","com.ai.ecp.pmph.order.cartAdd");
		api.put("session","MDzjI012CaqX4HG1HbOj35ps1yOYxJ7KfL1ezjKT89OLZZe0f22S6LY6eZ4DleBR");
		api.put("sign_method","md5");
		api.put("timestamp", DateUtil.formatTimeStamp("yyy-MM-dd HH:mm:ss",DateUtil.getCurrentTime()));
		api.put("v","1.0");
		String sign = DigestUtil.digest(api, "hbP5YsbmiWnkOP4IPtXE126JiIaFRCWD4gpfrcULPbs5hytCw06T2SooKfcUnc2g");

		String params = SyncUtils.getUrlApi(api);
		params+="&sign="+sign;

		params+="&biz_content="+ CodecUtil.encodeURL("{\"staff_code\":\"test666\",\"gds_detail\":[{\"bb_code\":\"2015001072\",\"order_amount\":\"2\"}]}");

		String url="http://aip.pmph.com/route/rest";

		String urlapi=url+"?"+params;
		String s1 = HttpUtil.doGet(url+"?"+params);
		JSONObject jsonObject = JSON.parseObject(s1);

		int code=0;
		if(code==0){


		}





	}
	@Test
	public void redeem(){

		Map<String,String> api=new HashMap<String,String>();
		api.put("app_key","nmkt8v9NkWbQ9WPFl3l6lFNsyThsfcep");
		api.put("format","json");
		api.put("method","com.ai.ecp.pmph.staff.scoreCal");
		api.put("session","MDzjI012CaqX4HG1HbOj35ps1yOYxJ7KfL1ezjKT89OLZZe0f22S6LY6eZ4DleBR");
		api.put("sign_method","md5");
		api.put("timestamp", DateUtil.formatTimeStamp("yyy-MM-dd HH:mm:ss",DateUtil.getCurrentTime()));
		api.put("v","1.0");
		String sign = DigestUtil.digest(api, "hbP5YsbmiWnkOP4IPtXE126JiIaFRCWD4gpfrcULPbs5hytCw06T2SooKfcUnc2g");

		String params = SyncUtils.getUrlApi(api);
		params+="&sign="+sign;

		params+="&biz_content="+ CodecUtil.encodeURL("{\"staff_code\":\"test666\",\"score\":\"100\"}");

		String url="http://aip.pmph.com/route/rest";

		String urlapi=url+"?"+params;
		String s1 = HttpUtil.doGet(url+"?"+params);
		JSONObject jsonObject = JSON.parseObject(s1);

		int code=0;
		if(code==0){


		}





	}







	/**
	 * post请求，参数为json字符串
	 * @param url 请求地址
	 * @param jsonString json字符串
	 * @return 响应
	 */
	public static String postJson(String url,String jsonString)
	{
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			post.setEntity(stringEntity);
			response = httpClient.execute(post);
			if(response != null && response.getStatusLine().getStatusCode() == 200)
			{
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
				if(response != null)
				{
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
	public static  String entityToString(HttpEntity entity) throws IOException {
		String result = null;
		if(entity != null)
		{
			long lenth = entity.getContentLength();
			if(lenth != -1 && lenth < 2048)
			{
				result = EntityUtils.toString(entity,"UTF-8");
			}else {
				InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
				CharArrayBuffer buffer = new CharArrayBuffer(2048);
				char[] tmp = new char[1024];
				int l;
				while((l = reader1.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
				result = buffer.toString();
			}
		}
		return result;
	}




	@Test
	public void testAddBook() {
		Book book = this.addBook();
		logger.info("插入的Book对象=" + book.toString());
		Assert.assertNotNull("插入内容后返回的Book不应为空", book.getId());
	}

	@Test
	public void testAddBookDetail() {
		BookDetail bookDetail = this.addBookDetail();
		logger.info("插入的BookDetail对象=" + bookDetail.toString());
		Assert.assertNotNull("插入内容后返回的BookDetail不应为空", bookDetail.getId());
	}

	@Test
	public void testAddBookUserLike() {
		BookUserLike bookUserLike = this.addBookUserLike();
		logger.info("插入的BookUserLike对象=" + bookUserLike.toString());
		Assert.assertNotNull("插入内容后返回的BookUserLike不应为空", bookUserLike.getId());
	}

	@Test
	public void testAddBookUserMark() {
		BookUserMark bookUserMark = this.addBookUserMark();
		logger.info("插入的BookUserMark对象=" + bookUserMark.toString());
		Assert.assertNotNull("插入内容后返回的BookUserMark不应为空", bookUserMark.getId());
	}

	@Test
	public void testUpdateBookById() {
		String returnSring = "ERROR";
		Book book = this.addBook();
		Assert.assertNotNull("插入内容后返回的Book不应为空", book.getId());
		Long[] ids = { book.getId() };
		returnSring = bookService.updateBookById(ids, 1L, true, true, false, null, false,false);
		Assert.assertEquals("是否更新成功", "SUCCESS", returnSring);

	}

	@Test
	public void testDeleteBookById() {
		String returnSring = "ERROR";
		Book book = this.addBook();
		Assert.assertNotNull("插入内容后返回的Book不应为空", book.getId());
		returnSring = bookService.deleteBookById(book.getId());
		Assert.assertEquals("是否删除成功", "SUCCESS", returnSring);
	}

	@Test
	public void testListBookVO() {
		// listBookVO 分页初始化/查询图书详情
		PageParameter<BookVO> pageParameter = new PageParameter<>(1, 1);
		BookVO bookVO = new BookVO();
		bookVO.setName("书名1");
		bookVO.setIsNew(true);
		bookVO.setPath(null);
		bookVO.setIsOnSale(true);
		bookVO.setIsPromote(true);
		pageParameter.setParameter(bookVO);
		PageResult<BookVO> pageResult = bookService.listBookVO(pageParameter);
		Assert.assertTrue("查询结果为空：", pageResult.getRows().isEmpty());

	}

	@Test
	public void testAllSynchronization() {
		String returnSring = "ERROR";
		returnSring = bookService.AllSynchronization(1);
		Assert.assertEquals("书籍同步成功", "SUCCESS", returnSring);
	}

	@Test
	@Rollback(true)
	public void testAbuttingJoint() {
		String returnSring = "ERROR";
		String[] vns = { "2017005621",
			"2017005208",
			"2017005543",
			"2017005896",
			"2017005209",
			"2017005899",
			"2017005901",
			"2017005959",
			"2017005902",
			"2017006046"
		};
		returnSring = bookService.AbuttingJoint(vns, 1,null);
		Assert.assertEquals("书籍同步成功", "SUCCESS", returnSring);
	}



	@Test
	public void testGetBookPreferenceAnalysis() {
		PageParameter<BookPreferenceAnalysisVO> pageParameter = new PageParameter<>(1, 10);
		BookPreferenceAnalysisVO bookPreferenceAnalysisVO = new BookPreferenceAnalysisVO();
		bookPreferenceAnalysisVO.setBookname(null);
		pageParameter.setParameter(bookPreferenceAnalysisVO);
		PageResult<BookPreferenceAnalysisVO> pageResult = bookService.getBookPreferenceAnalysis(pageParameter);
		Assert.assertNotNull(pageResult.getRows());
	}

	private Book addBook() {
		// add 图书添加
		Book book = bookService.add(new Book(null, "生理学", "1234", "1", "作者", "出版社", null, null, 1, 1L, null, null, 99D,
				9D, "http:www.baidu.com", null, "d://ee", "d://aa", 0L, 0L, 0L, 0L, true, 999, null, true, 999, null,
				true, 999, null, 0L, true, null, null, null, null, null,null,null));
		return book;
	}

	private BookDetail addBookDetail() {
		// add 保存图书详情
		BookDetail bookDetail = bookService.add(new BookDetail(1L, "这是一本医学巨著"));
		return bookDetail;
	}

	private BookUserLike addBookUserLike() {
		// add 图书被点赞
		BookUserLike bookUserLike = bookService.add(new BookUserLike(1L, 2L, null));
		return bookUserLike;
	}

	private BookUserMark addBookUserMark() {
		// add 图书被收藏
		BookUserMark bookUserMark = bookService.add(new BookUserMark(2L, 5L, 7L, null));
		return bookUserMark;
	}

}
