package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.SysOperation;

/**
 * 
 * <pre>
 * 功能描述：系统操作日志实体类，数据访问层接口
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
@Repository
public interface SysOperationDao {
    /**
     * 
     * <pre>
     * 功能描述：新增SysOperation
     * 使用示范：
     *
     * @param sysOperation SysOperation对象
     * </pre>
     */
    Integer addSysOperation(SysOperation sysOperation);

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
    List<SysOperation> getListSysOperation(PageParameter<SysOperation> pageParameter);
    
    /**
     * 通过用户id 查询
     * @param userId
     * @return
     */
	List<SysOperation> getSysOperation(Long userId);
}
