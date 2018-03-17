package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.SensitiveDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Sensitive;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

@Service
public class SensitiveServiceImpl extends BaseService implements SensitiveService {
	@Autowired
	SensitiveDao sensitiveDao;

	@Override
	public Sensitive add(Sensitive sensitive) throws CheckedServiceException {
		if (ObjectUtil.isNull(sensitive)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE, CheckedExceptionResult.NULL_PARAM,
					"参数为空");
		}
		if (sensitive.getWord().length() > 10) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE, CheckedExceptionResult.ILLEGAL_PARAM,
					"敏感词太长了，请控制在10个字以内");
		}
		if (!StringUtil.isEmpty(sensitive.getNote())) {
			if (sensitive.getNote().length() > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE,
						CheckedExceptionResult.ILLEGAL_PARAM, "备注太长了，请控制在100个字以内");
			}
		} else {
			sensitive.setNote("-");
		}
		Sensitive sen = sensitiveDao.getSensitiveId(sensitive.getWord());
		if (ObjectUtil.notNull(sen)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE, CheckedExceptionResult.ILLEGAL_PARAM,
					"已经有该敏感词了。");
		} else {
			sensitiveDao.add(sensitive);
		}
		return sensitive;
	}

	@Override
	public String update(Sensitive sensitive) throws CheckedServiceException {
		if (ObjectUtil.isNull(sensitive.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE, CheckedExceptionResult.NULL_PARAM,
					"需要修改的敏感词id为空");
		}
		if (StringUtil.notEmpty(sensitive.getNote())) {
			if (sensitive.getNote().length() > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE,
						CheckedExceptionResult.ILLEGAL_PARAM, "备注太长了，请控制在100个字以内");
			}
		} else {
			sensitive.setNote("-");
		}
		String result = "FAIL";
		if (StringUtil.notEmpty(sensitive.getWord())) {
			if (sensitive.getWord().length() > 10) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE,
						CheckedExceptionResult.ILLEGAL_PARAM, "敏感词太长了，请控制在10个字以内");
			}
			Sensitive sen = sensitiveDao.getSensitiveId(sensitive.getWord());
			if (ObjectUtil.notNull(sen)) {
				Long id = sen.getId();
				if (null != id && !sensitive.getId().equals(id)) {
					throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE,
							CheckedExceptionResult.ILLEGAL_PARAM, "修改的敏感词重复了");
				}
			}
		}
		Integer total = sensitiveDao.update(sensitive);
		if (total > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public PageResult<Sensitive> list(PageParameter<Sensitive> pageParameter) throws CheckedServiceException {
		PageResult<Sensitive> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = sensitiveDao.getTotal(pageParameter.getParameter().getWord());
		if (total > 0) {
			pageResult.setRows(sensitiveDao.list(pageParameter.getParameter().getWord(), pageParameter.getPageSize(),
					pageParameter.getStart()));
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public String deletedIsDeleted(Long[] id) throws CheckedServiceException {
		if (ArrayUtil.isEmpty(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SENSITIVE, CheckedExceptionResult.NULL_PARAM,
					"没有找到需要删除的敏感词");
		}
		String result = "FAIL";
		Integer total = sensitiveDao.deletedIsDeleted(id);
		if (total > 0) {
			result = "SUCCESS";
		}
		return result;
	}

}
