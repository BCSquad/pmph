package com.bc.pmpheep.back.service;

import java.io.IOException;
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

import com.bc.pmpheep.back.dao.MaterialDao;
import com.bc.pmpheep.back.dao.TextbookDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.FileUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookListVO;
import com.bc.pmpheep.back.vo.BookPositionVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
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
			String textBookIds, Long materialId, String sessionId) {
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
		if (null == power) {
			Material material = materialService.getMaterialById(materialId);
			if (null != material && null != material.getDirector() && pmphUser.getId().equals(material.getDirector())) {
				power = 2; // 我是教材的主任
			}
		}
		// 教材项目编辑检查
		if (null == power) {
			List<MaterialProjectEditor> materialProjectEditors = materialProjectEditorService
					.listMaterialProjectEditors(materialId);
			if (null != materialProjectEditors && materialProjectEditors.size() > 0) {
				for (MaterialProjectEditor materialProjectEditor : materialProjectEditors) {
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
		Gson gson = new Gson();
		List<Long> bookIds = gson.fromJson(textBookIds, new TypeToken<ArrayList<Long>>() {
		}.getType());
		// 拼装复合参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("materialId", materialId); // 教材id
		if (null != bookIds && bookIds.size() > 0) {
			map.put("list", bookIds); // 书籍id
		}
		if (null != state && !state.equals(0)) {
			map.put("state", state); // 书籍状态
		}
		map.put("pmphUserId", pmphUser.getId()); // 用户id
		map.put("power", power); // 用户id
		PageParameter<Map<String, Object>> pageParameter = new PageParameter<Map<String, Object>>(pageNumber, pageSize,
				map);
		PageResult<BookPositionVO> pageResult = new PageResult<>();
		// 获取总数
		Integer total = textbookDao.listBookPositionTotal(pageParameter);
		if (null != total && total > 0) {
			List<BookPositionVO> rows = textbookDao.listBookPosition(pageParameter);
			pageResult.setRows(rows);
		}
		pageResult.setTotal(total);
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		return pageResult;

	}

	@Override
	public Integer updateTextbooks(Long[] ids) {
		List<Textbook> textbooks = textbookDao.getTextbooks(ids);
		List<Textbook> textBook = new ArrayList<Textbook>(textbooks.size());
		for (Textbook textbook : textbooks) {
			if (Const.FALSE == textbook.getIsPlanningEditorConfirm()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "未分配策划编辑");
			}
			if (Const.FALSE == textbook.getIsChiefChosen()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "未确定第一主编");
			}
			if (Const.FALSE == textbook.getIsQualifierSelected()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "未确定编委");
			}
			textBook.add(new Textbook(textbook.getId()));
		}
		Integer count = 0;
		if (CollectionUtil.isNotEmpty(textBook)) {
			count = textbookDao.updateTextbooks(textBook);
		}
		return count;
	}

	public BookListVO getBookListVO(Long materialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"教材id不能为空");
		}
		Material material = materialService.getMaterialById(materialId);
		List<Textbook> bookList = textbookDao.getTextbookByMaterialId(materialId);
		Long materialType = material.getMaterialType();
		BookListVO bookListVO = new BookListVO();
		bookListVO.setMaterialId(material.getId());
		bookListVO.setMaterialName(material.getMaterialName());
		bookListVO.setMaterialRound(material.getMaterialRound());
		String path = materialTypeService.getMaterialTypeById(materialType).getPath();
		if (StringUtil.isEmpty(path)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,
					CheckedExceptionResult.NULL_PARAM, "分类路径为空");
		}
		if (path.indexOf("0-") != -1){
			path = path.replace("0-", "");
		}
		String[] pathType = path.split("-");
		for (int i = 0; i < pathType.length ; i++){
			String type = materialTypeService.getMaterialTypeById(Long.valueOf(pathType[i]))
					.getTypeName();
			pathType[i] = pathType[i].replace(pathType[i], type);
		}
		Gson gson = new Gson();
		bookListVO.setMaterialType(pathType);
		bookListVO.setTextbooks(gson.toJson(bookList));
		return bookListVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Textbook> addOrUpdateTextBookList(BookListVO bookListVO) throws CheckedServiceException {
		if (ObjectUtil.isNull(bookListVO)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"参数不能为空");
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Gson gson = new Gson();
		List<Textbook> bookList =gson.fromJson(bookListVO.getTextbooks(), 
				new TypeToken<ArrayList<Textbook>>(){
		}.getType()) ;
		ComparatorChain comparatorChain = new ComparatorChain();
		comparatorChain.addComparator(new BeanComparator<Textbook>("sort"));
		Collections.sort(bookList, comparatorChain);
		int count = 1; //判断书序号的连续性计数器
		for (Textbook textbook : bookList){
		if (count != textbook.getSort()){
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "书籍序号必须连续");
			}
			Map<String, Object> map = new HashMap<>();
			map.put(textbook.getTextbookName(), textbook.getTextbookRound());
			if (list.contains(map)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "同一教材下书名和版次不能都相同");
			}
			if (ObjectUtil.notNull(textbookDao.getTextbookById(textbook.getId()))) {
				textbookDao.updateTextbook(textbook);
			} else {
				textbookDao.addTextbook(textbook);
			}
			list.add(map);
			count++;
		}
		return bookList;
	}
	
	@SuppressWarnings({ "resource", "deprecation" })
	@Override
	public List<Textbook> importExcel(MultipartFile file) throws CheckedServiceException,IOException{
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		Workbook workbook = null;
		if ((".xls").equals(fileType)){
			workbook = new HSSFWorkbook(file.getInputStream());
		} else if ((".xlsx").equals(fileType)){
			workbook = new XSSFWorkbook(file.getInputStream());
		} else{
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
					CheckedExceptionResult.ILLEGAL_PARAM, "读取的不是Excel文件");
		}
		List<Textbook> bookList = new ArrayList<>();
		for (int numSheet = 0 ; numSheet < workbook.getNumberOfSheets();numSheet ++){
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (null == sheet){
				continue;
			}
			Textbook textbook = new Textbook();
			for (int rowNum = 1 ; rowNum <= sheet.getLastRowNum();rowNum ++){
				Row row = sheet.getRow(rowNum);
				if (null == row){
					break;
				}
				Cell first = row.getCell(0);
				Cell second = row.getCell(1);
				Cell third = row.getCell(2);
				if (ObjectUtil.isNull(first) || ObjectUtil.isNull(second) || ObjectUtil.isNull(third)){
					break;
				}
				if (second.getCellType() != Cell.CELL_TYPE_STRING){
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "文件内容格式错误");
				} else {
					Integer sort = (int) row.getCell(0).getNumericCellValue();
					String bookName = row.getCell(1).getStringCellValue();
					Integer round = (int) row.getCell(2).getNumericCellValue();
					textbook.setSort(sort);
					textbook.setTextbookName(bookName);
					textbook.setTextbookRound(round);
					bookList.add(textbook);
				}
			}
		}
		FileUtil.delFile(Const.FILE_PATH_FILE + file.getOriginalFilename());;
		return bookList;
	}
	
	@Override
	public Integer updateTextbookAndMaterial(Long[] ids) throws CheckedServiceException {
		List<Textbook> textbooks=textbookDao.getTextbooks(ids);
		List<Textbook> textBooks =new ArrayList<Textbook>(textbooks.size());
		Material material=new Material();
		for (Textbook textbook : textbooks) {
			if(Const.TRUE==textbook.getIsPublished()){
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, 
						CheckedExceptionResult.ILLEGAL_PARAM,"名单已确认");
			}
			textBooks.add(new Textbook(textbook.getId()));
			material.setId(textbook.getMaterialId());
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
}
