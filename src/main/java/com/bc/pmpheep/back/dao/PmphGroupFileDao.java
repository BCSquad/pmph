package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;

/**
 * PmphGroupFile 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphGroupFileDao {
    /**
     * 
     * @param pmphGroupFile 实体对象
     * @return 影响行数
     */
    Integer addPmphGroupFile(PmphGroupFile pmphGroupFile);

    /**
     * 
     * @param id 主键id
     * @return PmphGroupFile
     */
    PmphGroupFile getPmphGroupFileById(Long id);

    /**
     * 
     * @param id 主键id
     * @return 影响行数
     */
    Integer deletePmphGroupFileById(List<Long> ids);

    /**
     * 全字段更新
     * 
     * @Param： pmphGroupFile
     * @Return： 影响行数
     */
    Integer updatePmphGroupFile(PmphGroupFile pmphGroupFile);

    /**
     * 
     * Description:查询总共的条数
     * 
     * @author:lyc
     * @date:2017年9月30日下午1:48:36
     * @Param:pageParameter
     * @Return:Integer查询到的数据数量
     */
    Integer getGroupFileTotal(PageParameter<PmphGroupFileVO> pageParameter);

    /**
     * 
     * Description:获取小组共享文件列表
     * 
     * @author:lyc
     * @date:2017年9月30日上午9:20:07
     * @Param:pageParameter查询条件，若有文件名则为模糊查询
     * @Return:List<PmphGroupFileVO>需要的小组共享文件集合
     */
    List<PmphGroupFileVO> listGroupFile(PageParameter<PmphGroupFileVO> pageParameter);

    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getPmphGroupFileCount();
}
