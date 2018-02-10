package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：书籍对应申报者
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-2-9
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("TextBookDecPositionVO")
public class TextBookDecPositionVO implements Serializable {

    //
    private static final long serialVersionUID = 7342840891723381722L;
    // 作家id
    private Long              userId;
    // 作家真实姓名
    private String            realname;
    // 作家申报单位
    private String            orgName;
    // 书籍ID
    private String[]          textBookIds;
    private Integer           count;

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the textBookIds
     */
    public String[] getTextBookIds() {
        return textBookIds;
    }

    /**
     * @param textBookIds the textBookIds to set
     */
    public void setTextBookIds(String[] textBookIds) {
        this.textBookIds = textBookIds;
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

}
