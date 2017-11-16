package com.bc.pmpheep.back.erp.DBFun;

import java.sql.ResultSet;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 * 
 * 功能描述：连接erp中间表的配置
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年11月16日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
public class DBUtil {

	// 定义链接所需要的变量
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;

	// 定义链接数据库所需要的参数
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";

	// 定义读取配置文件所需要的变量
	private static Properties pp = null;
	private static InputStream fis = null;

	/**
	 * 加载驱动
	 */
	static {
		try {
			// 从dbinfo.properties配置文件中读取配置信息
			pp = new Properties();
			fis = DBUtil.class.getClassLoader().getResourceAsStream("mssql-config.properties");

			pp.load(fis);
			url = pp.getProperty("url");
			username = pp.getProperty("username");
			driver = pp.getProperty("driver");
			password = pp.getProperty("password");

			// 加载驱动
			Class.forName(driver);

		} catch (Exception e) {
			System.out.println("驱动加载失败！");
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			fis = null; // 垃圾回收自动处理
		}

	}

	/**
	 * 得到Connection链接
	 *
	 * @return Connection
	 */
	public static Connection getConnection() {

		try {
			// 建立连接
			con = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			System.out.println("数据库链接失败！");
			e.printStackTrace();
		}

		return con;
	}

	/**
	 * 统一的资源关闭函数
	 *
	 * @param rs
	 * @param ps
	 * @param ct
	 */
	public static void close(ResultSet rs, Statement ps, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}