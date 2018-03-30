package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.TopicWriterDao;
import com.bc.pmpheep.back.po.TopicWriter;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：选题申报编者信息业务实现层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月21日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Service
public class TopicWriertServiceImpl implements TopicWriertService {
	@Autowired
	TopicWriterDao topicWriterDao;

	@Override
	public List<TopicWriter> listTopicWriterByTopicId(Long topicId) throws CheckedServiceException {
		if (ObjectUtil.isNull(topicId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"选题申报id为空！");
		}
		List<TopicWriter> list = topicWriterDao.listTopicWriterByTopicId(topicId);
		for (TopicWriter topicWriter : list) {
			Integer degree = topicWriter.getDegree();
			Integer sex = topicWriter.getSex();
			if (ObjectUtil.notNull(sex)){
				switch (sex) {
				case 0:
					topicWriter.setSexName(new String[]{"男"});
					break;
				case 1:
					topicWriter.setSexName(new String[]{"女"});
					break;
				default:
					throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC,
							CheckedExceptionResult.ILLEGAL_PARAM, "性别信息有错误");
				}
			}
			if (ObjectUtil.notNull(degree)) {
				switch (degree) {
				case 0:
					topicWriter.setDegreeName("博士");
					break;
				case 1:
					topicWriter.setDegreeName("硕士");
					break;
				case 2:
					topicWriter.setDegreeName("学士");
					break;
				case 3:
					topicWriter.setDegreeName("其他");
					break;

				default:
					throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
							"没有这个文凭");
				}
			}
		}
		return list;
	}

	@Override
	public TopicWriter add(TopicWriter topicWriter) throws CheckedServiceException {
		if (ObjectUtil.isNull(topicWriter)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"选题申报为空！");
		}
		topicWriterDao.add(topicWriter);
		return topicWriter;
	}

}
