/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.dao.DecPositionDao;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.JsonUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.DecPositionEditorSelectionVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecPositionServiceImpl
 * <p>
 * <p>
 * Description:TODO
 * <p>
 * 
 * @author Administrator
 * @date 2017年10月9日 下午6:05:54
 */
@Service
public class DecPositionServiceImpl implements DecPositionService {

    @Autowired
    private DecPositionDao decPositionDao;
    @Autowired
    private FileService    fileService;

    @Override
    public DecPosition addDecPosition(DecPosition decPosition) throws CheckedServiceException {
        if (null == decPosition) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        if (null == decPosition.getDeclarationId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
        }
        if (null == decPosition.getTextbookId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        if (null == decPosition.getPresetPosition()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "申报职务不能为空");
        }
        decPositionDao.addDecPosition(decPosition);
        return decPosition;
    }

    @Override
    public Integer deleteDecPosition(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "id不能为空");
        }
        return decPositionDao.deleteDecPosition(id);
    }

    @Override
    public Integer updateDecPosition(DecPosition decPosition) throws CheckedServiceException {
        if (null == decPosition.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "id不能为空");
        }
        return decPositionDao.updateDecPosition(decPosition);
    }

    @Override
    public DecPosition getDecPositionById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "id不能为空");
        }
        return decPositionDao.getDecPositionById(id);
    }

    @Override
    public List<DecPosition> listDecPositionsByTextbookIdAndOrgid(List<Long> textBookIds, Long orgId)
    throws CheckedServiceException {
        if (null == textBookIds || textBookIds.size() == 0 || null == orgId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        for (Long bookId : textBookIds) {
            if (null == bookId) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                                  CheckedExceptionResult.NULL_PARAM, "书籍参数为空");
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", textBookIds); // 书籍ids
        map.put("orgId", orgId); // 网址类型机构
        return decPositionDao.listDecPositionsByTextbookIdAndOrgid(map);
    }

    @Override
    public List<DecPosition> listDecPositions(Long declarationId) throws CheckedServiceException {
        if (null == declarationId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
        }
        return decPositionDao.listDecPositions(declarationId);
    }

    @Override
    public List<DecPosition> listDecPositionsByTextbookId(Long textbookId)
    throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.listDecPositionsByTextbookId(textbookId);
    }

    @Override
    public List<DecPosition> listChosenDecPositionsByTextbookId(Long textbookId)
    throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.listChosenDecPositionsByTextbookId(textbookId);
    }

    @Override
    public List<Long> listDecPositionsByTextbookIds(String[] textbookIds)
    throws CheckedServiceException {
        if (0 == textbookIds.length) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.listDecPositionsByTextbookIds(textbookIds);
    }

    @Override
    public DecPosition saveBooks(Long declarationId, Long textbookId, Integer presetPosition,
    String syllabusName, MultipartFile file) throws IOException {
        DecPosition decPosition = new DecPosition();
        decPosition.setDeclarationId(declarationId);
        decPosition.setTextbookId(textbookId);
        decPosition.setPresetPosition(presetPosition);
        decPosition.setSyllabusId("---------");
        decPosition.setSyllabusName(syllabusName);
        decPositionDao.addDecPosition(decPosition);
        String mongoId = null;
        mongoId = fileService.save(file, FileType.SYLLABUS, decPosition.getId());
        if (null != mongoId) {
            decPosition.setSyllabusId(mongoId);
            decPositionDao.updateDecPosition(decPosition);
        }
        return decPosition;
    }

    @Override
    public List<DecPositionEditorSelectionVO> listEditorSelection(Long textbookId, String realName,
    Integer presetPosition) throws CheckedServiceException {
        if (ObjectUtil.isNull(textbookId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.listEditorSelection(textbookId,
                                                  StringUtil.toAllCheck(realName),
                                                  presetPosition);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Integer updateDecPositionEditorSelection(String jsonDecPosition)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(jsonDecPosition)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "遴选职位不能为空");
        }
        Integer count = 0;
        List<DecPosition> decPositions =
        new JsonUtil().getArrayListObjectFromStr(DecPosition.class, jsonDecPosition);// json字符串转List对象集合
        if (CollectionUtil.isNotEmpty(decPositions)) {
            count = decPositionDao.updateDecPositionEditorSelection(decPositions);
        }
        return count;
    }

    @Override
    public String saveBooks(Long[] ids, Long declarationId, Long[] textbookIds,
    Integer[] presetPositions, MultipartFile[] files) throws IOException {
        List<DecPosition> istDecPositions = decPositionDao.listDecPositions(declarationId);
        String newIds = ",";
        for (int i = 0; i < ids.length; i++) { // 遍历主键数组
            DecPosition decPosition = new DecPosition();
            Long textbookId = textbookIds[i];
            Integer presetPosition = presetPositions[i];
            MultipartFile file = files[i];
            if (null == file) {
                decPosition.setSyllabusName(null);
            } else {
                String fileName = file.getOriginalFilename(); // 获取原文件名字
                decPosition.setSyllabusName(fileName);
            }
            decPosition.setDeclarationId(declarationId);
            decPosition.setTextbookId(textbookId);
            decPosition.setPresetPosition(presetPosition);
            if (null == ids[i]) { // 保存或者修改
                decPositionDao.addDecPosition(decPosition);
                String mongoId = null;
                if (null == file) {

                } else {
                    mongoId = fileService.save(file, FileType.SYLLABUS, decPosition.getId());
                    if (null != mongoId) {
                        decPosition.setSyllabusId(mongoId);
                        decPositionDao.updateDecPosition(decPosition);
                    }
                }
            } else {
                decPositionDao.updateDecPosition(decPosition);
            }
            newIds += decPosition.getId() + ",";
        }
        for (DecPosition decPosition : istDecPositions) {
            if (!newIds.contains("," + decPosition.getId() + ",")) {
                decPositionDao.deleteDecPosition(decPosition.getId());
            }
        }
        return newIds;
    }
}
