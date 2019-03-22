package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookSupport;
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
	Long addBook(Book book);

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
	 * 查询一个 Book 通过书籍名称
	 * 
	 * @param Book
	 *            必须包含主键ID
	 * @return Book
	 */
	Book getBookByBookname(String bookname);

	/**
	 * 查询一个 BookSupport 通过书籍名称
	 * 
	 * @param Book
	 *            必须包含主键ID
	 * @return Book
	 */
	BookSupport getBookSupport(@Param("bookId") Long bookId, @Param("supportId") Long supportId);

	/**
	 * 新增一个BookSupport
	 * 
	 * @param Book
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addBookSupport(@Param("id") Long id, @Param("bookId") Long bookId);

	/**
	 * 删除BookSupport
	 * 
	 * @param Book
	 *            实体对象
	 * @return 影响行数
	 */
	void deletedBookSupport();

	/**
	 * 查询一个 Book 通过isbn
	 * 
	 * @param Book
	 *            必须包含主键ID
	 * @return Book
	 */
	Book getBookByIsbn(String isbn);
	
	/**
	 * 查询一个 Book 通过vn
	 * 
	 * @param Book
	 *            必须包含主键ID
	 * @return Book
	 */
	Book getBookByVn2(String vn);

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
	 * 功能描述：获取所有书籍的本版号
	 *
	 * @param vn
	 *            本版号
	 * @return
	 *
	 */
	List<String> getBookByVn();

	Book getBookByBookVn(String vn);

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
	 * 功能描述：更新评论数+1
	 *
	 * @param id
	 * @throws CheckedServiceException
	 *
	 */
	void updateUpComments(@Param("id") Long id);

	/**
	 * 
	 * 
	 * 功能描述：更新评论数-1
	 *
	 * @param id
	 * @throws CheckedServiceException
	 *
	 */
	void updateDownComments(@Param("id") Long id);

	/**
	 * 
	 * 
	 * 功能描述：获取所有书籍
	 *
	 * @return
	 *
	 */
	List<Long> listBookId();

	/**
	 * 
	 * 
	 * 功能描述：批量删除book
	 *
	 * @param id
	 *
	 */
	void deleted();

    List<BookVO> recommendlist(PageParameter<BookVO> pageParameter);

	int recommendTotal(PageParameter<BookVO> pageParameter);

	/**
	 * 查询书籍是否存在关系
	 * @param currentBookId
	 * @param selectType
	 * @param recommendBookId
	 * @return
	 */
	Boolean recommendisExist(@Param("currentBookId") Long currentBookId,@Param("selectType") int selectType,@Param("recommendBookId") Long recommendBookId);

	/**
	 * 插入书籍关系
	 * @param currentBookId
	 * @param selectType
	 * @param recommendBookId
	 */
	void insertrecommend(@Param("currentBookId") Long currentBookId,@Param("selectType") int selectType,@Param("recommendBookId") Long recommendBookId);

	/**
	 * 删除书籍关系
	 * @param currentBookId
	 * @param selectType
	 * @param recommendBookId
	 */
	void deleterecommend(@Param("currentBookId") Long currentBookId,@Param("selectType") int selectType,@Param("recommendBookId") Long recommendBookId);

	/**
	 * 获取当前书的最大Sort值
	 * @return
	 */
    Long getMaxSort();

	List<Book> queryTscxReadList(PageParameter<Map<String, Object>> pageParameter);
	List<Book> querySellwelList(PageParameter<Map<String, Object>> pageParameter);

	int queryTscxReadListCount(PageParameter<Map<String, Object>> pageParameter);
	Integer addSellwell(@Param("id") Long id);
	void updateBookSellWell(List<Book> books);
	int updateBookSellWellByid(Map<String,Object> params);

}
