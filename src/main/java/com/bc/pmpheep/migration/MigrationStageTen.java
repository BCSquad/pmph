/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.CmsContentCategory;
import com.bc.pmpheep.back.service.CmsCategoryService;
import com.bc.pmpheep.back.service.CmsContentCategoryService;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 数据迁移方案第十部分 - CMS相关数据迁移
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class MigrationStageTen {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageTen.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    CmsContentService contentService;
    @Resource
    CmsCategoryService categoryService;
    @Resource
    CmsContentCategoryService contentCategoryService;
    @Resource
    FileService fileService;
    @Resource
    ExcelHelper excelHelper;

    public void start() {
        cmsCategory();
    }

    public void cmsCategory() {
        String tableName = "site_column"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        int count = 3;//迁移成功的条目数
        /* 只有三条有效数据，直接新建CmsCategory保存 */
        CmsCategory category1 = new CmsCategory(0L, "0", "公告管理", true);
        category1.setIsMaterialNotice(true);
        category1.setIsClicksVisible(true);
        category1 = categoryService.addCmsCategory(category1);
        long pk = category1.getId();
        JdbcHelper.updateNewPrimaryKey(tableName, pk, "name", "公告管理");//更新旧表中new_pk字段
        CmsCategory category2 = new CmsCategory(0L, "0", "快报管理", true);
        category2.setIsClicksVisible(true);
        category2 = categoryService.addCmsCategory(category2);
        pk = category2.getId();
        JdbcHelper.updateNewPrimaryKey(tableName, pk, "name", "快报管理");//更新旧表中new_pk字
        CmsCategory category3 = new CmsCategory(0L, "0", "医学随笔", false);
        category3.setIsAuthRequired(true);
        category3.setIsAuthorVisible(true);
        category3.setIsClicksVisible(true);
        category3.setIsCommentsAllow(true);
        category3.setIsCommentsVisible(true);
        category3.setIsLikesVisible(true);
        category3.setIsBookmarksVisible(true);
        category3 = categoryService.addCmsCategory(category3);
        pk = category3.getId();
        JdbcHelper.updateNewPrimaryKey(tableName, pk, "name", "医学随笔");//更新旧表中new_pk字
        logger.info("'{}'表迁移完成", tableName);
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
    }

    public void cmsContentAndCategory() {
        String tableName = "site_article"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;//迁移成功的条目数
        for (Map<String, Object> map : maps) {
            CmsContent cmsContent = new CmsContent();
            String title = (String) map.get("title");
            if (StringUtil.isEmpty(title)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "内容标题为空");
                excel.add(map);
                logger.error("内容标题为空，本条数据无效，将记录在Excel中");
                continue;
            }
            cmsContent.setTitle(title);
            //TODO
            BigDecimal colid = (BigDecimal) map.get("colid");
            Long pk = JdbcHelper.getPrimaryKey("site_column", "colid", colid);
            if (null == pk) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "获取site_column表new_pk字段失败，colid=" + colid);
                excel.add(map);
                logger.error("获取site_column表new_pk字段失败，此结果将被记录在Excel中");
                continue;
            }
            cmsContent = contentService.addCmsContent(cmsContent);
            CmsContentCategory contentCategory = new CmsContentCategory(cmsContent.getId(), pk);
            contentCategoryService.addCmsContentCategory(contentCategory);
            Integer category = (Integer) map.get("category");
            if (null == category) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "内容类别为空");
                excel.add(map);
                logger.error("内容类别为空，本条数据无效，将记录在Excel中");
                continue;
            }
            //TODO
        }
    }
}
