/**
 *
 */
package com.bc.pmpheep.back.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.back.dao.DataDictionaryDao;
import com.bc.pmpheep.back.dao.DeclarationDao;
import com.bc.pmpheep.back.util.*;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecPositionDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.vo.DecPositionEditorSelectionVO;
import com.bc.pmpheep.back.vo.DecPositionVO;
import com.bc.pmpheep.back.vo.DeclarationCountVO;
import com.bc.pmpheep.back.vo.DeclarationResultBookVO;
import com.bc.pmpheep.back.vo.DeclarationResultSchoolVO;
import com.bc.pmpheep.back.vo.DeclarationSituationBookResultVO;
import com.bc.pmpheep.back.vo.DeclarationSituationSchoolResultVO;
import com.bc.pmpheep.back.vo.NewDecPosition;
import com.bc.pmpheep.back.vo.TextBookDecPositionVO;
import com.bc.pmpheep.back.vo.TextbookDecVO;
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
 * Description:作家职位遴选信息
 * <p>
 *
 * @author lyc
 * @date 2017年10月9日 下午6:05:54
 */
@Service
public class DecPositionServiceImpl implements DecPositionService {

    @Autowired
    private DecPositionDao              decPositionDao;
    @Autowired
    private FileService                 fileService;
    @Autowired
    private DecPositionService          decPositionService;
    @Autowired
    private TextbookLogService          textbookLogService;
    @Autowired
    private TextbookService             textbookService;
    @Autowired
    private MaterialService             materialService;
    @Autowired
    private SystemMessageService        systemMessageService;
    @Autowired
    private DecPositionPublishedService decPositionPublishedService;
    @Autowired
    private DecPositionTempService      decPositionTempService;
    @Autowired
    private DataDictionaryDao dataDictionaryDao;
    @Autowired
    DeclarationDao declarationDao;

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
    public List<DecPosition> listDecPositionsByTextBookIds(List<Long> textbookIds)
            throws CheckedServiceException {
        if (null == textbookIds || textbookIds.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        for (Long bookId : textbookIds) {
            if (null == bookId) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                        CheckedExceptionResult.NULL_PARAM, "有书籍id为空");
            }
        }
        return decPositionDao.listDecPositionsByTextBookIds(textbookIds);
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
    public long saveBooks(DecPositionVO decPositionVO, HttpServletRequest request)
            throws IOException {
        List<NewDecPosition> list = decPositionVO.getList();
        if (CollectionUtil.isEmpty(list)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "添加内容不能为空");
        }
        List<DecPosition> istDecPositions =
                decPositionDao.listDecPositions(list.get(0).getDeclarationId());
        if (CollectionUtil.isEmpty(istDecPositions)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "内容不能为空");
        }
        String newId = ",";
        for (NewDecPosition newDecPosition : list) { // 遍历所有的id
            newId += newDecPosition.getId() + ",";
        }
        for (DecPosition DecPosition : istDecPositions) { // 遍历原数据
            if (!newId.contains("," + DecPosition.getId() + ",")) {
                if (ObjectUtil.notNull(DecPosition.getId())) {
                    decPositionDao.deleteDecPosition(DecPosition.getId());
                }
            }
        }
        for (NewDecPosition newDecPosition : list) {
            Long id = newDecPosition.getId();
            Long declarationId = newDecPosition.getDeclarationId();
            Long textbookId = newDecPosition.getTextbookId();
            String file = newDecPosition.getFile();
            String showPosition = newDecPosition.getShowPosition();
            DecPosition decPosition = new DecPosition();
            if ("编委".equals(showPosition)) {
                decPosition.setPresetPosition("3");
            } else if ("副主编".equals(showPosition)) {
                decPosition.setPresetPosition("2");
            } else if ("副主编,编委".equals(showPosition)) {
                decPosition.setPresetPosition("2,3");
            } else if ("主编".equals(showPosition)) {
                decPosition.setPresetPosition("1");
            } else if ("主编,编委".equals(showPosition)) {
                decPosition.setPresetPosition("1,3");
            } else if ("主编,副主编".equals(showPosition)) {
                decPosition.setPresetPosition("1,2");
            } else if ("主编,副主编,编委".equals(showPosition)) {
                decPosition.setPresetPosition("1,2,3");
            } else if ("数字编委".equals(showPosition)) {
                decPosition.setPresetPosition("8");
            } else if ("编委,数字编委".equals(showPosition)) {
                decPosition.setPresetPosition("3,8");
            } else if ("副主编,数字编委".equals(showPosition)) {
                decPosition.setPresetPosition("2,8");
            } else if ("副主编,编委,数字编委".equals(showPosition)) {
                decPosition.setPresetPosition("2,3,8");
            } else if ("主编,数字编委".equals(showPosition)) {
                decPosition.setPresetPosition("1,8");
            } else if ("主编,编委,数字编委".equals(showPosition)) {
                decPosition.setPresetPosition("1,3,8");
            } else if ("主编,副主编,数字编委".equals(showPosition)) {
                decPosition.setPresetPosition("1,2,8");
            } else if ("主编,副主编,编委,数字编委".equals(showPosition)) {
                decPosition.setPresetPosition("1,2,3,8");
            }
            File files = null;
            String fileName = null;
            if (StringUtil.isEmpty(file)) {
                decPosition.setSyllabusId(null);
                decPosition.setSyllabusName(null);
            } else {
                files = new File(file);
                if (files.exists()) {
                    fileName = files.getName(); // 获取原文件名字
                    decPosition.setSyllabusName(fileName);
                } else {
                    decPosition.setSyllabusId(null);
                    decPosition.setSyllabusName(null);
                }
            }
            decPosition.setDeclarationId(declarationId);
            decPosition.setTextbookId(textbookId);
            decPosition.setId(id);
            if (ObjectUtil.isNull(id)) { // 保存或者修改
                decPositionDao.addDecPosition(decPosition);
                String fileNames = null;
                String mongoId = null;
                if (ObjectUtil.notNull(decPosition.getId()) && StringUtil.notEmpty(file)) {
                    // mongoId = fileService.saveLocalFile(files, FileType.SYLLABUS,
                    // decPosition.getId());
                    byte[] fileByte = (byte[]) request.getSession(false).getAttribute(file);
                    fileNames = (String) request.getSession(false).getAttribute("fileName_" + file);
                    InputStream input = new ByteArrayInputStream(fileByte);
                    mongoId =
                            fileService.save(input, fileNames, FileType.SYLLABUS, decPosition.getId());
                    if (StringUtil.isEmpty(mongoId)) {
                        throw new CheckedServiceException(
                                CheckedExceptionBusiness.MATERIAL,
                                CheckedExceptionResult.FILE_UPLOAD_FAILED,
                                "文件上传失败!");
                    }
                }
                if (StringUtil.notEmpty(mongoId)) {
                    decPosition.setSyllabusId(mongoId);
                    decPosition.setSyllabusName(fileNames);
                    decPositionDao.updateDecPosition(decPosition);
                }
            } else {
                decPositionDao.updateDecPosition(decPosition);
            }
        }
        return list.size();
    }

    @Override
    public Map<String, Object> listEditorSelection(Long textbookId, Long materialId,
                                                   String realName, String orgName) throws CheckedServiceException {
        if (ObjectUtil.isNull(textbookId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<DecPositionEditorSelectionVO> listEditorSelectionVOs =
                decPositionDao.listEditorSelection(textbookId,
                        StringUtil.toAllCheck(realName),
                        StringUtil.toAllCheck(orgName));
        // 因为作家申报机构为0时 为人卫社 但机构中又不存在0机构 在此遍历作家申报的机构，如果为null这里设置为人卫社
        for (DecPositionEditorSelectionVO decPositionEditorSelectionVO : listEditorSelectionVOs) {


            HashMap<String, Object> paraMap = new HashMap<>();
            paraMap.put("declarationId",decPositionEditorSelectionVO.getDid());
            String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
            Date date1 = DateUtil.fomatDate(declarationlCreateDate);
            Date date = DateUtil.fomatDate("2019-04-12 12:00");
            if(date1.getTime()>date.getTime()) {


                String post = decPositionEditorSelectionVO.getPresetPosition();
                String post2="";

                if (post != null) {

                    if (ObjectUtil.isNumber(post)) {
                        if(Integer.parseInt(post)==8){
                            post="数字编委";
                        }else{
                            post = dataDictionaryDao.getDataDictionaryItemNameByCode2(Const.PMPH_POSITION, post);
                        }
                    }else{
                        String[] split = post.split(",");
                        for (String s: split) {
                            if(Integer.parseInt(s)==8){
                                post2+="数字编委,";
                            }else{
                                post2 += dataDictionaryDao.getDataDictionaryItemNameByCode2(Const.PMPH_POSITION, s)+",";
                            }
                        }
                        post=post2.substring(0,post2.lastIndexOf(","));

                    }



                }
                decPositionEditorSelectionVO.setStrPresetPosition(post);
            }

            if (null == decPositionEditorSelectionVO.getReportName()) {
                decPositionEditorSelectionVO.setReportName("人民卫生出版社");
            }
        }
        // 排序
        List<DecPositionEditorSelectionVO> selectedDecPositionEditorSelectionVOs =
                new ArrayList<DecPositionEditorSelectionVO>();// 已遴选集合
        List<DecPositionEditorSelectionVO> unselectedDecPositionEditorSelectionVOs =
                new ArrayList<DecPositionEditorSelectionVO>();// 未遴选集合
        for (DecPositionEditorSelectionVO de : listEditorSelectionVOs) {
            /*if (ObjectUtil.notNull(de.getRank())) {
                selectedDecPositionEditorSelectionVOs.add(de);
            } else */
            if (de.getChosenPosition() > 0) {
                selectedDecPositionEditorSelectionVOs.add(de);
            } else {
                unselectedDecPositionEditorSelectionVOs.add(de);
            }
        }
        List<DecPositionEditorSelectionVO> editorList =
                new ArrayList<DecPositionEditorSelectionVO>(selectedDecPositionEditorSelectionVOs.size());// 已遴选主编集合
        List<DecPositionEditorSelectionVO> subeditorList =
                new ArrayList<DecPositionEditorSelectionVO>(selectedDecPositionEditorSelectionVOs.size());// 已遴选副主编集合
        List<DecPositionEditorSelectionVO> editorialMemberList =
                new ArrayList<DecPositionEditorSelectionVO>(selectedDecPositionEditorSelectionVOs.size());// 已遴选编委集合
        List<DecPositionEditorSelectionVO> digitalrList =
                new ArrayList<DecPositionEditorSelectionVO>(selectedDecPositionEditorSelectionVOs.size());// 已遴选数字编委集合
        for (DecPositionEditorSelectionVO decVo : selectedDecPositionEditorSelectionVOs) {
            if (1 == decVo.getChosenPosition()) {// 主编 1100 0100
                editorList.add(decVo);
            } else if (2 == decVo.getChosenPosition() ) {// 副主编
                // 1010
                // 0010
                subeditorList.add(decVo);
            } else if ( decVo.getChosenPosition()>=3) {// 编委 1001
                // 0001
                editorialMemberList.add(decVo);
            } else if (8 == decVo.getChosenPosition()) {// 数字编委 1000
                digitalrList.add(decVo);
            }
        }
        Collections.sort(editorList, new Comparator<DecPositionEditorSelectionVO>() {
            public int compare(DecPositionEditorSelectionVO arg0, DecPositionEditorSelectionVO arg1) {
                Integer com1 = arg0.getRank()==null?0:arg0.getRank();
                Integer com2 = arg1.getRank()==null?0:arg1.getRank();
                return com1.compareTo(com2);
            }
        });

        Collections.sort(subeditorList, new Comparator<DecPositionEditorSelectionVO>() {
            public int compare(DecPositionEditorSelectionVO arg0, DecPositionEditorSelectionVO arg1) {
                Integer com1 = arg0.getRank()==null?0:arg0.getRank();
                Integer com2 = arg1.getRank()==null?0:arg1.getRank();
                return com1.compareTo(com2);
            }
        });
        Collections.sort(unselectedDecPositionEditorSelectionVOs,
                new Comparator<DecPositionEditorSelectionVO>() {
                    public int compare(DecPositionEditorSelectionVO arg0,
                                       DecPositionEditorSelectionVO arg1) {
                        return arg1.getPresetPosition()
                                .compareTo(arg0.getPresetPosition());
                    }
                });
        List<DecPositionEditorSelectionVO> newDecPositionEditorSelectionVOs =
                new ArrayList<DecPositionEditorSelectionVO>(listEditorSelectionVOs.size());// 重新排序后的集合
        newDecPositionEditorSelectionVOs.addAll(editorList);
        newDecPositionEditorSelectionVOs.addAll(subeditorList);
        newDecPositionEditorSelectionVOs.addAll(editorialMemberList);
        newDecPositionEditorSelectionVOs.addAll(digitalrList);
        newDecPositionEditorSelectionVOs.addAll(unselectedDecPositionEditorSelectionVOs);
        resultMap.put("DecPositionEditorSelectionVO", newDecPositionEditorSelectionVOs);
        Material material = materialService.getMaterialById(materialId);
        resultMap.put("IsDigitalEditorOptional", material.getIsDigitalEditorOptional());
        List<Map<String,Object>> isZhuBian = decPositionDao.getIsZhuBian( materialId);
        resultMap.put("isZhuBian",isZhuBian);
        return resultMap;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Integer updateDecPositionEditorSelection(String jsonDecPosition, Integer selectionType,
                                                    Integer editorOrSubeditorType, Integer unselectedHold, Long textbookId, String sessionId)
            throws CheckedServiceException, IOException {
        if (StringUtil.isEmpty(jsonDecPosition)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "遴选职位不能为空");
        }
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }



        Integer count = 0;
        List<DecPosition> decPositions =
                new JsonUtil().getArrayListObjectFromStr(DecPosition.class, jsonDecPosition);// json字符串转List对象集合
       /* if (org.springframework.util.StringUtils.isEmpty(decPositions.get(0).getRank())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "排序不能为空");
        }*/
        // 编委遴选界面 (0:未选中)
        Integer unselectedHold_0 = 0;
        if (CollectionUtil.isEmpty(decPositions) && unselectedHold_0 == unselectedHold
            /*&& 2 == editorOrSubeditorType*/ && 1 == selectionType) {// 编委遴选界面，没有人员被选中也可以进行暂存
            // 查询书籍下所有申报id
            List<Long> ids =
                    decPositionService.getDecPositionIdByBookId(textbookId, editorOrSubeditorType);
            // 初始化作家职位申报表
            if (CollectionUtil.isNotEmpty(ids)) {
                decPositionService.updateDecPositionSetDefault(ids, editorOrSubeditorType);
            }
        } else {
            if (CollectionUtil.isEmpty(decPositions)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                        CheckedExceptionResult.NULL_PARAM, "请选择一个主编/副主编发布"/*"遴选职位为空"*/);
            }
            // (1:确定，2：发布)
            Integer selectionType_1 = 1;
            Integer selectionType_2 = 2;
            // 获取书籍id
            textbookId = decPositions.get(0).getTextbookId();
            // 原来的历史遴选记录
            List<DecPosition> oldlist =
                    decPositionService.listChosenDecPositionsByTextbookId(textbookId);
            // 1:确定
            if (selectionType_1.intValue() == selectionType.intValue()) {
                // 查询书籍下所有申报id
                List<Long> ids =
                        decPositionService.getDecPositionIdByBookId(textbookId, editorOrSubeditorType);
                if (2 == editorOrSubeditorType) {
                    List<Long> newEditorialMemberIds = new ArrayList<Long>();// 遴选的编委ID
                    for (DecPosition decPosition : decPositions) {
                        if (1 == decPosition.getChosenPosition()
                                || 8 == decPosition.getChosenPosition()) {
                            if (!ids.contains(decPosition.getId())) {
                                newEditorialMemberIds.add(decPosition.getId());
                            }
                        }
                    }
                    if (newEditorialMemberIds.isEmpty()) {
                        Textbook textbook = textbookService.getTextbookById(textbookId);
                        if (0 != textbook.getRevisionTimes()) {
                            textbookService.updatRevisionTimesByTextBookId(-1, textbookId);
                        }
                    } else {
                        textbookService.updatRevisionTimesByTextBookId(1, textbookId);
                    }
                    // 清楚dec_position_temp表
                    decPositionTempService.deleteDecPositionTempByTextbookId(textbookId);
                }

                // 初始化作家职位申报表
                if (CollectionUtil.isNotEmpty(ids)) {
                    decPositionService.updateDecPositionSetDefault(ids, editorOrSubeditorType);
                }
                if (CollectionUtil.isNotEmpty(decPositions)) {
                    count = decPositionDao.updateDecPositionEditorSelection(decPositions);
                }
                if (ObjectUtil.isNull(textbookId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                            CheckedExceptionResult.NULL_PARAM, "书籍id为空");
                }
                Integer userType = 1;// 用户类型
                Long updaterId = pmphUser.getId(); // 获取修改者id
                // 添加新的遴选记录
                textbookLogService.addTextbookLog(oldlist, textbookId, updaterId, userType);

            }
            // 2：发布
            if (selectionType_2.intValue() == selectionType.intValue()) {
                // 发布的时候 先确认
                this.updateDecPositionEditorSelection(jsonDecPosition,
                        1,
                        editorOrSubeditorType,
                        unselectedHold,
                        textbookId,
                        sessionId);

                if (ObjectUtil.isNull(textbookId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                            CheckedExceptionResult.NULL_PARAM, "书籍id为空");
                }
                // 获取当前书籍书申报信息(包含没有被遴选上的)
                List<DecPosition> decPositionsList =
                        decPositionService.listDecPositionsByTextBookIds(new ArrayList<Long>(
                                Arrays.asList(textbookId)));
                if (CollectionUtil.isEmpty(decPositionsList)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                            CheckedExceptionResult.NULL_PARAM,
                            "当前书籍还未遴选主编，副主编");
                }
                // DecPositionPublished对象集合
                List<DecPositionPublished> decPositionPublisheds =
                        new ArrayList<DecPositionPublished>(decPositionsList.size());
                for (DecPosition decPosition : decPositionsList) {
                    HashMap<String, Object> paraMap = new HashMap<>();
                    paraMap.put("declarationId",decPosition.getDeclarationId());
                    String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
                    Date date1 = DateUtil.fomatDate(declarationlCreateDate==null?"2019-04-13 12:00":declarationlCreateDate);
                    Date date = DateUtil.fomatDate("2019-04-12 12:00");

                    // 筛选出遴选上的主编副主编人员
                    Integer chosenPosition = decPosition.getChosenPosition();
                    if(date1.getTime()>date.getTime()) {
                        if (null != chosenPosition && (chosenPosition == 1 || chosenPosition == 2)) {
                            decPositionPublisheds.add(new DecPositionPublished(
                                    pmphUser.getId(),
                                    decPosition.getDeclarationId(),
                                    textbookId,
                                    decPosition.getPresetPosition(),
                                    chosenPosition,
                                    decPosition.getRank(),
                                    decPosition.getSyllabusId(),
                                    decPosition.getSyllabusName()));
                        }
                    }else{
                        if (null != chosenPosition
                                && (chosenPosition == 4 || chosenPosition == 2 || chosenPosition == 12 || chosenPosition == 10)) {
                            chosenPosition = chosenPosition > 8 ? chosenPosition - 8 : chosenPosition;
                            decPositionPublisheds.add(new DecPositionPublished(
                                    pmphUser.getId(),
                                    decPosition.getDeclarationId(),
                                    textbookId,
                                    decPosition.getPresetPosition(),
                                    chosenPosition,
                                    decPosition.getRank(),
                                    decPosition.getSyllabusId(),
                                    decPosition.getSyllabusName()));
                        }
                    }

                }
                // 已经公布的列表
                List<DecPositionPublished> lst =
                        decPositionPublishedService.getDecPositionPublishedListByBookId(textbookId);
                List<DecPositionPublished> addNew = new ArrayList<DecPositionPublished>(16);
                for (DecPositionPublished item : lst) {
                    HashMap<String, Object> paraMap = new HashMap<>();
                    paraMap.put("declarationId",item.getDeclarationId());
                    String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
                    Date date1 = DateUtil.fomatDate(declarationlCreateDate);
                    Date date = DateUtil.fomatDate("2019-04-12 12:00");
                    // 编委 | 数字编委
                    if(date1.getTime()>date.getTime()) {
                        if (item.getChosenPosition() == 3) {
                            // item.setChosenPosition(item.getChosenPosition() > 8
                            // ?(item.getChosenPosition()-8):item.getChosenPosition() );
                            addNew.add(item);
                        }

                    }else{
                        if (item.getChosenPosition() == 1 || item.getChosenPosition() >= 8) {
                            // item.setChosenPosition(item.getChosenPosition() > 8
                            // ?(item.getChosenPosition()-8):item.getChosenPosition() );
                            addNew.add(item);
                        }
                    }
                }
                // 加入主编副主编
                for (DecPositionPublished item : decPositionPublisheds) {
                    boolean no = true;
                    for (DecPositionPublished itemadd : addNew) {
                        HashMap<String, Object> paraMap = new HashMap<>();
                        paraMap.put("declarationId",itemadd.getDeclarationId());
                        String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
                        Date date1 = DateUtil.fomatDate(declarationlCreateDate);
                        Date date = DateUtil.fomatDate("2019-04-12 12:00");


                        if (itemadd.getDeclarationId().intValue() == item.getDeclarationId()
                                .intValue()) {
                            no = false;
                            if(date1.getTime()>date.getTime()) {
                                itemadd.setChosenPosition(item.getChosenPosition());
                            }else{
                                itemadd.setChosenPosition(item.getChosenPosition() + 8);
                            }


                            break;
                        }
                    }
                    if (no) {
                        addNew.add(item);
                    }
                }
                // clear Id
                for (DecPositionPublished item : addNew) {
                    item.setId(null);
                }
                // 对比重新发布之前于之后的不同
                List<DecPositionPublished> newMessage = new ArrayList<>();
                for (DecPositionPublished now : decPositionPublisheds) {
                    DecPositionPublished published =
                            decPositionPublishedService.getDecPositionByDeclarationId(now.getDeclarationId(),
                                    now.getTextbookId());
                    if (ObjectUtil.isNull(published)) {
                        newMessage.add(now);
                    }
                    for (DecPositionPublished old : lst) {
                        if (now.getDeclarationId().equals(old.getDeclarationId())) {
                            if (!now.getChosenPosition().equals(old.getChosenPosition())
                                    || !now.getRank().equals(old.getRank())) {
                                newMessage.add(now);
                            }
                        }

                    }
                }

                decPositionPublishedService.deleteDecPositionPublishedByTextBookId(textbookId);// 先删除当前发布人已发布的
                decPositionPublishedService.batchInsertDecPositionPublished(addNew);// 再添加
                // 发布时更新textbook表中is_chief_published（是否已公布主编/副主编）字段
                count = textbookService.updateTextbook(new Textbook(textbookId, true));
                if (count > 0) {
                    // 发送消息
                    systemMessageService.sendWhenConfirmFirstEditor(textbookId, newMessage,pmphUser);
                }
            }
        }
        return count;
    }

    @Override
    public PageResult<DeclarationSituationSchoolResultVO> listChosenDeclarationSituationSchoolResultVOs(
            PageParameter<DeclarationSituationSchoolResultVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
        }
        // 如果机构名称不为空，则为模糊查询
        String schoolName = pageParameter.getParameter().getSchoolName();
        if (StringUtil.notEmpty(schoolName)) {
            pageParameter.getParameter().setSchoolName(schoolName);
        }
        PageResult<DeclarationSituationSchoolResultVO> pageResult =
                new PageResult<DeclarationSituationSchoolResultVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 得到申报单位的总数
        //int total = decPositionDao.getSchoolCount(pageParameter.getParameter().getMaterialId());
        int total = decPositionDao.getSchoolResultPresetChosenCount(pageParameter);
        if (total > 0) {
            boolean flag = false;
            List<DeclarationSituationSchoolResultVO> chosens =
                    decPositionDao.getSchoolResultChosenPage2(pageParameter);
            List<DeclarationSituationSchoolResultVO> presets =
                    decPositionDao.getSchoolResultPresetChosen2(pageParameter);
            HashMap<String, Object> paraMap = new HashMap<>();
            paraMap.put("material_id",pageParameter.getParameter().getMaterialId());
            String declarationlCreateDate = declarationDao.findMaterialCreateDate(paraMap);
            Date date1 = DateUtil.fomatDate(declarationlCreateDate);
            Date date = DateUtil.fomatDate("2019-2-12 12:00");
            if(date1.getTime()>date.getTime()) {
                chosens =
                        decPositionDao.getSchoolResultChosenPage2(pageParameter);
                presets =
                        decPositionDao.getSchoolResultPresetChosen2(pageParameter);

            }
            List<DeclarationSituationSchoolResultVO> delList = new ArrayList<>();
            List<DeclarationSituationSchoolResultVO> list = new ArrayList<>();
            if (null == chosens || chosens.isEmpty()) {
                for (DeclarationSituationSchoolResultVO preset : presets) {
                    // 计算申报人数
                    Integer presetPersons =
                            preset.getPresetPositionEditor() + preset.getPresetPositionSubeditor()
                                    + preset.getPresetPositionEditorial() + preset.getPresetDigitalEditor();
                    preset.setPresetPersons(presetPersons);
                    preset.setState(1);
                    list.add(preset);
                }
                pageResult.setRows(list);
                pageResult.setTotal(total);
                return pageResult;
            }
            if (chosens.size() < presets.size()) {
                flag = true;
            }
            for (DeclarationSituationSchoolResultVO chosen : chosens) {
                for (DeclarationSituationSchoolResultVO preset : presets) {
                    if (preset.getOrgId().equals(chosen.getOrgId())) {
                        delList.add(preset);
                        chosen.setPresetPositionEditor(preset.getPresetPositionEditor());
                        chosen.setPresetPositionSubeditor(preset.getPresetPositionSubeditor());
                        chosen.setPresetPositionEditorial(preset.getPresetPositionEditorial());
                        chosen.setPresetDigitalEditor(preset.getPresetDigitalEditor());
                        break;
                    }
                }
                // 计算申报人数
                Integer presetPersons =
                        chosen.getPresetPositionEditor() + chosen.getPresetPositionSubeditor()
                                + chosen.getPresetPositionEditorial() + chosen.getPresetDigitalEditor();
                // 计算当选人数
                Integer chosenPersons =
                        chosen.getChosenPositionEditor() + chosen.getChosenPositionSubeditor()
                                + chosen.getChosenPositionEditorial() + chosen.getIsDigitalEditor();
                chosen.setPresetPersons(presetPersons);
                chosen.setChosenPersons(chosenPersons);
                chosen.setState(1);
                list.add(chosen);
            }
            if (flag) {
                presets.removeAll(delList);
                for (DeclarationSituationSchoolResultVO preset : presets) {
                    // 计算申报人数
                    Integer presetPersons =
                            preset.getPresetPositionEditor() + preset.getPresetPositionSubeditor()
                                    + preset.getPresetPositionEditorial() + preset.getPresetDigitalEditor();
                    preset.setPresetPersons(presetPersons);
                    preset.setState(1);
                    list.add(preset);
                }
            }
            pageResult.setRows(list);
            pageResult.setTotal(total);
        }
        return pageResult;
    }

    @Override
    public PageResult<DeclarationSituationSchoolResultVO> listPresetDeclarationSituationSchoolResultVOs(
            PageParameter<DeclarationSituationSchoolResultVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
        }
        String schoolName = pageParameter.getParameter().getSchoolName();
        // 如果机构名称不为空，则为模糊查询
        if (StringUtil.notEmpty(schoolName)) {
            pageParameter.getParameter().setSchoolName(schoolName);
        }
        PageResult<DeclarationSituationSchoolResultVO> pageResult =
                new PageResult<DeclarationSituationSchoolResultVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        int total = decPositionDao.getSchoolCount(pageParameter.getParameter().getMaterialId());
        if (total > 0) {

            List<DeclarationSituationSchoolResultVO> presets =
                    decPositionDao.getSchoolResultPreset2(pageParameter);
            List<DeclarationSituationSchoolResultVO> chosens =
                    decPositionDao.getSchoolResultChosen2(pageParameter);

            HashMap<String, Object> paraMap = new HashMap<>();
            paraMap.put("material_id",pageParameter.getParameter().getMaterialId());
            String material_id = declarationDao.findMaterialCreateDate(paraMap);
            Date date1 = DateUtil.fomatDate(material_id);
            Date date = DateUtil.fomatDate("2019-2-12 12:00");
            if(date1.getTime()>date.getTime()) {
               presets =
                        decPositionDao.getSchoolResultPreset2(pageParameter);
                chosens =
                        decPositionDao.getSchoolResultChosen2(pageParameter);
            }

            List<DeclarationSituationSchoolResultVO> list = new ArrayList<>();
            if (null == chosens || chosens.isEmpty()) {
                for (DeclarationSituationSchoolResultVO preset : presets) {
                    // 计算申报人数
                    Integer presetPersons =
                            preset.getPresetPositionEditor() + preset.getPresetPositionSubeditor()
                                    + preset.getPresetPositionEditorial() + preset.getPresetDigitalEditor();
                    preset.setPresetPersons(presetPersons);
                    preset.setState(2);
                    list.add(preset);
                }
                pageResult.setRows(list);
                pageResult.setTotal(total);
                return pageResult;
            }
            for (DeclarationSituationSchoolResultVO preset : presets) {
                for (DeclarationSituationSchoolResultVO chosen : chosens) {
                    if (chosen.getOrgId().equals(preset.getOrgId())) {
                        preset.setChosenPositionEditor(chosen.getChosenPositionEditor());
                        preset.setChosenPositionSubeditor(chosen.getChosenPositionSubeditor());
                        preset.setChosenPositionEditorial(chosen.getChosenPositionEditorial());
                        preset.setIsDigitalEditor(chosen.getIsDigitalEditor());
                        break;
                    }
                }
                // 计算申报人数
                Integer presetPersons =
                        preset.getPresetPositionEditor() + preset.getPresetPositionSubeditor()
                                + preset.getPresetPositionEditorial() + preset.getPresetDigitalEditor();
                // 计算当选人数
                Integer chosenPersons =
                        preset.getChosenPositionEditor() + preset.getChosenPositionSubeditor()
                                + preset.getChosenPositionEditorial() + preset.getIsDigitalEditor();
                preset.setPresetPersons(presetPersons);
                preset.setChosenPersons(chosenPersons);
                preset.setState(2);
                list.add(preset);
            }
            pageResult.setRows(list);
            pageResult.setTotal(total);
        }
        return pageResult;
    }

    @Override
    public DeclarationCountVO getDeclarationCountVO(Long materialId) throws CheckedServiceException {
        DeclarationCountVO declarationCountVO = new DeclarationCountVO();
        Integer schoolDeclarationCount = decPositionDao.getSchoolDeclarationCount(materialId);
        Integer schoolDeclarationChosenCount =
                decPositionDao.getSchoolDeclarationChosenCount(materialId);
        String schoolDeclarationAverage = "0";
        String schoolDeclarationChosenAverage = "0";
        if (decPositionDao.getSchoolCount(materialId) > 0) {
            /*
             * 若院校数量大于0，计算院校申报平均数
             */
            Double presetAverage =
                    (double) schoolDeclarationCount / decPositionDao.getSchoolCount(materialId);
            BigDecimal bigDecimalpreset = new BigDecimal(presetAverage);
            schoolDeclarationAverage =
                    String.valueOf(bigDecimalpreset.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            /*
             * 计算当选数的平均数
             */
            Double chosenAverage =
                    (double) schoolDeclarationChosenCount / decPositionDao.getSchoolCount(materialId);
            BigDecimal bigDecimalchosen = new BigDecimal(chosenAverage);
            schoolDeclarationChosenAverage =
                    String.valueOf(bigDecimalchosen.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("material_id",materialId);
        String material_id = declarationDao.findMaterialCreateDate(paraMap);
        Date date1 = DateUtil.fomatDate(material_id);
        Date date = DateUtil.fomatDate("2019-2-12 12:00");
        Integer editorCount =decPositionDao.getEditorCount(materialId);
        Integer subEditorCount = decPositionDao.getSubEditorCount(materialId);
        Integer editorialCount = decPositionDao.getEditorialCount(materialId);
        Integer digitalCount = decPositionDao.getDigitalCount(materialId);
        Integer chosenEditorCount = decPositionDao.getChosenEditorCount(materialId);
        Integer chosenSubeditorCount = decPositionDao.getChosenSubeditorCount(materialId);
        Integer chosenEditorialCount = decPositionDao.getChosenEditorialCount(materialId);
        Integer chosenDigitalCount = decPositionDao.getChosenDigitalCount(materialId);

        if(date1.getTime()>date.getTime()) {
            editorCount = decPositionDao.getEditorCount2(materialId);
            subEditorCount = decPositionDao.getSubEditorCount2(materialId);
            editorialCount = decPositionDao.getEditorialCount2(materialId);
            chosenEditorCount = decPositionDao.getChosenEditorCount2(materialId);
            chosenSubeditorCount = decPositionDao.getChosenSubeditorCount2(materialId);
            chosenEditorialCount = decPositionDao.getChosenEditorialCount2(materialId);
            digitalCount = decPositionDao.getDigitalCount2(materialId);

        }

        declarationCountVO.setSchoolDeclarationCount(schoolDeclarationCount);
        declarationCountVO.setSchoolDeclarationChosenCount(schoolDeclarationChosenCount);
        declarationCountVO.setSchoolDeclarationAverage(schoolDeclarationAverage);
        declarationCountVO.setSchoolDeclarationChosenAverage(schoolDeclarationChosenAverage);
        declarationCountVO.setEditorCount(editorCount);
        declarationCountVO.setSubEditorCount(subEditorCount);
        declarationCountVO.setEditorialCount(editorialCount);
        declarationCountVO.setDigitalCount(digitalCount);
        declarationCountVO.setChosenEditorCount(chosenEditorCount);
        declarationCountVO.setChosenSubeditorCount(chosenSubeditorCount);
        declarationCountVO.setChosenEditorialCount(chosenEditorialCount);
        declarationCountVO.setChosenDigitalCount(chosenDigitalCount);
        declarationCountVO.setMaterialId(materialId);
        return declarationCountVO;
    }

    @Override
    public PageResult<DeclarationSituationBookResultVO> listDeclarationSituationBookResultVOs(
            PageParameter<DeclarationSituationBookResultVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
        }
        String bookName = pageParameter.getParameter().getBookName();
        if (StringUtil.notEmpty(bookName)) {
            pageParameter.getParameter().setBookName(bookName);
        }
        PageResult<DeclarationSituationBookResultVO> pageResult =
                new PageResult<DeclarationSituationBookResultVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        //int total = decPositionDao.getBooks(pageParameter.getParameter().getMaterialId());
        int total = decPositionDao.getBookListOneCount(pageParameter);
        if (total > 0) {
            List<DeclarationSituationBookResultVO> books =
                    decPositionDao.getBookListOne(pageParameter);

            HashMap<String, Object> paraMap = new HashMap<>();
            paraMap.put("material_id",books.get(0).getMaterialId());
            String material_id = declarationDao.findMaterialCreateDate(paraMap);
            Date date1 = DateUtil.fomatDate(material_id);
            Date date = DateUtil.fomatDate("2019-2-12 12:00");

            List<DeclarationSituationBookResultVO> presets =
                    decPositionDao.getBookResultPreset(pageParameter);
            List<DeclarationSituationBookResultVO> chosens =
                    decPositionDao.getBookResultChosen(pageParameter);
            if(date1.getTime()>date.getTime()) {

                presets =
                        decPositionDao.getBookResultPreset2(pageParameter);
                 chosens =
                        decPositionDao.getBookResultChosen2(pageParameter);


            }



            List<DeclarationSituationBookResultVO> middle = new ArrayList<>();
            List<DeclarationSituationBookResultVO> list = new ArrayList<>();
            if (null == presets || presets.isEmpty()) {
                for (DeclarationSituationBookResultVO book : books) {
                    // 计算申报人数
                    Integer presetPersons =
                            book.getPresetPositionEditor() + book.getPresetPositionSubeditor()
                                    + book.getPresetPositionEditorial() + book.getPresetDigitalEditor();
                    book.setPresetPersons(presetPersons);
                    list.add(book);
                }
                pageResult.setRows(list);
                pageResult.setTotal(total);
                return pageResult;
            }
            for (DeclarationSituationBookResultVO book : books) {
                for (DeclarationSituationBookResultVO preset : presets) {
                    if (preset.getId().equals(book.getId())) {
                        book.setPresetPositionEditor(preset.getPresetPositionEditor());
                        book.setPresetPositionSubeditor(preset.getPresetPositionSubeditor());
                        book.setPresetPositionEditorial(preset.getPresetPositionEditorial());
                        book.setPresetDigitalEditor(preset.getPresetDigitalEditor());
                        break;
                    }
                }
                // 计算申报人数
                Integer presetPersons =
                        book.getPresetPositionEditor() + book.getPresetPositionSubeditor()
                                + book.getPresetPositionEditorial() + book.getPresetDigitalEditor();
                book.setPresetPersons(presetPersons);
                middle.add(book);
            }
            if (null == chosens || chosens.isEmpty()) {
                pageResult.setRows(middle);
                pageResult.setTotal(total);
                return pageResult;
            }
            for (DeclarationSituationBookResultVO book : middle) {
                for (DeclarationSituationBookResultVO chosen : chosens) {
                    if (chosen.getId().equals(book.getId())) {
                        book.setChosenPositionEditor(chosen.getChosenPositionEditor());
                        book.setChosenPositionSubeditor(chosen.getChosenPositionSubeditor());
                        book.setChosenPositionEditorial(chosen.getChosenPositionEditorial());
                        book.setIsDigitalEditor(chosen.getIsDigitalEditor());
                        break;
                    }
                }/*
                // 计算当选人数
                Integer chosenPersons =
                        book.getChosenPositionEditor() + book.getChosenPositionSubeditor()
                                + book.getChosenPositionEditorial() + book.getIsDigitalEditor();*/
                Integer chosenPersons = decPositionDao.getChoseCount(book.getId(),book.getMaterialId(),pageParameter.getParameter().getBookName());
                book.setChosenPersons(chosenPersons==null?0:chosenPersons);
                list.add(book);
            }
            pageResult.setRows(list);
            pageResult.setTotal(total);
        }
        return pageResult;
    }

    @Override
    public PageResult<DeclarationResultSchoolVO> listChosenDeclarationResultSchoolVOs(
            PageParameter<DeclarationResultSchoolVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
        }
        String schoolName = pageParameter.getParameter().getSchoolName();
        if (StringUtil.notEmpty(schoolName)) {
            pageParameter.getParameter().setSchoolName(schoolName);
        }
        PageResult<DeclarationResultSchoolVO> pageResult =
                new PageResult<DeclarationResultSchoolVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        //int total = decPositionDao.getSchoolCount(pageParameter.getParameter().getMaterialId());
        int total = decPositionDao.getSchoolListPresetChosenCount(pageParameter);
        if (total > 0) {
            boolean flag = false;
            List<DeclarationResultSchoolVO> chosens =
                    decPositionDao.getSchoolListChosenPage2(pageParameter);

            HashMap<String, Object> paraMap = new HashMap<>();
            paraMap.put("material_id",pageParameter.getParameter().getMaterialId());
            String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
            Date date1 = DateUtil.fomatDate(declarationlCreateDate);
            Date date = DateUtil.fomatDate("2019-04-12 12:00");
            if(date1.getTime()>date.getTime()) {
               chosens =
                        decPositionDao.getSchoolListChosenPage2(pageParameter);
            }

            List<DeclarationResultSchoolVO> presets =
                    decPositionDao.getSchoolListPresetChosen(pageParameter);
            List<DeclarationResultSchoolVO> delList = new ArrayList<>();
            List<DeclarationResultSchoolVO> list = new ArrayList<>();
            if (null == chosens || chosens.isEmpty()) {
                for (DeclarationResultSchoolVO preset : presets) {
                    preset.setState(1);
                    list.add(preset);
                }
                pageResult.setRows(list);
                pageResult.setTotal(total);
                return pageResult;
            }
            if (chosens.size() < presets.size()) {
                flag = true;
            }
            for (DeclarationResultSchoolVO chosen : chosens) {
                for (DeclarationResultSchoolVO preset : presets) {

                    if (preset.getOrgId().equals(chosen.getOrgId())) {
                        delList.add(preset);

                        //chosen.setBookname(preset.getBookname());
                        if (StringUtil.isEmpty(chosen.getEditorList())) {
                            chosen.setEditorList("-");
                        }
                        if (StringUtil.isEmpty(chosen.getSubEditorList())) {
                            chosen.setSubEditorList("-");
                        }
                        if (StringUtil.isEmpty(chosen.getEditorialList())) {
                            chosen.setEditorialList("-");
                        }
                        if (StringUtil.isEmpty(chosen.getIsDigitalEditorList())) {
                            chosen.setIsDigitalEditorList("-");
                        }
                        break;
                    }
                }
                chosen.setState(1);

                list.add(chosen);
            }
            if (flag) {
                presets.removeAll(delList);
                list.addAll(presets);
            }
            //对list 数据进行排序
            Collections.sort(list, new Comparator<DeclarationResultSchoolVO>() {
                @Override
                public int compare(DeclarationResultSchoolVO o1, DeclarationResultSchoolVO o2) {
                    int i = o1.getSchoolName().compareTo(o2.getSchoolName());
                    return i;
                }
            });
            pageResult.setRows(list);
            pageResult.setTotal(total);
        }
        return pageResult;
    }

    @Override
    public PageResult<DeclarationResultSchoolVO> listPresetDeclarationResultSchoolVOs(
            PageParameter<DeclarationResultSchoolVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
        }
        String schoolName = pageParameter.getParameter().getSchoolName();
        if (StringUtil.notEmpty(schoolName)) {
            pageParameter.getParameter().setSchoolName(schoolName);
        }
        PageResult<DeclarationResultSchoolVO> pageResult =
                new PageResult<DeclarationResultSchoolVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        //int total = decPositionDao.getSchoolCount(pageParameter.getParameter().getMaterialId());
        int total = decPositionDao.getSchoolListPresetChosenCount(pageParameter);
        if (total > 0) {
            List<DeclarationResultSchoolVO> presets =
                    decPositionDao.getSchoolListPreset2(pageParameter);
            List<DeclarationResultSchoolVO> chosens =
                    decPositionDao.getSchoolListChosen2(pageParameter);

            HashMap<String, Object> paraMap = new HashMap<>();
            paraMap.put("material_id",pageParameter.getParameter().getMaterialId());
            String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
            Date date1 = DateUtil.fomatDate(declarationlCreateDate);
            Date date = DateUtil.fomatDate("2019-04-12 12:00");
            if(date1.getTime()>date.getTime()) {
               presets =
                        decPositionDao.getSchoolListPreset2(pageParameter);
               chosens =
                        decPositionDao.getSchoolListChosen2(pageParameter);
            }




            List<DeclarationResultSchoolVO> list = new ArrayList<>();
            if (null == chosens || chosens.isEmpty()) {
                for (DeclarationResultSchoolVO preset : presets) {
                    preset.setState(2);
                    list.add(preset);
                }
                pageResult.setRows(list);
                pageResult.setTotal(total);
                return pageResult;
            }
            for (DeclarationResultSchoolVO preset : presets) {
                for (DeclarationResultSchoolVO chosen : chosens) {
                    if (chosen.getOrgId().equals(preset.getOrgId())) {
                        preset.setEditorList(chosen.getEditorList());
                        preset.setSubEditorList(chosen.getSubEditorList());
                        preset.setEditorialList(chosen.getEditorialList());
                        preset.setIsDigitalEditorList(chosen.getIsDigitalEditorList());
                        if (StringUtil.isEmpty(preset.getEditorList())) {
                            preset.setEditorList("-");
                        }
                        if (StringUtil.isEmpty(preset.getSubEditorList())) {
                            preset.setSubEditorList("-");
                        }
                        if (StringUtil.isEmpty(preset.getEditorialList())) {
                            preset.setEditorialList("-");
                        }
                        if (StringUtil.isEmpty(preset.getIsDigitalEditorList())) {
                            preset.setIsDigitalEditorList("-");
                        }
                        break;
                    }
                }
                preset.setState(2);
                list.add(preset);
            }
            pageResult.setRows(list);
            pageResult.setTotal(total);
        }
        return pageResult;
    }

    @Override
    public PageResult<DeclarationResultBookVO> listDeclarationResultBookVOs(
            PageParameter<DeclarationResultBookVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
        }
        String bookName = pageParameter.getParameter().getBookName();
        if (StringUtil.notEmpty(bookName)) {
            pageParameter.getParameter().setBookName(bookName);
        }
        PageResult<DeclarationResultBookVO> pageResult = new PageResult<DeclarationResultBookVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        //int total = decPositionDao.getBooks(pageParameter.getParameter().getMaterialId());
        int total = decPositionDao.getBookListTwoCount(pageParameter);
        if (total > 0) {
            List<DeclarationResultBookVO> VOs = decPositionDao.getBookChosenList(pageParameter);
            HashMap<String, Object> paraMap = new HashMap<>();
            paraMap.put("material_id",pageParameter.getParameter().getMaterialId());
            String declarationlCreateDate = declarationDao.findMaterialCreateDate(paraMap);
            Date date1 = DateUtil.fomatDate(declarationlCreateDate);
            Date date = DateUtil.fomatDate("2019-02-12 12:00");
            if(date1.getTime()>date.getTime()) {
                VOs = decPositionDao.getBookChosenList2(pageParameter);

            }

            List<DeclarationResultBookVO> books = decPositionDao.getBookListTwo(pageParameter);
            List<DeclarationResultBookVO> list = new ArrayList<>();
            if (null == VOs || VOs.isEmpty()) {
                pageResult.setRows(books);
                pageResult.setTotal(total);
                return pageResult;
            }
            for (DeclarationResultBookVO book : books) {
                for (DeclarationResultBookVO vo : VOs) {
                    if (vo.getId().equals(book.getId())) {
                        book.setEditorList(vo.getEditorList());
                        book.setSubEditorList(vo.getSubEditorList());
                        book.setEditorialList(vo.getEditorialList());
                        book.setIsDigitalEditorList(vo.getIsDigitalEditorList());
                        if (StringUtil.isEmpty(book.getEditorList())) {
                            book.setEditorList("-");
                        }
                        if (StringUtil.isEmpty(book.getSubEditorList())) {
                            book.setSubEditorList("-");
                        }
                        if (StringUtil.isEmpty(book.getEditorialList())) {
                            book.setEditorialList("-");
                        }
                        if (StringUtil.isEmpty(book.getIsDigitalEditorList())) {
                            book.setIsDigitalEditorList("-");
                        }
                        break;
                    }
                }
                list.add(book);
            }
            pageResult.setRows(list);
            pageResult.setTotal(total);
        }
        return pageResult;
    }

    @Override
    public List<TextbookDecVO> getTextbookEditorList(Long textbookId)
            throws CheckedServiceException {
        if (textbookId == null) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.getTextbookEditorList(textbookId);
    }

    @Override
    public List<Long> getDecPositionIdByBookId(Long textbookId, Integer editorOrSubeditorType)
            throws CheckedServiceException {
        if (ObjectUtil.isNull(textbookId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.getDecPositionIdByBookId(textbookId, editorOrSubeditorType);
    }

    @Override
    public Integer updateDecPositionSetDefault(List<Long> ids, Integer editorOrSubeditorType)
            throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "主键id不能为空");
        }
        return decPositionDao.updateDecPositionSetDefault(ids, editorOrSubeditorType);
    }

    @Override
    public List<DecPosition> getDecPositionByTextbookId(Long textbookId)
            throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                    CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.getDecPositionByTextbookId(textbookId);
    }

    @Override
    public List<DecPosition> getMainDecPositionByTextbookId(Long textbookId)
            throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                    CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.getMainDecPositionByTextbookId(textbookId);
    }



    @Override
    public PageResult<TextBookDecPositionVO> listDeclarationByTextbookIds(
            PageParameter<TextBookDecPositionVO> pageParameter) throws CheckedServiceException {
        if (ArrayUtil.isEmpty(pageParameter.getParameter().getTextBookIds())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK,
                    CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        PageResult<TextBookDecPositionVO> pageResult = new PageResult<TextBookDecPositionVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<TextBookDecPositionVO> textBookList =
                decPositionDao.listDeclarationByTextbookIds(pageParameter);
        if (!textBookList.isEmpty()) {
            Integer count = textBookList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(textBookList);
        }
        return pageResult;
    }

    @Override
    public Integer batchPublishEditor(List<Long> textbookIds, String sessionId)
            throws CheckedServiceException, IOException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(textbookIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "未选中书籍");
        }
        Integer count = 0;
        for (Long textbookId : textbookIds) {
            List<DecPosition> listDecPositions = this.getEditorByTextbookId(textbookId);// 查询当前书籍已遴选的主编，副主编
            if (CollectionUtil.isEmpty(listDecPositions)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                        CheckedExceptionResult.NULL_PARAM,
                        "当前选中书籍还未遴选主编，副主编");
            }
            // DecPositionPublished对象集合
            List<DecPositionPublished> decPositionPublisheds =
                    new ArrayList<DecPositionPublished>(listDecPositions.size());
            for (DecPosition decPosition : listDecPositions) {
                decPositionPublisheds.add(new DecPositionPublished(pmphUser.getId(),
                        decPosition.getDeclarationId(),
                        textbookId,
                        decPosition.getPresetPosition(),
                        decPosition.getChosenPosition(),
                        decPosition.getRank(),
                        decPosition.getSyllabusId(),
                        decPosition.getSyllabusName()));
            }
            // 删除已发布的主编，副主编
            decPositionPublishedService.deletePublishedEditorByTextbookId(textbookId);
            // 重新发布
            decPositionPublishedService.batchInsertDecPositionPublished(decPositionPublisheds);// 再添加
            // 发布时更新textbook表中is_chief_published（是否已公布主编/副主编）字段
            count = textbookService.updateTextbook(new Textbook(textbookId, true));
            if (count > 0) {
                // 发送消息
                systemMessageService.sendWhenConfirmFirstEditor(textbookId, decPositionPublisheds,pmphUser);
            }
        }
        return count;
    }

    @Override
    public List<DecPosition> getEditorByTextbookId(Long textbookId) throws CheckedServiceException {
        if (ObjectUtil.isNull(textbookId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "书籍ID为空");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("textbook_id",textbookId);
        String declarationCreateDateByTextBook = declarationDao.findDeclarationCreateDateByTextBook(params);
        java.util.Date date1 = DateUtil.fomatDate(declarationCreateDateByTextBook);
        java.util.Date date = DateUtil.fomatDate("2019-04-12 12:00");
        if(date1.getTime()>date.getTime()) {
            return decPositionDao.getEditorByTextbookId2(textbookId);
        }else{
            return decPositionDao.getEditorByTextbookId(textbookId);
        }

    }
}
