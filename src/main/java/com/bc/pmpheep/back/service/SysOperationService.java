package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SysOperation;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：SysOperationService 接口
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-15
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public interface SysOperationService {
    /**
     * 
     * <pre>
     * 功能描述：新增SysOperation
     * 使用示范：
     *
     * @param sysOperation SysOperation对象
     * </pre>
     */
    SysOperation addSysOperation(SysOperation sysOperation) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取SysOperation列表（同时查询分页数据和总条数）
     * 使用示范：
     *
     * @param pageParameter 
     * @return
     * </pre>
     */
    PageResult<SysOperation> getListSysOperation(PageParameter<SysOperation> pageParameter)
    throws CheckedServiceException;
    
    /**
     * 通过userId
     * @param id
     * @return
     */
	List<SysOperation> getSysOperation(Long userId);

}
