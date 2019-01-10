package com.bc.pmpheep.back.controller;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.SiteLink;
import com.bc.pmpheep.back.service.SiteLinkService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/SiteLink")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SiteLinkController {
	@Autowired
	SiteLinkService SiteLinkService;

	// 当前业务类型
	private static final String BUSSINESS_TYPE = "搜索友情链接";

	/**
	 * 
	 * 
	 * 功能描述：添加一个搜索友情链接
	 *
	 * @param SiteLink
	 * @return
	 *
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "添加一个搜索友情链接")
	@ResponseBody
	public ResponseBean add(SiteLink SiteLink) {
		return new ResponseBean(SiteLinkService.add(SiteLink));
	}

	/**
	 * 
	 * 
	 * 功能描述：修改搜索友情链接
	 *
	 * @param SiteLink
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改搜索友情链接")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseBean update(SiteLink SiteLink) {
		return new ResponseBean(SiteLinkService.update(SiteLink));
	}

	/**
	 * 
	 * 
	 * 功能描述：删除搜索友情链接
	 *
	 * @param ids
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除搜索友情链接")
	@RequestMapping(value = "/isDeleted", method = RequestMethod.DELETE)
	public ResponseBean isDeleted(Long[] ids) {
		return new ResponseBean(SiteLinkService.deletedIsDeleted(ids));
	}
	
	
	/**
	 * 
	 * 
	 * 功能描述：分页查询搜索友情链接
	 *
	 * @param name
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "分页查询搜索友情链接")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(String name, Integer pageSize, Integer pageNumber) {
		PageParameter<SiteLink> pageParameter = new PageParameter<>(pageNumber, pageSize);
		SiteLink SiteLink = new SiteLink();
		SiteLink.setName(name);
		pageParameter.setParameter(SiteLink);
		return new ResponseBean(SiteLinkService.list(pageParameter));
	}

	/**
	 *
	 *
	 * 功能描述：友情链接行内排序上下移
	 *
	 * @param id
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "友情链接行内排序上下移")
	@RequestMapping(value = "/sortMove", method = RequestMethod.GET)
	public ResponseBean sortMove(Long id,Boolean up) {
		Long neighbor_id = SiteLinkService.sortMove(id,up);
		if(ObjectUtil.isNull(neighbor_id)||neighbor_id<=0){
			throw new CheckedServiceException(CheckedExceptionBusiness.SiteLink, CheckedExceptionResult.NULL_PARAM,
					"已是行内最"+(up?"前":"后"));
		}
		return new ResponseBean(neighbor_id);
	}


}
