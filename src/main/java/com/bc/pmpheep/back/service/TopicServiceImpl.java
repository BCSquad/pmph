package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.TopicDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Topic;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.TopicDeclarationVO;
import com.bc.pmpheep.back.vo.TopicDirectorVO;
import com.bc.pmpheep.back.vo.TopicEditorVO;
import com.bc.pmpheep.back.vo.TopicOPtsManagerVO;
import com.bc.pmpheep.back.vo.TopicTextVO;
import com.bc.pmpheep.erp.db.SqlHelper;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：选题申报业务实现层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月20日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	TopicDao topicDao;
	@Autowired
	TopicExtraService topicExtraService;
	@Autowired
	TopicWriertService topicWriertService;

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
		if (pmphUser.getIsAdmin()) {
			total = topicDao.listTotal(pageParameter.getParameter().getBookname(),
					pageParameter.getParameter().getSubmitTime());
			if (total > 0) {
				List<TopicOPtsManagerVO> list = topicDao.list(pageParameter.getParameter().getBookname(),
						pageParameter.getParameter().getSubmitTime(), pageParameter.getStart(),
						pageParameter.getPageSize());
				list = addTypeName(list);
				pageResult.setRows(list);
			}
		} else {
			total = topicDao.listOptsTotal(pmphUser.getId(), pageParameter.getParameter().getBookname(),
					pageParameter.getParameter().getSubmitTime());
			if (total > 0) {
				List<TopicOPtsManagerVO> list = topicDao.listOpts(pmphUser.getId(),
						pageParameter.getParameter().getBookname(), pageParameter.getParameter().getSubmitTime(),
						pageParameter.getStart(), pageParameter.getPageSize());
				list = addTypeName(list);
				pageResult.setRows(list);
			}
		}

		pageResult.setTotal(total);
		return pageResult;
	}

	/**
	 * 
	 * 
	 * 功能描述：向VO中添加图书类别名称
	 *
	 * @param list
	 * @return
	 *
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
				vo.setTypeName("论文集");
				break;
			case 3:
				vo.setTypeName("科普");
				break;
			case 4:
				vo.setTypeName("应用技术");
				break;
			case 5:
				vo.setTypeName("工具书");
				break;
			case 6:
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
	 * 
	 * 
	 * 功能描述：向VO中添加图书类别名称
	 *
	 * @param list
	 * @return
	 *
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
				vo.setTypeName("论文集");
				break;
			case 3:
				vo.setTypeName("科普");
				break;
			case 4:
				vo.setTypeName("应用技术");
				break;
			case 5:
				vo.setTypeName("工具书");
				break;
			case 6:
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
	 * 
	 * 
	 * 功能描述：向VO中添加图书类别名称
	 *
	 * @param list
	 * @return
	 *
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
				vo.setTypeName("论文集");
				break;
			case 3:
				vo.setTypeName("科普");
				break;
			case 4:
				vo.setTypeName("应用技术");
				break;
			case 5:
				vo.setTypeName("工具书");
				break;
			case 6:
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
	public String update(Topic topic) throws CheckedServiceException {
		if (ObjectUtil.isNull(topic) || ObjectUtil.isNull(topic.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"该选题不存在");
		}
		String result = "FIAL";
		if (topicDao.update(topic) > 0) {
			result = "SUCCESS";
		}
		if (3 == topic.getAuthProgress()) {
			// 审核通过可以将数据插入到erp的中间表去了
			String remark = topic.getAuthFeedback();
			TopicTextVO topicTextVO = getTopicTextVO(topic.getId());
			String sql = "insert into i_declarestates (editionnum,rwusercode,rwusername,topicname,readerpeople,sources,fontcount,piccount,timetohand,subject,booktype,levels,depositbank,bankaccount,selectreason,publishingvalue,content,authorbuybooks,authorsponsor,originalname,originalauthor,originalnationality,originalpress,publishagerevision,topicnumber,auditstates,remark,creattime,states)";
			sql += "values('','" + topicTextVO.getUsername() + "','" + topicTextVO.getRealname() + "','"
					+ topicTextVO.getBookname() + "','" + topicTextVO.getReadType() + "','"
					+ topicTextVO.getSourceType() + "','" + topicTextVO.getWordNumber() + "','"
					+ topicTextVO.getPictureNumber() + "','"
					+ DateUtil.formatTimeStamp("yyyy-MM-dd", topicTextVO.getDeadline()) + "','"
					+ topicTextVO.getSubject() + "','" + topicTextVO.getTypeName() + "','" + topicTextVO.getRank()
					+ "','" + topicTextVO.getBank() + "','" + topicTextVO.getAccount_number() + "','"
					+ topicTextVO.getTopicExtra().getReason() + "','" + topicTextVO.getTopicExtra().getPrice() + "','"
					+ topicTextVO.getTopicExtra().getScore() + "','" + topicTextVO.getPurchase() + "','"
					+ topicTextVO.getSponsorship() + "','" + topicTextVO.getOriginalBookname() + "','"
					+ topicTextVO.getOriginalAuthor() + "','" + topicTextVO.getNation() + "','','"
					+ topicTextVO.getEdition() + "','','','" + remark + "',GETDATE(),1)";
			SqlHelper.executeUpdate(sql, null);
		}
		return result;
	}

	@Override
	public TopicTextVO getTopicTextVO(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"选题申报的id为空！");
		}
		TopicTextVO topicTextVO = topicDao.getTopicTextVO(id);
		topicTextVO.setTopicExtra(topicExtraService.getTopicExtraByTopicId(id));
		topicTextVO.setTopicWriters(topicWriertService.listTopicWriterByTopicId(id));
		switch (topicTextVO.getRank()) {
		case 0:
			topicTextVO.setRankType("低");
			break;
		case 1:
			topicTextVO.setRankType("中");
			break;
		case 2:
			topicTextVO.setRankType("高");
			break;

		default:
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"没有这个级别");
		}
		switch (topicTextVO.getSource()) {
		case 0:
			topicTextVO.setSourceType("社策划");
			break;
		case 1:
			topicTextVO.setSourceType("编辑策划");
			break;

		case 2:
			topicTextVO.setSourceType("专家策划");
			break;
		case 3:
			topicTextVO.setSourceType("离退休编审策划");
			break;
		case 4:
			topicTextVO.setSourceType("上级交办");
			break;
		case 5:
			topicTextVO.setSourceType("作者投稿");
			break;

		default:
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"没有这个选题来源");
		}
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
		switch (topicTextVO.getType()) {
		case 0:
			topicTextVO.setTypeName("专著");
			break;
		case 1:
			topicTextVO.setTypeName("基础理论");
			break;
		case 2:
			topicTextVO.setTypeName("论文集");
			break;
		case 3:
			topicTextVO.setTypeName("科普");
			break;
		case 4:
			topicTextVO.setTypeName("应用技术");
			break;
		case 5:
			topicTextVO.setTypeName("工具书");
			break;
		case 6:
			topicTextVO.setTypeName("其他");
			break;

		default:
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
					"没有这个图书类别");
		}
		return topicTextVO;
	}

	@Override
	public PageResult<TopicDeclarationVO> listCheckTopic(List<Long> authProgress,
			PageParameter<TopicDeclarationVO> pageParameter) throws CheckedServiceException {
		if (CollectionUtil.isEmpty(authProgress)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.ILLEGAL_PARAM,
					"选题申报状态不对");
		}
		PageResult<TopicDeclarationVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = topicDao.listCheckTopicTotal(authProgress, pageParameter.getParameter().getBookname(),
				pageParameter.getParameter().getSubmitTime());
		if (total > 0) {
			List<TopicDeclarationVO> list = topicDao.listCheckTopic(authProgress, pageParameter.getPageSize(),
					pageParameter.getStart(), pageParameter.getParameter().getBookname(),
					pageParameter.getParameter().getSubmitTime());
			list = addState(list);
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	/**
	 * 
	 * 
	 * 功能描述：向查看选题申报中插入状态详情
	 *
	 * @param list
	 * @return
	 *
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
		int total = 0;
		if (pmphUser.getIsAdmin()) {
			total = topicDao.totalDirectorView(pageParameter.getParameter().getBookName(),
					pageParameter.getParameter().getSubmitTime());
			if (total > 0) {
				List<TopicDirectorVO> list = topicDao.listDirectorView(pageParameter.getParameter().getBookName(),
						pageParameter.getParameter().getSubmitTime(), pageParameter.getStart(),
						pageParameter.getPageSize());
				list = addTypeNameDirector(list);
				pageResult.setRows(list);
			}
		} else {
			total = topicDao.totalTopicDirectorVOs(pageParameter.getParameter().getDepartmentId(),
					pageParameter.getParameter().getBookName(), pageParameter.getParameter().getSubmitTime());
			if (total > 0) {
				List<TopicDirectorVO> list = topicDao.listTopicDirectorVOs(
						pageParameter.getParameter().getDepartmentId(), pageParameter.getParameter().getBookName(),
						pageParameter.getParameter().getSubmitTime(), pageParameter.getStart(),
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
		int total = 0;
		if (pmphUser.getIsAdmin()) {
			total = topicDao.totalEditorView(pageParameter.getParameter().getBookName(),
					pageParameter.getParameter().getSubmitTime());
		}
		return pageResult;
	}

}
