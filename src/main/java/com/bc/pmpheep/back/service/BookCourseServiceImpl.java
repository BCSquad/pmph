package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.BookCourseDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.vo.BookCourseCountVO;
import com.bc.pmpheep.back.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookCourseServiceImpl implements BookCourseService {

    @Autowired
    BookCourseDao bookCourseDao;

    @Override
    public PageResult getBookCourseCountList(PageParameter<BookCourseCountVO> pageParameter) {
        PageResult pageResult  = new PageResult();
        int total = bookCourseDao.getBookCourseCountListTotal(pageParameter);
        List<BookCourseCountVO> list = new ArrayList<>();
        if(total>0){
            //bookCourseDao.preBookCourseCountList(pageParameter);
            list = bookCourseDao.getBookCourseCountList(pageParameter);
        }
        for(BookCourseCountVO b:list){

             BookCourseCountVO parameter = pageParameter.getParameter();
            parameter.setBookId(b.getBookId());

             Integer stuc = bookCourseDao.getStudentUserByBookIdCountByList(pageParameter);
             b.setStudentCount(stuc==null?0:stuc);

        }

        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }


    @Override
    public PageResult getCourseByBookId(PageParameter<CourseVO> pageParameter) {
        PageResult pageResult  = new PageResult();
        int total = bookCourseDao.getCourseByBookIdCount(pageParameter);
        List<CourseVO> list = new ArrayList<>();
        if(total>0){
            list = bookCourseDao.getCourseByBookId(pageParameter);
        }

        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }

    @Override
    public PageResult getOrgByBookId(PageParameter<Map<String, Object>> pageParameter) {
        PageResult pageResult  = new PageResult();
        int total = bookCourseDao.getOrgByBookIdCount(pageParameter);
        List<Map<String,Object>> list = new ArrayList<>();
        if(total>0){
            list =  bookCourseDao.getOrgByBookId(pageParameter);
        }

        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;


    }



    @Override
    public PageResult getWriterUserByBookId(PageParameter<Map<String, Object>> pageParameter,int type) {
        PageResult pageResult  = new PageResult();
        int total ;
        if(type==1){
            total=bookCourseDao.getTeacherUserByBookIdCount(pageParameter);
        }else{
            total=bookCourseDao.getStudentUserByBookIdCount(pageParameter);
        }

        List<Map<String,Object>> list = new ArrayList<>();
        if(total>0){
            if(type==1){
                list =  bookCourseDao.getTeacherUserByBookId(pageParameter);
            }else{
                list =  bookCourseDao.getStudentUserByBookId(pageParameter);
            }

        }
        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }


}
