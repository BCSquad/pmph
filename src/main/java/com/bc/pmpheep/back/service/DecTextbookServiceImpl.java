/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecTextbookDao;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecTextbook业务层实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 上午10:30:05
 */
@Service
public class DecTextbookServiceImpl implements DecTextbookService {

	@Autowired
	private DecTextbookDao decTextbookDao;

	@Override
	public DecTextbook addDecTextbook(DecTextbook decTextbook)
			throws CheckedServiceException {
		if (null == decTextbook){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == decTextbook.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id不能为空");
		}
		if (null == decTextbook.getMaterialName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "教材名称不能为空");
		}
		if (null == decTextbook.getRank()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "编写教材级别不能为空");
		}
		if (null == decTextbook.getPosition()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "编写职务不能为空");
		}
		if (null == decTextbook.getPublisher()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "出版社不能为空");
		}
		if (null == decTextbook.getPublishDate()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "出版时间不能为空");
		}
		if (null ==decTextbook.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "显示顺序不能为空");
		}
		decTextbookDao.addDecTextbook(decTextbook);
		return decTextbook;
	}

	@Override
	public Integer deleteDecTextbookById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTextbookDao.deleteDecTextbookById(id);
	}

	@Override
	public Integer updateDecTextbook(DecTextbook decTextbook)
			throws CheckedServiceException {
		if (null == decTextbook.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTextbookDao.updateDecTextbook(decTextbook);
	}

	@Override
	public DecTextbook getDecTextbookById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTextbookDao.getDecTextbookById(id);
	}

	@Override
	public List<DecTextbook> getListDecTextbookByDeclarationId(
			Long declarationId) throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decTextbookDao.getListDecTextbookByDeclarationId(declarationId);
	}

}
