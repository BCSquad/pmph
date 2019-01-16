package com.bc.pmpheep.back.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.vo.*;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bc.pmpheep.back.dao.TopicDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.erp.db.SqlHelper;
import com.bc.pmpheep.erp.service.InfoWorking;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 功能描述：选题申报业务实现层
 *
 * @author (作者) 曾庆峰
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月20日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 */
@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	TopicLogService topicLogService;
	@Autowired
	TopicDao topicDao;
	@Autowired
	TopicExtraService topicExtraService;
	@Autowired
	TopicWriertService topicWriertService;
	@Autowired
	WriterUserTrendstService writerUserTrendstService;
	@Autowired
	TopicSimilarBookService topicSimilarBookService;

	@Autowired
	WXQYUserService service;
	@Autowired
	WxSendMessageService wxSendMessageService;

	@Autowired
	private PmphDepartmentService    pmphDepartmentService;

	@Autowired
	private PmphUserService          pmphUserService;

	@Override
	public PageResult<TopicOPtsManagerVO> listOpts(String sessionId, PageParameter<TopicOPtsManagerVO> pageParameter)
			throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"用户为空！");
		}
		PageResult<TopicOPtsManagerVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = 0;
		String submitTime1 = DateUtil.date2Str(pageParameter.getParameter().getSubmitTime1(), "yyyy-MM-dd");
		String submitTime2 = DateUtil.date2Str(pageParameter.getParameter().getSubmitTime2(), "yyyy-MM-dd");
		if (pmphUser.getIsAdmin()) {
			total = topicDao.listTotal(pageParameter.getParameter().getBookname(), submitTime1,submitTime2);
			if (total > 0) {
				List<TopicOPtsManagerVO> list = topicDao.list(pageParameter.getParameter().getBookname(), submitTime1,submitTime2,
						pageParameter.getStart(), pageParameter.getPageSize());
				list = addTypeName(list);
				pageResult.setRows(list);
			}
		} else {
			total = topicDao.listOptsTotal(pmphUser.getId(), pageParameter.getParameter().getBookname(), submitTime1,submitTime2);
			if (total > 0) {
				List<TopicOPtsManagerVO> list = topicDao.listOpts(pmphUser.getId(),
						pageParameter.getParameter().getBookname(), submitTime1,submitTime2, pageParameter.getStart(),
						pageParameter.getPageSize());
				list = addTypeName(list);
				pageResult.setRows(list);
			}
		}

		pageResult.setTotal(total);
		return pageResult;
	}

	/**
	 * 功能描述：向VO中添加图书类别名称
	 *0=专著/1=基础理论/2=教材/3=论文集/4=图谱/5=科普/6=应用技术/7=教辅/8=工具书/9=其他
	 * @param list
	 * @return
	 */
	public List<TopicOPtsManagerVO> addTypeName(List<TopicOPtsManagerVO> list) {
		for (TopicOPtsManagerVO vo : list) {
			switch (vo.getType()) {
			case 0:
				vo.setTypeName("专著");
				break;
			case 1:
				vo.setTypeName("基础理论");
				break;
			case 2:
				vo.setTypeName("教材");
				break;
			case 3:
				vo.setTypeName("论文集");
				break;
			case 4:
				vo.setTypeName("图谱");
				break;
			case 5:
				vo.setTypeName("科普");
				break;
			case 6:
				vo.setTypeName("应用技术");
				break;
			case 7:
				vo.setTypeName("教辅");
				break;
			case 8:
				vo.setTypeName("工具书");
				break;
			case 9:
				vo.setTypeName("其他");
				break;

			default:
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
						"没有这个图书类别");
			}
		}
		return list;
	}

	/**
	 * 功能描述：向VO中添加图书类别名称
	 *
	 * @param list
	 * @return
	 */
	public List<TopicDirectorVO> addTypeNameDirector(List<TopicDirectorVO> list) {
		for (TopicDirectorVO vo : list) {
			switch (vo.getType()) {
			case 0:
				vo.setTypeName("专著");
				break;
			case 1:
				vo.setTypeName("基础理论");
				break;
			case 2:
				vo.setTypeName("教材");
				break;
			case 3:
				vo.setTypeName("论文集");
				break;
			case 4:
				vo.setTypeName("图谱");
				break;
			case 5:
				vo.setTypeName("科普");
				break;
			case 6:
				vo.setTypeName("应用技术");
				break;
			case 7:
				vo.setTypeName("教辅");
				break;
			case 8:
				vo.setTypeName("工具书");
				break;
			case 9:
				vo.setTypeName("其他");
				break;

			default:
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
						"没有这个图书类别");
			}
		}
		return list;
	}

	/**
	 * 功能描述：向VO中添加图书类别名称
	 *
	 * @param list
	 * @return
	 */
	public List<TopicEditorVO> addTypeNameEditor(List<TopicEditorVO> list) {
		for (TopicEditorVO vo : list) {
			switch (vo.getType()) {
			case 0:
				vo.setTypeName("专著");
				break;
			case 1:
				vo.setTypeName("基础理论");
				break;
			case 2:
				vo.setTypeName("教材");
				break;
			case 3:
				vo.setTypeName("论文集");
				break;
			case 4:
				vo.setTypeName("图谱");
				break;
			case 5:
				vo.setTypeName("科普");
				break;
			case 6:
				vo.setTypeName("应用技术");
				break;
			case 7:
				vo.setTypeName("教辅");
				break;
			case 8:
				vo.setTypeName("工具书");
				break;
			case 9:
				vo.setTypeName("其他");
				break;

			default:
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
						"没有这个图书类别");
			}
		}
		return list;
	}

	@Override
	public String update(TopicLog topicLog, String sessionId, Topic topic,String openid,Long adminId) throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"用户为空！");
		}
			topicLog.setUserId(pmphUser.getId());
		if (ObjectUtil.isNull(topic) || ObjectUtil.isNull(topic.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"该选题不存在");
		}
		if (!StringUtil.isEmpty(topic.getAuthFeedback())) {
			if (topic.getAuthFeedback().length() > 200) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
						"审核意见过长，请不要超过200个字符");
			}
		}

		if (!StringUtil.isEmpty(topic.getReasonDirector())) {
			if (topic.getReasonDirector().length() > 200) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
						"退回原因过长，请不要超过200个字符");
			}
		}
		if (!StringUtil.isEmpty(topic.getReasonEditor())) {
			if (topic.getReasonEditor().length() > 200) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
						"退回原因过长，请不要超过200个字符");
			}
		}
		String result = "FIAL";
		List<Long> useridList =new ArrayList<Long>();
		useridList.add(adminId);

		if (topicDao.update(topic) > 0) {
			topicLogService.add(topicLog);
			String content = "《"+topic.getBookname()+"》选题"+(topic.getIsDirectorHandling()!=null&&topic.getIsDirectorHandling()?"已分配到您的部门":"已分配给您");
			//分配部门负责人 不为空 给其发企业微信消息
			if(!StringUtil.isEmpty(openid)){
				service.sendTextMessage("2","3",openid,null,null,"text",content,(short)0,"&"+openid);
			}
			if (adminId!=null){
				wxSendMessageService.batchInsertWxMessage(content,0,useridList,"2","3","");
			}
			result = "SUCCESS";
		}
		WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
		writerUserTrendst.setUserId(topicDao.getTopicTextVO(topic.getId()).getUserId());
		writerUserTrendst.setBookId(topic.getId());
		writerUserTrendst.setType(9);
		writerUserTrendst.setIsPublic(false);
		if (ObjectUtil.notNull(topic.getAuthProgress())) {
			if (StringUtil.isEmpty(topic.getAuthFeedback())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
						"审核意见不能为空");
			}
			if (3 == topic.getAuthProgress()) {
				Map<String, Object> detail = new HashMap<String, Object>();
				detail.put("title", CheckedExceptionBusiness.TOPIC);
				detail.put("content", "您的选题《" + topicDao.get(topic.getId()).getBookname() + "》已经通过。");
				detail.put("img", 1);
				writerUserTrendst.setDetail(new Gson().toJson(detail));
				// 创建本版号并将本版号放入数据中
				// String editionnum = "10" + new SimpleDateFormat("yyyy").format(new Date());
				// String vn = topicDao.get(topic.getId()).getVn();
				// if (StringUtil.isEmpty(vn)) {
				// vn = topicDao.getMaxTopicVn();
				// if (StringUtil.isEmpty(vn)) {
				// vn = editionnum + "000001";
				// } else {
				// vn = editionnum + Integer.parseInt(vn.substring(7)) + 1000000 + 1 + "";
				// }
				// }
				// topic.setNote("选题通过");
				// topic.setVn(vn);
				// topicDao.update(topic);
				// String remark = topic.getAuthFeedback();
				// TopicTextVO topicTextVO = topicTextVO(topicLog, sessionId, topic.getId());
				// String sql = "insert into i_declarestates
				// (editionnum,rwusercode,rwusername,topicname,readerpeople,sources,fontcount,piccount,timetohand,subject,booktype,levels,depositbank,bankaccount,selectreason,publishingvalue,content,authorbuybooks,authorsponsor,originalname,originalauthor,originalnationality,originalpress,publishagerevision,topicnumber,auditstates,remark,creattime,states)";
				// sql += "values('" + vn + "','" + topicTextVO.getUsername() + "','" +
				// topicTextVO.getRealname()
				// + "','','" + topicTextVO.getReadType() + "','" + topicTextVO.getSourceType()
				// + "','"
				// + topicTextVO.getWordNumber() + "','" + topicTextVO.getPictureNumber() +
				// "','"
				// + DateUtil.formatTimeStamp("yyyy-MM-dd", topicTextVO.getDeadline()) + "','"
				// + topicTextVO.getSubject() + "','" + topicTextVO.getTypeName() + "','" +
				// topicTextVO.getRank()
				// + "','" + topicTextVO.getBank() + "','" + topicTextVO.getAccountNumber() +
				// "','"
				// + topicTextVO.getTopicExtra().getReason() + "','" +
				// topicTextVO.getTopicExtra().getPrice()
				// + "','" + topicTextVO.getTopicExtra().getScore() + "','" +
				// topicTextVO.getPurchase() + "','"
				// + topicTextVO.getSponsorship() + "','" + topicTextVO.getOriginalBookname() +
				// "','"
				// + topicTextVO.getOriginalAuthor() + "','" + topicTextVO.getNation() +
				// "','','"
				// + topicTextVO.getEdition() + "','','11','" + remark + "',GETDATE(),1)";
				// SqlHelper.executeUpdate(sql, null);
			} else {
				Map<String, Object> detail = new HashMap<String, Object>();
				detail.put("title", CheckedExceptionBusiness.TOPIC);
				detail.put("content", "您的选题《" + topicDao.get(topic.getId()).getBookname() + "》未通过。");
				detail.put("img", 2);
				writerUserTrendst.setDetail(new Gson().toJson(detail));
			}
			writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
		}
		return result;
	}

	@Override
	public TopicTextVO topicTextVO(TopicLog topicLog, String sessionId, Long id) throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"用户为空！");
		}

		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"选题申报的id为空！");
		}
		if(ObjectUtil.notNull(topicLog)){
			topicLog.setUserId(pmphUser.getId());
			topicLogService.add(topicLog);
		}
		TopicTextVO topicTextVO = topicDao.getTopicTextVO(id);
		topicTextVO.setTopicExtra(topicExtraService.getTopicExtraByTopicId(id));
		topicTextVO.setTopicWriters(topicWriertService.listTopicWriterByTopicId(id));
		topicTextVO.setTopicSimilarBooks(topicSimilarBookService.listTopicSimilarBookByTopicId(id));
		Integer pp = topicTextVO.getPositionProfession();
		Integer degree = topicTextVO.getDegree();
		if (ObjectUtil.notNull(degree)) {
			switch (degree) {
			case 0:
				topicTextVO.setDegreeName(new String[]{"博士"});
				break;
			case 1:
				topicTextVO.setDegreeName(new String[]{"硕士"});
				break;
			case 2:
				topicTextVO.setDegreeName(new String[]{"学士"});
				break;
			case 3:
				topicTextVO.setDegreeName(new String[]{"其他"});
				break;

			default:
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
						"没有这个文凭");
			}
		}
		if (ObjectUtil.notNull(pp)) {
			switch (pp) {
			case 0:
				topicTextVO.setPositionProfessionName(new String[]{"中科院院士"});
				break;
			case 1:
				topicTextVO.setPositionProfessionName(new String[]{"工程院院士"});
				break;
			case 2:
				topicTextVO.setPositionProfessionName(new String[]{"博导"});
				break;
			case 3:
				topicTextVO.setPositionProfessionName(new String[]{"硕导"});
				break;
			case 4:
				topicTextVO.setPositionProfessionName(new String[]{"正高"});
				break;
			case 5:
				topicTextVO.setPositionProfessionName(new String[]{"副高"});
				break;
			case 6:
				topicTextVO.setPositionProfessionName(new String[]{"中级"});
				break;
			case 7:
				topicTextVO.setPositionProfessionName(new String[]{"其他"});
				break;

			default:
				throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
						"没有这个职务");
			}
		}
		if (ObjectUtil.notNull(topicTextVO.getRank())) {
			switch (topicTextVO.getRank()) {
				case 0:
					topicTextVO.setRankType(new String[]{"低"});
					break;
				case 1:
					topicTextVO.setRankType(new String[]{"中"});
					break;
				case 2:
					topicTextVO.setRankType(new String[]{"高"});
					break;

				default:
					throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
							"没有这个级别");
			}
		}
		if (ObjectUtil.notNull(topicTextVO.getSource())) {
			switch (topicTextVO.getSource()) {
				case 0:
					topicTextVO.setSourceType(new String[]{"社策划"});
					break;
				case 1:
					topicTextVO.setSourceType(new String[]{"编辑策划"});
					break;
				case 2:
					topicTextVO.setSourceType(new String[]{"修订"});
					break;
				case 4:
					topicTextVO.setSourceType(new String[]{"专家策划"});
					break;
				case 3:
					topicTextVO.setSourceType(new String[]{"离退休编审策划"});
					break;
				case 5:
					topicTextVO.setSourceType(new String[]{"上级交办"});
					break;
				case 6:
					topicTextVO.setSourceType(new String[]{"作者投稿"});
					break;

				default:
					throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
							"没有这个选题来源");
			}
		}
		if (ObjectUtil.notNull(topicTextVO.getReader())) {
			switch (topicTextVO.getReader()) {
				case 0:
					topicTextVO.setReadType("医务工作者");
					break;
				case 1:
					topicTextVO.setReadType("医学院校师生");
					break;
				case 2:
					topicTextVO.setReadType("大众");
					break;

				default:
					throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
							"没有这个阅读人群");
			}
		}
		if (ObjectUtil.notNull(topicTextVO.getType())) {
			switch (topicTextVO.getType()) {
				case 0:
					topicTextVO.setTypeName(new String[]{"专著"});
					break;
				case 1:
					topicTextVO.setTypeName(new String[]{"基础理论"});
					break;
				case 3:
					topicTextVO.setTypeName(new String[]{"论文集"});
					break;
				case 2:
					topicTextVO.setTypeName(new String[]{"教材"});
					break;
				case 4:
					topicTextVO.setTypeName(new String[]{"图谱"});
					break;
				case 5:
					topicTextVO.setTypeName(new String[]{"科普"});
					break;
				case 7:
					topicTextVO.setTypeName(new String[]{"教辅"});
					break;
				case 6:
					topicTextVO.setTypeName(new String[]{"应用技术"});
					break;
				case 8:
					topicTextVO.setTypeName(new String[]{"工具书"});
					break;
				case 9:
					topicTextVO.setTypeName(new String[]{"其他"});
					break;

				default:
					throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
							"没有这个图书类别");
			}
		}
		return topicTextVO;
	}

	@Override
	public PageResult<TopicDeclarationVO> listCheckTopic(List<Long> authProgress,
			PageParameter<TopicDeclarationVO> pageParameter,String sessionId) throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (CollectionUtil.isEmpty(authProgress)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
					"选题申报状态不对");
		}
		PageResult<TopicDeclarationVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		String submitTime1 = DateUtil.date2Str(pageParameter.getParameter().getSubmitTime1(), "yyyy-MM-dd");
		String submitTime2 = DateUtil.date2Str(pageParameter.getParameter().getSubmitTime2(), "yyyy-MM-dd");


		List<Long> editor_id_list = null;
		Long departmentId = null;

		if(pmphUser.getIsAdmin()){ //登录人是管理员
			//do nothing ,for now
		}else if(pmphUser.getIsDirector() && pmphUser.getDepartmentId()==1l){ // 登录人是人卫社部门主任 即最上级主任

		}else if(pmphUser.getIsDirector()){ //登录人是部门主任
			departmentId = pmphUser.getDepartmentId(); // 部门主任传入其部门id 可见分配到其部门的申报
			PmphDepartment pmphDepartment =
					pmphDepartmentService.getPmphDepartmentById(pmphUser.getDepartmentId());
			PageParameter<PmphUserManagerVO> parameter = new PageParameter<>(1, 10000);
			PmphUserManagerVO pmphUserManagerVO = new PmphUserManagerVO();
			pmphUserManagerVO.setPath(pmphDepartment.getPath());
			pmphUserManagerVO.setDepartmentId(pmphDepartment.getId());
			parameter.setParameter(pmphUserManagerVO);
			PageResult<PmphUserManagerVO> listPageResult =
					pmphUserService.getListPmphUser(parameter, null);
			List<PmphUserManagerVO> listPmphUserManagerVOs = listPageResult.getRows();
			if (listPmphUserManagerVOs.size()>0){
				editor_id_list = new ArrayList<Long>();
			}
			for (PmphUserManagerVO pmManagerVO : listPmphUserManagerVOs) {
				editor_id_list.add(pmManagerVO.getId()); //部门主任传入其属成员id 可见分配给 本人及下级各迭代层级部门成员的申报
			}
		}else{ // 普通成员
			editor_id_list = new ArrayList<Long>();
			editor_id_list.add(pmphUser.getId()); //编辑本人传入本人id可见分配给 本人的申报
		}

		Integer total = topicDao.listCheckTopicTotal(authProgress, pageParameter.getParameter().getBookname(),
				submitTime1,submitTime2,editor_id_list,departmentId);
		if (total > 0) {
			List<TopicDeclarationVO> list = topicDao.listCheckTopic(authProgress, pageParameter.getPageSize(),
					pageParameter.getStart(), pageParameter.getParameter().getBookname(), submitTime1,submitTime2,editor_id_list,departmentId);
			list = addState(list);
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	/**
	 * 功能描述：向查看选题申报中插入状态详情
	 *
	 * @param list
	 * @return
	 */
	public List<TopicDeclarationVO> addState(List<TopicDeclarationVO> list) {
		for (TopicDeclarationVO vo : list) {
			if (1 == vo.getAuthProgress()) {
				if (vo.getIsOptsHandling()) {
					vo.setState("作者已提交");
					vo.setStateDeail("待管理员分配");
					if (vo.getIsDirectorHandling()) {
						vo.setState("主任已受理");
						vo.setStateDeail("待主任分配");
						if (vo.getIsEditorHandling()) {
							vo.setState("主任已分配");
							vo.setStateDeail("待编辑受理");
							if (vo.getIsAccepted()) {
								vo.setState("编辑已受理");
								vo.setStateDeail("待编辑处理");
							}
						}
					}
				}
			} else {
				if (2 == vo.getAuthProgress()) {
					vo.setState("不通过");
				}
				if (3 == vo.getAuthProgress()) {
					vo.setState("通过");
				}
			}
		}
		return list;
	}

	public PageResult<TopicDirectorVO> listTopicDirectorVOs(String sessionId,
			PageParameter<TopicDirectorVO> pageParameter) throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		PageResult<TopicDirectorVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = 0;
		String submitTime1 = DateUtil.date2Str(pageParameter.getParameter().getSubmitTime1(), "yyyy-MM-dd");
		String submitTime2 = DateUtil.date2Str(pageParameter.getParameter().getSubmitTime2(), "yyyy-MM-dd");
		if (pmphUser.getIsAdmin()) {
			total = topicDao.totalDirectorView(pageParameter.getParameter().getBookname(), submitTime1,submitTime2);
			if (total > 0) {
				List<TopicDirectorVO> list = topicDao.listDirectorView(pageParameter.getParameter().getBookname(),
						submitTime1,submitTime2, pageParameter.getStart(), pageParameter.getPageSize());
				list = addTypeNameDirector(list);
				pageResult.setRows(list);
			}
		} else {
			total = topicDao.totalTopicDirectorVOs(pmphUser.getId(), pageParameter.getParameter().getBookname(),
					submitTime1,submitTime2);
			if (total > 0) {
				List<TopicDirectorVO> list = topicDao.listTopicDirectorVOs(pmphUser.getId(),
						pageParameter.getParameter().getBookname(), submitTime1,submitTime2, pageParameter.getStart(),
						pageParameter.getPageSize());
				list = addTypeNameDirector(list);
				pageResult.setRows(list);
			}
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public PageResult<TopicEditorVO> listTopicEditorVOs(String sessionId, PageParameter<TopicEditorVO> pageParameter)
			throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		PageResult<TopicEditorVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = 0;
		String submitTime1 = DateUtil.date2Str(pageParameter.getParameter().getSubmitTime1(), "yyyy-MM-dd");
		String submitTime2 = DateUtil.date2Str(pageParameter.getParameter().getSubmitTime2(), "yyyy-MM-dd");
		if (pmphUser.getIsAdmin()) {
			total = topicDao.totalEditorView(pageParameter.getParameter().getBookname(), submitTime1,submitTime2);
			if (total > 0) {
				List<TopicEditorVO> list = topicDao.listEditorView(pageParameter.getParameter().getBookname(),
						submitTime1,submitTime2, pageParameter.getStart(), pageParameter.getPageSize());
				list = addTypeNameEditor(list);
				pageResult.setRows(list);
			}
		} else {
			total = topicDao.totalTopicEditorVOs(pmphUser.getId(), pageParameter.getParameter().getBookname(),
					submitTime1,submitTime2);
			if (total > 0) {
				List<TopicEditorVO> list = topicDao.listTopicEditorVOs(pmphUser.getId(),
						pageParameter.getParameter().getBookname(), submitTime1,submitTime2, pageParameter.getStart(),
						pageParameter.getPageSize());
				list = addTypeNameEditor(list);
				pageResult.setRows(list);
			}
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public Topic add(Topic topic) throws CheckedServiceException {
		if (ObjectUtil.isNull(topic)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"教材申报为空");
		}
		topicDao.add(topic);
		return topic;
	}

	@Override
	public void updateByErp() {
		InfoWorking bookInfoWorking = new InfoWorking();
		JSONArray topicInfo = bookInfoWorking.listTopicInfo();
		Long[] recordIds = new Long[topicInfo.size()];
		Integer total = 0;
		for (int i = 0; i < topicInfo.size(); i++) {
			JSONObject job = topicInfo.getJSONObject(i);
			String auditstates = job.getString("auditstates");
			String topicnumber = job.getString("topicnumber");
			String topicname = job.getString("topicname");
			String vn = job.getString("editionnum");
			recordIds[i] = job.getLong("record_id");
			switch (auditstates) {
			case "11":
				auditstates = "选题通过";
				break;
			case "12":
				auditstates = "转稿";
				break;
			case "13":
				auditstates = "发稿";
				break;
			case "14":
				auditstates = "即将出书";
				break;

			default:
				auditstates = "状态出现错误";
				break;
			}
			Topic topic = new Topic();
			topic.setNote(auditstates);
			topic.setTn(topicnumber);
			topic.setBookname(topicname);
			topic.setVn(vn);
			total += topicDao.updateByVn(topic);
		}
		if (total == topicInfo.size()) {
			bookInfoWorking.updateTopic(recordIds);
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"从erp更新教材申报信息失败请查看原因");
		}

	}

	@Override
	public PageResult<TopicDeclarationVO> listMyTopic(List<Long> authProgress,
			PageParameter<TopicDeclarationVO> pageParameter, Long editorId) {
		if (CollectionUtil.isEmpty(authProgress)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
					"选题申报状态不对");
		}
		PageResult<TopicDeclarationVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = topicDao.listMyTopicTotal(authProgress, pageParameter.getParameter().getBookname(),
				pageParameter.getParameter().getSubmitTime(), pageParameter.getParameter().getIsDirectorHandling(),
				pageParameter.getParameter().getIsEditorHandling(), pageParameter.getParameter().getIsOptsHandling(),
				editorId);
		if (total > 0) {
			List<TopicDeclarationVO> list = topicDao.listMyTopic(authProgress, pageParameter.getPageSize(),
					pageParameter.getStart(), pageParameter.getParameter().getBookname(),
					pageParameter.getParameter().getSubmitTime(), pageParameter.getParameter().getIsDirectorHandling(),
					pageParameter.getParameter().getIsEditorHandling(),
					pageParameter.getParameter().getIsOptsHandling(), editorId);
			list = addState(list);
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public PageResult<TopicDirectorVO> listIsDirectorTopic(Long userId,PageParameter<TopicDirectorVO> pageParameter) throws CheckedServiceException {
		PageResult<TopicDirectorVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = topicDao.listIsDirectorTopicTotal(userId);
		if(total>0){
			List<TopicDirectorVO> list=topicDao.listIsDirectorTopic(userId, pageParameter.getPageSize(),
					pageParameter.getStart());
			list = addTypeNameDirector(list);
			list = addStateTopicDirectorVO(list);
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}
	public List<TopicDirectorVO> addStateTopicDirectorVO(List<TopicDirectorVO> list) {
		for (TopicDirectorVO vo : list) {
			if (1 == vo.getAuthProgress()) {
				if (vo.getIsOptsHandling()) {
					vo.setState("作者已提交");
					vo.setStateDeail("待管理员分配");
					if (vo.getIsDirectorHandling()) {
						vo.setState("主任已受理");
						vo.setStateDeail("待主任分配");
						if (vo.getIsEditorHandling()) {
							vo.setState("主任已分配");
							vo.setStateDeail("待编辑受理");
							if (vo.getIsAccepted()) {
								vo.setState("编辑已受理");
								vo.setStateDeail("待编辑处理");
							}
						}
					}
				}
			} else {
				if (2 == vo.getAuthProgress()) {
					vo.setState("不通过");
				}
				if (3 == vo.getAuthProgress()) {
					vo.setState("通过");
				}
			}
		}
		return list;
	}

	@Override
	public PageResult<TopicOPtsManagerVO> listIsOptsTopic(Long userId,PageParameter<TopicOPtsManagerVO> pageParameter) throws CheckedServiceException {
		PageResult<TopicOPtsManagerVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = topicDao.listIsOptsTopicTotal(userId);
		if(total>0){
			List<TopicOPtsManagerVO> list=topicDao.listIsOptsTopic(userId, pageParameter.getPageSize(),
					pageParameter.getStart());
			list = addTypeName(list);
			list = addStateTopicOPtsManagerVO(list);
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	public List<TopicOPtsManagerVO> addStateTopicOPtsManagerVO(List<TopicOPtsManagerVO> list) {
		for (TopicOPtsManagerVO vo : list) {
			if (1 == vo.getAuthProgress()) {
				if (vo.getIsOptsHandling()) {
					vo.setState("作者已提交");
					vo.setStateDeail("待管理员分配");
					if (vo.getIsDirectorHandling()) {
						vo.setState("主任已受理");
						vo.setStateDeail("待主任分配");
						if (vo.getIsEditorHandling()) {
							vo.setState("主任已分配");
							vo.setStateDeail("待编辑受理");
							if (vo.getIsAccepted()) {
								vo.setState("编辑已受理");
								vo.setStateDeail("待编辑处理");
							}
						}
					}
				}
			} else {
				if (2 == vo.getAuthProgress()) {
					vo.setState("不通过");
				}
				if (3 == vo.getAuthProgress()) {
					vo.setState("通过");
				}
			}
		}
		return list;
	}

	@Override
	public PageResult<TopicEditorVO> listIsEditor(Long userId,PageParameter<TopicEditorVO> pageParameter) throws CheckedServiceException {
		PageResult<TopicEditorVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = topicDao.listIsEditorTotal(userId);
		if(total>0){
			List<TopicEditorVO> list=topicDao.listIsEditor(userId,pageParameter.getPageSize(),
					pageParameter.getStart());
			list = addTypeNameEditor(list);
			list = addStateTopicEditorVOO(list);
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	public List<TopicEditorVO> addStateTopicEditorVOO(List<TopicEditorVO> list) {
		for (TopicEditorVO vo : list) {
			if (1 == vo.getAuthProgress()) {
				if (vo.getIsOptsHandling()) {
					vo.setState("作者已提交");
					vo.setStateDeail("待管理员分配");
					if (vo.getIsDirectorHandling()) {
						vo.setState("主任已受理");
						vo.setStateDeail("待主任分配");
						if (vo.getIsEditorHandling()) {
							vo.setState("主任已分配");
							vo.setStateDeail("待编辑受理");
							if (vo.getIsAccepted()) {
								vo.setState("编辑已受理");
								vo.setStateDeail("待编辑处理");
							}
						}
					}
				}
			} else {
				if (2 == vo.getAuthProgress()) {
					vo.setState("不通过");
				}
				if (3 == vo.getAuthProgress()) {
					vo.setState("通过");
				}
			}
		}
		return list;
	}
}
