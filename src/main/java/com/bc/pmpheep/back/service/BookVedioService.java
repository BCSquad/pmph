package com.bc.pmpheep.back.service;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookVedio;
import com.bc.pmpheep.back.vo.BookVedioVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;





public interface BookVedioService {

	/**
	 * 新增 vedio
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年1月31日  9:24:01
	 * @param bookVedio
	 * @return
	 */
	Integer addBookVedio(BookVedio bookVedio) throws CheckedServiceException;
	/**
	 * 动态更新
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午5:10:58
	 * @param bookVedio
	 * @return
	 * @throws CheckedServiceException
	 */
	Integer updateBookVedio(BookVedio bookVedio) throws CheckedServiceException;
	
	
	/**
	 * 根据文件上传路径模糊获取BookVedio
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年1月31日  9:24:01
	 * @param bookVedio
	 * @return
	 */
	BookVedio  getBookVedioByOldPath(@Param("oldPath")String oldPath) throws CheckedServiceException;
	
	/**
	 * 根据文件id删除BookVedio
	 * 2018年2月6日 上午11:11:36 
	 * @param id
	 * @return
	 */
	Integer deleteBookVedioById(@Param("id")Long id) throws CheckedServiceException;
	
	/**
	 * 获取书籍视频列表
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午3:29:39
	 * @return
	 */
	PageResult<BookVedioVO>getList(Integer pageSize,Integer pageNumber,String bookName);
	
	

}
