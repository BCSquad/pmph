package com.bc.pmpheep.back.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.po.PmphGroupMember;

/**
 * 用于包装接收List参数的类 
 * 
 *@author MrYang 
 *@CreateDate 2017年10月11日 上午11:41:53
 *
 **/
public class ListPar {
	//成员集合
	private List<PmphGroupMember> pmphGroupMembers = new ArrayList<PmphGroupMember>(16);
	
	//文件集合
	private List<MultipartFile>   files = new ArrayList<MultipartFile> (16) ;
	
	//id的集合
	private List<Long> ids              =new ArrayList<Long> (16) ;

	public List<PmphGroupMember> getPmphGroupMembers() {
		return pmphGroupMembers;
	}

	public void setPmphGroupMembers(List<PmphGroupMember> pmphGroupMembers) {
		this.pmphGroupMembers = pmphGroupMembers;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	

	
	
}
