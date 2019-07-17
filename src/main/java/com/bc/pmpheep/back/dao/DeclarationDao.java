/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.vo.DeclarationListVO;
import com.bc.pmpheep.back.vo.DeclarationOrDisplayVO;

/**
 * <p>
 * Title:作家申报表 数据访问层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年9月24日 下午3:19:54
 */
@Repository
public interface DeclarationDao {

	/**
	 * 新增一个作家申报信息
	 * 
	 * @Param Declaration 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDeclaration(Declaration declaration);

	/**
	 * 通过主键id删除一个作家申报信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDeclarationById(Long id);

	/**
	 * 通过主键id更新作家申报信息
	 * 
	 * @Param Declaration 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDeclaration(Declaration declaration);

	/**
	 * 通过主键id查询一个作家申报信息
	 * 
	 * @Param id
	 * 
	 * @Return Declaration
	 */
	Declaration getDeclarationById(Long id);

	/**
	 * 查询多个申报
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月14日 下午4:41:09
	 * @param ids
	 * @return
	 */
	List<Declaration> getDeclarationByIds(@Param("ids") List<Long> ids);

	/**
	 * Description: 根据教材id查询作家申报信息
	 * 
	 * @Param materialId 教材id
	 * 
	 * @Return 作家申报信息
	 */
	List<Declaration> getDeclarationByMaterialId(Long materialId);

	/**
	 * Description: 根据教材id和作家id查询作家申报信息
	 * 
	 * @Param materialId 教材id
	 * 
	 * @Param userId 作家id
	 * 
	 * @Return 作家申报信息
	 */
	Declaration getDeclarationByMaterialIdAndUserId(@Param("materialId") Long materialId, @Param("userId") Long userId);

	int getDeclarationByUserId( @Param("userId") Long userId);

	/**
	 * Description: 根据教材id查询作家申报信息
	 * 
	 * @Param materialId 教材id
	 * 
	 * @Return 作家申报信息
	 */
	List<DeclarationOrDisplayVO> getDeclarationOrDisplayVOByMaterialId(@Param("materialId") Long materialId,
			@Param("bookIds") List<Long> bookIds, @Param("realname") String realname,
			@Param("position") String position, @Param("title") String title, @Param("orgName") String orgName,
			@Param("unitName") String unitName, @Param("positionType") Integer positionType,
			@Param("onlineProgress") Integer onlineProgress, @Param("offlineProgress") Integer offlineProgress,@Param("isSelected") Boolean isSelect);

	/**
	 * 根据教材申报id与姓名查询作家申报信息
	 * 
	 * @param id
	 * @return
	 */
	List<DeclarationOrDisplayVO> getDeclarationOrDisplayVOByIdOrRealname(@Param("id") List<Long> id);
	List<DeclarationOrDisplayVO> getDeclarationOrDisplayVOByIdOrRealname2(@Param("id") List<Long> id);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDeclaration();

	/**
	 * 申报表审核列表总数
	 * 
	 * @author Mryang
	 * @createDate 2017年11月23日 上午10:26:29
	 * @param pageParameter
	 * @return 符合条件的记录总数
	 */
	Integer listDeclarationTotal(PageParameter<Map<String, Object>> pageParameter);

	/**
	 * 申报表审核分页列表
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年11月23日 上午10:27:20
	 * @param pageParameter
	 * @return 符合条件的申报表审核分页数据
	 */
	List<DeclarationListVO> listDeclaration(PageParameter<Map<String, Object>> pageParameter);
	String findMaterialCreateDate(Map<String, Object> paraMap);
	String findDeclarationCreateDate(Map<String, Object> paraMap);
	String findDeclarationCreateDateByTextBook(Map<String, Object> paraMap);

	/**
	 * 通过主键id查询一个作家申报信息并包含申报机构名称，是否多选
	 * 
	 * @Param id
	 * 
	 * @Return Declaration
	 */
	DeclarationOrDisplayVO getDeclarationByIdOrOrgName(Long id);
	
	/**
	 * 通过教材id查询教材申报所有人
	 * @param materialId
	 * @return
	 */
	List<Declaration> getPositionChooseLossByMaterialId(Long materialId);

	/**
	 * wx获取用户的教材申报
	 * @param param
	 * @return
	 */
	Map<String,Object> getMaterialForResolve(Map<String,Object> param);

	Long getMaterialBydeclarationId(Map<String,Object> params);

}
