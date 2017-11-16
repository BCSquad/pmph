/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecAcadeDao;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecAcadeServiceImpl
 * <p>
 * <p>
 * <p>
 * 
 * @author lyc
 * @date 2017年9月22日 下午3:05:41
 */
@Service
public class DecAcadeServiceImpl implements DecAcadeService {

	@Autowired
	private DecAcadeDao decAcadeDao;

	@Override
	public DecAcade addDecAcade(DecAcade decAcade)
			throws CheckedServiceException {
		if (null == decAcade){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == decAcade.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id不能为空");
		}
		/*if (null == decAcade.getOrgName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "兼职学术组织名称不能为空");
		}
		if (null == decAcade.getRank()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "学术组织级别不能为空");
		}
		if (null == decAcade.getPosition()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "职务不能为空");
		}*/
		if (null == decAcade.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "显示顺序不能为空");
		}
		decAcadeDao.addDecAcade(decAcade);
		return decAcade;
	}

	@Override
	public Integer deleteDecAcadeById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decAcadeDao.deleteDecAcadeById(id);
	}

	@Override
	public Integer updateDecAcade(DecAcade decAcade)
			throws CheckedServiceException {
		if (null == decAcade.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decAcadeDao.updateDecAcade(decAcade);
	}

	
	@Override
	public DecAcade getDecAcadeById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decAcadeDao.getDecAcadeById(id);
	}

	@Override
	public List<DecAcade> getListDecAcadeByDeclarationId(Long declarationId)
			throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decAcadeDao.getListDecAcadeByDeclarationId(declarationId);
	}

}
