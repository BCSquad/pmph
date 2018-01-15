/**
 *
 */
package com.bc.pmpheep.migration;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.WriterProfile;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUserCertification;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.back.service.WriterProfileService;
import com.bc.pmpheep.back.service.WriterRoleService;
import com.bc.pmpheep.back.service.WriterUserCertificationService;
import com.bc.pmpheep.back.service.WriterUserRoleService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

/**
 * <p>
 * Title:图三数据迁移工具<p>
 * <p>
 * Description:作家用户模块，需作为第二模块进行<p>
 * @author lyc
 * @date 2017年11月2日 下午4:56:48
 */
@Component
public class MigrationStageTwo {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageTwo.class);

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
    @Resource
    WriterUserCertificationService writerCertificationService;

    public void start() {
        Date begin = new Date();
        writerRole();
        writerUserRole();
        writerProfile();
        writerUserCertification();
        logger.info("迁移第二步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
    }

    protected void writerRole() {
        String tableName = "sys_role";
        JdbcHelper.addColumn(tableName);//增加new_pk字段
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
            WriterRole writerRole = new WriterRole();
            writerRole.setRoleName(rolename);
            writerRole.setIsDisabled(isDisabled != 1);
            writerRole.setSort(sort);
            writerRole.setNote(note);
            writerRole = writerRoleService.add(writerRole);
            count++;
            Long pk = writerRole.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "roleid", roleId);
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家角色表", "writer_role");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("writer_role表迁移完成");
        logger.info("原数据库表共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "writer_role表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void writerUserRole() {
        String tableName = "sys_userrole";
        JdbcHelper.addColumn(tableName);//增加new_pk字段
        String sql = "SELECT a.userroleid,b.userid,b.new_pk user_new_pk,c.roleid,c.new_pk role_new_pk "
                + "FROM sys_userrole a "
                + "LEFT JOIN sys_user b ON a.userid = b.userid "
                + "LEFT JOIN sys_role c ON a.roleid = c.roleid "
                + "LEFT JOIN sys_userext d ON a.userid = d.userid "
                + "WHERE b.sysflag = 1 AND d.usertype != 2 ;";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            Double userroleId = (Double) map.get("userroleid");
            Long userId = (Long) map.get("user_new_pk");
            Long roleId = (Long) map.get("role_new_pk");
            if (ObjectUtil.isNull(userId)){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "此用户不存在。");
                excel.add(map);
                continue;
            }
            if (ObjectUtil.isNull(roleId)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "用户所属角色被删除。");
                excel.add(map);
                continue;
            }
            WriterUserRole writerUserRole = new WriterUserRole();
            writerUserRole.setUserId(userId);
            writerUserRole.setRoleId(roleId);
            writerUserRole = writerUserRoleService.addWriterUserRole(writerUserRole);
            count++;
            Long pk = writerUserRole.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "userroleid", userroleId);
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家-角色关联表", "writer_user_role");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("writer_user_role迁移完成");
        logger.info("原数据库表共{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "writer_user_role表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void writerProfile() {
        String sql = "SELECT b.userid,b.new_pk,b.usercode,b.username,c.seniority,"
        		+ "a.userid tag_userid,c.introduce,GROUP_CONCAT(d.tagname SEPARATOR '%') tag_name,"
        		+ "c.usertype FROM sys_usertagmap a LEFT JOIN sys_user b ON a.userid = b.userid "
                + "LEFT JOIN sys_userext c ON b.userid = c.userid "
                + "LEFT JOIN book_tag d ON a.tagid = d.tagid GROUP BY a.userid ;";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            Long userId = (Long) map.get("new_pk");
            if (ObjectUtil.isNull(userId)){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "此用户不存在");
            	excel.add(map);
            	continue;
            }
            Integer usertype = (Integer) map.get("usertype");
            if (ObjectUtil.notNull(usertype) && 2 == usertype.intValue()) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "此用户为机构用户。");
                excel.add(map);
                continue;
            }
            if (userId == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, "此用户为社内用户。");
                excel.add(map);
                continue;
            }
            String profile = (String) map.get("introduce");
            String tagName = (String) map.get("tag_name");
            if (StringUtil.isEmpty(tagName)){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "此标签不存在");
            	excel.add(map);
            }
            WriterProfile writerProfile = new WriterProfile();
            writerProfile.setUserId(userId);
            writerProfile.setProfile(profile);
            writerProfile.setTag(tagName);
            writerProfile = writerProfileService.addWriterProfile(writerProfile);
            count++;
            //此表原系统数据不存在，所以无需反向更新
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "作家标签表", "writer_profile");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("writer_profile表迁移完成");
        logger.info("原数据库表共{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "writer_profile表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void writerUserCertification() {
        String sql = "SELECT a.userid,a.new_pk user_new_pk,d.new_pk org_new_pk,b.handset,b.idcard,"
                + "CASE WHEN b.isteacher = 2 THEN 3 WHEN b.isteacher = 1 THEN 2 WHEN b.isteacher = 0 "
                + "THEN 1 ELSE 0 END progress,e.filedir,e.operdate "
                + "FROM sys_user a LEFT JOIN sys_userext b ON a.userid = b.userid "
                + "LEFT JOIN sys_userorganize c ON a.userid = c.userid "
                + "LEFT JOIN ba_organize d ON c.orgid = d.orgid "
                + "LEFT JOIN (SELECT * FROM pub_addfileinfo y WHERE y.fileid IN (SELECT MAX(p.fileid) "
                + "FROM pub_addfileinfo p WHERE p.childsystemname='sys_userext_teacher' GROUP BY p.operuserid))e "
                + "ON a.userid = e.operuserid "
                + "WHERE a.sysflag=1 AND b.usertype !=2 AND e.filedir IS NOT NULL ;";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : maps) {
            StringBuilder sb = new StringBuilder();
            Long userId = (Long) map.get("user_new_pk");
            if (ObjectUtil.isNull(userId)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("此用户不存在。"));
                excel.add(map);
                continue;
            }
            if (userId == 0){
            	map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("用户为社内用户。"));
                excel.add(map);
                continue;
            }
            Long orgId = (Long) map.get("org_new_pk");
            if (ObjectUtil.isNull(orgId) || orgId == 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("用户没有教师认证单位，可以"
                		+ "通过申报表和专家平台再次确定"));
                excel.add(map);
                continue;
            }
            if (orgId == 0){
            	map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("用户认证单位为社内部门"
            			+ "或认证单位在表里存在重复情况，可以通过申报表及专家平台再次确定"));
                excel.add(map);
                continue;
            }
            String handphone = (String) map.get("handset");
            String idcard = (String) map.get("idcard");
            Long progressNum = (Long) map.get("progress");
            short progress = progressNum.shortValue();
            String cert = (String) map.get("filedir");
            Timestamp gmtCreate = (Timestamp) map.get("operdate");
            WriterUserCertification writerUserCertification = new WriterUserCertification();
            writerUserCertification.setUserId(userId);
            writerUserCertification.setOrgId(orgId);
            writerUserCertification.setHandphone(handphone);
            writerUserCertification.setIdcard(idcard);
            writerUserCertification.setProgress(progress);
            writerUserCertification.setGmtCreate(gmtCreate);
            writerUserCertification = writerCertificationService
                    .addWriterUserCertification(writerUserCertification);
            count++;
            Long pk = writerUserCertification.getId();
            if (StringUtil.notEmpty(cert)) {
                String mongoId = "";
                try {
                    fileService.migrateFile(cert, ImageType.WRITER_USER_CERT, pk);
                } catch (IOException ex) {
                    mongoId = "DEFAULT";
                    map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常。");
                    excel.add(map);
                    logger.error("文件读取异常，路径<{}>,异常信息：{}", cert, ex.getMessage());
                } catch (Exception e) {
                    mongoId = "DEFAULT";
                    map.put(SQLParameters.EXCEL_EX_HEADER, sb.append("未知异常：" + e.getMessage() + "。"));
                    excel.add(map);
                }
                writerUserCertification.setCert(mongoId);
                writerCertificationService.updateWriterUserCertification(writerUserCertification);
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教师资格认证表", "writer_user_certification");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("writer_user_certification");
        logger.info("原数据库表共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "writer_user_certification表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }
}
