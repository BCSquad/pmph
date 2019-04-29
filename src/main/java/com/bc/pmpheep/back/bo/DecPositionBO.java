/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.bo;

import java.util.ArrayList;
import java.util.List;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 教材遴选表业务对象，用于Excel/Word(批量)导出
 *
 * @author L.X <gugia@qq.com>
 */
public class DecPositionBO {
	
	private Integer sort;
	
	private String textbookName;
	
    private Integer textbookRound;
    private Long did;

    private List<WriterBO> writers;

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return the textbookName
     */
    public String getTextbookName() {
        return textbookName;
    }

    /**
     * @param textbookName the textbookName to set
     */
    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName;
    }

    /**
     * @return the textbookRound
     */
    public Integer getTextbookRound() {
        return textbookRound;
    }

    /**
     * @param textbookRound the textbookRound to set
     */
    public void setTextbookRound(Integer textbookRound) {
        this.textbookRound = textbookRound;
    }

    /**
     * @return the writers
     */
    public List<WriterBO> getWriters() {
        return writers;
    }

    /**
     * @param writers the writers to set
     */
    public void setWriters(String writers) {
		if(StringUtil.isEmpty(writers)){
			this.writers = new ArrayList<>() ;
			return ;
		}
		Gson gson = new Gson();
		this.writers = gson.fromJson(writers,new TypeToken<ArrayList<WriterBO>>() {}.getType() );

	}

//	public void setWriters(List<WriterBO> writers) {
//		this.writers = writers;
//	}
//    
}
