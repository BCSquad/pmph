package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterMseeageDao;
import com.bc.pmpheep.back.po.WriterMseeage;

/**
 * PmphGroupService 接口实现
 * @author Mryang
 *
 */
@Service
public class WriterMseeageServiceImpl extends BaseService implements WriterMseeageService {
	@Autowired
	private WriterMseeageDao writerMseeageDao;
	
	/**
	 * 
	 * @param  WriterMseeage 实体对象
	 * @return  带主键的WriterMseeage
	 * @throws Exception 
	 */
	@Override
	public WriterMseeage addWriterMseeage (WriterMseeage writerMseeage) throws Exception{
		return writerMseeageDao.addWriterMseeage(writerMseeage);
	}
	
	/**
	 * 
	 * @param WriterMseeage 必须包含主键ID
	 * @return  WriterMseeage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public WriterMseeage getWriterMseeageById(WriterMseeage writerMseeage) throws Exception{
		if(null==writerMseeage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return writerMseeageDao.getWriterMseeageById(writerMseeage);
	}
	
	/**
	 * 
	 * @param WriterMseeage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteWriterMseeageById(WriterMseeage writerMseeage) throws Exception{
		if(null==writerMseeage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return writerMseeageDao.deleteWriterMseeageById(writerMseeage);
	}
	
	/**
	 * @param WriterMseeage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updateWriterMseeageById(WriterMseeage writerMseeage) throws Exception{
		if(null==writerMseeage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return writerMseeageDao.updateWriterMseeageById(writerMseeage);
	}
}
