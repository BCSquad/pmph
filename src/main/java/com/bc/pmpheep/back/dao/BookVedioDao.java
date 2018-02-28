package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.po.BookVedio;
import com.bc.pmpheep.back.vo.BookVedioVO2;
 


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
	 * 根据文件ids删除BookVedio
	 * 2018年2月6日 上午11:11:36 
	 * @param ids
	 * @return
	 */
	Integer deleteBookVedioByIds(@Param("ids")List<Long> ids);
	
	/**
	 * 获取微视频列表总数
	 * @return
	 */
	Integer           getVedioListTotal(Map<String, Object> map);
	/**
	 * 获取微视频列表
	 * @return
	 */
	List<BookVedioVO2> getVedioList(Map<String, Object> map);
	
	
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
	
	/**
	 * 根据文件上传路径获取BookVedio
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年1月31日  9:24:01
	 * @param bookVedio
	 * @return
	 */
	BookVedio  getBookVedioByOldPath(@Param("oldPath")String oldPath);
	
	
}
