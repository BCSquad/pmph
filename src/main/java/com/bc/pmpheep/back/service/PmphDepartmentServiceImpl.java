package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphDepartmentDao;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.vo.PmphUserDepartmentVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphDepartmentService 接口实现
 * 
 * @author Mryang
 *
 */
@Service
public class PmphDepartmentServiceImpl extends BaseService implements PmphDepartmentService {
	@Autowired
	private PmphDepartmentDao pmphDepartmentDao;

	/**
	 * 
	 * @param PmphDepartment
	 *            实体对象
	 * @return 带主键的PmphDepartment
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphDepartment addPmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException {
		if (null == pmphDepartment.getDpName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "部门名称为空");
		}

		pmphDepartmentDao.addPmphDepartment(pmphDepartment);
		return pmphDepartment;
	}

	/**
	 * 
	 * @param id
	 * @return PmphDepartment
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphDepartment getPmphDepartmentById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphDepartmentDao.getPmphDepartmentById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deletePmphDepartmentById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphDepartmentDao.deletePmphDepartmentById(id);
	}

	/**
	 * @param PmphDepartment
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updatePmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException {
		if (null == pmphDepartment.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphDepartmentDao.updatePmphDepartment(pmphDepartment);
	}

	@Override
	public PmphUserDepartmentVO getListPmphDepartment() throws CheckedServiceException {
		List<PmphUserDepartmentVO> list = pmphDepartmentDao.getListPmphDepartment(0L);
		PmphUserDepartmentVO departmentVO = list.get(0);// 最高层始终只会有一个对象
		RecursionPmphDepartment(departmentVO);

		return departmentVO;
	}

	/**
	 * 
	 * 功能描述：使用递归的方法将部门转化为树状图 使用示范：
	 *
	 * @param departmentVO
	 *            父级部门
	 */
	private void RecursionPmphDepartment(PmphUserDepartmentVO departmentVO) {
		List<PmphUserDepartmentVO> list = pmphDepartmentDao.getListPmphDepartment(departmentVO.getId());
		if (null != list && list.size() > 0) {
			departmentVO.setSonDepartment(list);
			for (PmphUserDepartmentVO userDepartmentVO : list) {
				RecursionPmphDepartment(userDepartmentVO);
			}
		}
	}
}
