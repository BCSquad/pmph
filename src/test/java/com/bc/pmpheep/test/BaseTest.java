/*
 * Copyright 2016 Masslink technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于测试的基类(抽象类)，所有测试类都从该类继承
 * 
 * @author L.X <gugia@qq.com>
 */
// @Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-framework.xml" })
@Transactional(transactionManager = "transactionManager")
public class BaseTest {

    // assertArrayEquals(expecteds, actuals) 查看两个数组是否相等。
    // byte[] expected = "trial".getBytes();
    // byte[] actual = "trial".getBytes();
    // Assert.assertArrayEquals("failure - byte arrays not same", expected, actual);
    // assertEquals(expected, actual) 查看两个对象是否相等。类似于字符串比较使用的equals()方法
    // Assert.assertEquals("failure - strings not same", 5l, 5l);
    // assertNotEquals(first, second) 查看两个对象是否不相等。
    // Assert.assertNull("should be null", new String("1"),new String("2"));
    // assertNull(object) 查看对象是否为空。
    // Assert.assertNull("should be null", null);
    // assertNotNull(object) 查看对象是否不为空。
    // Assert.assertNotNull("should not be null", new Object());
    // assertSame(expected, actual) 查看两个对象的引用是否相等。类似于使用“==”比较两个对象
    // Integer aNumber = Integer.valueOf(768);
    // Assert.assertSame("should be same", aNumber, aNumber);
    // assertNotSame(unexpected, actual) 查看两个对象的引用是否不相等。类似于使用“!=”比较两个对象
    // Assert.assertNotSame("should not be same Object", new Object(), new Object());
    // assertTrue(condition) 查看运行结果是否为true。
    // Assert.assertFalse("failure - should be false", true);
    // assertFalse(condition) 查看运行结果是否为false。
    // Assert.assertFalse("failure - should be false", false);
    // assertThat(actual, matcher) 查看实际值是否满足指定的条件
    // Assert.assertThat("albumen", both(containsString("a")).and(containsString("b")));
    // Assert.assertThat(Arrays.asList("one", "two", "three"), hasItems("one", "three"));
    // Assert.assertThat(Arrays.asList(new String[] { "fun", "ban", "net" }),
    // everyItem(containsString("n")));
    // Assert.assertThat("good", allOf(equalTo("good"), startsWith("good")));
    // Assert.assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
    // Assert.assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
    // Assert.assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
    // Assert.assertThat(new Object(), not(sameInstance(new Object())));
    // fail() 让测试失败
    // Assert.fail();
}
