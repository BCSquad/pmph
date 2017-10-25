/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Excel工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class ExcelHelper {

    private final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

    public Workbook getWorkbook(List list) throws IOException, SecurityException, IllegalArgumentException, IllegalAccessException {
        if (null == list || list.isEmpty()) {
            return null;
        }
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        /* 获取字段总数 */
        int totalFields = fields.length;
        try (Workbook workbook = new HSSFWorkbook()) {
            /* 新建工作表 */
            Sheet sheet = workbook.createSheet("123");
            /* 获取Excel首行，利用反射设置表头 */
            Row header = sheet.createRow(0);
            for (int i = 0; i < totalFields; i++) {
                Cell cell = header.createCell(i);
                fields[i].setAccessible(true);
                cell.setCellValue(fields[i].getName());
            }
            /* 设置计数器 */
            int count = 1;
            /* 遍历list中的对象 */
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                Row row = sheet.createRow(count);
                for (int i = 0; i < totalFields; i++) {
                    Object o = fields[i].get(object);
                    if (null != o) {
                        row.createCell(i).setCellValue(o.toString());
                    }
                }
                count++;
            }
            return workbook;
        }
    }
}
