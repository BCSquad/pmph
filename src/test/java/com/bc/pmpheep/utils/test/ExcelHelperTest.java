package com.bc.pmpheep.utils.test;

import com.bc.pmpheep.back.bo.DecPositionBO;
import com.bc.pmpheep.back.bo.DeclarationBO;
import com.bc.pmpheep.back.bo.WriterBO;
import com.bc.pmpheep.back.service.DecTeachExpService;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialExtensionService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.service.exception.CheckedServiceException;
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
import org.junit.Ignore;
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
    @Resource
    MaterialService materialService;
    @Resource
    MaterialExtensionService materialExtensionService;
    @Resource
    DeclarationService declarationService;
    @Resource
    DecTeachExpService decTeachExpService;

    @Test
    @Ignore
    public void fromDecPositionBOList() throws IOException, SecurityException, IllegalArgumentException, IllegalAccessException {
        List<DecPositionBO> list = makeDecPositionBOList();
        Workbook workbook = excelHelper.fromDecPositionBOList(list, "遴选表");
        String path = this.getClass().getResource("/").getPath().concat("position.xls");
        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);
        out.flush();
        out.close();
    }

    @Test
    @Ignore
    public void fromBusinessObjectList() throws IOException, SecurityException, IllegalArgumentException, IllegalAccessException {
        List<DeclarationBO> list = new ArrayList();
        DeclarationBO declarationBO = new DeclarationBO();
        declarationBO.setRealname("John");
        list.add(declarationBO);
        declarationBO = new DeclarationBO();
        declarationBO.setUsername("Smith");
        list.add(declarationBO);
        declarationBO = new DeclarationBO();
        declarationBO.setTextbookName("人体解剖学与组织胚胎学");
        list.add(declarationBO);
        declarationBO = new DeclarationBO();
        declarationBO.setPresetPosition("副主编");
        list.add(declarationBO);
        Workbook workbook = excelHelper.fromBusinessObjectList(list, "专家信息表");
        String path = this.getClass().getResource("/").getPath().concat("book.xls");
        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);
        out.flush();
        out.close();
    }

    @Test
//    @Ignore
    public void fromDeclarationEtcBOList() throws CheckedServiceException, FileNotFoundException, IOException,
            IllegalArgumentException, IllegalAccessException {
        Workbook workbook = excelHelper.fromDeclarationEtcBOList(materialService.getMaterialById(2L),
                materialExtensionService.getMaterialExtensionByMaterialId(2L),
                declarationService.declarationEtcBO(2L,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null),
                "专家信息表");
        String path = this.getClass().getResource("/").getPath().concat("DeclarationEtcBOList.xls");
        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);
        out.flush();
        out.close();
    }

    @Test
    @Ignore
    public void fromMaps() throws FileNotFoundException, IOException {
        String sql = "SELECT * FROM bbs_groupusers";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> map : maps) {
            map.put("exception", "数据异常原因");
        }
        Workbook workbook = excelHelper.fromMaps(maps, "bbs_groupusers");
        String path = this.getClass().getResource("/").getPath().concat("bbs_groupusers.xls");
        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);
        out.flush();
        out.close();
    }

    @Test
    @Ignore
    public void exportFromMaps() throws IOException {
        String sql = "SELECT * FROM bbs_group";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> map : maps) {
            map.put("exception", "数据异常原因");
        }
        excelHelper.exportFromMaps(maps, "bbs_group", null);
    }

    private List<DecPositionBO> makeDecPositionBOList() {
        List<DecPositionBO> list = new ArrayList<>(3);
        for (int i = 1; i < 4; i++) {
            DecPositionBO bo = new DecPositionBO();
            bo.setSort(i);
            bo.setTextbookName("书" + i);
            bo.setTextbookRound(i + 5);
            List<WriterBO> writers = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                WriterBO writer = new WriterBO();
                writer.setRealname("随机姓名" + j);
                writer.setChosenOrgName("随机学校" + j);
                switch (j % 4) {
                    case 0:
                        writer.setChosenPosition(1);
                        break;
                    case 1:
                        writer.setChosenPosition(2);
                        break;
                    case 2:
                        writer.setChosenPosition(4);
                        break;
                    case 3:
                        writer.setChosenPosition(8);
                        break;
                }
                writer.setRank(j + 1);
                writers.add(writer);
            }
            bo.setWriters(writers.toString());
            list.add(bo);
        }
        return list;
    }
}
