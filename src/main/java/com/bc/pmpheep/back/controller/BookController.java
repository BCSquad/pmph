package com.bc.pmpheep.back.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.util.HttpUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.MaterialTypeService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/books")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BookController {

	@Autowired
	BookService bookService;
	@Autowired
	MaterialTypeService materialTypeService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "图书管理";

	/**
	 *
	 *
	 * 功能描述：初始化/条件查询书籍信息
	 *
	 * @param pageSize
	 *            当页条数
	 * @param pageNumber
	 *            当前页数
	 * @param type
	 *            书籍类别
	 * @param name
	 *            书籍名称/ISBN
	 * @param isOnSale
	 *            是否上架
	 * @param isNew
	 *            是否新书
	 * @param isPromote
	 *            是否推荐
	 * @param path
	 *            书籍类别根路径
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "初始化/条件查询书籍信息")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(Integer pageSize, Integer pageNumber, Long type, String name, Boolean isOnSale,
			Boolean isNew, Boolean isPromote, String path) {
		PageParameter<BookVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		BookVO bookVO = new BookVO();
		bookVO.setName(name);
		bookVO.setIsNew(isNew);
		bookVO.setPath(path);
		bookVO.setType(type);
		bookVO.setIsOnSale(isOnSale);
		bookVO.setIsPromote(isPromote);
		pageParameter.setParameter(bookVO);
		return new ResponseBean(bookService.listBookVO(pageParameter));
	}

	/**
	 * 功能描述: 初始化/条件查询推荐书籍信息
	 * @param recommendPageSize
	 * @param recommendPageNumber
	 * @param currentBookId
	 * @param ischeckteachbook
	 * @param ischeckxgcommend
	 * @param ischeckrwcommend
	 * @param name
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "初始化/条件查询推荐书籍信息")
	@RequestMapping(value = "/recommendlist", method = RequestMethod.GET)
	public ResponseBean recommendlist(Integer recommendPageSize, Integer recommendPageNumber, Long currentBookId,Boolean ischeckteachbook,Boolean ischeckxgcommend,Boolean ischeckrwcommend,String name) {
		PageParameter<BookVO> pageParameter = new PageParameter<>(recommendPageNumber, recommendPageSize);
		BookVO bookVO = new BookVO();
		bookVO.setName(name);
		bookVO.setId(currentBookId);
		bookVO.setIscheckteachbook(ischeckteachbook);
		bookVO.setIscheckrwcommend(ischeckrwcommend);
		bookVO.setIscheckxgcommend(ischeckxgcommend);
		pageParameter.setParameter(bookVO);
		return new ResponseBean(bookService.recommendlist(pageParameter));
	}
	/**
	 * 功能描述: 推荐书籍
	 * @param currentBookId
	 * @param ischeckteachbook
	 * @param ischeckxgcommend
	 * @param ischeckrwcommend
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "推荐书籍")
	@RequestMapping(value = "/recommendcheck", method = RequestMethod.GET)
	public ResponseBean recommendcheck(Long currentBookId,int selectType,Long recommendBookId,Boolean ischeckteachbook,Boolean ischeckxgcommend,Boolean ischeckrwcommend) {


		return new ResponseBean(bookService.recommendcheck( currentBookId, selectType, recommendBookId, ischeckteachbook, ischeckxgcommend, ischeckrwcommend));
	}

	/**
	 *
	 *
	 * 功能描述：修改单个/多个书籍详情
	 *
	 * @param ids
	 *            书籍id
	 * @param type
	 *            书籍类型id
	 * @param isOnSale
	 *            是否上架
	 * @param isNew
	 *            是否新书
	 * @param isPromote
	 *            是否推荐
	 * @return 是否成功
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改单个/多个书籍详情")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseBean update(Long[] ids, Long type, Boolean isOnSale, Boolean isNew, Boolean isPromote,
			Long materialId,Boolean isKey,Boolean isStick) {
		return new ResponseBean(bookService.updateBookById(ids, type, isOnSale, isNew, isPromote, materialId,isKey,isStick));
	}

	/**
	 *
	 *
	 * 功能描述：获取所有书籍类别
	 *
	 * @param parentId
	 *            父级id
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取所有书籍类别")
	@RequestMapping(value = "/list/materialType", method = RequestMethod.GET)
	public ResponseBean materialType(Long parentId) {
		return new ResponseBean(materialTypeService.listMaterialType(parentId));
	}

	/**
	 *
	 *
	 * 功能描述：根据书籍id删除书籍
	 *
	 * @param id
	 *            书籍id
	 * @return 是否成功
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除书籍")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseBean delete(Long id) {
		return new ResponseBean(bookService.deleteBookById(id));
	}

	/**
	 *
	 *
	 * 功能描述：商城更新图书的接口
	 *
	 * @param noteicetype
	 *            通知类型 0：修改
	 * @param key
	 *            本版号
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "商城更新图书")
	@RequestMapping(value = "/abuttingjoint", method = RequestMethod.GET)
	public ResponseBean abuttingjoint(Integer noteicetype, String[] key) {
		return new ResponseBean(bookService.AbuttingJoint(key, noteicetype));
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "图书配套导入")
	@RequestMapping(value = "/bookExcel", method = RequestMethod.POST)
	public ResponseBean bookExcel(@RequestParam("file") MultipartFile file) {
		return new ResponseBean<>(bookService.bookExcel(file));
	}

	/**
	 *
	 *
	 * 功能描述：图书同步 1：全量同步 2：增量同步
	 *
	 * @param type
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "图书同步")
	@RequestMapping(value = "/allsynchronization", method = RequestMethod.GET)
	public synchronized ResponseBean allsynchronization(Integer type) {
		return new ResponseBean(bookService.AllSynchronization(type));
	}

	/**
	 *
	 *
	 * 功能描述：获取图书是否同步完成
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取图书是否同步完成")
	@RequestMapping(value = "/isEnd", method = RequestMethod.GET)
	public ResponseBean isEnd() {
		return new ResponseBean(Const.AllSYNCHRONIZATION);
	}


	//查询某类下的图书畅销
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据获取图书销量查询列表")
	@RequestMapping(value = "/getBookListByManage", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean searchTscxBook(Integer pageSize, Integer pageNumber, String name,Integer type,Integer flag) {
		PageParameter<Map<String, Object>> pageParameter = new PageParameter<>(pageNumber, pageSize);
		Map<String, Object> map = new HashMap<>();
		//图书类型
		map.put("type", type);
		map.put("name",name);
		map.put("flag",flag);
		pageParameter.setParameter(map);
		List<Book> maps = bookService.queryTscxReadList(pageParameter);
		PageResult<Book> pageResult = new PageResult<>();
		pageResult.setRows(maps);
		pageResult.setTotal(bookService.queryTscxReadListCount(pageParameter));
		return new ResponseBean(pageResult);
	}


	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "添加图书畅销榜排序")
	@RequestMapping(value = "/updataBookByManage", method = RequestMethod.POST)
	public ResponseBean updataBookBymanage(@RequestBody String books,Integer type) throws Exception {
		if(type==null){
			type=1;
		}

		String decode = URLDecoder.decode(books, "utf-8");
		Object parse = JSONUtils.parse(decode);

		JSONObject jsonObject = JSON.parseObject(books);
		Object params = jsonObject.get("params");
		Field[] declaredFields = Book.class.getDeclaredFields();
		JSONArray objects = JSONArray.parseArray(params.toString());
		List<Book> bookList=new ArrayList<>();

			for(int i=0;i<objects.size();i++){
				Object o = objects.get(i);
				JSONObject jsonObject1 = JSON.parseObject(o.toString());
				Book bookById = bookService.getBookById(jsonObject1.getLong("id"));
				bookById.setFlag(type);
				switch (type){

					case 1:
						bookById.setIsSellWell(jsonObject1.getBoolean("isSellWell"));
						bookById.setSortSellWell(jsonObject1.getInteger("sortSellWell"));
						break;
					case 2:
						bookById.setIsNewBook(jsonObject1.getBoolean("isNewBook"));
						bookById.setSortNewBook(jsonObject1.getInteger("sortNewBook"));
						break;
					case 3:
						bookById.setIsHighly(jsonObject1.getBoolean("isHighly"));
						bookById.setSortHighly(jsonObject1.getInteger("sortHighly"));
						break;

				}

				bookList.add(bookById);
			}


		return new ResponseBean(bookService.updataSellwell(bookList));
	}


	/**
	 * 根据属性，获取get方法
	 * @param ob 对象
	 * @param name 属性名
	 * @return
	 * @throws Exception
	 */
	public static Object getGetMethod(Object ob , String name)throws Exception{
		Method[] m = ob.getClass().getMethods();
		for(int i = 0;i < m.length;i++){
			if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
				return m[i].invoke(ob);
			}
		}
		return null;
	}

	/**
	 * 根据属性，拿到set方法，并把值set到对象中
	 * @param obj 对象
	 * @param clazz 对象的class
	 * @param fileName 需要设置值得属性
	 * @param typeClass
	 * @param value
	 */
	public static void setValue(Object obj,Class<?> clazz,String filedName,Class<?> typeClass,Object value){
		filedName = removeLine(filedName);
		String methodName = "set" + filedName.substring(0,1).toUpperCase()+filedName.substring(1);
		try{
			Method method =  clazz.getDeclaredMethod(methodName, new Class[]{typeClass});
			method.invoke(obj, new Object[]{getClassTypeValue(typeClass, value)});
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 通过class类型获取获取对应类型的值
	 * @param typeClass class类型
	 * @param value 值
	 * @return Object
	 */
	private static Object getClassTypeValue(Class<?> typeClass, Object value){
		if(typeClass == int.class  || value instanceof Integer){
			if(null == value){
				return 0;
			}
			return value;
		}else if(typeClass == short.class){
			if(null == value){
				return 0;
			}
			return value;
		}else if(typeClass == byte.class){
			if(null == value){
				return 0;
			}
			return value;
		}else if(typeClass == double.class){
			if(null == value){
				return 0;
			}
			return value;
		}else if(typeClass == long.class){
			if(null == value){
				return 0;
			}
			return value;
		}else if(typeClass == String.class){
			if(null == value){
				return "";
			}
			return value;
		}else if(typeClass == boolean.class){
			if(null == value){
				return true;
			}
			return value;
		}else if(typeClass == BigDecimal.class){
			if(null == value){
				return new BigDecimal(0);
			}
			return new BigDecimal(value+"");
		}else {
			return typeClass.cast(value);
		}
	}
	/**
	 * 处理字符串  如：  abc_dex ---> abcDex
	 * @param str
	 * @return
	 */
	public static  String removeLine(String str){
		if(null != str && str.contains("_")){
			int i = str.indexOf("_");
			char ch = str.charAt(i+1);
			char newCh = (ch+"").substring(0, 1).toUpperCase().toCharArray()[0];
			String newStr = str.replace(str.charAt(i+1), newCh);
			String newStr2 = newStr.replace("_", "");
			return newStr2;
		}
		return str;
	}



	//查询某类下的图书畅销
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取图书畅销榜")
	@RequestMapping(value = "/getManageList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean getManageList(Integer pageSize, Integer pageNumber,Integer type,Integer flag) {
		PageParameter<Map<String, Object>> pageParameter = new PageParameter<>(0, 6);
		Map<String, Object> map = new HashMap<>();
		//图书类型
		map.put("type", type);
		map.put("flag", flag);
		pageParameter.setParameter(map);
		List<Book> maps = bookService.querySellwelList(pageParameter);
		PageResult<Book> pageResult = new PageResult<>();
		pageResult.setRows(maps);
		return new ResponseBean(pageResult);
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除图书畅销")
	@RequestMapping(value = "/delSellWellById", method = RequestMethod.GET)
	public ResponseBean delectSellwell(Long id,Integer flag) {
		 HashMap<String, Object> params = new HashMap<>();
		 params.put("id", id);
		 params.put("flag",flag);
		return new ResponseBean(bookService.updateBookSellWellByid(params));
	}

}
