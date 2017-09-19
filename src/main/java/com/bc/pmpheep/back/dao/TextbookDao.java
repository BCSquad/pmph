package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.Textbook;

/**
 * TextbookDao实体类数据访问层接口
 * 
 * @author 曾庆峰
 *
 */
@Repository
public interface TextbookDao {

	/**
	 * 新增一个Textbook
	 * 
	 * @param Textbook
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addTextbook(Textbook textbook);

	/**
	 * 删除Textbook 通过主键id
	 * 
	 * @param Textbook
	 * @return 影响行数
	 */
	Integer deleteTextbookById(Long id);

	/**
	 * 更新一个 Textbook通过主键id
	 * 
	 * @param Textbook
	 * @return 影响行数
	 */
	Integer updateTextbook(Textbook textbook);

	/**
	 * 查询一个 Textbook 通过主键id
	 * 
	 * @param Textbook
	 *            必须包含主键ID
	 * @return Textbook
	 */
	Textbook getTextbookById(Long id);

}
