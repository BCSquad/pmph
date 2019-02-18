package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.BookFeedBack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.BookCorrection;
import com.bc.pmpheep.back.vo.BookCorrectionAuditVO;
import com.bc.pmpheep.back.vo.BookCorrectionTrackVO;

/**
 * BookCorrection 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface BookCorrectionDao {

	/**
	 * 新增一个 BookCorrection
	 * 
	 * @param bookCorrection
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addBookCorrection(BookCorrection bookCorrection);

	/**
	 * 根据主键删除BookCorrection
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return 影响行数
	 */
	Integer deleteBookCorrectionById(Long id);

	/**
	 * 更新一个BookCorrection
	 * 
	 * @param bookCorrection
	 *            实体对象
	 * @return 影响行数
	 */
	Integer updateBookCorrection(BookCorrection bookCorrection);

	/**
	 * 根据主键获取BookCorrection
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return BookCorrection
	 */
	BookCorrection getBookCorrectionById(Long id);

	/**
	 * 获取审核纠错页面的分页数据
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月20日 上午11:22:04
	 * @param map
	 * @return
	 */
	List<BookCorrectionAuditVO> listBookCorrectionAudit(Map<String, Object> map);

	/**
	 * 根据id获取审核详情
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年1月29日 上午9:58:50
	 * @param id
	 * @return
	 */
	BookCorrectionAuditVO getBookCorrectionAuditDetailById(@Param("id") Long id);

	/**
	 * 获取审核纠错页面的数据总数
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月20日 上午11:22:04
	 * @param map
	 * @return
	 */
	Integer listBookCorrectionAuditTotal(Map<String, Object> map);

	/**
	 * 获取审核纠错跟踪页面的数据总数
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月20日 上午11:22:04
	 * @param map
	 * @return
	 */
	Integer listBookCorrectionTrackTotal(Map<String, Object> map);

	/**
	 * 获取审核纠错跟踪页面的分页数据
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月20日 上午11:22:04
	 * @param map
	 * @return List<BookCorrectionTrackVO>
	 */
	List<BookCorrectionTrackVO> listBookCorrectionTrack(Map<String, Object> map);

	/**
	 * 
	 * 
	 * 功能描述：根据书籍id删除纠错
	 *
	 * @param id
	 *
	 */
	void deleteBookCoorrectionTrackByBookIds();

	/**
	 * 读者反馈数据总数
	 * @param map
	 * @return
	 */
    Integer bookFeedBackListTotal(Map<String, Object> map);

	/**
	 * 读者反馈数据
	 * @param map
	 * @return
	 */
	List<BookFeedBack> bookFeedBackList(Map<String, Object> map);

	/**
	 * 获取某个具体读者反馈详情
	 * @param id
	 * @return
	 */
    BookFeedBack getBookFeedBackDetailById(@Param("id") Long id);

	/**
	 * 回复 读者反馈
	 * @param id
	 * @param authorReply
	 * @return
	 */
	Integer replyBookFeedBackWriter(@Param("id") Long id, @Param("authorReply") String authorReply,@Param("authorId") Long authorId);

	/**
	 * 导出审核纠错信息
	 * @param map
	 * @return
	 */
	List<BookCorrectionAuditVO> exportBookCheck(Map<String, Object> map);

	/**
	 * 翻转当前id的前台显示
	 * @param id
	 * @param showFront 为true时，修改为false
	 * @return
	 */
	BookCorrectionTrackVO switchFrontShow(@Param("id") Long id,@Param("showFront") Boolean showFront);

	/**
	 * 翻转当前id的前台显示
	 * @param id
	 * @param showFront 为true时，修改为false
	 * @return
	 */
	BookFeedBack switchFrontShowFeedBack(@Param("id") Long id,@Param("showFront") Boolean showFront);
}
