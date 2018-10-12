package com.bc.pmpheep.back.service;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookCorrection;
import com.bc.pmpheep.back.vo.BookCorrectionAuditVO;
import com.bc.pmpheep.back.vo.BookCorrectionTrackVO;
import com.bc.pmpheep.back.vo.BookFeedBack;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.List;

/**
 * BookCorrectionService 接口
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午5:10:35
 *
 **/
public interface BookCorrectionService {
	
	/**
	 * 将状态更新至受理中
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月20日 下午3:50:46
	 * @param id
	 * @return 更新行数
	 */
	Integer updateToAcceptancing(Long id )throws CheckedServiceException;
	
	/**
	 * 回复作家用户
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月20日 下午3:50:46
	 * @param id
	 * @param result
	 * @param editorReply
	 * @return 更新行数
	 */
	Integer replyWriter(Long id ,Boolean result , String editorReply,String authorReply)throws CheckedServiceException;
	
	/**
	 * 获取审核纠错跟踪页面的分页数据
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月20日 上午11:50:06
	 * @param pageNumber
	 * @param pageSize
	 * @param bookname
	 * @param isEditorReplied
	 * @return PageResult<BookCorrectionTrackVO>
	 * @throws CheckedServiceException
	 */
	PageResult<BookCorrectionTrackVO> listBookCorrectionTrack(HttpServletRequest request,
			Integer pageNumber,Integer pageSize,String bookname ,Boolean isEditorReplied,Boolean result) throws CheckedServiceException;

	
	/**
	 * 获取审核纠错页面的分页数据
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月20日 上午11:50:06
	 * @param pageNumber
	 * @param pageSize
	 * @param bookname
	 * @param result
	 * @return
	 * @throws CheckedServiceException
	 */
	PageResult<BookCorrectionAuditVO> listBookCorrectionAudit(HttpServletRequest request,
			Integer pageNumber,Integer pageSize,String bookname,Boolean isOver,Boolean result) throws CheckedServiceException;
	
	/**
	 * 根据id获取审核详情
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年1月29日 上午10:01:50
	 * @param id
	 * @return
	 * @throws CheckedServiceException
	 */
	BookCorrectionAuditVO getBookCorrectionAuditDetailById(Long id ) throws CheckedServiceException;
	
	 /**
     * 新增一个    BookCorrection
     * @param bookCorrection 实体对象
     * @return 带主键的 bookCorrection thorws CheckedServiceException
     */
	BookCorrection addBookCorrection(BookCorrection bookCorrection) throws CheckedServiceException;
	
	/**
	 * 根据主键删除BookCorrection
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer   deleteBookCorrectionById (Long id) throws CheckedServiceException;
    
	 /**
     * 更新BookCorrection不为null的字段
     * @param bookCorrection 实体对象
     * @return 影响行数
     */
	Integer  updateBookCorrection(BookCorrection bookCorrection) throws CheckedServiceException;
	
	/**
	 * 根据主键获取BookCorrection
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return BookCorrection
	 * @throws CheckedServiceException
	 */
	BookCorrection   getBookCorrectionById (Long id) throws CheckedServiceException;


	/**
	 * 获取读者反馈列表
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @param result
	 * @return
	 */
	PageResult<BookFeedBack>  bookFeedBaskList(HttpServletRequest request, Integer pageNumber, Integer pageSize, Boolean result);

	/**
	 * 获取某个具体的读者反馈详情
	 * @param id
	 * @return
	 */
	BookFeedBack getBookFeedBackDetailById(Long id);

	/**
	 * 反馈内容 回复
	 * @param id
	 * @param authorReply
	 * @return
	 */
	Integer replyBookFeedBackWriter(Long id, String authorReply,HttpServletRequest request);

	List<BookFeedBack> exportfeedback(Boolean result);

	/**
	 * 导出图书纠错信息
	 * @return
	 */
	List<BookCorrectionAuditVO> exportBookCheck(String bookname);
}
