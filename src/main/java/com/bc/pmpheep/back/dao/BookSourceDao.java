package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.vo.BookSource;
import com.bc.pmpheep.back.vo.BookSourceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public interface BookSourceDao {

	/*
	 * 新增 vedio
	 *
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年1月31日 9:24:01
	 * @param BookSource
	 * @return
	 * */

	Integer addBookSource(BookSource BookSource);

	/**
	 * 根据文件ids删除BookSource 2018年2月6日 上午11:11:36
	 *
	 * @param ids
	 * @return
	 */
	Integer deleteBookSourceByIds(@Param("ids") List<Long> ids);

	/**
	 * 获取微视频列表总数
	 *
	 * @return
	 */
	Integer getSourceListTotal(Map<String, Object> map);

	/**
	 * 获取微视频列表
	 *
	 * @return
	 */
	List<BookSourceVO> getSourceList(Map<String, Object> map);

	/**
	 * 获取vedio根据书籍id
	 *
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年2月6日 下午3:44:19
	 * @param bookIds
	 * @return
	 */
	List<BookSource> getBookSourceByBookIds(@Param("bookIds") List<Long> bookIds);


	Integer updateBookSource(BookSource BookSource);




	void deleteBookSourceByBookIds();

}
