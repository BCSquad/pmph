package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.AreaDao;
import com.bc.pmpheep.back.po.Area;
/**
 * AreaService 实现
 * @author mryang
 *
 */
@Service
public class AreaServiceImpl extends BaseService implements AreaService {
	
	@Autowired
	private AreaDao areaDao;
	
	/**
	 * 
	 * @param area 实体对象
	 * @return  id 主键
	 * @throws Exception 
	 */
	@Override
	public Long addArea(Area area) throws Exception{
		return areaDao.addArea(area);
	}
	
	/**
	 * 
	 * @param area 必须包含主键ID
	 * @return  area
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Area findAreaById(Area area) throws Exception{
		if(null==area.getId()){
			throw new NullPointerException("主键id为空");
		}
		return areaDao.getAreaById( area);
	}
	
	/**
	 * 
	 * @param area
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteAreaById(Area area) throws Exception{
		if(null==area.getId()){
			throw new NullPointerException("主键id为空");
		}
		return areaDao.deleteAreaById(area);
	}
	
	/**
	 * @param area
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updateAreaById(Area area) throws Exception{
		if(null==area.getId()){
			throw new NullPointerException("主键id为空");
		}
		return areaDao.updateAreaById(area);
	}
	
	
	
		
}






