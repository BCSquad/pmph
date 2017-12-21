package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.CmsAdvertisementDao;
import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 广告管理实现
 * 
 * @author mr
 *
 */
@Service
public class CmsAdvertisementServiceImpl  implements CmsAdvertisementService {

	@Autowired
	CmsAdvertisementDao cmsAdvertisementDao;
	@Autowired
	FileService fileService;

	@Override
	public List<CmsAdvertisement> getAdvertisementList(String sessionId) throws CheckedServiceException{
		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		List<CmsAdvertisement> cmsAdvertisements=cmsAdvertisementDao.getAdvertisementList();
		return cmsAdvertisements;
	}

	@Override
	public Integer updateCmsAdvertisement(CmsAdvertisement cmsAdvertisement, String sessionId)
			throws CheckedServiceException {
		Integer count = 0;
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (ObjectUtil.isNull(cmsAdvertisement)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == cmsAdvertisement.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "广告id");
		}
		count=cmsAdvertisementDao.updateCmsAdvertisement(cmsAdvertisement);
		return count;
	}

	@Override
	public Integer updateCmsAdvertisement(CmsAdvertisement cmsAdvertisement, MultipartFile file, String sessionId)
			throws CheckedServiceException, IOException {
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (ObjectUtil.isNull(cmsAdvertisement)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == cmsAdvertisement.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "广告id为空");
		}
		if (null == cmsAdvertisement.getAdname()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "广告名称为空");
		}
		if(null == file){
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "图片为空");
		}
		Integer count = 0;
		// 当有图片的时候
		if (null != file) {
			// 先保存上传的广告图片返回MongoDBid
			String newImage = fileService.save(file, ImageType.CMS_ADVERTISEMENT_IMAGE, cmsAdvertisement.getId());
			// 移除以前的广告图片
			fileService.remove(cmsAdvertisement.getImage());
			// 保存本次上传图片的MongoDBid
			cmsAdvertisement.setImage(newImage);
		}
		count = cmsAdvertisementDao.updateCmsAdvertisement(cmsAdvertisement);
		return count;
	}

	@Override
	public CmsAdvertisement addCmsAdvertisement(CmsAdvertisement cmsAdvertisement, MultipartFile file, String sessionId)
			throws CheckedServiceException, IOException {
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (ObjectUtil.isNull(cmsAdvertisement)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.isNull(cmsAdvertisement)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == cmsAdvertisement.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "广告id");
		}
		if (null == file) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "图片为空");
		}
		// 先保存上传的广告图片返回MongoDBid
		String newImage = fileService.save(file, ImageType.CMS_ADVERTISEMENT_IMAGE, cmsAdvertisement.getId());
		cmsAdvertisement.setImage(newImage);
		cmsAdvertisementDao.addCmsAdvertisement(cmsAdvertisement);
		return cmsAdvertisement;
	}

	@Override
	public Integer deleteCmsAdvertisementById(Long id, String sessionId) throws CheckedServiceException {
		Integer count=0;
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		CmsAdvertisement cmsAdvertisement = this.getCmsAdvertisementById(id);
		// 移除对应的广告图片
		fileService.remove(cmsAdvertisement.getImage());
		count=cmsAdvertisementDao.deleteCmsAdvertisementById(id);
		return count;
	}

	@Override
	public CmsAdvertisement getCmsAdvertisementById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		CmsAdvertisement cmsAdvertisement = cmsAdvertisementDao.getCmsAdvertisementById(id);
		return cmsAdvertisement;
	}

	@Override
	public Integer deleteCmsAdvertisementByImage(Long id, String sessionId) throws CheckedServiceException {
		Integer count=0;
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		CmsAdvertisement cmsAdvertisement=this.getCmsAdvertisementById(id);
		// 移除广告图片
		fileService.remove(cmsAdvertisement.getImage());
		count=cmsAdvertisementDao.deleteCmsAdvertisementByImage(id);
		return count;
	}

}
