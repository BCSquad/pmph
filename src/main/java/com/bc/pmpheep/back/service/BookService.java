package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

public interface BookService {

	/**
	 * 
	 * 
	 * 功能描述：分页初始化/查询图书详情
	 *
	 * @param pageParameter
	 *            分页参数 ，isOnSale 是否上架，isNew 是否新书 ，path 书籍类别根路径 ， isPromote 是否推荐，name
	 *            isbn/图书名称
	 * @return 分好页的结果集
	 * @throws CheckedServiceException
	 *
	 */
	PageResult<BookVO> listBookVO(PageParameter<BookVO> pageParameter) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：批量/单个修改图书详情
	 *
	 * @param ids
	 *            需要修改的id
	 * @param type
	 *            图书类别id
	 * @param isOnSale
	 *            是否上架
	 * @param isNew
	 *            是否新书
	 * @param isPromote
	 *            是否推荐
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	String updateBookById(Long[] ids, Long type, Boolean isOnSale, Boolean isNew, Boolean isPromote)
			throws CheckedServiceException;
}
