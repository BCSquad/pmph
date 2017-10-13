/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:业务层<p>
 * <p>Description:作家申报职位业务层接口<p>
 * @author lyc
 * @date 2017年10月9日 下午5:53:39
 */
public interface DecPositionService {
	/**
	 * @Param DecPosition 实体对象
	 * @Return DecPosition带主键
	 * @throws CheckedServiceException
	 */
    DecPosition decPosition(DecPosition decPosition) throws CheckedServiceException;
    
    /**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
    Integer deleteDecPosition(Long id) throws CheckedServiceException;
    
    /**
	 * @Param DecPosition 实体对象
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
    Integer updateDecPosition(DecPosition decPosition) throws CheckedServiceException;
    
    /**
   	 * @Param id
   	 * @Return DecPosition 实体对象
   	 * @throws CheckedServiceException
   	 */  
    DecPosition decPositionById(Long id) throws CheckedServiceException;
    
    /**
   	 * @Param declarationId
   	 * @Return DecPosition 实体对象集合
   	 * @throws CheckedServiceException
   	 */ 
    List<DecPosition> listDecPositions(Long declarationId) throws CheckedServiceException;
    
    /**
     * 
     *  
     * 功能描述：根据书籍id获取申报表id
     *
     * @param textbookId 书籍id
     * @return 申报表id结果集
     * @throws CheckedServiceException
     *
     */
    List<DecPosition> listDecPositionsByTextbookId(Long textbookId) throws CheckedServiceException;
}
