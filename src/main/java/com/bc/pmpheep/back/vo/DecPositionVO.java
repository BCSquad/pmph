package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
@Alias("DecPositionVO")
public class DecPositionVO implements Serializable {
	
	private List<NewDecPosition> lst ;
	
	
	
	
	
	public List<NewDecPosition> getLst() {
		return lst;
	}



	public void setLst(List<NewDecPosition> lst) {
		this.lst = lst;
	}



	public DecPositionVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}




