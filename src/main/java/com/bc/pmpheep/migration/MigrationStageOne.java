package com.bc.pmpheep.migration;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 区域迁移工具类
 * <p>
 * Description:区域模块数据迁移类，此为所有迁移工具的第一步<p>
 * @author 陶勇诚
 */
@Component
public class MigrationStageOne {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageOne.class);

    @Resource
    AreaService areaService;
    @Resource
    OrgTypeService orgTypeService;
    @Resource
    OrgService orgService;
    @Resource
    OrgUserService orgUserService;
    @Resource
    FileService fileService;
    @Resource
    WriterUserService writerUserService;
    @Resource
    ExcelHelper excelHelper;

    public void start() {
        area();
        orgType();
        org();
        orgUser();
        writerUser();
    }

    protected void area() {
        String tableName = "ba_areacode"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        List<Map<String,Object>> excel = new LinkedList<>();
        int count = 0;//迁移成功的条目数
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            /* 根据MySQL字段类型进行类型转换 */
            BigDecimal areaId = (BigDecimal) map.get("AreaID");
            BigDecimal parentCode = (BigDecimal) map.get("ParentCode");
            if (null == parentCode){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "父区域编码为空");
            	excel.add(map);
            	logger.error("父区域编码为空，有误，此结果将被记录在Excel中");
            	continue;
            }
            String areaName = map.get("AreaName").toString();
            if (null == areaName){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "区域名称为空");
            	excel.add(map);
            	logger.error("区域名称为空，有误，此结果将被记录在Excel中");
            	continue;
            }
            /* 开始新增新表对象，并设置属性值 */
            Area area = new Area();
            area.setAreaName(areaName);
            long parentPk = 0L;
            /**
             * 该对象默认为根节点，如果不是根节点，则根据parentCode反查父节点的new_pk。 注意此处由于ba_areacode表爷爷-父亲-儿子是按正序排列的，父节点总是已经被插入到新表，所以不需要再次循环。
             */
            if (parentCode.intValue() != 0) {
                parentPk = JdbcHelper.getPrimaryKey(tableName, "AreaID", parentCode);//返回Long型新主键
            }
            area.setParentId(parentPk);
            area = areaService.addArea(area);
            long pk = area.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "AreaID", areaId);//更新旧表中new_pk字段
            count++;
        }
        if(excel.size()>0){
	        try {
				excelHelper.exportFromMaps(excel, tableName, tableName);
			} catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
        }
        logger.info("area表迁移完成");
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
    }

    protected void orgType() {
        String tableName = "ba_organize";
        JdbcHelper.addColumn(tableName);
        String sql = "SELECT orgid,orgname,sortno FROM ba_organize WHERE orgcode NOT LIKE '15%' AND parentid=0 ;";
        List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String,Object>> excel = new LinkedList<>();
        int count = 0 ;
        for (Map<String,Object> map : maps){
        	String orgTypeId = (String) map.get("orgid");
        	String orgName = (String) map.get("orgname");
        	if (null == orgName){
        		map.put(SQLParameters.EXCEL_EX_HEADER, "机构类型名称为空");
        		excel.add(map);
        		logger.error("机构类型名称为空，有误，此结果将被记录在Excel中");
        		continue;
        	}
        	Integer sort = (Integer) map.get("sortno");
        	if (null !=sort && sort<0){
        		map.put(SQLParameters.EXCEL_EX_HEADER, "显示顺序为负数");
        		excel.add(map);
        		logger.error("显示顺序为负数，有误，此结果将被记录在Excel中");
        		continue;
        	}
        	OrgType orgType = new OrgType();
        	orgType.setTypeName(orgName);
        	orgType.setSort(sort);
        	orgType = orgTypeService.addOrgType(orgType);
        	Long pk = orgType.getId();
        	JdbcHelper.updateNewPrimaryKey(tableName, pk, "orgid", orgTypeId);
        	count++;
        }
        if(excel.size()>0){
	        try {
				excelHelper.exportFromMaps(excel, tableName+"机构类型", tableName+"机构类型");
			}  catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
        }
        logger.info("org_type表迁移完成");
        logger.info("原数据库表中共有{}条数据，迁移了{}条数据",maps.size(),count);
    }
    
    protected void org() {
    	String tableName = "ba_organize";
		String sql = "SELECT a.*,b.new_pk FROM ba_organize a "
					+"LEFT JOIN ba_areacode b ON b.AreaID =a.orgprovince " 
					+"WHERE a.orgcode NOT LIKE '15%' AND a.parentid !=0 ORDER BY a.orgcode";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String,Object>> excel = new LinkedList<>();
		int count = 0 ;
		for (Map<String,Object> map : maps){
			String orgId = (String) map.get("orgid");
			String orgName = (String) map.get("orgname");
			if (null == orgName){
				map.put(SQLParameters.EXCEL_EX_HEADER, "机构名称为空");
				excel.add(map);
				logger.error("机构名称为空，有误，此结果将被记录在Excel中");
				continue;
			}
			if (count>0){
				boolean flag = false;
				for(int i = 0 ; i < count;i++){
					if(maps.get(i).get("orgname").equals(orgName)){
						map.put(SQLParameters.EXCEL_EX_HEADER, "机构名称重复，无法插入新表");
						excel.add(map);
						logger.error("机构名称重复，有误，此结果将被记录在Excel中");
						flag = true;
						break;
					}
				}
				if (flag){
					continue;
				}
			}
			Integer orgType = (Integer) map.get("orgtype");
			if (null == orgType){
				map.put(SQLParameters.EXCEL_EX_HEADER, "机构类型id为空");
				excel.add(map);
				logger.error("机构类型id为空，有误，此结果将被记录在Excel中");
				continue;
			}
			Long orgTypeId = Long.valueOf(orgType);
			Long areaId = (Long) map.get("new_pk");
			if (null == areaId){
				map.put(SQLParameters.EXCEL_EX_HEADER, "所在区域id为空");
				excel.add(map);
				logger.error("所在区域id为空，有误，此结果将被记录在Excel中");
				continue;
			}
			String contactPerson = (String) map.get("linker");
			String contactPhone = (String) map.get("linktel");
			Integer sort = (Integer) map.get("sortno");
			if (null !=sort && sort<0){
				map.put(SQLParameters.EXCEL_EX_HEADER, "显示顺序为负数");
				excel.add(map);
				logger.error("显示顺序为负数，有误，此结果将被记录在Excel中");
				continue;
			}
			String note = (String) map.get("remark");
			Integer isDeleted = (Integer) map.get("isdelete");
			if (null == isDeleted){
				map.put(SQLParameters.EXCEL_EX_HEADER, "是否逻辑删除数据为空");
				excel.add(map);
				logger.error("是否逻辑删除数据为空，有误，此结果将被记录在Excel中");
				continue;
			}
			Org org = new Org();
			org.setOrgName(orgName);
			org.setOrgTypeId(orgTypeId);
			org.setAreaId(areaId);
			org.setContactPerson(contactPerson);
			org.setContactPhone(contactPhone);
			org.setSort(sort);
			org.setNote(note);
			org.setIsDeleted(isDeleted == 1);
			org = orgService.addOrg(org);
			Long pk = org.getId();
			JdbcHelper.updateNewPrimaryKey(tableName, pk, "orgid", orgId);
			count++;
		}
		if(excel.size()>0){
			try {
				excelHelper.exportFromMaps(excel, tableName, tableName);
			} catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
		}
		logger.info("org表迁移完成");
		logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
	}
    protected void orgUser() {
    	String tableName = "sys_user";
    	JdbcHelper.addColumn(tableName);
    	JdbcHelper.addColumn("sys_userext");
        String sql = "SELECT a.userid,a.usercode,a.`password`,a.isvalid,d.new_pk,a.username,b.sex,b.duties,"
					+"b.positional,b.fax,b.handset,b.phone,b.idcard,b.email,b.address,b.postcode,"
					+"CASE WHEN e.fileid IS NOT NULL THEN 1 ELSE 0 END is_proxy_upload,e.filedir,e.fileid,"
					+"CASE WHEN b.audittype=0 THEN 0 WHEN 2 THEN 1 WHEN 1 THEN 2 END progress,"
					+"b.auditdate,a.memo,a.sortno "
					+"FROM sys_user a "
					+"LEFT JOIN sys_userext b ON a.userid = b.userid "
					+"LEFT JOIN sys_userorganize c ON a.userid = c.userid "
					+"LEFT JOIN ba_organize d ON c.orgid = d.orgid "
					+"LEFT JOIN pub_addfileinfo e ON a.userid = e.operuserid "
					+"WHERE a.sysflag=1 AND b.usertype=2 "
					+"AND NOT EXISTS (SELECT * FROM pub_addfileinfo p WHERE e.operdate<p.operdate "
					+"AND e.operuserid=p.operuserid) "
					+"ORDER BY a.userid ;";
        List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String,Object>> excel = new LinkedList<>();
        int count = 0 ;
        for (Map<String,Object> map : maps){
        	String userId = map.get("userid").toString();
        	String username = (String) map.get("usercode");
        	if (null == username){
        		map.put(SQLParameters.EXCEL_EX_HEADER, "机构代码为空");
        		excel.add(map);
        		logger.error("机构代码为空，有误，此结果将被记录在Excel中");
        		continue;
        	}
        	String password = (String) map.get("password");
        	if (null == password){
        		map.put(SQLParameters.EXCEL_EX_HEADER, "机构用户登陆密码为空");
        		excel.add(map);
        		logger.error("机构用户登陆密码为空，有误，此结果将被记录在Excel中");
        		continue;
        	}
        	Integer isDisabled = (Integer) map.get("isvalid");
        	Long orgId = (Long) map.get("new_pk");
        	if (null == orgId){
        		map.put(SQLParameters.EXCEL_EX_HEADER, "对应学校id理应不能为空，为可疑问题数据");
        		excel.add(map);
        		logger.info("对应学校id理应不能为空，为可疑问题数据，此结果将被记录在Excel中，"
        				+ "进行二次确认核对");
        	}
        	String realName= (String) map.get("username");
        	if ( null == realName || "".equals(realName)){
        		realName = username;
        	}
        	String sexNum = (String) map.get("sex");
        	Integer sex = null;
        	if (null != sexNum){
        		sex = Integer.parseInt(sexNum);
        	}
        	String position = (String) map.get("duties");
        	String title = (String) map.get("positional");
        	String fax = (String) map.get("fax");
        	String handphone = (String) map.get("handset");
        	String telephone = (String) map.get("phone");
        	String idcard = (String) map.get("idcard");
        	String email = (String) map.get("email");
        	String address = (String) map.get("address");
        	String postcode = (String) map.get("postcode");
        	Integer isProxyUpload = (Integer) map.get("is_proxy_upload");
        	String proxy = (String) map.get("filedir");
        	Integer progress = (Integer) map.get("progress");
        	Timestamp reviewDate = (Timestamp) map.get("auditdate");
        	String note = (String) map.get("memo");
        	Integer sort = (Integer) map.get("sortno");
        	if (null !=sort && sort<0){
        		map.put(SQLParameters.EXCEL_EX_HEADER, "显示顺序为负数");
        		excel.add(map);
        		logger.error("显示顺序为负数，有误，此结果将被记录在Excel中");
        		continue;
        	}
        	OrgUser orgUser = new OrgUser();
        	orgUser.setUsername(username);
        	orgUser.setPassword(password);
        	orgUser.setIsDisabled(isDisabled == 1);
        	orgUser.setOrgId(orgId);
        	orgUser.setRealname(realName);
        	orgUser.setSex(sex);
        	orgUser.setPosition(position);
        	orgUser.setTitle(title);
        	orgUser.setFax(fax);
        	orgUser.setHandphone(handphone);
        	orgUser.setTelephone(telephone);
        	orgUser.setIdcard(idcard);
        	orgUser.setEmail(email);
        	orgUser.setAddress(address);
        	orgUser.setPostcode(postcode);
        	orgUser.setIsProxyUpload(isProxyUpload == 1);
        	orgUser.setProgress(progress);
        	orgUser.setReviewDate(reviewDate);
        	orgUser.setNote(note);
        	orgUser.setSort(sort);
        	if (!username.equals(realName) ){
        		if(null == position || null == title || null == handphone
                		|| null == address || null == postcode || null == email){
        			map.put(SQLParameters.EXCEL_EX_HEADER, "学校有联系人后，提交认证时的资料理应不为空，"
        					+ "为可疑问题数据");
                	excel.add(map);
                	logger.info("学校有联系人后，提交认证时的资料理应不为空，为可疑问题数据,"
                			+ "但仍会导入新表，过后核对和进行二次处理");     
        		}
        	}
        	if (orgUser.getProgress() == 1 && null == orgUser.getReviewDate() 
        			&& null == proxy){
        		map.put(SQLParameters.EXCEL_EX_HEADER, "审核通过，但没有委托书，并且审核时间不为空，"
        				+ "疑为问题数据");
        		excel.add(map);
        		logger.info("审核通过，却没有委托书，并且审核时间不为空，疑为问题数据，"
        				+ "此结果将被记录在Excel中进行二次确认核对");
        	}
        	orgUser = orgUserService.addOrgUser(orgUser);
        	Long pk = orgUser.getId();
        	JdbcHelper.updateNewPrimaryKey(tableName, pk, "userid", userId);
        	JdbcHelper.updateNewPrimaryKey("sys_userext", pk, "userid", userId);
        	count++;
            String mongoId = "";
            if (null != proxy){
            	try {
					mongoId = fileService.migrateFile(proxy, ImageType.ORG_USER_PROXY, pk);
				} catch (IOException ex) {
					logger.error("文件读取异常，路径<{}>,异常信息：{}",proxy,ex.getMessage());
					map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常");
					excel.add(map);
					continue;
				}
            }else{
            	mongoId = proxy;
            }
        	orgUser.setProxy(mongoId);
        	orgUserService.updateOrgUser(orgUser);
        }
        if(excel.size()>0){
        	try {
				excelHelper.exportFromMaps(excel, tableName+"机构用户", tableName+"机构用户");
			}  catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
        }
        logger.info("org_user表迁移完成");
        logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
    }

    protected void writerUser() {
        String tableName = "sys_user";
        String sql = "SELECT a.userid,a.usercode,a.`password`,a.isvalid,d.new_pk org_new_pk,a.username,b.sex,"
					+"b.birthdate,b.seniority,b.duties,b.positional,b.fax,b.handset,b.phone,b.idcard,b.email,"
					+"b.address,b.postcode,"
					+"CASE WHEN b.usertype=4 THEN 1 WHEN b.usertype=1 OR b.usertype=6 THEN 2 "
					+"WHEN b.usertype=5 OR b.usertype=7 THEN 3 ELSE 0 END rank,"
					+"CASE WHEN b.isteacher=2 THEN 1 ELSE 0 END is_teacher,f.filedir,b.teacherauditdate,"
					+"CASE WHEN g.sysflag=0 THEN 1 WHEN g.sysflag=1 THEN 2 END auth_user_type,"
					+"g.new_pk auth_user_id,"
					+"CASE WHEN b.usertype=1 OR b.usertype=6 THEN 1 ELSE 0 END is_writer,"
					+"CASE WHEN b.usertype=5 OR b.usertype=7 THEN 1 ELSE 0 END is_expert,"
					+"e.filedir avatar,b.usersign,a.memo,a.sortno "
					+"FROM sys_user a "
					+"LEFT JOIN sys_userext b ON a.userid = b.userid "
					+"LEFT JOIN sys_userorganize c ON b.userid = c.userid "
					+"LEFT JOIN ba_organize d ON c.orgid = d.orgid "
					+"LEFT JOIN (SELECT * FROM pub_addfileinfo x WHERE x.fileid IN (SELECT MAX(o.fileid) "
					+"FROM pub_addfileinfo o WHERE o.childsystemname='sys_userext_avatar' GROUP BY o.operuserid))e " 
					+"ON a.userid = e.operuserid "
					+"LEFT JOIN (SELECT * FROM pub_addfileinfo y WHERE y.fileid IN (SELECT MAX(p.fileid) "
					+"FROM pub_addfileinfo p WHERE p.childsystemname='sys_userext_teacher' GROUP BY p.operuserid))f "
					+"ON a.userid = f.operuserid "
					+"LEFT JOIN sys_user g ON b.teacheraudituser = g.userid "
					+"WHERE a.sysflag=1 AND b.usertype !=2 ;";
        List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String,Object>> excel = new LinkedList<>();
        int count = 0 ;
        for (Map<String,Object> map : maps){
        	String userId = (String) map.get("userid");
            String username = (String) map.get("usercode");
            if (null == username){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "未找到用户的登陆名");
            	excel.add(map);
            	logger.error("未找到用户的登录名，此结果将被记录在Excel中");
            	continue;
            }
            String password = "888888";
            Integer isDisabled = (Integer) map.get("isvalid");
            Long orgid = (Long) map.get("org_new_pk");
            String realName = (String) map.get("username");
            if (null == realName || "".equals(realName)){
            	realName = username;
            }
            String sexNum = (String) map.get("sex");
            Integer sex = null;
            if (null != sexNum){
            	sex = Integer.parseInt(sexNum);
            }
            Date birthday = (Date) map.get("birthdate");
            String experienceNum = (String) map.get("seniority");
            if ("".equals(experienceNum) || "无".equals(experienceNum) || "、".equals(experienceNum)){
            	experienceNum = null;
            	map.put(SQLParameters.EXCEL_EX_HEADER, "教龄为空字符串或无或顿号，导出Excel进行核准");
            	excel.add(map);
            	logger.info("教龄为空字符串或无或顿号，此结果将被记录在Excel中与专家平台进行核准");
            }
            Integer experience = null;
            if (null != experienceNum ){
            	if (experienceNum.indexOf("年")!= -1){
            		experienceNum = experienceNum.substring(0, experienceNum.indexOf("年"))
            				.replaceAll("五", "5");
            		map.put(SQLParameters.EXCEL_EX_HEADER, "教龄有年等汉字，导出Excel进行核对");
            		excel.add(map);
            		logger.info("教龄有年等汉字，此结果将被记录在Excel中与专家平台进行核准");
            	}
            	if (experienceNum.indexOf("s")!= -1){
            		experienceNum = experienceNum.substring(0, experienceNum.indexOf("s"));
            		map.put(SQLParameters.EXCEL_EX_HEADER, "教龄有英文字母，导出Excel进行核对");
            		excel.add(map);
            		logger.info("教龄有英文字母，此结果将被记录在Excel中与专家平台进行核准");
            	}
            	if (experienceNum.indexOf(" ")!= -1){
            		experienceNum = experienceNum.substring(0, experienceNum.indexOf(" "));
            		map.put(SQLParameters.EXCEL_EX_HEADER, "教龄中包含空格，导出Excel进行核对");
            		excel.add(map);
            		logger.info("教龄中有空格，此结果将被记录在Excel中与专家平台进行核准");
            	}
            	if (experienceNum.indexOf("岁")!= -1){
            		experienceNum = "32";
            		map.put(SQLParameters.EXCEL_EX_HEADER, "教龄中数据疑似为年龄，导出Excel进行核对");
            		excel.add(map);
            		logger.info("教龄中数据疑似为年龄，此结果将被记录在Excel中与专家平台进行核对");
            	}
            	if (experienceNum.indexOf("年")== -1 && experienceNum.indexOf("s")== -1 && 
            			experienceNum.indexOf("岁")== -1 && experienceNum.length()>2){
            		experienceNum = experienceNum.substring(0,experienceNum.length()-1);
            		map.put(SQLParameters.EXCEL_EX_HEADER, "教龄为三位数数字，有误");
            		excel.add(map);
            		logger.info("教龄为三位数数字，有误，此结果将被记录在Excel进行核对");
            	}
            	experience = Integer.parseInt(experienceNum);
            }
            String position = (String) map.get("duties");
            String title = (String) map.get("positional");
            String fax = (String) map.get("fax");
            String handphone = (String) map.get("handset");
            String telephone = (String) map.get("phone");
            String idcard = (String) map.get("idcard");
            String email = (String) map.get("email");
            String address= (String) map.get("address");
            String postcode = (String) map.get("postcode");
            Long rankNum = (Long) map.get("rank");
            Integer rank = null;
            if (null != rankNum){
            	rank = rankNum.intValue();
            }
            String cert = (String) map.get("filedir");
            Timestamp authTime = (Timestamp) map.get("teacherauditdate");
            Long isTeacher = (Long) map.get("is_teacher");
            Long authUserTypeNum = (Long) map.get("auth_user_type");
            Integer authUserType = null;
            if (null != authUserTypeNum){
            	authUserType = authUserTypeNum.intValue();
            }
            Long authUserId = (Long) map.get("auth_user_id");
            Long isWriter = (Long) map.get("is_writer");
            Long isExpert = (Long) map.get("is_expert");
            String avatar = (String) map.get("avatar");
            if (null == avatar || "".endsWith(avatar)){
            	avatar = "/upload/sys_userext_avatar/1706/20170623191553876.png";
            }
            String signature = (String) map.get("usersign");
            String note = (String) map.get("memo");
            Integer sort = (Integer) map.get("sortno");
            if (null !=sort && sort<0){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "显示顺序数据为负数");
            	excel.add(map);
            	logger.error("显示顺序数据为负数，有误，此结果将被记录在Excel中");
            	continue;
            }
            WriterUser writerUser = new WriterUser();
            writerUser.setUsername(username);
            writerUser.setNickname(username);
            writerUser.setPassword(password);
            writerUser.setIsDisabled(isDisabled==1);
            writerUser.setOrgId(orgid);
            writerUser.setRealname(realName);
            writerUser.setSex(sex);
            writerUser.setBirthday(birthday);
            writerUser.setExperience(experience);
            writerUser.setPosition(position);
            writerUser.setTitle(title);
            writerUser.setFax(fax);
            writerUser.setHandphone(handphone);
            writerUser.setTelephone(telephone);
            writerUser.setIdcard(idcard);
            writerUser.setEmail(email);
            writerUser.setAddress(address);
            writerUser.setPostcode(postcode);
            writerUser.setRank(rank);
            writerUser.setIsTeacher(isTeacher == 1);
            writerUser.setAuthTime(authTime);
            writerUser.setAuthUserType(authUserType);
            writerUser.setAuthUserId(authUserId);
            writerUser.setIsWriter(isWriter == 1);
            writerUser.setIsExpert(isExpert == 1);
            writerUser.setAvatar("DEFAULT");
            writerUser.setSignature(signature);
            writerUser.setNote(note);
            writerUser.setSort(sort);
            if (username.equals(realName) || null == sex || null == birthday || null == orgid 
            		|| null == experience || null == position || null == title || null == handphone
            		|| null == address || null == postcode || null == signature){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "字段为前台必填项，查询出字段值为空，"
            			+ "疑为问题数据");
            	excel.add(map);
            	logger.info("字段为前台必填项，查询出字段值为空，疑为问题数据，此结果将被记录在Excel中,"
            			+ "但仍会导入新表，过后核对和进行二次处理");            	
            }
            if (writerUser.getIsTeacher() && null == cert && null == writerUser.getAuthTime()){
            	map.put(SQLParameters.EXCEL_EX_HEADER, "审核通过但教师资格证和认证时间均为空，"
            			+ "疑为问题数据");
            	excel.add(map);
            	logger.info("审核通过但教师资格证和认证时间均为空，疑为问题数据，此结果将被记录在Excel中，"
            			+ "但仍会导入新表，过后核对和进行二次处理");
            }
            writerUser = writerUserService.add(writerUser);
            Long pk = writerUser.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "userid", userId);
        	JdbcHelper.updateNewPrimaryKey("sys_userext", pk, "userid", userId);
        	count++;
        	String certMongoId = "";
        	if(null != cert){
	        	try {
					certMongoId = fileService.migrateFile(cert, ImageType.WRITER_USER_CERT, pk);
				} catch (IOException ex) {
					certMongoId = "DEFAULT";
					logger.error("文件读取异常，路径<{}>,异常信息：{}",cert,ex.getMessage());
					map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常");
					excel.add(map);
				}
        	}else{
        		certMongoId = cert;
        	}
        	writerUser.setCert(certMongoId);
        	String avatarMongoId = "";
        	try {
				avatarMongoId = fileService.migrateFile(avatar, ImageType.WRITER_USER_AVATAR, pk);
			} catch (IOException ex) {
				avatarMongoId = "DEFAULT";
				logger.error("文件读取异常，路径<{}>,异常信息：{}",avatar,ex.getMessage());
				map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常");
				excel.add(map);
			}
        	writerUser.setAvatar(avatarMongoId);
        	writerUser.setPassword(null);
        	writerUserService.update(writerUser);
        }
        if(excel.size()>0){
	        try {
				excelHelper.exportFromMaps(excel, tableName+"作家用户", tableName+"作家用户");
			} catch (IOException ex) {
				logger.error("异常数据导出到Excel失败",ex);
			}
        }
        logger.info("writer_user表迁移完成");
        logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
    }

}
