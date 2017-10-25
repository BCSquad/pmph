package com.bc.pmpheep.migration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.Until;

/**
 * 机构数据迁移工具类
 * <p>Description:机构模块数据迁移类<p>
 * @author tyc
 *
 */
@Component
public class OrgMigationHelper {

	private final Logger logger = LoggerFactory.getLogger(OrgMigationHelper.class);
	Map<String, Long> orgIdMap;

	@Resource
	OrgService orgService;
	@Resource
	OrgTypeService orgTypeService;
	@Resource
	OrgUserService orgUserService;
    
	/**
	 * 
	 * Description:机构类型表，此为机构模块数据迁移的第一步
	 * @author:lyc
	 * @date:2017年10月25日下午3:14:26
	 * @param 
	 * @return void
	 */
	public void OrgType() throws Exception{
		String sql = "SELECT orgid,orgname,sortno FROM ba_organize WHERE orgcode NOT LIKE '15%' AND parentid=0";
		List<Object[]> list = Until.getListData(sql);
		int count = 0 ;
		for (Object[] s : list){
			String orgTypeId = String.valueOf(s[0]);
			OrgType orgType = new OrgType();
			orgType.setTypeName(String.valueOf(s[1]));
            if (!(null == s[2] || "".equals(String.valueOf(s[2])))){
            	orgType.setSort(Integer.parseInt(String.valueOf(s[2])));
            }
            orgType = orgTypeService.addOrgType(orgType);
            Until.updateNewPk(orgTypeId, "org_type", orgType.getId());
            count++;
		}
		logger.info("org_type表迁移完成");
		logger.info("原数据库表共有{}条数据，迁移了{}条数据",list.size(),count);
	}
	
	/**
	 * 
	 * Description:机构信息表，此为机构模块数据迁移第二步
	 * @author:lyc
	 * @date:2017年10月25日下午3:15:02
	 * @param 
	 * @return void
	 */
	public void Org() throws Exception{
		String sql = "SELECT b.NEW_AREAID, a.* FROM ba_organize a "
					+"LEFT JOIN ba_areacode b ON b.AreaID =a.orgprovince " 
					+"WHERE a.orgcode NOT LIKE '15%' AND a.parentid !=0 ORDER BY a.orgcode";
		List<Object[]> list = Until.getListData(sql);
		int count = 0 ;
		for (Object[] s : list){
			String orgId = String.valueOf(s[1]);
			Org org = new Org();
			org.setParentId(0L);
			org.setOrgName(String.valueOf(s[3]));
			org.setOrgTypeId(Long.parseLong(String.valueOf(s[4])));
			if (!(null==s[0] || "".equals(String.valueOf(s[0])))){
				org.setAreaId(Long.parseLong(String.valueOf(s[0])));
			}
			if (!(null==s[8] || "".equals(String.valueOf(s[8])))){
				org.setContactPerson(String.valueOf(s[8]));
			}
			if (!(null==s[9] || "".equals(String.valueOf(s[9])))){
				org.setContactPhone(String.valueOf(s[9]));
			}
			if (!(null==s[13] || "".equals(String.valueOf(s[13])))){
				org.setSort(Integer.parseInt(String.valueOf(s[13])));
			}
			if (!(null==s[15] || "".equals(String.valueOf(s[15])))){
				org.setNote(String.valueOf(s[15]));
			}
			if (!(null==s[16] || "".equals(String.valueOf(s[16])))){
				org.setIsDeleted(Boolean.parseBoolean(String.valueOf(s[16])));
			}
			org = orgService.addOrg(org);
			Until.updateNewPk(orgId, "ba_organize", org.getId());
			count++;
		}
          logger.info("org表前已完成");
          logger.info("原数据库表共有{}条数据，迁移了{}条数据",list.size(),count);
	}
}
