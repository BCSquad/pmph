package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookVideo;
import com.bc.pmpheep.back.vo.BookVideoTrackVO;
import com.bc.pmpheep.back.vo.PastBookVideoVO;
import com.bc.pmpheep.back.vo.BookVideoVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface BookVideoService {

    Integer addBookVideoFront(Long userId, BookVideo bookVideo, MultipartFile cover) throws CheckedServiceException, IOException;
    
    Integer addBookVideo(String sessionId, BookVideo bookVideo, MultipartFile cover) throws CheckedServiceException, IOException;

    /**
     * 动态更新
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午5:10:58
     * @param bookVideo
     * @return
     * @throws CheckedServiceException
     */
    Integer updateBookVideo(BookVideo bookVideo) throws CheckedServiceException;

    /**
     * 根据文件id删除BookVideo 2018年2月6日 上午11:11:36
     *
     * @param id
     * @return
     */
    Integer deleteBookVideoByIds(List<Long> ids) throws CheckedServiceException;

    /**
     * 获取书籍视频列表
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午3:29:39
     * @return
     */
    PageResult<PastBookVideoVO> getList(Integer pageSize, Integer pageNumber, String bookName);

    /**
     * 获取书籍视频列表2
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午3:29:39
     * @return
     */
    PageResult<BookVideoVO> getVideoList(Integer pageSize, Integer pageNumber, String bookName, Integer state, String upLoadTimeStart, String upLoadTimeEnd);
    /**
     * 获取书籍视频列表2
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午3:29:39
     * @return
     */
    List<BookVideoTrackVO> getVideoTrackList(Integer pageSize, Integer pageNumber, String bookName, Integer state, String upLoadTimeStart, String upLoadTimeEnd);

}
