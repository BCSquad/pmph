/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecPosition;

/**
 * <p>Title:作家申报职位表实体类<p>
 * <p>Description:作家申报职位表 数据访问层接口<p>
 * @author lyc
 * @date 2017年10月9日 下午4:42:32
 */

@Repository
public interface DecPositionDao {
    /**
     * 
     * Description:新增一个作家申报职位信息
     * @author:lyc
     * @date:2017年10月9日下午4:45:24
     * @Param:
     * @Return:Integer
     */
	Integer DecPosition (DecPosition decPosition);
	
	/**
	 * 
	 * Description:根据id删除一个作家申报职位信息
	 * @author:lyc
	 * @date:2017年10月9日下午4:48:01
	 * @Param:
	 * @Return:Integer
	 */
	Integer deleteDecPosition(Long id);
	
	/**
	 * 
	 * Description:更新作家申报职位信息
	 * @author:lyc
	 * @date:2017年10月9日下午4:48:46
	 * @Param:
	 * @Return:Integer
	 */
	Integer updateDecPosition(DecPosition decPosition);
	
	/**
	 * 
	 * Description:根据id查询一个作家申报职位信息
	 * @author:lyc
	 * @date:2017年10月9日下午4:52:40
	 * @Param:
	 * @Return:DecPosition
	 */
	DecPosition decPositionById(Long id);
	
	/**
	 * 
	 * Description:根据申报表id查询申报职位信息
	 * @author:lyc
	 * @date:2017年10月9日下午4:54:30
	 * @Param:
	 * @Return:List<DecPosition>
	 */
	List<DecPosition> listDecPositions(Long declarationId);
	
	/**
	 * 
	 * Description:查询表的总记录数
	 * @author:lyc
	 * @date:2017年10月9日下午4:55:26
	 * @Param:
	 * @Return:Long
	 */
	Long getDecPosition();
}
