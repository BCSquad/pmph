package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.SearchKeyword;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * 功能描述：敏感词数据实现层
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
public interface SearchKeywordDao {
	/**
	 * 
	 * 
	 * 功能描述：添加一个敏感词对象
	 *
	 * @param SearchKeyword
	 * @return
	 *
	 */
	Integer add(SearchKeyword SearchKeyword);

	/**
	 * 
	 * 
	 * 功能描述：修改敏感词
	 *
	 * @param SearchKeyword
	 * @return
	 *
	 */
	Integer update(SearchKeyword SearchKeyword);
	/**
	 * 
	 * 
	 * 功能描述：通过敏感词查询id
	 *
	 * @param word
	 * @return
	 *
	 */
	SearchKeyword getSearchKeywordId(String word);

	/**
	 * 
	 * 
	 * 功能描述：获取敏感词总条数
	 *
	 * @param word
	 * @return
	 *
	 */
	Integer getTotal(@Param("word") String word);

	/**
	 * 
	 * 
	 * 功能描述：获取敏感词列表并分页
	 *
	 * @param word
	 * @param pageSize
	 * @param start
	 * @return
	 *
	 */
	List<SearchKeyword> list(@Param("word") String word, @Param("pageSize") Integer pageSize,
                         @Param("start") Integer start);

	/**
	 * 
	 * 
	 * 功能描述：批量逻辑删除敏感词
	 *
	 * @param id
	 * @return
	 *
	 */
	Integer deletedIsDeleted(Long[] id);

	/**
	 * 
	 * 
	 * 功能描述：物理删除敏感词
	 *
	 * @param id
	 * @return
	 *
	 */
	Integer delete(Long id);
}
