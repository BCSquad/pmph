package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupFileDao;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
	 * @param  pmphGroupFile 实体对象
	 * @return  带主键的 PmphGroupFile
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupFile addPmphGroupFile (PmphGroupFile pmphGroupFile) throws CheckedServiceException{
		if(Tools.isEmpty(pmphGroupFile.getFileName())){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "文件名称为空不允许新增");
		}
		pmphGroupFileDao.addPmphGroupFile(pmphGroupFile);
		return pmphGroupFile;
	}
	
	/**
	 * 
	 * @param id 主键id
	 * @return  PmphGroupFile
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupFile getPmphGroupFileById(Long  id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphGroupFileDao.getPmphGroupFileById(id);
	}
	
	/**
	 * 
	 * @param id 主键id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deletePmphGroupFileById (Long  id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphGroupFileDao.deletePmphGroupFileById(id);
	}
	
	/**
	 * @param PmphGroupFile
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override 
	public Integer updatePmphGroupFile (PmphGroupFile pmphGroupFile) throws CheckedServiceException{
		if(null==pmphGroupFile.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphGroupFileDao.updatePmphGroupFile(pmphGroupFile);
	}

}
