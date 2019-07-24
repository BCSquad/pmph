package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecExtension;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecTextbookPmph;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.service.DecAcadeService;
import com.bc.pmpheep.back.service.DecCourseConstructionService;
import com.bc.pmpheep.back.service.DecEduExpService;
import com.bc.pmpheep.back.service.DecExtensionService;
import com.bc.pmpheep.back.service.DecLastPositionService;
import com.bc.pmpheep.back.service.DecNationalPlanService;
import com.bc.pmpheep.back.service.DecPositionPublishedService;
import com.bc.pmpheep.back.service.DecPositionService;
import com.bc.pmpheep.back.service.DecResearchService;
import com.bc.pmpheep.back.service.DecTeachExpService;
import com.bc.pmpheep.back.service.DecTextbookPmphService;
import com.bc.pmpheep.back.service.DecTextbookService;
import com.bc.pmpheep.back.service.DecWorkExpService;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
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
    WriterUserService writerUserService;
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
    DecPositionPublishedService decPositionPublishedService;
    @Resource
    DecTextbookPmphService decTextbookPmphService;
    @Resource
    FileService fileService;
    @Resource
    ExcelHelper excelHelper;

    @Autowired
    private MaterialService materialService;

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
        decTextbookOther();
        decResearch();
        decExtension();
        decPosition();
        decPositionPublished();
        logger.info("迁移第六步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
    }

    /**
     * 作家申报表
     */
    protected void declaration() {
        String tableName = "writer_declaration";// 要迁移的旧库表名
        JdbcHelper.addColumn(tableName); // 增加new_pk字段
        String sql = "select wd.writerid,wd.materid,wd.writername,s.usercode,s.username,"
                + "wd.sex,wd.birthdate,wd.seniority,wd.duties,wd.positional,wd.address,"
                + "wd.postcode,wd.handset,wd.email,wd.idcardtype,"
                + "IFNULL(wd.idcardtype,0) idcardtype,"
                + "wd.idcard,wd.linktel,wd.fax,tm.new_pk tm_materid,"
                + "s.new_pk sys_userid,wd.unitid,bo.new_pk org_id,wd.workunit,bo.orgcode,"
                + "case when wd.submittype=10 then 0 "
                + "when wd.submittype=11 and ta.auditstate=10 then 1 "
                + "when ta.auditstate=12 and wd.submittype=11 then 2 "
                + "when ta.auditstate=11 and wd.submittype=11 then 3 "
                + "when wd.submittype=11 then 1 "
                + "when wd.submittype=12 then 2 "
                + "end online_progress,wd.submittype,ta.auditstate,ta.auditdate,"
                + "case when eup.new_pk is not null then eup.new_pk else null "
                + "end auth_user_id,"
                + "case when ta.isreceivedpaper=1 then 2 "
                + "when ta.isreceivedpaper=0 then 0 "
                + "when ta.isreceivedpaper is null then 0 "
                + "end offline_progress,"
                + "ta.isreceivedpaper,ta.editauditstate,"
                + "case when wd.submittype=10 then 1 "
                + "else 0 end is_staging,wd.submittype,ta.editauditdate,"
                + "wd.userid,s.sysflag,su.usertype "
                + "from writer_declaration wd "
                + "left join teach_material tm on tm.materid=wd.materid "
                + "left join ba_organize bo on bo.orgid=wd.unitid "
                + "left join sys_user s on s.userid=wd.userid "
                + "left join sys_userext su on su.userid=wd.userid "
                + "left join (select writerid,auditstate,auditid,editauditid,auditdate,"
                + "isreceivedpaper,editauditstate,editauditdate "
                + "from teach_applyposition group by writerid) ta "
                + "left join sys_user eup on eup.userid = ta.editauditid "
                + "on ta.writerid=wd.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql); // 查询所有数据
        int count = 0; // 迁移成功的条目数
        int materialidCount = 0;
        int sysflagCount = 0;
        int usertypeCount = 0;
        int useridCount = 0;
        int decCount = 0;
        int realNameCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;//统计正常数据的数量
        int[] state = {0,0,0,0,0,0,0};//判断该数据是否有相应异常情况的标识
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("writerid"); // 旧表主键值
            Long materialid = (Long) map.get("tm_materid"); // 教材id
            Long userid = (Long) map.get("sys_userid"); // 作家id
            String realName = (String) map.get("writername"); // 申报表作家姓名
            String sexJudge = (String) map.get("sex"); // 性别
            String experienceNum = (String) map.get("seniority"); // 教龄
            String postCode = (String) map.get("postcode"); // 邮编
            Long orgId = (Long) map.get("org_id"); // 申报单位id
            Long onlineProgressJudge = (Long) map.get("online_progress"); // 审核进度
            Long authUserid = (Long) map.get("auth_user_id"); // 审核人id
            Long offlineProgressJudge = (Long) map.get("offline_progress"); // 纸质表进度
            Long isStagingJudge = (Long) map.get("is_staging"); // 是否暂存
            String unitid = (String) map.get("unitid"); // 旧表申报单位id
            Integer sysflag = (Integer) map.get("sysflag"); // 0为后台用户，1为前台用户
            Integer usertype = (Integer) map.get("usertype"); // 2为学校管理员
            // 机构代码去除空格截取
            String orgcode = (String) map.get("orgcode"); // 机构代码
            String orgCodes = null;
            if (orgcode != null && !orgcode.equals("") && orgcode.length() > 3) {
            	orgCodes = orgcode.trim().substring(0, 3); // 去除空格截取
            }
            if (ObjectUtil.isNull(sysflag) || sysflag.equals(0)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到为后台用户申报教材。"));
                excel.add(map);
                logger.debug("找到为后台用户申报教材，此结果将被记录在Excel中");
                sysflagCount++;
                if (state[0] == 0){
                	reason.append("申报教材的对应人员为社内用户。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            if (usertype.equals(2)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到为用户类型为学校管理员申报教材。"));
                excel.add(map);
                logger.debug("找到为用户类型为学校管理员申报教材，此结果将被记录在Excel中");
                usertypeCount++;
                if (state[1] == 0){
                	reason.append("申报教材的对应人员为机构管理员用户。");
                	dealWith.append("放弃迁移。");
                	state[1] = 1;
                }
                continue;
            }
            Declaration declaration = new Declaration();
            if (ObjectUtil.isNull(materialid) || materialid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到教材对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到教材对应的关联结果，此结果将被记录在Excel中");
                materialidCount++;
                if (state[2] == 0){
                	reason.append("找不到用户申报的教材。");
                	dealWith.append("放弃迁移。");
                	state[2] = 1;
                }
                continue;
            }
            declaration.setMaterialId(materialid);
            if (ObjectUtil.isNull(userid) || userid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到作家对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到作家对应的关联结果，此结果将被记录在Excel中");
                useridCount++;
                if (state[3] == 0){
                	reason.append("找不到申报人员是哪一位用户。");
                	dealWith.append("放弃迁移。");
                	state[3] = 1;
                }
                continue;
            } else {
                if ("7d4856e6-99ca-48fb-9205-3704c01a109e".equals(id)) {
                    WriterUserManagerVO vo = new WriterUserManagerVO();
                    vo.setUsername("18045661072");
                    PageParameter<WriterUserManagerVO> param = new PageParameter<>(1, 1, vo);
                    PageResult<WriterUserManagerVO> list = writerUserService.getListWriterUser(param,null);
                    if (list.getRows().isEmpty()) {
                        map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到'18045661072-李勇'对应的关联结果。"));
                        excel.add(map);
                        logger.debug("未找到'18045661072-李勇'对应的关联结果，此结果将被记录在Excel中");
                        useridCount++;
                        if (state[4] == 0){
                        	reason.append("未找到申报人员李勇所对应的系统用户。");
                        	dealWith.append("放弃迁移。");
                        	state[4] = 1;
                        }
                        continue;
                    }
                    declaration.setUserId(list.getRows().get(0).getId());
                } else {
                    declaration.setUserId(userid);
                }
            }
            if (StringUtil.isEmpty(realName) && isStagingJudge.intValue() == 0) { // 申报表作家姓名为空并且没有暂存
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找到申报表作家姓名为空并且没有暂存。"));
                realNameCount++;
                if (state[5] == 0){
                	reason.append("已提交申报表的作家找不到真实姓名。");
                	dealWith.append("照常迁入。");
                	state[5] = 1;
                }
                /*excel.add(map);
                logger.debug("找到申报表作家姓名为空并且没有暂存，此结果将被记录在Excel中");
                continue;*/
            } else {
            	/*if ("0579c3b70c3e4adcbd008b6065e83c7f".equals(id)) {
            		declaration.setRealname("魏敏杰");
            	} else {
            		
				}*/
            	declaration.setRealname(realName);
            }
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
            declaration.setExperience(Integer.parseInt(experienceNum)); // 教龄
            declaration.setOrgName((String) map.get("workunit")); // 工作单位
            declaration.setPosition((String) map.get("duties")); // 职务
            declaration.setTitle((String) map.get("positional")); // 职称
            declaration.setAddress((String) map.get("address")); // 联系地址
            if (StringUtil.strLength(postCode) > 20 || "55894b98-6b15-4210-9460-11bdf6e8e89c".equals(id)) {
                declaration.setPostcode("100000");
            } else if ("097674e55b094bb58e99c09f12b4e124".equals(id)) {
                declaration.setPostcode("434020");
            } else if ("245328b530b04523ab26e6b70d7764d8".equals(id)) {
                declaration.setPostcode("211199");
            } else {
                declaration.setPostcode(postCode); // 邮编
            }
            declaration.setHandphone((String) map.get("handset")); // 手机
            declaration.setEmail((String) map.get("email")); // 邮箱
            declaration.setIdtype((Short) map.get("idcardtype1")); // 证件类型
            declaration.setHandphone((String) map.get("handset")); // 手机
            String idcard = (String) map.get("idcard");
            if (StringUtil.notEmpty(idcard)) {
                if (idcard.length() > 20) {
                    idcard = idcard.substring(0, 19);
                }
            }
            declaration.setIdcard(idcard); // 证件号码
            declaration.setIdtype((Short) map.get("idcardtype1")); // 证件类型
            declaration.setTelephone((String) map.get("linktel")); // 联系电话
            declaration.setFax((String) map.get("fax")); // 传真
            declaration.setIsDispensed(false); // 服从调剂
            declaration.setIsUtec(false); // 参与本科教学评估认证
            declaration.setDegree(0); // 学历
            declaration.setExpertise(null); // 专业特长
            /* 修改一个特殊情况 */
            if ("a4大小、128g铜板、彩印、正反面、2000册，印刷费1600元".equals(declaration.getHandphone())) {
                declaration.setHandphone(null);
                declaration.setIdcard(null); // 证件号码
                declaration.setTelephone(null); // 联系电话
                declaration.setFax(null); // 传真
            }
            // 旧表申报单位id为5的或者机构代码截取为15-的把orgid设置成0
            if ("5".equals(unitid) || "15-".equals(orgCodes) 
            		|| (ObjectUtil.isNull(orgId) && isStagingJudge.intValue() == 0)) { 
                declaration.setOrgId(0L); // 0为人民卫生出版社
            } else {
                declaration.setOrgId(orgId); // 申报单位id
            }
            if (ObjectUtil.notNull(onlineProgressJudge)) {
                Integer onlineProgress = onlineProgressJudge.intValue(); // 审核进度
                declaration.setOnlineProgress(onlineProgress);
            } else {
                declaration.setOnlineProgress(0);
            }
            declaration.setAuthUserId(authUserid);
            declaration.setAuthDate((Timestamp) map.get("auditdate")); // 审核通过时间
            if (ObjectUtil.notNull(offlineProgressJudge)) {
                Integer offlineProgress = offlineProgressJudge.intValue(); // 纸质表进度
                declaration.setOfflineProgress(offlineProgress);
            } else {
                declaration.setOfflineProgress(0);
            }
            declaration.setPaperDate((Timestamp) map.get("editauditdate")); // 纸质表收到时间
            declaration.setReturnCause(null); // 退回原因
            if (ObjectUtil.isNull(isStagingJudge)) {
                declaration.setIsStaging(false);
            } else {
                Integer isStaging = isStagingJudge.intValue(); // 是否暂存
                if (isStaging.equals(0)) {
                    declaration.setIsStaging(false);
                } else {
                    declaration.setIsStaging(true);
                }
            }
            declaration.setIsDeleted(false); // 是否被逻辑删除
            if (declaration.getUserId() == null) {
                logger.info("userId为空的作家名称为：{}", declaration.getRealname());
            } else {
                //logger.info("当前执行的作家id：{}", declaration.getUserId().toString());
            }
            Declaration dec = declarationService.getDeclarationByMaterialIdAndUserId(declaration.getMaterialId(),
                    declaration.getUserId());
            if (dec != null) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("已存在教材id和作家id均相同的记录。"));
                excel.add(map);
                logger.error("已存在教材id和作家id均相同的记录，本条数据放弃插入，material_id={}，user_id={}",
                        declaration.getMaterialId(), declaration.getUserId());
                decCount++;
                if (state[6] == 0){
                	reason.append("同一用户申报同一教材的记录多于1条。");
                	dealWith.append("放弃迁移。");
                	state[6] = 1;
                }
                continue;
            }
            declaration = declarationService.addDeclaration(declaration);
            long pk = declaration.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "writerid", id); // 更新旧表中new_pk字段
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家申报表", "declaration");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "declaration");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家申报表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
        }
        logger.info("申报表作家姓名为空并且不是暂存表数量：{}", realNameCount);
        logger.info("后台用户申报教材数量：{}", sysflagCount);
        logger.info("用户类型为学校管理员申报教材数量：{}", usertypeCount);
        logger.info("未找到作家对应的关联结果数量：{}", useridCount);
        logger.info("教材id和作家id均相同的记录数量：{}", decCount);
        logger.info("未找到教材对应的关联结果数量：{}", materialidCount);
        logger.info("writer_declaration表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<>();
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
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
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
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家学习经历表", "dec_edu_exp");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_edu_exp");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家学习经历表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
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
        String sql = "select w.workid,w.workunitname,w.position,w.remark,w.startstopdate,w.enddate,"
        		+ "wd.new_pk id "
        		+ "from writer_work w "
                + "left join writer_declaration wd on wd.writerid=w.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state= {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            decWorkExp.setDeclarationId(declarationid);
            decWorkExp.setOrgName(orgName);
            decWorkExp.setPosition(position);
            decWorkExp.setNote((String) map.get("remark")); // 备注
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
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家工作经历表", "dec_work_exp");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_work_exp");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家工作经历表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
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
        String sql = "select w.teachid,w.schoolname,w.subjects,w.remark,w.startstopdate,w.enddate,"
        		+ "wd.new_pk id "
        		+ "from writer_teach w "
                + "left join writer_declaration wd on wd.writerid=w.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
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
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家教学经历表", "dec_teach_exp");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_teach_exp");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家教学经历表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
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
        String sql = "select wa.acadeid,wa.level,wa.duties,wa.organization,wa.remark,"
        		+ "wd.new_pk id "
        		+ "from writer_acade wa "
                + "left join writer_declaration wd on wd.writerid=wa.writerid";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
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
                    if (4 == rank) {
                    	decAcade.setRank(5);
                    } else {
                    	decAcade.setRank(rank);
                    }
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
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家兼职学术表", "dec_acade");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_acade");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家兼职学术表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
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
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            decLastPosition.setDeclarationId(declarationid);
            decLastPosition.setMaterialName(materialName);
            Integer position = positionJudge.intValue();
            decLastPosition.setPosition(position);
            decLastPosition.setNote((String) map.get("remark")); // 备注
            decLastPosition.setSort(999); // 显示顺序
            decLastPosition.setPublisher("人民卫生出版社"); // 出版社
            decLastPosition.setPublishDate(null); // 出版时间
            decLastPosition = decLastPositionService.addDecLastPosition(decLastPosition);
            long pk = decLastPosition.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "materpatid", id);
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家上套教材参编情况表", "dec_last_position");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_last_position");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家上套教材参编情况表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
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
        String sql = "select wc.constructionid,wc.curriculumname,wc.classhour,wc.type,wc.remark,"
        		+ "wd.new_pk id "
        		+ "from writer_construction wc "
                + "left join writer_declaration wd on wd.writerid=wc.writerid";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("constructionid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String courseName = (String) map.get("curriculumname"); // 课程名称
            String classHour = (String) map.get("classhour"); // 课程全年课时数
            String typeJudge = (String) map.get("type"); // 课程级别
            DecCourseConstruction decCourseConstruction = new DecCourseConstruction();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            decCourseConstruction.setDeclarationId(declarationid);
            decCourseConstruction.setCourseName(courseName);
            decCourseConstruction.setClassHour(classHour);
            Integer type = Integer.parseInt(typeJudge);
            if (type.intValue() == 1) {
            	decCourseConstruction.setType(2);
            } else if (type.intValue() == 2) {
            	decCourseConstruction.setType(3);
            } else if (type.intValue() == 3) {
            	decCourseConstruction.setType(0);
            } else {
            	decCourseConstruction.setType(0);
            }
            decCourseConstruction.setNote((String) map.get("remark")); // 备注
            decCourseConstruction.setSort(999); // 显示顺序
            decCourseConstruction = decCourseConstructionService.addDecCourseConstruction(decCourseConstruction);
            long pk = decCourseConstruction.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "constructionid", id);
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家精品课程建设情况表", "dec_course_construction");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_course_construction");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家精品课程建设情况表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
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
                + "else 0 end rank,wa.remark,wd.new_pk id "
                + "from writer_editorbook wa "
                + "left join writer_declaration wd on wd.writerid=wa.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            decNationalPlan.setDeclarationId(declarationid);
            decNationalPlan.setMaterialName(materialName);
            if (StringUtil.notEmpty(isbn)) {
                isbn = isbn.trim();
                isbn = isbn.replace("ISBN", "ISBN ").replace("isbn", "ISBN ").replace(":", "")
                        .replace("：", "").replace("、", "/").replace(".", "·").replace("*", "·")
                        .replace("•", "·");
            }
            decNationalPlan.setIsbn(isbn);
            Integer rank = rankJudge.intValue();
            decNationalPlan.setRank(rank);
            // 教材级别(文字输入)0=无/1=教育部十二五/2=国家卫计委十二五/3=both
            if (0 == rank) {
            	decNationalPlan.setRankText("无");
            } else if (1 == rank) {
            	decNationalPlan.setRankText("教育部十二五");
            } else if (2 == rank) {
            	decNationalPlan.setRankText("国家卫计委十二五");
            } else if (3 == rank) {
            	decNationalPlan.setRankText("both");
            }
            decNationalPlan.setNote((String) map.get("remark")); // 备注
            decNationalPlan.setSort(999); // 显示顺序
            decNationalPlan = decNationalPlanService.addDecNationalPlan(decNationalPlan);
            long pk = decNationalPlan.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "editorbookid", id);
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家主编国家级规划教材情况表", "dec_national_plan");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_national_plan");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家主编国家级规划教材情况表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
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
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
            DecTextbookPmph decTextbookPmph = new DecTextbookPmph();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            if (StringUtil.notEmpty(isbn)) {
                isbn = isbn.trim();
                isbn = isbn.replace("ISBN", "ISBN ").replace("isbn", "ISBN ").replace(":", "")
                        .replace("：", "").replace("、", "/").replace(".", "·").replace("*", "·")
                        .replace("•", "·");
            }
            Integer rank;
            if (ObjectUtil.isNull(rankJudge)) {
                rank = 0;
            } else {
                rank = rankJudge.intValue();
            }
            if (ObjectUtil.isNull(publisher)) {
                publisher = "未填写";
            }
            int position;
            if (ObjectUtil.isNull(positionJudge)){
                position = 5;
            }else{
                position = positionJudge.intValue();
            }
            long pk;
            if (!"人民卫生出版社".equals(publisher.trim())) {
                decTextbook.setDeclarationId(declarationid);
                decTextbook.setMaterialName(materialName);
                decTextbook.setRank(rank);
                decTextbook.setPosition(position);
                decTextbook.setPublisher(publisher);
                decTextbook.setPublishDate(publishDate);
                decTextbook.setIsbn(isbn);
                decTextbook.setNote((String) map.get("remark")); // 备注
                decTextbook.setSort(999); // 显示顺序
                decTextbook = decTextbookService.addDecTextbook(decTextbook);
                pk = decTextbook.getId();
            } else {
                decTextbookPmph.setDeclarationId(declarationid);
                decTextbookPmph.setMaterialName(materialName);
                decTextbookPmph.setRank(rank);
                decTextbookPmph.setPosition(position);
                decTextbookPmph.setPublishDate(publishDate);
                decTextbookPmph.setIsbn(isbn);
                decTextbookPmph.setNote((String) map.get("remark")); // 备注
                decTextbookPmph.setSort(999); // 显示顺序
                decTextbookPmph = decTextbookPmphService.addDecTextbookPmph(decTextbookPmph);
                pk = decTextbookPmph.getId();
            }
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "materwriteid", id);
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家教材编写情况表", "dec_textbook");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_textbook");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家教材编写情况表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_materwrite表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void decTextbookOther() {
        String tableName = "writer_othermaterwrite "; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        String sql = "select wo.othermaterwriteid,wo.writerid,wo.matername,"
                + "case when wo.duty like '%1%' then 1 when wo.duty like '%2%' then 2 "
                + "else 3 end position,"
                + "wo.publishing,"
                + "if(wo.publisdate='0000-00-00 00:00:00',"
                + "STR_TO_DATE('2017-01-01 14:17:17','%Y-%c-%d %H:%i:%s'),wo.publisdate)publisdates,"
                + "wo.booknumber,wo.remark,wd.new_pk id "
                + "from writer_othermaterwrite wo "
                + "left join writer_declaration wd on wd.writerid=wo.writerid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("othermaterwriteid"); // 旧表主键值
            Long declarationid = (Long) map.get("id"); // 申报表id
            String materialName = (String) map.get("matername"); // 教材名称
            Long positionJudge = (Long) map.get("position"); // 编写职务
            String publisher = (String) map.get("publishing"); // 出版社
            Date publishDate = (Date) map.get("publisdates"); // 出版时间
            String isbn = (String) map.get("booknumber"); // 标准书号
            DecTextbook decTextbook = new DecTextbook();
            DecTextbookPmph decTextbookPmph = new DecTextbookPmph();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            if (StringUtil.notEmpty(isbn)) {
                isbn = isbn.trim();
                isbn = isbn.replace("ISBN", "ISBN ").replace("isbn", "ISBN ").replace(":", "")
                        .replace("：", "").replace("、", "/").replace(".", "·").replace("*", "·")
                        .replace("•", "·");
            }
            if (ObjectUtil.isNull(publisher)) {
                publisher = "未填写";
            }
            int position;
            if (ObjectUtil.isNull(positionJudge)){
                position = 5;
            }else{
                position = positionJudge.intValue();
            }
            long pk;
            if (!"人民卫生出版社".equals(publisher.trim())) {
                decTextbook.setDeclarationId(declarationid);
                decTextbook.setMaterialName(materialName);
                decTextbook.setRank(0); // 教材级别（设置成无）
                decTextbook.setPosition(position);
                String publishers = publisher.trim();
                if (StringUtil.length(publishers) > 50 || "-1819".equals(id)) {
                    publishers.substring(0, 7);
                    decTextbook.setPublisher(publishers);
                } else {
                    decTextbook.setPublisher(publishers);
                }
                decTextbook.setPublishDate(publishDate);
                decTextbook.setIsbn(isbn);
                decTextbook.setNote((String) map.get("remark")); // 备注
                decTextbook.setSort(999); // 显示顺序
                decTextbook = decTextbookService.addDecTextbook(decTextbook);
                pk = decTextbook.getId();
            } else {
                decTextbookPmph.setDeclarationId(declarationid);
                decTextbookPmph.setMaterialName(materialName);
                decTextbookPmph.setRank(0); // 教材级别（设置成无）
                decTextbookPmph.setPosition(position);
                decTextbookPmph.setPublishDate(publishDate);
                decTextbookPmph.setIsbn(isbn);
                decTextbookPmph.setNote((String) map.get("remark")); // 备注
                decTextbookPmph.setSort(999); // 显示顺序
                decTextbookPmph = decTextbookPmphService.addDecTextbookPmph(decTextbookPmph);
                pk = decTextbookPmph.getId();
            }
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "othermaterwriteid", id);
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家教材其它编写情况表", "dec_textbookOther");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_textbookOther");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家其他教材编写情况表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
        }
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
        logger.info("writer_othermaterwrite表迁移完成，异常条目数量：{}", excel.size());
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
        String sql = "select ws.scientresearchid,ws.topicname,ws.approvaluntiname,ws.award,ws.remark,"
        		+ "wd.new_pk id "
        		+ "from writer_scientresearch ws "
                + "left join writer_declaration wd on wd.writerid=ws.writerid";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
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
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            decResearch.setDeclarationId(declarationid);
            decResearch.setResearchName(researchName);
            decResearch.setApprovalUnit(approvalUnit);
            decResearch.setAward(award);
            decResearch.setNote((String) map.get("remark")); // 备注
            decResearch.setSort(999); // 显示顺序
            decResearch = decResearchService.addDecResearch(decResearch);
            long pk = decResearch.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "scientresearchid", id);
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家科研情况表", "dec_research");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_research");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家科研情况表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
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
     * 作家扩展项填报表
     */
    protected void decExtension() {
        String tableName = "teach_material_extvalue"; // 要迁移的旧库表名
        JdbcHelper.addColumn(tableName); // 增加new_pk字段
        String sql = "select wme.extvalueid,wme.content,"
        		+ "tme.expendname,wd.new_pk wdid,tme.new_pk tmeid "
                + "from teach_material_extvalue wme "
                + "left join writer_declaration wd on wd.writerid=wme.writerid "
                + "left join teach_material_extend tme on tme.expendid=wme.expendid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0; // 迁移成功的条目数
        int extensionidCount = 0;
        int declarationidCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0,0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            Double id = (Double) map.get("extvalueid"); // 旧表主键值
            Long extensionid = (Long) map.get("tmeid"); // 教材扩展项id
            Long declarationid = (Long) map.get("wdid"); // 申报表id
            String content = (String) map.get("content"); // 扩展项内容
            String contents = content.trim(); // 扩展项内容去除空格
            DecExtension decExtension = new DecExtension();
            if (ObjectUtil.isNull(extensionid) || extensionid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到教材扩展项对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到教材扩展项对应的关联结果，此结果将被记录在Excel中");
                extensionidCount++;
                if (state[0] == 0){
                	reason.append("未找到教材对应的扩展项。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            decExtension.setExtensionId(extensionid);
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                declarationidCount++;
                if (state[1] == 0){
                	reason.append("没有找对对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[1] = 1;
                }
                continue;
            }
            decExtension.setDeclarationId(declarationid);
            decExtension.setContent(contents);
            decExtension = decExtensionService.addDecExtension(decExtension);
            long pk = decExtension.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "extvalueid", id);
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家扩展项填报表", "dec_extension");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_extension");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家扩展项填报表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
        }
        logger.info("未找到教材扩展项对应的关联结果数量：{}", extensionidCount);
        logger.info("未找到申报表对应的关联结果数量：{}", declarationidCount);
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
                + "GROUP_CONCAT(case when tp.positiontype=1 and tp.directoraudit>=11 then 'a' "
                + "when tp.positiontype=2 and tp.directoraudit>=11 then 'b' "
                + "when tp.positiontype=3 and tp.directoraudit>=41 then 'c' else 'd' "
                + "end ORDER BY tp.positiontype) chosen_position,"
                + "min(tp.mastersort) mastersort,ta.outlineurl,ta.outlinename syllabus_name,"
                + "ifnull(wd.updatedate,wd.createdate) gmt_create,"
                + "wd.new_pk wdid,tb.new_pk tbid "
                + "from teach_applyposition ta "
                + "left join teach_positionset tp on tp.appposiid=ta.appposiid "
                + "left join writer_declaration wd on wd.writerid=ta.writerid "
                + "left join teach_bookinfo tb on tb.bookid=ta.bookid "
                + "WHERE ta.positiontype in (1,2,3) and wd.writerid is not null "
                + "and tb.bookid is not null "
                + "GROUP BY wd.writerid,tb.bookid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0; // 迁移成功的条目数
        int extensionidCount = 0;
        int textbookidCount = 0;
        int outListUrlCount = 0;
        int outListCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0,0,0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("materid"); // 旧表主键值
            Long declarationid = (Long) map.get("wdid"); // 申报表id
            Long textbookid = (Long) map.get("tbid"); // 书籍id
            String temppresetPosition = (String) map.get("preset_position"); // 申报职务
            Long isOnList = (Long) map.get("is_on_list"); // 是否进入预选名单
            String tempchosenPosition = (String) map.get("chosen_position"); // 遴选职务
            Integer mastersort = (Integer) map.get("mastersort"); // 排位
            DecPosition decPosition = new DecPosition();
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                extensionidCount++;
                if (state[0] == 0){
                	reason.append("找不带对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            decPosition.setDeclarationId(declarationid);
            if (ObjectUtil.isNull(textbookid) || textbookid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到书籍对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到书籍对应的关联结果，此结果将被记录在Excel中");
                textbookidCount++;
                if (state[1] == 0){
                	reason.append("找不到申报的书籍。");
                	dealWith.append("放弃迁移。");
                	state[1] = 1;
                }
                continue;
            }
            decPosition.setTextbookId(textbookid);
            temppresetPosition += "," + temppresetPosition + ",";
            String Positions = "0";
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
            decPosition.setPresetPosition(Positions);//转成10进制
            if (ObjectUtil.isNull(isOnList)) {
                decPosition.setIsOnList(true);
            } else {
                Integer isOn = isOnList.intValue();
                if (isOn.equals(1)) {
                    decPosition.setIsOnList(true);
                } else {
                    decPosition.setIsOnList(false);
                }
            }
            tempchosenPosition += "," + tempchosenPosition + ",";
            Integer chosen = 0;
            if (tempchosenPosition.contains(",a,")) {
                chosen = 4;
            } else if (tempchosenPosition.contains(",b,")) {
                chosen = 2;
            } else if (tempchosenPosition.contains(",c,")) {
                chosen = 1;
            } else if (tempchosenPosition.contains(",d,")) {
                chosen = 0;
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
                    map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常");
                    excel.add(map);
                    logger.debug("文件读取异常，路径<{}>，异常信息：{}", outLineUrl, ex.getMessage());
                    outListUrlCount++;
                    if (state[2] == 0){
                    	reason.append("教学大纲文件丢失。");
                    	dealWith.append("教学大纲可以没有，照常迁入数据库。");
                    	state[2] = 1;
                    }
                }
                decPosition.setSyllabusId(mongoId);
                decPositionService.updateDecPosition(decPosition);
            }
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家申报职位表", "dec_position");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_position");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "作家申报职位表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
        }
        logger.info("文件读取异常数量：{}", outListUrlCount);
        logger.info("更新mongoDB的id错误数量：{}", outListCount);
        logger.info("未找到教材扩展项对应的关联结果数量：{}", extensionidCount);
        logger.info("未找到书籍对应的关联结果数量：{}", textbookidCount);
        logger.info("teach_applyposition表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    private Map<Long, Long> publisherIdMap = new HashMap<Long, Long>(); // 教材id和主任id

    /**
     * 已公布作家申报职位表
     */
    protected void decPositionPublished() {
        String tableName = "teach_positionset"; // 要迁移的旧库表名
        JdbcHelper.addColumn(tableName); // 增加new_pk字段
        String sql = "select tp.positionid,tp.materid,tp.writerid,tp.bookid,"
                + "GROUP_CONCAT(case when tp.applypositiontype=1 then 'a' when tp.applypositiontype=2 "
                + "then 'b' when tp.applypositiontype=3 then 'c' else 'c' "
                + "end ORDER BY tp.applypositiontype) preset_position,"
                + "if(sum(if(tp.positiontype in (1,2,3),1,0))>0,true,false) is_on_list,"
                + "GROUP_CONCAT(case when tp.positiontype=1 and tp.directoraudit>=11 then 'a' "
                + "when tp.positiontype=2 and tp.directoraudit>=11 then 'b' "
                + "when tp.positiontype=3 and tp.directoraudit>=41 then 'c' else 'd' "
                + "end ORDER BY tp.positiontype) chosen_position,"
                + "min(tp.mastersort) mastersort,ta.outlineurl,ta.outlinename syllabus_name,"
                + "ifnull(wd.updatedate,wd.createdate) gmt_create,"
                + "wd.new_pk wdid,tb.new_pk tbid,tm.new_pk newmaterid "
                + "from teach_positionset tp "
                + "left join teach_applyposition ta on ta.appposiid=tp.appposiid "
                + "left join writer_declaration wd on wd.writerid=ta.writerid "
                + "left join teach_bookinfo tb on tb.bookid=ta.bookid "
                + "left join teach_material tm on tm.materid=ta.materid "
                + "WHERE ta.positiontype in (1,2,3) and wd.writerid is not null "
                + "and tb.bookid is not null "
                + "and tb.dealtype >= 70 "
                + "and ((tp.positiontype =1 and tp.directoraudit >=11) "
                + "or (tp.positiontype =2 and tp.directoraudit >=11) "
                + "or (tp.positiontype =3 and tp.directoraudit >=41)) "
                + "GROUP BY wd.writerid,tb.bookid ";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0; // 迁移成功的条目数
        int extensionidCount = 0;
        int textbookidCount = 0;
        int outListUrlCount = 0;
        int outListCount = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int correctCount = 0;
        int[] state = {0,0,0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String id = (String) map.get("positionid"); // 旧表主键值
            Long materid = (Long) map.get("newmaterid"); // 教材id
            Long publisherId = publisherIdMap.get(materid); // 公布人id
            if (ObjectUtil.isNull(publisherId)) {
                publisherId = materialService.getMaterialById(materid).getDirector();
                publisherIdMap.put(materid, publisherId);
            }
            Long declarationid = (Long) map.get("wdid"); // 申报表id
            Long textbookid = (Long) map.get("tbid"); // 书籍id
            String temppresetPosition = (String) map.get("preset_position"); // 申报职务
            Long isOnList = (Long) map.get("is_on_list"); // 是否进入预选名单
            String tempchosenPosition = (String) map.get("chosen_position"); // 遴选职务
            Integer mastersort = (Integer) map.get("mastersort"); // 排位
            DecPositionPublished decPositionPublished = new DecPositionPublished();
            decPositionPublished.setPublisherId(publisherId); // 公布人id
            if (ObjectUtil.isNull(declarationid) || declarationid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到申报表对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到申报表对应的关联结果，此结果将被记录在Excel中");
                extensionidCount++;
                if (state[0] == 0){
                	reason.append("找不到对应的申报作家。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            decPositionPublished.setDeclarationId(declarationid);
            if (ObjectUtil.isNull(textbookid) || textbookid.intValue() == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未找到书籍对应的关联结果。"));
                excel.add(map);
                logger.debug("未找到书籍对应的关联结果，此结果将被记录在Excel中");
                textbookidCount++;
                if (state[1] == 0){
                	reason.append("找不到申报的书籍。");
                	dealWith.append("放弃迁移。");
                	state[1] = 1;
                }
                continue;
            }
            decPositionPublished.setTextbookId(textbookid);
            temppresetPosition += "," + temppresetPosition + ",";
            String Positions = "0";
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
            decPositionPublished.setPresetPosition(Positions);//转成10进制
            if (ObjectUtil.isNull(isOnList)) {
                decPositionPublished.setIsOnList(true);
            } else {
                Integer isOn = isOnList.intValue();
                if (isOn.equals(1)) {
                    decPositionPublished.setIsOnList(true);
                } else {
                    decPositionPublished.setIsOnList(false);
                }
            }
            tempchosenPosition += "," + tempchosenPosition + ",";
            Integer chosen = 0;
            if (tempchosenPosition.contains(",a,")) {
                chosen = 4;
            } else if (tempchosenPosition.contains(",b,")) {
                chosen = 2;
            } else if (tempchosenPosition.contains(",c,")) {
                chosen = 1;
            } else if (tempchosenPosition.contains(",d,")) {
                chosen = 0;
            }
            decPositionPublished.setChosenPosition(chosen);
            decPositionPublished.setRank(mastersort);
            decPositionPublished.setSyllabusName((String) map.get("syllabus_name")); // 教学大纲名称
            decPositionPublished.setGmtCreate((Timestamp) map.get("gmt_create")); // 创建时间
            String outLineUrl = (String) map.get("outlineurl"); // 教学大纲id
            decPositionPublished = decPositionPublishedService.addDecPositionPublished(decPositionPublished);
            long pk = decPositionPublished.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "positionid", id);
            count++;
            if (StringUtil.notEmpty(outLineUrl)) {
                /* 以下读取教学大纲id并保存在mongoDB中，读取失败时导出到Excel中 */
                String mongoId = "";
                try {
                    mongoId = fileService.migrateFile(outLineUrl, FileType.SYLLABUS, pk);
                } catch (IOException ex) {
                    map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常");
                    excel.add(map);
                    logger.debug("文件读取异常，路径<{}>，异常信息：{}", outLineUrl, ex.getMessage());
                    outListUrlCount++;
                    if (state[2] == 0){
                    	reason.append("教学大纲文件丢失。");
                    	dealWith.append("教学大纲可以没有，照常迁入数据库。");
                    	state[2] = 1;
                    }
                }
                decPositionPublished.setSyllabusId(mongoId);
                decPositionPublishedService.updateDecPositionPublished(decPositionPublished);
            }
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "已公布作家申报职位表", "dec_position_published");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "dec_position_published");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "已公布作家申报职位表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
        }
        logger.info("文件读取异常数量：{}", outListUrlCount);
        logger.info("更新mongoDB的id错误数量：{}", outListCount);
        logger.info("未找到教材扩展项对应的关联结果数量：{}", extensionidCount);
        logger.info("未找到书籍对应的关联结果数量：{}", textbookidCount);
        logger.info("teach_positionset表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }
}
