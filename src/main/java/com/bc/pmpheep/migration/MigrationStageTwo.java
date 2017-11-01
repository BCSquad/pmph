/**
 * 
 */
package com.bc.pmpheep.migration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.service.PmphDepartmentService;
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

	private Logger logger = LoggerFactory.getLogger(MigrationStageTwo.class);
	
	@Resource
	ExcelHelper excelHelper;
	@Resource
	PmphDepartmentService pmphDepartmentService;
	
	public void start(){
		pmphDepartment();
	}
	
	protected void pmphDepartment() {
		String tableName = "ba_organize";
		String sql = "SELECT orgid,parentid,orgname,sortno,remark FROM ba_organize WHERE orgcode LIKE '15%' ORDER BY orgcode ;";
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
            pmphDepartment = pmphDepartmentService.addPmphDepartment(pmphDepartment);
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
	}
}
