package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 已公布作家申报职位表业务层接口
 * 
 * @author tyc 2018年1月15日 16:20
 */
public interface DecPositionPublishedService {

    /**
     * 新增
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:20:55
     * @param decPositionPublished
     * @return
     */
    DecPositionPublished addDecPositionPublished(DecPositionPublished decPositionPublished)
    throws CheckedServiceException;

    /**
     * 删除
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:21:05
     * @param id
     * @return
     */
    Integer deleteDecPositionPublished(Long id) throws CheckedServiceException;

    /**
     * 更新
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:21:37
     * @param decPositionPublished
     * @return
     */
    Integer updateDecPositionPublished(DecPositionPublished decPositionPublished)
    throws CheckedServiceException;

    /**
     * 查询
     * 
     * @author:tyc
     * @date:2018年1月15日下午16:21:49
     * @param id
     * @return
     */
    DecPositionPublished getDecPositionPublishedById(Long id) throws CheckedServiceException;

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
    Integer batchInsertDecPositionPublished(List<DecPositionPublished> decPositionPublisheds)
    throws CheckedServiceException;

    /**
     * 根据书籍ids删除
     * 
     * @introduction
     * @author Mryang
     * @createDate 2018年2月7日 下午2:48:08
     * @param bookIds
     * @return
     */
    Integer deleteDecPositionPublishedByBookIds(List<Long> bookIds) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按公布人id和申报表id删除
     * 使用示范： 影响行数
     *
     * @param map
     * @return
     * </pre>
     */
    Integer deleteDecPositionPublishedByTextBookId(Long textbookId) throws CheckedServiceException;
    
    /**
     * 按照书籍查询
     * @param textbookId
     * @return
     * @throws CheckedServiceException
     */
    List<DecPositionPublished> getDecPositionPublishedListByBookId(Long textbookId) throws CheckedServiceException;
}
