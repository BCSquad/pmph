package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyTemplateDao;
import com.bc.pmpheep.back.dao.MaterialSurveyTemplateQuestionDao;
import com.bc.pmpheep.back.dao.SurveyTemplateDao;
import com.bc.pmpheep.back.dao.SurveyTemplateQuestionDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.sessioncontext.SessionContext;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.back.vo.SurveyQuestionListVO;
import com.bc.pmpheep.back.vo.SurveyTemplateListVO;
import com.bc.pmpheep.back.vo.SurveyTemplateVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mchange.lang.LongUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <pre>
 * 功能描述：调研表模版业务层接口实现类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-21
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Service
public class MaterialSurveyTemplateServiceImpl implements MaterialSurveyTemplateService {

    @Autowired
    private MaterialSurveyTemplateDao surveyTemplateDao;
    @Autowired
    private MaterialSurveyService             surveyService;
    @Autowired
    private MaterialSurveyTemplateService  SurveyTemplateService;
    @Autowired
    private MaterialSurveyTemplateQuestionDao surveyTemplateQuestionDao;
    @Autowired
    private MaterialSurveyTemplateQuestionService             surveyQuestionService;
    @Autowired
    MaterialSurveyTemplateQuestionOptionService       surveyQuestionOptionService;
    @Autowired
    MaterialService materialService;

    @Override
    public MaterialSurveyTemplate addSurveyTemplate(MaterialSurveyTemplate surveyTemplate)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyTemplate)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(surveyTemplate.getTemplateName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "模版名称为空");
        }
        if (ObjectUtil.isNull(surveyTemplate.getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "模版创建人为空");
        }

        if(ObjectUtil.notNull(surveyTemplate.getPreVersionMaterialId())){
            surveyTemplate.setPreVersionMaterialName(materialService.getMaterialNameById(surveyTemplate.getPreVersionMaterialId()));
        }

        surveyTemplateDao.addSurveyTemplate(surveyTemplate);

        Long id = surveyTemplate.getId();
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "新增失败");
        }
        return surveyTemplate;
    }

    @Override
    public Integer deleteSurveyTemplateById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateDao.deleteSurveyTemplateById(id);
    }

    @Override
    public Integer updateSurveyTemplate(SurveyTemplate surveyTemplate)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyTemplate)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateDao.updateSurveyTemplate(surveyTemplate);
    }

    @Override
    public SurveyTemplate getSurveyTemplateById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateDao.getSurveyTemplateById(id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public MaterialSurveyTemplate addSurveyTemplateVO(String questionAnswerJosn,String del_question,
                                                      String del_question_option,
                                              MaterialSurveyTemplate surveyTemplateVO, String sessionId) throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(surveyTemplateVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(questionAnswerJosn)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问题及问题选项为空");
        }
        if (StringUtil.isEmpty(surveyTemplateVO.getTemplateName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "模板名称为空");
        }

        List<SurveyTemplateListVO> existedTemplateList = surveyTemplateDao.getTemplateNameList();
        for (SurveyTemplateListVO et: existedTemplateList) {
            if(et.getTemplateName().equals(surveyTemplateVO.getTemplateName())
                    &&!et.getId().equals(surveyTemplateVO.getId())){
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                        CheckedExceptionResult.ILLEGAL_PARAM, "调研表模板重名："+surveyTemplateVO.getTemplateName());
            }
        }
        // json字符串转List对象集合
        List<SurveyQuestionListVO> surveyQuestionListVO;
        List<SurveyQuestionListVO> delQuestionList;
        List<SurveyQuestionOption> delQuestionOption;
        try {
            surveyQuestionListVO =
            new JsonUtil().getArrayListObjectFromStr(SurveyQuestionListVO.class, questionAnswerJosn);
            delQuestionList =
                    new JsonUtil().getArrayListObjectFromStr(SurveyQuestionListVO.class, del_question);
            delQuestionOption =
                    new JsonUtil().getArrayListObjectFromStr(SurveyQuestionOption.class, del_question_option);
        } catch (Exception e) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.VO_CONVERSION_FAILED,
                                              "json字符串转List对象失败");
        }
        if (CollectionUtil.isEmpty(surveyQuestionListVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        String templateName = surveyTemplateVO.getTemplateName();// 调研表名称
        if (templateName.length() > 50) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表标题不能超过50个字符");
        }
        String intro = surveyTemplateVO.getIntro();// 调研表概述
        if (intro.length() > 200) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表概述不能超过200个字符");
        }
        Long typeId = surveyTemplateVO.getTypeId();// 调研表类型
        Long userId = pmphUser.getId();// 调研表创建人
        surveyTemplateVO.setUserId(userId);


        MaterialSurveyTemplate surveyTemplate = addSurveyTemplate(surveyTemplateVO); // 添加模版表

        Long templateId = surveyTemplate.getId(); // 获取模版id
        if (ObjectUtil.isNull(templateId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "新增模版失败");
        }

        // 添加问题及问题选项
        List<Long> newIds = addQuestionAndOption(surveyQuestionListVO,delQuestionList,delQuestionOption,templateId);

        return surveyTemplate;
    }

    @Override
    public Map<String, Object> getSurveyTemplateQuestionByTemplateId(Long templateId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(templateId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "模版ID为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("survey", SurveyTemplateService.getSurveyTemplateById(templateId));

        resultMap.put("qestionAndOption",
                      surveyTemplateDao.getSurveyTemplateQuestionByTemplateId(templateId));
        return resultMap;
    }

    @Override
    public PageResult<SurveyTemplateListVO> listSurveyTemplateList(
    PageParameter<SurveyTemplateListVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        PageResult<SurveyTemplateListVO> pageResult = new PageResult<SurveyTemplateListVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<SurveyTemplateListVO> surveyTemplateList =
        surveyTemplateDao.listSurveyTemplateList(pageParameter);
        if (CollectionUtil.isNotEmpty(surveyTemplateList)) {
            Integer count = surveyTemplateList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(surveyTemplateList);
        }
        return pageResult;
    }

    /**
     * 获取已存在的模板名称
     * @return
     */
    @Override
    public List<SurveyTemplateListVO> getTemplateNameList() {
        List<SurveyTemplateListVO> list = surveyTemplateDao.getTemplateNameList();
        return list;
    }

    /**
     * 
     * <pre>
     * 功能描述：添加问题及问题选项
     * 使用示范：
     *
     * @param surveyQuestionListVO
     * @return
     * </pre>
     */
    private List<Long> addQuestionAndOption(List<SurveyQuestionListVO> surveyQuestionListVO,
                                            List<SurveyQuestionListVO> delQuestionList,
                                            List<SurveyQuestionOption> delQuestionOption,Long templateId) {
        List<Long> questionIds = new ArrayList<Long>(surveyQuestionListVO.size());

        if(!CollectionUtil.isEmpty(delQuestionList)){
            List<SurveyQuestion> delQuestionSurveyList = new ArrayList<SurveyQuestion>(delQuestionList.size());
            for (SurveyQuestionListVO delQuestion:delQuestionList) {
                delQuestionSurveyList.add(new SurveyQuestion(delQuestion.getId(),
                        delQuestion.getTitle(),
                        delQuestion.getDeleted(),
                        delQuestion.getType(),
                        delQuestion.getDirection(),
                        delQuestion.getSort()!=null?delQuestion.getSort():999,
                        templateId
                        ));
            }
            surveyQuestionService.batchInsertSurveyTemplateQuestion(delQuestionSurveyList);
        }

        if(!CollectionUtil.isEmpty(delQuestionOption)){
            for (SurveyQuestionOption delOption : delQuestionOption) {
                delOption.setDeleted(true);
                delOption.setQuestionId(0L);
            }
            surveyQuestionOptionService.batchInsertSurveyQuestionOption(delQuestionOption);
        }

        for (SurveyQuestionListVO surveyQuestionLists : surveyQuestionListVO) { // 遍历获取问题的集合
            SurveyQuestion surveyQuestion =
            new SurveyQuestion(surveyQuestionLists.getId(),surveyQuestionLists.getCategoryId(),surveyQuestionLists.getTitle(), surveyQuestionLists.getType(),
                               surveyQuestionLists.getSort(), surveyQuestionLists.getDirection(),surveyQuestionLists.getIsAnswer()); // 问题实体
            surveyQuestion.setSurveyId(templateId);
            Long categoryId = surveyTemplateQuestionDao.getCategoryIdByName(surveyQuestionLists.getCategoryName());
            surveyQuestion.setCategoryId(categoryId);
            SurveyQuestion surveyQuestion_saved =
            surveyQuestionService.addSurveyTemplateQuestion(surveyQuestion); // 先保存问题
            if (ObjectUtil.isNull(surveyQuestion_saved)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                                  CheckedExceptionResult.NULL_PARAM, "新增数据为空");
            }
            Long newId = surveyQuestion_saved.getId(); // 获取数据库新生成的问题id
            questionIds.add(newId);
            if (Const.SURVEY_QUESTION_TYPE_1 == surveyQuestionLists.getType()
                || Const.SURVEY_QUESTION_TYPE_2 == surveyQuestionLists.getType()) {
                List<SurveyQuestionOption> surveyQuestionOptionList =
                surveyQuestionLists.getSurveyQuestionOptionList(); // 获取问题选项list
                for (SurveyQuestionOption surveyQuestionOptions : surveyQuestionOptionList) { // 遍历问题选项
                    surveyQuestionOptions.setQuestionId(newId);
                    surveyQuestionOptions.setDeleted(false);
                    //surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOptions); // 再保存问题选项
                }
                surveyQuestionOptionService.batchInsertSurveyQuestionOption(surveyQuestionOptionList);
            }
        }
        return questionIds;
    }

    @Override
    public MaterialSurveyTemplate addSurveyTemplateFromExcel(List<SurveyQuestionListVO> surveyQuestionListVO,
                                                             Set<String> categoryNameSet,
                                                             MaterialSurveyTemplate surveyTemplateVO, String sessionId) throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(surveyTemplateVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(surveyTemplateVO.getTemplateName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "模板名称为空");
        }

        List<SurveyTemplateListVO> existedTemplateList = surveyTemplateDao.getTemplateNameList();
        for (SurveyTemplateListVO et: existedTemplateList) {
            if(et.getTemplateName().equals(surveyTemplateVO.getTemplateName())
                    &&!et.getId().equals(surveyTemplateVO.getId())){
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                        CheckedExceptionResult.ILLEGAL_PARAM, "调研表模板重名："+surveyTemplateVO.getTemplateName());
            }
        }
        String templateName = surveyTemplateVO.getTemplateName();// 调研表名称
        if (templateName.length() > 50) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "调研表标题不能超过50个字符");
        }
        if (CollectionUtil.isEmpty(surveyQuestionListVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, surveyTemplateVO.getTemplateName()+"问题选项为空");
        }

        String intro = surveyTemplateVO.getIntro();// 调研表概述
        if (intro.length() > 200) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "调研表概述不能超过200个字符");
        }
        Long typeId = surveyTemplateVO.getTypeId();// 调研表类型
        Long userId = pmphUser.getId();// 调研表创建人
        surveyTemplateVO.setUserId(userId);

        MaterialSurveyTemplate surveyTemplate = addSurveyTemplate(surveyTemplateVO); // 添加模版表

        Long templateId = surveyTemplate.getId(); // 获取模版id
        if (ObjectUtil.isNull(templateId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "新增模版失败");
        }

        categoryNameSet.remove(null);
        categoryNameSet.remove("");
        if(CollectionUtil.isNotEmpty(categoryNameSet)){
            int categoryInsertNum =surveyTemplateQuestionDao.batchInsertCategory(new ArrayList<>(categoryNameSet));
        }

        // 添加问题及问题选项
        List<Long> newIds = addQuestionAndOption(surveyQuestionListVO,null,null,templateId);

        return surveyTemplate;
    }

    @Override
    public ResponseBean importExcel(MultipartFile file,String sessionId) {
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        Workbook workbook = null;
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (FileNotFoundException e) {
            throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
                    CheckedExceptionResult.NULL_PARAM, "获取上传的文件失败");
        } catch (IOException e){
            throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
                    CheckedExceptionResult.ILLEGAL_PARAM, "读取文件失败");
        }
        try {
            if (".xls".equals(fileType)){
                workbook = new HSSFWorkbook(in);
            } else if (".xlsx".equals(fileType)){
                workbook = new XSSFWorkbook(in);
            } else {
                throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
                        CheckedExceptionResult.ILLEGAL_PARAM, "读取的不是Excel文件");
            }
        } catch (IOException e){
            throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
                    CheckedExceptionResult.ILLEGAL_PARAM, "文件读取失败");
        } catch (OfficeXmlFileException e){
            throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
                    CheckedExceptionResult.ILLEGAL_PARAM, "此文档不是对应的.xls或.xlsx的Excel文档，请修改为正确的后缀名再进行上传");
        }

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);

        //以fullNamePath为key
        Map<String,ProductType> productTypeMap = new HashMap<String,ProductType>();
        List<MaterialSurveyTemplate> surveyTemplateList = new ArrayList<>();
        for (int numSheet =0 ; numSheet < workbook.getNumberOfSheets(); numSheet++ ){

            List<SurveyQuestionListVO> surveyQuestionListVO = new ArrayList<SurveyQuestionListVO>();
            Set<String> categoryNameSet = new HashSet<String>();
            MaterialSurveyTemplate surveyTemplateVO = new MaterialSurveyTemplate();

            Sheet sheet = workbook.getSheetAt(numSheet);
            if (null == sheet||sheet.getRow(0)==null){
                continue;
            }

            //调研表名称
            String templateName = StringUtil.getCellValue(sheet.getRow(0).getCell(1));

            templateName = StringUtil.isEmpty(templateName)?sheet.getSheetName():templateName;
            templateName = StringUtil.isEmpty(templateName)?file.getOriginalFilename():templateName;
            surveyTemplateVO.setTemplateName(templateName);
            if(StringUtil.isEmpty(templateName)){
                continue;
            }
            //调研表说明
            String intro = StringUtil.getCellValue(sheet.getRow(1).getCell(1));
            surveyTemplateVO.setIntro(intro);
            surveyTemplateVO.setTypeId(3L);
            surveyTemplateVO.setUserId(pmphUser.getId());

            int questSort = 1;

            for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++){
                Row row = sheet.getRow(rowNum);
                if (null == row){
                    break;
                }

                SurveyQuestionListVO surveyQuestion = new SurveyQuestionListVO();
                //问题类型
                String qTypeStr = StringUtil.getCellValue(row.getCell(0));
                Short typeId = 4;
                switch (qTypeStr){
                    case "单行文本":
                        typeId = 4;
                        break;
                    case "多行文本":
                        typeId = 5;
                        break;
                    case "单项选择":
                        typeId = 1;
                        break;
                    case "多项选择":
                        typeId = 2;
                        break;
                    default:
                        typeId = 4;
                    break;
                }

                //问题分类
                String categoryName = StringUtil.getCellValue(row.getCell(1));
                surveyQuestion.setCategoryName(categoryName);
                if(StringUtil.notEmpty(categoryName)){
                    categoryNameSet.add(categoryName);
                }
                //问题题干
                surveyQuestion.setTitle(StringUtil.getCellValue(row.getCell(2)));
                if(StringUtil.isEmpty(surveyQuestion.getTitle())){
                    break;
                }
                List<SurveyQuestionOption> optionList = new ArrayList<>();
                surveyQuestion.setSurveyQuestionOptionList(optionList);

                if(typeId != null && (typeId == 1 || typeId == 2)){
                    //问题选项
                    for(int cellNum = 3;cellNum < row.getLastCellNum();cellNum++){
                        //给每个单元格创建或关联实体类
                        Cell cell = row.getCell(cellNum);
                        String cellOptionName = StringUtil.getCellValue(cell);

                        if (StringUtil.isEmpty(cellOptionName)){
                        /*throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
                                CheckedExceptionResult.NULL_PARAM, "Excel文件里序号为" + rowNum + "的分类名称为空");*/
                            continue;
                        }else{
                            SurveyQuestionOption option = new SurveyQuestionOption();
                            option.setOptionContent(cellOptionName);
                            optionList.add(option);
                        }

                    }
                    //若无选项 设为单行文本
                    if(CollectionUtil.isEmpty(optionList)){
                        typeId = 4;
                    }
                }
                surveyQuestion.setType(typeId);
                surveyQuestion.setSort(questSort);
                questSort += 1;
                surveyQuestionListVO.add(surveyQuestion);

            }

            if(CollectionUtil.isEmpty(surveyQuestionListVO)){
                break;
            }
            //TODO 如果插入速度太慢 此处可以改为启动线程
            surveyTemplateVO = addSurveyTemplateFromExcel(surveyQuestionListVO, categoryNameSet, surveyTemplateVO, sessionId);
            surveyTemplateList.add(surveyTemplateVO);
        }

        return new ResponseBean(surveyTemplateList);
    }

}
