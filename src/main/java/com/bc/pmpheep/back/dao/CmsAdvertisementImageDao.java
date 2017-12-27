package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.vo.CmsAdvertisementOrImageVO;

/**
 * CmsAdvertisementImage 实体类数据访问层接口
 * 
 * @author mr
 *
 */
@Repository
public interface CmsAdvertisementImageDao {
	/**
	 * 批量删除图片
	 * @param id
	 * @return
	 */
	Integer deleteCmsAdvertisementByImage(@Param("advertId") Long advertId);
	/**
	 * 保存图片id和关联广告id
	 * @param cmsAdvertisementImage
	 * @return
	 */
	Integer addCmsAdvertisementImage(CmsAdvertisementImage cmsAdvertisementImage);
	/**
	 * 通过广告id修改图片
	 * @param cmsAdvertisementImage
	 * @return
	 */
	Integer updateCmsAdvertisementImage(CmsAdvertisementImage cmsAdvertisementImage);
	/**
	 * 通过图片id删除图片
	 * @param image
	 * @return
	 */
	Integer deleteCmsAdvertisementByImages(Long id);
	
}
