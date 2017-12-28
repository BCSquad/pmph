package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 积分规则 业务层
 * @author mr
 *
 */
@Service
public class WriterPointRuleServiceImpl implements WriterPointRuleService{


	@Override
	public Integer updateWriterPointRule(WriterPointRule writerPointRule) throws CheckedServiceException {
		return null;
	}

	@Override
	public WriterPointRule addWriterPointRule(WriterPointRule writerPointRule) throws CheckedServiceException {
		return null;
	}

	@Override
	public Integer deleteWriterPointRule(Long id) throws CheckedServiceException {
		return null;
	}

	@Override
	public PageResult<WriterPointRule> getListWriterPointRule(PageParameter<WriterPointRule> pageParameter)
			throws CheckedServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
