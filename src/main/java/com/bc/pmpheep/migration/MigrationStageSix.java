package com.bc.pmpheep.migration;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecExtension;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.service.DecAcadeService;
import com.bc.pmpheep.back.service.DecAchievementService;
import com.bc.pmpheep.back.service.DecCourseConstructionService;
import com.bc.pmpheep.back.service.DecEduExpService;
import com.bc.pmpheep.back.service.DecExtensionService;
import com.bc.pmpheep.back.service.DecLastPositionService;
import com.bc.pmpheep.back.service.DecNationalPlanService;
import com.bc.pmpheep.back.service.DecPositionService;
import com.bc.pmpheep.back.service.DecResearchService;
import com.bc.pmpheep.back.service.DecTeachExpService;
import com.bc.pmpheep.back.service.DecTextbookService;
import com.bc.pmpheep.back.service.DecWorkExpService;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;
import java.text.ParseException;

/**
 * 作家申报与遴选迁移工具类
 * <p>
 * Description:作家申报与遴选模块数据迁移类，此为所有迁移工具的第六步<p>
 * @author tyc
 *
 */
@Component
public class MigrationStageSix {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageSix.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    Date split;
    Date blank;

    @Resource
    DeclarationService declarationService;
    @Resource
    DecPositionService decPositionService;
    @Resource
    DecCourseConstructionService decCourseConstructionService;
    @Resource
    DecEduExpService decEduExpService;
    @Resource
    DecWorkExpService decWorkExpService;
    @Resource
    DecNationalPlanService decNationalPlanService;
    @Resource
    DecTeachExpService decTeachExpService;
    @Resource
    DecTextbookService decTextbookService;
    @Resource
    DecAcadeService decAcadeService;
    @Resource
    DecLastPositionService decLastPositionService;
    @Resource
    DecResearchService decResearchService;
    @Resource
    DecAchievementService decAchievementService;
    @Resource
    DecExtensionService decExtensionService;
    @Resource
    FileService fileService;
    @Resource
    ExcelHelper excelHelper;

    public void start() {
        try {
            split = sdf.parse("2017-07-29 15:25:03");
            blank = sdf.parse("0000-00-00 00:00:00");
        } catch (ParseException ex) {
            logger.error("日期转换错误，错误信息：{}", ex.getMessage());
            return;
        }
        Date begin = new Date();
        declaration();
        decEduExp();
        decWorkExp();
        decTeachExp();
        decAcade();
        decLastPosition();
        decCourseConstruction();
        decNationalPlan();
        decTextbook();
        decResearch();
        decAchievement();
        decExtension();
        decPosition();
        logger.info("迁移第六步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
    }

    /**
     * 作家申报表
     */
    protected void declaration() {
        String tableName = "writer_declaration";// 要迁移的旧库表名
        JdbcHelper.addColumn(tableName); // 增加new_pk字段
        String sql = "select wd.writerid,wd.materid,wd.writername,wd.sex,wd.birthdate,wd.seniority,"
                + "wd.duties,wd.positional,wd.address,wd.postcode,wd.handset,wd.email,wd.idcardtype,"
                + "IFNULL(wd.idcardtype,0) idcardtype,"
                + "wd.idcard,wd.linktel,wd.fax,tm.new_pk tm_materid,s.new_pk sys_userid,"
                + "bo.new_pk org_id,wd.workunit,"
                + "case when wd.submittype=10 then 0 "
                + "when wd.submittype=11 and ta.auditstate=10 then 1 "
                + "when ta.auditstate=12 and wd.submittype=11 then 2 "
                + "when ta.auditstate=11 and wd.submittype=11 then 3 "
                + "when wd.submittype=11 then 1 "
                + "when wd.submittype=12 then 2 "
                + "end online_progress,wd.submittype,ta.auditstate,"
                + "case when ta.auditid is null and ta.editauditid is null then null "
                + "when ta.auditid is not null and ta.editauditid is not null then ta.auditid "
                + "when ta.auditid is null and ta.editauditid is not null then ta.editauditid "
                + "when ta.auditid is not null and ta.editauditid is null then ta.auditid "
                + "end auth_user_id,ta.auditid,ta.editauditid,ta.auditdate,"
                + "case when ta.isreceivedpaper=0 or ta.editauditstate=10 then 0 "
                + "when ta.editauditstate=12 then 1 "
                + "when ta.isreceivedpaper=1 or ta.editauditstate=11 then 2 "
                + "when ta.isreceivedpaper is null or ta.editauditstate is null then 0 "
                + "when ta.isreceivedpaper=0 or ta.editauditstate=10 then 0 "
                + "end offline_progress,ta.isreceivedpaper,ta.editauditstate,"
                + "case when wd.submittype=10 then 1 "
                + "else 0 end is_staging,wd.submittype,ta.editauditdate,wd.userid,s.sysflag "
                + "from writer_declaration wd "
                + "left join teach_material tm on tm.materid=wd.materid "
                + "left join ba_organize bo on bo.orgid=wd.unitid "
                + "left join sys_user s on s.userid=wd.userid "
                + "left join sys_userext su on su.userid=wd.userid "
                + "left join teach_applyposition ta on ta.writerid=wd.writerid "
                + "where su.userid is not null "
                + "group by wd.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql); // 查询所有数据
        int count = 0; // 迁移成功的条目数
        int materialidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("writerid"); // 旧表主键值
            Long materialid = (Long) map.get("tm_materid"); // 教材id
            Long userid = (Long) map.get("sys_userid"); // 作家id
            String realName = (String) map.get("writername"); // 作家姓名
            String sexJudge = (String) map.get("sex"); // 性别
            String experienceNum = (String) map.get("seniority"); // 教龄
            String postCode = (String) map.get("postcode"); // 邮编
            Long onlineProgressJudge = (Long) map.get("online_progress"); // 审核进度
            String authUserid = (String) map.get("auth_user_id"); // 审核人id
            Long offlineProgressJudge = (Long) map.get("offline_progress"); // 纸质表进度
            Long isStagingJudge = (Long) map.get("is_staging"); // 是否暂存
            Declaration declaration = new Declaration();
            if (ObjectUtil.isNull(materialid) || materialid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到教材对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到教材对应的关联结果，此结果将被记录在Excel中");
                materialidCount++;
                continue;
            }
            declaration.setMaterialId(materialid);
            if (ObjectUtil.isNull(userid) || userid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到作家对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到作家对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            declaration.setUserId(userid);
            declaration.setRealname(realName);
            if (StringUtil.isEmpty(sexJudge)) {
                declaration.setSex(1);
            } else {
                Integer sex = Integer.parseInt(sexJudge.trim()); // 性别
                declaration.setSex(sex);
            }
            declaration.setBirthday((Date) map.get("birthdate")); // 生日
            if (JdbcHelper.judgeExperience(experienceNum)) {
                experienceNum = JdbcHelper.correctExperience(experienceNum);
            }
            declaration.setExperience(Integer.parseInt(experienceNum));
            declaration.setOrgName((String) map.get("workunit")); // 工作单位
            declaration.setPosition((String) map.get("duties")); // 职务
            declaration.setTitle((String) map.get("positional")); // 职称
            declaration.setAddress((String) map.get("address")); // 联系地址
            if (StringUtil.strLength(postCode) > 20) {
                declaration.setPostcode("100000");
            }
            //declaration.setPostcode(postCode); // 邮编
            declaration.setHandphone((String) map.get("handset")); // 手机
            declaration.setEmail((String) map.get("email")); // 邮箱
            declaration.setIdtype((Short) map.get("idcardtype1")); // 证件类型
            declaration.setIdcard((String) map.get("idcard")); // 证件号码
            declaration.setTelephone((String) map.get("linktel")); // 联系电话
            declaration.setFax((String) map.get("fax")); // 传真
            declaration.setOrgId((Long) map.get("org_id")); // 申报单位id
            if (ObjectUtil.notNull(onlineProgressJudge)) {
                Integer onlineProgress = onlineProgressJudge.intValue(); // 审核进度
                declaration.setOnlineProgress(onlineProgress);
            } else {
                declaration.setOnlineProgress(0);
            }
            Long authUserId = JdbcHelper.getPrimaryKey("sys_user", "userid", authUserid);
            declaration.setAuthUserId(authUserId);
            declaration.setAuthDate((Timestamp) map.get("auditdate")); // 审核通过时间
            if (ObjectUtil.notNull(offlineProgressJudge)) {
                Integer offlineProgress = offlineProgressJudge.intValue(); // 纸质表进度
                declaration.setOfflineProgress(offlineProgress);
            } else {
                declaration.setOfflineProgress(0);
            }
            declaration.setPaperDate((Timestamp) map.get("editauditdate")); // 纸质表收到时间
            /*String submitType = (String) map.get("submittype"); // 旧表字段：是否暂存
            if (StringUtil.strLength(submitType) > 2) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("是否暂存的位数大于2位数。"));
                excel.add(map);
                logger.error("是否暂存的位数大于2位数，此结果将被记录在Excel中");
                continue;
            }*/
            if (ObjectUtil.isNull(isStagingJudge)) {
                declaration.setIsStaging(0);
            } else {
                Integer isStaging = isStagingJudge.intValue(); // 是否暂存
                declaration.setIsStaging(isStaging);
            }
            declaration = declarationService.addDeclaration(declaration);
            long pk = declaration.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "writerid", id); // 更新旧表中new_pk字段
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家申报表", "declaration");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到教材对应的关联结果数量：{}", materialidCount);
        logger.info("writer_declaration表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家学习经历表
     */
    protected void decEduExp() {
        String tableName = "writer_learn"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select wl.leamid,wl.writerid,wl.schoolname,wl.speciality,"
                + "wl.record,wl.remark,wl.startstopdate,wl.enddate,wl.createdate,"
                + "wd.new_pk id "
                + "from writer_learn wl "
                + "left join writer_declaration wd on wd.writerid=wl.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql); //取得该表中所有数据
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("leamid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String schoolName = (String) map.get("schoolname"); // 学校名称
            String major = (String) map.get("speciality"); // 所学专业
            String degree = (String) map.get("record");  // 学历
            DecEduExp decEduExp = new DecEduExp();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decEduExp.setDeclarationId(declarationid);
            decEduExp.setSchoolName(schoolName);
            decEduExp.setMajor(major);
            decEduExp.setDegree(degree);
            decEduExp.setNote((String) map.get("remark")); // 备注
            Timestamp startstopDate = (Timestamp) map.get("startstopdate"); // 起始时间
            if (null != startstopDate) {
                decEduExp.setDateBegin(sdf.format(startstopDate));
            } else {
                decEduExp.setDateBegin("未知");
            }
            Timestamp endDate = (Timestamp) map.get("enddate"); // 终止时间
            if (null != endDate) {
                if (endDate.getTime() >= split.getTime() || endDate.getTime() == blank.getTime()) {
                    decEduExp.setDateEnd("至今");
                } else {
                    decEduExp.setDateEnd(sdf.format(endDate));
                }
            } else {
                decEduExp.setDateEnd("至今");
            }
            decEduExp.setSort(999); // 显示顺序
            decEduExp = decEduExpService.addDecEduExp(decEduExp);
            long pk = decEduExp.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "leamid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家学习经历表", "dec_edu_exp");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_learn表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家工作经历表
     */
    protected void decWorkExp() {
        String tableName = "writer_work"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select *,wd.new_pk id from writer_work w "
                + "left join writer_declaration wd on wd.writerid=w.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("workid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String orgName = (String) map.get("workunitname"); // 工作单位
            String position = (String) map.get("position"); // 职位
            DecWorkExp decWorkExp = new DecWorkExp();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decWorkExp.setDeclarationId(declarationid);
            decWorkExp.setOrgName(orgName);
            decWorkExp.setPosition(position);
            decWorkExp.setNote((String) map.get("remark")); // 备注
            SimpleDateFormat dateChange = new SimpleDateFormat("yyyy-MM"); //时间转换
            Timestamp startstopDate = (Timestamp) map.get("startstopdate"); // 起始时间
            if (null != startstopDate) {
                decWorkExp.setDateBegin(sdf.format(startstopDate));
            } else {
                decWorkExp.setDateBegin("未知");
            }
            Timestamp endDate = (Timestamp) map.get("enddate"); // 终止时间
            if (null != endDate) {
                if (endDate.getTime() >= split.getTime() || endDate.getTime() == blank.getTime()) {
                    decWorkExp.setDateEnd("至今");
                } else {
                    decWorkExp.setDateEnd(sdf.format(endDate));
                }
            } else {
                decWorkExp.setDateEnd("至今");
            }
            decWorkExp.setSort(999); // 显示顺序
            decWorkExp = decWorkExpService.addDecWorkExp(decWorkExp);
            long pk = decWorkExp.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "workid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家工作经历表", "dec_work_exp");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_work表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家教学经历表
     */
    protected void decTeachExp() {
        String tableName = "writer_teach"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select *,wd.new_pk id from writer_teach w "
                + "left join writer_declaration wd on wd.writerid=w.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("teachid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String schoolName = (String) map.get("schoolname"); // 学校名称
            String subject = (String) map.get("subjects"); // 教学科目
            DecTeachExp decTeachExp = new DecTeachExp();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decTeachExp.setDeclarationId(declarationid);
            decTeachExp.setSchoolName(schoolName);
            decTeachExp.setSubject(subject);
            decTeachExp.setNote((String) map.get("remark")); // 备注
            Timestamp startstopDate = (Timestamp) map.get("startstopdate"); // 起始时间
            if (null != startstopDate) {
                decTeachExp.setDateBegin(sdf.format(startstopDate));
            } else {
                decTeachExp.setDateBegin("未知");
            }
            Timestamp endDate = (Timestamp) map.get("enddate"); // 终止时间
            if (null != endDate) {
                if (endDate.getTime() >= split.getTime() || endDate.getTime() == blank.getTime()) {
                    decTeachExp.setDateEnd("至今");
                } else {
                    decTeachExp.setDateEnd(sdf.format(endDate));
                }
            } else {
                decTeachExp.setDateEnd("至今");
            }
            decTeachExp.setSort(999); // 显示顺序
            decTeachExp = decTeachExpService.addDecTeachExp(decTeachExp);
            long pk = decTeachExp.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "teachid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家教学经历表", "dec_teach_exp");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_teach表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家兼职学术表
     */
    protected void decAcade() {
        String tableName = "writer_acade"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select *,wd.new_pk id from writer_acade wa "
                + "left join writer_declaration wd on wd.writerid=wa.writerid";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("acadeid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String rankJudge = (String) map.get("level"); // 级别
            String position = (String) map.get("duties"); // 职务
            String orgName = (String) map.get("organization"); // 兼职学术组织
            DecAcade decAcade = new DecAcade();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decAcade.setDeclarationId(declarationid);
            if ("nu".equals(rankJudge)) {
                decAcade.setRank(null);
            } else {
                if (StringUtil.isEmpty(rankJudge)) {
                    decAcade.setRank(null);
                } else {
                    Integer rank = Integer.parseInt(rankJudge);
                    decAcade.setRank(rank);
                }
            }
            decAcade.setOrgName(orgName);
            decAcade.setPosition(position);
            decAcade.setNote((String) map.get("remark")); // 备注
            decAcade.setSort(999);
            decAcade = decAcadeService.addDecAcade(decAcade);
            long pk = decAcade.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "acadeid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家兼职学术表", "dec_acade");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_acade表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家上套教材参编情况表
     */
    protected void decLastPosition() {
        String tableName = "writer_materpat"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select wm.materpatid,wm.writerid,wm.matername,"
                + "case when wm.duties like '%1%' then 1 "
                + "when wm.duties like '%2%' then 2 "
                + "when wm.duties like '%3%' then 3 "
                + "else 0 end position,wm.remark,wd.new_pk id "
                + "from writer_materpat wm "
                + "left join writer_declaration wd on wd.writerid=wm.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("materpatid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String materialName = (String) map.get("matername"); // 教材名称
            Long positionJudge = (Long) map.get("position"); // 编写职务
            DecLastPosition decLastPosition = new DecLastPosition();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decLastPosition.setDeclarationId(declarationid);
            decLastPosition.setMaterialName(materialName);
            Integer position = positionJudge.intValue();
            decLastPosition.setPosition(position);
            decLastPosition.setNote((String) map.get("remark")); // 备注
            decLastPosition.setSort(999); // 显示顺序
            decLastPosition = decLastPositionService.addDecLastPosition(decLastPosition);
            long pk = decLastPosition.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "materpatid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家上套教材参编情况表", "dec_last_position");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_materpat表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家精品课程建设情况表
     */
    protected void decCourseConstruction() {
        String tableName = "writer_construction "; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select *,wd.new_pk id from writer_construction wc "
                + "left join writer_declaration wd on wd.writerid=wc.writerid";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("constructionid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String courseName = (String) map.get("curriculumname"); // 课程名称
            String classHour = (String) map.get("classhour"); // 课程全年课时数
            String typeJudge = (String) map.get("type"); // 职务
            DecCourseConstruction decCourseConstruction = new DecCourseConstruction();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decCourseConstruction.setDeclarationId(declarationid);
            decCourseConstruction.setCourseName(courseName);
            decCourseConstruction.setClassHour(classHour);
            Integer type = Integer.parseInt(typeJudge);
            decCourseConstruction.setType(type);
            decCourseConstruction.setNote((String) map.get("remark")); // 备注
            decCourseConstruction.setSort(999); // 显示顺序
            decCourseConstruction = decCourseConstructionService.addDecCourseConstruction(decCourseConstruction);
            long pk = decCourseConstruction.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "constructionid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家精品课程建设情况表", "dec_course_construction");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_construction表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家主编国家级规划教材情况表
     */
    protected void decNationalPlan() {
        String tableName = "writer_editorbook "; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select wa.editorbookid,wa.writerid,wa.matername,wa.booknumber,"
                + "case when materlevel like '%1%,%2%' then 3 "
                + "when materlevel like '%1%' then 1 "
                + "when materlevel like '%2%' then 2 "
                + "else 2 end rank,wa.remark,wd.new_pk id "
                + "from writer_editorbook wa "
                + "left join writer_declaration wd on wd.writerid=wa.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("editorbookid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String materialName = (String) map.get("matername"); // 教材名称
            String isbn = (String) map.get("booknumber"); // 标准书号
            Long rankJudge = (Long) map.get("rank"); // 教材级别
            DecNationalPlan decNationalPlan = new DecNationalPlan();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decNationalPlan.setDeclarationId(declarationid);
            decNationalPlan.setMaterialName(materialName);
            if (StringUtil.notEmpty(isbn)) {
                isbn = isbn.trim();
                isbn = isbn.replace("ISBN", "").replace("isbn", "").replace(":", "").replace("：", "");
            }
            if (("无").equals(isbn)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到标准书号为无字。"));
                excel.add(map);
                logger.error("找到标准书号为无字，此结果将被记录在Excel中");
            }
            decNationalPlan.setIsbn(isbn);
            Integer rank = rankJudge.intValue();
            decNationalPlan.setRank(rank);
            decNationalPlan.setNote((String) map.get("remark")); // 备注
            decNationalPlan.setSort(999); // 显示顺序
            decNationalPlan = decNationalPlanService.addDecNationalPlan(decNationalPlan);
            long pk = decNationalPlan.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "editorbookid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家主编国家级规划教材情况表", "dec_national_plan");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_editorbook表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家教材编写情况表
     */
    protected void decTextbook() {
        String tableName = "writer_materwrite "; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select wm.materwriteid,wm.writerid,wm.matername,wm.publishing,"
                + "case when level like '%1%' then 1 when level like '%2%' then 2 "
                + "when level like '%3%,%4%' then 4 "
                + "when level like '%3%' then 5 when level like '%4%' then 4 "
                + "else 5 end rank,"
                + "case when wm.duty like '%1%' then 1 when wm.duty like '%2%' then 2 "
                + "else 3 end position,wm.booknumber,wm.remark,wm.publisdate,wd.new_pk id "
                + "from writer_materwrite wm "
                + "left join writer_declaration wd on wd.writerid=wm.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("materwriteid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String materialName = (String) map.get("matername"); // 教材名称
            Long rankJudge = (Long) map.get("rank"); // 教材级别
            Long positionJudge = (Long) map.get("position"); // 编写职务
            String publisher = (String) map.get("publishing"); // 出版社
            Date publishDate = (Date) map.get("publisdate"); // 出版时间
            String isbn = (String) map.get("booknumber"); // 标准书号
            DecTextbook decTextbook = new DecTextbook();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decTextbook.setDeclarationId(declarationid);
            decTextbook.setMaterialName(materialName);
            Integer rank = rankJudge.intValue();
            decTextbook.setRank(rank);
            Integer position = positionJudge.intValue();
            decTextbook.setPosition(position);
            decTextbook.setPublisher(publisher);
            decTextbook.setPublishDate(publishDate);
            if (StringUtil.notEmpty(isbn)) {
                isbn = isbn.trim();
                isbn = isbn.replace("ISBN", "").replace("isbn", "").replace(":", "").replace("：", "");
            }
            if (("无").equals(isbn)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到标准书号为无字。"));
                excel.add(map);
                logger.error("找到标准书号为无字，此结果将被记录在Excel中");
            }
            decTextbook.setIsbn(isbn);
            decTextbook.setNote((String) map.get("remark")); // 备注
            decTextbook.setSort(999); // 显示顺序
            decTextbook = decTextbookService.addDecTextbook(decTextbook);
            long pk = decTextbook.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "materwriteid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家教材编写情况表", "dec_textbook");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_materwrite表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家科研情况表
     */
    protected void decResearch() {
        String tableName = "writer_scientresearch "; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select *,wd.new_pk id from writer_scientresearch ws "
                + "left join writer_declaration wd on wd.writerid=ws.writerid";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("scientresearchid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String researchName = (String) map.get("topicname"); // 课题名称
            String approvalUnit = (String) map.get("approvaluntiname"); // 审批单位
            String award = (String) map.get("award"); // 获奖情况
            DecResearch decResearch = new DecResearch();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                continue;
            }
            decResearch.setDeclarationId(declarationid);
            decResearch.setResearchName(researchName);
            if (("无").equals(approvalUnit)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到审批单位为无字。"));
                excel.add(map);
                logger.error("找到审批单位为无字，此结果将被记录在Excel中");
            }
            decResearch.setApprovalUnit(approvalUnit);
            decResearch.setAward(award);
            decResearch.setNote((String) map.get("remark")); // 备注
            decResearch.setSort(999); // 显示顺序
            decResearch = decResearchService.addDecResearch(decResearch);
            long pk = decResearch.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "scientresearchid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家科研情况表", "dec_research");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_scientresearch表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家个人成就表
     */
    protected void decAchievement() {
        String tableName = "teach_material_extvalue"; // 要迁移的旧库表名
        JdbcHelper.addColumn(tableName); // 增加new_pk字段
        String sql = "select *,wd.new_pk wdid from teach_material_extvalue wme "
                + "left join writer_declaration wd on wd.writerid=wme.writerid "
                + "left join teach_material_extend tme on tme.expendid=wme.expendid "
                + "where tme.expendname = '个人成就'";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0; // 迁移成功的条目数
        List<Map<String, Object>> excel = new LinkedList<>();
        String regular = "^[0-9a-zA-Z]{8,10}$"; // 正则表达式判断
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            Double id = (Double) map.get("extvalueid"); // 旧表主键值
            Long declarationid = (Long) map.get("wdid"); // 申报表id
            String content = (String) map.get("content"); // 个人成就内容
            DecAchievement decAchievement = new DecAchievement();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decAchievement.setDeclarationId(declarationid);
            if (regular.equals(content)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到个人成就内容为数字或者字母。"));
                excel.add(map);
                logger.error("找到个人成就内容为数字或者字母，此结果将被记录在Excel中");
            }
            String contents = content.trim();
            decAchievement.setContent(contents);
            decAchievement = decAchievementService.addDecAchievement(decAchievement);
            long pk = decAchievement.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "extvalueid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家个人成就表", "dec_achievement");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("teach_material_extvalue表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家扩展项填报表
     */
    protected void decExtension() {
        String tableName = "teach_material_extvalue"; // 要迁移的旧库表名
        JdbcHelper.addColumn(tableName); // 增加new_pk字段
        String sql = "select *,wd.new_pk wdid,tme.new_pk tmeid from teach_material_extvalue wme "
                + "left join writer_declaration wd on wd.writerid=wme.writerid "
                + "left join teach_material_extend tme on tme.expendid=wme.expendid "
                + "where tme.expendname != '个人成就'";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0; // 迁移成功的条目数
        int extensionidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        String regular = "^[0-9a-zA-Z]{8,10}$"; // 正则表达式判断
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            Double id = (Double) map.get("extvalueid"); // 旧表主键值
            Long extensionid = (Long) map.get("tmeid"); // 教材扩展项id
            Long declarationid = (Long) map.get("wdid"); // 申报表id
            String content = (String) map.get("content"); // 扩展项内容
            DecExtension decExtension = new DecExtension();
            if (ObjectUtil.isNull(extensionid) || extensionid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到教材扩展项对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到教材扩展项对应的关联结果，此结果将被记录在Excel中");
                extensionidCount++;
                continue;
            }
            decExtension.setExtensionId(extensionid);
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decExtension.setDeclarationId(declarationid);
            if (regular.equals(content)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到扩展项内容为数字或者字母。"));
                excel.add(map);
                logger.error("找到扩展项内容为数字或者字母，此结果将被记录在Excel中");
            }
            String contents = content.trim();
            decExtension.setContent(contents);
            decExtension = decExtensionService.addDecExtension(decExtension);
            long pk = decExtension.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "extvalueid", id);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家扩展项填报表", "dec_extension");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到教材扩展项对应的关联结果数量：{}", extensionidCount);
        logger.info("teach_material_extvalue表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 作家申报职位表（多对多）
     */
    protected void decPosition() {
        String tableName = "teach_applyposition"; // 要迁移的旧库表名
        JdbcHelper.addColumn(tableName); // 增加new_pk字段
        String sql = "select ta.materid,ta.writerid,ta.bookid,"
                + "GROUP_CONCAT(case when ta.positiontype=1 then 'a' when ta.positiontype=2 "
                + "then 'b' when ta.positiontype=3 then 'c' else 'c' "
                + "end ORDER BY ta.positiontype) preset_position,"
                + "if(sum(if(tp.positiontype in (1,2,3),1,0))>0,true,false) is_on_list,"
                + "GROUP_CONCAT(case when tp.positiontype=1 then 'a' when tp.positiontype=2 "
                + "then 'b' when tp.positiontype=3 then 'c' else 'd' "
                + "end ORDER BY tp.positiontype) chosen_position, "
                + "min(tp.mastersort) mastersort,ta.outlineurl,ta.outlinename,"
                + "ifnull(wd.updatedate,wd.createdate) gmt_create,"
                + "wd.new_pk wdid,tb.new_pk tbid "
                + "from teach_applyposition ta "
                + "left join teach_positionset tp on tp.appposiid=ta.appposiid "
                + "left join writer_declaration wd on wd.writerid=ta.writerid "
                + "left join teach_bookinfo tb on tb.bookid=ta.bookid  "
                + "WHERE ta.positiontype in (1,2,3) and wd.writerid is not null and tb.bookid is not null  "
                + "GROUP BY wd.writerid,tb.bookid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0; // 迁移成功的条目数
        int textbookidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("materid"); // 旧表主键值
            DecPosition decPosition = new DecPosition();
            Long declarationid = (Long) map.get("wdid"); // 申报表id
            Long textbookid = (Long) map.get("tbid"); // 书籍id
            String temppresetPosition = (String) map.get("preset_position"); // 申报职务
            Long isOnList = (Long) map.get("is_on_list"); // 是否进入预选名单
            String tempchosenPosition = (String) map.get("chosen_position"); // 遴选职务
            Integer mastersort = (Integer) map.get("mastersort"); // 排位
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decPosition.setDeclarationId(declarationid);
            if (ObjectUtil.isNull(textbookid) || textbookid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到书籍对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到书籍对应的关联结果，此结果将被记录在Excel中");
                textbookidCount++;
                continue;
            }
            decPosition.setTextbookId(textbookid);
            temppresetPosition += "," + temppresetPosition + ",";
            String Positions = "";
            if (temppresetPosition.contains(",a,")) {
                Positions += "1";
            } else {
                Positions += "0";
            }
            if (temppresetPosition.contains(",b,")) {
                Positions += "1";
            } else {
                Positions += "0";
            }
            if (temppresetPosition.contains(",c,")) {
                Positions += "1";
            } else {
                Positions += "0";
            }
            decPosition.setPresetPosition(Integer.valueOf(Positions, 2));//转成10进制
            if (ObjectUtil.isNull(isOnList)) {
                decPosition.setIsOnList(1);
            }
            Integer isOn = isOnList.intValue();
            decPosition.setIsOnList(isOn);
            tempchosenPosition += "," + tempchosenPosition + ",";
            Integer chosen = 0;
            if (tempchosenPosition.contains(",a,")) {
                chosen = 1;
            } else if (tempchosenPosition.contains(",b,")) {
                chosen = 2;
            } else if (tempchosenPosition.contains(",c,")) {
                chosen = 3;
            }
            decPosition.setChosenPosition(chosen);
            decPosition.setRank(mastersort);
            decPosition.setSyllabusName((String) map.get("syllabus_name")); // 教学大纲名称
            decPosition.setGmtCreate((Timestamp) map.get("gmt_create")); // 创建时间
            String outLineUrl = (String) map.get("outlineurl"); // 教学大纲id
            decPosition = decPositionService.addDecPosition(decPosition);
            long pk = decPosition.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "materid", id);
            count++;
            if (StringUtil.notEmpty(outLineUrl)) {
                /* 以下读取教学大纲id并保存在mongoDB中，读取失败时导出到Excel中 */
                String mongoId = "";
                try {
                    mongoId = fileService.migrateFile(outLineUrl, FileType.SYLLABUS, pk);
                } catch (IOException ex) {
                    logger.error("文件读取异常，路径<{}>，异常信息：{}", outLineUrl, ex.getMessage());
                    map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常");
                    excel.add(map);
                } catch (Exception e) {
                    map.put(SQLParameters.EXCEL_EX_HEADER, "存文件未知异常：" + e.getMessage() + "。");
                    excel.add(map);
                    logger.error("更新mongoDB的id错误，此结果将被记录在Excel中");
                }
                decPosition.setSyllabusId(mongoId);
                decPositionService.updateDecPosition(decPosition);
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家申报职位表", "dec_position");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("未找到书籍对应的关联结果数量：{}", textbookidCount);
        logger.info("teach_applyposition表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }
}
