package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.SiteLink;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * 功能描述：友情链接数据实现层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018年1月29日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Repository
public interface SiteLinkDao {
	/**
	 * 
	 * 
	 * 功能描述：添加一个友情链接对象
	 *
	 * @param SiteLink
	 * @return
	 *
	 */
	Integer add(SiteLink SiteLink);

	/**
	 * 
	 * 
	 * 功能描述：修改友情链接
	 *
	 * @param SiteLink
	 * @return
	 *
	 */
	Integer update(SiteLink SiteLink);
	/**
	 * 
	 * 
	 * 功能描述：通过友情链接查询id
	 *
	 * @param name
	 * @return
	 *
	 */
	SiteLink getSiteLinkId(String name);

	/**
	 * 
	 * 
	 * 功能描述：获取友情链接总条数
	 *
	 * @param name
	 * @return
	 *
	 */
	Integer getTotal(@Param("name") String name);

	/**
	 * 
	 * 
	 * 功能描述：获取友情链接列表并分页
	 *
	 * @param name
	 * @param pageSize
	 * @param start
	 * @return
	 *
	 */
	List<SiteLink> list(@Param("name") String name, @Param("pageSize") Integer pageSize,
                             @Param("start") Integer start);

	/**
	 * 
	 * 
	 * 功能描述：批量逻辑删除友情链接
	 *
	 * @param id
	 * @return
	 *
	 */
	Integer deletedIsDeleted(Long[] id);

	/**
	 * 
	 * 
	 * 功能描述：物理删除友情链接
	 *
	 * @param id
	 * @return
	 *
	 */
	Integer delete(Long id);

	/**
	 * 行内上移
	 * @param id
	 * @return
	 */
	Long pushForward(Long id);

	/**
	 * 行内下移
	 * @param id
	 * @return
	 */
	Long pushBack(Long id);
}
