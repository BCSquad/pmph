/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.back.util.StringUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

    /**
     * 根据Map集合创建工作簿
     *
     * @param maps 工作簿的数据源
     * @param sheetname 表名
     * @return 根据Map集合创建的工作簿
     */
    public Workbook fromMaps(List<Map<String, Object>> maps, String sheetname) {
        if (null == maps || maps.isEmpty()) {
            throw new IllegalArgumentException("生成Excel时数据源对象为空");
        }
        /* 创建工作簿 */
        Workbook workbook = new HSSFWorkbook();
        /* 创建工作表 */
        Sheet sheet = workbook.createSheet(sheetname);
        int rowCount = 0; //行计数
        int columnCount = 0; //列计数
        for (Map<String, Object> map : maps) {
            /* 先设置表头，表头等于Map键名 */
            if (rowCount == 0) {
                Row header = sheet.createRow(0);
                for (String key : map.keySet()) {
                    Cell cell = header.createCell(columnCount);
                    cell.setCellValue(key);
                    columnCount++;
                }
                rowCount++;
            }
            columnCount = 0;//列计数归零
            Row row = sheet.createRow(rowCount);
            /* 开始以键值填充单元格 */
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (null != entry.getValue()) {
                    Cell cell = row.createCell(columnCount);
                    cell.setCellValue(entry.getValue().toString());
                }
                columnCount++;
            }
            rowCount++;
        }
        return workbook;
    }

    /**
     * 根据Map集合创建工作簿并导出到指定路径
     *
     * @param maps 工作簿的数据源
     * @param sheetname 表名
     * @param path Excel导出路径，为空时导出到本工程路径下
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void exportFromMaps(List<Map<String, Object>> maps, String sheetname, String path) throws FileNotFoundException, IOException {
    	LinkedHashSet<Map<String,Object>> set = new LinkedHashSet<>();
    	set.addAll(maps);
    	maps.clear();
    	maps.addAll(set);
        Workbook workbook = fromMaps(maps, sheetname);
        if (StringUtil.isEmpty(path)) {
            path = "";
        }
        StringBuilder sb = new StringBuilder(path);
        sb.append(sheetname);
        sb.append(".xls");
        try (FileOutputStream out = new FileOutputStream(sb.toString())) {
            workbook.write(out);
            out.flush();
        }
    }

    @Deprecated
    public Workbook fromPersistentObject(List dataSource) throws IOException, SecurityException, IllegalArgumentException, IllegalAccessException {
        if (null == dataSource || dataSource.isEmpty()) {
            return null;
        }
        Field[] fields = dataSource.get(0).getClass().getDeclaredFields();
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
            Iterator iterator = dataSource.iterator();
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
