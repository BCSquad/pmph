package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bc.pmpheep.back.vo.CmsAdvertisementVos;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.CmsAdvertisementDao;
import com.bc.pmpheep.back.dao.CmsAdvertisementImageDao;
import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.CmsAdvertisementOrImageVO;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;

/**
 * 广告管理实现
 * 
 * @author mr
 *
 */
@Service
public class CmsAdvertisementServiceImpl implements CmsAdvertisementService {

	@Autowired
	CmsAdvertisementDao cmsAdvertisementDao;
	@Autowired
	CmsAdvertisementImageDao cmsAdvertisementImageDao;
	@Autowired
	FileService fileService;

	@Override
	public List<CmsAdvertisementOrImageVO> getAdvertisementList(String sessionId) throws CheckedServiceException {
		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		List<CmsAdvertisementOrImageVO> cmsAdvertisements = cmsAdvertisementDao.getAdvertisementList();
			for (CmsAdvertisementOrImageVO cmsAdvertisementVO : cmsAdvertisements) {
				if (pmphUser.getIsAdmin()) {
					cmsAdvertisementVO.setIsPlay(true);
				}
				List<CmsAdvertisementImage> cmsAdvertisementImages = cmsAdvertisementDao.getAdvertisementImageListByAdverId(cmsAdvertisementVO.getId());

				if(ObjectUtil.notNull(cmsAdvertisementImages)){
					for (CmsAdvertisementImage image:
						 cmsAdvertisementImages) {
						image.setImage("/pmpheep/image/"+image.getImage());

					}
					cmsAdvertisementVO.setImage(cmsAdvertisementImages);
				}
			}


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
		if (!pmphUser.getIsAdmin()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有操作权限");
		}
		if (ObjectUtil.isNull(cmsAdvertisement)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == cmsAdvertisement.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "广告id");
		}
		count = cmsAdvertisementDao.updateCmsAdvertisement(cmsAdvertisement);
		return count;
	}

	@Override
	public Integer updateCmsAdvertisement(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO, String sessionId,
			Long[] imageId, Long[] disable,String[] imageJumpUrl) throws CheckedServiceException {
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (!pmphUser.getIsAdmin()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有操作权限");
		}
		if (ObjectUtil.isNull(cmsAdvertisementOrImageVO)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null != cmsAdvertisementOrImageVO.getAdname()) {
			if (StringUtil.strLength(cmsAdvertisementOrImageVO.getAdname()) > 25) {
				throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
						"最多只能输入25个字符");
			}
			CmsAdvertisement cmsAdvertisement = cmsAdvertisementDao
					.getCmsAdvertisementById(cmsAdvertisementOrImageVO.getId());
			if (!cmsAdvertisementOrImageVO.getAdname().equals(cmsAdvertisement.getAdname())) {
				if (cmsAdvertisementDao.getCmsAdvertisementByName(cmsAdvertisementOrImageVO.getAdname()).size() > 0) {
					throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
							CheckedExceptionResult.ILLEGAL_PARAM, "广告位置名称已存在，请重新输入");
				}
			}
		}
		if (null == cmsAdvertisementOrImageVO.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"广告id为空");
		}
		if (null == cmsAdvertisementOrImageVO.getAdname()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"广告名称为空");
		}
		if (null == imageId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null != cmsAdvertisementOrImageVO.getUrl()) {
			if (StringUtil.strLength(cmsAdvertisementOrImageVO.getUrl()) > 150) {
				throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
						"最多只能输入150个字符");
			}
		}
		if (null != cmsAdvertisementOrImageVO.getNote()) {
			if (StringUtil.strLength(cmsAdvertisementOrImageVO.getNote()) > 20) {
				throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
						"备注最多只能输入20个字符");
			}
		}
		if(null==cmsAdvertisementOrImageVO.getNote()){
			cmsAdvertisementOrImageVO.setNote("");
		}
		if(null==cmsAdvertisementOrImageVO.getUrl()){
			cmsAdvertisementOrImageVO.setUrl("");
		}
		Integer count = 0;
		CmsAdvertisementImage cmsAdvertisementImage = new CmsAdvertisementImage();
		if (0 != imageId.length) {
			for (int i = 0; i < imageId.length; i++) {
				cmsAdvertisementImage.setId(imageId[i]);
				cmsAdvertisementImage.setIsDisabled((Boolean) cmsAdvertisementOrImageVO.getIsDisplay());
				cmsAdvertisementImage.setImageJumpUrl(imageJumpUrl.length>0?imageJumpUrl[i]:null);
				// 修改图片为显示
				cmsAdvertisementImageDao.updateCmsAdvertisementImage(cmsAdvertisementImage);
			}
		}
		if (0 != disable.length) {
			for (int i = 0; i < disable.length; i++) {
				// 把不启用的图片更改禁用
				cmsAdvertisementImage.setId(disable[i]);
				cmsAdvertisementImageDao.updateImageIsDisabled(cmsAdvertisementImage);
			}
		}
		count = cmsAdvertisementDao.updateCmsAdvertisement(cmsAdvertisementOrImageVO);
		return count;
	}

	@Override
	public CmsAdvertisement addCmsAdvertisement(CmsAdvertisement cmsAdvertisement,
			CmsAdvertisementImage cmsAdvertisementImage, MultipartFile file, String sessionId)
			throws CheckedServiceException, IOException {
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (!pmphUser.getIsAdmin()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有操作权限");
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
		// 保存广告管理设置
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
	public CmsAdvertisementImage addCmsAdevertisementImage(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO,
			MultipartFile file, String sessionId) throws CheckedServiceException, IOException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (!pmphUser.getIsAdmin()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有操作权限");
		}
		if (ObjectUtil.isNull(cmsAdvertisementOrImageVO)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == file) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "图片为空");
		}
		CmsAdvertisementImage cmsAdvertisementImage = new CmsAdvertisementImage();
		cmsAdvertisementImage.setAdvertId(cmsAdvertisementOrImageVO.getAdvertId());
		// 新增图片默认不启用
		cmsAdvertisementImage.setIsDisabled(Const.TRUE);
		// 因新建图片信息，未有图片id，先插入芒果DB默认路径
		cmsAdvertisementImage.setImage(RouteUtil.MONGODB_IMAGE);
		cmsAdvertisementImageDao.addCmsAdvertisementImage(cmsAdvertisementImage);
		// 保存图片到 MongoDB 返回id存入图片id
		String newImage = fileService.save(file, ImageType.CMS_ADVERTISEMENT_IMAGE, cmsAdvertisementImage.getId());
		// 替换之前图片id的临时值
		cmsAdvertisementImage.setImage(newImage);
		cmsAdvertisementImageDao.updateCmsAdvertisementImage(cmsAdvertisementImage);
		// 拼接芒果DB图片路径
		cmsAdvertisementImage.setImage(RouteUtil.MONGODB_IMAGE.concat(newImage));
		return cmsAdvertisementImage;
	}

	@Override
	public Integer deleteCmsAdvertisementImageById( String[] image,Long[] id, String sessionId) {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (!pmphUser.getIsAdmin()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有操作权限");
		}
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == image) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		Integer count = 0;
		// 遍历数组 进行删除MongoDB的图
		for (int i = 0; i < image.length; i++) {
			if(null!=id[i]){
				//取芒果图片上保存的id
				String ids=image[i].substring(15);
				fileService.remove(ids);
			}
		}
		// 删除本地相对应的图片信息
		for (int i = 0; i < id.length; i++) {
			if(null!=id[i]){
				count = cmsAdvertisementImageDao.deleteCmsAdvertisementByImages(id[i]);
			}
		}
		return count;
	}

}
