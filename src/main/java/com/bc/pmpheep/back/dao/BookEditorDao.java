package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.BookEditor;

/**
 * BookEditor 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface BookEditorDao {

	/**
	 * 新增一个 BookEditor
	 * 
	 * @param bookEditor
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addBookEditor(BookEditor bookEditor);

	/**
	 * 根据主键删除BookEditor
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return 影响行数
	 */
	Integer deleteBookEditorById(Long id);

	/**
	 * 更新BookEditor不为null的字段
	 * 
	 * @param bookEditor
	 *            实体对象
	 * @return 影响行数
	 */
	Integer updateBookEditor(BookEditor bookEditor);

	/**
	 * 根据主键获取BookEditor
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return BookEditor
	 */
	BookEditor getBookEditorById(Long id);

	/**
	 * 
	 * 
	 * 功能描述：根据书籍id删除
	 *
	 * @param id
	 *
	 */
	void deleteBookEditorByBookIds(List<Long> id);
}
