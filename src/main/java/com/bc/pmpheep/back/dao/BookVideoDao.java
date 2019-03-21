package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.vo.BookVideoTrackVO;
import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.po.BookVideo;
import com.bc.pmpheep.back.vo.BookVideoVO;

@org.springframework.stereotype.Repository
public interface BookVideoDao {

	/**
	 * 新增 vedio
	 *
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年1月31日 9:24:01
	 * @param bookVideo
	 * @return
	 */
	Integer addBookVideo(BookVideo bookVideo);

	/**
	 * 根据文件ids删除BookVideo 2018年2月6日 上午11:11:36
	 *
	 * @param ids
	 * @return
	 */
	Integer deleteBookVideoByIds(@Param("ids") List<Long> ids);

	/**
	 * 获取微视频列表总数
	 *
	 * @return
	 */
	Integer getVideoListTotal(Map<String, Object> map);

	/**
	 * 获取微视频列表
	 *
	 * @return
	 */
	List<BookVideoVO> getVideoList(Map<String, Object> map);
	/**
	 * 获取微视频列表
	 *
	 * @return
	 */
	List<BookVideoTrackVO> getVideoTrackList(Map<String, Object> map);
										/**
	 * 获取vedio根据书籍id
	 *
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年2月6日 下午3:44:19
	 * @param bookIds
	 * @return
	 */
	List<BookVideo> getBookVideoByBookIds(@Param("bookIds") List<Long> bookIds);

	/**
	 * 动态更新
	 *
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年2月6日 下午5:10:58
	 * @param bookVideo
	 * @return
	 */
	Integer updateBookVideo(BookVideo bookVideo);

	/**
	 * 根据文件上传路径获取BookVideo
	 *
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年1月31日 9:24:01
	 * @param bookVideo
	 * @return
	 */
	BookVideo getBookVideoByOldPath(@Param("oldPath") String oldPath);

	/**
	 * 根据书籍id批量删除
	 * 
	 *
	 * @param id
	 *
	 */
	void deleteBookVideoByBookIds();

}
