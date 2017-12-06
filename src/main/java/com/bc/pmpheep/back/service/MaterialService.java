package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.vo.MaterialListVO;
import com.bc.pmpheep.back.vo.MaterialVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * MaterialService 接口
 * 
 * @author 曾庆峰
 * 
 */
public interface MaterialService {

    /**
     * 新增一个Material
     * 
     * @param Material 实体对象
     * @return 带主键的 Material thorws CheckedServiceException
     */
    Material addMaterial(Material material) throws CheckedServiceException;
    /**
     * 
     * 新建遴选公告
     * @createDate 2017年11月13日 下午2:33:56
     * @param sessionId
     * @param MaterialVO materialVO
     * @param noticeFiles
     * @param noteFiles
     * @param isUpdate false新增,true更新
     * @return material 主键
     * @throws CheckedServiceException
     * @throws IOException 
     */
    Long addOrUpdateMaterial(String sessionId, 
    		MaterialVO materialVO,
		    MultipartFile[]   noticeFiles,
		    MultipartFile[]   noteFiles,
		    boolean isUpdate) throws CheckedServiceException, IOException;
    
    /**
	 * 获取教材名称  通过主键id
	 * 
	 * @param id
	 * @return Material
	 * @throws CheckedServiceException
	 * @return 教材名称
	 */
	String getMaterialNameById(Long id) throws CheckedServiceException;


	/**
	 * 查询一个 Material 通过主键id
	 * 
	 * @param id
	 * @return Material
	 * @throws CheckedServiceException
	 */
	Material getMaterialById(Long id) throws CheckedServiceException;

	/**
	 * 获取用户在该教材是几本书的策划编辑
	 * 
	 * @author Mryang
	 * @createDate 2017年11月21日 下午2:26:17
	 * @param materialId
	 *            教材id
	 * @param pmphUserId
	 *            机构用户id
	 * @return 担任策划编辑数目本数
	 * @throws CheckedServiceException
	 */
	Integer getPlanningEditorSum(Long materialId, Long pmphUserId) throws CheckedServiceException;

	/**
	 * 删除Material 通过主键id
	 * 
	 * @param Material
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteMaterialById(Long id) throws CheckedServiceException;

	/**
	 * 通过主键id更新material 不为null 的字段
	 * 
	 * @param Material
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateMaterial(Material material) throws CheckedServiceException;

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
	List<Material> getListMaterial(String materialName) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：初始化/条件加载教材申报公告页面
	 *
	 * @param pageParameter
	 *            pageSize 当页条数 pageNumber 当前页数 materialName 教材名称
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	PageResult<MaterialListVO> listMaterials(PageParameter<MaterialListVO> pageParameter, String sessionId)
			throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：根据教材id逻辑删除教材
	 *
	 * @param id
	 *            教材id
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	String updateMaterial(Long id, String sessionId) throws CheckedServiceException;
	
	/**
	 * 根据教材id获取更新教材视图数据
	 * @author Mryang
	 * @createDate 2017年11月23日 下午5:47:53
	 * @param id
	 * @return MaterialVO
	 * @throws CheckedServiceException
	 */
	MaterialVO getMaterialVO(Long id) throws CheckedServiceException ;
}
