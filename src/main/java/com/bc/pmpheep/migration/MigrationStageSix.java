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
    DecExtensionService decExtensionService;
    @Resource
    FileService fileService;
    @Resource
    ExcelHelper excelHelper;

    public void start() {
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
                + "bo.new_pk org_id, "
                + "case when wd.submittype=10 then 0 "
                + "when wd.submittype=11 or ta.auditstate=10 then 1 "
                + "when ta.auditstate=12 or wd.submittype is null then 2 "
                + "when ta.auditstate=11 and wd.submittype=11 then 3 "
                + "else 1 end online_progress,wd.submittype,ta.auditstate,"
                + "case when ta.auditid is null and ta.editauditid is null then null "
                + "when ta.auditid is not null and ta.editauditid is not null then ta.auditid "
                + "when ta.auditid is null and ta.editauditid is not null then ta.editauditid "
                + "when ta.auditid is not null and ta.editauditid is null then ta.auditid "
                + "end auth_user_id,ta.auditid,ta.editauditid,ta.auditdate,"
                + "case when ta.isreceivedpaper=0 or ta.editauditstate=10 then 0 "
                + "when ta.editauditstate=12 then 1 "
                + "when ta.isreceivedpaper=1 or ta.editauditstate=11 then 2 "
                + "when ta.isreceivedpaper is null or ta.editauditstate is null then 0 "
                + "end offline_progress,ta.isreceivedpaper,ta.editauditstate,"
                + "case when wd.submittype=10 then 0 "
                + "when wd.submittype=11 then 1 "
                + "end is_staging,wd.submittype,ta.editauditdate,wd.userid,s.sysflag "
                + "from writer_declaration wd "
                + "left join teach_material tm on tm.materid=wd.materid "
                + "left join ba_organize bo on bo.orgid=wd.unitid "
                + "left join sys_user s on s.userid=wd.userid "
                + "left join sys_userext su on su.userid=wd.userid "
                + "left join teach_applyposition ta on ta.writerid=wd.writerid "
                + "where su.userid is not null "
                + "group by wd.writerid ;";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql); // 查询所有数据
        int count = 0; // 迁移成功的条目数
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
                logger.error("未找到教材对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            declaration.setMaterialId(materialid);
            if (ObjectUtil.isNull(userid) || userid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到作家对应的关联结果。"));
                excel.add(map);
                logger.error("未找到作家对应的关联结果，此结果将被记录在Excel中");
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
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("教龄数据不符合新表类型规范。"));
                excel.add(map);
                logger.info("教龄数据不符合新表类型规范，此结果将被记录在Excel中");
            }
            declaration.setExperience(Integer.parseInt(experienceNum));
            declaration.setOrgName((String) map.get("workunit")); // 工作单位
            declaration.setPosition((String) map.get("duties")); // 职务
            declaration.setTitle((String) map.get("positional")); // 职称
            declaration.setAddress((String) map.get("address")); // 联系地址
            if (StringUtil.notEmpty(postCode)) {
                if (StringUtil.strLength(postCode) > 20) {
                    map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("邮编长度过长并不规范。"));
                    excel.add(map);
                    logger.error("邮编长度过长并不规范，此结果将被记录在Excel中");
                    continue;
                }
            }
            declaration.setPostcode(postCode); // 邮编
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
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到审核进度信息。"));
                excel.add(map);
                logger.error("未找到审核进度信息，此结果将被记录在Excel中");
                declaration.setOnlineProgress(0);
            }
            Long authUserId = JdbcHelper.getPrimaryKey("sys_user", "userid", authUserid);
            declaration.setAuthUserId(authUserId);
            declaration.setAuthDate((Timestamp) map.get("auditdate")); // 审核通过时间
            if (ObjectUtil.notNull(offlineProgressJudge)) {
                Integer offlineProgress = offlineProgressJudge.intValue(); // 纸质表进度
                declaration.setOfflineProgress(offlineProgress);
            } else {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到纸质表进度信息。"));
                excel.add(map);
                logger.error("未找到纸质表进度信息，此结果将被记录在Excel中");
                declaration.setOfflineProgress(0);
            }
            declaration.setPaperDate((Timestamp) map.get("editauditdate")); // 纸质表收到时间
            String submitType = (String) map.get("submittype"); // 旧表字段：是否暂存
            if (StringUtil.strLength(submitType) > 2) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("是否暂存的位数大于2位数。"));
                excel.add(map);
                logger.error("是否暂存的位数大于2位数，此结果将被记录在Excel中");
                continue;
            }
            if (ObjectUtil.isNull(isStagingJudge)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到是否暂存信息。"));
                excel.add(map);
                logger.error("未找到是否暂存信息，此结果将被记录在Excel中");
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
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("leamid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            DecEduExp decEduExp = new DecEduExp();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decEduExp.setDeclarationId(declarationid);
            String schoolName = (String) map.get("schoolname"); // 学校名称
            if (("无").equals(schoolName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到学校名称为无。"));
                excel.add(map);
                logger.error("找到学校名称为无，此结果将被记录在Excel中");
            }
            decEduExp.setSchoolName(schoolName);
            String major = (String) map.get("speciality"); // 所学专业
            if (("无").equals(major)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到所学专业为无。"));
                excel.add(map);
                logger.error("找到所学专业为无，此结果将被记录在Excel中");
            }
            decEduExp.setMajor(major);
            String degree = (String) map.get("record");  // 学历
            if (("无").equals(degree)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到学历为无。"));
                excel.add(map);
                logger.error("找到学历为无，此结果将被记录在Excel中");
            }
            decEduExp.setDegree(degree);
            decEduExp.setNote((String) map.get("remark")); // 备注
            SimpleDateFormat dateChange = new SimpleDateFormat("yyyy-MM"); //时间转换
            Timestamp startstopDate = (Timestamp) map.get("startstopdate"); // 起始时间
            if (ObjectUtil.isNull(startstopDate)) {
                logger.error("未找到起始时间");
            } else {
                String dateBegin = dateChange.format(startstopDate);
                decEduExp.setDateBegin(dateBegin);
            }
            Timestamp createDate = (Timestamp) map.get("createdate"); // 获取对比时间
            Timestamp endDate = (Timestamp) map.get("enddate"); // 终止时间
            if (ObjectUtil.isNull(endDate)) {
                logger.error("未找到终止时间");
            } else {
                if (endDate.equals(createDate) || endDate.equals("2017-07-29 15:25:03.0")) {
                    decEduExp.setDateEnd("至今");
                } else {
                    String dateEnd = dateChange.format(endDate);
                    decEduExp.setDateEnd(dateEnd);
                }
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
                + "left join writer_declaration wd on wd.writerid=w.writerid "
                + "where w.enddate != '0000-00-00 00:00:00' or w.enddate is null ";
        // 此处保存maps里的数据有一条未查询，已单独导出
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("workid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            DecWorkExp decWorkExp = new DecWorkExp();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decWorkExp.setDeclarationId(declarationid);
            String orgName = (String) map.get("workunitname"); // 工作单位
            if (("无").equals(orgName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到工作单位为无。"));
                excel.add(map);
                logger.error("找到工作单位为无，此结果将被记录在Excel中");
            }
            decWorkExp.setOrgName(orgName);
            String position = (String) map.get("position"); // 职位
            if (("无").equals(position)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到职位为无。"));
                excel.add(map);
                logger.error("找到职位为无，此结果将被记录在Excel中");
            }
            decWorkExp.setPosition(position);
            decWorkExp.setNote((String) map.get("remark")); // 备注
            SimpleDateFormat dateChange = new SimpleDateFormat("yyyy-MM"); //时间转换
            Timestamp startstopDate = (Timestamp) map.get("startstopdate"); // 起始时间
            if (ObjectUtil.isNull(startstopDate)) {
                logger.error("未找到起始时间");
            } else {
                String dateBegin = dateChange.format(startstopDate);
                decWorkExp.setDateBegin(dateBegin);
            }
            Timestamp createDate = (Timestamp) map.get("createdate"); // 获取对比时间
            Timestamp endDate = (Timestamp) map.get("enddate"); // 终止时间
            if (ObjectUtil.isNull(endDate)) {
                logger.error("未找到终止时间");
                continue;
            } else {
                if (endDate.equals(createDate) || endDate.equals("2017-07-29 15:25:03.0")) {
                    decWorkExp.setDateEnd("至今");
                } else {
                    String dateEnd = dateChange.format(endDate);
                    decWorkExp.setDateEnd(dateEnd);
                }
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
                + "left join writer_declaration wd on wd.writerid=w.writerid "
                + "where w.enddate != '0000-00-00 00:00:00' or w.enddate is null";
        // 此处保存maps里的数据有一条未查询，已单独导出
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("teachid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            DecTeachExp decTeachExp = new DecTeachExp();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decTeachExp.setDeclarationId(declarationid);
            String schoolName = (String) map.get("schoolname"); // 学校名称
            if (schoolName.indexOf("1") != -1) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到学校名称。"));
                excel.add(map);
                logger.error("未找到学校名称，此结果将被记录在Excel中");
            }
            decTeachExp.setSchoolName(schoolName);
            String subject = (String) map.get("subjects"); // 教学科目
            if (StringUtil.isNumeric(subject)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到教学科目为数字。"));
                excel.add(map);
                logger.error("找到教学科目为数字，此结果将被记录在Excel中");
            }
            decTeachExp.setSubject(subject);
            decTeachExp.setNote((String) map.get("remark")); // 备注
            SimpleDateFormat dateChange = new SimpleDateFormat("yyyy-MM"); //时间转换
            Timestamp startstopDate = (Timestamp) map.get("startstopdate"); // 起始时间
            if (ObjectUtil.isNull(startstopDate)) {
                logger.error("未找到起始时间");
            } else {
                String dateBegin = dateChange.format(startstopDate);
                decTeachExp.setDateBegin(dateBegin);
            }
            Timestamp createDate = (Timestamp) map.get("createdate"); // 获取对比时间
            Timestamp endDate = (Timestamp) map.get("enddate"); // 终止时间
            if (ObjectUtil.isNull(endDate)) {
                logger.error("未找到终止时间");
            } else {
                if (endDate.equals(createDate) || endDate.equals("2017-07-29 15:25:03.0")) {
                    decTeachExp.setDateEnd("至今");
                } else {
                    String dateEnd = dateChange.format(endDate);
                    decTeachExp.setDateEnd(dateEnd);
                }
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
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);;//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("acadeid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String rankJudge = (String) map.get("level");
            DecAcade decAcade = new DecAcade();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decAcade.setDeclarationId(declarationid);
            String position = (String) map.get("duties"); // 职务
            if (("无").equals(position) || StringUtil.isNumeric(position)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到职务或职务数据不规范。"));
                excel.add(map);
                logger.error("未找到职务，此结果将被记录在Excel中");
            }
            if ("nu".equals(rankJudge)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到级别数据是nu。"));
                excel.add(map);
                logger.error("找到级别数据是nu，此结果将被记录在Excel中");
            } else {
                if (StringUtil.isEmpty(rankJudge)) {
                    decAcade.setRank(null);
                } else {
                    Integer rank = Integer.parseInt(rankJudge); // 级别
                    decAcade.setRank(rank);
                }
            }
            String orgName = (String) map.get("organization"); // 兼职学术组织
            if (("无").equals(orgName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到兼职学术组织为无。"));
                excel.add(map);
                logger.error("找到兼职学术组织为无，此结果将被记录在Excel中");
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
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("materpatid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            Long positionJudge = (Long) map.get("position");
            DecLastPosition decLastPosition = new DecLastPosition();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decLastPosition.setDeclarationId(declarationid);
            String materialName = (String) map.get("matername"); // 教材名称
            if (("无").equals(materialName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到教材名称为无。"));
                excel.add(map);
                logger.error("找到教材名称为无，此结果将被记录在Excel中");
            }
            decLastPosition.setMaterialName(materialName);
            Integer position = positionJudge.intValue(); // 编写职务
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
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("constructionid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String typeJudge = (String) map.get("type");
            DecCourseConstruction decCourseConstruction = new DecCourseConstruction();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decCourseConstruction.setDeclarationId(declarationid);
            String courseName = (String) map.get("curriculumname"); // 课程名称
            if (("无").equals(courseName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到课程名称为无。"));
                excel.add(map);
                logger.error("找到课程名称为无，此结果将被记录在Excel中");
            }
            decCourseConstruction.setCourseName(courseName);
            String classHour = (String) map.get("classhour"); // 课程全年课时数
            if (("无").equals(classHour)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到课程全年课时数为无。"));
                excel.add(map);
                logger.error("找到课程全年课时数为无，此结果将被记录在Excel中");
            }
            decCourseConstruction.setClassHour(classHour);
            Integer type = Integer.parseInt(typeJudge); // 职务
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
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("editorbookid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            Long rankJudge = (Long) map.get("rank");
            DecNationalPlan decNationalPlan = new DecNationalPlan();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decNationalPlan.setDeclarationId(declarationid);
            String materialName = (String) map.get("matername"); // 教材名称
            if (("无").equals(materialName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到教材名称为无。"));
                excel.add(map);
                logger.error("找到教材名称为无，此结果将被记录在Excel中");
            }
            decNationalPlan.setMaterialName(materialName);
            String isbn = (String) map.get("booknumber"); // 标准书号
            if (StringUtil.notEmpty(isbn)) {
                isbn = isbn.trim();
                isbn = isbn.replace("ISBN", "").replace("isbn", "").replace(":", "").replace("：", "");
            }
            if (("无").equals(isbn)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到标准书号为无。"));
                excel.add(map);
                logger.error("找到标准书号为无，此结果将被记录在Excel中");
            }
            decNationalPlan.setIsbn(isbn);
            Integer rank = rankJudge.intValue(); // 教材级别
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
                + "when level like '%3%' then 3 when level like '%4%' then 4 "
                + "else 5 end rank,"
                + "case when wm.duty like '%1%' then 1 when wm.duty like '%2%' then 2 "
                + "else 3 end position,wm.booknumber,wm.remark,wm.publisdate,wd.new_pk id "
                + "from writer_materwrite wm "
                + "left join writer_declaration wd on wd.writerid=wm.writerid "
                + "where wm.publisdate != '0000-00-00 00:00:00' or wm.publisdate is null ";
        // 此处保存maps里的数据有35条未查询，已单独导出
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("materwriteid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            Long rankJudge = (Long) map.get("rank");
            Long positionJudge = (Long) map.get("position");
            DecTextbook decTextbook = new DecTextbook();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decTextbook.setDeclarationId(declarationid);
            String materialName = (String) map.get("matername"); // 教材名称
            if (("无").equals(materialName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到教材名称为无。"));
                excel.add(map);
                logger.error("找到教材名称为无，此结果将被记录在Excel中");
            }
            decTextbook.setMaterialName(materialName);
            Integer rank = rankJudge.intValue(); // 教材级别
            decTextbook.setRank(rank);
            Integer position = positionJudge.intValue(); // 编写职务
            decTextbook.setPosition(position);
            String publisher = (String) map.get("publishing"); // 出版社
            if (("无").equals(publisher)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到出版社为无。"));
                excel.add(map);
                logger.error("找到出版社为无，此结果将被记录在Excel中");
            }
            decTextbook.setPublisher(publisher);
            Date publishDate = (Date) map.get("publisdate"); // 出版时间
            decTextbook.setPublishDate(publishDate);
            String isbn = (String) map.get("booknumber"); // 标准书号
            if (StringUtil.notEmpty(isbn)) {
                isbn = isbn.trim();
                isbn = isbn.replace("ISBN", "").replace("isbn", "").replace(":", "").replace("：", "");
            }
            if (("无").equals(isbn)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到标准书号为无。"));
                excel.add(map);
                logger.error("找到标准书号为无，此结果将被记录在Excel中");
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
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("scientresearchid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            DecResearch decResearch = new DecResearch();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decResearch.setDeclarationId(declarationid);
            String researchName = (String) map.get("topicname"); // 课题名称
            if (("无").equals(researchName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到课题名称为无。"));
                excel.add(map);
                logger.error("找到课题名称为无，此结果将被记录在Excel中");
            }
            decResearch.setResearchName(researchName);
            String approvalUnit = (String) map.get("approvaluntiname"); // 审批单位
            if (("无").equals(approvalUnit)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到审批单位为无。"));
                excel.add(map);
                logger.error("找到审批单位为无，此结果将被记录在Excel中");
            }
            decResearch.setApprovalUnit(approvalUnit);
            String award = (String) map.get("award"); // 获奖情况
            if (("无").equals(award)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到获奖情况为无。"));
                excel.add(map);
                logger.error("找到获奖情况为无，此结果将被记录在Excel中");
            }
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
        logger.info("writer_scientresearch表迁移完成，异常条目数量：{}", excel.size());
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
                + "left join teach_material_extend tme on tme.expendid=wme.expendid";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0; // 迁移成功的条目数
        List<Map<String, Object>> excel = new LinkedList<>();
        String regular = "^[0-9a-zA-Z]{8,10}$"; // 正则表达式判断
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            Double id = (Double) map.get("extvalueid"); // 旧表主键值
            Long extensionid = (Long) map.get("tmeid"); // 教材扩展项id
            Long declarationid = (Long) map.get("wdid"); // 申报表id
            DecExtension decExtension = new DecExtension();
            if (ObjectUtil.isNull(extensionid) || extensionid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到教材扩展项对应的关联结果。"));
                excel.add(map);
                logger.error("未找到教材扩展项对应的关联结果，此结果将被记录在Excel中");
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
            String content = (String) map.get("content"); // 扩展项内容
            if (("无").equals(content) || regular.equals(content)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到扩展项内容为无。"));
                excel.add(map);
                logger.error("找到扩展项内容为无，此结果将被记录在Excel中");
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
                + "case when ta.positiontype=1 then 1 when ta.positiontype=2 then 2 "
                + "when ta.positiontype=3 then 3 "
                + "else 0 end preset_position,"
                + "if(tp.appposiid is not null,true,false) is_on_list,"
                + "case when tp.positiontype=1 then 1 when tp.positiontype=2 then 2 "
                + "when tp.positiontype=3 then 3 "
                + "else 0 end chosen_position,tp.mastersort,ta.outlineurl,ta.outlinename,"
                + "ifnull(wd.updatedate,wd.createdate) gmt_create,"
                + "wd.new_pk wdid,tb.new_pk tbid "
                + "from teach_applyposition ta "
                + "left join teach_positionset tp on tp.appposiid=ta.appposiid "
                + "left join writer_declaration wd on wd.writerid=ta.writerid "
                + "left join teach_bookinfo tb on tb.bookid=ta.bookid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0; // 迁移成功的条目数
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("materid"); // 旧表主键值
            DecPosition decPosition = new DecPosition();
            Long declarationid = (Long) map.get("wdid"); // 申报表id
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.error("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decPosition.setDeclarationId(declarationid);
            Long textbookid = (Long) map.get("tbid"); // 书籍id
            if (ObjectUtil.isNull(textbookid) || textbookid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到书籍对应的关联结果。"));
                excel.add(map);
                logger.error("未找到书籍对应的关联结果，此结果将被记录在Excel中");
                continue;
            }
            decPosition.setTextbookId(textbookid);
            Long presetPosition = (Long) map.get("preset_position"); // 申报职务
            if (ObjectUtil.isNull(presetPosition)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到申报职务为空值。"));
                excel.add(map);
                logger.error("找到申报职务为空值，此结果将被记录在Excel中");
                continue;
            }
            Integer preset = (Integer) presetPosition.intValue();
            decPosition.setPresetPosition(preset);
            Long isOnList = (Long) map.get("is_on_list"); // 是否进入预选名单
            if (ObjectUtil.isNull(isOnList)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到是否进入预选名单为空值。"));
                excel.add(map);
                logger.error("找到是否进入预选名单为空值，此结果将被记录在Excel中");
                continue;
            }
            Integer isOn = isOnList.intValue();
            decPosition.setIsOnList(isOn);
            Long chosenPosition = (Long) map.get("chosen_position"); // 遴选职务
            if (ObjectUtil.isNull(chosenPosition)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到遴选职务为空值。"));
                excel.add(map);
                logger.error("找到遴选职务为空值，此结果将被记录在Excel中");
                continue;
            }
            Integer chosen = chosenPosition.intValue();
            decPosition.setChosenPosition(chosen);
            Integer mastersort = (Integer) map.get("mastersort"); // 排位
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
        logger.info("teach_applyposition表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }
}
