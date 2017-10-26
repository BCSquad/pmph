package com.bc.pmpheep.migration;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.migration.common.Until;

/**
 * 区域迁移工具类
 * <p>Description:区域模块数据迁移类，此为所有迁移工具的第一步<p>
 * @author 陶勇诚
 */
@Component
public class AreaMigrationHelper {

    private final Logger logger = LoggerFactory.getLogger(AreaMigrationHelper.class);

    @Resource
    AreaService areaService;

    public void area() throws Exception{
        String sql = "SELECT * FROM ba_areacode";
        List<Object[]> list = Until.getListData(sql);
        int count = 0 ;
        for (Object[] s : list){
        	String areaId = String.valueOf(s[0]);
        	String parentId = String.valueOf(s[2]);
        	Area area = new Area();
        	area.setAreaName(String.valueOf(s[3]));
        	area = areaService.addArea(area);
        	Until.updateNewPk(areaId, "ba_areacode", area.getId());
        	if (!"0".equals(area.getParentId())){
        		String oldSql = "SELECT NEW_AREAID FROM ba_areacode WHERE AreaID = '"+ parentId +"';";
        		List<Object[]> parentIds = Until.getListData(oldSql);
        		area.setParentId(Long.parseLong(String.valueOf(parentIds.get(0)[0])));
        	}
        	areaService.updateArea(area);
        	count++;
        }
        logger.info("area表迁移完成");
        logger.info("原数据库中共有{}条数据，迁移了{}条数据",list.size(),count);
    }

}
