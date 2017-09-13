package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupFileDao;
import com.bc.pmpheep.back.po.PmphGroupFile;

/**
 * PmphGroupService 接口实现
 * @author Mryang
 *
 */
@Service
public class PmphGroupFileServiceImpl extends BaseService implements PmphGroupFileService {
	@Autowired
	private PmphGroupFileDao pmphGroupFileDao;
	
	/**
	 * 
	 * @param  PmphGroupFile 实体对象
	 * @return  带主键的 PmphGroupFile
	 * @throws Exception 
	 */
	@Override
	public PmphGroupFile addPmphGroupFile (PmphGroupFile pmphGroupFile) throws Exception{
		return pmphGroupFileDao.addPmphGroupFile (pmphGroupFile);
	}
	
	/**
	 * 
	 * @param PmphGroupFile 必须包含主键ID
	 * @return  PmphGroupFile
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public PmphGroupFile getPmphGroupFileById(PmphGroupFile pmphGroupFile) throws Exception{
		if(null==pmphGroupFile.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupFileDao.getPmphGroupFileById(pmphGroupFile);
	}
	
	/**
	 * 
	 * @param PmphGroupFile
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deletePmphGroupFileById(PmphGroupFile pmphGroupFile) throws Exception{
		if(null==pmphGroupFile.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupFileDao.deletePmphGroupFileById(pmphGroupFile);
	}
	
	/**
	 * @param PmphGroupFile
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updatePmphGroupFileById(PmphGroupFile pmphGroupFile) throws Exception{
		if(null==pmphGroupFile.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupFileDao.updatePmphGroupFileById(pmphGroupFile);
	}

}
