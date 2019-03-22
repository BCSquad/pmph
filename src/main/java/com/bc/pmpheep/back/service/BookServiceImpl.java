package com.bc.pmpheep.back.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookCorrectionDao;
import com.bc.pmpheep.back.dao.BookDao;
import com.bc.pmpheep.back.dao.BookDetailDao;
import com.bc.pmpheep.back.dao.BookEditorDao;
import com.bc.pmpheep.back.dao.BookUserCommentDao;
import com.bc.pmpheep.back.dao.BookUserLikeDao;
import com.bc.pmpheep.back.dao.BookUserMarkDao;
import com.bc.pmpheep.back.dao.BookVideoDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookDetail;
import com.bc.pmpheep.back.po.BookSupport;
import com.bc.pmpheep.back.po.BookUserLike;
import com.bc.pmpheep.back.po.BookUserMark;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ContactMallUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.MD5;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookPreferenceAnalysisVO;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.erp.service.InfoWorking;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

@Service
public class BookServiceImpl extends BaseService implements BookService {
	private static Properties pmphapiconfigPro = null;
	private static InputStream pmphapiconfigIs = null;

	@Autowired
	BookDao bookDao;
	@Autowired
	BookDetailDao bookDetailDao;
	@Autowired
	BookUserCommentDao bookUserCommentDao;
	@Autowired
	BookUserMarkDao bookUserMarkDao;
	@Autowired
	BookUserLikeDao bookUserLikeDao;
	@Autowired
	BookVideoDao bookVideoDao;
	@Autowired
	BookCorrectionDao bookCorrectionDao;
	@Autowired
	BookEditorDao bookEditorDao;

	@Override
	public PageResult<BookVO> listBookVO(PageParameter<BookVO> pageParameter) throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		String path = pageParameter.getParameter().getPath();
		Long type = pageParameter.getParameter().getType();
		if (StringUtil.notEmpty(path) && ObjectUtil.notNull(type)) {
			pageParameter.getParameter().setPath(path + "-" + String.valueOf(type));
		}
		int total = bookDao.getBookVOTotal(pageParameter);
		PageResult<BookVO> pageResult = new PageResult<>();
		if (total > 0) {
			PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
			pageResult.setRows(bookDao.listBookVO(pageParameter));
		}
		pageResult.setTotal(total);
		return pageResult;
	}
	@Override
	public PageResult<BookVO> recommendlist(PageParameter<BookVO> pageParameter) throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(ObjectUtil.isNull(pageParameter.getParameter().getId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}

		int total = bookDao.recommendTotal(pageParameter);
		PageResult<BookVO> pageResult = new PageResult<>();
		if (total > 0) {
			PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
			pageResult.setRows(bookDao.recommendlist(pageParameter));
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public Boolean recommendcheck(Long currentBookId,int selectType,Long recommendBookId,
								  Boolean ischeckteachbook,Boolean ischeckxgcommend,
								  Boolean ischeckrwcommend) throws CheckedServiceException {

		if (ObjectUtil.isNull(currentBookId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.isNull(selectType)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.isNull(recommendBookId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.isNull(ischeckteachbook)&&ObjectUtil.isNull(ischeckxgcommend)&&ObjectUtil.isNull(ischeckrwcommend)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		Boolean returnZt = false;
		switch (selectType) {
			case 1:
				returnZt = ischeckteachbook; break;

			case 2:
				returnZt = ischeckxgcommend; break;
			case 3:
				returnZt = ischeckrwcommend; break;
		}
		if(returnZt){
			//数据库中是否存在关系 如果存在 就不用插入，如果不存在就插入
			Boolean isExisting = bookDao.recommendisExist(currentBookId, selectType, recommendBookId);
			if(!isExisting){
				bookDao.insertrecommend(currentBookId, selectType, recommendBookId);
			}
		}else{
			//取消 书籍之间的关系 删除 关系
			Boolean isExisting = bookDao.recommendisExist(currentBookId, selectType, recommendBookId);
			if(isExisting){
				bookDao.deleterecommend(currentBookId, selectType, recommendBookId);
			}
		}


		return returnZt;

	}
	@Override
	public String updateBookById(Long[] ids, Long type, Boolean isOnSale, Boolean isNew, Boolean isPromote,
			Long materialId, Boolean isKey,Boolean isStick) throws CheckedServiceException {
		if (ArrayUtil.isEmpty(ids)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "id为空");
		}
		if (ObjectUtil.isNull(type)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍类型为空");
		}
		if (ObjectUtil.isNull(isOnSale)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否上架为空");
		}
		if (ObjectUtil.isNull(isNew)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否新书为空");
		}
		if (ObjectUtil.isNull(isPromote)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否推荐为空");
		}
		if (ObjectUtil.isNull(isKey)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否重点学科为空");
		}
		String result = "FAIL";
		for (Long id : ids) {
			Book ob = bookDao.getBookById(id);
			Book book = new Book();
			book.setId(id);
			book.setType(type);
			book.setIsNew(isNew!=ob.getIsNew()?isNew:null); //新书 重点 等 只有修改时才改变排序 传入与数据库里一致时 应不传入update
			book.setIsKey(isKey!=ob.getIsKey()?isKey:null);
			book.setIsStick(isStick!=ob.getIsStick()?isStick:null);
			book.setIsOnSale(isOnSale);
			book.setIsPromote(isPromote!=ob.getIsPromote()?isPromote:null);
			book.setMaterialId(materialId);
			bookDao.updateBook(book);
		}
		result = "SUCCESS";
		return result;
	}

	@Override
	public Book add(Book book) {
		if (ObjectUtil.isNull(book)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"图书对象为空");
		}
		bookDao.addBook(book);
		return book;
	}

	@Override
	public BookDetail add(BookDetail detail) {
		if (ObjectUtil.isNull(detail)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"图书详情对象为空");
		}
		bookDetailDao.addBookDetail(detail);
		return detail;
	}

	@Override
	public BookUserLike add(BookUserLike like) {
		if (ObjectUtil.isNull(like)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"图书点赞对象为空");
		}
		bookUserLikeDao.addBookUserLike(like);
		return like;
	}

	@Override
	public BookUserMark add(BookUserMark mark) {
		if (ObjectUtil.isNull(mark)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"图书收藏对象为空");
		}
		bookUserMarkDao.addBookUserMark(mark);
		return mark;
	}

	@Override
	public String AbuttingJoint(String[] vns, Integer type) throws CheckedServiceException {
		String result = "SUCCESS";
		Const.AllSYNCHRONIZATION = 1;
		int num = vns.length / 100;
		for (int i = 0; i < vns.length; i++) {
			Book oldBook = bookDao.getBookByBookVn(vns[i]);
			JSONObject ot = new JSONObject();
			if (type == 0) {// 商城发送修改的请求
				if (ObjectUtil.isNull(oldBook)) {
					continue;
				}
			}
			try {
				// System.out.println("第"+(i+1)+"条数据，本版号为"+vns[i]+" 共"+vns.length+"条");
				ot = PostBusyAPI(vns[i]);
				if (null != ot && "1".equals(ot.getJSONObject("RESP").getString("CODE"))) {
					JSONArray array = ot.getJSONObject("RESP").getJSONObject("responseData").getJSONArray("results");
					if (array.size() > 0) {
						Book book = BusyResJSONToModel(array.getJSONObject(0), null);
						String content = book.getContent();// 获取到图书详情将其存入到图书详情表中
						if (ObjectUtil.isNull(oldBook)) {
							book.setScore(9.0);
							book.setType(1L);
							bookDao.addBook(book);
							//Q93
							book.setIsNew(true);
							bookDao.updateBook(book);
							BookDetail bookDetail = new BookDetail(book.getId(), content);
							bookDetailDao.addBookDetail(bookDetail);
						} else {
							Book newBook = new Book(book.getBookname(), book.getIsbn(), book.getSn(), book.getAuthor(),
									book.getPublisher(), book.getLang(), book.getRevision(), book.getType(),
									book.getPublishDate(), book.getReader(), book.getPrice(), book.getScore(),
									book.getBuyUrl(), book.getImageUrl(), book.getPdfUrl(), book.getClicks(),
									book.getComments(), book.getLikes(), book.getBookmarks(), book.getIsStick(),
									book.getSort(), book.getDeadlineStick(), book.getIsNew(), book.getSortNew(),
									book.getDeadlineNew(), book.getIsPromote(), book.getSortPromote(),
									book.getDeadlinePromote(), book.getIsKey(), book.getSortKey(), book.getSales(),
									book.getIsOnSale(), book.getGmtCreate(), book.getGmtUpdate());
							newBook.setId(oldBook.getId());
							bookDao.updateBook(newBook);
							BookDetail bookDetail = new BookDetail(oldBook.getId(), content);
							bookDetailDao.updateBookDetailByBookId(bookDetail);
						}
					}
					if ((i + 1) >= num * (Const.AllSYNCHRONIZATION + 1)) {
						Const.AllSYNCHRONIZATION++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = "FAIL";
			}
		}

		return result;
	}

	@Override
	public String AbuttingJoint(String[] vns, Integer type,String materialName) throws CheckedServiceException {
		String result = "SUCCESS";
		Const.AllSYNCHRONIZATION = 1;
		int num = vns.length / 100;
		for (int i = 0; i < vns.length; i++) {
			Book oldBook = bookDao.getBookByBookVn(vns[i]);
			JSONObject ot = new JSONObject();
			if (type == 0) {// 商城发送修改的请求
				if (ObjectUtil.isNull(oldBook)) {
					continue;
				}
			}
			try {
				// System.out.println("第"+(i+1)+"条数据，本版号为"+vns[i]+" 共"+vns.length+"条");
				ot = PostBusyAPI(vns[i]);
				if (null != ot && "1".equals(ot.getJSONObject("RESP").getString("CODE"))) {
					JSONArray array = ot.getJSONObject("RESP").getJSONObject("responseData").getJSONArray("results");
					if (array.size() > 0) {
						Book book = BusyResJSONToModel(array.getJSONObject(0), null);
						String content = book.getContent();// 获取到图书详情将其存入到图书详情表中
						if (ObjectUtil.isNull(oldBook)) {
							book.setScore(10.0);
							book.setType(1L);
							bookDao.addBook(book);





							BookDetail bookDetail = new BookDetail(book.getId(), content);
							bookDetailDao.addBookDetail(bookDetail);
						} else {
							Book newBook = new Book(book.getBookname(), book.getIsbn(), book.getSn(), book.getAuthor(),
									book.getPublisher(), book.getLang(), book.getRevision(), book.getType(),
									book.getPublishDate(), book.getReader(), book.getPrice(), book.getScore(),
									book.getBuyUrl(), book.getImageUrl(), book.getPdfUrl(), book.getClicks(),
									book.getComments(), book.getLikes(), book.getBookmarks(), book.getIsStick(),
									book.getSort(), book.getDeadlineStick(), book.getIsNew(), book.getSortNew(),
									book.getDeadlineNew(), book.getIsPromote(), book.getSortPromote(),
									book.getDeadlinePromote(), book.getIsKey(), book.getSortKey(), book.getSales(),
									book.getIsOnSale(), book.getGmtCreate(), book.getGmtUpdate());
							newBook.setId(oldBook.getId());
							bookDao.updateBook(newBook);
							BookDetail bookDetail = new BookDetail(oldBook.getId(), content);
							bookDetailDao.updateBookDetailByBookId(bookDetail);
						}
					}
					if ((i + 1) >= num * (Const.AllSYNCHRONIZATION + 1)) {
						Const.AllSYNCHRONIZATION++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = "FAIL";
			}
		}

		return result;
	}

	/**
	 * 
	 * 
	 * 功能描述: 获取数据
	 *
	 * @param config
	 * @return
	 * @throws IOException
	 *
	 */
	private JSONObject PostBusyAPI(String vn, String... config) throws IOException {
		String uri = "", app_key = "", app_secret = "", session_key = "";
		if (config != null) {
			if (pmphapiconfigPro == null || pmphapiconfigIs == null) {
				pmphapiconfigPro = new Properties();
				pmphapiconfigIs = BookServiceImpl.class.getClassLoader()
						.getResourceAsStream("pmphapi-config.properties");
				pmphapiconfigPro.load(pmphapiconfigIs);
			}
			uri = pmphapiconfigPro.getProperty("uri");
			app_key = pmphapiconfigPro.getProperty("app_key");
			app_secret = pmphapiconfigPro.getProperty("app_secret");
			session_key = pmphapiconfigPro.getProperty("session_key");
		} else {
			uri = config[0];
			app_key = config[1];
			app_secret = config[2];
			session_key = config[3];
		}
		String sendTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		StringBuffer sbSgin = new StringBuffer();
		sbSgin.append(app_secret);
		sbSgin.append("app_key" + app_key + "&");
		sbSgin.append("formatjson&");
		sbSgin.append("methodcom.ai.ecp.pmph.gdsdetail.services&");
		sbSgin.append("session" + session_key + "&");
		sbSgin.append("sign_methodmd5&");
		sbSgin.append("timestamp" + sendTime + "&");
		sbSgin.append("v1.0&");
		sbSgin.append("versionNumber" + vn);
		sbSgin.append(app_secret);
		String strSign = MD5.md5(sbSgin.toString()).toLowerCase();

		StringBuffer sbPar = new StringBuffer();
		sbPar.append("sign=" + strSign + "&");
		sbPar.append("timestamp=" + URLEncoder.encode(sendTime, "UTF-8") + "&");
		sbPar.append("sign_method=md5&");
		sbPar.append("session=" + session_key + "&");
		sbPar.append("method=com.ai.ecp.pmph.gdsdetail.services&");
		sbPar.append("format=json&");
		sbPar.append("app_key=" + app_key + "&");
		sbPar.append("v=1.0&");
		sbPar.append("versionNumber=" + URLEncoder.encode(vn, "UTF-8"));
		String strRes = ContactMallUtil.getInfomation(uri, sbPar.toString());
		System.out.println(strRes);
		return JSONObject.fromObject(strRes);
	}

	/**
	 * 
	 * 
	 * 功能描述：将获取的信息装到Book里面去
	 *
	 * @param item
	 * @param model
	 * @return
	 *
	 */
	public Book BusyResJSONToModel(JSONObject item, Book model) {
		if (model == null) {
			model = bookDao.getBookByBookVn(item.getString("versionNumber"));
			if (model == null) {
				model = new Book();
				model.setIsPromote(true);
				model.setIsOnSale(true);
				model.setIsNew(true);
				model.setIsKey(false);
				model.setSales(0L);
				model.setGmtCreate(DateUtil.getCurrentTime());
			}
			Long sort = bookDao.getMaxSort();
			model = model == null ? new Book() : model;

			model.setIsNew(true);
			model.setSortNew(sort.intValue()+1);
		}

		String revison = item.getString("edition");
		if (null == revison || "".equals(revison)) {
			revison = "0";
		}
		String isbn = item.getString("isbn");
		if (StringUtil.isEmpty(isbn) || "".equals(isbn)) {
			isbn = "-";
		}
		if (CollectionUtil.isEmpty(item.getJSONArray("gdsDescList"))) {// 内容简介
			model.setContent("暂缺"); // 内容简介
		} else {
			model.setContent(item.getJSONArray("gdsDescList").getJSONObject(0).getString("gdsDescContent"));
		}
		model.setRevision(Integer.parseInt(revison)); // 版次 ,印次
		model.setBookname(item.getString("gdsName")); // 书名
		model.setAuthor(item.getString("author")); // 作者
		model.setReader(item.getString("reader")); // 读者对象
		model.setPublishDate(DateUtil.str3Date(item.getString("publicDate"))); // 出版时间
		model.setPublisher(item.getString("publicCompany")); // 出版社
		model.setLang(item.getString("language")); // 语言
		model.setImageUrl(item.getJSONArray("imageList").size() > 0
				? item.getJSONArray("imageList").getJSONObject(0).getString("imgUrl")
				: ""); // 图片地址
		model.setPdfUrl(item.getString("pdfFile"));
		model.setBuyUrl(item.getString("webGdsDetailUrl"));
		model.setVn(item.getString("versionNumber"));
		model.setIsbn(isbn);
 		return model;
	}

	@Override
	public String deleteBookById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍id为空");
		}
		bookDao.deleteBookById(id);
		bookDetailDao.deleteBookDetailByBookId(id);
		bookUserCommentDao.deleteBookUserCommentByBookId(id);
		bookUserLikeDao.deleteBookUserLikeByBookId(id);
		bookUserMarkDao.deleteBookUserMarkByBookId(id);
		return "SUCCESS";
	}

	@Override
	public String AllSynchronization(Integer type) throws CheckedServiceException {
		if (ObjectUtil.isNull(type)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"同步中产生了错误，请重新同步");
		}
		Const.AllSYNCHRONIZATION = 100;
		String[] vns = new InfoWorking().listBookInfo();
		vns = ArrayUtil.array_unique(vns);
		if (1 == type) {
			AbuttingJoint(vns, type);
		} else {
			List<String> vn = ArrayUtil.toList(vns);
			List<String> bookVns = bookDao.getBookByVn();
			vn.remove(bookVns);
			String[] newBookVn = new String[vn.size()];
			for (int i = 0; i < vn.size(); i++) {
				newBookVn[i] = vn.get(i);
			}
			AbuttingJoint(newBookVn, type);
		}
		Const.AllSYNCHRONIZATION = 0;
		return "SUCCESS";
	}

	@Override
	public PageResult<BookPreferenceAnalysisVO> getBookPreferenceAnalysis(
			PageParameter<BookPreferenceAnalysisVO> pageParameter) throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"查询出现问题，请重新加载");
		}
		String path = pageParameter.getParameter().getPath();
		Long type = pageParameter.getParameter().getType();
		if (StringUtil.notEmpty(path) && ObjectUtil.notNull(type)) {
			path = path + "-" + String.valueOf(type);
		}
		PageResult<BookPreferenceAnalysisVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = bookDao.getBookPreferenceAnalysisTotal(pageParameter.getParameter().getBookname(),
				pageParameter.getParameter().getType(), pageParameter.getParameter().getPath());
		if (total > 0) {
			pageResult.setRows(bookDao.getBookPreferenceAnalysis(pageParameter.getParameter().getBookname(),
					pageParameter.getStart(), pageParameter.getPageSize(), pageParameter.getParameter().getType(),
					pageParameter.getParameter().getPath()));
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public PageResult<Book> listBook(Integer pageSize, Integer pageNumber, String bookName)
			throws CheckedServiceException {
		Map<String, Object> map = new HashMap<String, Object>(3);
		if (!StringUtil.isEmpty(bookName)) {
			bookName = StringUtil.toAllCheck(bookName.trim());
			map.put("bookName", bookName);
		}
		map.put("start", ((pageNumber - 1) * pageSize));
		map.put("pageSize", pageSize);
		PageResult<Book> pageResult = new PageResult<Book>();
		pageResult.setPageNumber(pageNumber);
		pageResult.setPageSize(pageSize);
		List<Book> rows = new ArrayList<Book>(1);
		Integer total = bookDao.getListToatl(map);
		if (null != total && total.intValue() > 0) {
			rows = bookDao.geList(map);
		}
		pageResult.setTotal(total);
		pageResult.setRows(rows);
		return pageResult;
	}

	@Override
	public Integer updateBookCore(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return bookDao.updateBookCore(id);
	}

	@Override
	public void updateUpComments(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		bookDao.updateUpComments(id);
	}

	@Override
	public void updateDownComments(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		bookDao.updateDownComments(id);
	}

	@Override
	public String bookExcel(MultipartFile file) throws CheckedServiceException {
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		Workbook workbook = null;
		InputStream in = null;
		try {
			in = file.getInputStream();
		} catch (FileNotFoundException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.NULL_PARAM,
					"未获取到文件");
		} catch (IOException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.ILLEGAL_PARAM,
					"读取文件失败");
		}
		try {
			if ((".xls").equals(fileType)) {
				workbook = new HSSFWorkbook(in);
			} else if ((".xlsx").equals(fileType)) {
				workbook = new XSSFWorkbook(in);
			} else {
				throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.ILLEGAL_PARAM,
						"读取的不是Excel文件");
			}
		} catch (IOException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.ILLEGAL_PARAM,
					"读取文件失败");
		} catch (OfficeXmlFileException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.ILLEGAL_PARAM,
					"此文档不是对应的.xls或.xlsx的Excel文档，请修改为正确的后缀名再进行上传");
		}
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (ObjectUtil.isNull(sheet)) {
				continue;
			}

			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				Long id = 0L;
				List<Long> bookIds = new ArrayList<>();
				if (null == row) {
					continue;
				}
				int lastCell = row.getLastCellNum();
				for (int cellNum = 8; cellNum <= lastCell; cellNum++) {
					Cell cell = row.getCell(cellNum);
					if (ObjectUtil.notNull(cell) && !"".equals(cell.toString())) {
						throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
								CheckedExceptionResult.ILLEGAL_PARAM, "提交的Excel格式不正确，请按照模版修改后重试");
					}
				}

				for (int i = 0; i < 7; i += 2) {
					Cell name = row.getCell(i);
					Cell isbn = row.getCell(i + 1);
					Book book = new Book();
					if (!ObjectUtil.isNull(isbn) && !"".equals(isbn.toString())) {
						String ISBN = isbn.toString();
						if (!ISBN.contains("ISBN")) {
							ISBN = "ISBN " + ISBN;
						}
						book = bookDao.getBookByIsbn(ISBN);
						if (ObjectUtil.isNull(book)) {
							throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
									CheckedExceptionResult.ILLEGAL_PARAM,
									"Excel中第" + (rowNum + 1) + "行第" + (i + 1) + "列isbn填写错误请确认后重试。");
						}
						if (0 == i) {
							id = book.getId();
						} else {
							bookIds.add(book.getId());
						}
					} else if (!ObjectUtil.isNull(name) && !"".equals(name.toString())) {
						try {
							book = bookDao.getBookByBookname(name.toString());
						} catch (TooManyResultsException e) {
							throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
									CheckedExceptionResult.ILLEGAL_PARAM, "Excel中第" + (rowNum + 1) + "行第" + (i + 1)
											+ "列" + name.toString() + "书籍存在多本同名书籍，填写isbn后重试。");
						}
						if (ObjectUtil.isNull(book)) {
							throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
									CheckedExceptionResult.ILLEGAL_PARAM, "Excel中第" + (rowNum + 1) + "行第" + (i + 1)
											+ "列" + name.toString() + "书籍填写错误，请确认后或者填写isbn后重试。");
						}
						if (0 == i) {
							id = book.getId();
						} else {
							bookIds.add(book.getId());
						}
					}
				}
				addBookExcel(id, bookIds, rowNum);
			}
		}
		return "SECCESS";
	}

	public void addBookExcel(Long id, List<Long> bookIds, int rowNum) {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.NULL_PARAM,
					"参数为空");
		}
		if (bookIds.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.NULL_PARAM,
					"Excel中第" + rowNum + "行没有填写配套图书，请确认后重试。");
		}
		for (Long bookId : bookIds) {
			BookSupport bookSupport = bookDao.getBookSupport(id, bookId);
			if (ObjectUtil.isNull(bookSupport)) {
				bookDao.addBookSupport(id, bookId);
			}
		}
	}

	@Override
	public List<Book> queryTscxReadList(PageParameter<Map<String, Object>> pageParameter) {
		// TODO Auto-generated method stub
		return this.bookDao.queryTscxReadList(pageParameter);
	}

	@Override
	public int queryTscxReadListCount(PageParameter<Map<String, Object>> pageParameter) {
		// TODO Auto-generated method stub
		return this.bookDao.queryTscxReadListCount(pageParameter);
	}

	@Override
	public String updataSellwell(List<Book> books) throws CheckedServiceException {
		bookDao.updateBookSellWell(books);

		return null;
	}
	@Override
	public List<Book> querySellwelList(PageParameter<Map<String, Object>> pageParameter){
		return bookDao.querySellwelList(pageParameter);
	}
	public int updateBookSellWellByid(Map<String,Object> params){
		return bookDao.updateBookSellWellByid(params);
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		return bookDao.getBookByIsbn(isbn);
	}

	@Override
	public Book getBookById(Long id) {
		return bookDao.getBookById(id);
	}

	@Override
	public BookDetail getBookDetailByBookId(Long id) {
		return bookDetailDao.getBookDetailById(id);
	}

	@Override
	public BookDetail addBookDetail(BookDetail bookDetail) {

		bookDetailDao.addBookDetail(bookDetail);
		return bookDetail;
	}

	@Override
	public Integer updateBook(Book book) throws CheckedServiceException {
		if (null == book.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "id为空");
		}
		return bookDao.updateBook(book);
	}
	@Override
	public Integer updateBookDetail(BookDetail detail) throws CheckedServiceException {
		if (null == detail.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "id为空");
		}
		return bookDetailDao.updateBookDetail(detail);
	}

}
