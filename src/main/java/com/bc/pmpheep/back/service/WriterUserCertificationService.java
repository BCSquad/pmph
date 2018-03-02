package com.bc.pmpheep.back.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.back.po.WriterUserCertification;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * @introduction WriterUserCertificationService 接口
 * 
 * @author Mryang
 * 
 * @createDate 2017年9月19日 上午9:54:29
 * 
 */
public interface WriterUserCertificationService {

    /**
     * 
     * @introduction 新增一个WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:50:09
     * @param writerUserCertification
     * @return 带主键的WriterUserCertification
     * @throws CheckedServiceException
     */
    WriterUserCertification addWriterUserCertification(
    WriterUserCertification writerUserCertification) throws CheckedServiceException;

    /**
     * 
     * @introduction 根据id查询 WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:51:41
     * @param id
     * @return WriterUserCertification
     * @throws CheckedServiceException
     */
    WriterUserCertification getWriterUserCertificationById(Long id) throws CheckedServiceException;

    /**
     * 
     * @introduction 根据id删除WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:52:18
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteWriterUserCertificationById(Long id) throws CheckedServiceException;

    /**
     * 
     * @introduction 根据id 更新writerUserCertification不为null和''的字段
     * @author Mryang
     * @createDate 2017年9月19日 上午9:53:00
     * @param writerUserCertification
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateWriterUserCertification(WriterUserCertification writerUserCertification)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：教师审核按userId更新WriterUserCertification中Progress状态字段
     * 使用示范：
     *
     * @param progress 状态
     * @param userIds 作家用户IDS
     * @return　修改成功条数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateWriterUserCertificationProgressByUserId(Short progress, Long[] userIds,HttpServletRequest request)
    throws CheckedServiceException,Exception;

    /**
     * 
     * <pre>
     * 功能描述：按userIds集合查询WriterUserCertification对象
     * 使用示范：
     * 
     * @param userIds userIds集合
     * @return WriterUserCertification对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<WriterUserCertification> getWriterUserCertificationByUserIds(Long[] userIds)
    throws CheckedServiceException;
}
