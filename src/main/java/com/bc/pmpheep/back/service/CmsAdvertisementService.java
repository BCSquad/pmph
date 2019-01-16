package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.vo.CmsAdvertisementOrImageVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 广告管理
 * 
 * @author mr
 *
 */
public interface CmsAdvertisementService {
	/**
	 * 获取广告列表
	 * 
	 * @param sessionId
	 * @return
	 */
	List<CmsAdvertisementOrImageVO> getAdvertisementList(String sessionId) throws CheckedServiceException;

	/**
	 * 更新广告内容
	 * 
	 * @param cmsAdvertisement
	 * @return
	 */
	Integer updateCmsAdvertisement(CmsAdvertisement cmsAdvertisement, String sessionId) throws CheckedServiceException;
	
	/**
	 * 修改广告
	 * @param cmsAdvertisementOrImageVO
	 * @param file
	 * @param sessionId
	 * @param imageId
	 * @return
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	Integer updateCmsAdvertisement(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO,
			String sessionId,Long[] imageId,Long[] disable,String[] imageJumpUrl) throws CheckedServiceException;

	/**
	 * 增加广告
	 * @param cmsAdvertisement
	 * @param cmsAdvertisementImage
	 * @param file
	 * @param sessionId
	 * @return
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	CmsAdvertisement addCmsAdvertisement(CmsAdvertisement cmsAdvertisement,CmsAdvertisementImage cmsAdvertisementImage, 
			MultipartFile file, String sessionId)
			throws CheckedServiceException, IOException;

	/**
	 * 根据id获取广告
	 * 
	 * @param id
	 * @return
	 * @throws CheckedServiceException
	 */
	CmsAdvertisement getCmsAdvertisementById(Long id) throws CheckedServiceException;
	
	/**
	 * 增加图片
	 * @param cmsAdvertisementOrImageVO
	 * @param file
	 * @param sessionId
	 * @return
	 * @throws CheckedServiceException,IOException 
	 */
	CmsAdvertisementImage addCmsAdevertisementImage(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO, 
			MultipartFile file, String sessionId)throws CheckedServiceException, IOException;
	/**
	 * 删除图片
	 * @param id
	 * @param image
	 * @param sessionId
	 * @return
	 */
	Integer deleteCmsAdvertisementImageById( String[] image,Long[] id, String sessionId);

}
