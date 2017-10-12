/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils.test;

import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.utils.ZipHelper;
import java.io.File;
import javax.annotation.Resource;
import net.lingala.zip4j.exception.ZipException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 压缩工具类单元测试
 *
 * @author L.X <gugia@qq.com>
 */
public class ZipHelperTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(ZipHelperTest.class);

    @Resource
    ZipHelper zipHelper;

    @Test
    public void zip() {
        String src = ZipHelperTest.class.getResource("/").getPath();
        src = src.substring(1);
        logger.info("获取到的路径地址是 {}", src);
        zipHelper.zip(src, null, false, null);//压缩src到父目录
    }

    @Test
    public void unzip() throws ZipException {
        String src = ZipHelperTest.class.getResource("/").getPath();
        src = src.substring(1);
        logger.info("获取到的路径地址是 {}", src);
        zipHelper.zip(src, null, false, null);//压缩src到父目录
        src = src.substring(0, src.lastIndexOf("/"));
        String dest = src.substring(0, src.lastIndexOf("/") + 1) + "dest";
        zipHelper.unZip(new File(src + ".zip"), dest, null);
    }
}
