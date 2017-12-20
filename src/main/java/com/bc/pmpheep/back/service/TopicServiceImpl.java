package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.TopicDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.TopicOPtsManagerVO;
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
				pageResult.setRows(list);
			}
		} else {
			total = topicDao.listOptsTotal(pmphUser.getId(), pageParameter.getParameter().getBookname(),
					pageParameter.getParameter().getSubmitTime());
			if (total > 0) {
				List<TopicOPtsManagerVO> list = topicDao.listOpts(pmphUser.getId(),
						pageParameter.getParameter().getBookname(), pageParameter.getParameter().getSubmitTime(),
						pageParameter.getStart(), pageParameter.getPageSize());
				pageResult.setRows(list);
			}
		}

		pageResult.setTotal(total);
		return pageResult;
	}

}
