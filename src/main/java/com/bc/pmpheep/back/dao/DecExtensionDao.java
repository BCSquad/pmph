/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecExtension;
import com.bc.pmpheep.back.po.DecSci;
import com.bc.pmpheep.back.vo.DecExtensionVO;

/**
 * <p>Title:教材扩展项填报表<p>
 * <p>Description:数据访问层<p>
 * @author lyc
 * @date 2017年10月30日 下午10:56:27
 */
@Repository
public interface DecExtensionDao {
	
	/**
	 * 
	 * Description:添加一项教材扩展项
	 * @author:lyc
	 * @date:2017年10月30日下午10:58:14
	 * @param DecExtension对象
	 * @return Integer影响行数
	 */
  Integer addDecExtension(DecExtension decExtension);
  
  /**
   * 
   * Description:删除一项教材扩展项
   * @author:lyc
   * @date:2017年10月30日下午10:59:52
   * @param 主键
   * @return Integer影响行数
   */
  Integer deleteDecExtension(Long id);
  
  /**
   * 根据扩展id 删除 扩展作家扩展项填报
   * @author Mryang
   * @createDate 2017年11月16日 下午1:44:45
   * @param extensionId
   * @return 影响行数
   */
  Integer  deleteDecExtensionByExtensionId(Long extensionId);
  
  /**
   * 
   * Description:更新一项教材扩展项
   * @author:lyc
   * @date:2017年10月30日下午11:00:53
   * @param DecExtension对象
   * @return Integer影响行数
   */
  Integer updateDecExtension(DecExtension decExtension);
  
  /**
   * 
   * Description:获取一项教材扩展项
   * @author:lyc
   * @date:2017年10月30日下午11:02:46
   * @param 主键
   * @return DecExtension对象
   */
  DecExtension getDecExtensionById(Long id);
  
  /**
   * 
   * Description:根据教材扩展项id获取教材扩展项信息集合
   * @author:lyc
   * @date:2017年10月30日下午11:04:45
   * @param 教材扩展项id
   * @return DecExtension对象集合
   */
  List<DecExtension> getListDecExtensionsByExtensionId(Long extensionId);
  
  /**
   * 
   * Description:根据申报表id获取教材扩展项信息集合
   * @author:lyc
   * @date:2017年10月30日下午11:07:24
   * @param 申报表id
   * @return DecExtension对象集合
   */
  List<DecExtension> getListDecExtensionsByDeclarationId(Long declarationId);
  
  /**
   * 
   * Description:获取表数据总数
   * @author:lyc
   * @date:2017年10月30日下午11:09:15
   * @param 
   * @return 数据总数
   */
  Long getDecExtensionCount();
  
  /**
   * 
   * Description:根据申报表id获取教材扩展项信息集合包含扩展项名称
   * @author:tyc
   * @date:2017年12月12日上午10:44:24
   * @param 申报表id
   * @return DecExtension对象集合
   */
  List<DecExtensionVO> getListDecExtensionByDeclarationId(Long declarationId);
  
  /**
	 * Description: 根据申报表id查询
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家学习经历信息
	 */
	List<DecExtensionVO> getListDecExtensionVOByDeclarationIds(List<Long> declarationId);
}
