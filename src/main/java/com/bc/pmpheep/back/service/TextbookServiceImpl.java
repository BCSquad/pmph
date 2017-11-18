package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.TextbookDao;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * TextbookService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class TextbookServiceImpl implements TextbookService {

    @Autowired
    private TextbookDao textbookDao;

    /**
     * 
     * @param Textbook 实体对象
     * @return 带主键的 Textbook
     * @throws CheckedServiceException
     */
    @Override
    public Textbook addTextbook(Textbook textbook) throws CheckedServiceException {
    	if (ObjectUtil.isNull(textbook.getMaterialId())){
    		throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
    				CheckedExceptionResult.NULL_PARAM, "教材id不能为空！");
    	}
        if (StringUtil.isEmpty(textbook.getTextbookName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                                              CheckedExceptionResult.NULL_PARAM, "书籍名称为空");
        }
        if (ObjectUtil.isNull(textbook.getTextbookRound())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                                              CheckedExceptionResult.NULL_PARAM, "书籍轮次为空");
        }
        if (ObjectUtil.isNull(textbook.getSort())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                                              CheckedExceptionResult.NULL_PARAM, "图书序号为空");
        }
        if (ObjectUtil.isNull(textbook.getFounderId())){
        	throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
        			CheckedExceptionResult.NULL_PARAM, "创建人id不能为空！");
        }
        textbookDao.addTextbook(textbook);
        return textbook;
    }

    /**
     * 
     * @param id
     * @return Textbook
     * @throws CheckedServiceException
     */
    @Override
    public Textbook getTextbookById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return textbookDao.getTextbookById(id);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteTextbookById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return textbookDao.deleteTextbookById(id);
    }

    /**
     * @param Textbook
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateTextbook(Textbook textbook) throws CheckedServiceException {
        if (null == textbook.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return textbookDao.updateTextbook(textbook);
    }

    @Override
    public List<Textbook> getTextbookByMaterialId(Long materialId) throws CheckedServiceException {
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                                              CheckedExceptionResult.NULL_PARAM, "教材ID为空，禁止查询！");
        }
        return textbookDao.getTextbookByMaterialId(materialId);
    }

}
