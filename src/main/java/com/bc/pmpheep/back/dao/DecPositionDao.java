/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.vo.DecPositionEditorSelectionVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:作家申报职位表实体类
 * <p>
 * <p>
 * Description:作家申报职位表 数据访问层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年10月9日 下午4:42:32
 */

@Repository
public interface DecPositionDao {
    /**
     * 
     * Description:新增一个作家申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:45:24
     * @Param:
     * @Return:Integer
     */
    Integer addDecPosition(DecPosition decPosition);

    /**
     * 
     * Description:根据id删除一个作家申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:48:01
     * @Param:
     * @Return:Integer
     */
    Integer deleteDecPosition(Long id);

    /**
     * 
     * Description:更新作家申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:48:46
     * @Param:
     * @Return:Integer
     */
    Integer updateDecPosition(DecPosition decPosition);

    /**
     * 
     * Description:根据id查询一个作家申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:52:40
     * @Param:
     * @Return:DecPosition
     */
    DecPosition getDecPositionById(Long id);

    /**
     * 根据orgid和bookid获取该机构某些已公布的书的申报职位
     * 
     * @author Mryang
     * @createDate 2017年11月19日 上午10:13:40
     * @param map
     * @return
     */
    List<DecPosition> listDecPositionsByTextbookIdAndOrgid(Map<String, Object> map);

    /**
     * 
     * Description:根据申报表id查询申报职位信息
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:54:30
     * @Param:
     * @Return:List<DecPosition>
     */
    List<DecPosition> listDecPositions(Long declarationId);

    /**
     * 根据书籍id获取申报职位
     * 
     * @author Mryang
     * @createDate 2017年11月16日 下午2:37:19
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPosition> listDecPositionsByTextbookId(Long textbookId);

    /**
     * 
     * <pre>
     * 功能描述：根据书籍id获取申报表id
     * 使用示范：
     *
     * @param textbookIds textbookIds 书籍id数组
     * @return 申报表id结果集
     * </pre>
     */
    List<Long> listDecPositionsByTextbookIds(String[] textbookIds);

    /**
     * 
     * Description:查询表的总记录数
     * 
     * @author:lyc
     * @date:2017年10月9日下午4:55:26
     * @Param:
     * @Return:Long
     */
    Long getDecPosition();

    /**
     * 
     * <pre>
     * 功能描述：教材申报-遴选主编/遴选编委(列表)
     * 使用示范：
     *
     * @param textbookId 书籍ID
     * @param realName 申报人姓名
     * @param presetPosition 申报职位
     * @return
     * </pre>
     */
    List<DecPositionEditorSelectionVO> listEditorSelection(@Param("textbookId") Long textbookId,
    @Param("realName") String realName, @Param("presetPosition") Integer presetPosition);

    /**
     * 
     * <pre>
     * 功能描述：教材申报-遴选主编/遴选编委(更新)
     * 使用示范：
     *
     * @param decPositions DecPosition 对象集合
     * @return
     * </pre>
     */
    Integer updateDecPositionEditorSelection(List<DecPosition> decPositions);

}
