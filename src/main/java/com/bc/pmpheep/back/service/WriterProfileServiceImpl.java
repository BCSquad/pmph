/**
 * 
 */
package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterProfileDao;
import com.bc.pmpheep.back.po.WriterProfile;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:WriterProfileServiceImpl 作家个人简介及标签信息业务层接口实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月24日 上午11:23:17
 */
@Service
public class WriterProfileServiceImpl implements WriterProfileService {

	@Autowired
	private WriterProfileDao writerProfileDao;

	@Override
	public WriterProfile addWriterProfile(WriterProfile writerProfile)
			throws CheckedServiceException {
		if (null == writerProfile.getUserId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "作家id为空");
		}
		Long id = writerProfile.getId();
		writerProfileDao.addWriterProfile(writerProfile);
		if (null != id) {
			writerProfile.setId(id);
		}
		return writerProfile;
	}

	@Override
	public Integer deleteWriterProfileById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return writerProfileDao.deleteWriterProfileById(id);
	}

	@Override
	public Integer updateWriterProfile(WriterProfile writerProfile)
			throws CheckedServiceException {
		if (null == writerProfile.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return writerProfileDao.updateWriterProfile(writerProfile);
	}

	@Override
	public WriterProfile getWriterProfileById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return writerProfileDao.getWriterProfileById(id);
	}
}
