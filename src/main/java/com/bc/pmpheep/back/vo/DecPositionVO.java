package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("DecPositionVO")
public class DecPositionVO implements Serializable {
	
	private List<NewDecPosition> list;

	public List<NewDecPosition> getList() {
		return list;
	}

	public void setList(List<NewDecPosition> list) {
		this.list = list;
	}

	public DecPositionVO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
