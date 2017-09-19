package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.AreaDao;
import com.bc.pmpheep.back.log.annotation.Log;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * AreaService 实现
 * 
 * @author mryang
 *
 */
@Service
public class AreaServiceImpl extends BaseService implements AreaService {

	@Autowired
	private AreaDao areaDao;

	/**
	 * 
	 * @param area
	 *            实体对象
	 * @return 带主键的 area
	 * @throws CheckedServiceException
	 */
	@Override
	public Area addArea(Area area) throws CheckedServiceException {
		areaDao.addArea(area);
		return area;
	}

	/**
	 * 
	 * @param id
	 * @return area
	 * @throws CheckedServiceException
	 */
	@Override
	public Area getAreaById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return areaDao.getAreaById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteAreaById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return areaDao.deleteAreaById(id);
	}

	/**
	 * @param area
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateArea(Area area) throws CheckedServiceException {
		if (null == area.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return areaDao.updateArea(area);
	}

}
