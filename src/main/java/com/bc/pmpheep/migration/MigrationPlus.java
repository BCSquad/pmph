package com.bc.pmpheep.migration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.dao.CmsAdvertisementDao;
import com.bc.pmpheep.back.dao.CmsAdvertisementImageDao;
import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Sensitive;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.po.SurveyQuestionOption;
import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.po.SurveyTemplateQuestion;
import com.bc.pmpheep.back.po.SurveyType;
import com.bc.pmpheep.back.po.Topic;
import com.bc.pmpheep.back.po.TopicExtra;
import com.bc.pmpheep.back.po.TopicWriter;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.SensitiveService;
import com.bc.pmpheep.back.service.SurveyQuestionAnswerService;
import com.bc.pmpheep.back.service.SurveyQuestionCategoryService;
import com.bc.pmpheep.back.service.SurveyQuestionOptionService;
import com.bc.pmpheep.back.service.SurveyQuestionService;
import com.bc.pmpheep.back.service.SurveyService;
import com.bc.pmpheep.back.service.SurveyTargetService;
import com.bc.pmpheep.back.service.SurveyTemplateQuestionService;
import com.bc.pmpheep.back.service.SurveyTemplateService;
import com.bc.pmpheep.back.service.SurveyTypeService;
import com.bc.pmpheep.back.service.TopicExtraService;
import com.bc.pmpheep.back.service.TopicService;
import com.bc.pmpheep.back.service.TopicWriertService;
import com.bc.pmpheep.back.service.WriterPointRuleService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.vo.CmsAdvertisementOrImageVO;
import com.bc.pmpheep.back.vo.PmphUserDepartmentVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 数据填充工具类
 * 
 * Description:数据填充工具类
 * @author tyc
 *
 */
@Component
public class MigrationPlus {
	
	 private final Logger logger = LoggerFactory.getLogger(MigrationPlus.class);
	 @Resource
	 SurveyQuestionAnswerService surveyQuestionAnswerService;
	 @Resource
	 SurveyQuestionCategoryService surveyQuestionCategoryService;
	 @Resource
	 SurveyQuestionOptionService surveyQuestionOptionService;
	 @Resource
	 SurveyQuestionService surveyQuestionService;
	 @Resource
	 SurveyService surveyService;
	 @Resource
	 SurveyTargetService surveyTargetService;
	 @Resource
	 SurveyTemplateQuestionService surveyTemplateQuestionService;
	 @Resource
	 SurveyTemplateService surveyTemplateService;
	 @Resource
	 SurveyTypeService surveyTypeService;
	 @Resource
	 PmphUserService pmphUserService;
	 @Resource
	 CmsAdvertisementDao cmsAdvertisementDao;
	 @Resource
	 CmsAdvertisementImageDao cmsAdvertisementImageDao;
	 @Resource
	 FileService fileService;
	 @Resource
	 TopicService topicService;
	 @Resource
	 TopicExtraService topicExtraService;
	 @Resource
	 TopicWriertService topicWriertService;
	 @Resource
	 WriterUserService writerUserService;
	 @Resource
	 SensitiveService sensitiveService;
	 @Resource
	 WriterPointRuleService writerPointRuleService;
	 @Resource
	 PmphDepartmentService pmphDepartmentService;
	 public void start() {
		 Date begin = new Date();
         logger.info("填充调查问卷测试数据");
		 survey();
         logger.info("填充选题申报测试数据");
		 topic();
		 logger.info("填充积分规则数据");
		 point();
		 logger.info("初始化广告数据");
		 initCmsAdvertisementData();
		 logger.info("数据填充运行结束，用时：{}", JdbcHelper.getPastTime(begin));
		 department();
		 logger.info("更新人卫社组织结构，清除冗余部门");
	 }
	 
	 //初始化广告数据
	 @SuppressWarnings("unchecked")
	 public void initCmsAdvertisementData() {
	        //初始化的数据
	        String dataJson
	                = "["
	                + "{adname:'首页轮播',          type:1,autoPlay:true, animationInterval:3000, image:[{image:'/img/banner4.png'},{image:'/img/banner3.png'},{image:'/img/banner2.png'}]} ,"
	                + "{adname:'首页中部1',         type:0,autoPlay:false,animationInterval:0,   image:[{image:'/upload/site/2670f031-35da-4dd6-b079-8f295c51a339.png'}]} ,"
	                + "{adname:'首页中部2',         type:0,autoPlay:false,animationInterval:0,   image:[{image:'/upload/site/af598f9e-ae9e-48a0-a3e4-17acc363051a.png'}]} ,"
	                + "{adname:'首页中部3',         type:0,autoPlay:false,animationInterval:0,   image:[{image:'/upload/site/a4160c1e-8beb-4530-9f2b-df022a6f751d.png'}]} ,"
	                + "{adname:'首页中部4',         type:0,autoPlay:false,animationInterval:0,   image:[{image:'/upload/site/a69b782d-f1ad-42e6-a91a-08432963b54a.png'}]} ,"
	                + "{adname:'信息快报和遴选公告列表', type:0,autoPlay:false,animationInterval:0,   image:[{image:'/img/caode.png'}]} ,"
	                + "{adname:'首页原重点推荐1',	    type:0,autoPlay:false,animationInterval:0,   image:[{image:'/upload/site/a2067cf8-d076-4ba5-90b8-f63dd4d3a172.png'}]} ,"
	                + "{adname:'首页原重点推荐2',	    type:0,autoPlay:false,animationInterval:0,   image:[{image:'/upload/site/aafeba35-79e8-49f6-931c-45678ef58d86.png'}]} ,"
	                + "{adname:'读书首页轮播 ',      type:1,autoPlay:true ,animationInterval:3000,image:[{image:'/img/bannerd.png'},{image:'/img/banner2d.png'}]} "
	                + "]";
	        Gson gson = new Gson();
	        List<CmsAdvertisementOrImageVO> lst = gson.fromJson(dataJson, new TypeToken<ArrayList<CmsAdvertisementOrImageVO>>() {
	        }.getType());
	        for (CmsAdvertisementOrImageVO cmsAdvertisementAndImages : lst) {
	            CmsAdvertisement cmsAdvertisement = new CmsAdvertisement();
	            //广告名称
	            cmsAdvertisement.setAdname(cmsAdvertisementAndImages.getAdname());
	            //是否自动播放
	            cmsAdvertisement.setAutoPlay(cmsAdvertisementAndImages.getAutoPlay());
	            //循环间隔时间
	            cmsAdvertisement.setAnimationInterval(cmsAdvertisementAndImages.getAnimationInterval());
	            //类型    0 普通  1 轮播  2两张
	            cmsAdvertisement.setType(cmsAdvertisementAndImages.getType());
	            //保存广告
	            cmsAdvertisementDao.addCmsAdvertisement(cmsAdvertisement);
	            List<CmsAdvertisementImage> images = (List<CmsAdvertisementImage>) (cmsAdvertisementAndImages.getImage());
	            for (CmsAdvertisementImage image : images) {
	                String filePath = image.getImage();
	                image.setAdvertId(cmsAdvertisement.getId());
	                image.setImage("----");
	                //保存图片文件 
	                cmsAdvertisementImageDao.addCmsAdvertisementImage(image);
	                String mongoId = null;
	                try {
	                    //保存图片至mongo
	                    mongoId = fileService.migrateFile(filePath, FileType.CMS_ADVERTISEMENT, image.getId());
	                } catch (Exception ex) {
	                    logger.warn("文件上传失败 :{}", ex.getMessage());
	                    //文件保存失败删除这条记录
	                    cmsAdvertisementImageDao.deleteCmsAdvertisementByImages(image.getId());
	                    continue;
	                }
	                image.setImage(mongoId);
	                //修改图片文件地址
	                cmsAdvertisementImageDao.updateCmsAdvertisementImage(image);
	            }
	        }
	    }
	 
	 protected void survey() {
		 SurveyType surveyType = new SurveyType("学生", 1);
		 surveyTypeService.addSurveyType(surveyType);
		 SurveyType surveyType2 = new SurveyType("教师", 2);
		 surveyTypeService.addSurveyType(surveyType2);
		 SurveyType surveyType3 = new SurveyType("医生", 3);
		 surveyTypeService.addSurveyType(surveyType3);
		 String sql = "select * from sys_user where userid = '1496375695709123-789241'"; // 查询武震的new_pk
		 List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		 Map<String, Object> map = maps.get(0);
		 Long id = (Long) map.get("new_pk");
		 SurveyTemplate surveyTemplate = new SurveyTemplate("人卫e教平台满意度问卷模板", 999, id, 
				 "该问卷只针对教师", surveyType2.getId(), false);
		 surveyTemplateService.addSurveyTemplate(surveyTemplate);
		 Survey survey = new Survey("人卫e教平台满意度问卷模板", 
				 "希望您能抽出几分钟时间，将您的感受和建议告诉我们，我们非常重视每位教师的宝贵意见，期待您的参与！", 
				 "该问卷只针对教师", surveyTemplate.getId(), surveyType2.getId(), 
				 id, null, null, 999, (short) 0);
		 surveyService.addSurvey(survey);
		 SurveyQuestion surveyQuestion = new SurveyQuestion(0L, "您是否使用过人卫e教平台网站？", (short) 1,
				 1, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion);
		 SurveyQuestionOption surveyQuestionOption = new SurveyQuestionOption(surveyQuestion.getId(), 
				 "是", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption);
		 SurveyQuestionOption surveyQuestionOption2 = new SurveyQuestionOption(surveyQuestion.getId(), 
				 "否", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption2);
		 SurveyQuestion surveyQuestion2 = new SurveyQuestion(0L, "您使用人卫e教平台网站的目的是？", 
				 (short) 2, 2, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion2);
		 SurveyQuestionOption  surveyQuestionOption3 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "工作需要", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption3);
		 SurveyQuestionOption  surveyQuestionOption4 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "获取生活信息，扩展视野受", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption4);
		 SurveyQuestionOption  surveyQuestionOption5 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "关注名人动态", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption5);
		 SurveyQuestionOption  surveyQuestionOption6 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "寻找知趣相投的群体", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption6);
		 SurveyQuestionOption  surveyQuestionOption7 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "展现自己多彩的生活", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption7);
		 SurveyQuestionOption  surveyQuestionOption8 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "其他", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption8);
		 SurveyQuestion surveyQuestion3 = new SurveyQuestion(0L, "您在人卫e教平台网站上主要做什么？", 
				 (short) 2, 3, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion3);
		 SurveyQuestionOption  surveyQuestionOption9 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "教材申报", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption9);
		 SurveyQuestionOption  surveyQuestionOption10 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "查看图书", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption10);
		 SurveyQuestionOption  surveyQuestionOption11 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "图书纠错", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption11);
		 SurveyQuestionOption  surveyQuestionOption12 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "选题申报", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption12);
		 SurveyQuestionOption  surveyQuestionOption13 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "评论图书", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption13);
		 SurveyQuestionOption  surveyQuestionOption14 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "其他", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption14);
		 SurveyQuestion surveyQuestion4 = new SurveyQuestion(0L, "您使用人卫e教平台网站的频率是？", 
				 (short) 1, 4, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion4);
		 SurveyQuestionOption surveyQuestionOption15 = new SurveyQuestionOption(surveyQuestion4.getId(), 
				 "几乎每天都会使用", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption15);
		 SurveyQuestionOption surveyQuestionOption16 = new SurveyQuestionOption(surveyQuestion4.getId(), 
				 "每个星期都会使用", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption16);
		 SurveyQuestionOption surveyQuestionOption17 = new SurveyQuestionOption(surveyQuestion4.getId(), 
				 "每个月都会使用", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption17);
		 SurveyQuestionOption surveyQuestionOption18 = new SurveyQuestionOption(surveyQuestion4.getId(), 
				 "很少使用", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption18);
		 SurveyQuestion surveyQuestion5 = new SurveyQuestion(0L, "到目前为止，您使用人卫e教平台网站有多久了？", 
				 (short) 1, 5, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion5);
		 SurveyQuestionOption surveyQuestionOption19 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "不到1个月", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption19);
		 SurveyQuestionOption surveyQuestionOption20 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "1-3个月（含3个月）", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption20);
		 SurveyQuestionOption surveyQuestionOption21 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "3-6个月（含6个月）", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption21);
		 SurveyQuestionOption surveyQuestionOption22 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "6-12个月（含12个月）", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption22);
		 SurveyQuestionOption surveyQuestionOption23 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "1-2年（含2年）", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption23);
		 SurveyQuestionOption surveyQuestionOption24 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "2年以上", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption24);
		 SurveyQuestion surveyQuestion6 = new SurveyQuestion(0L, "您的性别是", (short) 1,
				 6, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion6);
		 SurveyQuestionOption surveyQuestionOption25 = new SurveyQuestionOption(surveyQuestion6.getId(), 
				 "男", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption25);
		 SurveyQuestionOption surveyQuestionOption26 = new SurveyQuestionOption(surveyQuestion6.getId(), 
				 "女", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption26);
		 SurveyQuestion surveyQuestion7 = new SurveyQuestion(0L, "您的学历是", (short) 1,
				 7, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion7);
		 SurveyQuestionOption surveyQuestionOption27 = new SurveyQuestionOption(surveyQuestion7.getId(), 
				 "本科", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption27);
		 SurveyQuestionOption surveyQuestionOption28 = new SurveyQuestionOption(surveyQuestion7.getId(), 
				 "硕士及以上", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption28);
		 SurveyQuestion surveyQuestion8 = new SurveyQuestion(0L, "您对人卫e教平台网站的发展还有哪些期待", 
				 (short) 5, 8, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion8);
		 SurveyQuestionOption surveyQuestionOption29 = new SurveyQuestionOption(surveyQuestion8.getId(), 
				 "期待", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption29);
		 SurveyQuestion surveyQuestion9 = new SurveyQuestion(0L, "您对人卫e教平台网站的发展还有哪些建议", 
				 (short) 5, 9, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion9);
		 SurveyQuestionOption surveyQuestionOption30 = new SurveyQuestionOption(surveyQuestion9.getId(), 
				 "建议", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption30);
		 SurveyQuestion surveyQuestion10 = new SurveyQuestion(0L, "您对人卫e教平台网站的态度是", 
				 (short) 4, 10, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion10);
		 SurveyQuestionOption surveyQuestionOption31 = new SurveyQuestionOption(surveyQuestion10.getId(), 
				 "态度", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption31);
		 SurveyTemplateQuestion surveyTemplateQuestion = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion);
		 SurveyTemplateQuestion surveyTemplateQuestion2 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion2.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion2);
		 SurveyTemplateQuestion surveyTemplateQuestion3 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion3.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion3);
		 SurveyTemplateQuestion surveyTemplateQuestion4 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion4.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion4);
		 SurveyTemplateQuestion surveyTemplateQuestion5 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion5.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion5);
		 SurveyTemplateQuestion surveyTemplateQuestion6 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion6.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion6);
		 SurveyTemplateQuestion surveyTemplateQuestion7 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion7.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion7);
		 SurveyTemplateQuestion surveyTemplateQuestion8 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion8.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion8);
		 SurveyTemplateQuestion surveyTemplateQuestion9 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion9.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion9);
		 SurveyTemplateQuestion surveyTemplateQuestion10 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion10.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion10);
	 }
	 
	 protected void topic() {
		Topic topic = new Topic(4017L, "内科学", 1, new Timestamp(445555L), 2, 123, 210, "医学", 1, 1, 17L,
				"20", "21", null, null, null, "2000", null, null, null, null, null, null, false, null, null, null,
				null, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, 0, "内容丰富", new Timestamp(755564L), true, 15L, false, null, true, 35L, false, 
				null, true, 423L, true, false, false, "基础覆盖面广", null, null, null, null, new Timestamp(446666L));
		topic = topicService.add(topic);
		TopicExtra topicExtra = new TopicExtra(topic.getId(), "丰富学生基础理论的辅导教材", 
				"出版价值高", "内科学的基础理论",null);
		topicExtraService.add(topicExtra);
		WriterUser writerUser = writerUserService.get(topic.getUserId());
		TopicWriter topicWriter = new TopicWriter(topic.getId(), writerUser.getRealname(),
				writerUser.getSex(), 47, writerUser.getPosition(), writerUser.getWorkPlace());
		topicWriertService.add(topicWriter);		
		Topic topic1 = new Topic(4018L, "外科学", 1, new Timestamp(455985L), 2, 115, 200, "医学", 1, 1, 18L, "60",
				"18", null, null, null, "1200", null, null, null, null, null, null, false, null, null, null, null,
				null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, 2, "重复", new Timestamp(765464l), true, 15L, false, null, true, 35L, false, null,
				true, 546L, true, false, false, "退回", null, null, null, null, new Timestamp(456000L));
		topic1 = topicService.add(topic1);
		TopicExtra topicExtra1 = new TopicExtra(topic1.getId(), "注重临床经验", "实用，工具书",
				"临床技术",null);
		topicExtraService.add(topicExtra1);
		WriterUser writerUser1 = writerUserService.get(topic1.getUserId());
		TopicWriter topicWriter1 = new TopicWriter(topic1.getId(), writerUser1.getRealname(),
				writerUser1.getSex(), 45, writerUser1.getPosition(), writerUser1.getWorkPlace());
		topicWriertService.add(topicWriter1);		
		Topic topic2 = new Topic(4020L, "脑科学", 1, new Timestamp(435648L), 2, 95, 175, "医学", 1, 4, 20L, null, 
				"17", null, null, null, "2600", null, null, null, null, null, null, false, null, null, null, null,
				null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, 1, null, null, true, 15L, false, null, null, 35L, false, null, false, null,
				false, false, false, null, null, null, null, null, new Timestamp(445648L));
		topic2 = topicService.add(topic2);
		TopicExtra topicExtra2 = new TopicExtra(topic2.getId(), "前沿技术", "科普读物",
				"介绍脑科学研究的发展",null);
		topicExtraService.add(topicExtra2);
		WriterUser writerUser2 = writerUserService.get(topic2.getUserId());
		TopicWriter topicWriter2 = new TopicWriter(topic2.getId(), writerUser2.getRealname(),
				writerUser2.getSex(), 51, writerUser2.getPosition(), writerUser2.getWorkPlace());
		topicWriertService.add(topicWriter2);		
		Topic topic3 = new Topic(4025L, "医学遗传学", 2, new Timestamp(452135L), 4, 135, 785, "遗传学", 0, 3, 25L,
				null, "26", null, null, null, "2100", null, null, null, null, null, null, null, null, null, null,
				null, null, true, "Medical Genetics", "林恩·乔德", "XXX出版社", "美国", "1", "林恩·乔德", 0, 50, null,
				2, 0, "德克萨斯州", null, null, null, null, 1, null, null, true, 15L, false, null, true, 35L, true, "其他受理选题",
				false, 419L, false, false, false, null, null, null, null, null, new Timestamp(453135L));
		topic3 = topicService.add(topic3);
		TopicExtra topicExtra3 = new TopicExtra(topic3.getId(), "国外经典教材", "工具书", 
				"最新译本",null);
		topicExtraService.add(topicExtra3);
		WriterUser writerUser3 = writerUserService.get(topic3.getUserId());
		TopicWriter topicWriter3 = new TopicWriter(topic3.getId(), writerUser3.getRealname(),
				writerUser3.getSex(), 53, writerUser3.getPosition(), writerUser3.getWorkPlace());
		topicWriertService.add(topicWriter3);		
		Topic topic4 = new Topic(4030L, "社会心理学", 1, new Timestamp(475625L), 2, 1210, 179, "心理学", 2, 5, 30L,
				null, "27", null, null, null, "3100", null, null, null, null, null, null, false, null, null, null,
				null, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, 
				null, null, null, 1, null, null, true, 15L, true, "不属于本部门领域", false, 35L, false, null, false, 
				null, false, false, false, null, null, null, null, null, new Timestamp(476522L));
		topic4 = topicService.add(topic4);
		TopicExtra topicExtra4 = new TopicExtra(topic4.getId(), "心理学必修教材", "重要，必修课程",
				"基础理论",null);
		topicExtraService.add(topicExtra4);
		WriterUser writerUser4 = writerUserService.get(topic4.getId());
		TopicWriter topicWriter4 = new TopicWriter(topic4.getId(), writerUser4.getRealname(),
				writerUser4.getSex(), 37, writerUser4.getPosition(), writerUser4.getWorkPlace());
		topicWriertService.add(topicWriter4);				
		Topic topic5 = new Topic(4035L, "人体解剖学", 0, new Timestamp(423654L), 1, 97, 1098, "医学", 0, 3, 35L,
				null, "14", null, null, null, "3300", null, null, null, null, null, null, false, null, null, null,
				null, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, 1, null, null, true, 15L, false, null, false, null, false, null, false, null, false, 
				false, false, null, null, null, null, null, new Timestamp(433654L));
		topic5 = topicService.add(topic5);
		TopicExtra topicExtra5 = new TopicExtra(topic5.getId(), "医学院学生必读辅导书",
				"重要", "人体图解",null);
		topicExtraService.add(topicExtra5);
		WriterUser writerUser5 = writerUserService.get(topic5.getUserId());
		TopicWriter topicWriter5 = new TopicWriter(topic5.getId(), writerUser5.getRealname(), 
				writerUser5.getSex(), 46, writerUser5.getPosition(), writerUser5.getWorkPlace());
		topicWriertService.add(topicWriter5);		
		Topic topic6 = new Topic(4050L, "医学统计学", 1, new Timestamp(413564L), 3, 1150, 108, "医学", 1, 1, 50L, 
				null, "18", null, null, null, "1100", null, null, null, null, null, null, false, null, null, null,
				null, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null,  0, null, null, null, null, null, null, null, null, null, null, null,null, null, true, 
				null, null, null, null, null, null, null);
		topic6 = topicService.add(topic6);
		TopicExtra topicExtra6 = new TopicExtra(topic6.getId(), "辅助教材", "基础教材",
				"统计学在医学上的应用",null);
		topicExtraService.add(topicExtra6);
		WriterUser writerUser6 = writerUserService.get(topic6.getUserId());
		TopicWriter topicWriter6 = new TopicWriter(topic6.getId(), writerUser6.getRealname(),
				writerUser6.getSex(), 52, writerUser6.getPosition(), writerUser6.getWorkPlace());
		topicWriertService.add(topicWriter6);
	}
	 
	 protected void sensitive() {
		Sensitive sensitive = new Sensitive("妈的", 1, "不文明用语", false, false, null, null);
		sensitiveService.add(sensitive);
		Sensitive sensitive1 = new Sensitive("闭嘴", 2, "不礼貌", false, false, null, null);
		sensitiveService.add(sensitive1);
		Sensitive sensitive2 = new Sensitive("死人", 3, null, false, false, null, null);
		sensitiveService.add(sensitive2);
		Sensitive sensitive3 = new Sensitive("王八蛋", 4, "不文明用语", false, false, null, null);
		sensitiveService.add(sensitive3);
		Sensitive sensitive4 = new Sensitive("傻逼", 5, null, false, false, null, null);
		sensitiveService.add(sensitive4);
		Sensitive sensitive5 = new Sensitive("垃圾", 6, "不合适用语", false, false, null, null);
		sensitiveService.add(sensitive5);
		Sensitive sensitive6 = new Sensitive("渣渣", 7, "网络用语", false, true, null, null);
		sensitiveService.add(sensitive6);
		Sensitive sensitive7 = new Sensitive("二", 8, "网络用语", true, false, null, null);
		sensitiveService.add(sensitive7);
		Sensitive sensitive8 = new Sensitive("放屁", 9, "口头用语", true, true, null, null);
		sensitiveService.add(sensitive8);
	}
	 //积分规则迁移
	 protected  void point(){
		 WriterPointRule writerPointRule1=new WriterPointRule("连续登录", "logins", 1, false, null, null, "连续登录每天增加1分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule1);
		 WriterPointRule writerPointRule2=new WriterPointRule("连续最大积分", "max_login_integral", 5, false, null, null, "到第5天增加5分后，每天给5分，中间有一天不来，重新开始积分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule2);
		 WriterPointRule writerPointRule3=new WriterPointRule("登录", "login", 1, false, null, null, "每天登录一次给1分，一天仅一次", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule3);
		 /*WriterPointRule writerPointRule4=new WriterPointRule("回复话题", "reply_topic", 1, false, null, null, "回复话题给1分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule4);
		 WriterPointRule writerPointRule5=new WriterPointRule("创建话题", "create_topic", 1, false, null, null, "创建话题给1分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule5);*/
		 WriterPointRule writerPointRule9=new WriterPointRule("图书评论", "book_comment", 1, false, null, null, "图书评论给1分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule9);
		 WriterPointRule writerPointRule10=new WriterPointRule("图书纠错", "book_correction", 1, false, null, null, "图书纠错给1分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule10);
		 WriterPointRule writerPointRule11=new WriterPointRule("发表文章", "cms_content", 1, false, null, null, "发表文章给1分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule11);
		 WriterPointRule writerPointRule12=new WriterPointRule("问卷调查", "survey", 1, false, null, null, "问卷调查给1分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule12);
		 WriterPointRule writerPointRule6=new WriterPointRule("平台a", "sys_a", 50, true, "2", 2, "本平台50积分=商城2积分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule6);
		 WriterPointRule writerPointRule7=new WriterPointRule("平台b", "sys_b", 200, true, "0", 10, "本平台200积分=平台b10积分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule7);
		 WriterPointRule writerPointRule8=new WriterPointRule("智慧商城", "buss", 100, true, "1", 1, "本平台100积分=智慧商城1积分", false);
		 writerPointRuleService.addWriterPointRule(writerPointRule8);
	 }
	 
	 //	清除冗余部门
	protected void department(){
		//查询现在所有部门，
		PmphUserDepartmentVO departmentVO = pmphDepartmentService.listPmphDepartment(null);
		//部门总数为28，超过则是多余部门
		if(ObjectUtil.notNull(departmentVO)&&departmentVO.getSonDepartment().size()>28){
			for (PmphUserDepartmentVO pmphDepartment : departmentVO.getSonDepartment()) {
				//查询该部门下的所有成员
				List<PmphUser> pmphUsers=pmphUserService.listPmphUserByDepartmentId(pmphDepartment.getId());
				switch (pmphDepartment.getDpName()) {
				case "出版社科室1":
					if(ObjectUtil.notNull(pmphUsers)){
						for (PmphUser pmphUser : pmphUsers) {//把该部门人员移到人民卫生出版社部门下
							pmphUser.setDepartmentId(0L);
							pmphUserService.updateUser(pmphUser);
						}
					}
					//删除多余的部门
					pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId());
					break;
				case "公司领导":
					//查询该部门下的所有成员
					if(ObjectUtil.notNull(pmphUsers)){
						for (PmphUser pmphUser : pmphUsers) {//把该部门人员移到人民卫生出版社部门下
							pmphUser.setDepartmentId(0L);
							pmphUserService.updateUser(pmphUser);
						}
					}
					//删除多余的部门
					pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId());
					break;
				case "其他":
					//查询该部门下的所有成员
					if(ObjectUtil.notNull(pmphUsers)){
						for (PmphUser pmphUser : pmphUsers) {//把该部门人员移到人民卫生出版社部门下
							pmphUser.setDepartmentId(0L);
							pmphUserService.updateUser(pmphUser);
						}
					}
					//删除多余的部门
					pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId());
					break;
				case "农协":
					//查询该部门下的所有成员
					if(ObjectUtil.notNull(pmphUsers)){
						for (PmphUser pmphUser : pmphUsers) {//把该部门人员移到人民卫生出版社部门下
							pmphUser.setDepartmentId(0L);
							pmphUserService.updateUser(pmphUser);
						}
					}
					//删除多余的部门
					pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId());
					break;
				default:
					break;
				}
			}
		}
	 }
}
