package com.bc.pmpheep.migration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.util.DesRun;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 增加测试数据的类 
 * @author MrYang
 *
 */
@Component
public class AddTestUser {
	
	@Autowired
	private PmphUserService pmphUserService;
	
	@Autowired
	private PmphDepartmentService pmphDepartmentService;
	
	@Autowired
	private  PmphRoleService pmphRoleService;
	
	@Autowired
	private   PmphUserRoleService pmphUserRoleService;
	
	@Autowired
	private   OrgService orgService;
	
	@Autowired
	private   AreaService areaService;
	
	@Autowired
	private   OrgTypeService orgTypeService;
	
	@Autowired
	private   WriterUserService writerUserService;
	
	@Autowired
	private   OrgUserService orgUserService;
	
	public void addTestUser() {
		String pmphUserData = 
			  "["+
			  "{'username':'yewutest01','realname':'业务测试账号A','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'主任'}]},"+
			  "{'username':'yewutest02','realname':'业务测试账号B','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'系统管理员'},{'roleName':'项目编辑'}]},"+
			  "{'username':'yewutest03','realname':'业务测试账号C','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'项目编辑'}]},"+
			  "{'username':'yewutest04','realname':'业务测试账号D','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'项目编辑'}]},"+
			  "{'username':'yewutest05','realname':'业务测试账号E','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'项目编辑'}]},"+
			  "{'username':'yewutest06','realname':'业务测试账号F','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'项目编辑'}]},"+
			  "{'username':'yewutest07','realname':'业务测试账号J','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'主任'}]},"+
			  "{'username':'yewutest08','realname':'业务测试账号H','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[]},"+
			  "{'username':'yewutest09','realname':'业务测试账号I','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'数字编辑'}]},"+
			  "{'username':'yewutest10','realname':'业务测试账号J','password':'888888','department':'人民卫生出版社','avatar':'DEFAULT','roles':[{'roleName':'数字编辑'},{'roleName':'其他用户'}]}"+
              "]" ;
		 Gson gson = new Gson();
         List<TestPmphUserBo> testPmphUsers = gson.fromJson(pmphUserData,new TypeToken<ArrayList<TestPmphUserBo>>(){ }.getType());
         for(TestPmphUserBo user: testPmphUsers) {
        	 PmphDepartment pmphDepartment= pmphDepartmentService.getPmphDepartmentByName(user.getDepartment());
        	 PmphUser pmphUser = new PmphUser();
        	 pmphUser.setUsername(user.getUsername());
        	 pmphUser.setRealname(user.getRealname());
        	 pmphUser.setPassword(user.getPassword());
        	 pmphUser.setDepartmentId(pmphDepartment == null ? 0L : pmphDepartment.getId());
        	 pmphUser.setAvatar(user.getAvatar());
        	 pmphUser = pmphUserService.add(pmphUser);
        	 for(PmphRole pmphRole :user.getRoles()) {
        		 pmphRole = pmphRoleService.getByName(pmphRole.getRoleName());
        		 PmphUserRole pmphUserRole = new PmphUserRole();
        		 pmphUserRole.setUserId(pmphUser.getId());
        		 pmphUserRole.setRoleId(pmphRole == null ? 0L : pmphRole.getId());
        		 pmphUserRoleService.addPmphUserRole(pmphUserRole);
        	 }
         }
         String orgData = 
        		  "["+
    				  "{'orgName':'北京测试学校','orgType':'本科','areaName':'北京市','username':'bjcsxxtest','password':'123456','realname':'北京学校管理员','avatar':'DEFAULT',"+
		      		  		"writerUsers:["+
		      		  			"{'username':'bjzuojia01','password':'888888','nickname':'北京作家01','realname':'北京作家01'},"+
		      		  			"{'username':'bjzuojia02','password':'888888','nickname':'北京作家02','realname':'北京作家02'},"+
		      		  			"{'username':'bjzuojia03','password':'888888','nickname':'北京作家03','realname':'北京作家03'},"+
		      		  			"{'username':'bjzuojia04','password':'888888','nickname':'北京作家04','realname':'北京作家04'},"+
		      		  			"{'username':'bjzuojia05','password':'888888','nickname':'北京作家05','realname':'北京作家05'},"+
					      		"{'username':'bjzuojia06','password':'888888','nickname':'北京作家06','realname':'北京作家06'},"+
					      		"{'username':'bjzuojia07','password':'888888','nickname':'北京作家07','realname':'北京作家07'},"+
					      		"{'username':'bjzuojia08','password':'888888','nickname':'北京作家08','realname':'北京作家08'},"+
					      		"{'username':'bjzuojia09','password':'888888','nickname':'北京作家09','realname':'北京作家09'},"+
					      		"{'username':'bjzuojia10','password':'888888','nickname':'北京作家10','realname':'北京作家10'},"+
					      		"{'username':'bjzuojia11','password':'888888','nickname':'北京作家11','realname':'北京作家11'}"+
		      		  		"]"+
		      		  "},"+
	        		  "{'orgName':'天津测试学校','orgType':'本科','areaName':'天津市','username':'tjcsxxtest','password':'123456','realname':'天津学校管理员','avatar':'DEFAULT',"+
		      		  		"writerUsers:["+
		      		  			"{'username':'tjzuojia01','password':'888888','nickname':'天津作家01','realname':'天津作家01'}"+
		      		  		"]"+
		      		  "}"+
				  "]" ;
         List<TestOrgBo> orgBos = gson.fromJson(orgData,new TypeToken<ArrayList<TestOrgBo>>(){ }.getType());
         for(TestOrgBo testOrgBo:orgBos) {
        	 Org org = new Org();
        	 org.setParentId(0L);
        	 org.setOrgName(testOrgBo.getOrgName());
        	 org.setOrgTypeId(orgTypeService.listOrgTypeByTypeName(testOrgBo.getOrgType()).get(0).getId());
        	 org.setAreaId(areaService.getAreaIdByName(testOrgBo.getAreaName()));
        	 org = orgService.addOrg(org);
        	 OrgUser orgUser = new OrgUser();
        	 orgUser.setUsername(testOrgBo.getUsername());
        	 orgUser.setPassword(new DesRun("",testOrgBo.getPassword()).enpsw );
        	 orgUser.setOrgId(org.getId());
        	 orgUser.setRealname(testOrgBo.getRealname());
        	 orgUser.setAvatar(testOrgBo.getAreaName());
        	 orgUser = orgUserService.addOrgUser(orgUser);
        	 for(WriterUser writerUser:testOrgBo.getWriterUsers()) {
        		 writerUser.setOrgId(org.getId());
            	 writerUserService.add(writerUser);
        	 }
         }
	}
	class TestOrgBo{
		//机构名称
		private String orgName ;
		//机构类型名称
		private String orgType ;
		//区域名称
		private String areaName;
		//用户名
		private String username;
		//密码
		private String password;
		//管理员姓名
		private String realname;
		//头像
		private String avatar;
		//作家用户集
		List<WriterUser>  writerUsers ;
		
		
		public String getOrgName() {
			return this.orgName  ;
		}
		public void setOrgName (String orgName) {
			this.orgName = orgName;
		}
		public String getOrgType() {
			return this.orgType;
		}
		public void setOrgType(String orgType) {
			this.orgType = orgType;
		}
		public String getAreaName() {
			return this.areaName;
		}
		public void setAreaName (String areaName) {
			this.areaName = areaName;
		}
		public String getUsername() {
			return this.username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return this.password;
		}
		public void setPassword(String password) {
			this.password= password;
		}
		public String getRealname() {
			return this.realname;
		}
		public void setRealname(String realname) {
			this.realname= realname;
		}
		
		public String getAvatar() {
			return this.avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar ;
		}
		public List<WriterUser> getWriterUsers(){
			return this.writerUsers;
		}
		public void setWriterUsers(List<WriterUser>  writerUsers) {
			this.writerUsers =writerUsers;
		}
		@Override
		public String toString() {
			return "{orgName:" + orgName + ",orgType:" + orgType + ",areaName:"
					+ areaName + ",username:" + username + ",password:"
					+ password + ",realname:" + realname + ",avatar:" + avatar
					+ ",writerUsers:" + writerUsers + "}";
		}
	}
	
	class TestPmphUserBo{
		//用户名
		private String username;
		//真实姓名
		private String realname;
		//密码
		private String password;
		//部门名称
		private String department;
		//头像
		private String avatar;
		//角色名称
		List<PmphRole> roles;
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getRealname() {
			return realname;
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public List<PmphRole> getRoles() {
			return roles;
		}
		public void setRoles(List<PmphRole> roles) {
			this.roles = roles;
		}
		@Override
		public String toString() {
			return "TestPmphUserBo [username=" + username + ", realname=" + realname + ", password=" + password
					+ ", department=" + department + ", avatar=" + avatar + ", roles=" + roles + "]";
		}
		
	}
}
























