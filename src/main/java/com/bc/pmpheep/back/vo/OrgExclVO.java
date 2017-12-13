package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;

/**
 * 
 * <pre>
 * 功能描述：导出已发布教材下的学校
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-13
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class OrgExclVO {
    /**
     * 学校名称
     */
    @ExcelHeader(header = "学校名称")
    private String orgName;
    /**
     * 学校账号
     */
    @ExcelHeader(header = "学校账号")
    private String username;
    /**
     * 教材名称
     */
    private String materialName;

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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the materialName
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * @param materialName the materialName to set
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * <pre>
     * 构造器描述：
     *
     * @param orgName
     * @param username
     * @param materialName
     *</pre>
     */
    public OrgExclVO(String orgName, String username, String materialName) {
        super();
        this.orgName = orgName;
        this.username = username;
        this.materialName = materialName;
    }

}
