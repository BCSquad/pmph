package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.service.WriterPointService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.WriterPointVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 用户积分业务层接口单元测试
 * @author tyc
 *
 */
public class WriterPointServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(WriterPointServiceTest.class);
	
	@Resource
	WriterPointService writerPointService;
	WriterPoint writerPoint = new WriterPoint(7L, 0, 0, 0);
	public WriterPoint addWriterPoints(){
		WriterPoint writerPoint = writerPointService.addWriterPoint(
				new WriterPoint(7L, 0, 0, 0));
		return writerPoint;
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addWriterPoint(){
		writerPointService.addWriterPoint(writerPoint);
		Assert.assertNotNull("插入内容后返回的writerPoint.id不应为空", 
				writerPoint.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateWriterPoint(){
		writerPointService.addWriterPoint(writerPoint);
		writerPoint.setTotal(5);
		Assert.assertTrue("更新失败", 
				writerPointService.updateWriterPoint(writerPoint) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteWriterPoint(){
		writerPointService.addWriterPoint(writerPoint);
		Integer deleteInteger = writerPointService.deleteWriterPoint(writerPoint.getId());
		Assert.assertTrue("删除失败", deleteInteger > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getWriterPoint(){
		writerPointService.addWriterPoint(writerPoint);
		Assert.assertNotNull("获取数据失败", 
				writerPointService.getWriterPoint(writerPoint.getId()));
	}
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getListWriterPoint(){
		WriterPoint writerPoint = this.addWriterPoints();
		PageParameter pageParameter = new PageParameter<>();
        PageResult pageResult = new PageResult<>();
        WriterPointVO writerPointVO = new WriterPointVO();
        pageParameter.setParameter(writerPointVO);
		pageParameter.setPageSize(10);
		pageResult = writerPointService.getListWriterPoint(pageParameter);
		Assert.assertNotNull("分页数据失败", pageResult);
	}
	
}
