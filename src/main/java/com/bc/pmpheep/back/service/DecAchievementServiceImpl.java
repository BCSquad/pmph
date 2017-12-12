/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecAchievementDao;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:个人成就表<p>
 * <p>Description:业务层实现类<p>
 * @author tyc
 * @date 2017年12月5日 下午17:42:55
 */
@Service
public class DecAchievementServiceImpl implements DecAchievementService{

	@Autowired
	private DecAchievementDao decAchievementDao;
	
	@Override
	public DecAchievement addDecAchievement(DecAchievement decAchievement)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decAchievement)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decAchievement.getDeclarationId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
		}
		if (StringUtil.isEmpty(decAchievement.getContent())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "内容为空");
		}
		
		decAchievementDao.addDecAchievement(decAchievement);
		
		return decAchievement;
	}
	
	@Override
	public DecAchievement getDecAchievementByDeclarationId (Long declarationId) throws CheckedServiceException{
		if (null == declarationId){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
		}
		
		return decAchievementDao.getDecAchievementByDeclarationId(declarationId);
	}
}
