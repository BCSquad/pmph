package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.service.SurveyTemplateService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.SurveyTemplateVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查模版业务层接口单元测试
 * @author tyc
 *
 */
public class SurveyTemplateServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(SurveyTemplateServiceTest.class);
	
	@Resource
	SurveyTemplateService surveyTemplateService;
	SurveyTemplate surveyTemplate = new SurveyTemplate("测试", 1, 3L, false, null, null);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyTemplate(){
		surveyTemplateService.addSurveyTemplate(surveyTemplate);
		Assert.assertNotNull("插入内容后返回的surveyQuestionCategory.id不应为空", surveyTemplate.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurveyTemplate(){
		surveyTemplateService.addSurveyTemplate(surveyTemplate);
		surveyTemplate.setTemplateName("测试1");
		Assert.assertTrue("更新失败", 
				surveyTemplateService.updateSurveyTemplate(surveyTemplate) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyTemplateById(){
		surveyTemplateService.addSurveyTemplate(surveyTemplate);
		Assert.assertNotNull("获取数据失败", 
				surveyTemplateService.getSurveyTemplateById(surveyTemplate.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteSurveyTemplateById(){
		surveyTemplateService.addSurveyTemplate(surveyTemplate);
		Assert.assertNotNull("逻辑删除数据失败", 
				surveyTemplateService.deleteSurveyTemplateById(surveyTemplate.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyTemplateVO(){
		SurveyTemplateVO surveyTemplateVO = new SurveyTemplateVO();
		surveyTemplateVO.setTemplateName("测试2");
		surveyTemplateVO.setSort(3);
		surveyTemplateVO.setUserId(7L);
		surveyTemplateVO.setQuestionId(9L);
		Assert.assertNotNull("添加数据失败", surveyTemplateService.addSurveyTemplateVO(surveyTemplateVO));
	}
	
}
