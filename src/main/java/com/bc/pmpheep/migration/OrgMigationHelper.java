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

/**
 * 机构数据迁移工具类
 * 
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

	public void Org() {
		String sql = "SELECT * FROM ba_organize";
		List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		orgIdMap = new HashMap(maps.size());// 初始化
		int count = 0;// 迁移成功的条目数
		for (Map<String, Object> map : maps) {
			Org org = new Org();
			// 上级机构id
			if (null != map.get("parentid")) {
				String parentid = map.get("parentid").toString();
				long parentID = Long.valueOf(parentid);
				org.setParentId(parentID);
			}
			// 机构名称
			String orgname = map.get("orgname").toString();
			org.setOrgName(orgname);
			// 机构类型id
			org.setOrgTypeId(0L);// 有中间表，建立新库org_type表的映射关系
			// 所在区域id
			if (null != map.get("orgprovince")) {
				String orgprovince = map.get("orgprovince").toString();
				long areaid = Long.valueOf(orgprovince);
				org.setAreaId(areaid);
			}
			// 联系人
			if (null != map.get("linker")) {
				String linker = map.get("linker").toString();
				org.setContactPerson(linker);
			}
			// 联系电话
			if (null != map.get("linktel")) {
				String linktel = map.get("linktel").toString();
				org.setContactPhone(linktel);
			}
			// 显示顺序
			if (null != map.get("sortno")) {
				int sortno = (int) map.get("sortno");
				org.setSort(sortno);
			}
			// 备注
			if (null != map.get("remark")) {
				String remark = map.get("remark").toString();
				org.setNote(remark);
			}
			// 是否被逻辑删除
			if (null != map.get("isdelete")) {
				int isdelete = (Integer) map.get("isdelete");
				org.setIsDeleted(isdelete == 0);
			}
			// 主键
			org = orgService.addOrg(org);
			String orgid = map.get("orgid").toString();
			orgIdMap.put(orgid, org.getId());
			count++;
		}
		logger.info("ba_organize表迁移完成！");
		logger.info("旧库中共 {} 条数据，迁移完成 {} 条", maps.size(), count);

	}
}
