package com.bc.pmpheep.utils.test;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.RandomUtil;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.utils.WordHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Word工具测试类
 *
 * @author L.X <gugia@qq.com>
 */
public class WordHelperTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(WordHelperTest.class);

    @Resource
    WordHelper wordHelper;
    @Resource
    MaterialService materialService;
    @Resource
    DeclarationService declarationService;

    @Test
    @Ignore
    public void fromDeclarationEtcBOList() throws FileNotFoundException, IOException {
        List<DeclarationEtcBO> list = new ArrayList<>();
        DeclarationEtcBO declarationEtcBO = new DeclarationEtcBO();
        declarationEtcBO.setRealname("欧阳望月");
        declarationEtcBO.setUsername("Smith");
        declarationEtcBO.setTextbookName("人体解剖学与组织胚胎学");
        declarationEtcBO.setPresetPosition("副主编");
        declarationEtcBO.setChosenOrgName("人民卫生出版社");
        declarationEtcBO.setSex("女");
        declarationEtcBO.setBirthday("1975年11月22日");
        declarationEtcBO.setAddress("浙江省金华市婺城区婺州街1188号");
        declarationEtcBO.setExperience(23);
        declarationEtcBO.setOrgName("金华职业技术学院医学院");
        declarationEtcBO.setPosition("教师");
        declarationEtcBO.setTitle("教授");
        declarationEtcBO.setPostcode("321017");
        declarationEtcBO.setHandphone("13857989881");
        declarationEtcBO.setEmail("test10001test@163.com");
        declarationEtcBO.setFax("01065930013");
        declarationEtcBO.setTelephone("010-65930013");
        declarationEtcBO.setDecEduExps(makeFakeDecEduExpList());
        list.add(declarationEtcBO);
        declarationEtcBO = new DeclarationEtcBO();
        declarationEtcBO.setRealname("西门吹雪");
        declarationEtcBO.setUsername("13800009201");
        declarationEtcBO.setTextbookName("医疗化学");
        declarationEtcBO.setPresetPosition("编委");
        declarationEtcBO.setChosenOrgName("首都医科大学");
        list.add(declarationEtcBO);
        String textbook = "全国高等学校健康服务与管理专业第一轮规划教材";
        HashMap<String, XWPFDocument> map = wordHelper.fromDeclarationEtcBOList(textbook, list, "111111111111111111");
        for (Map.Entry<String, XWPFDocument> entry : map.entrySet()) {
            FileOutputStream out = new FileOutputStream(entry.getKey());
            entry.getValue().write(out);
            out.flush();
            out.close();
        }
    }

    @Test
    @Ignore
    public void fromDeclarationEtcBOListAlpha() throws FileNotFoundException, IOException {
        List<DeclarationEtcBO> declarationEtcBOs = declarationService.getDeclarationEtcBOs(125L);
        if (CollectionUtil.isEmpty(declarationEtcBOs)) {
            return;
        }
        String textbook = "全国高等学校健康服务与管理专业第一轮规划教材";
        HashMap<String, XWPFDocument> map = wordHelper.fromDeclarationEtcBOList(textbook, declarationEtcBOs, "111111111111111111");
        for (Map.Entry<String, XWPFDocument> entry : map.entrySet()) {
            FileOutputStream out = new FileOutputStream(entry.getKey());
            entry.getValue().write(out);
            out.flush();
            out.close();
        }
    }

    @Test
    public void export() throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
        /* 生成唯一临时目录名 */
        String tempDir = String.valueOf(System.currentTimeMillis()).concat(String.valueOf(RandomUtil.getRandomNum()));
        List<Material> materials = materialService.getListMaterial("四川重庆中等卫生职业教育规划教材（护理、助产专业） 第二轮修订");
        List<DeclarationEtcBO> declarationEtcBOs = declarationService.declarationEtcBO(materials.get(0).getId(),
                null, null, null, null, null, null, null, null, null);
        if (CollectionUtil.isEmpty(declarationEtcBOs)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String src = this.getClass().getResource("/").getPath();
        src = src.substring(1);
        sb.append(src);
        if (!src.endsWith(File.separator)) {
            sb.append(File.separator);
        }
        sb.append(tempDir);
        sb.append(File.separator);
        sb.append("全国高等学校健康服务与管理专业第一轮规划教材");
        sb.append(File.separator);
        sb.append("1.人体解剖学与组织胚胎学");
        sb.append(File.separator);
        logger.info("获取到的路径地址是 {}", sb.toString());
        wordHelper.export("四川重庆中等卫生职业教育规划教材（护理、助产专业） 第二轮修订", sb.toString(), declarationEtcBOs, "111111111111111111");
    }

    private ArrayList<DecEduExp> makeFakeDecEduExpList() {
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
        return exps;
    }
}
