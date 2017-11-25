/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.po.DecPosition;
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
     * @author Mryang
     * @createDate 2017年11月20日 上午10:13:40
     * @param textBookIds
     * @param orgId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPosition> listDecPositionsByTextbookIdAndOrgid(List<Long> textBookIds,Long orgId) throws CheckedServiceException;

    /**
     * 根据书籍id获取申报职位
     * @author Mryang
     * @createDate 2017年11月16日 下午2:37:19
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPosition> listDecPositionsByTextbookId(Long textbookId) throws CheckedServiceException;

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
	 * @author tyc
	 * @createDate 2017年11月25日 晚上21:15:30
     * @param declarationId		申报表id
     * @param textbookId		书籍id
     * @param presetPosition	申报职务
     * @param syllabusName		教学大纲名称
     * @return
     * @throws IOException 
     */
    DecPosition saveBooks(Long declarationId, Long textbookId, Integer presetPosition, 
    		String syllabusName, MultipartFile file) throws IOException;
}
