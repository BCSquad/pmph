package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.BookCourseCountVO;

/**
 * 图书课程选用情况统计
 */
public interface BookCourseService {


    /**
     * 统计列表
     * @param pageParameter
     * @return
     */
    PageResult getBookCourseCountList(PageParameter<BookCourseCountVO> pageParameter);
}
