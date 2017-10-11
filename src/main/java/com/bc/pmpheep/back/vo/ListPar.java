package com.bc.pmpheep.back.vo;

import java.util.ArrayList;
import java.util.List;
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

	public List<PmphGroupMember> getPmphGroupMembers() {
		return pmphGroupMembers;
	}

	public void setPmphGroupMembers(List<PmphGroupMember> pmphGroupMembers) {
		this.pmphGroupMembers = pmphGroupMembers;
	}
	
}
