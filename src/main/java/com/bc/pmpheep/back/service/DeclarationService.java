/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.vo.DeclarationListVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
	 * @Param materialId
	 * @Return Declaration 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<Declaration> getDeclarationByMaterialId(Long materialId) throws CheckedServiceException;
	
	/**
	 * 符合条件的申报表审核分页数据
	 * @author Mryang
	 * @createDate 2017年11月23日 上午10:31:36
	 * @param pageNumber 页码
	 * @param pageSize   页面大小
	 * @param materialId 教材id
	 * @param textBookids条件查询的书籍ids [1,2,4,...]
	 * @param realname   条件查询的账号或者姓名
	 * @param position   条件查询 职务
	 * @param title      条件查询 职称
	 * @param orgName    条件查询 工作单位
	 * @param unitName   条件查询 申报单位
	 * @param positionType 条件查询 申报职位 ;null全部  1主编 2副主编 3编委 
	 * @param onlineProgress 1待审核 3已经审核 
	 * @param offlineProgress 0 未  2 收到
	 * @return PageResult<DeclarationListVO>
	 * @Throws CheckedServiceException
	 */
	public PageResult<DeclarationListVO> pageDeclaration   (
				Integer pageNumber, 
				Integer pageSize,
				Long materialId, 
				String textBookids ,
				String realname,
				String position,
				String title,
				String orgName,
				String unitName,
				Integer positionType,
				Integer onlineProgress,
				Integer offlineProgress
			) throws CheckedServiceException ;
	
	/**
	 * 确认收到纸质表
	 * @author tyc
	 * @createDate 2017年11月24日 下午15:27:36
	 * @param declaration
	 * @return 
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	Declaration confirmPaperList(Long id, Integer offlineProgress, 
			Long materialId) throws CheckedServiceException, IOException;
	
	/**
	 * 审核进度
	 * @author tyc
	 * @createDate 2017年11月24日 下午16:37:36
	 * @param declaration
	 * @return 
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	Declaration onlineProgress(Long id, Integer onlineProgress, 
			Long materialId) throws CheckedServiceException, IOException;
	
	/**
	 * 显示专家信息
	 * @author tyc
	 * @createDate 2017年11月25日 上午9:13:09
	 * @param decPosition
	 */
	List<?> exportExcel(Long id, Long declarationId);
}
