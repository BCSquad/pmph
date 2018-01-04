package com.bc.pmpheep.back.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;

@Controller
@RequestMapping(value = "/properties")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertiesImplController {
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "外部接口";
	// 定义读取配置文件所需要的变量
	private static Properties pp = null;
	private static InputStream fis = null;
	private static OutputStream os = null;

	/**
	 * 
	 * 
	 * 功能描述：获取商城的接口信息
	 *
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取商城接口信息")
	@RequestMapping(value = "/mall", method = RequestMethod.GET)
	public ResponseBean mall() {
		ResponseBean<Map> responseBean = new ResponseBean<>();
		Map<String, String> map = new HashMap<>();
		pp = new Properties();
		fis = PropertiesImplController.class.getClassLoader().getResourceAsStream("pmphapi-config.properties");
		responseBean.setCode(1);
		try {
			pp.load(fis);
			map.put("uri", pp.getProperty("uri"));
			map.put("appKey", pp.getProperty("app_key"));
			map.put("appSecret", pp.getProperty("app_secret"));
			map.put("sessionKey", pp.getProperty("session_key"));
		} catch (IOException e) {
			responseBean.setCode(0);
			responseBean.setMsg(e.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				responseBean.setCode(0);
				responseBean.setMsg(e.getMessage());
			}
		}
		responseBean.setData(map);
		return responseBean;
	}

	/**
	 * 
	 * 
	 * 功能描述：获取erp接口详情
	 *
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取erp接口信息")
	@RequestMapping(value = "/erp", method = RequestMethod.GET)
	public ResponseBean erp() {
		ResponseBean<Map> responseBean = new ResponseBean<>();
		Map<String, String> map = new HashMap<>();
		pp = new Properties();
		fis = PropertiesImplController.class.getClassLoader().getResourceAsStream("mssql-config.properties");
		responseBean.setCode(1);
		try {
			pp.load(fis);
			map.put("uri", pp.getProperty("url").replace("jdbc:sqlserver:", ""));
			map.put("username", pp.getProperty("username"));
			map.put("driver", pp.getProperty("driver"));
			map.put("password", pp.getProperty("password"));
		} catch (IOException e) {
			responseBean.setCode(0);
			responseBean.setMsg(e.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				responseBean.setCode(0);
				responseBean.setMsg(e.getMessage());
			}
		}
		responseBean.setData(map);
		return responseBean;
	}

	/**
	 * 
	 * 
	 * 功能描述：修改商城接口信息
	 *
	 * @param uri
	 *            商城接口路径
	 * @param appKey
	 *            AIP分配给用户的sessionKey
	 * @param appSecret
	 *            加密代码
	 * @param sessionKey
	 *            AIP分配给用户的SessionKey(或 Access Token）.
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改商城接口信息")
	@RequestMapping(value = "/updateMall", method = RequestMethod.PUT)
	public ResponseBean updateMall(String uri, String appKey, String appSecret, String sessionKey) {
		ResponseBean responseBean = new ResponseBean<>();
		pp = new Properties();
		fis = PropertiesImplController.class.getClassLoader().getResourceAsStream("pmphapi-config.properties");
		try {
			pp.load(fis);
			if (!StringUtil.isEmpty(uri)) {
				pp.setProperty("uri", uri);
			}
			if (!StringUtil.isEmpty(appKey)) {
				pp.setProperty("app_key", appKey);
			}
			if (!StringUtil.isEmpty(appSecret)) {
				pp.setProperty("app_secret", appSecret);
			}
			if (!StringUtil.isEmpty(sessionKey)) {
				pp.setProperty("session_key", sessionKey);
			}
			URL url = PropertiesImplController.class.getClassLoader().getResource("pmphapi-config.properties");
			os = new FileOutputStream(new File(url.toURI()));
			pp.store(os, "the primary key of article table");
		} catch (URISyntaxException | IOException e) {
			responseBean.setCode(0);
			responseBean.setMsg(e.getMessage());
		} finally {
			try {
				fis.close();
				os.flush();
				os.close();
			} catch (IOException e) {
				responseBean.setCode(0);
				responseBean.setMsg(e.getMessage());
			}
		}
		return responseBean;
	}

	/**
	 * 
	 * 
	 * 功能描述：修改erp接口信息
	 *
	 * @param uri
	 * @param username
	 * @param driver
	 * @param password
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改erp接口信息")
	@RequestMapping(value = "/updateErp", method = RequestMethod.PUT)
	public ResponseBean updateErp(String uri, String username, String driver, String password) {
		ResponseBean responseBean = new ResponseBean<>();
		pp = new Properties();
		fis = PropertiesImplController.class.getClassLoader().getResourceAsStream("mssql-config.properties");
		try {
			pp.load(fis);
			if (!StringUtil.isEmpty(uri)) {
				String url = "jdbc:sqlserver:" + uri;
				pp.setProperty("url", url);
			}
			if (!StringUtil.isEmpty(username)) {
				pp.setProperty("username", username);
			}
			if (!StringUtil.isEmpty(driver)) {
				pp.setProperty("driver", driver);
			}
			if (!StringUtil.isEmpty(password)) {
				pp.setProperty("password", password);
			}
			URL url = PropertiesImplController.class.getClassLoader().getResource("mssql-config.properties");
			os = new FileOutputStream(new File(url.toURI()));
			pp.store(os, "the primary key of article table");
		} catch (URISyntaxException | IOException e) {
			responseBean.setCode(0);
			responseBean.setMsg(e.getMessage());
		} finally {
			try {
				fis.close();
				os.flush();
				os.close();
			} catch (IOException e) {
				responseBean.setCode(0);
				responseBean.setMsg(e.getMessage());
			}
		}
		return responseBean;
	}
}
