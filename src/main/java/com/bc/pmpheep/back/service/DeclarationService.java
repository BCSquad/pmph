/**
 *
 */
package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;

import java.io.IOException;
import java.util.List;

import com.bc.pmpheep.back.po.PmphUser;
import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.vo.ApplicationVO;
import com.bc.pmpheep.back.vo.DeclarationListVO;
import com.bc.pmpheep.back.vo.DeclarationOrDisplayVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title:Declaration业务层接口
 * <p>
 *
 * @author lyc
 * @date 2017年9月24日 下午3:49:19
 */
public interface DeclarationService {

	/**
	 * @Param Declaration 实体对象
	 * @Return Declaration带主键
	 * @throws CheckedServiceException
	 */
	Declaration addDeclaration(Declaration declaration) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDeclarationById(Long id) throws CheckedServiceException;

	/**
	 * @Param Declaration 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDeclaration(Declaration declaration) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return Declaration 实体对象
	 * @Throws CheckedServiceException
	 */
	Declaration getDeclarationById(Long id) throws CheckedServiceException;

	/**
	 * 查询多个申报
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月14日 下午4:35:32
	 * @param ids
	 * @return
	 * @throws CheckedServiceException
	 */
	List<Declaration> getDeclarationByIds(List<Long> ids) throws CheckedServiceException;

	/**
	 * @Param materialId
	 * @Return Declaration 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<Declaration> getDeclarationByMaterialId(Long materialId) throws CheckedServiceException;

	/**
	 * @Param materialId
	 * @Param userId
	 * @Return Declaration 实体对象
	 * @Throws CheckedServiceException
	 */
	Declaration getDeclarationByMaterialIdAndUserId(Long materialId, Long userId) throws CheckedServiceException;

	/**
	 * 符合条件的申报表审核分页数据
	 *
	 * @author Mryang
	 * @createDate 2017年11月23日 上午10:31:36
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页面大小
	 * @param materialId
	 *            教材id
	 * @param textBookids条件查询的书籍ids
	 *            [1,2,4,...]
	 * @param realname
	 *            条件查询的账号或者姓名
	 * @param position
	 *            条件查询 职务
	 * @param title
	 *            条件查询 职称
	 * @param orgName
	 *            条件查询 工作单位
	 * @param orgId
	 *            条件查询 申报单位id
	 * @param unitName
	 *            条件查询 申报单位
	 * @param positionType
	 *            条件查询 申报职位 ;null全部 1主编 2副主编 3编委
	 * @param onlineProgress
	 *            1待审核 3已经审核
	 * @param offlineProgress
	 *            0 未 2 收到
	 * @param haveFile
	 *            有无教材大纲 null true false
	 * @return PageResult<DeclarationListVO>
	 * @Throws CheckedServiceException
	 */
	public PageResult<DeclarationListVO> pageDeclaration(Integer pageNumber, Integer pageSize, Long materialId,
			String textBookids, String realname, String position, String title, String orgName, Long orgId,
			String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress, Boolean haveFile,Boolean isSelected,String startCommitDate,String endCommitDate,String tag,
														 HttpServletRequest request)
			throws CheckedServiceException;

	/**
	 * 确认收到纸质表
	 *
	 * @author tyc
	 * @createDate 2017年11月24日 下午15:27:36
	 * @param declaration
	 * @return
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	Declaration confirmPaperList(Long id, Integer offlineProgress, String sessionId)
			throws CheckedServiceException, IOException;

	/**
	 * 审核进度
	 *
	 * @author tyc
	 * @createDate 2017年11月24日 下午16:37:36
	 * @param declaration
	 * @return
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	Declaration onlineProgress(Long id, Integer onlineProgress, String returnCause,PmphUser pmphUser)
			throws CheckedServiceException, IOException;

	/**
	 * 显示专家信息
	 *
	 * @author tyc
	 * @createDate 2017年11月25日 上午9:13:09
	 * @param decPosition
	 */
	ApplicationVO exportExcel(Long declarationId);

	/**
	 *
	 *
	 * 功能描述：批量导出申报表
	 *
	 * @param materialId
	 *
	 */
	List<DeclarationEtcBO> declarationEtcBO(Long materialId, String textBookids, String realname, String position,
			String title, String orgName, String unitName, Integer positionType, Integer onlineProgress,
			Integer offlineProgress,Boolean isSelect) throws CheckedServiceException, IllegalArgumentException, IllegalAccessException;

	/**
	 * 根据教材申报id与姓名查询作家申报信息
	 * 
	 * @param id
	 * @return
	 */
	List<DeclarationEtcBO> getDeclarationOrDisplayVOByIdOrRealname(@Param("id") List<Long> id)
			throws CheckedServiceException, IllegalArgumentException, IllegalAccessException;

	/**
	 * 根据教材申报id与姓名查询作家申报信息
	 * 
	 * @param id
	 * @return
	 */
	List<DeclarationOrDisplayVO> getDeclarationOrDisplayVOByRealname(@Param("id") List<Long> id)
			throws CheckedServiceException;

	/**
	 * 根据教材id获取导出Excel所需的申报表数据
	 *
	 * @param materialId
	 *            教材id
	 * @return 符合查询条件的DeclarationEtcBO实例
	 */
	@Deprecated
	List<DeclarationEtcBO> getDeclarationEtcBOs(Long materialId);
	
	/**
	 * 通过教材id 查询教材已结束并且未遴选上的作家
	 * @param materialId
	 * @return
	 */
	List<Declaration> getPositionChooseLossByMaterialId(Long materialId)throws CheckedServiceException;
}
