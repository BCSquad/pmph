package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.CmsAdvertisementDao;
import com.bc.pmpheep.back.dao.CmsAdvertisementImageDao;
import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.CmsAdvertisementOrImageVO;
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
	CmsAdvertisementImageDao cmsAdvertisementImageDao;
	@Autowired
	FileService fileService;

	@Override
	public List<CmsAdvertisementOrImageVO> getAdvertisementList(String sessionId) throws CheckedServiceException{
		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		List<CmsAdvertisementOrImageVO> cmsAdvertisementOrImageVOs=cmsAdvertisementDao.getAdvertisementList();
		return cmsAdvertisementOrImageVOs;
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
	public Integer updateCmsAdvertisement(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO, MultipartFile file, String sessionId)
			throws CheckedServiceException, IOException {
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (ObjectUtil.isNull(cmsAdvertisementOrImageVO)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == cmsAdvertisementOrImageVO.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "广告id为空");
		}
		if (null == cmsAdvertisementOrImageVO.getAdname()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "广告名称为空");
		}
		if(null == file){
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "图片为空");
		}
		Integer count = 0;
		// 当有图片的时候
		if (null != file) {
			// 先保存上传的广告图片返回MongoDBid
			String newImage = fileService.save(file, ImageType.CMS_ADVERTISEMENT_IMAGE, cmsAdvertisementOrImageVO.getImageId());
			// 保存本次上传图片的MongoDBid
			cmsAdvertisementOrImageVO.setImage(newImage);
		}
		CmsAdvertisementImage cmsAdvertisementImage=new CmsAdvertisementImage();
		cmsAdvertisementImage.setAdvertId(cmsAdvertisementOrImageVO.getId());
		cmsAdvertisementImage.setIsDisabled(cmsAdvertisementOrImageVO.getIsDisplay());
		//修改图片是否显示
		cmsAdvertisementImageDao.updateCmsAdvertisementImage(cmsAdvertisementImage);
		count = cmsAdvertisementDao.updateCmsAdvertisement(cmsAdvertisementOrImageVO);
		return count;
	}

	@Override
	public CmsAdvertisement addCmsAdvertisement(CmsAdvertisement cmsAdvertisement,CmsAdvertisementImage cmsAdvertisementImage, MultipartFile file, String sessionId)
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
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "广告id");
		}
		if (null == file) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "图片为空");
		}
		// 先保存上传的广告图片返回MongoDBid
		String newImage = fileService.save(file, ImageType.CMS_ADVERTISEMENT_IMAGE, cmsAdvertisementImage.getId());
		// 保存图片id和关联广告id 
		cmsAdvertisementImage.setAdvertId(cmsAdvertisement.getId());
		cmsAdvertisementImage.setImage(newImage);
		cmsAdvertisementImageDao.addCmsAdvertisementImage(cmsAdvertisementImage);
		//保存广告管理设置
		cmsAdvertisementDao.addCmsAdvertisement(cmsAdvertisement);
		return cmsAdvertisement;
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
	public CmsAdvertisementImage addCmsAdevertisementImage(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO, MultipartFile file,
			String sessionId) throws CheckedServiceException,IOException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (ObjectUtil.isNull(cmsAdvertisementOrImageVO)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(null==file){
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "图片为空");
		}
		CmsAdvertisementImage cmsAdvertisementImage=new CmsAdvertisementImage();
		cmsAdvertisementImage.setAdvertId(cmsAdvertisementOrImageVO.getAdvertId());
		cmsAdvertisementImage.setIsDisabled(cmsAdvertisementOrImageVO.getIsDisplay());
		// 保存图片到 MongoDB 返回id存入图片id
		String newImage=fileService.save(file, ImageType.CMS_ADVERTISEMENT_IMAGE,cmsAdvertisementOrImageVO.getImageId());
		cmsAdvertisementImage.setImage(newImage);
		cmsAdvertisementImageDao.addCmsAdvertisementImage(cmsAdvertisementImage);
		return cmsAdvertisementImage;
	}

	@Override
	public Integer deleteCmsAdvertisementImageById(Long advertId, String[] image, String sessionId) {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,"用户为空");
		}
		if(null==advertId){
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(null==image){
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		Integer count=0;
		//遍历数组 进行删除MongoDB的图
		for (int i = 0; i < image.length; i++) {
			fileService.remove(image[i]);
		}
		// 删除本地相对应的图片信息
		count=cmsAdvertisementImageDao.deleteCmsAdvertisementByImage(advertId);
		return count;
	}

}
