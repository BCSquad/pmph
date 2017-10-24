package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.WriterUserCertification;

/**
 * 
 * @introduction WriterUserCertification 实体类数据访问层接口
 * 
 * @author Mryang
 * 
 * @createDate 2017年9月19日 上午10:01:54
 * 
 */
@Repository
public interface WriterUserCertificationDao {

    /**
     * 
     * @introduction 新增一个WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:50:09
     * @param writerUserCertification
     * @return 影响行数
     */
    Integer addWriterUserCertification(WriterUserCertification writerUserCertification);

    /**
     * 
     * @introduction 根据id查询 WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:51:41
     * @param id
     * @return WriterUserCertification
     */
    WriterUserCertification getWriterUserCertificationById(Long id);

    /**
     * 
     * @introduction 根据id删除WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:52:18
     * @param id
     * @return 影响行数
     */
    Integer deleteWriterUserCertificationById(Long id);

    /**
     * 
     * @introduction 根据id 更新writerUserCertification不为null和''的字段
     * @author Mryang
     * @createDate 2017年9月19日 上午9:53:00
     * @param writerUserCertification
     * @return 影响行数
     */
    Integer updateWriterUserCertification(WriterUserCertification writerUserCertification);

    /**
     * 
     * <pre>
	 * 功能描述：教师审核按userId更新WriterUserCertification中Progress状态字段
	 * 使用示范：
	 *
	 * @param writerUserCertification
	 * @return
	 * </pre>
     */
    Integer updateWriterUserCertificationProgressByUserId(
    List<WriterUserCertification> writerUserCertification);

    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的数据总条数
     * </pre>
     */
    Long getWriterUserCertificationCount();
}
