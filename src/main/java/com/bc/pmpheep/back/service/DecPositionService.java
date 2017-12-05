/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.vo.DecPositionEditorSelectionVO;
import com.bc.pmpheep.back.vo.DecPositionVO;
import com.bc.pmpheep.back.vo.DeclarationCountVO;
import com.bc.pmpheep.back.vo.DeclarationResultBookVO;
import com.bc.pmpheep.back.vo.DeclarationResultSchoolVO;
import com.bc.pmpheep.back.vo.DeclarationSituationBookResultVO;
import com.bc.pmpheep.back.vo.DeclarationSituationSchoolResultVO;
import com.bc.pmpheep.back.vo.TextbookDecVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:业务层
 * <p>
 * <p>
 * Description:作家申报职位业务层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年10月9日 下午5:53:39
 */
public interface DecPositionService {
    /**
     * @Param DecPosition 实体对象
     * @Return DecPosition带主键
     * @throws CheckedServiceException
     */
    DecPosition addDecPosition(DecPosition decPosition) throws CheckedServiceException;

    /**
     * @Param id
     * @Return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteDecPosition(Long id) throws CheckedServiceException;

    /**
     * @Param DecPosition 实体对象
     * @Return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateDecPosition(DecPosition decPosition) throws CheckedServiceException;

    /**
     * @Param id
     * @Return DecPosition 实体对象
     * @throws CheckedServiceException
     */
    DecPosition getDecPositionById(Long id) throws CheckedServiceException;

    /**
     * @Param declarationId
     * @Return DecPosition 实体对象集合
     * @throws CheckedServiceException
     */
    List<DecPosition> listDecPositions(Long declarationId) throws CheckedServiceException;

    /**
     * 根据orgid和bookid获取该机构某些已公布的书的申报职位
     * 
     * @author Mryang
     * @createDate 2017年11月20日 上午10:13:40
     * @param textBookIds
     * @param orgId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPosition> listDecPositionsByTextbookIdAndOrgid(List<Long> textBookIds, Long orgId)
    throws CheckedServiceException;

    /**
     * 根据书籍id获取申报职位
     * 
     * @author Mryang
     * @createDate 2017年11月16日 下午2:37:19
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPosition> listDecPositionsByTextbookId(Long textbookId) throws CheckedServiceException;

    /**
     * 根据书籍id获取入选的职位职位(主编、副主编、编委、数字编辑)
     * 
     * @author Mryang
     * @createDate 2017年11月16日 下午2:37:19
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPosition> listChosenDecPositionsByTextbookId(Long textbookId)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据书籍id获取申报表id
     * 使用示范：
     *
     * @param textbookIds textbookId 书籍id数组
     * @return 申报表id结果集
     * @throws CheckedServiceException
     * </pre>
     */
    List<Long> listDecPositionsByTextbookIds(String[] textbookIds) throws CheckedServiceException;

    /**
     * 保存图书
     * 
     * @author tyc
     * @createDate 2017年11月25日 晚上21:15:30
     * @param decPositionVO
     * @return
     * @throws IOException
     */
    long saveBooks(DecPositionVO decPositionVO) throws IOException;

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
     * @throws CheckedServiceException
     * </pre>
     */
    List<DecPositionEditorSelectionVO> listEditorSelection(Long textbookId, String realName,
    Integer presetPosition) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：教材申报-遴选主编/遴选编委(更新)
     * 使用示范：
     *
     * @param decPositions DecPosition 对象集合
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateDecPositionEditorSelection(String jsonDecPosition, String sessionId)
    throws CheckedServiceException;
    
    /**
     * 
     * Description:获取申报情况统计数据
     * @author:lyc
     * @date:2017年11月30日下午6:19:48
     * @param 
     * @return DeclarationCountVO
     */
    DeclarationCountVO getDeclarationCountVO(Long materialId) throws CheckedServiceException;
    
    /**
     * 
     * Description:加载申请情况按学校统计界面（按当选数排序）
     * @author:lyc
     * @date:2017年11月29日下午5:04:52
     * @param 
     * @return PageResult<DeclarationSituationSchoolResultVO>
     */
    PageResult<DeclarationSituationSchoolResultVO> listChosenDeclarationSituationSchoolResultVOs
    (PageParameter<DeclarationSituationSchoolResultVO> pageParameter) throws CheckedServiceException;
    
    /**
     * 
     * Description:加载申请情况按学校统计界面（按申报数排序）
     * @author:lyc
     * @date:2017年12月5日上午11:29:03
     * @param 
     * @return PageResult<DeclarationSituationSchoolResultVO>
     */
    PageResult<DeclarationSituationSchoolResultVO> listPresetDeclarationSituationSchoolResultVOs
    (PageParameter<DeclarationSituationSchoolResultVO> pageParameter)throws CheckedServiceException;
    
    /**
     * 
     * Description:加载申请情况按书本统计界面
     * @author:lyc
     * @date:2017年12月1日下午5:18:52
     * @param 
     * @return PageResult<DeclarationSituationBookResultVO>
     */
    PageResult<DeclarationSituationBookResultVO> listDeclarationSituationBookResultVOs
    (PageParameter<DeclarationSituationBookResultVO> pageParameter)throws CheckedServiceException;
    
    /**
     * 
     * Description:加载按学校统计申报结果界面（按当选数排序）
     * @author:lyc
     * @date:2017年12月1日下午6:28:07
     * @param 
     * @return PageResult<DeclarationResultSchoolVO>
     */
    PageResult<DeclarationResultSchoolVO> listChosenDeclarationResultSchoolVOs
    (PageParameter<DeclarationResultSchoolVO> pageParameter)throws CheckedServiceException;
    
    /**
     * 
     * Description:加载按学校统计申报结果界面（按申报数排序）
     * @author:lyc
     * @date:2017年12月5日上午11:30:31
     * @param 
     * @return PageResult<DeclarationResultSchoolVO>
     */
    PageResult<DeclarationResultSchoolVO> listPresetDeclarationResultSchoolVOs
    (PageParameter<DeclarationResultSchoolVO> pageParameter)throws CheckedServiceException;
    
    /**
     * 
     * Description:加载按书籍统计申报结果界面
     * @author:lyc
     * @date:2017年12月1日下午6:40:00
     * @param 
     * @return PageResult<DeclarationResultBookVO>
     */
    PageResult<DeclarationResultBookVO> listDeclarationResultBookVOs
    (PageParameter<DeclarationResultBookVO> pageParameter)throws CheckedServiceException;
    /**
     * 根据书籍id查询该书的主编、副主编、编委
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
	List<TextbookDecVO> getTextbookEditorList(Long textbookId)throws CheckedServiceException;
}
