/**
 * 
 */
package com.bc.pmpheep.migration;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

/**
 * <p>Title:迁移工具图二<p>
 * <p>Description:社内模块数据迁移<p>
 * @author lyc
 * @date 2017年11月1日 下午11:18:46
 */
@Component
public class MigrationStageTwo {

	private final Logger logger = LoggerFactory.getLogger(MigrationStageTwo.class);
	
	@Resource
	ExcelHelper excelHelper;
	@Resource
	FileService fileService;
	@Resource
	PmphDepartmentService pmphDepartmentService;
	@Resource
	PmphUserService pmphUserService;
	@Resource
	PmphRoleService pmphRoleService;
	@Resource
	PmphUserRoleService pmphUserRoleService;
	
	public void start(){
		pmphDepartment();
		pmphUser();
		pmphRole();
		pmphUserRole();
		cannotFindUser();
		cannotFindRole();
	}
	
	protected void pmphDepartment() {
		String tableName = "ba_organize";
		JdbcHelper.addColumn(tableName);
		String sql = "SELECT orgid,parentid,orgname,sortno,remark "
				   + "FROM ba_organize WHERE orgcode "
				   + "LIKE '15%' ORDER BY LENGTH(orgcode),orgcode ;";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String,Object>> excel = new LinkedList<>();
		int count = 0;
		for (Map<String,Object> map : maps) {
			String departmentId = (String) map.get("orgid");
			String parentCode = (String) map.get("parentid");
			String dpName = (String) map.get("orgname");
            Integer sort = (Integer) map.get("sortno");
            if (null != sort && sort < 0){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "显示顺序为负数");
            	excel.add(map);
            	logger.error("显示顺序为负数，有误，此结果将被记录在Excel中");
            	continue;
            }
            String note = (String) map.get("remark");
            PmphDepartment pmphDepartment = new PmphDepartment();
            Long parentId = 0L;
            if (!"0".equals(parentCode)){
            	parentId = JdbcHelper.getPrimaryKey(tableName, "orgid", parentCode);
            }
            pmphDepartment.setParentId(parentId);
            String path = JdbcHelper.getPath(tableName, "orgid", "parentid", parentCode);
            pmphDepartment.setPath(path);
            pmphDepartment.setDpName(dpName);
            pmphDepartment.setSort(sort);
            pmphDepartment.setNote(note);
            pmphDepartment = pmphDepartmentService.add(pmphDepartment);
            Long pk = pmphDepartment.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "orgid", departmentId);            
			count++;			
		}
		if(excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, tableName+"社内部门", tableName+"社内部门");
			}  catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("pmph_department表数据迁移完成");
		logger.info("原数据库中共有{}条数据，迁移了{}条数据",maps.size(),count);
		//记录信息
        Map<String,Object> msg= new HashMap<String,Object>();
        msg.put("result", "pmph_department表迁移完成"+count+"/"+ maps.size());
        SQLParameters.msg.add(msg);
	}
	
	protected void pmphUser() {
		String tableName="sys_user";
		String sql = "SELECT a.userid,a.usercode,a.`password`,a.isvalid,a.username,d.new_pk,"
					+"b.handset,b.email,e.filedir,a.memo,a.sortno "
					+"FROM sys_user a "
					+"LEFT JOIN sys_userext b ON a.userid = b.userid "
					+"LEFT JOIN sys_userorganize c ON a.userid = c.userid "
					+"LEFT JOIN ba_organize d ON c.orgid = d.orgid "
					+"LEFT JOIN (SELECT * FROM pub_addfileinfo x WHERE x.fileid IN (SELECT MAX(y.fileid) "
					+"FROM pub_addfileinfo y WHERE y.childsystemname='sys_userext_avatar' "
					+"GROUP BY y.operuserid))e "
					+"ON a.userid = e.operuserid "
					+"WHERE a.sysflag = 0 ;";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String,Object>> excel = new LinkedList<>();
	    int count = 0 ;
		for(Map<String,Object> map : maps){
			  StringBuilder sb = new StringBuilder();
			  String userId = (String) map.get("userid");
			  String userName = (String) map.get("usercode");
			  if (null == userName){
				  map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找不到用户的登陆账号  "));
				  excel.add(map);
				  logger.error("找不到用户的登陆账号，有误，此结果将被记录在Excel中");
				  continue;
			  }
			  String password = "888888";
			  Integer isDisabled = (Integer) map.get("isvalid");
			  String realName = (String) map.get("username");
			  if (null == realName || "".equals(realName)){
				  realName = userName;
			  }
			  Long departmentId = (Long) map.get("new_pk");
			  if (null == departmentId){
				  departmentId = 0L;
				  map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找不到对应的社内部门，暂时设为0，"
				  		+ "即未分配部门  "));
				  excel.add(map);
				  logger.info("找不到对应的社内部门，此结果将被记录在Excel中，暂时设为0，"
				  		+ "即未分配部门，过后同专家库进行核对确认进行二次处理");
			  }
			  String handphone = (String) map.get("handset");
			  String email = (String) map.get("email");
			  String avatar = (String) map.get("filedir");
			  String note = (String) map.get("memo");
			  Integer sort = (Integer) map.get("sortno");
			  if (null != sort && sort < 0){
				  sort = 999;
				  map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("显示顺序为负数，先暂时设为默认值  "));
				  excel.add(map);
				  logger.info("显示顺序为负数，有误，此结果将被记录在Excel中，先暂时设为默认值");
			  }
			  PmphUser pmphUser = new PmphUser();
			  pmphUser.setUsername(userName);
			  pmphUser.setPassword(password);
			  pmphUser.setIsDisabled(isDisabled==1);
			  pmphUser.setRealname(realName);
			  pmphUser.setDepartmentId(departmentId);
			  pmphUser.setHandphone(handphone);
			  pmphUser.setEmail(email);
			  pmphUser.setAvatar("DEFAULT");
			  pmphUser.setNote(note);
			  pmphUser.setSort(sort);
			  pmphUser = pmphUserService.add(pmphUser);
			  Long pk = pmphUser.getId();
			  JdbcHelper.updateNewPrimaryKey(tableName, pk, "userid", userId);
			  JdbcHelper.updateNewPrimaryKey("sys_userext", pk, "userid", userId);
			  count++;
			  String mongoId = "";
			  if (null != avatar){
				  try {
					mongoId = fileService.migrateFile(avatar, ImageType.PMPH_USER_AVATAR, pk);
				} catch (IOException ex) {
					mongoId = "DEFAULT";
					logger.error("文件读取异常，路径<{}>,异常信息：{}",avatar,ex.getMessage());
					map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("文件读取异常  "));
					excel.add(map);
				}
			  }else{
				  mongoId = pmphUser.getAvatar();
			  }
			  pmphUser.setAvatar(mongoId);
			  pmphUser.setPassword(null);
			  pmphUserService.update(pmphUser);
		  }
		if(excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, tableName + "社内用户", tableName + "社内用户");
			}  catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		  logger.info("pmph_user表迁移完成");
		  logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
		//记录信息
        Map<String,Object> msg= new HashMap<String,Object>();
        msg.put("result", "pmph_user表迁移完成"+count+"/"+ maps.size());
        SQLParameters.msg.add(msg);
	}
	
	protected void pmphRole() {
		String tableName = "sys_role";
		List<Map<String,Object>> maps = JdbcHelper.queryForList(tableName);
		List<Map<String,Object>> excel = new LinkedList<>();
		int count = 0;
		for (Map<String,Object> map : maps){
			Double roleId = (Double) map.get("roleid");
			String rolename = (String) map.get("rolename");
			Integer isDisabled = (Integer) map.get("isvalid");
			Integer sort = (Integer) map.get("sortno");
			if ( null != sort && sort < 0){
				sort = 999;
				map.put(SQLParameters.EXCEL_EX_HEADER, "显示顺序为负数");
				excel.add(map);
				logger.info("显示顺序为负数，有误，此结构将被记录在Excel中");
			}
			String note = (String) map.get("memo");
			PmphRole pmphRole = new PmphRole();
			pmphRole.setRoleName(rolename);
			pmphRole.setIsDisabled(isDisabled==1);
			pmphRole.setSort(sort);
			pmphRole.setNote(note);
			pmphRole = pmphRoleService.addPmphRole(pmphRole);
			count++ ;
			Long pk = pmphRole.getId();
			JdbcHelper.updateNewPrimaryKey(tableName, pk, "roleid", roleId);
		}
		if (excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, tableName, tableName);
			} catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("pmph_role表迁移完成");
		logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
		//记录信息
        Map<String,Object> msg= new HashMap<String,Object>();
        msg.put("result", "pmph_role表迁移完成"+count+"/"+ maps.size());
        SQLParameters.msg.add(msg);
	}
	
	protected void pmphUserRole() {
		String tableName = "sys_userrole";
		String sql = "SELECT a.userroleid,b.userid,b.new_pk user_new_pk,c.roleid,c.new_pk role_new_pk "
				    + "FROM sys_userrole a "
					+"LEFT JOIN sys_user b ON a.userid = b.userid "
					+"LEFT JOIN sys_role c ON a.roleid = c.roleid "
					+"WHERE b.sysflag = 0 ;";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		int count = 0;
		for (Map<String,Object> map : maps){
			Double userroleId = (Double) map.get("userroleid");
			Long userId = (Long) map.get("user_new_pk");
			Long roleId = (Long) map.get("role_new_pk");
			PmphUserRole pmphUserRole = new PmphUserRole();
			pmphUserRole.setUserId(userId);
			pmphUserRole.setRoleId(roleId);
			pmphUserRole = pmphUserRoleService.addPmphUserRole(pmphUserRole);
			count++;
			Long pk = pmphUserRole.getId();
			JdbcHelper.updateNewPrimaryKey(tableName, pk, "userroleid", userroleId);
		}
		logger.info("pmph_user_role表迁移完成");
		logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
		//记录信息
        Map<String,Object> msg= new HashMap<String,Object>();
        msg.put("result", "pmph_user_role表迁移完成"+count+"/"+ maps.size());
        SQLParameters.msg.add(msg);
	}
	
	protected void cannotFindUser() {
		String tableName = "sys_userext";
		String sql = "SELECT a.* "
					+"FROM sys_userext a "
					+"WHERE a.userid NOT IN (SELECT b.userid FROM sys_user b) ;";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String,Object>> excel = new LinkedList<>();
		int count = 0 ;
		for (Map<String,Object> map : maps){
			map.put(SQLParameters.EXCEL_EX_HEADER, "这些数据的关联字段在关联表sys_user表中不存在");
			excel.add(map);
			logger.error("这些数据的关联字段在关联表sys_user表中不存在,将被记录在Excel中，"
					+ "通过团队协商处理");
			count++;
		}
		if (excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, tableName + "缺失关联字段", tableName + "缺失关联字段");
			}  catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("sys_userext缺失关联字段数据共有{}条数据，导出{}条数据",maps.size(),count);
		//查找sys_user表中是否存在关联字段缺失的数据
		tableName = "sys_user";
		sql = "SELECT a.* "
			 +"FROM sys_user a "
			 +"WHERE a.userid NOT IN (SELECT b.userid FROM sys_userext b) ;";
		maps.clear();
		maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		excel.clear();
		count = 0;
		for (Map<String,Object> map : maps){
			map.put(SQLParameters.EXCEL_EX_HEADER, "这些数据的关联字段在关联表sys_userext表中不存在");
			excel.add(map);
			logger.error("这些数据的关联字段在关联表sys_userext表中不存在,将被记录在Excel中，"
					+ "通过团队协商处理");
			count++;
		}
		if (excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, tableName + "缺失关联字段", tableName + "缺失关联字段");
			}  catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("sys_user缺失关联字段数据共有{}条数据，导出{}条数据",maps.size(),count);
		//记录信息
        Map<String,Object> msg= new HashMap<String,Object>();
        msg.put("result", "sys_user表迁移完成"+count+"/"+ maps.size());
        SQLParameters.msg.add(msg);
	}
	
	protected void cannotFindRole() {
		String tableName = "sys_userrole";
		String sql = "SELECT a.* FROM sys_userrole a WHERE a.userid NOT IN "
				    +"(SELECT b.userid FROM sys_user b) ;";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String,Object>> excel = new LinkedList<>();
		int count = 0 ;
		for (Map<String,Object> map : maps){
			map.put(SQLParameters.EXCEL_EX_HEADER, "无法在sys_user用户表找到对应用户");
			excel.add(map);
			logger.info("用户表可能已将这些用户删除，导出Excel进行评定");
			count++;
		}
		if (excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, tableName + "关联缺失", tableName + "关联缺失");
			} catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("用户-角色关联表关联字段缺失共{}条数据，导出{}条数据",maps.size(),count);
		//记录信息
        Map<String,Object> msg= new HashMap<String,Object>();
        msg.put("result", "用户-角色关联 表迁移完成"+count+"/"+ maps.size());
        SQLParameters.msg.add(msg);
	}
	
}
