/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.service.CmsCategoryService;
import com.bc.pmpheep.back.service.CmsContentCategoryService;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;
import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    CmsContentService cmsContentService;
    @Resource
    CmsCategoryService cmsCategoryService;
    @Resource
    CmsContentCategoryService cmsContentCategoryService;
    @Resource
    FileService fileService;
    @Resource
    ExcelHelper excelHelper;
    @Resource
    ContentService contentService;

    public void start() {
        Date begin = new Date();
        cmsCategory();
        cmsContent();
        logger.info("迁移第十步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
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
        category1 = cmsCategoryService.addCmsCategory(category1);
        long pk = category1.getId();
        JdbcHelper.updateNewPrimaryKey(tableName, pk, "name", "公告管理");//更新旧表中new_pk字段
        CmsCategory category2 = new CmsCategory(0L, "0", "快报管理", true);
        category2.setIsClicksVisible(true);
        category2 = cmsCategoryService.addCmsCategory(category2);
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
        category3 = cmsCategoryService.addCmsCategory(category3);
        pk = category3.getId();
        JdbcHelper.updateNewPrimaryKey(tableName, pk, "name", "医学随笔");//更新旧表中new_pk字
        logger.info("'{}'表迁移完成", tableName);
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
    }

    public void cmsContent() {
        String tableName = "site_article"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        List<Map<String, Object>> excel = new LinkedList<>();
        String sql = "SELECT sysflag FROM sys_user WHERE userid = ?";
        int count = 0;//迁移成功的条目数
        for (Map<String, Object> map : maps) {
            CmsContent cmsContent = new CmsContent();
            cmsContent.setParentId(0L);//全是文章，没有评论
            cmsContent.setPath("0");
            String title = (String) map.get("title");
            if (StringUtil.isEmpty(title)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "内容标题为空");
                excel.add(map);
                logger.error("内容标题为空，本条数据无效，将记录在Excel中");
                continue;
            }
            cmsContent.setTitle(title);
            String ct = (String) map.get("content");
            if (StringUtil.isEmpty(ct)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "文章内容为空");
                excel.add(map);
                logger.error("文章内容为空，本条数据无效，将记录在Excel中");
                continue;
            }
            BigDecimal colid = (BigDecimal) map.get("colid");
            Long pk = JdbcHelper.getPrimaryKey("site_column", "colid", colid);
            if (null == pk) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "获取site_column表new_pk字段失败，colid=" + colid);
                excel.add(map);
                logger.error("获取site_column表new_pk字段失败，此结果将被记录在Excel中");
                continue;
            }
            cmsContent.setCategoryId(pk);
            String brief = (String) map.get("brief");
            if (StringUtil.notEmpty(brief)) {
                cmsContent.setSummary(brief);
            }
            /* 判断作者类型 */
            String userid = (String) map.get("publisheruserid");
            Long userId = JdbcHelper.getPrimaryKey("sys_user", "userid", userid);
            if (null == userId) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "未找到成员在sys_user表中的对应主键");
                excel.add(map);
                logger.error("未找到成员在sys_user表中的对应主键，此结果将被记录在Excel中");
                continue;
            } else {
                cmsContent.setAuthorId(userId);
                Integer sysflag = JdbcHelper.getJdbcTemplate().queryForObject(sql, Integer.class, userid);
                switch (sysflag) {
                    case 0:
                        cmsContent.setAuthorType((short) 1);
                        break;
                    case 1:
                        cmsContent.setAuthorType((short) 2);
                        break;
                    default:
                        map.put(SQLParameters.EXCEL_EX_HEADER, "小组成员对应的sysflag无法识别");
                        excel.add(map);
                        logger.error("小组成员对应的sysflag无法识别，此结果将被记录在Excel中");
                        continue;
                }
            }
            Integer clicknum = (Integer) map.get("clicknum");
            cmsContent.setClicks(clicknum.longValue());
            Integer isallowpublish = (Integer) map.get("isallowpublish");
            cmsContent.setIsPublished(isallowpublish == 1);
            Integer isaudit = (Integer) map.get("isaudit");
            if (null == isaudit || 1 == isaudit) {
                cmsContent.setAuthStatus((short) 2);
            } else {
                cmsContent.setAuthStatus((short) 0);
            }
            /* 获取审核者主键 */
            String audituser = (String) map.get("audituser");
            if (StringUtil.notEmpty(audituser)) {
                Long authUserId = JdbcHelper.getPrimaryKey("sys_user", "userid", audituser);
                if (null == authUserId) {
                    map.put(SQLParameters.EXCEL_EX_HEADER, "未找到成员在sys_user表中的对应主键");
                    excel.add(map);
                    logger.error("未找到成员在sys_user表中的对应主键，此结果将被记录在Excel中");
                    continue;
                } else {
                    cmsContent.setAuthUserId(authUserId);
                }
                /* 获取审核时间 */
                String auditdate = (String) map.get("auditdate");
                cmsContent.setAuthDate(auditdate);
            }
            /* 创建时间以最后更新时间为准（原来的createtime大部分为空） */
            String lastupdatetime = map.get("lastupdatetime").toString();
            cmsContent.setGmtCreate(Timestamp.valueOf(lastupdatetime));
            //String materid = (String) map.get("materid");
            /* 以下处理文章内容 */
            cmsContent.setMid("Pending");//先保存实体类，调用MongoDB方法后再更新
            cmsContent = cmsContentService.addCmsContent(cmsContent);
            pk = cmsContent.getId();
            if (ct.contains("src=")) {
                List<String> srcs = JdbcHelper.getImgSrc(ct);
                for (String src : srcs) {
                    try {
                        String mongoId = fileService.migrateFile(src.replace("/pmph_imesp", ""), FileType.CMS_IMG, pk);
                        ct = ct.replace(src, "/image/" + mongoId);
                    } catch (IOException ex) {
                        logger.warn("无法根据文章内容中的图片路径找到指定文件{}", ex.getMessage());
                    }
                }
            }
            Content content = new Content(ct);
            content = contentService.add(content);
            cmsContent.setMid(content.getId());
            cmsContentService.updateCmsContent(cmsContent);//更新文章
            Double artid = (Double) map.get("artid");
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "artid", artid);//更新旧表中new_pk字段
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, tableName, tableName);
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
    }
}
