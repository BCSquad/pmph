package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.AreaDao;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.vo.AreaTreeVO;
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
		if (null == area) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == area.getAreaName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM,
					"区域名称为空");
		}
		Long id = area.getId();
		areaDao.addArea(area);
		if (null != id) {
			area.setId(id);
		}
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
		if (null == area) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == area.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return areaDao.updateArea(area);
	}

	@Override
	public List<AreaTreeVO> getAreaChirldren(Long parentId) throws CheckedServiceException {
		if (null == parentId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		List<AreaTreeVO> areaVOList = areaDao.getAreaByParentId(parentId);
		for (AreaTreeVO areaTreeVO : areaVOList) {
			// 检查是否是子节点
			List<AreaTreeVO> areaChirldrenVOList = areaDao.getAreaByParentId(areaTreeVO.getId());
			if (null != areaChirldrenVOList && areaChirldrenVOList.size() > 0) {
				areaTreeVO.setIsLeaf(false);
			}
		}
		return areaVOList;
	}

	@Override
	public List<AreaTreeVO> getAreaTreeVO(Long parentId) throws CheckedServiceException {
		if (null == parentId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		Long id = parentId;
		AreaTreeVO areaTreeVO = new AreaTreeVO(id);
		this.getAreaTree(areaTreeVO, new ArrayList<Long>());
		return areaTreeVO.getChirldren();
	}

	@Override
	public Integer deleteAreaBatch(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.AREA, CheckedExceptionResult.NULL_PARAM, "ID为空");
		}
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		getAreaTree(new AreaTreeVO(id), ids);
		return areaDao.deleteAreaBatch(ids);
	}

	/****************************
	 * 下面是必须的辅助方法
	 *******************************************/
	/**
	 * 
	 * 得到parentId的树 和树的所有id
	 * 
	 * @author Mryang
	 * @createDate 2017年9月25日 下午2:05:27
	 * @param areaTreeVO
	 *            带parentId
	 * @param ids
	 * 
	 */
	public void getAreaTree(AreaTreeVO areaTreeVO, List<Long> ids) {
		List<AreaTreeVO> areaVOList = areaDao.getAreaByParentId(areaTreeVO.getId());
		if (null != areaVOList && areaVOList.size() > 0) {
			areaTreeVO.setChirldren(areaVOList);
			areaTreeVO.setIsLeaf(false);
			for (AreaTreeVO areaTreeVOChirld : areaVOList) {
				ids.add(areaTreeVOChirld.getId());
				getAreaTree(areaTreeVOChirld, ids);
			}
		}
	}

	@Override
	public void deleteAllArea() throws CheckedServiceException {
		areaDao.deleteAllArea();
	}

}
