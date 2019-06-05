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
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bc.pmpheep.back.dao.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.*;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
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

import com.bc.pmpheep.back.bo.DecPositionBO;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.service.common.SystemMessageService;
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
	private WriterUserService writerUserService;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private MaterialTypeService materialTypeService;

	@Autowired
	private MaterialProjectEditorService materialProjectEditorService;

	@Autowired
	private DecPositionService decPositionService;

	@Autowired
	private DecPositionPublishedService decPositionPublishedService;

	@Autowired
	private SystemMessageService systemMessageService;

	@Autowired
	private TextbookService textbookService;
	
	@Autowired
	private DeclarationService declarationService;
	
	@Autowired
	private WriterUserTrendstService writerUserTrendstService;

	@Autowired
	WXQYUserService wxqyUserService;

	@Autowired
	WxSendMessageService wxSendMessageService;
	@Autowired
	DeclarationDao declarationDao;
	@Autowired
	DataDictionaryDao dataDictionaryDao;
	/**
	 *
	 * @param textbook
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

	@Override
	public Integer updateTextbookAndMaterial(Long[] ids, String sessionId, Long materialId)
			throws CheckedServiceException, Exception {
		// 获取当前用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		// if (!pmphUser.getIsAdmin()) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
		// CheckedExceptionResult.ILLEGAL_PARAM,
		// "该用户没有操作权限");
		// }
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
		List<Textbook> textbooks = textbookDao.getTextbooks(ids);
		if (textbooks.size() > 0) {
			for (Textbook textbook : textbooks) {
				// 是否存在策划编辑
				if (ObjectUtil.isNull(textbook.getPlanningEditor())) {
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
							CheckedExceptionResult.NULL_PARAM, "还未选择策划编辑，不能进行公布");
				}
				// 是否发布主编
				if (!textbook.getIsChiefPublished()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
							CheckedExceptionResult.NULL_PARAM, "还未发布主编/副主编，不能进行公布");
				}


				List<DecPosition> decMainPosition = decPositionService.getMainDecPositionByTextbookId(textbook.getId());
				//确认是否有主编副主编
				if (decMainPosition.size() == 0) {
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
							CheckedExceptionResult.NULL_PARAM, "还未遴选主编/副主编，不能进行公布");
				}

				//textbookDao.
				List<DecPosition> decPosition = decPositionService.getDecPositionByTextbookId(textbook.getId());
				// 是否确认编委
				if (decPosition.size() == 0) {
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
							CheckedExceptionResult.NULL_PARAM, "还未确认编委，不能进行公布");
				}
			}
		}
		Material materials = new Material();
		List<Long> textBookIds = new ArrayList<>(textbooks.size());
		for (Textbook textbook : textbooks) {
			// if(Const.TRUE==textbook.getIsPublished()){
			// throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
			// CheckedExceptionResult.ILLEGAL_PARAM,"名单已确认");
			// }
			// if(textbook.getIsPublished()) {
			Textbook textbook2 = new Textbook(textbook.getId(), textbook.getRevisionTimes().intValue() + 1)
					.setIsPublished(true);
			textbook2.setRevisionTimes(textbook.getRevisionTimes().intValue() + 1);
			textbookDao.updateTextbook(textbook2);
			// }else {
			// Textbook textbook2 = new Textbook(textbook.getId(), 0).setIsPublished(true);
			// textbook2.setRevisionTimes(0) ;
			// textbookDao.updateTextbook(textbook2);
			// }
			materials.setId(textbook.getMaterialId());
			textBookIds.add(textbook.getId());
		}
		// textbookDao.updateBookPublished(textBooks);
		// textbookDao.updateTextbook(textbook);

		/*下面是向主任、项目编辑、策划编辑 发送企业微信推送*/
		//向列表中书籍的主任、项目编辑、策划编辑，发送微信推送，内容拼接如下
		localMethodToLeaderWXMsg(pmphUser, textbooks, "结果公布");

		/** 下面是发布更新最终结果表的数据 */
		// 获取这些书的申报者
		List<DecPosition> lst = decPositionService.listDecPositionsByTextBookIds(textBookIds);
		// 这些书的被遴选者
		List<DecPositionPublished> decPositionPublishedLst = new ArrayList<DecPositionPublished>(lst.size());
		for (DecPosition decPosition : lst) {
			if (null == decPosition || null == decPosition.getChosenPosition()
					|| decPosition.getChosenPosition() <= 0) {
				continue;
			}
			DecPositionPublished decPositionPublished = new DecPositionPublished();
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
		List<DecPositionPublished> olds = decPositionPublishedService.getDecPositionPublishedListByBookIds(textBookIds);
		List<DecPositionPublished> sends = new ArrayList<>();
		for (DecPositionPublished now : decPositionPublishedLst) {
			if (ObjectUtil.notNull(now.getRank())) {
				sends.add(now);
			} else {
				DecPositionPublished published = decPositionPublishedService
						.getDecPositionByDeclarationId(now.getDeclarationId(), now.getTextbookId());
				if (ObjectUtil.isNull(published)) {
					sends.add(now);
				}
				for (DecPositionPublished old : olds) {
					if (old.getDeclarationId().equals(now.getDeclarationId())
							&& old.getTextbookId().equals(now.getTextbookId())) {
						if (!old.getChosenPosition().equals(now.getChosenPosition())) {
							sends.add(now);
						} else {
							if (null == now.getRank() && null == now.getRank()) {
							} else if (null != now.getRank() && null != now.getRank()) {
								if (!now.getRank().equals(now.getRank())) {
									sends.add(now);
								}
							} else {
								sends.add(now);
							}
						}
					}
				}
			}
		}

		// 被撤销的职位的人发动态
		for (DecPositionPublished old : olds) {
			boolean isDismissed = true; //初始化 是否被撤销
			int dismissPosition = old.getChosenPosition(); //初始化 被撤销的职位
			for (DecPositionPublished now : decPositionPublishedLst) {
				if (old.getDeclarationId().equals(now.getDeclarationId())
						&& old.getTextbookId().equals(now.getTextbookId())) {
					isDismissed = false;
					//被撤销的职务数字 = 原职务数字 按位与 （新职务数字 按位取反） [即原本有现在没有的]
					dismissPosition = old.getChosenPosition() & (~now.getChosenPosition());
				}
			}
			if (isDismissed){ //撤销需要发动态的，从此处打开
				//转换为文字
				String dismissPositionStr = "";
				if((dismissPosition & 4)>0){
					dismissPositionStr += "主编";
				}
				if((dismissPosition & 2)>0){
					dismissPositionStr += "副主编";
				}
				if((dismissPosition & 1)>0){
					dismissPositionStr += "编委";
				}
				if((dismissPosition & 8)>0){
					if(dismissPositionStr.length()>0){
						dismissPositionStr +=",";
					}
					dismissPositionStr += "数字编委";
				}
				if(dismissPositionStr.length()>0){ //若存在被撤销的职务 发动态给相应作者
					// 添加动态信息
					WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
					writerUserTrendst.setUserId(old.getWriterUserId());
					writerUserTrendst.setIsPublic(false);// 自己可见
					writerUserTrendst.setType(8);
					String detail = "";
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("title", CheckedExceptionBusiness.MATERIAL);
					//map.put("content", "抱歉，您《" + old.getTextbookName() + "》的"+dismissPositionStr+"职务已被取消。");
					map.put("content", "您参加的教材“"+material.getMaterialName()+"”的《"+old.getTextbookName()+"》遴选已结束。");
					map.put("img", 2);
					detail = new Gson().toJson(map);
					writerUserTrendst.setDetail(detail);
					writerUserTrendst.setCmsContentId(null);
					writerUserTrendst.setBookId(materialId);
					writerUserTrendst.setBookCommentId(null);
					writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
				}
			}
		}

		// 先删除dec_position_published表中的所有数据
		decPositionPublishedService.deleteDecPositionPublishedByBookIds(textBookIds);
		// 向dec_position_published插入新数据
		decPositionPublishedService.batchInsertDecPositionPublished(decPositionPublishedLst);
		/** 发布更新最终结果表的数据 ---end --- */
		// List<Textbook> books = materialDao.getMaterialAndTextbook(materials);
		List<Textbook> books = textbookService.getTextbookByMaterialId(materials.getId());
		Integer count = 0;
		/* 通过遍历查看教材下面所有书籍是否公布，当数据全部公布则该教材改为最终公布 */
		for (Textbook book : books) {
			if (book.getIsPublished()) {
				count++;
			}
		}
		if (count == books.size()) {
			// 检查有没有再次公布
			PageResult<BookPositionVO> listBookPosition = this.listBookPosition(1, 9999999, null, null, null,
					materials.getId(), sessionId);
			boolean haveNo = true;
			for (BookPositionVO bookPositionVO : listBookPosition.getRows()) {
				if (bookPositionVO.getIsPublished() && bookPositionVO.getRepub()) {
					haveNo = false;
					break;
				}
			}
			if (haveNo) {
				count = materialDao.updateMaterialPublished(materials);
			}
		}
		// 发送消息
		for (Textbook textbook : textbooks) {
			systemMessageService.sendWhenPubfinalResult(textbook.getId(), sends,pmphUser);
		}
		//当教材遴选结束时给为遴选上的用户推送消息
		Material material2=materialService.getMaterialById(materialId);
		if(ObjectUtil.notNull(material2)){
			if(material2.getIsAllTextbookPublished()){
				List<Declaration> declaration=declarationService.getPositionChooseLossByMaterialId(materialId);
				systemMessageService.sendWhenPositionChooserLoss(materialId, declaration,pmphUser);
				if(null != declaration && declaration.size() > 0 ){
					for(Declaration d: declaration){
						// 添加动态信息
						WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
						writerUserTrendst.setUserId(d.getUserId());
						writerUserTrendst.setIsPublic(false);// 自己可见
						writerUserTrendst.setType(8);
						String detail = "";
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("title", CheckedExceptionBusiness.MATERIAL);
						map.put("content", "教材已遴选结束,很遗憾,您未入选");
						map.put("img", 2);
						detail = new Gson().toJson(map);
						writerUserTrendst.setDetail(detail);
						writerUserTrendst.setCmsContentId(null);
						writerUserTrendst.setBookId(materialId);
						writerUserTrendst.setBookCommentId(null);
						writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
					}
				}
			}
		}
		// 遍历被遴选人发送动态 和被修改成专家
		for (DecPositionPublished decPositionPublished : decPositionPublishedLst) {
			if (null == decPositionPublished || null == decPositionPublished.getChosenPosition()
					|| decPositionPublished.getChosenPosition() <= 0) {
				continue;
			}
			// 获取申报表
			Declaration declarationById =  declarationService.getDeclarationById(decPositionPublished.getDeclarationId());
			//修改成专家
			WriterUser  writerUser   =   new WriterUser();
			writerUser.setId(declarationById.getUserId());
			writerUser.setIsExpert(true);
			writerUser.setRank(3);
			writerUserService.update(writerUser);
			// 获取书籍
			Textbook textbook = textbookService.getTextbookById(decPositionPublished.getTextbookId());
			// 作家遴选
			String showChosenPosition = "";
			if (decPositionPublished.getChosenPosition() != 0) {
				switch (decPositionPublished.getChosenPosition()) {
				case 1:
					showChosenPosition = "编委";
					break;
				case 2:
					showChosenPosition = "副主编";
					break;
				case 3:
					showChosenPosition = "副主编,编委";
					break;
				case 4:
					showChosenPosition = "主编";
					break;
				case 5:
					showChosenPosition = "主编,编委";
					break;
				case 6:
					showChosenPosition = "主编,副主编";
					break;
				case 7:
					showChosenPosition = "主编,副主编,编委";
					break;
				case 8:
					showChosenPosition = "数字编委";
					break;
				case 9:
					showChosenPosition = "编委,数字编委";
					break;
				case 10:
					showChosenPosition = "副主编,数字编委";
					break;
				case 11:
					showChosenPosition = "副主编,编委,数字编委";
					break;
				case 12:
					showChosenPosition = "主编,数字编委";
					break;
				case 13:
					showChosenPosition = "主编,编委,数字编委";
					break;
				case 14:
					showChosenPosition = "主编,副主编,数字编委";
					break;
				case 15:
					showChosenPosition = "主编,副主编,编委,数字编委";
					break;
				default:
					break;
				}
			}
			// 添加动态信息
			WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
			writerUserTrendst.setUserId(declarationById.getUserId());
			writerUserTrendst.setIsPublic(false);// 自己可见
			writerUserTrendst.setType(8);
			String detail = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", CheckedExceptionBusiness.MATERIAL);
			map.put("content", "您已被遴选为《" + textbook.getTextbookName() + "》的" 
			+ showChosenPosition  + "。");
			map.put("img", 1);
			detail = new Gson().toJson(map);
			writerUserTrendst.setDetail(detail);
			writerUserTrendst.setCmsContentId(null);
			writerUserTrendst.setBookId(declarationById.getMaterialId());
			writerUserTrendst.setBookCommentId(null);
			writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
		}
		return count;
	}


	@Override
	public List<Textbook> getTextbookByMaterialIdAndUserId(Long materialId, Long userId)
			throws CheckedServiceException {
		return textbookDao.getTextbookByMaterialIdAndUserId(materialId, userId);
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
	 * @param textbook
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateTextbook(Textbook textbook) throws CheckedServiceException {
		if (null == textbook.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		// PmphUser pmphUser =pmphUserDao.get(textbook.getPlanningEditor());
		// if(pmphUser.getIsDisabled()){
		// throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
		// CheckedExceptionResult.ILLEGAL_PARAM, "该用户未启用时禁止选择");
		// }
		// String roleName="策划编辑";//通过roleName查询roleid
		// List<PmphRole> list=pmphRoleService.getList(roleName);//角色id
		// if (ObjectUtil.isNull(textbook.getPlanningEditor()) ||
		// ObjectUtil.isNull(list.get(0).getId())) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
		// CheckedExceptionResult.NULL_PARAM, "角色ID或策划编辑ID为空时禁止新增");
		// }
		// // 判断该用户是否已有策划编辑的角色 没有则新加
		// List<PmphUserRole>
		// pmphUserRoles=pmphRoleService.getUserRoleList(textbook.getPlanningEditor(),
		// list.get(0).getId());
		// if(ObjectUtil.isNull(pmphUserRoles) && pmphUserRoles.size() == 0){
		// roleDao.addUserRole(textbook.getPlanningEditor(),
		// list.get(0).getId());//给策划编辑绑定权限
		// }

		String touser = "";
		List<Long> useridList = new ArrayList<Long>();
		String msg = "";
		if (!ObjectUtil.isNull(textbook.getPlanningEditor())){
			Long planningEditorId = textbook.getPlanningEditor();
			Long textbookId = textbook.getId();
			PmphUser planningEditor = pmphUserService.get(planningEditorId);
			touser = planningEditor.getOpenid();
			useridList.add(planningEditor.getId());
			Textbook originalTextbook = textbookService.getTextbookById(textbookId);
			String materialName = materialService.getMaterialNameById(originalTextbook.getMaterialId());
			//***（策划编辑人名[多个逗号隔开]）已被为《***》（教材名称）的策划编辑
			msg = planningEditor.getRealname()+"已被选为“"+materialName+"”-《"+originalTextbook.getTextbookName()+"》的策划编辑。";
			if (StringUtil.notEmpty(touser)){
				wxqyUserService.sendTextMessage("0","0",touser,"","","text",msg,(short)0,"");
			}
			wxSendMessageService.batchInsertWxMessage(msg,0,useridList,"0","0","");
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
			String textBookIds, String bookName, Long materialId, String sessionId) {
		// 验证用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}


		Material material = materialService.getMaterialById(materialId);
		Integer power = getPower(materialId, pmphUser, material);


		// 拼装复合参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("materialId", materialId); // 教材id
		if (StringUtil.notEmpty(textBookIds)) {
			Gson gson = new Gson();
			List<Long> bookIds = gson.fromJson(textBookIds, new TypeToken<ArrayList<Long>>() {
			}.getType());
			if (null != bookIds && bookIds.size() > 0) {
				map.put("list", bookIds); // 书籍id
			}
		}
		if (null != state && !state.equals(0)) {
			map.put("state", state); // 书籍状态
		}
		String bookNameTemp = StringUtil.toAllCheck(bookName);
		if (null != bookNameTemp) {
			map.put("bookName", bookNameTemp); // 书籍名称
		}
		map.put("pmphUserId", pmphUser.getId()); // 用户id
		map.put("power", power); // 用户id
		PageParameter<Map<String, Object>> pageParameter = new PageParameter<Map<String, Object>>(pageNumber, pageSize,
				map);
		PageResult<BookPositionVO> pageResult = new PageResult<>();
		// 获取总数

		Integer total = textbookDao.listBookPositionTotal(pageParameter);
		if (null != total && total > 0) {

			HashMap<String, Object> paraMap = new HashMap<>();
			paraMap.put("material_id",materialId);
			String material_id = declarationDao.findMaterialCreateDate(paraMap);
			Date date1 = DateUtil.fomatDate(material_id);
			Date date = DateUtil.fomatDate("2019-2-12 12:00");
			List<BookPositionVO> rows;
			if(date1.getTime()>date.getTime()) {
				rows = textbookDao.listBookPosition_up12(pageParameter);
			}else{
				rows = textbookDao.listBookPosition_up1(pageParameter);
			}
			// 下面进行授权
			for (BookPositionVO row : rows) {
				String rowpower = "000000";
				if (power == 1 || power == 2) { // 管理员或者主任
					rowpower = "11111111";
				} else if (power == 3) { // 教材项目编辑
					// 因为项目编辑的权限不是全部 ，因此要检查我是不是这本书的策划编辑，如果是 ，这本书我的权利就是项目编辑+策划编辑的权利
					Integer tempProjectPermission = material.getProjectPermission();
					if (null != row && null != row.getPlanningEditor() && null != pmphUser.getId()
							&& row.getPlanningEditor().intValue() == pmphUser.getId().intValue()) { // 我又是策划编辑
						tempProjectPermission = (tempProjectPermission | material.getPlanPermission());
					}
					rowpower = StringUtil.tentToBinary(tempProjectPermission);
				} else if (power == 4) { // 教材策划编辑
					rowpower = StringUtil.tentToBinary(material.getPlanPermission());
				}
				// 把权限拿出来一个个判断
				// 分配策划编辑的权限
				String s1 = rowpower.substring(0, 1);
				String s2 = rowpower.substring(1, 2);
				String s3 = rowpower.substring(2, 3);
				String s4 = rowpower.substring(3, 4);
				String s5 = rowpower.substring(4, 5);
				String s6 = rowpower.substring(5, 6);
				String s7 = rowpower.substring(6, 7);
				String s8 = rowpower.substring(7, 8);// qiangzhijiesu
				if (material.getIsForceEnd() || material.getIsAllTextbookPublished()) { // 教材结束或者强制结束
					s2 = "0";
					s3 = "0";
					s4 = "0";
					s5 = "0";
					s6 = "0";
				} else if (row.getIsLocked() || row.getIsPublished()) { // 书籍已经发布了或者确认了名单
					s2 = (power == 1 || power == 2) ? s2 : "0";
					s3 = (power == 1 || power == 2) ? s3 : "0";
					s4 = (power == 1 || power == 2) ? s4 : "0";
					s5 = "0";
				}
				rowpower = s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8;
				row.setMyPower(rowpower);
			}
			pageResult.setRows(rows);
		}
		pageResult.setTotal(total);
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		return pageResult;

	}

	private Integer getPower(Long materialId, PmphUser pmphUser, Material material) {
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
			if (null != material && null != material.getDirector()) {
				//2948 N198-组织机构核对完成后，在教材申报发通知的时候，选择的主任（可能是主任，某个部门的普通员工），要求选择的主任的所有归属单位的主任都有和选择主任的权限一样。
				List<PmphUser> parentDeptsDirectors = pmphUserService.getSomebodyParentDeptsPmphUserOfSomeRole(material.getDirector(), null, "主任");
				for (PmphUser PDDirector : parentDeptsDirectors) {
					if (pmphUser.getId().equals(PDDirector.getId())) {
						power = 2; // 我是教材的主任 的部门或上级部门的主任
						break;
					}
				}
				if (pmphUser.getId().equals(material.getDirector())) {
					power = 2; // 我是教材的主任 本人
				}
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
		return power;
	}

	@Override
	public Integer updateTextbooks(Long[] ids, String sessionId) {
		// 获取当前用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		// if (!pmphUser.getIsAdmin()) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
		// CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有操作权限");
		// }
		if (null == ids) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍id为空");
		}
		List<Textbook> textbooks = textbookDao.getTextbooks(ids);
		// 判断书籍是否存在
		if (textbooks.size() > 0) {
			for (Textbook textbook : textbooks) {
				// 是否存在策划编辑
				if (ObjectUtil.isNull(textbook.getPlanningEditor())) {
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
							CheckedExceptionResult.NULL_PARAM, "还未选择策划编辑，不能名单确认");
				}
				// 是否发布主编
				if (!textbook.getIsChiefPublished()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
							CheckedExceptionResult.NULL_PARAM, "还未发布主编/副主编，不能名单确认");
				}
				List<DecPosition> decPosition = decPositionService.getDecPositionByTextbookId(textbook.getId());
				// 是否确认编委
				if (decPosition.size() == 0) {
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
							CheckedExceptionResult.NULL_PARAM, "还未确认编委，不能名单确认");
				}
			}

		}
		//向列表中书籍的主任、项目编辑、策划编辑，发送微信推送，内容拼接如下
		localMethodToLeaderWXMsg(pmphUser, textbooks, "名单确认");

		Integer count = 0;
		if (CollectionUtil.isNotEmpty(textbooks)) {
			count = textbookDao.updateTextbooks(textbooks);
		}
		return count;
	}

	/**
	 * 向列表中书籍的主任、项目编辑、策划编辑，发送微信推送，内容拼接如下
	 * @param pmphUser 操作者(登录人)
	 * @param textbooks 对列表中书籍
	 * @param operateText 进行了如下操作
	 */
	private void localMethodToLeaderWXMsg(PmphUser pmphUser, List<Textbook> textbooks, String operateText) {
		if (textbooks.size() > 0) {
            MaterialVO materialVo = materialService.getMaterialVO(textbooks.get(0).getMaterialId());
            for (Textbook textbook : textbooks) {
                String msg = "";
                String touser = "";
                Set<String> touserIdSet = new HashSet<String>();
                Set<String> touserOpenidSet = new HashSet<String>();
                List<Long> useridList = new ArrayList<Long>();
                //策划编辑
				if(textbook.getPlanningEditor()!=null){
					touserIdSet.add(String.valueOf(textbook.getPlanningEditor()));
				}
                //主任
                touserIdSet.add(String.valueOf(materialVo.getDirector()));
                //项目编辑集合 [{"id":319,"realname":"ADiTest","materialId":75,"editorId":610}]
                List<Map> projectEditorsJA = JSON.parseArray(materialVo.getMaterialProjectEditors(), Map.class);
                for (Map m : projectEditorsJA) {
                    touserIdSet.add(m.get("editorId").toString());
                    //projectEditorNames += m.get("realname").toString()+",";
                }
                //projectEditorNames.substring(0,projectEditorNames.lastIndexOf(",")>0?projectEditorNames.lastIndexOf(","):projectEditorNames.length());
                if (touserIdSet.size() > 1) { //排除操作者自己，但如果除此以外没有别人，仍然给操作者自己发推送
                    touserIdSet.remove(pmphUser.getId().toString());
                }
                for (String id : touserIdSet) { //通过主任、项目编辑、策划编辑的id集合（set无重复id）查询其企业微信号id集合
                    PmphUser pu = pmphUserService.get(Long.parseLong(id));
                    if (StringUtil.notEmpty(pu.getOpenid())) {
                        touserOpenidSet.add(pu.getOpenid());

                    }
					useridList.add(pu.getId());
                }
				touserOpenidSet.remove(null);
                touser = touserOpenidSet.toString();
                // 推送内容： ***（名单确人名）进行了《***》的***（操作）
                msg = pmphUser.getRealname() + "进行了“" + materialVo.getMaterial().getMaterialName() + "”-《" + textbook.getTextbookName() + "》的"+operateText;


                if (touserOpenidSet.size()>0) {
                    wxqyUserService.sendTextMessage("0", "0", touser, "", "", "text", msg, (short) 0,"");
                }
				wxSendMessageService.batchInsertWxMessage(msg,0,useridList,"0","0","");

            }
        }
	}

	public List<BookListVO> getBookListVOs(Long materialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"教材id不能为空");
		}
		Material material = materialService.getMaterialById(materialId);
		if (null == material){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "该教材不存在，请保存教材后再进行相关操作");
		}
		Long materialType = material.getMaterialType();
		String path;
		try {
			path = materialTypeService.getMaterialTypeById(materialType).getPath();
		} catch (NullPointerException e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"找不到此教材的分类");
		}
		if (StringUtil.isEmpty(path)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"分类路径为空");
		}
		path = path + "-" + String.valueOf(materialType);
		if (path.indexOf("0-") != -1) {
			path = path.replaceFirst("0-", "");
		}
		String[] pathType = path.split("-");
		for (int i = 0; i < pathType.length; i++) {
			String type = materialTypeService.getMaterialTypeById(Long.valueOf(pathType[i])).getTypeName();
			pathType[i] = pathType[i].replace(pathType[i], type);
		}
		List<Textbook> bookList = textbookDao.getTextbookByMaterialId(materialId);
		List<BookListVO> books = new ArrayList<>();
		if (null == bookList || bookList.isEmpty()) {
			BookListVO bookListVO = new BookListVO();
			bookListVO.setMaterialId(material.getId());
			bookListVO.setMaterialName(material.getMaterialName());
			bookListVO.setMaterialRound(material.getMaterialRound());
			bookListVO.setMaterialType(pathType);
			bookListVO.setIsPublic(material.getIsPublic());
			bookListVO.setSurveyNum(0);
			books.add(bookListVO);
			return books;
		}
		for (Textbook textbook : bookList) {
			BookListVO bookListVO = new BookListVO();
			bookListVO.setMaterialId(material.getId());
			bookListVO.setMaterialName(material.getMaterialName());
			bookListVO.setMaterialRound(material.getMaterialRound());
			bookListVO.setMaterialType(pathType);
			bookListVO.setIsPublic(material.getIsPublic());
			bookListVO.setTextbook(textbook);
			bookListVO.setSurveyNum(textbook.getSurveyNum());
			if (CollectionUtil.isNotEmpty(decPositionService.listDecPositionsByTextbookId(textbook.getId()))
					&& decPositionService.listDecPositionsByTextbookId(textbook.getId()).size() > 0) {
				bookListVO.setAllowedDelete(false);
			} else {
				bookListVO.setAllowedDelete(true);
			}
			books.add(bookListVO);
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Textbook> addOrUpdateTextBookList(Long materialId, Boolean isPublic, String textbooks, String sessionId)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"教材id不能为空");
		}
		if (ObjectUtil.isNull(isPublic)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"可见性区别不能为空");
		}
		if (StringUtil.isEmpty(textbooks)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍信息不能为空");
		}
		/*
		 * 检测同一教材下书名和版次都相同的数据
		 */
		List<Map<String, Object>> list = new ArrayList<>();
		Gson gson = new GsonBuilder().serializeNulls().create();
		List<Textbook> bookList = gson.fromJson(textbooks, new TypeToken<ArrayList<Textbook>>() {
		}.getType());
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
		for (Textbook textbook : textbookList) {
			ids.add(textbook.getId());
		}
		List<Long> delBook = new ArrayList<>();// 装数据库中本来已经有的书籍id
		int count = 1; // 判断书序号的连续性计数器
		for (Textbook book : bookList) {
			if (ObjectUtil.isNull(book.getMaterialId())) {
				book.setMaterialId(materialId);
			}
			if (StringUtil.isEmpty(book.getTextbookName())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
						"书籍名称不能为空");
			}
			if (StringUtil.strLength(book.getTextbookName()) > 200) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "书籍名称字数不能超过200个,请修改后再提交");
			}
			if (ObjectUtil.isNull(book.getTextbookRound())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
						"书籍轮次不能为空");
			}
			if (book.getTextbookRound().intValue() > 2147483647) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "图书轮次过大，请修改后再提交");
			}
			if (ObjectUtil.isNull(book.getSort())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
						"图书序号不能为空");
			}
			if (book.getSort().intValue() > 2147483647) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "图书序号过大，请修改后再提交");
			}
			if (ObjectUtil.isNull(book.getFounderId())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
						"创建人id不能为空");
			}
			if (count != book.getSort()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "书籍序号必须从1开始且连续");
			}
			Map<String, Object> map = new HashMap<>();
			map.put(book.getTextbookName(), book.getTextbookRound());
			if (list.contains(map)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "同一教材下书名和版次不能都相同");
			}
			/*
			 * 设置书目录时保存操作传回来的对象里可能有已经存在的书籍，已经存在的修改 相关信息，没有的进行新增操作
			 */
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
		if (CollectionUtil.isNotEmpty(ids)) {
			for (Long id : ids) {
				if (CollectionUtil.isNotEmpty(decPositionService.listDecPositionsByTextbookId(id))
						&& decPositionService.listDecPositionsByTextbookId(id).size() > 0) {
					throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
							CheckedExceptionResult.NULL_PARAM, "被申报的书籍不允许被删除");
				} else {
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

	@SuppressWarnings({ "resource" })
	@Override
	public List<Textbook> importExcel(MultipartFile file, Long materialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"教材id不能为空");
		}
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
		List<Textbook> bookList = new ArrayList<>();
		List<Textbook> books = textbookDao.getTextbookByMaterialId(materialId);
		// 声明一个有人申报的书籍的集合
		List<Textbook> havePeople = new ArrayList<>();
		// 声明一个没有人申报的书籍集合
		List<Textbook> noPeople = new ArrayList<>();
		for (Textbook book : books) {
			if (CollectionUtil.isNotEmpty(decPositionService.listDecPositionsByTextbookId(book.getId()))
					&& decPositionService.listDecPositionsByTextbookId(book.getId()).size() > 0) {
				havePeople.add(book);
			} else {
				noPeople.add(book);
			}
		}
		/* 有人申报的教材，以数据库里查询出来的数据为准 */
		if (null != havePeople || !havePeople.isEmpty()) {
			for (Textbook book : havePeople) {
				bookList.add(book);
			}
		}
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (null == sheet) {
				continue;
			}
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Textbook textbook = new Textbook();
				Row row = sheet.getRow(rowNum);
				if (null == row) {
					break;
				}
				Cell first = row.getCell(0);
				Cell second = row.getCell(1);
				Cell third = row.getCell(2);
				if ((ObjectUtil.isNull(first) || "".equals(first.toString()))
						&& (ObjectUtil.isNull(second) || "".equals(second.toString()))
						&& (ObjectUtil.isNull(third) || "".equals(third.toString()))) {
					break;
				}
				String bookName = StringUtil.getCellValue(second);
				if (StringUtil.strLength(bookName) > 50) {
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "图书名称不能超过25个字数，请修改后再上传");
				}
				Integer sort = 0;
				Integer round = 0;
				try {
					sort = ObjectUtil.getCellValue(first);
				} catch (NumberFormatException e) {
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "图书序号格式错误，请按照模板格式修改后" + "再上传");
				}
				try {
					round = ObjectUtil.getCellValue(third);
				} catch (NumberFormatException e) {
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "书籍版次格式错误，请按照模板格式修改后" + "再上传");
				}
				/* 无人申报的教材，如果与Excel文档相同的书籍，保留数据库里的数据，否则保存Excel文档里的书籍 */
				if (null == noPeople || noPeople.isEmpty()) {
					textbook.setSort(sort);
					textbook.setTextbookName(bookName);
					textbook.setTextbookRound(round);
					bookList.add(textbook);
				} else {
					textbook.setSort(sort);
					textbook.setTextbookName(bookName);
					textbook.setTextbookRound(round);
					for (Textbook book : noPeople) {
						if (sort.intValue() == book.getSort() && round.intValue() == book.getTextbookRound()
								&& bookName.trim().equals(book.getTextbookName().trim())) {
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
	public List<Textbook> listTopicNumber(Long materialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"教材id不能为空");
		}
		List<Textbook> textbooksList = textbookDao.listTopicNumber(materialId);
		return textbooksList;
	}

	@Override
	public List<Textbook> addTopicNumber(String topicTextbooks) throws CheckedServiceException {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Textbook>>() {
		}.getType();
		List<Textbook> textbooks = gson.fromJson(topicTextbooks, type);
		if (CollectionUtil.isEmpty(textbooks)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"参数不能为空");
		}
		for (Textbook textbook : textbooks) {
			if (StringUtil.notEmpty(textbook.getTopicNumber()) && StringUtil.strLength(textbook.getTopicNumber()) > 30){
				throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
						CheckedExceptionResult.ILLEGAL_PARAM, "选题号不能超过30个字符");
			}
			textbookDao.updateTextbook(textbook);
		}
		return textbooks;
	}

	@SuppressWarnings("resource")
	@Override
	public List<Textbook> importTopicExcel(MultipartFile file) throws CheckedServiceException {
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
		}
		List<Textbook> textbookList = new ArrayList<>();
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (null == sheet) {
				continue;
			}
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Textbook textbook = new Textbook();
				Row row = sheet.getRow(rowNum);
				if (null == row) {
					break;
				}
				Cell first = row.getCell(0);
				Cell second = row.getCell(1);
				Cell third = row.getCell(2);
				Cell fourth = row.getCell(3);
				if ((ObjectUtil.isNull(first) || "".equals(first.toString()))
						&& (ObjectUtil.isNull(second) || "".equals(second.toString()))
						&& (ObjectUtil.isNull(third) || "".equals(third.toString()))) {
					break;
				}
				String bookName = StringUtil.getCellValue(second);
				if (StringUtil.strLength(bookName) > 25) {
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "图书名称不能超过25个字数，请修改后再上传");
				}
				String topicNumber = StringUtil.getCellValue(fourth);
				if (StringUtil.notEmpty(topicNumber)) {
					if (topicNumber.indexOf(".0") > -1) {
						topicNumber = topicNumber.substring(0, topicNumber.indexOf(".0"));
					}
				}
				if (StringUtil.strLength(topicNumber) > 30) {
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "选题号不能超过30个字数，请修改后再上传");
				}
				Integer sort = 0;
				Integer round = 0;
				try {
					sort = ObjectUtil.getCellValue(first);
				} catch (NumberFormatException e) {
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "图书序号格式错误，请按照模板格式修改后" + "再上传");
				}
				try {
					round = ObjectUtil.getCellValue(third);
				} catch (NumberFormatException e) {
					throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
							CheckedExceptionResult.ILLEGAL_PARAM, "书籍版次格式错误，请按照模板格式修改后" + "再上传");
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
		if (ObjectUtil.isNull(pageParameter.getParameter().getTextBookId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"参数不能为空");
		}
		PageResult<TextbookDecVO> pageResult = new PageResult<TextbookDecVO>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = textbookDao.getTextbookDecTotal(pageParameter);
		if (total > 0) {
			pageResult.setTotal(total);
			List<TextbookDecVO> list = textbookDao.getTextbookDecVOList(pageParameter);
			pageResult.setRows(list);
		}
		return pageResult;
	}

	@Override
	public List<ExcelDecAndTextbookVO> getExcelDecAndTextbooks(Long[] textbookIds) throws CheckedServiceException {
		if (null == textbookIds) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB, CheckedExceptionResult.NULL_PARAM,
					"书籍id为空");
		}
		List<ExcelDecAndTextbookVO> list = textbookDao.getExcelDecAndTextbooks(textbookIds);
		for (ExcelDecAndTextbookVO excelDecAndTextbookVO : list) {
			HashMap<String, Object> paraMap = new HashMap<>();
			paraMap.put("declarationId",excelDecAndTextbookVO.getDid());
			String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
			Date date1 = DateUtil.fomatDate(declarationlCreateDate);
			Date date = DateUtil.fomatDate("2019-04-12 12:00");
			String tit =excelDecAndTextbookVO.getTitle().toString();
			if(tit!=null){
				if(ObjectUtil.isNumber(tit)){
					tit = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.WRITER_USER_TITLE, tit);
				}
			}

			excelDecAndTextbookVO.setTitle(tit);

			if(date1.getTime()>date.getTime()) {
				String post =excelDecAndTextbookVO.getChosenPosition().toString();
				if(Integer.parseInt(post)==8){
					post="数字编委";
				}else{
					post = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.PMPH_POSITION, post);

				}
				excelDecAndTextbookVO.setShowChosenPosition(post);
			}else{

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
		if (null == materialId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB, CheckedExceptionResult.NULL_PARAM,
					"教材id为空");
		}
		List<Textbook> textbooks = textbookDao.getTextbookByMaterialId(materialId);
		return textbooks;
	}

	@Override
	public List<DecPositionBO> getExcelDecByMaterialId(Long[] textbookIds) {
		if (null == textbookIds) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB, CheckedExceptionResult.NULL_PARAM,
					"教材id为空");
		}
		List<DecPositionBO> list = textbookDao.getExcelDecByMaterialId2(textbookIds);

		return list;
	}

	@Override
	public Integer updatRevisionTimesByTextBookId(Integer number, Long textBookId) throws CheckedServiceException {
		if (ObjectUtil.isNull(number)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"参数为空");

		}
		if (ObjectUtil.isNull(textBookId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍id为空");

		}
		return textbookDao.updatRevisionTimesByTextBookId(number, textBookId);
	}

	@Override
	public List<Map<String,Object>> listBookPositionIds(Integer pageNumber, Integer pageSize, Integer state, String textBookIds, String bookName, Long materialId, String sessionId) {
		// 验证用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		Material material = materialService.getMaterialById(materialId);
		Integer power = getPower(materialId, pmphUser, material);
		// 拼装复合参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("materialId", materialId); // 教材id

		if (null != state && !state.equals(0)) {
			map.put("state", state); // 书籍状态
		}
		String bookNameTemp = StringUtil.toAllCheck(bookName);
		if (null != bookNameTemp) {
			map.put("bookName", bookNameTemp); // 书籍名称
		}
		map.put("pmphUserId", pmphUser.getId()); // 用户id
		PageParameter<Map<String, Object>> pageParameter = new PageParameter<Map<String, Object>>(pageNumber, pageSize,
				map);
		List<Map<String,Object>> rows = textbookDao.listBookPosition_up1_ids(pageParameter);
		// 下面进行授权
		for (Map<String,Object> row : rows) {
			String rowpower = "000000";
			if (power == 1 || power == 2) { // 管理员或者主任
				rowpower = "11111111";
			} else if (power == 3) { // 教材项目编辑
				// 因为项目编辑的权限不是全部 ，因此要检查我是不是这本书的策划编辑，如果是 ，这本书我的权利就是项目编辑+策划编辑的权利
				Integer tempProjectPermission = material.getProjectPermission();
				if (null != row && null != row.get("planningEditor") && null != pmphUser.getId()
						&& ((Long)(row.get("planningEditor"))).intValue() == pmphUser.getId().intValue()) { // 我又是策划编辑
					tempProjectPermission = (tempProjectPermission | material.getPlanPermission());
				}
				rowpower = StringUtil.tentToBinary(tempProjectPermission);
			} else if (power == 4) { // 教材策划编辑
				rowpower = StringUtil.tentToBinary(material.getPlanPermission());
			}
			// 把权限拿出来一个个判断
			// 分配策划编辑的权限
			String s1 = rowpower.substring(0, 1);
			String s2 = rowpower.substring(1, 2);
			String s3 = rowpower.substring(2, 3);
			String s4 = rowpower.substring(3, 4);
			String s5 = rowpower.substring(4, 5);
			String s6 = rowpower.substring(5, 6);
			String s7 = rowpower.substring(6, 7);
			String s8 = rowpower.substring(7, 8);// qiangzhijiesu
			if (material.getIsForceEnd() || material.getIsAllTextbookPublished()) { // 教材结束或者强制结束
				s2 = "0";
				s3 = "0";
				s4 = "0";
				s5 = "0";
				s6 = "0";
			} else if ((boolean)row.get("isLocked") || (boolean)row.get("isPublished")) { // 书籍已经发布了或者确认了名单
				s2 = (power == 1 || power == 2) ? s2 : "0";
				s3 = (power == 1 || power == 2) ? s3 : "0";
				s4 = (power == 1 || power == 2) ? s4 : "0";
				s5 = "0";
			}
			rowpower = s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8;
			row.put("myPower",rowpower);
		}

		return rows;
	}

}
