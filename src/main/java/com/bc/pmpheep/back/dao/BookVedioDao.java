package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.po.BookVedio;
 


@org.springframework.stereotype.Repository
public interface BookVedioDao {

	/**
	 * 新增 vedio
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年1月31日  9:24:01
	 * @param bookVedio
	 * @return
	 */
	Integer addBookVedio(BookVedio bookVedio);

	/**
	 * 根据文件上传路径模糊获取BookVedio
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年1月31日  9:24:01
	 * @param bookVedio
	 * @return
	 */
	BookVedio  getBookVedioByOldPath(@Param("oldPath")String oldPath);
	
	/**
	 * 根据文件id删除BookVedio
	 * 2018年2月6日 上午11:11:36 
	 * @param id
	 * @return
	 */
	Integer deleteBookVedioById(@Param("id")Long id);
	
	
	/**
	 * 获取vedio根据书籍id
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午3:44:19
	 * @param bookIds
	 * @return
	 */
	List<BookVedio>  getBookVedioByBookIds  (@Param("bookIds")List<Long> bookIds);
	
	/**
	 * 动态更新
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午5:10:58
	 * @param bookVedio
	 * @return
	 */
	Integer updateBookVedio(BookVedio bookVedio)  ;
	
	
}
