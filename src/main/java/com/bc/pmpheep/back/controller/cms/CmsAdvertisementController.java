package com.bc.pmpheep.back.controller.cms;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.service.CmsAdvertisementService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.CmsAdvertisementOrImageVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * @desc 广告管理
 * @author mr
 * @cratedate 2017-12-19
 */
@Controller
@RequestMapping(value = "/cms")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmsAdvertisementController {
	@Autowired
	CmsAdvertisementService cmsAdvertisementService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "广告管理";

	/**
	 * 获取广告列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "初始化广告列表")
	@RequestMapping(value = "/cmsAdvertisement/list", method = RequestMethod.GET)
	public ResponseBean list(HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(cmsAdvertisementService.getAdvertisementList(sessionId));
	}

	/**
	 * 广告管理页面修改广告
	 * 
	 * @param cmsAdvertisement
	 * @param file
	 * @return	影响行数
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改广告")
	@RequestMapping(value = "/cmsAdvertisement/update", method = RequestMethod.PUT)
	public ResponseBean update(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO,HttpServletRequest request,
			@RequestParam(name="imageId")Long[] imageId,@RequestParam(name="disable")Long[] disable,@RequestParam(name="imageJumpUrl")String[] imageJumpUrl)
			throws CheckedServiceException {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(cmsAdvertisementService.updateCmsAdvertisement(cmsAdvertisementOrImageVO, sessionId,imageId,disable,imageJumpUrl));
	}

	/**
	 * 更新广告
	 * 
	 * @param cmsAdvertisement
	 * @param request
	 * @return 影响行数
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新广告")
	@RequestMapping(value = "/cmsAdvertisement/updateOfback", method = RequestMethod.PUT)
	public ResponseBean updateOfback(CmsAdvertisement cmsAdvertisement, HttpServletRequest request)
			throws CheckedServiceException, IOException {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(cmsAdvertisementService.updateCmsAdvertisement(cmsAdvertisement, sessionId));
	}

	/**
	 * 广告管理页面新增广告
	 * 
	 * @param cmsAdvertisement
	 * @param file
	 * @return	CmsAdvertisement 对象
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增广告")
	@RequestMapping(value = "/cmsAdvertisement/add", method = RequestMethod.POST)
	public ResponseBean add(CmsAdvertisement cmsAdvertisement,CmsAdvertisementImage cmsAdvertisementImage, MultipartFile file, HttpServletRequest request)
			throws CheckedServiceException, IOException {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(cmsAdvertisementService.addCmsAdvertisement(cmsAdvertisement,cmsAdvertisementImage, file, sessionId));
	}

	/**
	 * 广告编辑页面增加图片
	 * @param cmsAdvertisementOrImageVO
	 * @param request
	 * @param file
	 * @return
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "广告编辑页面增加图片")
	@RequestMapping(value = "/cmsAdvertisement/addimage", method = RequestMethod.POST)
	public ResponseBean addimage(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO,
			HttpServletRequest request,MultipartFile file) throws CheckedServiceException, IOException {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(cmsAdvertisementService.addCmsAdevertisementImage(cmsAdvertisementOrImageVO,file,sessionId));
	}
	/**
	 * 广告编辑页面批量删除图片
	 * @param image
	 * @param advertId
	 * @param request
	 * @return
	 * @throws CheckedServiceException
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量删除图片")
	@RequestMapping(value = "/cmsAdvertisement/delete", method = RequestMethod.DELETE)
	public ResponseBean delete(String[] image,Long[] id,HttpServletRequest request) throws CheckedServiceException {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(cmsAdvertisementService.deleteCmsAdvertisementImageById(image,id,sessionId));
	}
}
