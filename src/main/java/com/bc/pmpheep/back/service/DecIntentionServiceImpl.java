package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecIntentionDao;
import com.bc.pmpheep.back.po.DecIntention;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 编写内容意向表业务层实现类
 * @author tyc
 * @date 2018年02月28日 下午18:07:46
 */
@Service
public class DecIntentionServiceImpl implements DecIntentionService{

	@Autowired
	private DecIntentionDao decIntentionDao;
	
	@Override
	public DecIntention addDecIntention(DecIntention decIntention)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decIntention)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decIntention.getDeclarationId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
		}
		decIntentionDao.addDecIntention(decIntention);
		return decIntention;
	}
	
	@Override
	public DecIntention getDecIntentionByDeclarationId (Long declarationId) 
			throws CheckedServiceException{
		if (null == declarationId){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, 
					CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
		}
		return decIntentionDao.getDecIntentionByDeclarationId(declarationId);
	}
}
