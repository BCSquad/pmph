package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.service.SurveyQuestionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.SurveyQuestionVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查问题业务层接口单元测试
 * @author tyc
 *
 */
public class SurveyQuestionServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(SurveyQuestionServiceTest.class);
	
	@Resource
	SurveyQuestionService surveyQuestionService;
	SurveyQuestion surveyQuestion = new SurveyQuestion(1L, "测试问题", (short) 1, 
			12, "测试", true, false, null, null);
	
	private SurveyQuestion addSurveyQuestions(){
		SurveyQuestion surveyQuestion = surveyQuestionService.addSurveyQuestion(new SurveyQuestion(3L, 
				"测试问题1", (short) 2, 17, "测试1", true, false, null, null));
		return surveyQuestion;
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyQuestion(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		Assert.assertNotNull("插入内容后返回的surveyQuestion.id不应为空", surveyQuestion.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurveyQuestion(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		surveyQuestion.setTitle("测试问题2");
		Assert.assertTrue("更新失败", 
				surveyQuestionService.updateSurveyQuestion(surveyQuestion) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyQuestionById(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		Assert.assertNotNull("获取数据失败", 
				surveyQuestionService.getSurveyQuestionById(surveyQuestion.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteSurveyQuestionById(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		Assert.assertNotNull("逻辑删除数据失败", 
				surveyQuestionService.deleteSurveyQuestionById(surveyQuestion.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyQuestionListVOList(){
		String jsonSurveyQuestion = "[{\"id\":\"3\",\"categoryId\":\"2\",\"title\":\"测试\",\"type\":"
				+ "\"1\",\"sort\":\"1\",\"direction\":\"测试问题\",\"isAnswer\":\"true\","
				+ "\"surveyQuestionOptionList\":[{\"id\":\"\",\"questionId\":\"\",\"optionContent\":"
				+ "\"测试1\",\"isOther\":\"true\",\"remark\":\"测试备注\"},{\"id\":\"\",\"questionId\":"
				+ "\"\",\"optionContent\":\"测试2\",\"isOther\":\"false\",\"remark\":\"\"}]}]";
		Assert.assertNotNull("添加数据失败", 
				surveyQuestionService.addSurveyQuestionListVOList(jsonSurveyQuestion));
	}
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Test
	@Rollback(Const.ISROLLBACK)
	public void listSurveyQuestion(){
		SurveyQuestion surveyQuestion = this.addSurveyQuestions();
		PageParameter pageParameter = new PageParameter<>();
        PageResult pageResult = new PageResult<>();
		SurveyQuestionVO surveyQuestionVO = new SurveyQuestionVO();
		pageParameter.setParameter(surveyQuestionVO);
		pageParameter.setPageSize(10);
		pageResult = surveyQuestionService.listSurveyQuestion(pageParameter);
		Assert.assertNotNull("分页数据失败", pageResult);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getQuestionOptionByQuestionIdOrCategoryId(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		Assert.assertNotNull("获取数据失败", 
				surveyQuestionService.getQuestionOptionByQuestionIdOrCategoryId(surveyQuestion.getCategoryId(), 
						surveyQuestion.getCategoryId()));
	}
	
}
