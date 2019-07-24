package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.BookUserComment;
import com.bc.pmpheep.back.vo.BookUserCommentVO;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface BookUserCommentDao {

	/**
	 * 新增一个BookUserComment
	 * 
	 * @param BookUserComment
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addBookUserComment(BookUserComment bookUserComment);

	/**
	 * 删除BookUserComment 通过主键id
	 * 
	 * @param BookUserComment
	 * @return 影响行数
	 */
	Integer deleteBookUserCommentById(Long id);

	/**
	 * 更新一个 BookUserComment通过主键id
	 * 
	 * @param BookUserComment
	 * @return 影响行数
	 */
	Integer updateBookUserComment(BookUserComment bookUserComment);



	/**
	 * 查询一个 BookUserComment 通过主键id
	 * 
	 * @param BookUserComment
	 *            必须包含主键ID
	 * @return BookUserComment
	 */
	BookUserComment getBookUserCommentById(Long id);

	/**
	 * 
	 * 
	 * 功能描述：分页初始化/模糊查询图书评论
	 *
	 * @param pageParameter
	 *            分页参数，name ibsn/书籍名称 ，isAuth 是否通过审核
	 * @return 分好页的结果集
	 *
	 */
	List<BookUserCommentVO> listBookUserComment(PageParameter<BookUserCommentVO> pageParameter);

	/**
	 * 
	 * 
	 * 功能描述：获取评论总数
	 *
	 * @param pageParameter
	 *            name ibsn/书籍名称 ，isAuth 是否通过审核
	 * @return 总数
	 *
	 */
	Integer getBookUserCommentTotal(PageParameter<BookUserCommentVO> pageParameter);

	/**
	 * 
	 * 
	 * 功能描述：删除图书评论
	 *
	 * @param ids
	 *            需要删除的评论id数组
	 * @return 影响行数
	 *
	 */
	Integer deleteBookUserCommentById(Long[] ids);

	/**
	 * 
	 * 
	 * 功能描述：根据书籍id删除书籍评论
	 *
	 * @param bookId
	 *            书籍id
	 * @return
	 *
	 */
	Integer deleteBookUserCommentByBookId(Long bookId);

	/**
	 * 
	 * 
	 * 功能描述：删除图书评论
	 *
	 * @param ids
	 *            需要删除的评论id数组
	 * @return 影响行数
	 *
	 */
	Integer deleteBookUserCommentBookIds();

	/**
	 * admin查询图书评论数量
	 * 
	 * @param pageParameter
	 * @return
	 */
	Integer getBookUserCommentAdminTotal(PageParameter<BookUserCommentVO> pageParameter);

	/**
	 * admin查询图书评论
	 * 
	 * @param pageParameter
	 * @return
	 */
	List<BookUserCommentVO> listBookUserCommentAdmin(PageParameter<BookUserCommentVO> pageParameter);
	/**
	 * 
	 * 
	 * 功能描述：获取最小推荐排序
	 *
	 * @param bookId
	 * @return
	 *
	 */
	Integer getMinSort(Long bookId);
}
