package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.vo.BookCourseCountVO;
import com.bc.pmpheep.back.vo.CourseVO;

import java.util.List;
import java.util.Map;

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



    PageResult getCourseByBookId(PageParameter<CourseVO> pageParameter);



    PageResult getOrgByBookId(PageParameter<Map<String,Object>> pageParameter);



    PageResult getWriterUserByBookId(PageParameter<Map<String,Object>> pageParameter,int type);


}
