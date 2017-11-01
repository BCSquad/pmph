package com.bc.pmpheep.back.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class ContactMallUtil {

	/**
	 * 
	 * 
	 * 功能描述：连接商城
	 *
	 * @param strUrl
	 * @param param
	 * @return
	 *
	 */
	public static String getInfomation(String strUrl, String param) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(strUrl + "?" + param);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
