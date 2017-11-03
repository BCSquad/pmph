package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgDao;
import com.bc.pmpheep.back.dao.OrgTypeDao;
import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgTypeService 接口实现
 * 
 * @author Mryang
 *
 */
@Service
public class OrgTypeServiceImpl extends BaseService implements OrgTypeService {
	@Autowired
	private OrgTypeDao orgTypeDao;
	@Autowired
	OrgDao orgDao;

	/**
	 * 
	 * @param OrgType
	 *            实体对象
	 * @return 带主键的OrgType
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgType addOrgType(OrgType orgType) throws CheckedServiceException {
		if (null == orgType.getTypeName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM,
					"机构类型名称为空");
		}
		orgTypeDao.addOrgType(orgType);
		return orgType;
	}

	/**
	 * 
	 * @param id
	 *            如果为null 查询全部 不为null查询对应得数据
	 * @return List<OrgType>
	 * @throws CheckedServiceException
	 */
	@Override
	public List<OrgType> getOrgType(Long id) throws CheckedServiceException {
		return orgTypeDao.getOrgType(new OrgType(id));
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteOrgTypeById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		if (orgDao.listOrgByOrgType(id).size() > 0) {

		}
		return orgTypeDao.deleteOrgTypeById(id);
	}

	/**
	 * @param orgType
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateOrgType(OrgType orgType) throws CheckedServiceException {
		if (null == orgType.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgTypeDao.updateOrgType(orgType);
	}

	@Override
	public List<OrgType> listOrgTypeByTypeName(String typeName) throws CheckedServiceException {
		return orgTypeDao.listOrgTypeByTypeName(typeName);
	}

}
