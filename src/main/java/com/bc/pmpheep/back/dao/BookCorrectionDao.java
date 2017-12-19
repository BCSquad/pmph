package com.bc.pmpheep.back.dao;



import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.BookCorrection;


/**
 * BookCorrectionDao  实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface BookCorrectionDao {

	 /**
     * 新增一个    BookCorrection
     * @param bookCorrection 实体对象
     * @return  影响行数
     */
	Integer addBookCorrection(BookCorrection bookCorrection) ;
	
	/**
	 * 根据主键删除BookCorrection
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return  影响行数
	 */
	Integer   deleteBookCorrectionById (Long id) ;
    
	 /**
     * 更新一个BookCorrection
     * @param bookCorrection 实体对象
     * @return 影响行数
     */
	Integer  updateBookCorrection(BookCorrection bookCorrection);
	
	/**
	 * 根据主键获取BookCorrection
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return BookCorrection
	 */
	BookCorrection   getBookCorrectionById (Long id) ;
}
