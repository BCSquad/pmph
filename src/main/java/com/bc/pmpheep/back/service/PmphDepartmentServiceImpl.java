package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphDepartmentDao;
import com.bc.pmpheep.back.dao.PmphUserDao;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.util.Const;
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
	@Autowired
	PmphUserDao pmphUserDao;

	/**
	 * 
	 * @param pmphDepartment
	 *            实体对象
	 * @return 带主键的PmphDepartment
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphDepartment addPmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException {
		if (null == pmphDepartment) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == pmphDepartment.getParentId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "上级id为空");
		}
		if (null == pmphDepartment.getDpName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "部门名称为空");
		}
		if (null == pmphDepartment.getPath()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "根节点为空");
		}
		if (pmphDepartmentDao.getPmphDepartmentByDpNameAndParentId(pmphDepartment).size() > 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "该部门下有相同的子部门了");
		}

		String path = pmphDepartment.getPath() + "-" + pmphDepartment.getParentId();
		pmphDepartment.setPath(path);
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
		if (null == pmphDepartment) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == pmphDepartment.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		if (null == pmphDepartment.getParentId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "上级id为空");
		}
		if (null == pmphDepartment.getDpName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "部门名称为空");
		}
		if (null == pmphDepartment.getPath()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "根节点为空");
		}
		return pmphDepartmentDao.updatePmphDepartment(pmphDepartment);
	}

	@Override
	public PmphUserDepartmentVO listPmphDepartment(Long parentId) throws CheckedServiceException {
		if (null == parentId) {
			parentId = Const.PMPH_DEPARTMENT_ROOT_ID;
		}
		Long id = parentId;
		PmphUserDepartmentVO pmphUserDepartmentVO = new PmphUserDepartmentVO(id);
		recursionPmphDepartment(pmphUserDepartmentVO, new ArrayList<Long>(16));
		return pmphUserDepartmentVO;
	}

	@Override
	public Integer deletePmphDepartmentBatch(Long id) {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		recursionPmphDepartment(new PmphUserDepartmentVO(id), ids);
		for (Long departmentId : ids) {
			if (pmphUserDao.getPmphUserByDepartmentId(departmentId).size() > 0) {
				throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "部门中还有用户，不能删除部门");
			}
		}
		return pmphDepartmentDao.deletePmphDepartmentBatch(ids);
	}

	/**
	 * 
	 * 功能描述：使用递归的方法将部门转化为树状图 使用示范：
	 *
	 * @param departmentVO
	 *            ids (为后面删除做准备) 父级部门
	 */
	private void recursionPmphDepartment(PmphUserDepartmentVO pmphUserDepartmentVO, List<Long> ids) {
		List<PmphUserDepartmentVO> list = pmphDepartmentDao.listPmphDepartment(pmphUserDepartmentVO.getId());
		if (null != list && list.size() > 0) {
			pmphUserDepartmentVO.setSonDepartment(list);
			pmphUserDepartmentVO.setIsLeaf(false);
			for (PmphUserDepartmentVO userDepartmentVO : list) {
				ids.add(userDepartmentVO.getId());
				recursionPmphDepartment(userDepartmentVO, ids);
			}
		}
	}

	@Override
	public List<PmphUserDepartmentVO> listPmphUserDepartmentByDpName(String dpName) throws CheckedServiceException {
		if (null != dpName) {
			dpName = dpName.trim();
			dpName = dpName.replace(" ", "%");
			dpName = "%" + dpName + "%";
		}
		List<PmphUserDepartmentVO> list = pmphDepartmentDao.listPmphDepartmentByDpName(dpName);
		return list;
	}

}
