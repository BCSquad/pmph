/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecCourseConstructionDao;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecCourseConstructionServiceImpl
 * <p>
 * <p>
 * Description:精品课程建设情况业务层实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月22日 下午4:50:56
 */

@Service
public class DecCourseConstructionServiceImpl implements
		DecCourseConstructionService {

	@Autowired
	private DecCourseConstructionDao decCourseConstructionDao;

	@Override
	public DecCourseConstruction addDecCourseConstruction(
			DecCourseConstruction decCourseConstruction)
			throws CheckedServiceException {
		if (null == decCourseConstruction){
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
            		CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");			
		}
		if (null == decCourseConstruction.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报id不能为空");
		}
		if (null == decCourseConstruction.getCourseName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "课程名称不能为空");
		}
		if (null == decCourseConstruction.getClassHour()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "课程全年课时不能为空");
		}
		if (null == decCourseConstruction.getType()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "职务不能为空");
		}
		if (null == decCourseConstruction.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "显示顺序不能为空");
		}
		decCourseConstructionDao.addDecCourseConstruction(decCourseConstruction);
		return decCourseConstruction;
	}

	@Override
	public Integer deleteDecCourseConstruction(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decCourseConstructionDao.deleteDecCourseConstrction(id);
	}

	@Override
	public Integer updateDecCourseConstruction(
			DecCourseConstruction decCourseConstruction)
			throws CheckedServiceException {
		if (null == decCourseConstruction.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decCourseConstructionDao.updateDecCourseCnstruction(decCourseConstruction);
	}

	@Override
	public DecCourseConstruction getDecCourseConstructionById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decCourseConstructionDao.getDecCourseConstructionById(id);
	}

	@Override
	public List<DecCourseConstruction> getDecCourseConstructionBydeclarationId(
			Long declarationId) throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decCourseConstructionDao.getDecCourseConstructionByDeclarationId(declarationId);
	}

}
