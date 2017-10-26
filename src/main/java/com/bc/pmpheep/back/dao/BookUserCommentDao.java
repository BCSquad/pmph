package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.BookUserComment;
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
}
