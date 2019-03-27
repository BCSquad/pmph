package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.vo.BookCourseCountVO;
import com.bc.pmpheep.back.vo.CourseVO;

import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface BookCourseDao {
    int getBookCourseCountListTotal(PageParameter<BookCourseCountVO> pageParameter);

    List<BookCourseCountVO> getBookCourseCountList(PageParameter<BookCourseCountVO> pageParameter);

    List<CourseVO> getCourseByBookId(PageParameter<CourseVO> pageParameter);
    Integer getCourseByBookIdCount(PageParameter<CourseVO> pageParameter);

    List<Map<String,Object>> getOrgByBookId(PageParameter<Map<String,Object>> pageParameter);

    Integer getOrgByBookIdCount(PageParameter<Map<String,Object>> pageParameter);

    List<Map<String,Object>> getTeacherUserByBookId(PageParameter<Map<String,Object>> pageParameter);

    Integer getTeacherUserByBookIdCount(PageParameter<Map<String,Object>> pageParameter);
    List<Map<String,Object>> getStudentUserByBookId(PageParameter<Map<String,Object>> pageParameter);

    Integer getStudentUserByBookIdCount(PageParameter<Map<String,Object>> pageParameter);

    Integer getStudentUserByBookIdCountByList(PageParameter<BookCourseCountVO> pageParameter);



}
