package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.vo.MateriaHistorylVO;

/**
 * MaterialExtraDao实体类数据访问层接口
 * 
 * @author 曾庆峰
 * 
 */
@Repository
public interface MaterialExtraDao {

    /**
     * 新增一个MaterialExtra
     * 
     * @param materialExtra 实体对象
     * @return 影响行数
     */
    Integer addMaterialExtra(MaterialExtra materialExtra);

    /**
     * 删除MaterialExtra 通过主键id
     * 
     * @param id
     * @return 影响行数
     */
    Integer deleteMaterialExtraById(Long id);

    /**
     * 根据主键id 更新materialExtra 不为null和不为‘’的字段
     * 
     * @param materialExtra
     * @return 影响行数
     */
    Integer updateMaterialExtra(MaterialExtra materialExtra);

    /**
     * 
     * <pre>
     * 功能描述：根据教材ID查询MaterialExtra对象
     * 使用示范：
     *
     * @param materialId 教材ID
     * @return MaterialExtra对象
     * </pre>
     */
    MaterialExtra getMaterialExtraByMaterialId(Long materialId);

    /**
     * 查询一个 MaterialExtra 通过主键id
     * 
     * @param id 必须包含主键ID
     * @return MaterialExtra
     */
    MaterialExtra getMaterialExtraById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getMartialExtraCount();

    /**
     * 
     * <pre>
     * 功能描述：查询历史教材通知列表
     * 使用示范：
     *
     * @param pageParameter pageSize 当页条数 pageNumber 当前页数
     * @param sessionId
     * @return
     * </pre>
     */
    List<MateriaHistorylVO> listMaterialHistory(PageParameter<MateriaHistorylVO> pageParameter);

    /**
     * 
     * <pre>
     * 功能描述：按attachment修改下载次数 
     * 使用示范：
     *
     * @param attachment  MongoDB附件表的主键
     * @return 影响行数
     * </pre>
     */
    Integer updateMaterialExtraDownLoadCountsByAttachment(String attachment);
}
