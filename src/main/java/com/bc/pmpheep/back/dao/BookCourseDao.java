package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.vo.BookCourseCountVO;

import java.util.List;

public interface BookCourseDao {
    int getBookCourseCountListTotal(PageParameter<BookCourseCountVO> pageParameter);

    List<BookCourseCountVO> getBookCourseCountList(PageParameter<BookCourseCountVO> pageParameter);
}
