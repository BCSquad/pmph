package com.bc.pmpheep.back.service;
/**
 * 
 * 
 * 功能描述：选题申报业务层
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

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.TopicOPtsManagerVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

public interface TopicService {
	/**
	 * 
	 * 
	 * 功能描述：初始化/查询可以操作的选题申报
	 *
	 * @param sessionId
	 * @param pageParameter
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	PageResult<TopicOPtsManagerVO> listOpts(String sessionId, PageParameter<TopicOPtsManagerVO> pageParameter)
			throws CheckedServiceException;

}
