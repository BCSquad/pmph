/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Microsoft Offic Word 工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class WordHelper {

    private final Logger logger = LoggerFactory.getLogger(WordHelper.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public HashMap<String, XWPFDocument> fromDeclarationEtcBOList(List<DeclarationEtcBO> list) throws CheckedServiceException {
        InputStream is;
        XWPFDocument document;
        String path = this.getClass().getClassLoader().getResource("ResumeTemplate.docx").getPath();
        HashMap<String, XWPFDocument> map = new HashMap<>(list.size());
        for (DeclarationEtcBO bo : list) {
            try {
                is = new FileInputStream(path);
                document = new XWPFDocument(is);
            } catch (IOException ex) {
                logger.error("读取Word模板文件'ResumeTemplate.docx'时出现错误：{}", ex.getMessage());
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                        CheckedExceptionResult.CREATE_FILE_FAILED, "未找到模板文件");
            }
            /* 申报单位 */
            String chosenOrgName = bo.getChosenOrgName();
            if (StringUtil.notEmpty(chosenOrgName)) {
                document.getParagraphs().get(12).createRun().setText(chosenOrgName);
            }
            List<XWPFTable> tables = document.getTables();
            String filename = generateFileName(bo);
            fillDeclarationData(tables.get(0), bo);
            fillDecEduExpData(tables.get(1), bo.getDecEduExps());
            fillDecWorkExpData(tables.get(2), bo.getDecWorkExps());
            fillDecTeachExpData(tables.get(3), bo.getDecTeachExps());
            fillDecAchievementData(tables.get(4),bo.getDecAchievement());
            fillDecAcadeData(tables.get(5),bo.getDecAcades());
            fillDecLastPositionData(tables.get(6),bo.getDecLastPositions());
            fillDecCourseConstructionData(tables.get(7),bo.getDecCourseConstructions());
            fillDecNationalPlanData(tables.get(8),bo.getDecNationalPlans());
            fillDecTextbookData(tables.get(9), bo.getDecTextbooks());
            fillDecResearchData(tables.get(10),bo.getDecResearchs());
            map.put(filename, document);
        }
        return map;
    }

    private String generateFileName(DeclarationEtcBO bo) throws CheckedServiceException {
        String realname = bo.getRealname();
        String textbookName = bo.getTextbookName();
        String presetPosition = bo.getPresetPosition();
        String filename;
        if (StringUtil.notEmpty(textbookName) && StringUtil.notEmpty(presetPosition)) {
            if (StringUtil.isEmpty(realname)) {
                realname = "未署名";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(textbookName);
            sb.append("_");
            sb.append(realname);
            sb.append("_");
            sb.append(presetPosition);
            sb.append(".docx");
            filename = sb.toString();
        } else {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, realname.concat("的申报图书或申报职位为空"));
        }
        return filename;
    }

    private XWPFTable fillDeclarationData(XWPFTable table, DeclarationEtcBO bo) {
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells = rows.get(0).getTableCells();
        /* 第一行 */
        String realname = bo.getRealname();
        if (StringUtil.notEmpty(realname)) {
            cells.get(1).setText(realname);
        }
        String textbookName = bo.getTextbookName();
        String presetPosition = bo.getPresetPosition();
        cells.get(3).setText("《".concat(textbookName).concat("》").concat(presetPosition));
        /* 第二行 */
        cells = rows.get(1).getTableCells();
        String sex = bo.getSex();
        if (StringUtil.notEmpty(sex)) {
            cells.get(1).setText(sex);
        }
        String birthday = bo.getBirthday();
        if (StringUtil.notEmpty(birthday)) {
            cells.get(3).setText(birthday);
        }
        Integer experience = bo.getExperience();
        if (ObjectUtil.notNull(experience)) {
            cells.get(5).setText(String.valueOf(experience));
        }
        /* 第三行 */
        cells = rows.get(2).getTableCells();
        String orgname = bo.getOrgName();
        if (StringUtil.notEmpty(orgname)) {
            cells.get(1).setText(orgname);
        }
        String position = bo.getPosition();
        if (StringUtil.notEmpty(position)) {
            cells.get(3).setText(position);
        }
        String title = bo.getTitle();
        if (StringUtil.notEmpty(title)) {
            cells.get(5).setText(title);
        }
        /* 第四行 */
        cells = rows.get(3).getTableCells();
        String postcode = bo.getPostcode();
        if (StringUtil.notEmpty(postcode)) {
            cells.get(1).setText(postcode);
        }
        String address = bo.getAddress();
        if (StringUtil.notEmpty(address)) {
            cells.get(3).setText(address);
        }
        /* 第五行 */
        cells = rows.get(4).getTableCells();
        String telephone = bo.getTelephone();
        if (StringUtil.notEmpty(telephone)) {
            cells.get(1).setText(telephone);
        }
        String fax = bo.getFax();
        if (StringUtil.notEmpty(fax)) {
            cells.get(3).setText(fax);
        }
        String handphone = bo.getHandphone();
        if (StringUtil.notEmpty(handphone)) {
            cells.get(5).setText(handphone);
        }
        /* 第六行 */
        cells = rows.get(5).getTableCells();
        String email = bo.getEmail();
        if (StringUtil.notEmpty(email)) {
            cells.get(1).setText(email);
        }
        return table;
    }

    private XWPFTable fillDecEduExpData(XWPFTable table, List<DecEduExp> decEduExps) {
        if (CollectionUtil.isEmpty(decEduExps)) {
            return table;
        }
        if (decEduExps.size() > 1) {
            for (int i = 1; i < decEduExps.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecEduExp decEduExp : decEduExps) {
            cells = rows.get(rowCount).getTableCells();
            String value = decEduExp.getDateBegin().concat("～").concat(decEduExp.getDateEnd());
            cells.get(0).setText(value);
            value = decEduExp.getSchoolName();
            cells.get(1).setText(value);
            value = decEduExp.getMajor();
            cells.get(2).setText(value);
            value = decEduExp.getDegree();
            cells.get(3).setText(value);
            value = decEduExp.getNote();
            cells.get(4).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }

    private XWPFTable fillDecWorkExpData(XWPFTable table, List<DecWorkExp> decWorkExps) {
        if (CollectionUtil.isEmpty(decWorkExps)) {
            return table;
        }
        if (decWorkExps.size() > 1) {
            for (int i = 1; i < decWorkExps.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecWorkExp decWorkExp : decWorkExps) {
            cells = rows.get(rowCount).getTableCells();
            String value = decWorkExp.getDateBegin().concat("～").concat(decWorkExp.getDateEnd());
            cells.get(0).setText(value);
            value = decWorkExp.getOrgName();
            cells.get(1).setText(value);
            value = decWorkExp.getPosition();
            cells.get(2).setText(value);
            value = decWorkExp.getNote();
            cells.get(3).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }

    private XWPFTable fillDecTeachExpData(XWPFTable table, List<DecTeachExp> decTeachExps) {
        if (CollectionUtil.isEmpty(decTeachExps)) {
            return table;
        }
        if (decTeachExps.size() > 1) {
            for (int i = 1; i < decTeachExps.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecTeachExp decTeachExp : decTeachExps) {
            cells = rows.get(rowCount).getTableCells();
            String value = decTeachExp.getDateBegin().concat("～").concat(decTeachExp.getDateEnd());
            cells.get(0).setText(value);
            value = decTeachExp.getSchoolName();
            cells.get(1).setText(value);
            value = decTeachExp.getSubject();
            cells.get(2).setText(value);
            value = decTeachExp.getNote();
            cells.get(3).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }

    private XWPFTable fillDecAchievementData(XWPFTable table, DecAchievement decAchievement) {
        if (null == decAchievement) {
            return table;
        }
        List<XWPFTableRow> rows = table.getRows();
        rows.get(0).getCell(0).setText(decAchievement.getContent());
        return table;
    }

    private XWPFTable fillDecAcadeData(XWPFTable table, List<DecAcade> decAcades) {
        if (CollectionUtil.isEmpty(decAcades)) {
            return table;
        }
        if (decAcades.size() > 1) {
            for (int i = 1; i < decAcades.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecAcade decAcade : decAcades) {
            cells = rows.get(rowCount).getTableCells();
            String value = decAcade.getOrgName();
            cells.get(0).setText(value);
            int rank = decAcade.getRank();//1=国际/2=国家/3=省部/4=其他
            switch (rank) {
                case 1:
                    value = "国际";
                    break;
                case 2:
                    value = "国家";
                    break;
                case 3:
                    value = "省部";
                    break;
                case 4:
                    value = "其他";
                    break;
                default:
                    value = "无";
                    break;
            }
            cells.get(1).setText(value);
            value = decAcade.getPosition();
            cells.get(2).setText(value);
            value = decAcade.getNote();
            cells.get(3).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }
    
    private XWPFTable fillDecLastPositionData(XWPFTable table, List<DecLastPosition> decLastPositions) {
        if (CollectionUtil.isEmpty(decLastPositions)) {
            return table;
        }
        if (decLastPositions.size() > 1) {
            for (int i = 1; i < decLastPositions.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecLastPosition decLastPosition : decLastPositions) {
            cells = rows.get(rowCount).getTableCells();
            String value = decLastPosition.getMaterialName();
            cells.get(0).setText(value);
            int position = decLastPosition.getPosition();//0=无/1=主编/2=副主编/3=编委
            switch (position) {
                case 1:
                    value = "主编";
                    break;
                case 2:
                    value = "副主编";
                    break;
                case 3:
                    value = "编委";
                    break;
                default:
                    value = "无";
                    break;
            }
            cells.get(1).setText(value);
            value = decLastPosition.getNote();
            cells.get(2).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }
    
    private XWPFTable fillDecCourseConstructionData(XWPFTable table, List<DecCourseConstruction> decCourseConstructions) {
        if (CollectionUtil.isEmpty(decCourseConstructions)) {
            return table;
        }
        if (decCourseConstructions.size() > 1) {
            for (int i = 1; i < decCourseConstructions.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecCourseConstruction decCourseConstruction : decCourseConstructions) {
            cells = rows.get(rowCount).getTableCells();
            String value = decCourseConstruction.getCourseName();
            cells.get(0).setText(value);
            int type = decCourseConstruction.getType();//1=国家/2=省部/3=学校
            switch (type) {
                case 1:
                    value = "国家";
                    break;
                case 2:
                    value = "省部";
                    break;
                case 3:
                    value = "学校";
                    break;
                default:
                    value = "其他";
                    break;
            }
            cells.get(1).setText(value);
            value = decCourseConstruction.getClassHour();
            cells.get(2).setText(value);
            value = decCourseConstruction.getNote();
            cells.get(3).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }
    
    private XWPFTable fillDecNationalPlanData(XWPFTable table, List<DecNationalPlan> decNationalPlans) {
        if (CollectionUtil.isEmpty(decNationalPlans)) {
            return table;
        }
        if (decNationalPlans.size() > 1) {
            for (int i = 1; i < decNationalPlans.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecNationalPlan decNationalPlan : decNationalPlans) {
            cells = rows.get(rowCount).getTableCells();
            String value = decNationalPlan.getMaterialName();
            cells.get(0).setText(value);
            value = decNationalPlan.getIsbn();
            cells.get(1).setText(value);
            int rank = decNationalPlan.getRank();//1=教育部十二五/2=国家卫计委十二五/3=both
            switch (rank) {
                case 1:
                    value = "教育部十二五";
                    break;
                case 2:
                    value = "国家卫计委十二五";
                    break;
                case 3:
                    value = "教育部和国家卫计委十二五";
                    break;
                default:
                    value = "其他";
                    break;
            }
            cells.get(2).setText(value);
            value = decNationalPlan.getNote();
            cells.get(3).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }
    
    private XWPFTable fillDecTextbookData(XWPFTable table, List<DecTextbook> decTextbooks) {
        if (CollectionUtil.isEmpty(decTextbooks)) {
            return table;
        }
        if (decTextbooks.size() > 1) {
            for (int i = 1; i < decTextbooks.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecTextbook decTextbook : decTextbooks) {
            cells = rows.get(rowCount).getTableCells();
            String value = decTextbook.getMaterialName();
            cells.get(0).setText(value);
            /* 1=国家/2=省部/3=协编/4=/5=其他教材/6=教育部规划/7=卫计委规划/8=区域规划/9=创新教材 */
            int rank = decTextbook.getRank();
            switch (rank) {
                case 1:
                    value = "国家";
                    break;
                case 2:
                    value = "省部";
                    break;
                case 3:
                    value = "协编";
                    break;
                case 4:
                    value = "校本";
                    break;
                case 5:
                    value = "其他教材";
                    break;
                case 6:
                    value = "教育部规划";
                    break;
                case 7:
                    value = "卫计委规划";
                    break;
                case 8:
                    value = "区域规划";
                    break;
                case 9:
                    value = "创新教材";
                    break;
                default:
                    value = "其他";
                    break;
            }
            cells.get(1).setText(value);
            int position = decTextbook.getPosition();//0=无/1=主编/2=副主编/3=编委
            switch (position) {
                case 1:
                    value = "主编";
                    break;
                case 2:
                    value = "副主编";
                    break;
                case 3:
                    value = "编委";
                    break;
                default:
                    value = "无";
                    break;
            }
            cells.get(2).setText(value);
            Date publishDate = decTextbook.getPublishDate();
            value = sdf.format(publishDate);
            cells.get(3).setText(value);
            value = decTextbook.getIsbn();
            cells.get(4).setText(value);
            value = decTextbook.getNote();
            cells.get(5).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }
    
    private XWPFTable fillDecResearchData(XWPFTable table, List<DecResearch> decResearchs) {
        if (CollectionUtil.isEmpty(decResearchs)) {
            return table;
        }
        if (decResearchs.size() > 1) {
            for (int i = 1; i < decResearchs.size(); i++) {
                table.createRow();
            }
        }
        List<XWPFTableRow> rows = table.getRows();
        List<XWPFTableCell> cells;
        int rowCount = 1;
        for (DecResearch decResearch : decResearchs) {
            cells = rows.get(rowCount).getTableCells();
            String value = decResearch.getResearchName();
            cells.get(0).setText(value);
            value = decResearch.getApprovalUnit();
            cells.get(1).setText(value);
            value = decResearch.getAward();
            cells.get(2).setText(value);
            value = decResearch.getNote();
            cells.get(3).setText(value);
            for (XWPFTableCell cell : cells) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
            rowCount++;
        }
        return table;
    }
}
