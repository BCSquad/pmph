package com.bc.pmpheep.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 
 * 功能描述：从erp获取书籍本版号以及判断该本版号是否在erp中
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年11月15日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Service
public class BookInfoWorking extends BaseWorking implements ErpWorking {
	/**
	 * 对应I_BOOKINFO的ERP对接工作
	 */
	@Override
	public void DoWork() {

	}

	/**
	 * 
	 * 
	 * 功能描述：从erp中获取所有书籍本版号
	 *
	 * @return
	 *
	 */
	public String[] listBookInfo() {
		JSONArray bookInfo = list("i_bookinfo");
		String[] vns = new String[bookInfo.size()];
		for (int i = 0; i < bookInfo.size(); i++) {
			JSONObject job = bookInfo.getJSONObject(i);
			vns[i] = job.getString("editionnum");
		}
		return vns;
	}

}
