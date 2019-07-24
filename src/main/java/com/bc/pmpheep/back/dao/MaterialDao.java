package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.po.MaterialPosition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.vo.MaterialListVO;

/**
 * MaterialDao实体类数据访问层接口
 * 
 * @author 曾庆峰
 * 
 */
@Repository
public interface MaterialDao {

    /**
     * 新增一个Material
     * 
     * @param Material 实体对象
     * @return 影响行数
     */
    Integer addMaterial(Material material);

    /**
     * 删除Material 通过主键id
     * 
     * @param Material
     * @return 影响行数
     */
    Integer deleteMaterialById(Long id);

    /**
     * 通过主键id更新material 不为null 的字段
     * 
     * @param Material
     * @return 影响行数
     */
    Integer updateMaterial(Material material);

    /**
     * 查询一个 Material 通过主键id
     * 
     * @param Material 必须包含主键ID
     * @return Material
     */
    Material getMaterialById(Long id);

    /**
     * 获取教材名称 通过主键id
     * 
     * @param id
     * @return Material
     * @return 教材名称
     */
    String getMaterialNameById(Long id);

    /**
     * 
     * <pre>
	 * 功能描述：查询表单的数据总条数
	 * 使用示范：
	 *
	 * &#64;return 表单的总条数
	 * </pre>
     */
    Long getMaterialCount();

    /**
     * 
     * <pre>
	 * 功能描述：获取教材集合
	 * 使用示范：
	 *
	 * &#64;param materialName 教材名称
	 * &#64;return
	 * </pre>
     */
    List<Material> getListMaterial(@Param("materialName") String materialName);

    /**
     * 
     * 
     * 功能描述：初始化/未公布或者已公布状态下的教材列表
     * 
     * @param pageParameter
     * @return
     * 
     */
    List<MaterialListVO> listMaterial(PageParameter<MaterialListVO> pageParameter);

    /**
     * 
     * 
     * 功能描述：初始化/未公布或者已公布状态下的教材列表的条数
     * 
     * @param pageParameter
     * @return
     * 
     */
    Integer listMaterialTotal(PageParameter<MaterialListVO> pageParameter);

    /**
     * 
     * 
     * 功能描述：已结束状态下的教材列表
     * 
     * @param pageParameter
     * @return
     * 
     */
    List<MaterialListVO> listMaterialEnd(PageParameter<MaterialListVO> pageParameter);
    List<MaterialListVO> listMaterialOrEndSignUpEnd(PageParameter<MaterialListVO> pageParameter);

    /**
     * 
     * 
     * 功能描述：已结束状态下的教材列表的条数
     * 
     * @param pageParameter
     * @return
     * 
     */
    Integer listMaterialEndTotal(PageParameter<MaterialListVO> pageParameter);
    Integer listMaterialOrEndSignUpEndTotal(PageParameter<MaterialListVO> pageParameter);


    /**
     * 获取用户在该教材是几本书的策划编辑
     * 
     * @author Mryang
     * @createDate 2017年11月21日 下午2:26:17
     * @param materialId 教材id
     * @param pmphUserId 用户id
     * @return 担任策划编辑数目本数
     */
    Integer getPlanningEditorSum(@Param("materialId") Long materialId,
    @Param("pmphUserId") Long pmphUserId);

    /**
     * 最终结果公布（批量结果公布）
     * 
     * @param material
     * @return
     */
    Integer updateMaterialPublished(Material material);

    /**
     * 通过教材id获取该教材的全部书籍
     * 
     * @param material
     * @return
     */
    List<Textbook> getMaterialAndTextbook(Material material);

    /**
     * 通过书籍id获取教材信息
     * 
     * @param textbookIds
     * @return
     */
    Material getMaterialByName(Long[] textbookIds);

    /**
     * 
     * 
     * 功能描述：书籍页面获取所有已经结束的教材
     * 
     * @param materialName 教材名称
     * @return
     * 
     */
    List<Material> listBook(String materialName);

    /**
     * 
     * <pre>
     * 功能描述：获取已经发布教材信息
     * 使用示范：
     *
     * @return Material 对象集合
     * </pre>
     */
    List<Material> listPublishedMaterial();

    /**
     * 报名已结束 实际报名截止时间 且遴选为结束
     * @param pageParameter
     * @return
     */
    Integer listMaterialSignUpEndTotal(PageParameter<MaterialListVO> pageParameter);

    /**
     * 报名已结束 实际报名截止时间 且遴选为结束
     * @param pageParameter
     * @return
     */
    List<MaterialListVO> listMaterialSignUpEnd(PageParameter<MaterialListVO> pageParameter);

    void addMaterialPositions(List<MaterialPosition> list);
    void delMaterialPositions(long id);
    List<MaterialPosition> getMaterialPositions (Long id);
}
