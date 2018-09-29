package com.bc.pmpheep.back.service.test;

import com.bc.pmpheep.back.service.ProductTypeService;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author：MrXiao
 * @Description:
 * @Date:created in 18:58 2018/9/27
 * @Modified By:
 */
public class ProdcutTypeServiceTest   extends BaseTest {

    Logger logger = LoggerFactory.getLogger(ProdcutTypeServiceTest.class);
    @Autowired
    ProductTypeService productTypeService;

    @Test
    public void testRecursionProductTypeVo(){
        Integer productTypeParam = 1;
        Assert.assertNotNull("获取内容分类树失败",productTypeService.listProductContentType(0L,productTypeParam.shortValue(),""));
        System.out.println(productTypeService.listProductContentType(0L,productTypeParam.shortValue(),"").toString());
    }
}
