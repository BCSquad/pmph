package com.bc.pmpheep.back.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.vo.TopicDirectorVO;
import com.bc.pmpheep.back.vo.TopicEditorVO;
import com.bc.pmpheep.back.vo.TopicOPtsManagerVO;

/**
 * 
 * 
 * 功能描述：选题申报数据持久层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月20日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Repository
public interface TopicDao {
	/**
	 * 
	 * 
	 * 功能描述：运维人员查询自己可以操作的选题
	 *
	 * @param userId
	 *            运维人员id
	 * @param bookname
	 *            书籍名称
	 * @param submitTime
	 *            提交时间
	 * @param start
	 *            分页函数
	 * @param pageSize
	 * @return
	 *
	 */
	List<TopicOPtsManagerVO> listOpts(@Param("userId") Long userId, @Param("bookname") String bookname,
			@Param("submitTime") Timestamp submitTime, @Param("start") Integer start,
			@Param("pageSize") Integer pageSize);

	/**
	 * 
	 * 
	 * 功能描述：系统管理员查询选题申报
	 *
	 * @param bookname
	 *            书籍名称
	 * @param submitTime
	 *            提交时间
	 * @param start
	 * @param pageSize
	 * @return
	 *
	 */
	List<TopicOPtsManagerVO> list(@Param("bookname") String bookname, @Param("submitTime") Timestamp submitTime,
			@Param("start") Integer start, @Param("pageSize") Integer pageSize);

	/**
	 * 
	 * 
	 * 功能描述：查询运维人员的选题总数
	 *
	 * @param userId
	 *            运维人员id
	 * @param bookname
	 *            书籍名称
	 * @param submitTime
	 *            提交时间
	 * @return
	 *
	 */
	Integer listOptsTotal(@Param("userId") Long userId, @Param("bookname") String bookname,
			@Param("submitTime") Timestamp submitTime);

	/**
	 * 
	 * 
	 * 功能描述：系统管理员的选题总数
	 *
	 * @param userId
	 *            运维人员id
	 * @param bookname
	 *            书籍名称
	 * @param submitTime
	 *            提交时间
	 * @return
	 *
	 */
	Integer listTotal(@Param("bookname") String bookname, @Param("submitTime") Timestamp submitTime);
	
    /**
     * 
     * Description:部门主任查看被运维人员分配的选题
     * @author:lyc
     * @date:2017年12月20日下午4:26:38
     * @param 
     * @return List<TopicDirectorVO>
     */
	List<TopicDirectorVO> listTopicDirectorVOs(@Param("departmentId") Long departmentId,@Param("bookName") String bookName,
			@Param("submitTime") Timestamp submitTime,@Param("start") Integer start,@Param("pageSize") Integer pageSize);
	
	/**
	 * 
	 * Description:统计部门主任被分配的选题总数
	 * @author:lyc
	 * @date:2017年12月20日下午4:28:58
	 * @param 
	 * @return Integer
	 */
	Integer totalTopicDirectorVOs(@Param("departmentId") Long departmentId,
			@Param("bookName") String bookName,@Param("submitTime") Timestamp submitTime);
	
	/**
	 * 
	 * Description:部门编辑获取被分配的选题
	 * @author:lyc
	 * @date:2017年12月20日下午4:47:20
	 * @param 
	 * @return List<TopicEditorVO>
	 */
	List<TopicEditorVO> listTopicEditorVOs(@Param("userId") Long userId, @Param("bookName") String bookName,
			@Param("submitTime") Timestamp submitTime, @Param("start") Integer start,
			@Param("pageSize") Integer pageSize);
	
	/**
	 * 
	 * Description:部门编辑获取被分配的选题的总数
	 * @author:lyc
	 * @date:2017年12月20日下午4:49:53
	 * @param 
	 * @return Integer
	 */
	Integer totalTopicEditorVOs(@Param("userId") Long userId, @Param("bookName") String bookName,
			@Param("submitTime") Timestamp submitTime);
}
