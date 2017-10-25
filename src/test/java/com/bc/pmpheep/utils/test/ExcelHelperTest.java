package com.bc.pmpheep.utils.test;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.utils.ExcelHelper;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel工具测试类
 *
 * @author L.X <gugia@qq.com>
 */
public class ExcelHelperTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(ExcelHelperTest.class);

    @Resource
    ExcelHelper excelHelper;

    @Test
    public void fromPersistentObject() throws IOException, SecurityException, IllegalArgumentException, IllegalAccessException {
        List<Area> list = new ArrayList();
        Area area = new Area(1L, "北京", 999);
        list.add(area);
        area = new Area(2L, "天津", 999);
        list.add(area);
        area = new Area(3L, "上海", 999);
        list.add(area);
        area = new Area(4L, "重庆", 999);
        list.add(area);
        Workbook workbook = excelHelper.fromPersistentObject(list);
        FileOutputStream out = new FileOutputStream("book.xls");
        workbook.write(out);
        out.flush();
        out.close();
    }

    @Test
    public void fromMaps() throws FileNotFoundException, IOException {
        String sql = "SELECT * FROM bbs_groupusers";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> map : maps) {
            map.put("exception", "数据异常原因");
        }
        Workbook workbook = excelHelper.fromMaps(maps, "bbs_groupusers");
        FileOutputStream out = new FileOutputStream("bbs_groupusers.xls");
        workbook.write(out);
        out.flush();
        out.close();
    }
}
