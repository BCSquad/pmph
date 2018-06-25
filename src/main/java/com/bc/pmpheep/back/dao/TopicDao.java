package com.bc.pmpheep.back.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.Topic;
import com.bc.pmpheep.back.vo.TopicDeclarationVO;
import com.bc.pmpheep.back.vo.TopicDirectorVO;
import com.bc.pmpheep.back.vo.TopicEditorVO;
import com.bc.pmpheep.back.vo.TopicOPtsManagerVO;
import com.bc.pmpheep.back.vo.TopicTextVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
			@Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2, @Param("start") Integer start,
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
	List<TopicOPtsManagerVO> list(@Param("bookname") String bookname, @Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2,
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
			@Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2);

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
	Integer listTotal(@Param("bookname") String bookname, @Param("submitTime1") String submitTime1, @Param("submitTime2") String submitTime2);

	/**
	 * 
	 * 
	 * 功能描述：修改选题申报
	 *
	 * @param topic
	 * @return
	 *
	 */
	Integer update(Topic topic);

	/**
	 * 
	 * Description:部门主任查看被运维人员分配的选题
	 * 
	 * @author:lyc
	 * @date:2017年12月20日下午4:26:38
	 * @param
	 * @return List<TopicDirectorVO>
	 */
	List<TopicDirectorVO> listTopicDirectorVOs(@Param("userId") Long userId, @Param("bookname") String bookname,
			@Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2, @Param("start") Integer start,
			@Param("pageSize") Integer pageSize);

	/**
	 * 
	 * Description:统计部门主任被分配的选题总数
	 * 
	 * @author:lyc
	 * @date:2017年12月20日下午4:28:58
	 * @param
	 * @return Integer
	 */
	Integer totalTopicDirectorVOs(@Param("userId") Long userId, @Param("bookname") String bookname,
			@Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2);

	/**
	 * 
	 * Description:部门编辑获取被分配的选题
	 * 
	 * @author:lyc
	 * @date:2017年12月20日下午4:47:20
	 * @param
	 * @return List<TopicEditorVO>
	 */
	List<TopicEditorVO> listTopicEditorVOs(@Param("userId") Long userId, @Param("bookname") String bookname,
			@Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2, @Param("start") Integer start,
			@Param("pageSize") Integer pageSize);

	/**
	 * 
	 * Description:部门编辑获取被分配的选题的总数
	 * 
	 * @author:lyc
	 * @date:2017年12月20日下午4:49:53
	 * @param
	 * @return Integer
	 */
	Integer totalTopicEditorVOs(@Param("userId") Long userId, @Param("bookname") String bookname,
			@Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2);

	/**
	 * 
	 * 
	 * 功能描述：获取选题申报详情
	 *
	 * @param id
	 * @return
	 *
	 */
	TopicTextVO getTopicTextVO(Long id);

	/**
	 * 
	 * 
	 * 功能描述：查看选题申报
	 *
	 * @param authProgress
	 *            审核进度
	 * @param editor_id_list
	 * @param departmentId
	 * @return
	 *
	 */
	List<TopicDeclarationVO> listCheckTopic(@Param("authProgress") List<Long> authProgress,
											@Param("pageSize") Integer pageSize, @Param("start") Integer start, @Param("bookname") String bookname,
											@Param("submitTime1") String submitTime1,
											@Param("submitTime2") String submitTime2,
											@Param("editor_id_list") List<Long> editor_id_list,
											@Param("departmentId")Long departmentId);

	/**
	 * 
	 * 
	 * 功能描述：查看选题申报数量
	 *
	 * @param authProgress
	 *            审核进度
	 * @param editor_id_list
	 * @param departmentId
	 * @return
	 *
	 */
	Integer listCheckTopicTotal(@Param("authProgress") List<Long> authProgress,
								@Param("bookname") String bookname,
								@Param("submitTime1") String submitTime1,
								@Param("submitTime2") String submitTime2,
								@Param("editor_id_list") List<Long> editor_id_list,
								@Param("departmentId")Long departmentId);

	/**
	 * 
	 * Description:系统管理员查询选题申报（主任界面）
	 * 
	 * @author:lyc
	 * @date:2017年12月22日上午9:22:09
	 * @param
	 * @return List<TopicDirectorVO>
	 */
	List<TopicDirectorVO> listDirectorView(@Param("bookname") String bookname,
			@Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2, @Param("start") Integer start,
			@Param("pageSize") Integer pageSize);

	/**
	 * 
	 * Description:系统管路员查询的选题申报数（主任界面）
	 * 
	 * @author:lyc
	 * @date:2017年12月22日上午9:27:23
	 * @param
	 * @return Integer
	 */
	Integer totalDirectorView(@Param("bookname") String bookname, @Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2);

	/**
	 * 
	 * Description:系统管理员查询选题申报（编辑界面）
	 * 
	 * @author:lyc
	 * @date:2017年12月22日上午9:24:14
	 * @param
	 * @return List<TopicEditorVO>
	 */
	List<TopicEditorVO> listEditorView(@Param("bookname") String bookname, @Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2,
			@Param("start") Integer start, @Param("pageSize") Integer pageSize);

	/**
	 * 
	 * Description:系统管理员查询选题申报数（编辑界面）
	 * 
	 * @author:lyc
	 * @date:2017年12月22日上午9:28:37
	 * @param
	 * @return Integer
	 */
	Integer totalEditorView(@Param("bookname") String bookname, @Param("submitTime1") String submitTime1,@Param("submitTime2") String submitTime2);

	/**
	 * 
	 * 
	 * 功能描述：新增topic
	 *
	 * @param topic
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	Long add(Topic topic) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：获取最大的本版号
	 *
	 * @return
	 *
	 */
	String getMaxTopicVn();

	/**
	 * 
	 * 
	 * 功能描述：根据本版号vn修改选题信息
	 *
	 * @param topic
	 * @return
	 *
	 */
	Integer updateByVn(Topic topic);

	Topic get(Long id);
	
	/**
	 * 查询选题申报的数量
	 * @param authProgress
	 * @param bookname
	 * @param submitTime
	 * @return
	 */
	Integer listMyTopicTotal(@Param("authProgress") List<Long> authProgress, @Param("bookname") String bookname,
			@Param("submitTime") Timestamp submitTime,@Param("isDirectorHandling") Boolean isDirectorHandling,
			@Param("isEditorHandling") Boolean isEditorHandling, @Param("isOptsHandling") Boolean isOptsHandling,
			@Param("editorId") Long editorId);
	/**
	 * 	查询当前用户相关的选题申报
	 * @param authProgress
	 * @param pageSize
	 * @param start
	 * @param bookname
	 * @param submitTime
	 * @return
	 */
	List<TopicDeclarationVO> listMyTopic(@Param("authProgress") List<Long> authProgress, @Param("pageSize") Integer pageSize,
			@Param("start")  Integer start,@Param("bookname") String bookname,
			@Param("submitTime") Timestamp submitTime,@Param("isDirectorHandling")  Boolean isDirectorHandling,
			@Param("isEditorHandling")Boolean isEditorHandling,@Param("isOptsHandling") Boolean isOptsHandling,
			@Param("editorId") Long editorId);
	/**
	 * 运维受理总数
	 * @param userId
	 * @return
	 */
	Integer listIsOptsTopicTotal(@Param("userId") Long userId);
	/**
	 *  运维受理
	 * @param userId
	 * @return
	 */
	List<TopicOPtsManagerVO> listIsOptsTopic(@Param("userId")Long userId,@Param("pageSize") Integer pageSize,
			@Param("start")  Integer start);
	
	/**
	 * 主任受理
	 * @param userId
	 * @return
	 */
	List<TopicDirectorVO> listIsDirectorTopic(@Param("userId") Long userId,@Param("pageSize") Integer pageSize,
			@Param("start")  Integer start);
	/**
	 * 主任受理总数
	 * @param userId
	 * @return
	 */
	Integer listIsDirectorTopicTotal(@Param("userId") Long userId);
	
	/**
	 * 编辑受理总数
	 * @param userId
	 * @return
	 */
	Integer listIsEditorTotal(@Param("userId") Long userId);
	/**
	 * 编辑受理
	 * @param userId
	 * @return
	 */
	List<TopicEditorVO> listIsEditor(@Param("userId") Long userId,@Param("pageSize") Integer pageSize,
			@Param("start")  Integer start);
}
