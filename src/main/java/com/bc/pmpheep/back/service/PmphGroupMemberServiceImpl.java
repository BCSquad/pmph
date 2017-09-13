package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupMemberDao;
import com.bc.pmpheep.back.po.PmphGroupMember;

/**
 * PmphGroupMemberService 接口实现
 * @author Mryang
 *
 */
@Service
public class PmphGroupMemberServiceImpl extends BaseService implements PmphGroupMemberService {
	@Autowired
	private PmphGroupMemberDao pmphGroupMemberDao;
	
	/**
	 * 
	 * @param  PmphGroupMember 实体对象
	 * @return  带主键的 PmphGroupMember
	 * @throws Exception 
	 */
	@Override
	public PmphGroupMember addPmphGroupMember (PmphGroupMember pmphGroupMember) throws Exception{
		return pmphGroupMemberDao.addPmphGroupMember (pmphGroupMember);
	}
	
	/**
	 * 
	 * @param PmphGroupMember 必须包含主键ID
	 * @return  PmphGroupMember
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public PmphGroupMember getPmphGroupMemberById(PmphGroupMember pmphGroupMember) throws Exception{
		if(null==pmphGroupMember.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupMemberDao.getPmphGroupMemberById(pmphGroupMember);
	}
	
	/**
	 * 
	 * @param PmphGroupMember
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deletePmphGroupMemberById(PmphGroupMember pmphGroupMember) throws Exception{
		if(null==pmphGroupMember.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupMemberDao.deletePmphGroupMemberById(pmphGroupMember);
	}
	
	/**
	 * @param PmphGroupMember
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updatePmphGroupMemberById(PmphGroupMember pmphGroupMember) throws Exception{
		if(null==pmphGroupMember.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupMemberDao.updatePmphGroupMemberById(pmphGroupMember);
	}
}
