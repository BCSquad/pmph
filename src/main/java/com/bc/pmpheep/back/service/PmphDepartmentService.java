package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.vo.PmphUserDepartmentVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphDepartmentService 接口
 * 
 * @author Mryang
 */
public interface PmphDepartmentService {

    /**
     * 
     * @param PmphDepartment 实体对象
     * @return 带主键的PmphDepartment
     * @throws CheckedServiceException
     */
    PmphDepartment addPmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException;

    /**
     * 
     * @param id
     * @return PmphDepartment
     * @throws CheckedServiceException
     */
    PmphDepartment getPmphDepartmentById(Long id) throws CheckedServiceException;

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deletePmphDepartmentById(Long id) throws CheckedServiceException;

    /**
     * @param PmphDepartment
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updatePmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException;
    
    /**
     * 
     * 功能描述：获取出版社所有部门
     * 使用示范：
     *
     * @return  已经分级的社内部门
     * @throws CheckedServiceException
     */
    PmphUserDepartmentVO getListPmphDepartment() throws CheckedServiceException;

}
