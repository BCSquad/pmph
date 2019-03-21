package com.bc.pmpheep.back.vo;

/**
 * 图书课程选用情况统计 实体类
 */
public class BookCourseCountVO {

    private String courseName;

    private String orgName;

    private String teacherName;

    private String bookname;

    private String isbn;

    private Integer statusId;

    private String startTime;

    private String endTime;

    private Boolean orderPlaced;

    private Boolean paid;

    private Integer courseCount;

    private Integer orgCount;

    private Integer teacherCount;

    private Integer studentCount;


    public BookCourseCountVO() {
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
        if(statusId == 1){
            //已创建
            this.orderPlaced = false;
            this.paid = false;
        }else if(statusId == 2){
            //已下单
            this.orderPlaced = true;
            this.paid = false;
        }else if(statusId == 3){
            //已支付
            this.orderPlaced = null;
            this.paid = true;
        }else if(statusId == 0){
            this.paid = null;
            this.orderPlaced = null;
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public Integer getOrgCount() {
        return orgCount;
    }

    public void setOrgCount(Integer orgCount) {
        this.orgCount = orgCount;
    }

    public Integer getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(Integer teacherCount) {
        this.teacherCount = teacherCount;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Boolean getOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(Boolean orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
