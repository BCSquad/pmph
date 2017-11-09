package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterUserCertificationDao;
import com.bc.pmpheep.back.po.WriterUserCertification;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CastUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * @introduction WriterUserCertificationService 实现
 * 
 * @author Mryang
 * 
 * @createDate 2017年9月19日 上午9:46:59
 * 
 */
@Service
public class WriterUserCertificationServiceImpl extends BaseService implements WriterUserCertificationService {

	@Autowired
	private WriterUserCertificationDao writerUserCertificationDao;

	/**
	 * 
	 * @introduction 新增一个WriterUserCertification
	 * @author Mryang
	 * @createDate 2017年9月19日 上午9:50:09
	 * @param writerUserCertification
	 * @return 带主键的WriterUserCertification
	 * @throws CheckedServiceException
	 */
	@Override
	public WriterUserCertification addWriterUserCertification(WriterUserCertification writerUserCertification)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(writerUserCertification.getUserId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "作家为空");
		}
		if (ObjectUtil.isNull(writerUserCertification.getOrgId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "作家学校为空");
		}
		writerUserCertificationDao.addWriterUserCertification(writerUserCertification);
		return writerUserCertification;
	}

	/**
	 * 
	 * @introduction 根据id查询 WriterUserCertification
	 * @author Mryang
	 * @createDate 2017年9月19日 上午9:51:41
	 * @param id
	 * @return WriterUserCertification
	 * @throws CheckedServiceException
	 */
	@Override
	public WriterUserCertification getWriterUserCertificationById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return writerUserCertificationDao.getWriterUserCertificationById(id);
	}

	/**
	 * 
	 * @introduction 根据id删除WriterUserCertification
	 * @author Mryang
	 * @createDate 2017年9月19日 上午9:52:18
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteWriterUserCertificationById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return writerUserCertificationDao.deleteWriterUserCertificationById(id);
	}

	/**
	 * 
	 * @introduction 根据id 更新writerUserCertification不为null和''的字段
	 * @author Mryang
	 * @createDate 2017年9月19日 上午9:53:00
	 * @param writerUserCertification
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateWriterUserCertification(WriterUserCertification writerUserCertification)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(writerUserCertification.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return writerUserCertificationDao.updateWriterUserCertification(writerUserCertification);
	}

	@Override
	public Integer updateWriterUserCertificationProgressByUserId(Short progress, String[] userIds)
			throws CheckedServiceException {
		if (ArrayUtil.isEmpty(userIds) || ObjectUtil.isNull(progress)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK, CheckedExceptionResult.NULL_PARAM,
					"参数为空");
		}
		List<WriterUserCertification> writerUserCertifications = new ArrayList<WriterUserCertification>(userIds.length);
		for (String userId : userIds) {
			WriterUserCertification writerUserCertification = writerUserCertificationDao
					.getWriterUserCertificationById(CastUtil.castLong(userId));
			if (writerUserCertification.getProgress() == 0) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK,
						CheckedExceptionResult.NULL_PARAM, "用户未提交审核申请");
			}
			if (writerUserCertification.getProgress() == 2 || writerUserCertification.getProgress() == 3) {
				throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK,
						CheckedExceptionResult.NULL_PARAM, "用户已经审核过了");
			}
			writerUserCertifications.add(new WriterUserCertification(CastUtil.castLong(userId), progress));

		}
		return writerUserCertificationDao.updateWriterUserCertificationProgressByUserId(writerUserCertifications);
	}

}
