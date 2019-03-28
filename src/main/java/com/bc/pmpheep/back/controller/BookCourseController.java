package com.bc.pmpheep.back.controller;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.BookCourseService;
import com.bc.pmpheep.back.vo.BookCourseCountVO;
import com.bc.pmpheep.back.vo.CourseVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "courseDeatil",method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean courseDeatil(HttpServletRequest request, Integer pageNumber, Integer pageSize, CourseVO courseVO){
        PageResult pageResult  = new PageResult();
        PageParameter<CourseVO> pageParameter = new PageParameter<>(pageNumber,pageSize);
        pageParameter.setParameter(courseVO);
        pageResult = bookCourseService.getCourseByBookId(pageParameter);
        return new ResponseBean(pageResult);
    }

    @RequestMapping(value = "orgDeatil",method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean orgDeatil(HttpServletRequest request, Integer pageNumber, Integer pageSize, CourseVO courseVO){
        PageResult pageResult  = new PageResult();
        String bookId = request.getParameter("bookId");
        String orgName=request.getParameter("orgName");
        Map<String,Object> params=new HashMap<>();
        params.put("bookId",bookId);
        params.put("orgName",orgName);
        PageParameter<Map<String,Object>> pageParameter = new PageParameter<>(pageNumber,pageSize);
        pageParameter.setParameter(params);
        pageResult = bookCourseService.getOrgByBookId(pageParameter);
        return new ResponseBean(pageResult);
    }

    @RequestMapping(value = "writerUserDeatil",method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean witerUserDeatil(HttpServletRequest request, Integer pageNumber, Integer pageSize){
        String bookId = request.getParameter("bookId");
        String username=request.getParameter("username");
        Integer type=Integer.parseInt(request.getParameter("type"));
        Map<String,Object> params=new HashMap<>();
        params.put("bookId",bookId);
        params.put("username",username);
        PageResult pageResult  = new PageResult();
        PageParameter<Map<String,Object>> pageParameter = new PageParameter<>(pageNumber,pageSize);
        pageParameter.setParameter(params);
        pageResult = bookCourseService.getWriterUserByBookId(pageParameter,type);
        return new ResponseBean(pageResult);
    }




}
