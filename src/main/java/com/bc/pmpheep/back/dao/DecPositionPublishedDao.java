package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.back.vo.DecPositionPublishedVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 已公布作家申报职位表数据访问层接口
 * 
 * @author tyc 2018年1月15日 16:09
 */
@Repository
public interface DecPositionPublishedDao {

    /**
     * 新增
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:10:12
     * @param decPositionPublished
     * @return
     */
    Integer addDecPositionPublished(DecPositionPublished decPositionPublished);

    /**
     * 删除
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:17:54
     * @param id
     * @return
     */
    Integer deleteDecPositionPublished(Long id);

    /**
     * 更新
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:18:34
     * @param decPositionPublished
     * @return
     */
    Integer updateDecPositionPublished(DecPositionPublished decPositionPublished);

    /**
     * 查询
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:18:57
     * @param id
     * @return
     */
    DecPositionPublished getDecPositionPublishedById(Long id);

    /**
     * 查询
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:18:57
     * @param id
     * @return
     */
    DecPositionPublished getDecPositionByDeclarationId(@Param("declarationId") Long declarationId,
    @Param("textbookId") Long textbookId);

    /**
     * 
     * <pre>
     * 功能描述：批量新增
     * 使用示范：
     *
     * @param decPositionPublisheds
     * @return 影响行数
     * </pre>
     */
    Integer batchInsertDecPositionPublished(List<DecPositionPublished> decPositionPublisheds);

    /**
     * 根据书籍ids删除
     * 
     * @introduction
     * @author Mryang
     * @createDate 2018年2月7日 下午2:48:08
     * @param bookIds
     * @return
     */
    Integer deleteDecPositionPublishedByBookIds(@Param("bookIds") List<Long> bookIds);

    /**
     * 
     * <pre>
     * 功能描述：
     * 使用示范： 影响行数
     *
     * @param map
     * @return
     * </pre>
     */
    Integer deleteDecPositionPublishedByTextBookId(Long textbookId);

    /**
     * 按照书籍查询
     * 
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPositionPublished> getDecPositionPublishedListByBookId(Long textbookId);
    
    /**
     * 按照书籍查询
     * 
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPositionPublished> getDecPositionPublishedListByBookIds(List<Long> textbookIds);

    /**
	 * 
	 * <pre>
	 * 功能描述：按书籍id删除DecPositionPublished
	 * 使用示范：
	 *
	 * &#64;param textbookId 书籍ID
	 * &#64;return 影响行数
	 * </pre>
	 */
	Integer deletePublishedEditorByTextbookId(Long textbookId);
    Integer deletePublishedEditorByTextbookId2(Long textbookId);
	
	/**
     * 
     * Description:根据申报表id查询申报职位信息
     * 
     * @author:tyc
     * @date:2018年3月12日下午17:05:21
     * @Param:
     * @Return:List<DecPositionPublishedVO>
     */
    List<DecPositionPublishedVO> listDecPositionDisplayOrPosition(Long declarationId);

}
