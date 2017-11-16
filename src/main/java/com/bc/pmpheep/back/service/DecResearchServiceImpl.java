/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecResearchDao;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecResearchServiceImpl
 * <p>
 * <p>
 * Description:TODO
 * <p>
 * 
 * @author Administrator
 * @date 2017年9月24日 下午5:23:44
 */
@Service
public class DecResearchServiceImpl implements DecResearchService {

	@Autowired
	private DecResearchDao decResearchDao;

	@Override
	public DecResearch addDecResearch(DecResearch decResearch)
			throws CheckedServiceException {
		if (null == decResearch){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == decResearch.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id不能为空");
		}
		/*if (null == decResearch.getResearchName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "课题名称不能为空");
		}
		if (null == decResearch.getApprovalUnit()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "审批单位不能为空");
		}*/
		if (null == decResearch.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "显示顺序不能为空");
		}
		decResearchDao.addDecResearch(decResearch);
		return decResearch;
	}

	@Override
	public Integer deleteDecResearchById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decResearchDao.deleteDecResearchById(id);
	}

	@Override
	public Integer updateDecResearch(DecResearch decResearch)
			throws CheckedServiceException {
		if (null == decResearch.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decResearchDao.updateDecResearch(decResearch);
	}

	@Override
	public DecResearch getDecResearchById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decResearchDao.getDecResearchById(id);
	}

	@Override
	public List<DecResearch> getListDecResearchByDeclarationId(
			Long declarationId) throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decResearchDao.getListDecResearchByDeclarationId(declarationId);
	}

}
