/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.bo;

/**
 * 教材遴选表业务对象，用于Excel/Word(批量)导出
 *
 * @author L.X <gugia@qq.com>
 */
public class WriterBO {

    private String realname;

    private String chosenOrgName;

    private Integer chosenPosition;

    private Integer rank;

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
     * @return the chosenOrgName
     */
    public String getChosenOrgName() {
        return chosenOrgName;
    }

    /**
     * @param chosenOrgName the chosenOrgName to set
     */
    public void setChosenOrgName(String chosenOrgName) {
        this.chosenOrgName = chosenOrgName;
    }

    /**
     * @return the chosenPosition
     */
    public Integer getChosenPosition() {
        return chosenPosition;
    }

    /**
     * @param chosenPosition the chosenPosition to set
     */
    public void setChosenPosition(Integer chosenPosition) {
        this.chosenPosition = chosenPosition;
    }

    /**
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
