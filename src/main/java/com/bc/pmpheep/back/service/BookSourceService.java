package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageResult;

import com.bc.pmpheep.back.vo.BookSource;
import com.bc.pmpheep.back.vo.BookSourceVO;

import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface BookSourceService {

    Integer addBookSourceFront(Long userId, BookSource bookVideo, MultipartFile cover) throws CheckedServiceException, IOException;
    
    Integer addBookSource(String sessionId, BookSource bookVideo, String[] file, HttpServletRequest request) throws CheckedServiceException, IOException;

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
    Integer updateBookSource(BookSource bookVideo) throws CheckedServiceException;

    /**
     * 根据文件id删除BookSource 2018年2月6日 上午11:11:36
     *
     * @param id
     * @return
     */
    Integer deleteBookSourceByIds(List<Long> ids) throws CheckedServiceException;



    /**
     * 获取书籍视频列表2
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午3:29:39
     * @return
     */
    PageResult<BookSourceVO> getSourceList(Integer pageSize, Integer pageNumber, String bookName, Integer state, String upLoadTimeStart, String upLoadTimeEnd);

}
