package com.bc.pmpheep.back.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecPositionPublishedDao;
import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 已公布作家申报职位
 * 
 * @author tyc 2018年1月15日 16:23
 */
@Service
public class DecPositionPublishedServiceImpl implements DecPositionPublishedService {

    @Autowired
    private DecPositionPublishedDao decPositionPublishedDao;

    @Override
    public DecPositionPublished addDecPositionPublished(DecPositionPublished decPositionPublished)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(decPositionPublished)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        if (ObjectUtil.isNull(decPositionPublished.getPublisherId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "公布人不能为空");
        }
        if (ObjectUtil.isNull(decPositionPublished.getDeclarationId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "申报表不能为空");
        }
        if (ObjectUtil.isNull(decPositionPublished.getTextbookId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍不能为空");
        }
        if (ObjectUtil.isNull(decPositionPublished.getPresetPosition())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "申报职位不能为空");
        }
        if (ObjectUtil.isNull(decPositionPublished.getChosenPosition())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "遴选职位不能为空");
        }
        decPositionPublishedDao.addDecPositionPublished(decPositionPublished);
        return decPositionPublished;
    }

    @Override
    public Integer deleteDecPositionPublished(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        return decPositionPublishedDao.deleteDecPositionPublished(id);
    }

    @Override
    public Integer updateDecPositionPublished(DecPositionPublished decPositionPublished)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(decPositionPublished.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        return decPositionPublishedDao.updateDecPositionPublished(decPositionPublished);
    }

    @Override
    public DecPositionPublished getDecPositionPublishedById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        return decPositionPublishedDao.getDecPositionPublishedById(id);
    }

    @Override
    public Integer deleteDecPositionPublishedByBookIds(List<Long> bookIds) throws CheckedServiceException{
    	if (null ==  bookIds || bookIds.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
    	for(Long bookId :bookIds){
    		if(null == bookId){
    			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "有参数为空");
    		}
    	}
    	return decPositionPublishedDao.deleteDecPositionPublishedByBookIds(bookIds);
    }
    
    
    @Override
    public Integer batchInsertDecPositionPublished(List<DecPositionPublished> decPositionPublisheds)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(decPositionPublisheds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        return decPositionPublishedDao.batchInsertDecPositionPublished(decPositionPublisheds);
    }

    @Override
    public Integer deleteDecPositionPublishedByPublisherIdAndDeclarationId(Map<String, Object> map)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(map)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        return decPositionPublishedDao.deleteDecPositionPublishedByPublisherIdAndDeclarationId(map);
    }

}
