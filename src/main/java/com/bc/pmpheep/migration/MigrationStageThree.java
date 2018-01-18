/**
 *
 */
package com.bc.pmpheep.migration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
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
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphRolePermissionService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

/**
 * <p>
 * Title:迁移工具图二<p>
 * <p>
 * Description:社内模块数据迁移<p>
 * @author lyc
 * @date 2017年11月1日 下午11:18:46
 */
@Component
public class MigrationStageThree {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageThree.class);
    private Long number = 0L;

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
    @Resource
    PmphRolePermissionService pmphRolePermissionService;

    public void start() {
        Date begin = new Date();
        pmphDepartment();
        pmphUser();
        pmphRole();
        pmphUserRole();
        pmphRolePermission();
        cannotFindUser();
        cannotFindRole();
        logger.info("迁移第三步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
    }

    protected void pmphDepartment() {
        String tableName = "ba_organize";//图一此表已添加new_pk
        String sql = "SELECT orgid,parentid,orgname,sortno,remark "
                + "FROM ba_organize WHERE orgcode "
                + "LIKE '15%' ORDER BY LENGTH(orgcode),orgcode ;";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            String departmentId = (String) map.get("orgid");
            String parentCode = (String) map.get("parentid");
            String dpName = (String) map.get("orgname");
            Integer sort = (Integer) map.get("sortno");
            if (ObjectUtil.notNull(sort) && sort < 0) {
            	sort = 999;
            }
            String note = (String) map.get("remark");
            PmphDepartment pmphDepartment = new PmphDepartment();
            if ("人民卫生出版社".equals(dpName)){
            	pmphDepartment.setId(0L);
            }
            Long parentId = 0L;
            //不为0说明有父节点
            if (!"0".equals(parentCode)) {
                parentId = JdbcHelper.getPrimaryKey(tableName, "orgid", parentCode);
            }
            pmphDepartment.setParentId(parentId);
            //因为查询结果是排序了的，所以子节点的新id一定在父节点之后才生成
            String path = JdbcHelper.getPath(tableName, "orgid", "parentid", parentCode);
            pmphDepartment.setPath(path);
            pmphDepartment.setDpName(dpName);
            pmphDepartment.setSort(sort);
            pmphDepartment.setNote(note);
            pmphDepartment = pmphDepartmentService.add(pmphDepartment);
            Long pk = pmphDepartment.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "orgid", departmentId);
            count++;
            number = pk;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "社内部门表", "pmph_department");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("pmph_department表数据迁移完成");
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "pmph_department表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void pmphUser() {
        String tableName = "sys_user";//此表图一程序中已添加new_pk
        String sql = "SELECT a.userid,a.usercode,a.`password`,a.isvalid,a.username,d.new_pk,"
                + "b.handset,b.email,e.filedir,a.memo,a.sortno, "
        		+ "(SELECT q.rolename FROM sys_userrole p "
        		+ "LEFT JOIN sys_role q ON p.roleid = q.roleid "
                + "WHERE p.userid = a.userid AND q.rolename='主任')rolename "
                + "FROM sys_user a "
                + "LEFT JOIN sys_userext b ON a.userid = b.userid "
                + "LEFT JOIN sys_userorganize c ON a.userid = c.userid "
                + "LEFT JOIN ba_organize d ON c.orgid = d.orgid "
                + "LEFT JOIN (SELECT * FROM pub_addfileinfo x WHERE x.fileid IN (SELECT MAX(y.fileid) "
                + "FROM pub_addfileinfo y WHERE y.childsystemname='sys_userext_avatar' "
                + "GROUP BY y.operuserid))e "
                + "ON a.userid = e.operuserid "
                + "WHERE a.sysflag = 0 OR a.usercode = 'admin';";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            String userId = (String) map.get("userid");
            String userName = (String) map.get("usercode");
            if (StringUtil.isEmpty(userName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("找不到用户的登陆账号。"));
                excel.add(map);
                continue;
            }
            String password = "888888";
            if ("admin".equals(userName)){
            	password = "123";
            }
            Integer isDisabled = (Integer) map.get("isvalid");
            String realName = (String) map.get("username");
            if (StringUtil.isEmpty(realName)) {
                realName = userName;
            }
            Long departmentId = (Long) map.get("new_pk");
            if (ObjectUtil.isNull(departmentId)) {
                departmentId = 0L;
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("此用户没有所属的社内部门。"));
                excel.add(map);
            }
            if (departmentId > number){
            	departmentId = 0L;
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("此用户所属部门为学校机构。"));
                excel.add(map);
            }
            String handphone = (String) map.get("handset");
            String email = (String) map.get("email");
            String avatar = (String) map.get("filedir");
            String note = (String) map.get("memo");
            Integer sort = (Integer) map.get("sortno");
            if (ObjectUtil.notNull(sort) && sort < 0) {
                sort = 999;
            }
            String director = (String) map.get("rolename");
            PmphUser pmphUser = new PmphUser();
            pmphUser.setUsername(userName);
            pmphUser.setPassword(password);
            pmphUser.setIsDisabled(isDisabled != 1);
            pmphUser.setRealname(realName);
            pmphUser.setDepartmentId(departmentId);
            pmphUser.setHandphone(handphone);
            pmphUser.setEmail(email);
            pmphUser.setAvatar("DEFAULT");
            pmphUser.setNote(note);
            pmphUser.setSort(sort);
            pmphUser.setIsDirector("主任".equals(director));
            pmphUser = pmphUserService.add(pmphUser);
            Long pk = pmphUser.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "userid", userId);
            JdbcHelper.updateNewPrimaryKey("sys_userext", pk, "userid", userId);
            count++;
            if (null != avatar) {
                String mongoId = "";
                try {
                    mongoId = fileService.migrateFile(avatar, ImageType.PMPH_USER_AVATAR, pk);
                } catch (IOException ex) {
                    mongoId = "DEFAULT";
                    logger.error("文件读取异常，路径<{}>,异常信息：{}", avatar, ex.getMessage());
                    map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("文件读取异常。"));
                    excel.add(map);
                } catch (Exception e) {
                    mongoId = "DEFAULT";
                    map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未知异常：" + e.getMessage() + "。"));
                    excel.add(map);
                }
                pmphUser.setAvatar(mongoId);
                pmphUser.setPassword(null);
                pmphUserService.update(pmphUser);
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "社内用户表", "pmph_user");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("pmph_user表迁移完成");
        logger.info("原数据库表共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "pmph_user表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void pmphRole() {
        String tableName = "sys_role";
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            Double roleId = (Double) map.get("roleid");
            String rolename = (String) map.get("rolename");
            Integer isDisabled = (Integer) map.get("isvalid");
            Integer sort = (Integer) map.get("sortno");
            if (ObjectUtil.notNull(sort) && sort < 0) {
                sort = 999;
            }
            String note = (String) map.get("memo");
            PmphRole pmphRole = new PmphRole();
            pmphRole.setRoleName(rolename);
            pmphRole.setIsDisabled(isDisabled != 1);
            pmphRole.setSort(sort);
            pmphRole.setNote(note);
            pmphRole = pmphRoleService.addPmphRole(pmphRole);
            count++;
            Long pk = pmphRole.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "roleid", roleId);
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "社内用户角色表", "pmph_role");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("pmph_role表迁移完成");
        logger.info("原数据库表共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "pmph_role表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void pmphUserRole() {
        String tableName = "sys_userrole";//此表在图3中已经添加new_pk，运行顺序是先图3再图2
        String sql = "SELECT a.userroleid,b.userid,b.new_pk user_new_pk,c.roleid,c.new_pk role_new_pk "
                + "FROM sys_userrole a "
                + "LEFT JOIN sys_user b ON a.userid = b.userid "
                + "LEFT JOIN sys_role c ON a.roleid = c.roleid "
                + "WHERE b.sysflag = 0 OR b.usercode = 'admin';";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            Double userroleId = (Double) map.get("userroleid");
            Long userId = (Long) map.get("user_new_pk");
            Long roleId = (Long) map.get("role_new_pk");
            if (ObjectUtil.isNull(roleId)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "用户所属角色被删除。");
                excel.add(map);
                continue;
            }
            PmphUserRole pmphUserRole = new PmphUserRole();
            pmphUserRole.setUserId(userId);
            pmphUserRole.setRoleId(roleId);
            pmphUserRole = pmphUserRoleService.addPmphUserRole(pmphUserRole);
            count++;
            Long pk = pmphUserRole.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "userroleid", userroleId);
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "社内用户-角色关联表", "pmph_user_role");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("pmph_user_role表迁移完成");
        logger.info("原数据库表共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "pmph_user_role表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     * 
     * Description:角色权限表
     * @author:lyc
     * @date:2017年12月19日下午6:28:40
     * @param 
     * @return void
     */
    protected void pmphRolePermission() {
    	String tableName = "sys_role";
		List<Map<String,Object>> maps = JdbcHelper.queryForList(tableName);
		List<Map<String,Object>> excel = new LinkedList<>();
		for (Map<String,Object> map : maps){
			Long newpk = (Long) map.get("new_pk");
			if (newpk == 0){
				map.put(SQLParameters.EXCEL_EX_HEADER, "此角色未迁移成功。");
				excel.add(map);
				continue;
			}
			PmphRolePermission pmphRolePermission = new PmphRolePermission();
			for (int i = 1 ; i< 26 ; i++){
				pmphRolePermission.setRoleId(newpk);
				pmphRolePermission.setPermissionId(Long.parseLong(String.valueOf(i)));
				pmphRolePermissionService.addPmphRolePermission(pmphRolePermission);
			}
		}
		if (excel.size() > 0){
			try {
                excelHelper.exportFromMaps(excel, "社内用户-权限关联表", "pmph_role_permission");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
		}
	}
    
    /**
     *
     * Description:找出拓展表中有但sys_user表中已经不存在的userid的信息，即废数据
     *
     * @author:lyc
     * @date:2017年11月9日下午6:04:14
     * @param
     * @return void
     */
    protected void cannotFindUser() {
        String sql = "SELECT a.* "
                + "FROM sys_userext a "
                + "WHERE a.userid NOT IN (SELECT b.userid FROM sys_user b) ;";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            map.put(SQLParameters.EXCEL_EX_HEADER, "这些数据的关联字段在关联表sys_user表中不存在");
            excel.add(map);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "拓展表关联字段缺失", "");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("sys_userext缺失关联字段数据共有{}条数据，导出{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "sys_user表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    /**
     *
     * Description:用户角色关联表中部分userid无法在用户表sys_user中找到，可能被删除
     *
     * @author:lyc
     * @date:2017年11月9日下午6:17:36
     * @param
     * @return void
     */
    protected void cannotFindRole() {
        String sql = "SELECT a.* FROM sys_userrole a WHERE a.userid NOT IN "
                + "(SELECT b.userid FROM sys_user b) ;";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            map.put(SQLParameters.EXCEL_EX_HEADER, "无法在sys_user用户表找到对应用户");
            excel.add(map);
            count++;
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "用户角色关联表关联字段缺失", "");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("用户-角色关联表关联字段缺失共{}条数据，导出{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "用户-角色关联 表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

}
