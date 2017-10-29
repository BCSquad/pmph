package com.bc.pmpheep.migration;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.migration.common.JdbcHelper;
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

    public void start() {
        area();
        orgType();
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
                parentPk = JdbcHelper.getPrimaryKey(tableName, "AreaID", parentCode);//返回Long型新主键
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
        //todo
    }

    protected void orgUser() {
        //todo
    }

    protected void WriterUser() {
        //todo
    }
}
