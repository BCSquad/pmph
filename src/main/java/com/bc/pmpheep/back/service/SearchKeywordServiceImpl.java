package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.SearchKeywordDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SearchKeyword;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchKeywordServiceImpl extends BaseService implements SearchKeywordService {
	@Autowired
	SearchKeywordDao SearchKeywordDao;

	@Override
	public SearchKeyword add(SearchKeyword SearchKeyword) throws CheckedServiceException {
		if (ObjectUtil.isNull(SearchKeyword)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword, CheckedExceptionResult.NULL_PARAM,
					"参数为空");
		}
		if (SearchKeyword.getWord().length() > 10) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword, CheckedExceptionResult.ILLEGAL_PARAM,
					"敏感词太长了，请控制在10个字以内");
		}
		if (!StringUtil.isEmpty(SearchKeyword.getNote())) {
			if (SearchKeyword.getNote().length() > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword,
						CheckedExceptionResult.ILLEGAL_PARAM, "备注太长了，请控制在100个字以内");
			}
		} else {
			SearchKeyword.setNote("-");
		}
		SearchKeyword sen = SearchKeywordDao.getSearchKeywordId(SearchKeyword.getWord());
		if (ObjectUtil.notNull(sen)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword, CheckedExceptionResult.ILLEGAL_PARAM,
					"已经有该敏感词了。");
		} else {
			SearchKeywordDao.add(SearchKeyword);
		}
		return SearchKeyword;
	}

	@Override
	public String update(SearchKeyword SearchKeyword) throws CheckedServiceException {
		if (ObjectUtil.isNull(SearchKeyword.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword, CheckedExceptionResult.NULL_PARAM,
					"需要修改的敏感词id为空");
		}
		if (StringUtil.notEmpty(SearchKeyword.getNote())) {
			if (SearchKeyword.getNote().length() > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword,
						CheckedExceptionResult.ILLEGAL_PARAM, "备注太长了，请控制在100个字以内");
			}
		} else {
			SearchKeyword.setNote("-");
		}
		String result = "FAIL";
		if (StringUtil.notEmpty(SearchKeyword.getWord())) {
			if (SearchKeyword.getWord().length() > 10) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword,
						CheckedExceptionResult.ILLEGAL_PARAM, "敏感词太长了，请控制在10个字以内");
			}
			SearchKeyword sen = SearchKeywordDao.getSearchKeywordId(SearchKeyword.getWord());
			if (ObjectUtil.notNull(sen)) {
				Long id = sen.getId();
				if (null != id && !SearchKeyword.getId().equals(id)) {
					throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword,
							CheckedExceptionResult.ILLEGAL_PARAM, "修改的敏感词重复了");
				}
			}
		}
		Integer total = SearchKeywordDao.update(SearchKeyword);
		if (total > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public PageResult<SearchKeyword> list(PageParameter<SearchKeyword> pageParameter) throws CheckedServiceException {
		PageResult<SearchKeyword> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = SearchKeywordDao.getTotal(pageParameter.getParameter().getWord());
		if (total > 0) {
			pageResult.setRows(SearchKeywordDao.list(pageParameter.getParameter().getWord(), pageParameter.getPageSize(),
					pageParameter.getStart()));
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public String deletedIsDeleted(Long[] id) throws CheckedServiceException {
		if (ArrayUtil.isEmpty(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SearchKeyword, CheckedExceptionResult.NULL_PARAM,
					"没有找到需要删除的敏感词");
		}
		String result = "FAIL";
		Integer total = SearchKeywordDao.deletedIsDeleted(id);
		if (total > 0) {
			result = "SUCCESS";
		}
		return result;
	}

}
