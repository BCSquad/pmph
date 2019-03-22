package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.SiteLinkDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SiteLink;
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
public class SiteLinkServiceImpl extends BaseService implements SiteLinkService {
	@Autowired
	SiteLinkDao SiteLinkDao;

	@Override
	public SiteLink add(SiteLink SiteLink) throws CheckedServiceException {
		if (ObjectUtil.isNull(SiteLink)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"参数为空");
		}
		if (SiteLink.getName().length() > 25) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.ILLEGAL_PARAM,
					"名称太长了，请控制在25个字以内");
		}
		if (!ObjectUtil.isNull(SiteLink.getHref())&&SiteLink.getHref().length()>200) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"站点地址长度不可超过200字符。");
		}
		if (!StringUtil.isEmpty(SiteLink.getNote())) {
			if (SiteLink.getNote().length() > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink,
						CheckedExceptionResult.ILLEGAL_PARAM, "备注太长了，请控制在100个字以内");
			}
		} else {
			SiteLink.setNote("-");
		}
		SiteLink sen = SiteLinkDao.getSiteLinkId(SiteLink.getName());
		if (ObjectUtil.notNull(sen)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.ILLEGAL_PARAM,
					"已经有该链接了。");
		} else {
			SiteLinkDao.add(SiteLink);
		}
		return SiteLink;
	}

	@Override
	public String update(SiteLink SiteLink) throws CheckedServiceException {
		if (ObjectUtil.isNull(SiteLink.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"需要修改的链接id为空");
		}
		if (ObjectUtil.isNull(SiteLink.getRowNum())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"所在行号不可为空");
		}
		if (ObjectUtil.isNull(SiteLink.getRowNum())||SiteLink.getRowNum()>=10000) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"所在行号位数不可超过3位");
		}
		if (!ObjectUtil.isNull(SiteLink.getHref())&&SiteLink.getHref().length()>200) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"站点地址长度不可超过200字符。");
		}
		if (SiteLink.getName().length() > 25) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.ILLEGAL_PARAM,
					"名称太长了，请控制在25个字以内");
		}
		if (StringUtil.notEmpty(SiteLink.getNote())) {
			if (SiteLink.getNote().length() > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink,
						CheckedExceptionResult.ILLEGAL_PARAM, "备注太长了，请控制在100个字以内");
			}
		}
		String result = "FAIL";
		if (StringUtil.notEmpty(SiteLink.getName())) {
			if (SiteLink.getName().length() > 50) {
				throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink,
						CheckedExceptionResult.ILLEGAL_PARAM, "链接名称太长了，请控制在50个字以内");
			}
			SiteLink sen = SiteLinkDao.getSiteLinkId(SiteLink.getName());
			if (ObjectUtil.notNull(sen)) {
				Long id = sen.getId();
				if (null != id && !SiteLink.getId().equals(id)) {
					throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink,
							CheckedExceptionResult.ILLEGAL_PARAM, "修改的链接名称重复了");
				}
			}
		}
		Integer total = SiteLinkDao.update(SiteLink);
		if (total > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public PageResult<SiteLink> list(PageParameter<SiteLink> pageParameter) throws CheckedServiceException {
		PageResult<SiteLink> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = SiteLinkDao.getTotal(pageParameter.getParameter().getName());
		if (total > 0) {
			pageResult.setRows(SiteLinkDao.list(pageParameter.getParameter().getName(), pageParameter.getPageSize(),
					pageParameter.getStart()));
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public String deletedIsDeleted(Long[] id) throws CheckedServiceException {
		if (ArrayUtil.isEmpty(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"没有找到需要删除的链接");
		}
		String result = "FAIL";
		Integer total = SiteLinkDao.deletedIsDeleted(id);
		if (total > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public Long sortMove(Long id, Boolean up) {
		if(ObjectUtil.isNull(id)||id<=0){
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"所需修改的主键为空");
		}
		Long neighbor_id = 0L;
		if(up){
			neighbor_id = SiteLinkDao.pushForward(id);
		}else{
			neighbor_id = SiteLinkDao.pushBack(id);
		}
		return neighbor_id;
	}

}
