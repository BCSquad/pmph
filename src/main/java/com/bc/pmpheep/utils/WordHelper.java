/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
            map.put(filename, document);
        }
//        XWPFParagraph paragraph = document.createParagraph();
//        XWPFRun run = paragraph.createRun();
//        run.addTab();
//        run.setText("This is a test.");
//        paragraph = document.createParagraph();
//        run = paragraph.createRun();
//        run.setText("这是一个测试。");
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
}
