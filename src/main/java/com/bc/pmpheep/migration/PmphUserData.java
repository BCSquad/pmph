/**
 * 
 */
package com.bc.pmpheep.migration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.Until;

/**
 * <p>Title:PmphUserData<p>
 * <p>Description:社内用户模块数据迁移类<p>
 * @author lyc
 * @date 2017年10月24日 下午7:40:00
 */
@Component
public class PmphUserData {
	private final Logger logger = LoggerFactory.getLogger(PmphUserData.class);
	@Autowired
	private PmphDepartmentService pmphDepartmentService;
	@Autowired
	private PmphUserService pmphUserService;
	@Autowired
	private FileService fileService;
	@Autowired
	private PmphRoleService pmphRoleService;
	@Autowired
	private PmphUserRoleService pmphUserRoleService;

	/**
	 * 
	 * Description:迁移社内部门表,社内用户模块第一进行
	 * @author:lyc
	 * @date:2017年10月24日下午2:33:07
	 * @param 
	 * @return void
	 */
	public void pmphDepartment() throws Exception {
		String sql = "SELECT orgid,parentid,orgname,sortno,remark FROM ba_organize WHERE orgcode LIKE '15%' ORDER BY orgcode ;";
		List<Object[]> list = Until.getListData(sql);
		int count = 0;
		for (Object[] s : list) {
			PmphDepartment pmphDepartment = new PmphDepartment();
			String departmentId = String.valueOf(s[0]);
			String parentId = String.valueOf(s[1]);
			pmphDepartment.setParentId(0L);
			pmphDepartment.setPath("0-1");
			pmphDepartment.setDpName(String.valueOf(s[2]));
			if (!(null == s[3] || "".equals(String.valueOf(s[3])))){				
				pmphDepartment.setSort(Integer.parseInt(String.valueOf(s[3])));
			}
			pmphDepartment.setNote(String.valueOf(s[4]));
			pmphDepartment = pmphDepartmentService
					.addPmphDepartment(pmphDepartment);
			Until.updateNewPk(departmentId, "ba_organize",
					pmphDepartment.getId());
			if (!"0".equals(parentId)){
			String oldSql = "SELECT NEW_ORGID FROM ba_organize WHERE orgid ='"+ parentId + "';";
			List<Object[]> parentIds = Until.getListData(oldSql);
			pmphDepartment.setParentId(Long.parseLong(String.valueOf(parentIds.get(0)[0])));
			}
			String path = researchParentId(departmentId);
			pmphDepartment.setPath(path.substring(0, path.lastIndexOf("-")));
			pmphDepartmentService.updatePmphDepartment(pmphDepartment);
			count++;
		}
		logger.info("pmph_department表数据迁移完成");
		logger.info("原数据库中共有{}条数据，迁移了{}条数据",list.size(),count);
	}

	/**
	 * 
	 * Description:递归生成新表的path进行更新
	 * 
	 * @author:lyc
	 * @date:2017年10月24日上午9:55:25
	 * @param 主键
	 * @return path
	 */
	public String researchParentId(String id) throws Exception {
		String path = "";
		String sql = "SELECT parentid,NEW_ORGID FROM ba_organize WHERE orgid='"
				+ id + "';";
		List<Object[]> list = Until.getListData(sql);
		for (Object[] s : list) {
			String parentid = String.valueOf(s[0]);
			Long newOrgId = Long.parseLong(String.valueOf(s[1]));
			if (!"0".equals(parentid)) {
				path = researchParentId(parentid) + "-" + newOrgId;
			} else {
				path = "0-" + newOrgId;
			}
		}
		return path;
	}

	/**
	 * 
	 * Description:社内用户迁移，社内部门后第二步进行
	 * @author:lyc
	 * @date:2017年10月24日下午2:32:51
	 * @param 
	 * @return void
	 */
	public void pmphUser() throws Exception{
    	  String sql = "SELECT a.userid,a.usercode,a.`password`,a.isvalid,a.username,d.NEW_ORGID,b.handset,"
                       +"b.email,b.icon,a.memo,a.sortno"
                       +"FROM sys_user a" 
                       +"LEFT JOIN sys_userext b ON a.userid = b.userid " 
					   +"LEFT JOIN sys_userorganize c ON a.userid = c.userid "
					   +"LEFT JOIN ba_organize d ON c.orgid = d.orgid "
					   +"WHERE a.sysflag = 0 ;";
    	  List<Object[] > list = Until.getListData(sql);
    	  int count = 0 ;
    	  for(Object[] s : list){
    		  String userId = String.valueOf(s[0]);
    		  PmphUser pmphUser = new PmphUser();
    		  pmphUser.setUsername(String.valueOf(s[1]));
    		  pmphUser.setPassword(String.valueOf(s[2]));
    		  pmphUser.setIsDisabled(Boolean.parseBoolean(String.valueOf(s[3])));
    		  String realname = String.valueOf(s[4]);
    		  if (null == realname || "".equals(realname)){
    			  pmphUser.setRealname(String.valueOf(s[1]));
    		  }else{
    			  pmphUser.setRealname(realname);
    		  }
    		  pmphUser.setDepartmentId(Long.parseLong(String.valueOf(s[5])));
    		  pmphUser.setHandphone(String.valueOf(s[6]));
    		  pmphUser.setEmail(String.valueOf(s[7]));
    		  pmphUser.setAvatar("头像更新");
              pmphUser.setNote(String.valueOf(s[9]));
              pmphUser.setSort(Integer.parseInt(String.valueOf(s[10])));
              pmphUser = pmphUserService.add(pmphUser);
              Until.updateNewPk(userId, "sys_user", pmphUser.getId());
              String avatarUrl = String.valueOf(s[8]);
//              File file = new File(avatarUrl);
//              FileInputStream input = new FileInputStream(file);
//              MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
//              pmphUser.setAvatar(fileService.save(multipartFile, ImageType.PMPH_USER_AVATAR, pmphUser.getId()));
              pmphUserService.update(pmphUser);
              count++;
    	  }
    	  logger.info("pmph_user表迁移完成");
    	  logger.info("原数据库表共有{}条数据，迁移了{}条数据",list.size(),count);
      }
	
	/**
	 * 
	 * Description:社内用户角色表迁移，第三步进行
	 * @author:lyc
	 * @date:2017年10月24日下午2:42:04
	 * @param 
	 * @return void
	 */
	public void pmphRole() throws Exception{
		String sql = "SELECT roleid,rolename,isvalid,sortno,memo FROM sys_role";
		List<Object[]> list = Until.getListData(sql);
		int count = 0 ;
		for (Object[] s : list){
			String roleId = String.valueOf(s[0]);
			PmphRole pmphRole = new PmphRole();
			pmphRole.setRoleName(String.valueOf(s[1]));
			pmphRole.setIsDisabled(Boolean.parseBoolean(String.valueOf(s[2])));
			pmphRole.setSort(Integer.parseInt(String.valueOf(s[3])));
			pmphRole.setNote(String.valueOf(s[4]));
			pmphRole = pmphRoleService.add(pmphRole);
			Until.updateNewPk(roleId, "sys_role", pmphRole.getId());
			count++;
		}
		logger.info("pmph_role表迁移完成");
		logger.info("原数据库表共有{}条数据，迁移了{}条数据",list.size(),count);
	}
	
	/**
	 * 
	 * Description:用户-角色关联表迁移
	 * @author:lyc
	 * @date:2017年10月24日下午3:32:44
	 * @param 
	 * @return void
	 */
	public void pmphUserRole() throws Exception {
		String sql = "SELECT b.userroleid,a.NEW_USERID,c.NEW_ROLEID FROM sys_user a "
                      +"LEFT JOIN sys_userrole b ON a.userid = b.userid"
                      +"LEFT JOIN sys_role c ON b.roleid = c.roleid"
                      +"WHERE a.sysflag = 0 ;";
		List<Object[]> list = Until.getListData(sql);
		int count = 0 ;
		for (Object[] s : list){
			String userRoleId = String.valueOf(s[0]);
			PmphUserRole pmphUserRole = new PmphUserRole();
			pmphUserRole.setUserId(Long.parseLong(String.valueOf(s[1])));
			pmphUserRole.setRoleId(Long.parseLong(String.valueOf(s[2])));
			pmphUserRole = pmphUserRoleService.addPmphUserRole(pmphUserRole);
			Until.updateNewPk(userRoleId, "sys_userrole", pmphUserRole.getId());
			count++;
		}
		logger.info("pmph_user_role表迁移完成");
		logger.info("原数据库表共有{}条数据，迁移了{}条数据",list.size(),count);
	}
}
