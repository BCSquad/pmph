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

	/**
	 * 
	 * 
	 * 功能描述：通过本版编号来获取人卫系统上的图书商品详情
	 *
	 * @param revision
	 *            本版号
	 * @param type
	 *            访问类型 1：全量同步 2；增量同步
	 * @throws CheckedServiceException
	 *
	 */
	String AbuttingJoint(String[] vns, Integer type) throws CheckedServiceException;
}
