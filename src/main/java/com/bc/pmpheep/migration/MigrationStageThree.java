/**
 * 
 */
package com.bc.pmpheep.migration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.WriterProfile;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.back.service.WriterProfileService;
import com.bc.pmpheep.back.service.WriterRoleService;
import com.bc.pmpheep.back.service.WriterUserRoleService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

/**
 * <p>Title:图三数据迁移工具<p>
 * <p>Description:作家用户模块<p>
 * @author lyc
 * @date 2017年11月2日 下午4:56:48
 */
@Component
public class MigrationStageThree {

	private Logger logger = LoggerFactory.getLogger(MigrationStageThree.class);
	
	@Resource
	ExcelHelper excelHelper;
	@Resource
	FileService fileService;
	@Resource
	WriterRoleService writerRoleService;
	@Resource
	WriterUserRoleService writerUserRoleService;
	@Resource
	WriterProfileService writerProfileService;
	
	public void start(){
		writerRole();
	}
	
	protected void writerRole() {
		String tableName = "sys_role";
		List<Map<String,Object>> maps = JdbcHelper.queryForList(tableName);
		List<Map<String,Object>> excel = new LinkedList<>();
		int count = 0 ;
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
			WriterRole writerRole = new WriterRole();
			writerRole.setRoleName(rolename);
			writerRole.setIsDisabled(isDisabled == 1);
			writerRole.setSort(sort);
			writerRole.setNote(note);
			writerRole = writerRoleService.add(writerRole);
			count++ ;
			Long pk = writerRole.getId();
			JdbcHelper.updateNewPrimaryKey(tableName, pk, "roleid", roleId);
		}
		if (excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, tableName + "作家角色", tableName + "作家角色");
			} catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("writer_role表迁移完成");
		logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
	}
	
	protected void writerUserRole() {
		String tableName = "sys_userrole";
		String sql = "SELECT a.userroleid,b.userid,b.new_pk user_new_pk,c.roleid,c.new_pk role_new_pk "
					+"FROM sys_userrole a "
					+"LEFT JOIN sys_user b ON a.userid = b.userid "
					+"LEFT JOIN sys_role c ON a.roleid = c.roleid "
					+"LEFT JOIN sys_userext d ON a.userid = d.userid "
					+"WHERE b.sysflag = 1 AND d.usertype != 2 ;";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		int count = 0 ;
		for (Map<String,Object> map : maps){
			Double userroleId = (Double) map.get("userroleid");
			Long userId = (Long) map.get("user_new_pk");
			Long roleId = (Long) map.get("role_new_pk");
			WriterUserRole writerUserRole = new WriterUserRole();
			writerUserRole.setUserId(userId);
			writerUserRole.setRoleId(roleId);
			writerUserRole = writerUserRoleService.addWriterUserRole(writerUserRole);
			count++;
			Long pk = writerUserRole.getId();
			JdbcHelper.updateNewPrimaryKey(tableName, pk, "userroleid", userroleId);
		}
		logger.info("writer_user_role迁移完成");
		logger.info("原数据库表共{}条数据，迁移了{}条数据",maps.size(),count);
	}
	
	protected void writerProfile() {
		String sql= "SELECT  b.userid,b.new_pk,a.userid tag_userid,c.introduce,"
				        + "GROUP_CONCAT(d.tagname SEPARATOR '%') tag_name "
						+"FROM sys_usertagmap a LEFT JOIN sys_user b ON a.userid = b.userid "
						+"LEFT JOIN sys_userext c ON b.userid = c.userid "
						+"LEFT JOIN book_tag d ON a.tagid = d.tagid GROUP BY a.userid ;";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String,Object>> excel = new LinkedList<>();
		int count = 0;
		for (Map<String,Object> map : maps){
			Long userId = (Long) map.get("new_pk");
			if (null == userId || userId == 0){
				map.put(SQLParameters.EXCEL_EX_HEADER, "关联id主表存在或为社内用户");
				excel.add(map);
				logger.error("关联id主表存在或为社内用户,此结果将被记录在Excel中");
				continue;
			}
			String profile = (String) map.get("introduce");
			String tagName = (String) map.get("tag_name");
			WriterProfile writerProfile = new WriterProfile();
			writerProfile.setUserId(userId);
			writerProfile.setProfile(profile);
			writerProfile.setTag(tagName);
			writerProfile = writerProfileService.addWriterProfile(writerProfile);
			count++;
		}
		if (excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, "作家标签表", "");
			} catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("writer_profile表迁移完成");
		logger.info("原数据库表共{}条数据，迁移了{}条数据",maps.size(),count);
	}
	
	protected void writerUserCertification() {
		String tableName = "";
	}
	
	protected void orgUserRole() {
		String tableName = "sys_userrole";
		String sql = "SELECT a.userroleid,b.userid,b.usercode,b.username,b.new_pk user_new_pk,"
				+ "c.rolename,c.rolecode,c.isvalid,c.roleid,c.new_pk role_new_pk "
					+"FROM sys_userrole a "
					+"LEFT JOIN sys_user b ON a.userid = b.userid "
					+"LEFT JOIN sys_role c ON a.roleid = c.roleid "
					+"LEFT JOIN sys_userext d ON a.userid = d.userid "
					+"WHERE b.sysflag = 1 AND d.usertype = 2 ;";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String,Object>> excel = new LinkedList<>();
		int count = 0 ;
		for (Map<String,Object> map : maps){
			map.put(SQLParameters.EXCEL_EX_HEADER, "机构用户角色关联");
			excel.add(map);
			logger.info("现今没有机构用户角色关联表，暂时导出为Excel");
			count++;
		}
		if (excel.size() > 0){
			try {
				excelHelper.exportFromMaps(excel, tableName + "机构用户关联", tableName + "机构用户关联");
			} catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("机构用户关联角色共有{}条数据，导出了{}条数据",maps.size(),count);
	}
}
