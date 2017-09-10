/*
 * Copyright 2016 Masslink technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.test;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于测试的基类(抽象类)，所有测试类都从该类继承
 *
 * @author L.X <gugia@qq.com>
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-framework.xml")
@Transactional(transactionManager = "transactionManager")
public abstract class BaseTest {

}
