package com.bc.pmpheep.migration;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.DecEduExp;
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
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;

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
	
	public void start(){
		declaration();
		decEduExp();
		decWorkExp();
	}
	
	// 作家申报表
	protected void declaration(){
		String tableName = "writer_declaration"; // 要迁移的旧库表名
		JdbcHelper.addColumn(tableName); // 增加new_pk字段
		String sql = "select wd.writerid,wd.materid,wd.writername,wd.sex,wd.birthdate,wd.duties,"
				+ "wd.positional,wd.address,wd.postcode,wd.handset,wd.email,wd.idcardtype,"
				+ "wd.idcard,wd.linktel,wd.fax,"
				+ "case when wd.unitid=bo.orgid then 0 "
				+ "end org_id,wd.unitid,"
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
				+ "end offline_progress,ta.isreceivedpaper,ta.editauditstate,"
				+ "case when wd.submittype=10 then 0 "
				+ "when wd.submittype=11 then 1 "
				+ "end is_staging,wd.submittype,ta.editauditdate "
				+ "from writer_declaration wd "
				+ "left join teach_material tm on tm.materid=wd.materid "
				+ "left join ba_organize bo on bo.orgid=wd.unitid "
				+ "left join sys_userext su on su.userid=wd.userid "
				+ "left join teach_applyposition ta on ta.writerid=wd.writerid "
				+ "where su.userid is not null "
				+ "group by wd.writerid";
		List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		int count = 0; // 迁移成功的条目数
		List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
        	Declaration declaration = new Declaration();
        	String id = (String) map.get("writerid"); // 主键
        	declaration.setMaterialId((Long) map.get("materid")); // 教材id
        	if (null != map.get("writerid") || "" != map.get("writerid")) {
        		declaration.setUserId((Long) map.get("writerid")); // 作家id
        	}
        	declaration.setRealname((String) map.get("writername")); // 作家姓名
        	declaration.setSex((Short) map.get("sex")); // 性别
        	declaration.setBirthday((Date) map.get("birthdate")); // 生日
        	declaration.setExperience((Short) map.get("seniority")); // 教龄
        	declaration.setOrgName((String) map.get("workunit")); // 工作单位
        	declaration.setPosition((String) map.get("duties")); // 职务
        	declaration.setTitle((String) map.get("positional")); // 职称
        	declaration.setAddress((String) map.get("address")); // 联系地址
        	declaration.setPostcode((String) map.get("postcode")); // 邮编
        	declaration.setHandphone((String) map.get("handset")); // 手机
        	declaration.setEmail((String) map.get("email")); // 邮箱
        	declaration.setIdtype((Short) map.get("idcardtype")); // 证件类型
        	declaration.setIdcard((String) map.get("idcard")); // 证件号码
        	declaration.setTelephone((String) map.get("linktel")); // 联系电话
        	declaration.setFax((String) map.get("fax")); // 传真
        	declaration.setAuthDate((Timestamp) map.get("auditdate")); // 审核通过时间
        	declaration.setPaperDate((Timestamp) map.get("editauditdate")); // 纸质表收到时间
        	
        	Long orgId = (Long) map.get("org_id"); // 申报单位id
        	Short onlineProgress = (Short) map.get("online_progress"); // 审核进度
        	Long authUserId = (Long) map.get("auth_user_id"); // 审核人id
        	
        	Short offlineProgress = (Short) map.get("offline_progress"); // 纸质表进度
        	if (null == offlineProgress) {
        		map.put(SQLParameters.EXCEL_EX_HEADER, "未找到纸质表进度");
        		excel.add(map);
        		logger.error("未找到纸质表进度，此结果将被记录在Excel中");
                continue;
        	}
        	
        	Boolean isStaging = (Boolean) map.get("is_staging"); // 是否暂存
        	
        	long pk = declaration.getId();
        	JdbcHelper.updateNewPrimaryKey(tableName, pk, "writerid", id); // 更新旧表中new_pk字段
        	count++;
        }
        logger.info("writer_declaration表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
	}
	
	// 作家学习经历表
	protected void decEduExp(){
		String tableName = "writer_learn"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        List<Map<String, Object>> excel = new LinkedList<>();
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
        	String id = map.get("learnid").toString(); // 主键
        	DecEduExp decEduExp = new DecEduExp();
        	decEduExp.setId(0L); // 主键
        	decEduExp.setDeclarationId((Long) map.get("writerid")); // 申报表id
        	decEduExp.setSchoolName((String) map.get("schoolname")); // 学校名称
        	decEduExp.setMajor((String) map.get("speciality")); // 所学专业
        	decEduExp.setDegree((String) map.get("record")); // 学历
        	decEduExp.setNote((String) map.get("remark")); // 备注
        	decEduExp.setDateBegin((String) map.get("startstopdate")); // 起始时间
        	decEduExp.setDateEnd((String) map.get("enddate")); // 终止时间
        	decEduExp = decEduExpService.addDecEduExp(decEduExp);
        	long pk = decEduExp.getId();
        	JdbcHelper.updateNewPrimaryKey(tableName, pk, "learnid", id);
        	count++;
        }
        logger.info("writer_learn表迁移完成，异常条目数量：{}", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
	}
	
	// 作家工作经历表
	protected void decWorkExp(){
		
	}
}