package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.vo.CmsAdvertisementVos;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.vo.CmsAdvertisementOrImageVO;

/**
 * CmsAdvertisement 实体类数据访问层接口
 * 
 * @author mr
 *
 */
@Repository
public interface CmsAdvertisementDao {
	/**
	 * 获取广告列表
	 * 
	 * @return
	 */
	List<CmsAdvertisementOrImageVO> getAdvertisementList();

	List<CmsAdvertisementImage> getAdvertisementImageListByAdverId(Long id);

	/**
	 * 更新广告
	 * 
	 * @param cmsAdvertisement
	 * @return
	 */
	Integer updateCmsAdvertisement(CmsAdvertisementOrImageVO cmsAdvertisementOrImageVO);

	/**
	 * 增加广告
	 * 
	 * @param cmsAdvertisement
	 * @return
	 */
	Integer addCmsAdvertisement(CmsAdvertisement cmsAdvertisement);

	/**
	 * 删除广告
	 * 
	 * @param id
	 * @return
	 */
	Integer deleteCmsAdvertisementById(Long id);

	/**
	 * 根据id获取广告
	 * 
	 * @param id
	 * @return
	 */
	CmsAdvertisement getCmsAdvertisementById(@Param("id") Long id);
	/**
	 * 批量删除图片
	 * @param id
	 * @return
	 */
	Integer deleteCmsAdvertisementByImage(Long id);
	/**
	 * 保存图片id和关联广告id
	 * @param cmsAdvertisementImage
	 * @return
	 */
	CmsAdvertisementImage addCmsAdvertisementImage(CmsAdvertisementImage cmsAdvertisementImage);
	/**
	 * 更新广告
	 * @param cmsAdvertisement
	 * @return
	 */
	Integer updateCmsAdvertisement(CmsAdvertisement cmsAdvertisement);
	
	/**
	 * 通过广告名称获取广告
	 * @param adname
	 * @return
	 */
	List<CmsAdvertisement> getCmsAdvertisementByName(String adname);
	
}
