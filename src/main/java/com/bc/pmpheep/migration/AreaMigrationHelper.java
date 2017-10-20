package com.bc.pmpheep.migration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.service.AreaService;

/**
 * 区域迁移工具类
 *
 * @author 陶勇诚
 */
@Component
public class AreaMigrationHelper {

    private final Logger logger = LoggerFactory.getLogger(AreaMigrationHelper.class);
    private Map<String, Long> areaIdMap; //area与ba_areacode的主键关系映射

    @Resource
    AreaService areaService;

    public void area() {
        String sql = "SELECT * FROM ba_areacode";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        areaIdMap = new HashMap(maps.size());//初始化
        int count = 0;//迁移成功的条目数
        for (Map<String, Object> map : maps) {
            Area area = new Area();
            //获取旧表主键进行转换
            String areaId = map.get("areaId").toString();
            area.setId(Long.valueOf(areaId));
            //获取旧表父级区域编码进行转换后设置
            String parentCode = map.get("parentCode").toString();
            area.setParentId(Long.valueOf(parentCode));
            //直接转换区域名称
            area.setAreaName((String) map.get("areaName"));
            //直接转换顺序
            area.setSort((Integer) map.get("level"));
            areaService.addArea(area);
            getAreaIdMap().put(areaId, area.getId());
            count++;
        }
        logger.info("area表迁移完成！");
        logger.info("旧库中共 {} 条数据，迁移完成 {} 条", maps.size(), count);
    }

    /**
     * @return the areaIdMap
     */
    public Map<String, Long> getAreaIdMap() {
        return areaIdMap;
    }
}
