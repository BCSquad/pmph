package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.BookCourseDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.BookCourseCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }
}
