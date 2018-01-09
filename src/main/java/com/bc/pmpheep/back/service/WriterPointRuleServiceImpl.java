package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterPointRuleDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.WriterPointLogVO;
import com.bc.pmpheep.back.vo.WriterPointRuleVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 积分规则 业务层
 * @author mr
 *
 */
@Service
public class WriterPointRuleServiceImpl implements WriterPointRuleService{
	
	@Autowired
	WriterPointRuleDao writerPointRuleDao;
	
	@Override
	public Integer updateWriterPointRule(WriterPointRule writerPointRule) throws CheckedServiceException {
		if(ObjectUtil.isNull(writerPointRule)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(ObjectUtil.isNull(writerPointRule.getId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(ObjectUtil.isNull(writerPointRule.getRuleName())){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "积分规则名称为空");
		}
		if(StringUtil.strLength(writerPointRule.getRuleName()) > 25){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "积分或兑换规则名称不能超过25个字符");
		}
		if(StringUtil.strLength(writerPointRule.getRuleCode()) > 25){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "积分或兑换规则标识不能超过25个字符");
		}
		if (writerPointRuleDao.getWriterPointRuleByName(writerPointRule.getRuleName()).size() > 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "该名称已被使用，请重新输入");
		}
		if(null != writerPointRule.getDescription()){
			if(StringUtil.strLength(writerPointRule.getDescription()) > 50){
				throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "规则描述不能超过25个字符");
			}
		}
		if(ObjectUtil.isNull(writerPointRule.getPoint())){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "积分值为空");
		}
		return writerPointRuleDao.updateWriterPointRule(writerPointRule);
	}

	@Override
	public WriterPointRule addWriterPointRule(WriterPointRule writerPointRule) throws CheckedServiceException {
		if(ObjectUtil.isNull(writerPointRule)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(ObjectUtil.isNull(writerPointRule.getRuleName())){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "积分或兑换规则名称为空");
		}
		if(StringUtil.strLength(writerPointRule.getRuleName()) > 25){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "积分或兑换规则名称不能超过25个字符");
		}
		if (writerPointRuleDao.getWriterPointRuleByName(writerPointRule.getRuleName()).size() > 0) {
	            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
	                                              CheckedExceptionResult.ILLEGAL_PARAM, "该名称已被使用，请重新输入");
	    }
		if(StringUtil.strLength(writerPointRule.getRuleCode()) > 25){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "积分或兑换规则标识不能超过25个字符");
		}
		if(null != writerPointRule.getDescription()){
			if(StringUtil.strLength(writerPointRule.getDescription()) > 50){
				throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "规则描述不能超过25个字符");
			}
		}
		if(ObjectUtil.isNull(writerPointRule.getPoint())){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "积分值为空");
		}
		writerPointRuleDao.addWriterPointRule(writerPointRule);
		return writerPointRule;
	}

	@Override
	public Integer deleteWriterPointRule(Long id) throws CheckedServiceException {
		if(ObjectUtil.isNull(id)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerPointRuleDao.deleteWriterPointRule(id);
	}

	@Override
	public PageResult<WriterPointRuleVO> getListWriterPointRule(PageParameter<WriterPointRuleVO> pageParameter)
			throws CheckedServiceException {
		PageResult<WriterPointRuleVO> pageResult = new PageResult<WriterPointRuleVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        List<WriterPointRuleVO> writerPointRuleVOs = writerPointRuleDao.listWriterPointRule(pageParameter);
        if (CollectionUtil.isNotEmpty(writerPointRuleVOs)) {
            Integer count = writerPointRuleVOs.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(writerPointRuleVOs);
        }
        return pageResult;
	}

	@Override
	public PageResult<WriterPointRuleVO> getlistWriterPointRulePoint(PageParameter<WriterPointRuleVO> pageParameter)
			throws CheckedServiceException {
		PageResult<WriterPointRuleVO> pageResult = new PageResult<WriterPointRuleVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        List<WriterPointRuleVO> writerPointRuleVOs = writerPointRuleDao.listWriterPointRulePoint(pageParameter);
        if (CollectionUtil.isNotEmpty(writerPointRuleVOs)) {
            Integer count = writerPointRuleVOs.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(writerPointRuleVOs);
        }
        return pageResult;
	}

}
