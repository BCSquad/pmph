package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.vo.BookPreferenceAnalysisVO;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

@Repository
public interface BookDao {

	/**
	 * 新增一个Book
	 * 
	 * @param Book
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addBook(Book book);

	/**
	 * 删除Book 通过主键id
	 * 
	 * @param Book
	 * @return 影响行数
	 */
	Integer deleteBookById(Long id);

	/**
	 * 更新一个 Book通过主键id
	 * 
	 * @param Book
	 * @return 影响行数
	 */
	Integer updateBook(Book book);

	/**
	 * 查询一个 Book 通过主键id
	 * 
	 * @param Book
	 *            必须包含主键ID
	 * @return Book
	 */
	Book getBookById(Long id);

	/**
	 * 
	 * 
	 * 功能描述：分页初始化/查询图书详情
	 *
	 * @param pageParameter
	 *            分页参数 ，isOnSale 是否上架，isNew 是否新书 ，type 书籍类别 ， isPromote 是否推荐，name
	 *            isbn/图书名称
	 * @return 分好页的结果集
	 * @throws CheckedServiceException
	 *
	 */
	List<BookVO> listBookVO(PageParameter<BookVO> pageParameter);

	/**
	 * 
	 * 
	 * 功能描述：获取总条数
	 *
	 * @param pageParameter
	 *            分页参数 ，isOnSale 是否上架，isNew 是否新书 ，type 书籍类别 ， isPromote 是否推荐，name
	 *            isbn/图书名称
	 * @return 分好页的结果集
	 * @throws CheckedServiceException
	 *
	 */
	Integer getBookVOTotal(PageParameter<BookVO> pageParameter);

	/**
	 * 
	 * 
	 * 功能描述：根据本版号获取书籍信息
	 *
	 * @param vn
	 *            本版号
	 * @return
	 *
	 */
	Book getBookByVn(String vn);

	/**
	 * 
	 * 
	 * 功能描述：获取图书偏好分析
	 *
	 * @param bookname
	 * @return
	 *
	 */
	List<BookPreferenceAnalysisVO> getBookPreferenceAnalysis(@Param("bookname") String bookname,
			@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("type") Long type,
			@Param("path") String path);

	/**
	 * 
	 * 
	 * 功能描述：获取图书偏好分析总数
	 *
	 * @param bookname
	 * @return
	 *
	 */
	Integer getBookPreferenceAnalysisTotal(@Param("bookname") String bookname, @Param("type") Long type,
			@Param("path") String path);

	/**
	 * 获取书籍总数
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年2月6日 下午4:23:12
	 * @return
	 */
	Integer getListToatl(java.util.Map<String, Object> map);

	/**
	 * 获取数据分页数据
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年2月6日 下午4:23:21
	 * @return
	 */
	List<Book> geList(java.util.Map<String, Object> map);

	/**
	 * 更新书的评分
	 * 
	 * @param bookId
	 * @return 影响行数
	 */
	Integer updateBookCore(@Param("id") Long id);

	/**
	 * 
	 * 
	 * 功能描述：更新评论数
	 *
	 * @param id
	 * @throws CheckedServiceException
	 *
	 */
	void updateComments(@Param("id") Long id);
}
