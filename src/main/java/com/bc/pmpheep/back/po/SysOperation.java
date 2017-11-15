package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：系统操作日志
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-15
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("serial")
@Alias("SysOperation")
public class SysOperation implements java.io.Serializable {

    // 主键id
    private Long      id;
    // 操作用户ID
    private Long      userId;
    // 操作用户
    private String    userName;
    // 操作用户真实姓名
    private String    userRealName;
    // 操作时间
    private Timestamp operateDate;
    // 操作内容
    private String    operateText;
    // 操作人IP
    private String    clientIp;
    private Integer   count;

    // Constructors

    /** default constructor */
    public SysOperation() {
    }

    /** minimal constructor */
    public SysOperation(Long userId, String userName, Timestamp operateDate, String operateText) {
        this.userId = userId;
        this.userName = userName;
        this.operateDate = operateDate;
        this.operateText = operateText;
    }

    /** full constructor */
    public SysOperation(Long userId, String userName, String userRealName, Timestamp operateDate,
    String operateText, String clientIp) {
        this.userId = userId;
        this.userName = userName;
        this.userRealName = userRealName;
        this.operateDate = operateDate;
        this.operateText = operateText;
        this.clientIp = clientIp;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return this.userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public Timestamp getOperateDate() {
        return this.operateDate;
    }

    public void setOperateDate(Timestamp operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperateText() {
        return this.operateText;
    }

    public void setOperateText(String operateText) {
        this.operateText = operateText;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @Override
    public String toString() {
        return "{id:" + id + ", userId:" + userId + ", userName:" + userName + ", userRealName:"
               + userRealName + ", operateDate:" + operateDate + ", operateText:" + operateText
               + ", clientIp:" + clientIp + "}";
    }

}