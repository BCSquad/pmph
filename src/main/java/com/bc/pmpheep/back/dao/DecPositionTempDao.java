package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecPositionTemp;

@Repository
public interface DecPositionTempDao {
	/**
	 * 
	 * Description:新增一个作家申报职位信息
	 * 
	 * @author:lyc
	 * @date:2017年10月9日下午4:45:24
	 * @Param:
	 * @Return:Integer
	 */
	Integer addDecPositionTemp(DecPositionTemp decPositionTemp);

	/**
	 * 
	 * Description:根据id删除一个作家申报职位信息
	 * 
	 * @author:lyc
	 * @date:2017年10月9日下午4:48:01
	 * @Param:
	 * @Return:Integer
	 */
	Integer deleteDecPositionTemp(Long id);

	/**
	 * 
	 * Description:更新作家申报职位信息
	 * 
	 * @author:lyc
	 * @date:2017年10月9日下午4:48:46
	 * @Param:
	 * @Return:Integer
	 */
	Integer updateDecPositionTemp(DecPositionTemp decPositionTemp);

	/**
	 * 
	 * Description:根据id查询一个作家申报职位信息
	 * 
	 * @author:lyc
	 * @date:2017年10月9日下午4:52:40
	 * @Param:
	 * @Return:DecPosition
	 */
	DecPositionTemp getDecPositionTempById(Long id);

}
