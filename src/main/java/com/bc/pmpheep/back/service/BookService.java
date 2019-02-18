package com.bc.pmpheep.back.service;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookDetail;
import com.bc.pmpheep.back.po.BookUserLike;
import com.bc.pmpheep.back.po.BookUserMark;
import com.bc.pmpheep.back.vo.BookPreferenceAnalysisVO;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.List;
import java.util.Map;

public interface BookService {

	/**
	 * 添加图书
	 *
	 * @param book
	 *            图书实例
	 * @return 包含主键的插入对象
	 */
	Book add(Book book) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：商城更新图书的接口
	 *
	 * @param noteicetype
	 *            通知类型 0：修改
	 * @param key
	 *            本版号
	 * @return
	 *
	 */
	String AbuttingJoint(String[] key, Integer noteicetype) throws CheckedServiceException;

	String AbuttingJoint(String[] key, Integer noteicetype,String materialName) throws CheckedServiceException;

	/**
	 * 保存图书详情
	 *
	 * @param detail
	 *            图书详情实例
	 * @return 包含主键的插入对象
	 */
	BookDetail add(BookDetail detail) throws CheckedServiceException;

	/**
	 * 图书被点赞
	 *
	 * @param like
	 *            点赞关联数据
	 * @return 包含主键的插入对象
	 */
	BookUserLike add(BookUserLike like) throws CheckedServiceException;

	/**
	 * 图书被收藏
	 *
	 * @param mark
	 *            收藏关联数据
	 * @return 包含主键的插入对象
	 */
	BookUserMark add(BookUserMark mark) throws CheckedServiceException;

	/**
	 * 更新书的评分
	 * 
	 * @param bookId
	 * @return 影响行数
	 */
	Integer updateBookCore(Long id) throws CheckedServiceException;

	/**
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
	 * 书的分页数据
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年2月6日 下午4:19:33
	 * @param pageParameter
	 * @return
	 * @throws CheckedServiceException
	 */
	PageResult<Book> listBook(Integer pageSize, Integer pageNumber, String bookName) throws CheckedServiceException;

	/**
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
	String updateBookById(Long[] ids, Long type, Boolean isOnSale, Boolean isNew, Boolean isPromote, Long materialId,Boolean isKey,Boolean isStick)
			throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：根据书籍id删除书籍信息
	 *
	 * @param id
	 *            书籍id
	 * @return 是否成功
	 * @throws CheckedServiceException
	 *
	 */
	String deleteBookById(Long id) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：书籍同步
	 * 
	 * @param type
	 *            2：增量 1：全量
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	String AllSynchronization(Integer type) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：获取图书偏好分析数据
	 *
	 * @param bookname
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	PageResult<BookPreferenceAnalysisVO> getBookPreferenceAnalysis(
			PageParameter<BookPreferenceAnalysisVO> pageParameter) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：更新评论数+1
	 *
	 * @param id
	 * @throws CheckedServiceException
	 *
	 */
	void updateUpComments(Long id) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：更新评论数-1
	 *
	 * @param id
	 * @throws CheckedServiceException
	 *
	 */
	void updateDownComments(Long id) throws CheckedServiceException;

	/**
	 * 图书配套导入
	 * @param file 上传的图书配套Excel
	 * @return
	 * @throws CheckedServiceException
	 */
	String bookExcel(MultipartFile file) throws CheckedServiceException;

	/**
	 *  推荐图书 查询
	 * @param pageParameter
	 * @return
	 */
	PageResult<BookVO> recommendlist(PageParameter<BookVO> pageParameter);

    Boolean recommendcheck(Long currentBookId,int selectType,Long recommendBookId,Boolean ischeckteachbook,Boolean ischeckxgcommend,Boolean ischeckrwcommend);
	public List<Book> queryTscxReadList(PageParameter<Map<String, Object>> pageParameter);
	public List<Book> querySellwelList(PageParameter<Map<String, Object>> pageParameter);
	public int queryTscxReadListCount(PageParameter<Map<String, Object>> pageParameter);
	String updataSellwell(List<Book> books) throws CheckedServiceException;
	public int updateBookSellWellByid(Long id);

}
