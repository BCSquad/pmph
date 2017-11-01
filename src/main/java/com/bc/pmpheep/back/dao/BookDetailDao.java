package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.BookDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDetailDao {
	/**
	 * ����һ��BookDetail
	 * 
	 * @param BookDetail
	 *            ʵ�����
	 * @return Ӱ������
	 */
	Integer addBookDetail(BookDetail bookDetail);

	/**
	 * ɾ��BookDetail ͨ������id
	 * 
	 * @param BookDetail
	 * @return Ӱ������
	 */
	Integer deleteBookDetailById(Long id);

	/**
	 * ����һ�� BookDetailͨ������id
	 * 
	 * @param BookDetail
	 * @return Ӱ������
	 */
	Integer updateBookDetail(BookDetail bookDetail);

	/**
	 * ��ѯһ�� BookDetail ͨ������id
	 * 
	 * @param BookDetail
	 *            �����������ID
	 * @return BookDetail
	 */
	BookDetail getBookDetailById(Long id);

	/**
	 * 
	 * 
	 * 功能描述：根据书籍id删除书籍详情
	 *
	 * @param bookId
	 *            书籍id
	 * @return
	 *
	 */
	Integer deleteBookDetailByBookId(Long bookId);// 未写mapper方法
}
