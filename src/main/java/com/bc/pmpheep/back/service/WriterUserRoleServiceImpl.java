package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterUserRoleDao;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 *  WriterUserRoleService 实现
 *
 * @author Mryang
 *
 * @createDate 2017年9月19日 下午12:02:18
 *
 */
@Service
public class WriterUserRoleServiceImpl extends BaseService implements WriterUserRoleService {

	@Autowired
	private WriterUserRoleDao writerUserRoleDao;

	/**
	 * 
	 * @param writerUserRole  实体对象
	 * @return 带主键的 writerUserRole
	 * @throws CheckedServiceException
	 */
	@Override
	public WriterUserRole addWriterUserRole(WriterUserRole writerUserRole) throws CheckedServiceException {
		if(null == writerUserRole){
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT, CheckedExceptionResult.NULL_PARAM, "参数对象为空");
		}
		if(null == writerUserRole.getUserId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT, CheckedExceptionResult.NULL_PARAM, "用户id为空");
		}
		if(null == writerUserRole.getRoleId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT, CheckedExceptionResult.NULL_PARAM, "角色id为空");
		}
		writerUserRoleDao.addWriterUserRole(writerUserRole);
		return writerUserRole;
	}

	/**
	 * 
	 * @param id
	 * @return writerUserRole
	 * @throws CheckedServiceException
	 */
	@Override
	public WriterUserRole getWriterUserRoleById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return writerUserRoleDao.getWriterUserRoleById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteWriterUserRoleById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return writerUserRoleDao.deleteWriterUserRoleById(id);
	}

	/**
	 * 根据id 更新writerUserRole不为null和''的字段 
	 * @param writerUserRole
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateWriterUserRole(WriterUserRole writerUserRole) throws CheckedServiceException {
		if (null == writerUserRole.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return writerUserRoleDao.updateWriterUserRole(writerUserRole);
	}

}
