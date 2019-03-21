/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.service.CmsCategoryService;
import com.bc.pmpheep.back.service.CmsContentCategoryService;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.service.MaterialContactService;
import com.bc.pmpheep.back.service.MaterialExtraService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.util.CollectionUtil;
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
import java.util.Date;
import java.util.LinkedHashMap;
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
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

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
    @Resource
    MaterialService materialService;
    @Resource
    MaterialContactService materialContactService;
    @Resource
    MaterialExtraService materialExtraService;
    
   

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
        CmsCategory category3 = new CmsCategory(0L, "0", "医学随笔", false);
        category3.setIsAuthRequired(true);
        category3.setIsAuthorVisible(true);
        category3.setIsClicksVisible(true);
        category3.setIsCommentsAllow(true);
        category3.setIsCommentsVisible(true);
        category3.setIsLikesVisible(true);
        category3.setIsBookmarksVisible(true);
        category3.setId(1L);
        category3 = cmsCategoryService.addCmsCategory(category3);
        long pk = category3.getId();
        JdbcHelper.updateNewPrimaryKey(tableName, pk, "colid", "1005");//更新旧表中new_pk字
        CmsCategory category2 = new CmsCategory(0L, "0", "信息快报", true);
        category2.setIsClicksVisible(true);
        category2.setId(2L);
        category2 = cmsCategoryService.addCmsCategory(category2);
        pk = category2.getId();
        JdbcHelper.updateNewPrimaryKey(tableName, pk, "colid", "1004");//更新旧表中new_pk字
        CmsCategory category1 = new CmsCategory(0L, "0", "公告管理", true);
        category1.setIsMaterialNotice(true);
        category1.setIsClicksVisible(true);
        category1.setId(3L);
        category1 = cmsCategoryService.addCmsCategory(category1);
        pk = category1.getId();
        JdbcHelper.updateNewPrimaryKey(tableName, pk, "colid", "1003");//更新旧表中new_pk字段
        /* 新增帮助管理栏目 */
        CmsCategory category4 = new CmsCategory(0L, "0", "帮助管理", true);
        category4.setId(4L);
        cmsCategoryService.addCmsCategory(category4);
        logger.info("'{}'表迁移完成", tableName);
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
    }

    public void cmsContent() {
        String tableName = "site_article"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0,0,0,0,0,0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
                if (state[0] == 0){
                	reason.append("找不到内容标题。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            cmsContent.setTitle(title);
            String ct = (String) map.get("content");
            if (StringUtil.isEmpty(ct)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "文章内容为空");
                excel.add(map);
                logger.error("文章内容为空，本条数据无效，将记录在Excel中");
                if (state[1] == 0){
                	reason.append("找不到文章的内容。");
                	dealWith.append("放弃迁移。");
                	state[1] = 1;
                }
                continue;
            }
            BigDecimal colid = (BigDecimal) map.get("colid");
            Long pk = JdbcHelper.getPrimaryKey("site_column", "colid", colid);
            if (null == pk) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "获取site_column表new_pk字段失败，colid=" + colid);
                excel.add(map);
                logger.error("获取site_column表new_pk字段失败，此结果将被记录在Excel中");
                if (state[2] == 0){
                	reason.append("找不到对应的分类栏目。");
                	dealWith.append("放弃迁移。");
                	state[2] = 1;
                }
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
                if (state[3] == 0){
                	reason.append("找不到对应的作者信息。");
                	dealWith.append("放弃迁移。");
                	state[3] = 1;
                }
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
                        if (state[4] == 0){
                        	reason.append("找不到作者对应的人员类型。");
                        	dealWith.append("放弃迁移。");
                        	state[4] = 1;
                        }
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
                    if (state[5] == 0){
                    	reason.append("找不到对应的审核者的信息。");
                    	dealWith.append("放弃迁移。");
                    	state[5] = 1;
                    }
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
                        ct = ct.replace(src, "/pmpheep/image/" + mongoId);
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
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "CMS内容表", "cms_content");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "cms_content");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "CMS内容表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        if (SQLParameters.STATISTICS_RESULT.size() > 0){
        	try{
        		excelHelper.exportFromResultMaps(SQLParameters.STATISTICS_RESULT, "总体统计结果", null);
        	} catch(IOException ex){
        		logger.error("异常数据导出到Excel失败", ex);
        	}
        }
    }

    public void materialNotice() {
        List<Material> materials = materialService.getListMaterial("轮");
        List<CmsCategory> categorys = cmsCategoryService.getCmsCategoryListByCategoryName("公告");
        Long categoryId = categorys.get(0).getId();
        final String html = "<p><strong><span style=\"box-sizing: border-box; margin: 0px; padding: 0px;\">$f</span></strong>$d</p>";
        final String htmlS1 = "<p><strong><span style=\"box-sizing: border-box; margin: 0px; padding: 0px;\">$d</span></strong></p>";
        final String htmlS2 = "<p style=\"box-sizing: border-box; margin-top: 0px; margin-bottom: 10px; padding: 0px;\">$d</p>";
        for (Material material : materials) {
            CmsContent cmsContent = new CmsContent();
            cmsContent.setParentId(0L);
            cmsContent.setCategoryId(categoryId);
            cmsContent.setPath("0");
            cmsContent.setTitle(material.getMaterialName());
            cmsContent.setAuthorType((short) 0);
            cmsContent.setMaterialId(material.getId());
            /* 生成通知内容 */
            StringBuilder sb = new StringBuilder();
            String str = html.replace("$f", "截止日期：");
            str = str.replace("$d", sdf.format(material.getDeadline()));
            sb.append(str);
            /* 获取教材联系人 */
            List<MaterialContact> contacts = materialContactService.listMaterialContactByMaterialId(categoryId);
            if (CollectionUtil.isNotEmpty(contacts)) {
                str = htmlS1.replace("$f", "联系人：");
                sb.append(str);
                for (MaterialContact contact : contacts) {
                    /* 裴中惠&nbsp;(电话：010-59787110&nbsp;,&nbsp;Email：pzh@pmph.com) */
                    StringBuilder builder = new StringBuilder(contact.getContactUserName());
                    builder.append("&nbsp;(电话：");
                    builder.append(contact.getContactPhone());
                    builder.append("&nbsp;,&nbsp;Email：");
                    builder.append(contact.getContactEmail());
                    builder.append(")");
                    str = htmlS2.replace("$d", builder.toString());
                    sb.append(str);
                }
            }
            str = html.replace("$f", "邮寄地址：");
            str = str.replace("$d", material.getMailAddress());
            sb.append(str);
            /* 获取通知内容和备注 */
            MaterialExtra extra = materialExtraService.getMaterialExtraByMaterialId(material.getId());
            str = htmlS1.replace("$d", "简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介：");
            sb.append(str);
            if (null != extra) {
                //str = htmlS2.replace("$d", extra.getNotice()) /* 存入MongoDB */
            }
            Content content = new Content(sb.toString());
            content = contentService.add(content);
            cmsContent.setMid(content.getId());
        }
    }

}
