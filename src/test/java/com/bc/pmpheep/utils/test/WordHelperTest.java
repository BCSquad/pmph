package com.bc.pmpheep.utils.test;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.utils.WordHelper;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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

    @Test
    public void fromDeclarationEtcBOList() throws FileNotFoundException, IOException {
        List<DeclarationEtcBO> list = new ArrayList<>();
        DeclarationEtcBO declarationEtcBO = new DeclarationEtcBO();
        list.add(declarationEtcBO);
        XWPFDocument document = wordHelper.fromDeclarationEtcBOList(list);
        FileOutputStream out = new FileOutputStream("test.doc");
        document.write(out);
        out.flush();
        out.close();
    }
}
