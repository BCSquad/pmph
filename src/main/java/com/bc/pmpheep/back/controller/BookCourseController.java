package com.bc.pmpheep.back.controller;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.BookCourseService;
import com.bc.pmpheep.back.vo.BookCourseCountVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("bookCourse")
public class BookCourseController {

    @Autowired
    BookCourseService bookCourseService;

    @RequestMapping(value = "countList",method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean countList(HttpServletRequest request, Integer pageNumber, Integer pageSize, BookCourseCountVO bookCourseCountVO){
        PageResult pageResult  = new PageResult();
        PageParameter<BookCourseCountVO> pageParameter = new PageParameter<>(pageNumber,pageSize);
        pageParameter.setParameter(bookCourseCountVO);
        pageResult = bookCourseService.getBookCourseCountList(pageParameter);
        return new ResponseBean(pageResult);
    }

}
