package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SysOperation;
import com.bc.pmpheep.back.service.SysOperationService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * <pre>
 * 功能描述：系统操作日志单元测试
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-15
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class SysOperationSeviceTest extends BaseTest {
    Logger              logger = LoggerFactory.getLogger(SysOperationSeviceTest.class);

    @Resource
    SysOperationService sysOperationService;

    /**
     * 
     * <pre>
     * 功能描述：所有方法测试
     * 使用示范：
     *
     * </pre>
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testServiceAllMethodTest() {
        // add
        SysOperation sysOperation =
        sysOperationService.addSysOperation(new SysOperation(1L, "a", "a",
                                                             DateUtil.getCurrentTime(),
                                                             "/cms/content", "192.168.200.209",
                                                             "aaaa", "pc"));
        logger.info(sysOperation.toString());
        Assert.assertNotNull("插入内容后返回的sysOperation.id不应为空", sysOperation.getId());
    }
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetListSysOperation(){
    	PageParameter<SysOperation> pageParameter = new PageParameter<SysOperation>();
    	PageResult<SysOperation> pageResult = new PageResult<SysOperation>();
    	SysOperation sysOperation = new SysOperation(1L, "a", "a", DateUtil.getCurrentTime(),
                "/cms/content", "192.168.200.209", "aaaa", "pc");
    	pageParameter.setPageNumber(1);
		pageParameter.setPageSize(10);
		pageParameter.setParameter(sysOperation);
		pageResult = sysOperationService.getListSysOperation(pageParameter);
		Assert.assertNotNull("获取SysOperation列表失败", pageResult);
    }
}
