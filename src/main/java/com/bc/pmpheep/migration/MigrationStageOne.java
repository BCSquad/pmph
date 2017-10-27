package com.bc.pmpheep.migration;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;

import java.io.File;
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
    

    public void start() {
        area();
        orgType();
        org();
        orgUser();
        WriterUser();
    }

    protected void area() {
        String tableName = "ba_areacode"; //要迁移的旧库表名
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        int count = 0;//迁移成功的条目数
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
            /* 根据MySQL字段类型进行类型转换 */
            BigDecimal areaId = (BigDecimal) map.get("AreaID");
            BigDecimal parentCode = (BigDecimal) map.get("ParentCode");
            String areaName = map.get("AreaName").toString();
            /* 开始新增新表对象，并设置属性值 */
            Area area = new Area();
            area.setAreaName(areaName);
            long parentPk = 0L;
            /**
             * 该对象默认为根节点，如果不是根节点，则根据parentCode反查父节点的new_pk。 注意此处由于ba_areacode表爷爷-父亲-儿子是按正序排列的，父节点总是已经被插入到新表，所以不需要再次循环。
             */
            if (parentCode.intValue() != 0) {
                parentPk = JdbcHelper.getParentPrimaryKey(tableName, "AreaID", parentCode);//返回Long型新主键
            }
            area.setParentId(parentPk);
            area = areaService.addArea(area);
            long pk = area.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "AreaID", areaId);//更新旧表中new_pk字段
            count++;
        }
        logger.info("area表迁移完成");
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
    }

    protected void orgType() {
        String tableName = "ba_organize";
        String sql = "SELECT orgid,orgname,sortno FROM ba_organize WHERE orgcode NOT LIKE '15%' AND parentid=0 ;";
        List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0 ;
        for (Map<String,Object> map : maps){
        	String orgTypeId = map.get("orgid").toString();
        	String orgName = map.get("orgname").toString();
        	Integer sort = (Integer) map.get("sortno");
        	OrgType orgType = new OrgType();
        	orgType.setTypeName(orgName);
        	orgType.setSort(sort);
        	orgType = orgTypeService.addOrgType(orgType);
        	Long pk = orgType.getId();
        	JdbcHelper.updateNewPrimaryKey(tableName, pk, "orgid", orgTypeId);
        	count++;
        }
        logger.info("org_type表迁移完成");
        logger.info("原数据库表中共有{}条数据，迁移了{}条数据",maps.size(),count);
    }
    
    protected void org() {
    	String tableName = "ba_organize";
		String sql = "SELECT b.new_pk, a.* FROM ba_organize a "
					+"LEFT JOIN ba_areacode b ON b.AreaID =a.orgprovince " 
					+"WHERE a.orgcode NOT LIKE '15%' AND a.parentid !=0 ORDER BY a.orgcode";
		List<Map<String,Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		int count = 0 ;
		for (Map<String,Object> map : maps){
			String orgId = map.get("orgid").toString();
			Org org = new Org();
			org.setParentId(0L);
			org.setOrgName(map.get("orgname").toString());
			org.setOrgTypeId((Long) map.get("orgtype"));
			org.setAreaId((Long) map.get("new_pk"));
			org.setContactPerson(map.get("linker").toString());
			org.setContactPhone(map.get("linktel").toString());
			org.setSort((Integer) map.get("sortno"));
			org.setNote(map.get("remark").toString());
			org.setIsDeleted((Boolean) map.get("isdelete"));
			org = orgService.addOrg(org);
			Long pk = org.getId();
			JdbcHelper.updateNewPrimaryKey(tableName, pk, "orgid", orgId);
			count++;
		}
		logger.info("org表迁移完成");
		logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
	}
    protected void orgUser() {
    	String tableName = "sys_user";
        String sql = "SELECT a.userid,a.usercode,a.`password`,a.isvalid,d.new_pk,a.username,b.sex,b.duties,"
					+"b.positional,b.fax,b.handset,b.phone,b.idcard,b.email,b.address,b.postcode,"
					+"CASE WHEN e.fileid IS NOT NULL THEN 1 ELSE 0 END is_proxy_upload,e.filedir,e.fileid"
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
        int count = 0 ;
        for (Map<String,Object> map : maps){
        	String userId = map.get("userid").toString();
        	OrgUser orgUser = new OrgUser();
        	orgUser.setUsername((String) map.get("usercode"));
        	orgUser.setPassword((String) map.get("password"));
        	orgUser.setIsDisabled((Boolean) map.get("isvalid"));
        	orgUser.setOrgId((Long) map.get("new_pk"));
        	if ( null ==map.get("username") || "".equals(map.get("username").toString())){
        		orgUser.setRealname((String) map.get("usercode"));
        	}else{
        		orgUser.setRealname((String) map.get("username"));
        	}
        	orgUser.setSex((Integer) map.get("sex"));
        	orgUser.setPosition((String) map.get("duties"));
        	orgUser.setTitle((String) map.get("positional"));
        	orgUser.setFax((String) map.get("fax"));
        	orgUser.setHandphone((String) map.get("handset"));
        	orgUser.setTelephone((String) map.get("phone"));
        	orgUser.setIdcard((String) map.get("idcard"));
        	orgUser.setEmail((String) map.get("email"));
        	orgUser.setAddress((String) map.get("address"));
        	orgUser.setPostcode((String) map.get("postcode"));
        	orgUser.setIsProxyUpload((Boolean) map.get("is_proxy_upload"));
        	orgUser.setProgress((Integer) map.get("progress"));
        	orgUser.setReviewDate((Date) map.get("auditdate"));
        	orgUser.setNote((String) map.get("memo"));
        	orgUser.setSort((Integer) map.get("sortno"));
        	orgUser = orgUserService.addOrgUser(orgUser);
        	Long pk = orgUser.getId();
        	JdbcHelper.updateNewPrimaryKey(tableName, pk, "userid", userId);
        	File file = new File((String) map.get("filedir"));
        	try {
				String fileId = fileService.saveLocalFile(file, FileType.PROXY, pk);
				orgUser.setProxy(fileId);
			} catch (IOException e) {
				logger.info(e.getMessage());
				logger.warn("执行插入MongoDB程序出错，可能老数据库表pub_addfileinfo主键为{}文件找不到",
						map.get("fileid"));
			}
        	orgUserService.updateOrgUser(orgUser);
        	count++;
        }
        logger.info("org_user表迁移完成");
        logger.info("原数据库表共有{}条数据，迁移了{}条数据",maps.size(),count);
    }

    protected void WriterUser() {
        //todo
    }

}
