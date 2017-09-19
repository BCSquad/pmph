package com.bc.pmpheep.back.util.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.util.DataTransfer;
import com.bc.pmpheep.test.BaseTest;

/*
 * author:lyc
 * 数据迁移工具测试类
 */
public class DataTransferTest extends BaseTest{
	Logger logger = LoggerFactory.getLogger(DataTransfer.class);
	@Resource
    DataTransfer dataTransfer;
	@Test
	@Rollback(false)
	public void area() throws Exception{
		dataTransfer.area("jdbc:mysql://localhost:3306/pmph_imesp_9.11?useSSL=true", "root", "cc148604");
		logger.info("---------------区域表迁移----------------");
		dataTransfer.pmphUser("jdbc:mysql://localhost:3306/pmph_imesp_9.11?useSSL=true", "root", "cc148604");
		logger.info("---------------社内用户表迁移-------------");
	}

}
