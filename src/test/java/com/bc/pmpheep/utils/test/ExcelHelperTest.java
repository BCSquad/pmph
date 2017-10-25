package com.bc.pmpheep.utils.test;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.utils.ExcelHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    public void test() throws IOException, SecurityException, IllegalArgumentException, IllegalAccessException {
        List<Area> list = new ArrayList();
        Area area = new Area(1L, "北京", 999);
        list.add(area);
        area = new Area(2L, "天津", 999);
        list.add(area);
        area = new Area(3L, "上海", 999);
        list.add(area);
        area = new Area(4L, "重庆", 999);
        list.add(area);
        Workbook workbook = excelHelper.getWorkbook(list);
        FileOutputStream out = new FileOutputStream("book.xls");
        workbook.write(out);
        out.flush();
        out.close();
    }
}
