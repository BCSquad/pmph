package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPointLog;
import com.bc.pmpheep.back.service.WriterPointLogService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.WriterPointLogVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 积分日志记录业务层接口单元测试
 * @author tyc
 *
 */
public class WriterPointLogServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(WriterPointLogServiceTest.class);
	
	@Resource
	WriterPointLogService writerPointLogService;
	WriterPointLog writerPointLog = new WriterPointLog(3L, 1L, 5);
	public WriterPointLog addWriterPointLogs(){
		WriterPointLog writerPointLog = writerPointLogService.add(
				new WriterPointLog(3L, 1L, 5));
		return writerPointLog;
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void add(){
		writerPointLogService.add(writerPointLog);
		Assert.assertNotNull("插入内容后返回的writerPointLog.id不应为空", 
				writerPointLog.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void update(){
		writerPointLogService.add(writerPointLog);
		writerPointLog.setPoint(10);
		Assert.assertTrue("更新失败", 
				writerPointLogService.update(writerPointLog) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void delete(){
		writerPointLogService.add(writerPointLog);
		Integer deleteInteger = writerPointLogService.delete(writerPointLog.getId());
		Assert.assertTrue("删除失败", deleteInteger > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getWriterPointLog(){
		writerPointLogService.add(writerPointLog);
		Assert.assertNotNull("获取数据失败", 
				writerPointLogService.getWriterPointLog(writerPointLog.getId()));
	}
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getListWriterPointLog(){
		WriterPointLog writerPointLog = this.addWriterPointLogs();
		PageParameter pageParameter = new PageParameter<>();
        PageResult pageResult = new PageResult<>();
        WriterPointLogVO writerPointLogVO = new WriterPointLogVO();
        pageParameter.setParameter(writerPointLogVO);
		pageParameter.setPageSize(10);
		pageResult = writerPointLogService.getListWriterPointLog(pageParameter);
		Assert.assertNotNull("分页数据失败", pageResult);
	}
	
}
