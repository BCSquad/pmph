package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.po.CmsAdvertisement;
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
	List<CmsAdvertisement> getAdvertisementList(String sessionId) throws CheckedServiceException;

	/**
	 * 更新广告内容
	 * 
	 * @param cmsAdvertisement
	 * @return
	 */
	Integer updateCmsAdvertisement(CmsAdvertisement cmsAdvertisement, String sessionId) throws CheckedServiceException;

	/**
	 * 修改广告
	 * 
	 * @param cmsAdvertisement
	 * @param image
	 * @param url
	 * @param id必传
	 * @return
	 * @throws CheckedServiceException,IOException
	 */
	Integer updateCmsAdvertisement(CmsAdvertisement cmsAdvertisement, MultipartFile file, String sessionId)
			throws CheckedServiceException, IOException;

	/**
	 * 增加广告
	 * 
	 * @param cmsAdvertisement
	 * @return
	 * @throws CheckedServiceException,IOException
	 */
	CmsAdvertisement addCmsAdvertisement(CmsAdvertisement cmsAdvertisement, MultipartFile file, String sessionId)
			throws CheckedServiceException, IOException;

	/**
	 * 根据广告位置id删除广告
	 * 
	 * @param id
	 * @return
	 * @throws CheckedServiceException
	 */
	Integer deleteCmsAdvertisementById(Long id, String sessionId) throws CheckedServiceException;

	/**
	 * 根据id获取广告
	 * 
	 * @param id
	 * @return
	 * @throws CheckedServiceException
	 */
	CmsAdvertisement getCmsAdvertisementById(Long id) throws CheckedServiceException;
}