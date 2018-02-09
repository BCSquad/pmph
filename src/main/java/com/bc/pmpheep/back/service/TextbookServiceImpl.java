package com.bc.pmpheep.back.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.bo.DecPositionBO;
import com.bc.pmpheep.back.dao.MaterialDao;
import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.dao.PmphUserDao;
import com.bc.pmpheep.back.dao.TextbookDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookListVO;
import com.bc.pmpheep.back.vo.BookPositionVO;
import com.bc.pmpheep.back.vo.ExcelDecAndTextbookVO;
import com.bc.pmpheep.back.vo.MaterialProjectEditorVO;
import com.bc.pmpheep.back.vo.TextbookDecVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * TextbookService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class TextbookServiceImpl implements TextbookService {
	
	@Autowired
	PmphUserDao pmphUserDao;

	@Autowired
	PmphRoleDao roleDao;
	
    @Autowired
    private TextbookDao textbookDao;
    
    @Autowired
    private MaterialDao materialDao;
    
    @Autowired
    private PmphUserService pmphUserService;
    
    @Autowired
    private MaterialService materialService;
    
    @Autowired
    private MaterialTypeService materialTypeService;
    
    @Autowired
    private MaterialProjectEditorService materialProjectEditorService;
    
	@Autowired
	private PmphRoleService pmphRoleService;
    
	@Autowired
	private DecPositionService decPositionService;
	
	@Autowired
	private DecPositionPublishedService decPositionPublishedService;
	
	/**
	 * 
	 * @param Textbook
	 *            实体对象
	 * @return 带主键的 Textbook
	 * @throws CheckedServiceException
	 */
	@Override
	public Textbook addTextbook(Textbook textbook) throws CheckedServiceException {
		if (ObjectUtil.isNull(textbook.getMaterialId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"教材id不能为空！");
		}
		if (StringUtil.isEmpty(textbook.getTextbookName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍名称为空");
		}
		if (ObjectUtil.isNull(textbook.getTextbookRound())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍轮次为空");
		}
		if (ObjectUtil.isNull(textbook.getSort())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"图书序号为空");
		}
		if (ObjectUtil.isNull(textbook.getFounderId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"创建人id不能为空！");
		}
		textbookDao.addTextbook(textbook);
		return textbook;
	}

	/**
	 * 
	 * @param id
	 * @return Textbook
	 * @throws CheckedServiceException
	 */
	@Override
	public Textbook getTextbookById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return textbookDao.getTextbookById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteTextbookById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return textbookDao.deleteTextbookById(id);
	}

	/**
	 * @param Textbook
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateTextbook(Textbook textbook) throws CheckedServiceException {
		if (null == textbook.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
//		PmphUser pmphUser =pmphUserDao.get(textbook.getPlanningEditor());
//		if(pmphUser.getIsDisabled()){
//			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
//					CheckedExceptionResult.ILLEGAL_PARAM, "该用户未启用时禁止选择");
//		}
//		String roleName="策划编辑";//通过roleName查询roleid
//		List<PmphRole> list=pmphRoleService.getList(roleName);//角色id
//		if (ObjectUtil.isNull(textbook.getPlanningEditor()) || ObjectUtil.isNull(list.get(0).getId())) {
//			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
//					CheckedExceptionResult.NULL_PARAM, "角色ID或策划编辑ID为空时禁止新增");
//		}
//		// 判断该用户是否已有策划编辑的角色 没有则新加
//		List<PmphUserRole> pmphUserRoles=pmphRoleService.getUserRoleList(textbook.getPlanningEditor(), list.get(0).getId());
//		if(ObjectUtil.isNull(pmphUserRoles) && pmphUserRoles.size() == 0){
//			roleDao.addUserRole(textbook.getPlanningEditor(), list.get(0).getId());//给策划编辑绑定权限
//		}
		return textbookDao.updateTextbook(textbook);
	}

	@Override
	public List<Textbook> getTextbookByMaterialId(Long materialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"教材ID为空，禁止查询！");
		}
		return textbookDao.getTextbookByMaterialId(materialId);
	}

	@Override
	public PageResult<BookPositionVO> listBookPosition(Integer pageNumber, Integer pageSize, Integer state,
			String textBookIds,String bookName, Long materialId, String sessionId) {
		// 验证用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		// 教材权限的检查
		List<PmphRole> pmphRoles = pmphUserService.getListUserRole(pmphUser.getId());
		Integer power = null;
		// 系统管理员权限检查
		for (PmphRole pmphRole : pmphRoles) {
			if (null != pmphRole && null != pmphRole.getRoleName() && "系统管理员".equals(pmphRole.getRoleName())) {
				power = 1; // 我是系统管理原
			}
		}
		// 教材主任检查
		Material material = materialService.getMaterialById(materialId);
		if (null == power) {
			if (null != material && null != material.getDirector() && pmphUser.getId().equals(material.getDirector())) {
				power = 2; // 我是教材的主任
			}
		}
		// 教材项目编辑检查
		if (null == power) {
			List<MaterialProjectEditorVO> materialProjectEditors = materialProjectEditorService
					.listMaterialProjectEditors(materialId);
			if (null != materialProjectEditors && materialProjectEditors.size() > 0) {
				for (MaterialProjectEditorVO materialProjectEditor : materialProjectEditors) {
					if (null != materialProjectEditor && null != materialProjectEditor.getEditorId()
							&& materialProjectEditor.getEditorId().equals(pmphUser.getId())) {
						power = 3; // 我是教材的项目编辑
					}
				}
			}
		}
		// 教材策划编辑检查
		if (null == power) {
			Integer num = materialService.getPlanningEditorSum(materialId, pmphUser.getId());
			if (null != num && num > 0) {
				power = 4; // 我是教材的策划编辑编辑
			}
		}
		if (null == power) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"该教材您没操作权限");
		}
		
		// 拼装复合参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("materialId", materialId); // 教材id
		if(StringUtil.notEmpty(textBookIds)){
			Gson gson = new Gson();
			List<Long> bookIds =gson.fromJson(textBookIds, new TypeToken<ArrayList<Long>>() { }.getType());
			if (null != bookIds && bookIds.size() > 0) {
				map.put("list", bookIds);    // 书籍id
			}
		}
		if (null != state && !state.equals(0)) {
			map.put("state", state);         // 书籍状态
		}
		String bookNameTemp =  StringUtil.toAllCheck(bookName) ;
		if (null != bookNameTemp) {
			map.put("bookName", bookNameTemp); // 书籍名称
		}
		map.put("pmphUserId", pmphUser.getId()); // 用户id
		map.put("power", power); // 用户id
		PageParameter<Map<String, Object>> pageParameter = new PageParameter<Map<String, Object>>(pageNumber, pageSize, map);
		PageResult<BookPositionVO> pageResult = new PageResult<>();
		// 获取总数
		Integer total = textbookDao.listBookPositionTotal(pageParameter);
		if (null != total && total > 0) {
			List<BookPositionVO> rows = textbookDao.listBookPosition(pageParameter);
			//下面进行授权
			for(BookPositionVO row:rows){
				if(power == 1 || power ==2 ){ //管理员或者主任
					row.setMyPower("11111111");
				}else if (power == 3){        //教材项目编辑
					//因为项目编辑的权限不是全部  ，因此要检查我是不是这本书的策划编辑，如果是  ，这本书我的权利就是项目编辑+策划编辑的权利
					Integer tempProjectPermission =  material.getProjectPermission() ;
					if(null != row && 
							null != row.getPlanningEditor() && null != pmphUser.getId() && 
							   row.getPlanningEditor().intValue() == pmphUser.getId().intValue() ){ //我又是策划编辑 
						tempProjectPermission = (tempProjectPermission | material.getPlanPermission() );
					}
					row.setMyPower(StringUtil.tentToBinary(tempProjectPermission)) ;
				}else if (power == 4){ //教材策划编辑
					row.setMyPower(StringUtil.tentToBinary(material.getPlanPermission()));
				}
			}
			pageResult.setRows(rows);
		}
		pageResult.setTotal(total);
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		return pageResult;

	}

	@Override
	public Integer updateTextbooks(Long[] ids,String sessionId) {
		//获取当前用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		if (!pmphUser.getIsAdmin()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有操作权限");
		}
		if(null==ids){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍id为空");
		}
		List<Textbook> textbooks = textbookDao.getTextbooks(ids);
		//判断书籍是否存在
		if(textbooks.size()>0){
			for (Textbook textbook : textbooks) {
				//是否存在策划编辑
				if(ObjectUtil.isNull(textbook.getPlanningEditor())){
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
							"还未选择策划编辑，不能名单确认");
				}
				// 是否发布主编
				if(!textbook.getIsChiefPublished()){
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
							"还未发布主编/副主编，不能名单确认");
				}
				List<DecPosition> decPosition=decPositionService.getDecPositionByTextbookId(textbook.getId());
				// 是否确认编委
				if(decPosition.size()==0){
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
							"还未确认编委，不能名单确认");
				}
			}
		}
		Integer count = 0;
		if (CollectionUtil.isNotEmpty(textbooks)) {
			count = textbookDao.updateTextbooks(textbooks);
		}
		return count;
	}

	public List<BookListVO> getBookListVOs(Long materialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"教材id不能为空");
		}
		Material material = materialService.getMaterialById(materialId);
		Long materialType = material.getMaterialType();
		String path;
		try{
			path = materialTypeService.getMaterialTypeById(materialType).getPath();
		}catch (NullPointerException e){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "找不到此教材的分类");
		}
		if (StringUtil.isEmpty(path)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,
					CheckedExceptionResult.NULL_PARAM, "分类路径为空");
		}
		if (path.indexOf("0-") != -1 ){
			path = path.replaceFirst("0-", "");
		}
		String[] pathType = path.split("-");
		for (int i = 0; i < pathType.length ; i++){
			String type = materialTypeService.getMaterialTypeById(Long.valueOf(pathType[i]))
					.getTypeName();
			pathType[i] = pathType[i].replace(pathType[i], type);
		}	
		List<Textbook> bookList = textbookDao.getTextbookByMaterialId(materialId);
		List<BookListVO> books = new ArrayList<>();
		if (null == bookList || bookList.isEmpty()){
			BookListVO bookListVO = new BookListVO();
			bookListVO.setMaterialId(material.getId());
			bookListVO.setMaterialName(material.getMaterialName());
			bookListVO.setMaterialRound(material.getMaterialRound());
			bookListVO.setMaterialType(pathType);
			bookListVO.setIsPublic(material.getIsPublic());
			books.add(bookListVO);
			return books;
		}
        for (Textbook textbook : bookList){
        	BookListVO bookListVO = new BookListVO();
    		bookListVO.setMaterialId(material.getId());
    		bookListVO.setMaterialName(material.getMaterialName());
    		bookListVO.setMaterialRound(material.getMaterialRound());
    		bookListVO.setMaterialType(pathType);
    		bookListVO.setIsPublic(material.getIsPublic());
        	bookListVO.setTextbook(textbook);
        	if (CollectionUtil.isNotEmpty(decPositionService.listDecPositionsByTextbookId(textbook.getId()))
        			&& decPositionService.listDecPositionsByTextbookId(textbook.getId()).size() >0 ){
        		bookListVO.setAllowedDelete(false);
        	}else {
        		bookListVO.setAllowedDelete(true);
        	}
        	books.add(bookListVO);
        }
		return books;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Textbook> addOrUpdateTextBookList(Long materialId, Boolean isPublic,String textbooks, String sessionId)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
		}
		if (ObjectUtil.isNull(isPublic)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "可见性区别不能为空");
		}
		if (StringUtil.isEmpty(textbooks)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, 
					CheckedExceptionResult.NULL_PARAM, "书籍信息不能为空");
		}
		/*
		 * 检测同一教材下书名和版次都相同的数据
		 */
		List<Map<String,Object>> list = new ArrayList<>();
		Gson gson = new GsonBuilder().serializeNulls().create();
		List<Textbook> bookList =gson.fromJson(textbooks, 
				new TypeToken<ArrayList<Textbook>>(){
		}.getType()) ;
		/*
		 * 对数据进行排序
		 */
		ComparatorChain comparatorChain = new ComparatorChain();
		comparatorChain.addComparator(new BeanComparator<Textbook>("sort"));
		Collections.sort(bookList, comparatorChain);
		/*
		 * 查询此教材下现有的书籍
		 */
		List<Textbook> textbookList = textbookDao.getTextbookByMaterialId(materialId);
		List<Long> ids = new ArrayList<>();
		for (Textbook textbook : textbookList){
			ids.add(textbook.getId());
		}
		List<Long> delBook = new ArrayList<>();//装数据库中本来已经有的书籍id
		int count = 1; //判断书序号的连续性计数器
		for (Textbook book : bookList){
		if (ObjectUtil.isNull(book.getMaterialId())){
			book.setMaterialId(materialId);
		}
		if (StringUtil.isEmpty(book.getTextbookName())){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.NULL_PARAM, "书籍名称不能为空");
		}
		if (StringUtil.strLength(book.getTextbookName()) > 25){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.ILLEGAL_PARAM, "书籍名称字数不能超过25个,请修改后再提交");
		}
		if (ObjectUtil.isNull(book.getTextbookRound())){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.NULL_PARAM, "书籍轮次不能为空");
		}
		if (book.getTextbookRound().intValue() > 2147483647){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.ILLEGAL_PARAM, "图书轮次过大，请修改后再提交");
		}
		if (ObjectUtil.isNull(book.getSort())){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.NULL_PARAM, "图书序号不能为空");
		}
		if (book.getSort().intValue() > 2147483647){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.ILLEGAL_PARAM, "图书序号过大，请修改后再提交");
		}
		if (ObjectUtil.isNull(book.getFounderId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.NULL_PARAM, "创建人id不能为空");
		}
		if (count != book.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "书籍序号必须从1开始且连续");
		}
			Map<String, Object> map = new HashMap<>();
			map.put(book.getTextbookName(), book.getTextbookRound());
			if (list.contains(map)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "同一教材下书名和版次不能都相同");
			}
			/* 设置书目录时保存操作传回来的对象里可能有已经存在的书籍，已经存在的修改
			 相关信息，没有的进行新增操作 */
			if (ObjectUtil.notNull(textbookDao.getTextbookById(book.getId()))) {
				textbookDao.updateTextbook(book);
				delBook.add(book.getId());
			} else {
				textbookDao.addTextbook(book);
			}
			list.add(map);
			count++;
		}
		/* 设置书目录时若删除了部分书籍，找出这些书籍的id并将表中的相关数据删除掉 */
		ids.removeAll(delBook);
			if (CollectionUtil.isNotEmpty(ids)){
				for (Long id : ids){
					if (CollectionUtil.isNotEmpty(decPositionService.listDecPositionsByTextbookId(id))
		        			&& decPositionService.listDecPositionsByTextbookId(id).size() >0 ){
						throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
								CheckedExceptionResult.NULL_PARAM, "被申报的书籍不允许被删除");
					}else{
						textbookDao.deleteTextbookById(id);
					}
				}
			}
		/* 修改对应的教材的可见性区别 */
		Material material = new Material();
		material.setId(materialId);
		material.setIsPublic(isPublic);
		materialService.updateMaterial(material, sessionId);
		return textbookDao.getTextbookByMaterialId(materialId);
	}
	
	@SuppressWarnings({ "resource"})
	@Override
	public List<Textbook> importExcel(MultipartFile file, Long materialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
		}
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		Workbook workbook = null;
		InputStream in = null ;
		try {
			in = file.getInputStream();
		} catch (FileNotFoundException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
					CheckedExceptionResult.NULL_PARAM, "未获取到文件");
		} catch (IOException e){
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
					CheckedExceptionResult.ILLEGAL_PARAM, "读取文件失败");
		}
		try {
			if ((".xls").equals(fileType)){
				workbook = new HSSFWorkbook(in);
			} else if ((".xlsx").equals(fileType)){
				workbook = new XSSFWorkbook(in);
			} else{
				throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
						CheckedExceptionResult.ILLEGAL_PARAM, "读取的不是Excel文件");
			}
			} catch (IOException e) {
				throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
						CheckedExceptionResult.ILLEGAL_PARAM, "读取文件失败");
			}
		List<Textbook> bookList = new ArrayList<>();
		List<Textbook> books = textbookDao.getTextbookByMaterialId(materialId);
		//声明一个有人申报的书籍的集合
		List<Textbook> havePeople = new ArrayList<>();
		//声明一个没有人申报的书籍集合
		List<Textbook> noPeople = new ArrayList<>();
		for (Textbook book : books){
			if (CollectionUtil.isNotEmpty(decPositionService.listDecPositionsByTextbookId(book.getId()))
        			&& decPositionService.listDecPositionsByTextbookId(book.getId()).size() >0 ){
				havePeople.add(book);
			}else{
				noPeople.add(book);
			}
		}
		/* 有人申报的教材，以数据库里查询出来的数据为准 */
		if (null != havePeople || !havePeople.isEmpty()){
			for (Textbook book : havePeople){
				bookList.add(book);
			}
		}
		for (int numSheet = 0 ; numSheet < workbook.getNumberOfSheets();numSheet ++){
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (null == sheet){
				continue;
			}
			for (int rowNum = 1 ; rowNum <= sheet.getLastRowNum();rowNum ++){
				Textbook textbook = new Textbook();
				Row row = sheet.getRow(rowNum);
				if (null == row){
					break;
				}
				Cell first = row.getCell(0);
				Cell second = row.getCell(1);
				Cell third = row.getCell(2);
				if ((ObjectUtil.isNull(first) || "".equals(first.toString())) && ( ObjectUtil.isNull(second) 
						|| "".equals(second.toString()))&& (ObjectUtil.isNull(third)
						  || "".equals(third.toString()))){
					break;
				}
				String bookName = StringUtil.getCellValue(second);
				if (StringUtil.strLength(bookName) > 25){
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "图书名称不能超过25个字数，请修改后再上传");
				}
				Integer sort = 0;
				Integer round = 0;
				try{
					sort = ObjectUtil.getCellValue(first);
				} catch(NumberFormatException e){
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "图书序号格式错误，请按照模板格式修改后"
									+ "再上传");
				}
				try{
					round = ObjectUtil.getCellValue(third);
				} catch(NumberFormatException e){
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "书籍版次格式错误，请按照模板格式修改后"
									+ "再上传");
				}
				/* 无人申报的教材，如果与Excel文档相同的书籍，保留数据库里的数据，否则保存Excel文档里的书籍 */
				if (null == noPeople || noPeople.isEmpty()){
					textbook.setSort(sort);
					textbook.setTextbookName(bookName);
					textbook.setTextbookRound(round);
					bookList.add(textbook);								
				}else{
					textbook.setSort(sort);
					textbook.setTextbookName(bookName);
					textbook.setTextbookRound(round);
					for (Textbook book : noPeople){
						if (sort.intValue() == book.getSort() && round.intValue() == book.getTextbookRound()
								&& bookName.trim().equals(book.getTextbookName().trim())){
							textbook = book;
						}
					}
					bookList.add(textbook);
				}				
			}
		}
		return bookList;
	}
	
	@Override
	public Integer updateTextbookAndMaterial(Long[] ids,String sessionId) throws CheckedServiceException {
		//获取当前用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		if (!pmphUser.getIsAdmin()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有操作权限");
		}
		List<Textbook> textbooks=textbookDao.getTextbooks(ids);
		List<Textbook> textBooks =new ArrayList<Textbook>(textbooks.size());
		Material material=new Material();
		List <Long> textBookIds =  new ArrayList<>(textbooks.size());
		for (Textbook textbook : textbooks) {
			if(Const.TRUE==textbook.getIsPublished()){
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, 
						CheckedExceptionResult.ILLEGAL_PARAM,"名单已确认");
			}
			textBooks.add(new Textbook(textbook.getId()));
			material.setId(textbook.getMaterialId());
			textBookIds.add(textbook.getId());
		}
		textbookDao.updateBookPublished(textBooks);
		List<Textbook> books=materialDao.getMaterialAndTextbook(material);
		Integer count = 0;
		/*通过遍历查看教材下面所有书籍是否公布，当数据全部公布则该教材改为最终公布*/
		for (Textbook book : books) {
			if(book.getIsPublished()){
				count++;
			}
		}
		if(count==books.size()){
			count =materialDao.updateMaterialPublished(material);
		}
		/**下面是发布更新最终结果表的数据*/
		//获取这些书的申报者
		List<DecPosition> lst = decPositionService.listDecPositionsByTextBookIds(textBookIds);
		//这些书的被遴选者
		List<DecPositionPublished> decPositionPublishedLst  =  new ArrayList<DecPositionPublished>(lst.size());
		for(DecPosition  decPosition:lst ){
			if(null == decPosition || null == decPosition.getChosenPosition() || decPosition.getChosenPosition() <= 0 ){
				continue ;
			}
			DecPositionPublished decPositionPublished =  new DecPositionPublished();
			decPositionPublished.setPublisherId(pmphUser.getId());
			decPositionPublished.setDeclarationId(decPosition.getDeclarationId());
			decPositionPublished.setTextbookId(decPosition.getTextbookId());
			decPositionPublished.setPresetPosition(decPosition.getPresetPosition());
			decPositionPublished.setIsOnList(true);
			decPositionPublished.setChosenPosition(decPosition.getChosenPosition());
			decPositionPublished.setRank(decPosition.getRank());
			decPositionPublished.setSyllabusId(decPosition.getSyllabusId());
			decPositionPublished.setSyllabusName(decPosition.getSyllabusName());
			decPositionPublishedLst.add(decPositionPublished);
		}
		//先删除dec_position_published表中的所有数据 
		decPositionPublishedService.deleteDecPositionPublishedByBookIds(textBookIds);
		//向dec_position_published插入新数据
		decPositionPublishedService.batchInsertDecPositionPublished(decPositionPublishedLst);
		/**下面是发布更新最终结果表的数据  ---end ---*/
		return count;
	}
	
	@Override
	public List<Textbook> getTextbookByMaterialIdAndUserId(Long materialId, Long userId)
			throws CheckedServiceException {
		return textbookDao.getTextbookByMaterialIdAndUserId(materialId, userId);
	}

	@Override
	public List<Textbook> listTopicNumber(Long materialId)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
		}
		List<Textbook> textbooksList = textbookDao.listTopicNumber(materialId);
		return textbooksList;
	}

	@Override
	public List<Textbook> addTopicNumber(String topicTextbooks) throws CheckedServiceException {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Textbook>>(){
		}.getType();
		List<Textbook> textbooks = gson.fromJson(topicTextbooks, type);
		if (CollectionUtil.isEmpty(textbooks)){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		for (Textbook textbook : textbooks){
			if (!textbook.getIsPublished()){
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "未公布教材书籍不能设置选题号");
			}
			textbookDao.updateTextbook(textbook);
		}
		return textbooks;
	}
	
	@SuppressWarnings("resource")
	@Override
	public List<Textbook> importTopicExcel(MultipartFile file)
			throws CheckedServiceException {
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		Workbook workbook = null;
		InputStream in = null ;
		try {
			in = file.getInputStream();
		} catch (FileNotFoundException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
					CheckedExceptionResult.NULL_PARAM, "未获取到文件");
		} catch (IOException e){
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
					CheckedExceptionResult.ILLEGAL_PARAM, "读取文件失败");
		}
		try {
			if ((".xls").equals(fileType)){
				workbook = new HSSFWorkbook(in);
			} else if ((".xlsx").equals(fileType)){
				workbook = new XSSFWorkbook(in);
			} else{
				throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
						CheckedExceptionResult.ILLEGAL_PARAM, "读取的不是Excel文件");
			}
			} catch (IOException e) {
				throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
						CheckedExceptionResult.ILLEGAL_PARAM, "读取文件失败");
			}
		List<Textbook> textbookList = new ArrayList<>();
		for (int numSheet = 0 ; numSheet < workbook.getNumberOfSheets();numSheet ++){
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (null == sheet){
				continue;
			}
			for (int rowNum = 1 ; rowNum <= sheet.getLastRowNum();rowNum ++){
				Textbook textbook = new Textbook();
				Row row = sheet.getRow(rowNum);
				if (null == row){
					break;
				}
				Cell first = row.getCell(0);
				Cell second = row.getCell(1);
				Cell third = row.getCell(2);
				Cell fourth = row.getCell(3);
				if ((ObjectUtil.isNull(first) || "".equals(first.toString())) && ( ObjectUtil.isNull(second) 
						|| "".equals(second.toString()))&& (ObjectUtil.isNull(third)
								  || "".equals(third.toString()))){
					break;
				}
				String bookName = StringUtil.getCellValue(second);
				if (StringUtil.strLength(bookName) > 25){
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "图书名称不能超过25个字数，请修改后再上传");
				}
				String topicNumber = StringUtil.getCellValue(fourth);
				topicNumber = topicNumber.substring(0, topicNumber.indexOf(".0"));
				if (StringUtil.strLength(topicNumber) > 30){
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "选题号不能超过30个字数，请修改后再上传");
				}
				Integer sort = 0;
				Integer round = 0;
				try{
					sort = ObjectUtil.getCellValue(first);
				} catch(NumberFormatException e){
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "图书序号格式错误，请按照模板格式修改后"
									+ "再上传");
				}
				try{
					round = ObjectUtil.getCellValue(third);
				} catch(NumberFormatException e){
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "书籍版次格式错误，请按照模板格式修改后"
									+ "再上传");
				}
				textbook.setSort(sort);
				textbook.setTextbookName(bookName);
				textbook.setTextbookRound(round);
				textbook.setTopicNumber(topicNumber);
				textbookList.add(textbook);			
			}
		}
		return textbookList;
	}

	@Override
	public PageResult<TextbookDecVO> listEditorSelection(PageParameter<TextbookDecVO> pageParameter) {
		if(ObjectUtil.isNull(pageParameter.getParameter().getTextBookId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
					CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		PageResult<TextbookDecVO> pageResult = new PageResult<TextbookDecVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        int total = textbookDao.getTextbookDecTotal(pageParameter);
        if (total > 0) {
            pageResult.setTotal(total);
            List<TextbookDecVO>  list= textbookDao.getTextbookDecVOList(pageParameter);
            pageResult.setRows(list);
        }
        return pageResult;
	}

	@Override
	public List<ExcelDecAndTextbookVO> getExcelDecAndTextbooks(Long[] textbookIds) 
			throws CheckedServiceException {
		if(null==textbookIds){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                    CheckedExceptionResult.NULL_PARAM, "书籍id为空");
		}
		List<ExcelDecAndTextbookVO> list=textbookDao.getExcelDecAndTextbooks(textbookIds);
		for (ExcelDecAndTextbookVO excelDecAndTextbookVO : list) {
			switch (excelDecAndTextbookVO.getChosenPosition()) {
			case 1:
					excelDecAndTextbookVO.setShowChosenPosition("编委");
				break;
			case 2:
					excelDecAndTextbookVO.setShowChosenPosition("副主编");
				break;
			case 3:
					excelDecAndTextbookVO.setShowChosenPosition("副主编,编委");
				break;
			case 4:
					excelDecAndTextbookVO.setShowChosenPosition("主编");
				break;
			case 5:
					excelDecAndTextbookVO.setShowChosenPosition("主编,编委");
				break;
			case 6:
					excelDecAndTextbookVO.setShowChosenPosition("主编,副主编");
				break;
			case 7:
					excelDecAndTextbookVO.setShowChosenPosition("主编,副主编,编委");
				break;
			case 8:
				excelDecAndTextbookVO.setShowChosenPosition("数字编委");
				break;
			case 9:
				excelDecAndTextbookVO.setShowChosenPosition("编委，数字编委");
				break;
			case 10:
				excelDecAndTextbookVO.setShowChosenPosition("副主编,数字编委");
				break;
			case 11:
				excelDecAndTextbookVO.setShowChosenPosition("副主编,编委,数字编委");
				break;
			case 12:
				excelDecAndTextbookVO.setShowChosenPosition("主编,数字编委");
				break;
			case 13:
				excelDecAndTextbookVO.setShowChosenPosition("主编,编委,数字编委");
				break;
			case 14:
				excelDecAndTextbookVO.setShowChosenPosition("主编,副主编,数字编委");
				break;
			default:
				excelDecAndTextbookVO.setShowChosenPosition("主编,副主编,编委,数字编委");
				break;
			}
			switch (excelDecAndTextbookVO.getOnlineProgress()) {
			case 0:
				excelDecAndTextbookVO.setShowOnlineProgress("未提交");
				break;
			case 1:
				excelDecAndTextbookVO.setShowOnlineProgress("已提交");
				break;
			case 2:
				excelDecAndTextbookVO.setShowOnlineProgress("被退回");
				break;
			default:
				excelDecAndTextbookVO.setShowOnlineProgress("审核通过");
				break;
			}
			switch (excelDecAndTextbookVO.getOfflineProgress()) {
			case 0:
				excelDecAndTextbookVO.setShowOfflineProgress("未收到纸质表");
				break;
			case 1:
				excelDecAndTextbookVO.setShowOfflineProgress("被退回纸质表");
				break;
			default:
				excelDecAndTextbookVO.setShowOfflineProgress("已收到纸质表");
				break;
			}
			switch (excelDecAndTextbookVO.getIdtype()) {
			case 0:
				excelDecAndTextbookVO.setShowIdtype("身份证");
				break;
			case 1:
				excelDecAndTextbookVO.setShowIdtype("护照");
				break;
			default:
				excelDecAndTextbookVO.setShowIdtype("军官证");
				break;
			}
		}
		return list;
	}

	@Override
	public List<Textbook> getTextbooknameList(Long materialId) {
		if(null==materialId){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                    CheckedExceptionResult.NULL_PARAM, "教材id为空");
		}
		List<Textbook> textbooks=textbookDao.getTextbookByMaterialId(materialId);
		return textbooks;
	}


	@Override
	public List<DecPositionBO> getExcelDecByMaterialId(Long[] textbookIds) {
		if(null==textbookIds){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                    CheckedExceptionResult.NULL_PARAM, "教材id为空");
		}
		List<DecPositionBO> list=textbookDao.getExcelDecByMaterialId(textbookIds);
		return list;
	}
	
}

