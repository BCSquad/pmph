package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecTextbookPmphDao;
import com.bc.pmpheep.back.po.DecTextbookPmph;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 人卫社教材编写情况表业务层实现类
 * 
 * @author tyc
 * @date 2018年02月28日 下午17:49
 */
@Service
public class DecTextbookPmphServiceImpl implements DecTextbookPmphService {

	@Autowired
	private DecTextbookPmphDao decTextbookPmphDao;

	@Override
	public DecTextbookPmph addDecTextbookPmph(DecTextbookPmph decTextbookPmph)
			throws CheckedServiceException {
		if (null == decTextbookPmph){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == decTextbookPmph.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id不能为空");
		}
		decTextbookPmphDao.addDecTextbookPmph(decTextbookPmph);
		return decTextbookPmph;
	}

	@Override
	public Integer deleteDecTextbookPmphById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTextbookPmphDao.deleteDecTextbookPmphById(id);
	}

	@Override
	public Integer updateDecTextbookPmph(DecTextbookPmph decTextbookPmph)
			throws CheckedServiceException {
		if (null == decTextbookPmph.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTextbookPmphDao.updateDecTextbookPmph(decTextbookPmph);
	}

	@Override
	public DecTextbookPmph getDecTextbookPmphById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTextbookPmphDao.getDecTextbookPmphById(id);
	}

	@Override
	public List<DecTextbookPmph> getListDecTextbookPmphByDeclarationId(
			Long declarationId) throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decTextbookPmphDao.getListDecTextbookPmphByDeclarationId(declarationId);
	}

}
