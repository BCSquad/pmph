package com.bc.pmpheep.utils.test;

import com.bc.pmpheep.back.bo.DeclarationBO;
import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.service.DecTeachExpService;
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
    DecTeachExpService decTeachExpService;

    @Test
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
        FileOutputStream out = new FileOutputStream("book.xls");
        workbook.write(out);
        out.flush();
        out.close();
    }

    @Test
    public void fromDeclarationEtcBOList() throws CheckedServiceException, FileNotFoundException, IOException,
            IllegalArgumentException, IllegalAccessException {
        List<DeclarationEtcBO> list = new ArrayList();
        DeclarationEtcBO declarationEtcBO = new DeclarationEtcBO();
        declarationEtcBO.setRealname("John");
        list.add(declarationEtcBO);
        declarationEtcBO = new DeclarationEtcBO();
        declarationEtcBO.setUsername("Smith");
        ArrayList<DecEduExp> exps = new ArrayList<>(3);
        DecEduExp exp = new DecEduExp();
        exp.setDateBegin("1998-07-01");
        exp.setDateEnd("2003-12-01");
        exp.setDegree("本科");
        exp.setMajor("临床医学");
        exp.setSchoolName("首都医科大学");
        exps.add(exp);
        exp = new DecEduExp();
        exp.setDateBegin("2004-07-01");
        exp.setDateEnd("2007-12-01");
        exp.setDegree("硕士");
        exp.setMajor("临床医学");
        exp.setSchoolName("首都医科大学");
        exps.add(exp);
        exp = new DecEduExp();
        exp.setDateBegin("2008-07-01");
        exp.setDateEnd("2012-12-01");
        exp.setDegree("博士");
        exp.setMajor("临床医学");
        exp.setSchoolName("首都医科大学");
        exps.add(exp);
        declarationEtcBO.setDecEduExps(exps);
        list.add(declarationEtcBO);
        declarationEtcBO = new DeclarationEtcBO();
        declarationEtcBO.setTextbookName("人体解剖学与组织胚胎学");
        ArrayList<DecTeachExp> decTeachExps = (ArrayList<DecTeachExp>) decTeachExpService.getListDecTeachExpByDeclarationId(7747L);
        declarationEtcBO.setDecTeachExps(decTeachExps);
        list.add(declarationEtcBO);
        declarationEtcBO = new DeclarationEtcBO();
        declarationEtcBO.setPresetPosition("副主编");
        list.add(declarationEtcBO);
        Workbook workbook = excelHelper.fromDeclarationEtcBOList(list, "专家信息表");
        FileOutputStream out = new FileOutputStream("DeclarationEtcBOList.xls");
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

    @Test
    public void exportFromMaps() throws IOException {
        String sql = "SELECT * FROM bbs_group";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        for (Map<String, Object> map : maps) {
            map.put("exception", "数据异常原因");
        }
        excelHelper.exportFromMaps(maps, "bbs_group", null);
    }
}
