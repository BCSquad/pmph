package com.bc.pmpheep.back.datamigration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.datamigration.transfer.AlterDataBase;

/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 下午4:36:54
 *
 **/ 
@Component
@SuppressWarnings("all")
public class Import  {  
	@Autowired
    private com.bc.pmpheep.back.datamigration.transfer.material.Material material;
	@Autowired
    private AlterDataBase alterDataBase;

    public void start( ) {
    	try {
    		//添加主键
    		alterDataBase.addNewPk();
    		material.transferMaterial() ;
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }  
      
    
  
      
}  
