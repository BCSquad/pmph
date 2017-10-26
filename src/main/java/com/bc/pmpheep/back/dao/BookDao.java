package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.Book;

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
}
